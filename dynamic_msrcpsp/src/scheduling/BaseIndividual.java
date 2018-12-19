package scheduling;

import scheduling.schedule_generate_scheme.GreedyBasedSGS;

/**
 * 所有算法个体的父类
 * 描述个体的基本信息：染色体结构、目标值
 * @author XiongKai
 *
 */
public class BaseIndividual implements Comparable<BaseIndividual>{
	//描述资源分配的染色体结构，即任务1,2,...,N分别对应的资源分配序列
	protected int[] chromosome;
	//项目工期
	protected int makespan;
	//项目总成本
	protected double cost;
	//资源对分配任务的满意度
	protected double satisfaction;
	
	public BaseIndividual(int[] chromosome,Schedule schedule,GreedyBasedSGS sgs){
		this.chromosome=chromosome;
		//使用计划生成方案（SGS），生成最终的调度
		sgs.scheduleGegenateScheme(chromosome, schedule);
		
		//计算最终调度方案的各个目标值
	}
	
	@Override
	public int compareTo(BaseIndividual o) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
}
