package evolutionary_algorithms.differential_evolution_and_greedy_algorithm.mutation;

import java.util.HashSet;
import java.util.Stack;

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
     * 变异之后，对各元素进行修复操作，使其满足技能约束要求
     * 最后返回新的种群
     * @param pop
     * @return
     */
    public BasePopulation mutation(BasePopulation pop){
        BaseIndividual[] newPop=new BaseIndividual[pop.size()];
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
            for(int j=0;i<parentChromosome.length;j++){
                genotype[j]=chromosome1[j]+F*(chromosome2[j]-chromosome3[j]);
            }
            
            //将基因型染色体修复成显示型染色体，使资源分配满足技能约束条件
            Project project=parent.getSchedule().getProject();
            int[] pheotype=CommonUtil.repair(genotype, project);
            
            Schedule newSchedule=new Schedule(pheotype,project);
            newPop[i]=new BaseIndividual(newSchedule);
        }
        
        return new BasePopulation(newPop);
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
