package scheduling;

import java.util.LinkedList;

import project.Project;
import project.Resource;
import project.Skill;
import project.Task;

/**
 * 调度类 将任务安排给特定资源，并确定开始执行时间
 * 
 * @author XiongKai
 *
 */
public class Schedule {
    // 描述资源分配的染色体结构，即任务1,2,...,N分别对应的资源分配序列
    private int[] chromosome;
    private Project project;

    public Schedule(int[] chromosome, Project project) {
        this.chromosome = chromosome;
        this.project = project;
        //由于不同个体之间，资源和任务的部分属性不同，所以创建新个体之前对相关属性进行重置
        clear();
        scheduleGegenateScheme(chromosome, project);
    }

    /**
     * 计划生成方案：基于贪婪策略的计划生成方案 
     * 1.循环遍历每一个任务
     * 2.如果任务具有紧后任务
     *   2.1 preEnd=end time all predecessors 
     *   2.2 resEnd=end time of assigned resource work 
     *   2.3 start=max(preEnd,resEnd) 
     *   2.4 schedule.assign(task,resource,start) 3 循环结束
     * 4.再次循环任务集 
     * 5.如果任务不具有紧后任务
     *   同理2操作 
     * 6.循环结束
     * 
     */ 
    public void scheduleGegenateScheme(int[] chromosome, Project project) {
        Task[] tasks = project.getTasks();
        Resource[] resources = project.getResources();
        boolean[] hasSuccesors = getSuccesors();
        for (int i = 0; i < tasks.length; i++) {
            // 任务具有紧后任务
            if (hasSuccesors[i]) {
                Task t = tasks[i];
                // 任务的紧前任务集所有任务最后完成的时刻
                int preEnd = getPredecessorsEndTime(t);
                // 任务分配的资源
                int rID = chromosome[i];
                // 所分配资源完成上一个任务时刻
                int resEnd = resources[rID - 1].getFinishTime();
                // 任务的开始执行时间
                int start = Math.max(preEnd, resEnd);

                assign(t, resources[rID - 1], start);
            }
        }

        for (int i = 0; i < tasks.length; i++) {
            if (!hasSuccesors[i]) {
                Task t = tasks[i];
                // 任务的紧前任务集所有任务最后完成的时刻
                int preEnd = getPredecessorsEndTime(t);
                // 任务分配的资源
                int rID = chromosome[i];
                // 所分配资源完成上一个任务时刻
                int resEnd = resources[rID - 1].getFinishTime();
                // 任务的开始执行时间
                int start = Math.max(preEnd, resEnd);

                assign(t, resources[rID - 1], start);
            }
        }
    }

    public void assign(Task t, Resource res, int timestamp) {
        t.setResourceID(res.getId());
        t.setStartTime(timestamp);
        // 估计任务当前的执行工期
        int duration = estimateDurationOfTask(t, res);
        t.setSpecificDuration(duration);
        // 记录资源完成该任务的时刻
        res.setFinishTime(timestamp + duration);
        // 将该任务添加到资源的执行任务链表中
        res.getAssignedTasks().add(t);

        // 更新资源的技能水平
        Skill[] skills = res.getSkills();
        int[] accumulatedTime = res.getAccumulatedTime();
        double[] preferToSkills = res.getPreferToSkills();
        double[] initLevel = res.getInitLevel();
        int sIndex = getSkillIndex(res, t.getReqSkill());
        accumulatedTime[sIndex] += duration;
        Skill usedSkill = skills[sIndex];
        double prefer = preferToSkills[sIndex];
        double init = initLevel[sIndex];

        res.setAccumulatedTime(accumulatedTime);
        updateResouceSkillLevel(res, usedSkill, prefer, init, accumulatedTime[sIndex]);
    }

    public int getPredecessorsEndTime(Task t) {
        int preEnd = 0;
        int[] predecessors = t.getPredecessors();
        for (int i = 0; i < predecessors.length; i++) {
            int p = predecessors[i];
            Task preTask = project.getTasks()[p - 1];
            if (preTask.getStartTime() + preTask.getSpecificDuration() > preEnd) {
                preEnd = preTask.getStartTime() + preTask.getSpecificDuration();
            }
        }
        return preEnd;
    }

    public boolean[] getSuccesors() {
        Task[] tasks = project.getTasks();
        boolean[] hasSuccesors = new boolean[tasks.length];
        for (int i = 0; i < tasks.length; i++) {
            Task t = tasks[i];
            int[] pres = t.getPredecessors();
            for (int j = 0; i < pres.length; j++) {
                int predecessor = pres[j];
                int tempIndex = predecessor - 1;
                hasSuccesors[tempIndex] = true;
            }
        }
        return hasSuccesors;
    }

    public void updateResouceSkillLevel(Resource r, Skill usedSkill, double prefer, double initlevel, int accumulateTime) {

        double LA = r.getLearnAbility();
        double SD = usedSkill.getDifficulty();
        double growth = LA * prefer / SD;
        double offset = artanhx(initlevel / 4);// L=4

        double level = tanh(growth, offset, accumulateTime) * 4;
        usedSkill.setLevel(level);
    }

    public double tanh(double growthRate, double offset, double variable) {
        double ex = Math.pow(Math.E, growthRate * (variable + offset));
        double ey = Math.pow(Math.E, -growthRate * (variable + offset));
        double sinhx = ex - ey;
        double coshx = ex + ey;
        double tanhx = sinhx / coshx;
        return tanhx;
    }

    public double artanhx(double value) {
        double artanhx = (1 / 2) * Math.log((1 + value) / (1 - value));
        return artanhx;
    }

    public int estimateDurationOfTask(Task t, Resource r) {
        Skill s = t.getReqSkill();
        if (!r.hasSkill(s))
            return -1;
        double rLevel = r.getSkills()[getSkillIndex(r, s)].getLevel();
        int[] durations = t.getDurations();
        int index = (int) rLevel - 1;
        return durations[index];
    }

    public int getSkillIndex(Resource r, Skill s) {
        Skill[] tempSkills = r.getSkills();
        for (int i = 0; i < tempSkills.length; i++) {
            Skill as = tempSkills[i];
            if (as.getType().equals(s.getType())) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 重置任务和资源的部分属性 包括任务的开始执行时间、任务的分配资源、任务的特定工期、资源技能的使用时间、资源分配的任务链表
     * 
     * @param
     */
    public void clear() {
        Task[] _tasks = project.getTasks();
        int L = _tasks.length;
        for (int i = 0; i < L; i++) {
            Task t = _tasks[i];
            t.setStartTime(-1);
            t.setResourceID(-1);
            t.setSpecificDuration(-1);
        }

        Resource[] _resources = project.getResources();
        for (int i = 0; i < _resources.length; i++) {
            Resource r = _resources[i];
            r.setFinishTime(-1);
            r.setAccumulatedTime(new int[r.getSkills().length]);
            r.setAssignedTasks(new LinkedList<>());
        }
    }

    public Project getProject() {
        return this.project;
    }

    public int[] getChromosome() {
        return chromosome;
    }

    public void setChromosome(int[] chromosome) {
        this.chromosome = chromosome;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
