package runners;

import java.util.List;

import org.junit.Test;

import evolutionary_algorithms.CommonUtil;
import evolutionary_algorithms.differential_evolution_and_greedy_algorithm.DEGA;
import evolutionary_algorithms.differential_evolution_and_greedy_algorithm.crossover.Crossover;
import evolutionary_algorithms.differential_evolution_and_greedy_algorithm.initial_population.InitialPopulation;
import evolutionary_algorithms.differential_evolution_and_greedy_algorithm.initial_population.RandomInitialPopulation;
import evolutionary_algorithms.differential_evolution_and_greedy_algorithm.mutation.Mutation;
import io.DMSRCPSPIO;
import project.Project;
import scheduling.BaseIndividual;
import scheduling.BasePopulation;

public class DEGARunner {

    @Test
    public void runnerTest(){
        //1.创建案例对象
        DMSRCPSPIO io=new DMSRCPSPIO();
        Project project=io.readDefinition("instances/100_10_26_15.def");
                                                                                                                                                      
        //2.创建算法对象
        //2.1 创建种群初始初始化对象
        InitialPopulation intial=new RandomInitialPopulation(100);
        //2.2 创建变异算子对象
        Mutation mutation=new Mutation(0.1);
        //2.3 创建交叉算子对象
        Crossover cross=new Crossover(0.1);
        int maxGnerationCount=50;
        DEGA algorithm=new DEGA(maxGnerationCount,intial,mutation,cross);
        
        //3.算法求解步骤
        //3.1 初始化种群
        BasePopulation initialPop=algorithm.initialPopulation(project);
        int generationCount=1;
        BasePopulation parentPop=initialPop;
        
        //3.2 迭代求解
        while(!algorithm.isStop(generationCount)){
            //经过变异和交叉后，生成新的个体，组成新的种群
            BasePopulation temp=algorithm.generate(parentPop);
            
            //将新生成的种群与父代种群混合，并从中选择出较好的个体进入下一代
            parentPop=algorithm.updateParentPopulation(parentPop, temp);
            
            generationCount++;
        }
        
        //3.3迭代终止后，输出pareto 非支配解
        List<BaseIndividual> finalParetoFront=CommonUtil.nondominatedSorting(parentPop).get(0);
        System.out.println("makespan"+"\t"+"cost"+"\t"+"unwillingness");
        for(BaseIndividual indiv:finalParetoFront){
            System.out.println(indiv.getMakespan()+"\t"+indiv.getCost()+"\t"+indiv.getUnwillingness());
        }
    }
    
}
