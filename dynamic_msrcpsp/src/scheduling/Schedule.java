package scheduling;

import project.Project;
import project.Resource;
import project.Skill;
import project.Task;

/**
 * 调度类
 * 将任务安排给特定资源，并确定开始执行时间
 * @author XiongKai
 *
 */
public class Schedule {
	private Project project;
	
	public Schedule(Project project){
		this.project=project;
		clear(true);
	}
	
	public Schedule(Schedule schedule){
		
	}
	
	public void assign(Task t,Resource res,int timestamp){
		t.setResourceID(res.getId());
		t.setStartTime(timestamp);
		//估计任务当前的执行工期
		int duration=estimateDurationOfTask(t, res);
		res.setFinishTime(timestamp+duration);
		
		//更新资源的技能水平
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
	 * 重置任务的开始时间以及分配的资源
	 * @param withAssignments
	 */
	public void clear(boolean withAssignments){
		if(withAssignments){
			Task[] _tasks=project.getTasks();
			int L=_tasks.length;
			for(int i=0;i<L;i++){
				Task t=_tasks[i];
				t.setStartTime(-1);
				t.setResourceID(-1);
			}
		}else{
			Task[] _tasks2=project.getTasks();
			for(int i=0;i<_tasks2.length;i++){
				Task t=_tasks2[i];
				t.setStartTime(-1);
			}
		}
		Resource[] _resources=project.getResources();
		for(int i=0;i<_resources.length;i++){
			Resource r=_resources[i];
			r.setFinishTime(-1);
		}
	}
	
	public Project getProject(){
		return this.project;
	}
}
