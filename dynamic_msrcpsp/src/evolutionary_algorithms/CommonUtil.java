package evolutionary_algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import project.Project;
import project.Resource;
import project.Skill;
import project.Task;
import scheduling.BaseIndividual;
import scheduling.BasePopulation;

public class CommonUtil {
    
    /**
     * 获取项目中没有紧前任务的任务，即初始可执行的任务
     * @param project
     * @return
     */
    public static List<Task> getExecutableTasks(Project project){
        List<Task> initExecutableTasks=new ArrayList<>();
        Task[] tasks=project.getTasks();
        for(int i=0;i<tasks.length;i++){
            if(tasks[i].getPredecessors()==null){
                initExecutableTasks.add(tasks[i]);
            }
        }
        
        return initExecutableTasks;
    }
    
    /**
     * 获取项目中具有紧前任务的任务
     * @param project
     * @return
     */
    public static Map<Task,List<Task>> getInexecutableTasks(Project project){
        Map<Task,List<Task>> inexecutableTasks=new ConcurrentHashMap<Task,List<Task>>();
        Task[] tasks=project.getTasks();
        for(int i=0;i<tasks.length;i++){
            if(tasks[i].getPredecessors()!=null){
                int[] preIDs=tasks[i].getPredecessors();
                //任务的紧前任务集
                List<Task> predecessors=new ArrayList<Task>();
                for(int j=0;j<preIDs.length;j++){
                    predecessors.add(tasks[preIDs[j]-1]);
                }
                inexecutableTasks.put(tasks[i], predecessors);
            }
        }
        
        return inexecutableTasks;
    }
    
    /**
     * 获取可执行任务的资源集
     * @param t
     * @return
     */
    public static List<Resource> getCapableResources(Task t,Project project){
        List<Resource> list=new ArrayList<>();
        Skill reqSkill=t.getReqSkill();
        Resource[] resources=project.getResources();
        for(int i=0;i<resources.length;i++){
            Resource r=resources[i];
            if(r.hasSkill(reqSkill))
                list.add(r);
        }
        return list;
    }
    
    
    /**
     * 修复基因型染色体
     * 染色体中的每一个元素修订为任务可执行资源ID值中与该元素距离最近的资源ID
     * @param genotype
     * @param project
     * @return
     */
    public static int[] repair(double[] genotype,Project project){
        int[] pheotype=new int[genotype.length];
        Task[] tasks=project.getTasks();
        List<Resource> capableResources=null;
        int[] capResIDs=null;
        for(int i=0;i<genotype.length;i++){
            Task t=tasks[i];
            capableResources=getCapableResources(t, project);
            //对可执行资源按照资源ID进行排序，由于此处可执行资源集获取时已经保证了是有序的
            //利用二分查找可执行资源ID中与genotype[i]相近的值
            capResIDs=new int[capableResources.size()];
            for(int j=0;j<capResIDs.length;j++){
                capResIDs[j]=capableResources.get(j).getId();
            }
            
            int repairedResID=binarySearch(capResIDs, genotype[i]);
            pheotype[i]=repairedResID;
        }
        
        return pheotype;
    }
    
    public static int binarySearch(int[] array,double target){
        
        int left=0,right=0;
        for(right=array.length-1;left!=right;){
            int midIndex=(left+right)/2;
            int mid=right-left;
            int midValue=array[midIndex];
            if(target==(double)midValue){
                return midValue;
            }
            if(target>(double)midValue){
                left=midIndex;
            }else{
                right=midIndex;
            }
            if(mid<=1)
                break;
        }
        int rightNum=array[right];
        int leftNum=array[left];
        int result=Math.abs(((double)rightNum-leftNum)/2)>Math.abs(rightNum-target)?rightNum:leftNum;
        
        return result;
    }
    
    /**
     * 非支配排序
     * 根据个体之间的支配关系将种群划分成多个等级的个体集合
     * 第一层级个体集合就是种群的非支配解
     */
    public static List<List<BaseIndividual>> nondominatedSorting(BasePopulation pop){
        List<List<BaseIndividual>> rankedPop=new ArrayList<>();
        BaseIndividual[] members=pop.getPopulation();
        List<List<Integer>> temp=nonDominatedSorting(pop);
        
        List<BaseIndividual> paretoFront=null;
        List<Integer> tempPF=null;
        for(int i=0;i<temp.size();i++){
            paretoFront=new ArrayList<>();
            tempPF=temp.get(i);
            for(int j=0;j<tempPF.size();j++){
                int index=tempPF.get(j);
                paretoFront.add(members[index]);
            }
            rankedPop.add(paretoFront);
        }
        return rankedPop;
    }
    
