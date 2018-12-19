package project;

/**
 * ��Դ��
 * ��������Դ��ѧϰ�����Լ��Լ���ƫ���̶�
 * @author XiongKai
 *
 */
public class Resource {
	//��ԴID
	private int id;	
	//��Դ���յļ��ܼ�
	private Skill[] skills;	
	//��Դ�Լ��ܵ�ƫ��
	private double[] preferToSkills;
	//ѧϰ����
	private double learnAbility;	
	//нˮ
	private double salary;
	//��Դÿ��ܵ�ʹ��ʱ�����
	private int[] accumulatedTime;
	private int finishTime;
	private int idealStart;
	private int idealEnd;
	
	//ÿ��ܵĳ�ʼˮƽ
	private double[] initLevel;
	
	public Resource(int id,Skill[] skills,double[] preferToSkills,double learnAbility,double salary){
		this.id=id;
		this.skills=skills;
		this.preferToSkills=preferToSkills;
		this.learnAbility=learnAbility;
		this.salary=salary;
		for(int i=0;i<skills.length;i++){
			initLevel[i]=skills[i].getLevel();
		}
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

	public int getIdealStart() {
		return idealStart;
	}

	public void setIdealStart(int idealStart) {
		this.idealStart = idealStart;
	}

	public int getIdealEnd() {
		return idealEnd;
	}

	public void setIdealEnd(int idealEnd) {
		this.idealEnd = idealEnd;
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

	
	
}
