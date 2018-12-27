package evolutionary_algorithms.differential_evolution_and_greedy_algorithm;

import java.util.List;

import evolutionary_algorithms.CommonUtil;
import evolutionary_algorithms.differential_evolution_and_greedy_algorithm.crossover.Crossover;
import evolutionary_algorithms.differential_evolution_and_greedy_algorithm.initial_population.InitialPopulation;
import evolutionary_algorithms.differential_evolution_and_greedy_algorithm.mutation.Mutation;
import project.Project;
import scheduling.BaseIndividual;
import scheduling.BasePopulation;
/**
 * 基于贪婪策略的差分进化算法
 * @author XiongKai
 *
 */
public class DEGA {
    //最大迭代次数
    private int maxGenerationCount;
    //初始化种群
    private InitialPopulation initialPop;
    //变异算子
    private Mutation mutation;
    //交叉算子
    private Crossover cross;
    
    public DEGA(int maxGenerationCount,InitialPopulation initialPop,Mutation mutation,Crossover cross){
        this.maxGenerationCount=maxGenerationCount;
        this.initialPop=initialPop;
        this.mutation=mutation;
        this.cross=cross;
    }
    
    /**
     * 初始化种群
     * @param project
     * @return
     */
    public BasePopulation initialPopulation(Project project){
        return initialPop.generate(project);
    } 
    
    /**
     * 差分进化算法生成新一代种群
     * 包括变异和交叉操作算子
     * @param pop
     * @return
     */
    public BasePopulation generate(BasePopulation pop){
        return cross.cross(pop, mutation.mutation(pop));
    }
    
    /**
     * 从父代种群和新生种群混合种群中选择好的个体进入下一代，形成新的父代种群
     * 利用非支配排序技术
     * @param parent
     * @param offspring
     * @return
     */
    public BasePopulation updateParentPopulation(BasePopulation parent,BasePopulation offspring){
        int N=parent.size();
        BaseIndividual[] newPop=new BaseIndividual[N];
        BasePopulation mergedPop=merge(parent, offspring);
        BaseIndividual[] merged=mergedPop.getPopulation();
        //利用非支配排序技术对混合种群分等级排序
        List<List<Integer>> ranked=CommonUtil.nondominatedSorting(mergedPop);
        
        //选取混合种群中前N个个体作为新一代种群
        //第一层级的个体集合（非支配解集，帕累托前沿）
        List<Integer> paretoFront=ranked.get(0);
        
        //第一层级的个体数量（种群中非支配解数量）可能大于N、等于N或者小于N
        if(paretoFront.size()==N){
            for(int i=0;i<N;i++){
                //个体在混合种群中的索引
                int index=paretoFront.get(i);
                newPop[i]=merged[index];
            }
        }else if(paretoFront.size()>N){
            
        }else{
            
        }
        
        return new BasePopulation(newPop);
    }
    
    /**
     * 合并两个种群
     * @param parent
     * @param offspring
     * @return
     */
    public BasePopulation merge(BasePopulation parent,BasePopulation offspring){
        BaseIndividual[] mergedIndividuals=new BaseIndividual[parent.size()+offspring.size()];
        BaseIndividual[] parentIndividuals=parent.getPopulation();
        BaseIndividual[] offspringIndividuls=offspring.getPopulation();
        
        for(int i=0;i<parentIndividuals.length;i++){
            mergedIndividuals[i]=parentIndividuals[i];
        }
        for(int i=0;i<offspringIndividuls.length;i++){
            mergedIndividuals[i+parentIndividuals.length]=offspringIndividuls[i];
        }
        
        return new BasePopulation(mergedIndividuals);
    }
    
    
}
