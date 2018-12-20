package scheduling;

import java.util.LinkedList;

import project.Project;
import project.Resource;
import project.Skill;
import project.Task;

/**
 * ������
 * �������Ÿ��ض���Դ����ȷ����ʼִ��ʱ��
 * @author XiongKai
 *
 */
public class Schedule {
	//������Դ�����Ⱦɫ��ṹ��������1,2,...,N�ֱ��Ӧ����Դ��������
	private int[] chromosome;
	private Project project;

	
	public Schedule(int[] chromosome,Project project){
		this.chromosome=chromosome;
		this.project=project;
		clear();
		scheduleGegenateScheme(chromosome, project);
	}
	
	/**�ƻ����ɷ���������̰�����Եļƻ����ɷ���
	 * 1 ѭ������ÿһ������
	 * 2 ���������н�������
	 * 	 2.1 preEnd=end time all predecessors
	 *   2.2 resEnd=end time of assigned resource work
	 *   2.3 start=max(preEnd,resEnd)
	 *   2.4 schedule.assign(task,resource,start)   
	 * 3 ѭ������
	 * 4 �ٴ�ѭ������
	 * 5 ������񲻾��н�������
	 *   ͬ��2����
	 * 6ѭ������
	 * 
	 */
	public void scheduleGegenateScheme(int[] chromosome,Project project){
		Task[] tasks=project.getTasks();
		Resource[] resources=project.getResources();
		boolean[] hasSuccesors=getSuccesors();
		for(int i=0;i<tasks.length;i++){
			//������н�������
			if(hasSuccesors[i]){
				Task t=tasks[i];
				//����Ľ�ǰ�����������������ɵ�ʱ��
				int preEnd=getPredecessorsEndTime(t);
				//����������Դ
				int rID=chromosome[i];
				//��������Դ�����һ������ʱ��
				int resEnd=resources[rID-1].getFinishTime();
				//����Ŀ�ʼִ��ʱ��
				int start=Math.max(preEnd, resEnd);
				
				assign(t, resources[rID-1], start);
			}
		}
		
		for(int i=0;i<tasks.length;i++){
			if(!hasSuccesors[i]){
				Task t=tasks[i];
				//����Ľ�ǰ�����������������ɵ�ʱ��
				int preEnd=getPredecessorsEndTime(t);
				//����������Դ
				int rID=chromosome[i];
				//��������Դ�����һ������ʱ��
				int resEnd=resources[rID-1].getFinishTime();
				//����Ŀ�ʼִ��ʱ��
				int start=Math.max(preEnd, resEnd);
				
				assign(t, resources[rID-1], start);
			}
		}
	}
	
	public void assign(Task t,Resource res,int timestamp){
		t.setResourceID(res.getId());
		t.setStartTime(timestamp);
		//��������ǰ��ִ�й���
		int duration=estimateDurationOfTask(t, res);
		t.setSpecificDuration(duration);
		//��¼��Դ��ɸ������ʱ��
		res.setFinishTime(timestamp+duration);
		//����������ӵ���Դ��ִ������������
		res.getAssignedTasks().add(t);
		
		//������Դ�ļ���ˮƽ
		Skill[] skills=res.getSkills();
		int[] accumulatedTime=res.getAccumulatedTime();
		double[] preferToSkills=res.getPreferToSkills();
		double[] initLevel=res.getInitLevel();
		int sIndex=getSkillIndex(res, t.getReqSkill());
		accumulatedTime[sIndex]+=duration;
		Skill usedSkill=skills[sIndex];
		double prefer=preferToSkills[sIndex];
		double init=initLevel[sIndex];
		
		res.setAccumulatedTime(accumulatedTime);
		updateResouceSkillLevel(res, usedSkill, prefer, init,accumulatedTime[sIndex]);
	}
	
	public int getPredecessorsEndTime(Task t){
		int preEnd=0;
		int[] predecessors=t.getPredecessors();
		for(int i=0;i<predecessors.length;i++){
			int p=predecessors[i];
			Task preTask=project.getTasks()[p-1];
			if(preTask.getStartTime()+preTask.getSpecificDuration()>preEnd){
				preEnd=preTask.getStartTime()+preTask.getSpecificDuration();
			}
		}
		return preEnd;
	}
	
	public boolean[] getSuccesors(){
		Task[] tasks=project.getTasks();
		boolean[] hasSuccesors=new boolean[tasks.length];
		for(int i=0;i<tasks.length;i++){
			Task t=tasks[i];
			int[] pres=t.getPredecessors();
			for(int j=0;i<pres.length;j++){
				int predecessor=pres[j];
				int tempIndex=predecessor-1;
				hasSuccesors[tempIndex]=true;
			}
		}
		return hasSuccesors;
	} 
	
	public void updateResouceSkillLevel(Resource r,Skill usedSkill,double prefer,
			double initlevel,int accumulateTime){
		
		double LA=r.getLearnAbility();
		double SD=usedSkill.getDifficulty();
		double growth=LA*prefer/SD;
		double offset=artanhx(initlevel/4);//L=4
		
		double level=tanh(growth,offset,accumulateTime)*4;
		usedSkill.setLevel(level);
	}
	
	public double tanh(double growthRate,double offset,double variable){
		double ex=Math.pow(Math.E, growthRate*(variable+offset));
		double ey=Math.pow(Math.E, -growthRate*(variable+offset));
		double sinhx=ex-ey;
		double coshx=ex+ey;
		double tanhx=sinhx/coshx;
		return tanhx;
	}
	
	public double artanhx(double value){
		double artanhx=(1/2)*Math.log((1+value)/(1-value));
		return artanhx;
	}
	
	public int estimateDurationOfTask(Task t,Resource r){
		Skill s=t.getReqSkill();
		if(!r.hasSkill(s))
			return -1;
		double rLevel=r.getSkills()[getSkillIndex(r, s)].getLevel();
		int[] durations=t.getDurations();
		int index=(int)rLevel-1;
		return durations[index];
	}
	
	public int getSkillIndex(Resource r,Skill s){
		Skill[] tempSkills=r.getSkills();
		for(int i=0;i<tempSkills.length;i++){
			Skill as=tempSkills[i];
			if(as.getType().equals(s.getType())){
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * �����������Դ�Ĳ�������
	 * ��������Ŀ�ʼִ��ʱ�䡢����ķ�����Դ��������ض����ڡ���Դ���ܵ�ʹ��ʱ�䡢��Դ�������������
	 * @param 
	 */
	public void clear(){
		Task[] _tasks=project.getTasks();
		int L=_tasks.length;
		for(int i=0;i<L;i++){
			Task t=_tasks[i];
			t.setStartTime(-1);
			t.setResourceID(-1);
			t.setSpecificDuration(-1);
		}
		
		Resource[] _resources=project.getResources();
		for(int i=0;i<_resources.length;i++){
			Resource r=_resources[i];
			r.setFinishTime(-1);
			r.setAccumulatedTime(new int[r.getSkills().length]);
			r.setAssignedTasks(new LinkedList<>());
		}
	}
	
	public Project getProject(){
		return this.project;
	}

	public int[] getChromosome() {
		return chromosome;
	}

	public void setChromosome(int[] chromosome) {
		this.chromosome = chromosome;
	}

	public void setProject(Project project) {
		this.project = project;
	}
}
