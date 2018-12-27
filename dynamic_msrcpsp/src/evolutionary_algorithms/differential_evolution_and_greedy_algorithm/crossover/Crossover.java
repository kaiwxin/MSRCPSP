package evolutionary_algorithms.differential_evolution_and_greedy_algorithm.crossover;

import java.util.List;

import project.Project;
import scheduling.BaseIndividual;
import scheduling.BasePopulation;
import scheduling.Schedule;

public class Crossover {
    //交叉概率
    private double CR;

    public Crossover(double value){
        this.CR=value;
    }

    public BasePopulation cross(BasePopulation parentPop,List<int[]> mutantList){
        BaseIndividual[] newPop=new BaseIndividual[parentPop.size()];
        BaseIndividual[] oldPop=parentPop.getPopulation();
        for(int i=0;i<parentPop.size();i++){
            BaseIndividual parent=oldPop[i];
            //交叉操作
            int[] parentChromosome=parent.getSchedule().getChromosome();
            int[] mutant=mutantList.get(i);
            int[] newChromosome=new int[parentChromosome.length];
            for(int j=0;j<parentChromosome.length;j++){
                double rand=Math.random();
                if(rand<CR){
                    newChromosome[j]=mutant[j];
                }else{
                    newChromosome[j]=parentChromosome[j];
                }
            }

            //创建新的个体
            Project project=parent.getSchedule().getProject();
            Schedule newSchedule=new Schedule(newChromosome,project);
            newPop[i]=new BaseIndividual(newSchedule);

        }

        return new BasePopulation(newPop);
    }
}