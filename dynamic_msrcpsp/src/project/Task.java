package project;

import java.util.Arrays;

/**
 * 任务类 任务的工期不再是固定值，其值取决于资源的技能熟练水平 每一种水平对应一个整数工期
 * 
 * @author XiongKai
 *
 */
public class Task implements Comparable<Task> {
    // 编号
    private int id;
    // 技能要求
    private Skill reqSkill;
    // 所有可能工期
    private int[] durations;
    // 在具体调度方案中的具体工期
    private int specificDuration;
    // 紧前任务集
    private int[] predecessors;
    private int resourceID;
    // 开始执行时刻
    private int startTime;

    public Task(int id, Skill reqSkill, int[] durations) {
        this.id = id;
        this.reqSkill = reqSkill;
        this.durations = durations;
    }

    public Task(int id, Skill reqSkill, int[] durations, int[] predecessors) {
        this(id, reqSkill, durations);
        this.predecessors = predecessors;
    }

    /**
     * 判断当前任务与指定任务是否存在紧前约束
     * 
     * @return
     */
    public boolean isExistConstaint(Task t) {
        boolean flag = false;
        if (predecessors == null) {
            return flag;
        }
        for (int i = 0; i < predecessors.length; i++) {
            if (t.getId() == this.predecessors[i]) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    public boolean equals(Object o) {
        if (!(o instanceof Task)) {
            return false;
        } else {
            Task task = (Task) o;
            return id == task.id && durations == task.durations && Arrays.equals(predecessors, task.predecessors)
                    && reqSkill == task.reqSkill;
        }

    }

    @Override
    public int compareTo(Task o) {
        return Integer.compare(startTime, o.startTime);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Skill getReqSkill() {
        return reqSkill;
    }

    public void setReqSkill(Skill reqSkill) {
        this.reqSkill = reqSkill;
    }

    public int[] getDurations() {
        return durations;
    }

    public void setDurations(int[] durations) {
        this.durations = durations;
    }

    public int[] getPredecessors() {
        return predecessors;
    }

    public void setPredecessors(int[] predecessors) {
        this.predecessors = predecessors;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getResourceID() {
        return resourceID;
    }

    public void setResourceID(int resourceID) {
        this.resourceID = resourceID;
    }

    public int getSpecificDuration() {
        return specificDuration;
    }

    public void setSpecificDuration(int specificDuration) {
        this.specificDuration = specificDuration;
    }

}
