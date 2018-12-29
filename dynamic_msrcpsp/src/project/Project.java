package project;

import java.util.List;

/**
 * 项目类 
 * 包含案例文档相关信息
 * @author XiongKai
 *
 */
public class Project {
    // 项目任务集
    private Task[] tasks;
    //资源集
    private Resource[] resources;
    //技能种类
    private int numberOfSkill;
    
    private List<Task> noPreTasks;
    // ....Todo....

    public Project(Task[] tasks,Resource[] resources,int numberOfSkill){
        this.tasks=tasks;
        this.resources=resources;
        this.numberOfSkill=numberOfSkill;
    }
    
    
    public Task[] getTasks() {
        return tasks;
    }

    public void setTasks(Task[] tasks) {
        this.tasks = tasks;
    }

    public Resource[] getResources() {
        return resources;
    }

    public void setResources(Resource[] resources) {
        this.resources = resources;
    }

    public List<Task> getNoPreTasks() {
        return noPreTasks;
    }

    public void setNoPreTasks(List<Task> noPreTasks) {
        this.noPreTasks = noPreTasks;
    }


    public int getNumberOfSkill() {
        return numberOfSkill;
    }


    public void setNumberOfSkill(int numberOfSkill) {
        this.numberOfSkill = numberOfSkill;
    }

}
