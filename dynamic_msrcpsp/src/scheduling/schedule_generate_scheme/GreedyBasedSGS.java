package scheduling.schedule_generate_scheme;

import project.Resource;
import project.Task;
import scheduling.Schedule;

/**
 * 基于贪婪策略的计划生成方案
 * 根据输入的资源分配序列，并基于贪婪策略安排任务的开始时间，最后生成最终的调度方案
 * @author XiongKai
 *
 */
public class GreedyBasedSGS {
	
	public GreedyBasedSGS(){
		
	}
	
	public void scheduleGegenateScheme(int[] chromosome,Schedule schedule){
		/*
		 * 1 循环遍历每一个任务
		 * 2 如果任务具有紧后任务
		 * 	 2.1 preEnd=end time all predecessors
		 *   2.2 resEnd=end time of assigned resource work
		 *   2.3 start=max(preEnd,resEnd)
		 *   2.4 schedule.assign(task,resource,start)   
		 * 3 循环结束
		 * 4 再次循环任务集
		 * 5 如果任务不具有紧后任务
		 *   同理2操作
		 * 6循环结束
		 */
		Task[] tasks=schedule.getProject().getTasks();
		Resource[] resources=schedule.getProject().getResources();
		boolean[] hasSuccesors=schedule.getSuccesors();
		for(int i=0;i<tasks.length;i++){
			Task t=tasks[i];
			//任务具有紧后任务
			if(hasSuccesors[i]){
				//任务的紧前任务集
				int[] predecessors=t.getPredecessors();
				//紧前任务集中任务
				
				//该任务分配的资源
			}
		}
	}
}
