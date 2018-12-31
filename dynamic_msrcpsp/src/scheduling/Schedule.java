package scheduling;

import java.util.List;
import evolutionary_algorithms.CommonUtil;
import project.Project;
import project.Resource;
import project.Skill;
import project.Task;

/**
 * 调度类 
 * 包含任务-资源链表，程序中使用数组分别表示任务执行顺序序列和资源分配，并且每个元素是任务或资源的id值
 * @author XiongKai
 *
 */
public class Schedule {
    // 任务序列
    private int[] taskList;
    //资源分配链表
    private int[] resourceList;
    private Project project;

    public Schedule(int[] taskList, Project project) {
        this.taskList=taskList;
        this.project = project;
        this.resourceList=new int[taskList.length];
    }

    /**
     * 计划生成方案
     * 由于资源的技能水平可变，所以任务的可执行资源集是动态变化的
     * 根据任务执行顺序，确定每个任务的可执行资源集，然后基于某种规则或者随机方式选择资源
     * for i to n
     *    preEND=end time all predecessors
     *    resEnd=end time of assigned resource work
     *    start=max(preEnd,resEnd)
     *    schedule.assign(task,resource,start)
     * end
     * @param taskList  任务执行顺序
     * @param project   项目任务
     * @param rule      资源的选择规则
     */
    public void scheduleGenerateScheme(int[] taskList,Project project,String rule){
        Task[] tasks = project.getTasks();
        for(int i=0;i<taskList.length;i++){
            int taskID=taskList[i];
            Task t=tasks[taskID-1];
            //获取任务的可执行资源集
            List<Resource> capabelResources=CommonUtil.getCapableResources(t, project);
            //资源选择策略,随机选择
            Resource assignedResource=selectResourceRule(capabelResources,rule);
            // 任务分配的资源ID
            int resourceID = assignedResource.getId();
            resourceList[i]=resourceID;
            
            // 所分配资源完成上一个任务时刻
            int resEnd = assignedResource.getFinishTime();
            // 任务的紧前任务集所有任务最后完成的时刻
            int preEnd = getPredecessorsEndTime(t);
            // 任务的开始执行时间
            int start = Math.max(preEnd, resEnd);

            assign(t, assignedResource, start);
        }
    }
    
    /**
     * 从可执行资源集中选择资源分配给任务
     * rule=0表示随机选择
     * @param capabelResources
     * @param rule
     * @return
     */
    public Resource selectResourceRule(List<Resource> capabelResources,String rule){
        Resource r=null;
        int L=capabelResources.size();
        if(rule.equals("RANDOM")){
            //随机选择
            int rand=(int)(Math.random()*L);
            r=capabelResources.get(rand);
        }
        return r;
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

        updateResouceSkillLevel(res, usedSkill, prefer, init, accumulatedTime[sIndex]);
    }

    public int getPredecessorsEndTime(Task t) {
        int preEnd = 0;
        if(t.getPredecessors()!=null){
            int[] predecessors = t.getPredecessors();
            for (int i = 0; i < predecessors.length; i++) {
                int p = predecessors[i];
                Task preTask = project.getTasks()[p - 1];
                if (preTask.getStartTime() + preTask.getSpecificDuration() > preEnd) {
                    preEnd = preTask.getStartTime() + preTask.getSpecificDuration();
                }
            }
        }
        
        return preEnd;
    }

    public boolean[] getSuccesors() {
        Task[] tasks = project.getTasks();
        boolean[] hasSuccesors = new boolean[tasks.length];
        for (int i = 0; i < tasks.length; i++) {
            Task t = tasks[i];
            if(t.getPredecessors()!=null){
                int[] pres = t.getPredecessors();
                for (int j = 0; j < pres.length; j++) {
                    int predecessor = pres[j];
                    int tempIndex = predecessor - 1;
                    hasSuccesors[tempIndex] = true;
                }
            }
        }
        return hasSuccesors;
    }
    
    /*
     * 技能水平随使用时间变化
     * level(t)=tanh(a(t+I))*L
     */
    public void updateResouceSkillLevel(Resource r, Skill usedSkill, double prefer, double initlevel, int accumulateTime) {

        double LA = r.getLearnAbility();
        double SD = usedSkill.getDifficulty();
        double a = LA * prefer / SD;
        //当t=0,L=4
        double I = atanh(initlevel / 4)/a;
        //单位小时，天，还是月？选择月为时间单位
        double normalAccumulatedTile=((double)accumulateTime)/8/30; 
        double f = Math.tanh(a*(normalAccumulatedTile+I))*4;
        //f有可能等于4，所以要避免该情况发生
        double result=f<4.0?f:3.0;
        usedSkill.setLevel(result);
    }

    public double tanh(double growthRate, double offset, double variable) {
        double ex = Math.pow(Math.E, growthRate * (variable + offset));
        double ey = Math.pow(Math.E, -growthRate * (variable + offset));
        double sinhx = ex - ey;
        double coshx = ex + ey;
        double tanhx = sinhx / coshx;
        return tanhx;
    }

    public double atanh(double value) {
        double artanhx = 0.5 * Math.log((1+value) / (1-value));
        return artanhx;
    }

    public int estimateDurationOfTask(Task t, Resource r) {
        Skill s = t.getReqSkill();
        if (!r.hasSkill(s))
            return -1;
        //在资源的技能集中找到任务所需的技能
        Skill[] skills=r.getSkills();
        int index=getSkillIndex(r, s);
        double rLevel = skills[index].getLevel();
        int[] durations = t.getDurations();
        int k = (int) rLevel-1;
        return durations[k];
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
     * 重置任务和资源的部分属性
     * 包括任务的开始执行时间、任务的分配资源、任务的特定工期
     * 资源技能的使用时间、资源分配的任务链表、资源的技能水平
     * 
     * @param
     */
    public void reset() {
        Task[] _tasks = project.getTasks();
        int L = _tasks.length;
        for (int i = 0; i < L; i++) {
            Task t = _tasks[i];
            t.setStartTime(-1);
            t.setResourceID(-1);
            t.setSpecificDuration(-1);
        }

        Resource[] _resources = project.getResources();
        int[] accumulatedUsedTime=null;
        List<Task> assignedTasks=null;
        double[] initLevels=null;
        Skill[] skills=null;
        for (int i = 0; i < _resources.length; i++) {
            Resource r = _resources[i];
            r.setFinishTime(-1);
            
            //资源所掌握技能的累积使用时间重置
            accumulatedUsedTime=r.getAccumulatedTime();
            for(int j=0;j<accumulatedUsedTime.length;j++){
                accumulatedUsedTime[j]=0;
            }
            
            //资源所分配任务集重置
            assignedTasks=r.getAssignedTasks();
            if(!assignedTasks.isEmpty()){
                assignedTasks.clear();;
            }
            
            //资源的技能水平重置为初始水平
            initLevels=r.getInitLevel();
            skills=r.getSkills();
            for(int j=0;j<skills.length;j++){
                skills[j].setLevel(initLevels[j]);
            }
        }
    }

    public Project getProject() {
        return this.project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    /**
     * @return the taskList
     */
    public int[] getTaskList() {
        return taskList;
    }

    /**
     * @param taskList the taskList to set
     */
    public void setTaskList(int[] taskList) {
        this.taskList = taskList;
    }

    /**
     * @return the resourceList
     */
    public int[] getResourceList() {
        return resourceList;
    }

    /**
     * @param resourceList the resourceList to set
     */
    public void setResourceList(int[] resourceList) {
        this.resourceList = resourceList;
    }

}