    /**
     * 非支配排序
     * 根据个体之间的支配关系将种群划分成多个等级的个体集合
     * 每个分层记录的是个体在种群中的位置索引
     * 第一层级个体集合就是种群的非支配解
     * @param pop
     * @return
     */
    public static List<List<Integer>> nonDominatedSorting(BasePopulation pop) {
        // 利用个体位于种群中的索引值进行排序操作
        List<List<Integer>> ranked = new ArrayList<>();
        
        BaseIndividual[] members = pop.getPopulation();
        // 记录支配指定个体的个体数量
        int[] np = new int[members.length];
        // 被指定个体支配的个体集
        List<List<Integer>> spList = new ArrayList<>();
        for (int i = 0; i < members.length; i++) {
            List<Integer> sp = new ArrayList<Integer>();
            for (int j = 0; j < members.length; j++) {
                if (i == j) {
                    continue;
                }
                int flag =members[i].dominate(members[j]);
                // 个体i支配j
                if (flag>0) {
                    sp.add(j);
                }
                // 个体i被j所支配
                if (flag<0) {
                    np[i]++;
                }
            }
            spList.add(sp);
        }
        // 存储已排好序个体的索引值
        List<Integer> hasRanked = new ArrayList<>();
        while (true) {
            List<Integer> paretoFront = new ArrayList<>();
            for (int i = 0; i < members.length; i++) {
                if (np[i] == 0) {
                    if (!hasRanked.contains(i)) {
                        hasRanked.add(i);
                        paretoFront.add(i);
                    }
                }
            }
            for (int i = 0; i < paretoFront.size(); i++) {
                int index = paretoFront.get(i);
                //index个体所支配的个体集
                List<Integer> list=spList.get(index);
                for (int j = 0; j < list.size(); j++) {
                    //被支配个体的索引值
                    int temp=list.get(j);
                    //支配temp个体的数量减一
                    np[temp]--;
                }
            }
            ranked.add(paretoFront);
            
            int num = 0;
            for (int i = 0; i < ranked.size(); i++) {
                num += ranked.get(i).size();
            }
            if (num == members.length) {
                break;
            }
        }
        
        return ranked;
    }
    
    /**
     * 对同一层级的个体根据个体的拥挤度进行降序排序
     * @param front
     */
    public static void sortByCrowdingDistance(List<BaseIndividual> front){
        
        //计算该层级个体的拥挤度
        computeCrowdingDistance(front);
        //根据拥挤度大小排序
        Collections.sort(front, new Comparator<BaseIndividual>(){
            @Override
            public int compare(BaseIndividual o1, BaseIndividual o2) {
                return Double.compare(o1.getCrowdingDistance(), o2.getCrowdingDistance());
            }
        });
    }
    
    /**
     * 计算同一层级个体的拥挤度
     * @param front
     * @param pop
     * @return
     */
    public static void computeCrowdingDistance(List<BaseIndividual> front){
        
        //1.非支配解数量
        int L=front.size();
        //2.初始化个体的拥挤度
        double cd=0.0;
        for(int i=0;i<L;i++){
            front.get(i).setCrowdingDistance(cd);
        }
        //3.对于每一个目标
        int numOfObject=BaseIndividual.NUMBER_OF_OBJECT;
        for(int m=0;m<numOfObject;m++){
            //根据目标m对非支配解集中个体进行排序
            sort(front,m);
            //第一个和最后一个个体的拥挤度设置为无穷大
            front.get(0).setCrowdingDistance(Double.POSITIVE_INFINITY);
            front.get(L-1).setCrowdingDistance(Double.POSITIVE_INFINITY);
            //个体的优化目标
            double[] objs1=null;
            double[] objs2=null;
            for(int i=1;i<L-1;i++){
                cd=front.get(i).getCrowdingDistance();
                objs1=front.get(i+1).getObjs();
                objs2=front.get(i-1).getObjs();
                cd+=(objs1[m]-objs2[m])/(front.get(L-1).getObjs()[m]-front.get(0).getObjs()[m]); 
                front.get(i).setCrowdingDistance(cd);
            }
        }
    }
    
    /**根据个体目标m对非支配解集个体进行排序
     * m=0时，表示根据项目工期目标进行排序
     * m=1时，表示根据项目总成本进行排序
     * m=2时，表示根据员工对所分配任务不乐意程度进行排序
     */
    public static void sort(List<BaseIndividual> list,int m){
        //根据项目完工工期目标大小进行升序排序
        if(m==0){
            Collections.sort(list, new Comparator<BaseIndividual>(){

                @Override
                public int compare(BaseIndividual o1, BaseIndividual o2) {
                    return o1.getMakespan()-o2.getMakespan();
                }
            });
        }
        //根据项目总成本大小排序
        if(m==1){
            Collections.sort(list, new Comparator<BaseIndividual>(){

                @Override
                public int compare(BaseIndividual o1, BaseIndividual o2) {
                    return Double.compare(o1.getCost(), o2.getCost());
                }
            });
        }
        //根据员工对所分配任务不愿意程度排序
        if(m==2){
            Collections.sort(list, new Comparator<BaseIndividual>(){

                @Override
                public int compare(BaseIndividual o1, BaseIndividual o2) {
                    return Double.compare(o1.getUnwillingness(), o2.getUnwillingness());
                }
            });
        }
    }
    
}
