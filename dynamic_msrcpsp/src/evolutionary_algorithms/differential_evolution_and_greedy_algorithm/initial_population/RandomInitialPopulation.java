package evolutionary_algorithms.differential_evolution_and_greedy_algorithm.initial_population;

import java.util.List;
import java.util.Random;

import evolutionary_algorithms.CommonUtil;
import project.Project;
import project.Resource;
import project.Task;
import scheduling.BaseIndividual;
import scheduling.BasePopulation;
import scheduling.Schedule;
/**
 * 随机初始化种群类
 * @author dell
 *
 */
public class RandomInitialPopulation implements InitialPopulation{
    //种群大小
    private int popSize;
    
    public RandomInitialPopulation(int N){
        this.popSize=N;
    }
    
    @Override
    public BasePopulation generate(Project project){
        BaseIndividual[] pop=new BaseIndividual[popSize];
        Task[] tasks=project.getTasks();
        int numOfTask=tasks.length;
        Random rand=new Random();
        for(int i=0;i<popSize;i++){
            //生成染色体
            int[] chromosome=new int[numOfTask];
            for(int j=0;j<chromosome.length;j++){
                Task t=tasks[j];    //T1,T2,...TN顺序遍历，给任务分配资源
                List<Resource> capableResources=CommonUtil.getCapableResources(t, project);
                //生成一个随机数
                int index=rand.nextInt(capableResources.size());
                chromosome[j]=capableResources.get(index).getId();
            }
            
            Schedule schedule=new Schedule(chromosome,project);
            pop[i]=new BaseIndividual(schedule);
            
            //由于不同个体之间，资源和任务的部分属性不同，所以创建个体之后对相关属性进行重置，避免影响下一个个体
            schedule.reset();
        }
        
        return new BasePopulation(pop);
    }
    
    
    public int getPopSize() {
        return popSize;
    }

    public void setPopSize(int popSize) {
        this.popSize = popSize;
    }
    
    
}
