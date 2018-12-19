package project;

/**
 * ������
 * ��������������׳̶��Լ�����ˮƽ������
 * @author XiongKai
 *
 */
public class Skill {
	private String type;	//��������
	private double level;	//��������ˮƽ
	private double difficulty;	//�������׳̶�
	
	public Skill(String type,double level,double difficulty){
		this.type=type;
		this.level=level;
		this.difficulty=difficulty;
	}
	public Skill(){
		this("",0.0,0.0);
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public double getLevel() {
		return level;
	}
	public void setLevel(double level) {
		this.level = level;
	}
	public double getDifficulty() {
		return difficulty;
	}
	public void setDifficulty(double difficulty) {
		this.difficulty = difficulty;
	}
	
	public String toString(){
		return (new StringBuilder()).append(type).append(" : ").append(level).append(" : ").append(difficulty).toString();
	}
	
	public boolean equals(Object o){
		if(!(o instanceof Skill))
			return false;
		else{
			Skill skill=(Skill) o;
			return type.equals(skill.type) && level==skill.level && difficulty==skill.difficulty;
			
		}
	}
	
}
