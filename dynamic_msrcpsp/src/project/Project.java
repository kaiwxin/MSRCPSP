package project;

import java.util.List;

/**
 * 项目类 包含项目的基本信息
 * 
 * @author XiongKai
 *
 */
public class Project {
    // 项目任务集
    private Task[] tasks;

    private Resource[] resources;

    private List<Task> noPreTasks;

    // ....To do....

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

}
