package evolutionary_algorithms.differential_evolution_and_greedy_algorithm.mutation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Stack;
import java.util.List;

import evolutionary_algorithms.CommonUtil;
import project.Project;
import scheduling.BaseIndividual;
import scheduling.BasePopulation;
import scheduling.Schedule;

/**
 * 差分进化算法的变异算子
 * @author XiongKai
 *
 */
public class Mutation {
    //变异概率
    private double F;
    
    public Mutation(double value){
        this.F=value;
    }
    
    /**
     * 变异操作
     * 随机从种群中选择三个不同的个体对当前个体进行变异进化
     * 对变异后的任务链表和资源链表分别进行修复，使其满足要求
     * @param pop  种群
     * @return  返回修复后任务-资源链表集合      
     */
    public List<int[][]> mutation(BasePopulation pop){
        List<int[][]> task_resourceLists=new ArrayList<>();
        BaseIndividual[] oldPop=pop.getPopulation();
        for(int i=0;i<pop.size();i++){
            //待变异的个体
            BaseIndividual parent=oldPop[i];
            Schedule scheduleOfParent=parent.getSchedule();
            int[] taskList=scheduleOfParent.getTaskList();
            int[] resourceList=scheduleOfParent.getResourceList();

            //从（pop-1）种群中随机选择三个不相同的个体
            HashSet<Integer> set=new HashSet<>();
            set.add(i);
            randomSet(0,pop.size()-1,4,set);
            set.remove(i);
            Stack<Integer> stack=new Stack<>();
            for(int index:set){
                stack.push(index);
            }
            BaseIndividual indiv1=oldPop[stack.pop()];
            BaseIndividual indiv2=oldPop[stack.pop()];
            BaseIndividual indiv3=oldPop[stack.pop()];
            int[] taskList_1=indiv1.getSchedule().getTaskList();
            int[] resourceList_1=indiv1.getSchedule().getResourceList();
            int[] taskList_2=indiv2.getSchedule().getTaskList();
            int[] resourceList_2=indiv2.getSchedule().getResourceList();
            int[] taskList_3=indiv3.getSchedule().getTaskList();
            int[] resourceList_3=indiv3.getSchedule().getResourceList();

            //任务链表进行变异，此处用于变异的元素是任务的执行顺序号
            //比如任务执行顺序是[T1,T4,T2,T3],那么任务集{T1,T2,T3,T4}的执行顺序号对应为[1,3,4,2]


            //资源链表变异


        }
        return task_resourceLists;
    }

    public List<int[]> mutationpOfResourceList(BasePopulation pop){
        
        List<int[]> phenotypeList=new ArrayList<>();
        BaseIndividual[] oldPop=pop.getPopulation();
        
        for(int i=0;i<pop.size();i++){
            //待变异的个体
            BaseIndividual parent=oldPop[i];
            int[] parentChromosome=parent.getSchedule().getChromosome();
            
            //从（pop-1）种群中随机选择三个不相同的个体
            HashSet<Integer> set=new HashSet<>();
            set.add(i);
            randomSet(0,pop.size()-1,4,set);
            set.remove(i);
            Stack<Integer> stack=new Stack<>();
            for(int index:set){
                stack.push(index);
            }
            BaseIndividual indiv1=oldPop[stack.pop()];
            int[] chromosome1=indiv1.getSchedule().getChromosome();
            BaseIndividual indiv2=oldPop[stack.pop()];
            int[] chromosome2=indiv2.getSchedule().getChromosome();
            BaseIndividual indiv3=oldPop[stack.pop()];
            int[] chromosome3=indiv3.getSchedule().getChromosome();
            
            //变异原理
            double[] genotype=new double[parentChromosome.length];
            for(int j=0;j<parentChromosome.length;j++){
                genotype[j]=chromosome1[j]+F*(chromosome2[j]-chromosome3[j]);
            }
            
            //将基因型染色体修复成显示型染色体，使资源分配满足技能约束条件
            Project project=parent.getSchedule().getProject();
            int[] phenotype=CommonUtil.repair(genotype, project);
            phenotypeList.add(phenotype);
        
        }
        return phenotypeList;
    }
    
    
    
    
    /**
     * 随机指定范围内N个不重复的数
     * 利用HashSet的特征-只能存放不同的值
     * @param min 指定范围最小值
     * @param max 指定范围最大值   
     * @param n 随机数个数
     * @param set 集合
     */
    public void randomSet(int min,int max,int n,HashSet<Integer> set){
        if(n>(max-min+1) || max<min)
            throw new RuntimeException("给定范围内无法产生指定的随机数");
        
        int setSize=set.size();
        for(int i=0;i<(n-setSize);i++){
            int num=(int)(Math.random()*(max-min))+min;
            set.add(num);
        }
        
        //如果存入的数小于指定生成的个数，则调用递归再生成剩余个数的随机数
        if(setSize<n)
            randomSet(min,max,n,set);
    }
}
