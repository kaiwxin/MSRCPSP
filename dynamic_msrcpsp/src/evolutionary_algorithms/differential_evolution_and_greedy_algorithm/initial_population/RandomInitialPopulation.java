package evolutionary_algorithms.differential_evolution_and_greedy_algorithm.initial_population;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import evolutionary_algorithms.CommonUtil;
import project.Project;
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
        for(int i=0;i<popSize;i++){
            //随机生成任务序列
            int[] taskList=generateTaskList(project);
            Schedule schedule=new Schedule(taskList,project);
            //调用计划生成方案，并基于随机规则，生成资源分配链表
            schedule.scheduleGenerateScheme(taskList, project, "RANDOM");
            pop[i]=new BaseIndividual(schedule);
            
            //由于不同个体之间，资源和任务的部分属性不同，所以创建个体之后对相关属性进行重置，避免影响下一个个体
            schedule.reset();
        }
        
        return new BasePopulation(pop);
    }
    
    public int[] generateTaskList(Project project){
        int[] taskList=new int[project.getNumberOfTask()];
        //初始可执行任务集
        List<Task> executableTasks=CommonUtil.getExecutableTasks(project);
        
        //初始不可执行任务集
        Map<Task,List<Task>> inexecutableTasks=CommonUtil.getInexecutableTasks(project);
        
        for(int i=0;i<taskList.length;i++){
            int rand=(int)(Math.random()*executableTasks.size());
            Task currentTask=executableTasks.get(rand);
            taskList[i]=currentTask.getId();
            
            // 将已经被排序的任务从可执行任务集中剔除
            executableTasks.remove(currentTask);
            
            /*
             * 遍历当前具有紧前任务的不可执行任务集，判断各任务的紧前任务集中是否包含当前被选择的任务，
             * 如果选择的任务是某个不可执行任务的紧前任务，则需要更新不可执行任务的紧前任务集，即移除那个被选择的任务，
             * 如果不可执行任务的紧前任务为空了，就需要将该不可执行任务添加到可行任务集合中。
             */
            if (!inexecutableTasks.isEmpty()) {
                // 使用迭代器遍历当前不可执行任务集，以免引起并发修改异常
                Set<Task> keys = inexecutableTasks.keySet();
                Iterator<Task> iterator = keys.iterator();
                while (iterator.hasNext()) {
                    Task keyTask = iterator.next();
                    List<Task> predecessors = inexecutableTasks.get(keyTask);
                    Iterator<Task> iterator2 = predecessors.iterator();
                    while (iterator2.hasNext()) {
                        Task predecessor = iterator2.next();
                        if (predecessor.getId() == currentTask.getId()) {
                            iterator2.remove();
                            break;
                        }
                    }
                    if (predecessors.isEmpty()) {
                        executableTasks.add(keyTask);
                        iterator.remove();
                        inexecutableTasks.remove(keyTask);
                    }
                }
            }
        }
        
        return taskList;
    }
    
    public int getPopSize() {
        return popSize;
    }

    public void setPopSize(int popSize) {
        this.popSize = popSize;
    }
    
    
}
