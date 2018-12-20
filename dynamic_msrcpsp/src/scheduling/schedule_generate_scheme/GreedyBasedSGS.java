package scheduling.schedule_generate_scheme;

import project.Resource;
import project.Task;
import scheduling.Schedule;

/**
 * ����̰�����Եļƻ����ɷ���
 * �����������Դ�������У�������̰�����԰�������Ŀ�ʼʱ�䣬����������յĵ��ȷ���
 * @author XiongKai
 *
 */
public class GreedyBasedSGS {
	
	public GreedyBasedSGS(){
		
	}
	
	public void scheduleGegenateScheme(int[] chromosome,Schedule schedule){
		/*
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
		 */
		Task[] tasks=schedule.getProject().getTasks();
		Resource[] resources=schedule.getProject().getResources();
		boolean[] hasSuccesors=schedule.getSuccesors();
		for(int i=0;i<tasks.length;i++){
			//������н�������
			if(hasSuccesors[i]){
				Task t=tasks[i];
				//����Ľ�ǰ�����������������ɵ�ʱ��
				int preEnd=schedule.getPredecessorsEndTime(t);
				//����������Դ
				int rID=chromosome[i];
				//��������Դ�����һ������ʱ��
				int resEnd=resources[rID-1].getFinishTime();
				//����Ŀ�ʼִ��ʱ��
				int start=Math.max(preEnd, resEnd);
				
				schedule.assign(t, resources[rID-1], start);
			}
		}
		
		for(int i=0;i<tasks.length;i++){
			if(!hasSuccesors[i]){
				Task t=tasks[i];
				//����Ľ�ǰ�����������������ɵ�ʱ��
				int preEnd=schedule.getPredecessorsEndTime(t);
				//����������Դ
				int rID=chromosome[i];
				//��������Դ�����һ������ʱ��
				int resEnd=resources[rID-1].getFinishTime();
				//����Ŀ�ʼִ��ʱ��
				int start=Math.max(preEnd, resEnd);
				
				schedule.assign(t, resources[rID-1], start);
			}
		}
	}
}
