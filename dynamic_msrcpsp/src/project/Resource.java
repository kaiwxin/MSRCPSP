package project;

import java.util.LinkedList;
import java.util.List;

/**
 * 资源类
 * 考虑了资源的学习能力以及对技能偏爱程度
 * @author XiongKai
 *
 */
public class Resource {
	//资源ID
	private int id;	
	//资源掌握的技能集
	private Skill[] skills;	
	//资源对技能的偏爱
	private double[] preferToSkills;
	//学习能力
	private double learnAbility;	
	//薪水
	private double salary;
	//资源每项技能的使用时间积累
	private int[] accumulatedTime;
	private int finishTime;
	
	//资源所要执行的任务链表
	private List<Task> assignedTasks;
	//每项技能的初始水平
	private double[] initLevel;
	
	public Resource(int id,Skill[] skills,double[] preferToSkills,double learnAbility,double salary){
		this.id=id;
		this.skills=skills;
		this.preferToSkills=preferToSkills;
		this.accumulatedTime=new int[skills.length];
		this.learnAbility=learnAbility;
		this.salary=salary;
		for(int i=0;i<skills.length;i++){
			initLevel[i]=skills[i].getLevel();
		}
		this.assignedTasks=new LinkedList<>();
	}
	
	public boolean hasSkill(Skill s){
		Skill[] _skills=skills;
		for(int i=0;i<_skills.length;i++){
			Skill skill=_skills[i];
			if(skill.getType().equals(s.getType()) && skill.getLevel()>=s.getLevel()){
				return true;
			}
		}
		return false;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Skill[] getSkills() {
		return skills;
	}

	public void setSkills(Skill[] skills) {
		this.skills = skills;
	}

	public double[] getPreferToSkills() {
		return preferToSkills;
	}

	public void setPreferToSkills(double[] preferToSkills) {
		this.preferToSkills = preferToSkills;
	}

	public double getLearnAbility() {
		return learnAbility;
	}

	public void setLearnAbility(double learnAbility) {
		this.learnAbility = learnAbility;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}
	
	public int getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(int finishTime) {
		this.finishTime = finishTime;
	}

	public int[] getAccumulatedTime() {
		return accumulatedTime;
	}

	public void setAccumulatedTime(int[] accumulatedTime) {
		this.accumulatedTime = accumulatedTime;
	}

	public double[] getInitLevel() {
		return initLevel;
	}

	public List<Task> getAssignedTasks() {
		return assignedTasks;
	}

	public void setAssignedTasks(List<Task> assignedTasks) {
		this.assignedTasks = assignedTasks;
	}

	
	
}
