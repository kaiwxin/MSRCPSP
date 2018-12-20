package project;

import java.util.Arrays;

/**
 * ������
 * ����Ĺ��ڲ����ǹ̶�ֵ����ֵȡ������Դ�ļ�������ˮƽ
 * ÿһ��ˮƽ��Ӧһ����������
 * @author XiongKai
 *
 */
public class Task implements Comparable<Task> {
	//���
	private int id;
	//����Ҫ��
	private Skill reqSkill;
	//���п��ܹ���
	private int[] durations;
	//�ھ�����ȷ����еľ��幤��
	private int specificDuration;
	//��ǰ����
	private int[] predecessors;
	private int resourceID;
	//��ʼִ��ʱ��
	private int startTime;
	
	public Task(int id,Skill reqSkill,int[] durations){
		this.id=id;
		this.reqSkill=reqSkill;
		this.durations=durations;
	}
	
	public Task(int id,Skill reqSkill,int[] durations,int[] predecessors){
		this(id,reqSkill,durations);
		this.predecessors=predecessors;
	}
	
	/**
	 * �жϵ�ǰ������ָ�������Ƿ���ڽ�ǰԼ��
	 * @return
	 */
	public boolean isExistConstaint(Task t){
		boolean flag=false;
		if(predecessors==null){
			return flag;
		}
		for(int i=0;i<predecessors.length;i++){
			if(t.getId()==this.predecessors[i]){
				flag=true;
				break;
			}
		}
		return flag;
	}

	public boolean equals(Object o){
		if(!(o instanceof Task)){
			return false;
		}
		else{
			Task task=(Task) o;
			return id==task.id && durations==task.durations 
					&& Arrays.equals(predecessors, task.predecessors) 
					&& reqSkill==task.reqSkill;
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
