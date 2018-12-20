package scheduling;

import project.Resource;
import scheduling.schedule_generate_scheme.GreedyBasedSGS;

/**
 * �����㷨����ĸ���
 * ��������Ļ�����Ϣ��Ⱦɫ��ṹ��Ŀ��ֵ
 * @author XiongKai
 *
 */
public class BaseIndividual implements Comparable<BaseIndividual>{
	protected Schedule schedule;
	//��Ŀ����
	protected int makespan;
	//��Ŀ�ܳɱ�
	protected double cost;
	//��Դ�Է�������������
	protected double satisfaction;
	
	public BaseIndividual(Schedule schedule){
		this.schedule=schedule;
		
		//�������յ��ȷ����ĸ���Ŀ��ֵ
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
