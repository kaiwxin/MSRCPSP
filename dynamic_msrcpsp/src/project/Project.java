package project;

import java.util.List;

/**
 * ��Ŀ��
 * ������Ŀ�Ļ�����Ϣ
 * @author XiongKai
 *
 */
public class Project {
	//��Ŀ����
	private Task[] tasks;
	
	private Resource[] resources;
	
	private List<Task> noPreTasks;

	//....undo....
	
	
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
