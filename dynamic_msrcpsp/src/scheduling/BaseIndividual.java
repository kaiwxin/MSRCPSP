package scheduling;

import scheduling.schedule_generate_scheme.GreedyBasedSGS;

/**
 * �����㷨����ĸ���
 * ��������Ļ�����Ϣ��Ⱦɫ��ṹ��Ŀ��ֵ
 * @author XiongKai
 *
 */
public class BaseIndividual implements Comparable<BaseIndividual>{
	//������Դ�����Ⱦɫ��ṹ��������1,2,...,N�ֱ��Ӧ����Դ��������
	protected int[] chromosome;
	//��Ŀ����
	protected int makespan;
	//��Ŀ�ܳɱ�
	protected double cost;
	//��Դ�Է�������������
	protected double satisfaction;
	
	public BaseIndividual(int[] chromosome,Schedule schedule,GreedyBasedSGS sgs){
		this.chromosome=chromosome;
		//ʹ�üƻ����ɷ�����SGS�����������յĵ���
		sgs.scheduleGegenateScheme(chromosome, schedule);
		
		//�������յ��ȷ����ĸ���Ŀ��ֵ
	}
	
	@Override
	public int compareTo(BaseIndividual o) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
}
