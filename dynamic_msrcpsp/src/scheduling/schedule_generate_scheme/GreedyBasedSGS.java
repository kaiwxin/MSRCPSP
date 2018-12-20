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
			//任务具有紧后任务
			if(hasSuccesors[i]){
				Task t=tasks[i];
				//任务的紧前任务集所有任务最后完成的时刻
				int preEnd=schedule.getPredecessorsEndTime(t);
				//任务分配的资源
				int rID=chromosome[i];
				//所分配资源完成上一个任务时刻
				int resEnd=resources[rID-1].getFinishTime();
				//任务的开始执行时间
				int start=Math.max(preEnd, resEnd);
				
				schedule.assign(t, resources[rID-1], start);
			}
		}
		
		for(int i=0;i<tasks.length;i++){
			if(!hasSuccesors[i]){
				Task t=tasks[i];
				//任务的紧前任务集所有任务最后完成的时刻
				int preEnd=schedule.getPredecessorsEndTime(t);
				//任务分配的资源
				int rID=chromosome[i];
				//所分配资源完成上一个任务时刻
				int resEnd=resources[rID-1].getFinishTime();
				//任务的开始执行时间
				int start=Math.max(preEnd, resEnd);
				
				schedule.assign(t, resources[rID-1], start);
			}
		}
	}
}
