package project;

import java.util.List;

/**
 * 项目类 
 * 包含案例文档相关信息
 * @author XiongKai
 *
 */
public class Project {
    //项目任务数量
    private int numberOfTask;
    //项目资源数量
    private int numberOfResource;
    //技能种类
    private int numberOfSkill;
    
    // 项目任务集
    private Task[] tasks;
    //资源集
    private Resource[] resources;
    

    public Project(Task[] tasks,Resource[] resources,int numberOfSkill){
        this.tasks=tasks;
        this.resources=resources;
        this.numberOfTask=tasks.length;
        this.numberOfResource=resources.length;
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

    public int getNumberOfSkill() {
        return numberOfSkill;
    }


    public void setNumberOfSkill(int numberOfSkill) {
        this.numberOfSkill = numberOfSkill;
    }


    public int getNumberOfTask() {
        return numberOfTask;
    }


    public void setNumberOfTask(int numberOfTask) {
        this.numberOfTask = numberOfTask;
    }


    public int getNumberOfResource() {
        return numberOfResource;
    }


    public void setNumberOfResource(int numberOfResource) {
        this.numberOfResource = numberOfResource;
    }

}
