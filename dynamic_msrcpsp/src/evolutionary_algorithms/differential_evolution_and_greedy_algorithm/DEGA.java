package evolutionary_algorithms.differential_evolution_and_greedy_algorithm;

import java.util.ArrayList;
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
        //利用非支配排序技术对混合种群分等级排序
        List<List<BaseIndividual>> rankedPop=CommonUtil.nondominatedSorting(mergedPop);
        
        //选取混合种群中前N个个体作为新一代种群
        //第一层级的个体集合（非支配解集，帕累托前沿）
        List<BaseIndividual> paretoFront=rankedPop.get(0);
        
        //第一层级的个体数量（种群中非支配解数量）可能大于N、等于N或者小于N
        if(paretoFront.size()==N){
            for(int i=0;i<N;i++){
                newPop[i]=paretoFront.get(i);
            }
        }else if(paretoFront.size()>N){
            //对该层级的个体进行拥挤度排序，然后选择前N个体
            CommonUtil.sortByCrowdingDistance(paretoFront);
            for(int i=0;i<N;i++){
                newPop[i]=paretoFront.get(i);
            }
        }else{
            //如果第一层级的个体数量小于N，则需要从第二层级选取余下的个体，
            //如果还是不够，继续加入其它层级的个体,直到个体数量为N
            List<BaseIndividual> list = new ArrayList<>();
            for (int i = 0; i < rankedPop.size(); i++) {
                for (int j = 0; j < rankedPop.get(i).size(); j++) {
                    list.add(rankedPop.get(i).get(j));
                }
                int sum = list.size();
                if (sum < mergedPop.size()/2) {
                    continue;
                } else if (sum == mergedPop.size()/2) {
                    for (int m = 0; m < mergedPop.size()/2; m++) {
                        newPop[m] = list.get(m);
                    }
                    break;
                } 
                else {
                    List<BaseIndividual> front = rankedPop.get(i);
                    if (front.size() > 1) {
                        // 根据拥挤度大小降序排序
                       CommonUtil.sortByCrowdingDistance(front);
                    }

                    for (int k = 0; k < (sum - front.size()); k++) {
                        newPop[k] = list.get(k);
                    }
                    for (int k = 0; k < mergedPop.size()/2 - (sum - front.size()); k++) {
                        newPop[k + sum - front.size()] = front.get(k);
                    }
                    break;
                }
            }
            
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
    
    /**
     * 算法迭代终止条件
     * @param generationCount
     * @return
     */
    public boolean isStop(int generationCount){
        return generationCount>maxGenerationCount;
    }
}
