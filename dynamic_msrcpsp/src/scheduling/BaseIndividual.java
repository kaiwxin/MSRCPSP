package scheduling;

import project.Resource;
import scheduling.schedule_generate_scheme.GreedyBasedSGS;

/**
 * 所有算法个体的父类
 * 描述个体的基本信息：染色体结构、目标值
 * @author XiongKai
 *
 */
public class BaseIndividual implements Comparable<BaseIndividual>{
	protected Schedule schedule;
	//项目工期
	protected int makespan;
	//项目总成本
	protected double cost;
	//资源对分配任务的满意度
	protected double satisfaction;
	
	public BaseIndividual(Schedule schedule){
		this.schedule=schedule;
		
		//计算最终调度方案的各个目标值
		this.makespan=calMakespan(schedule);
	}
	
	public int calMakespan(Schedule schedule){
		int result=0;
		Resource[] resources=schedule.getProject().getResources();
		for(int i=0;i<resources.length;i++){
			Resource r=resources[i];
			if(r.getFinishTime()>result){
				result=r.getFinishTime();
			}
		}
		return result;
	}
	
	public double calCost(){
		double result=0.0;
		
		return result;
	}
	
	
	@Override
	public int compareTo(BaseIndividual o) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
}
