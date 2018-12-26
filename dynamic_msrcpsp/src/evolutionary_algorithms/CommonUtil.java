package evolutionary_algorithms;

import java.util.ArrayList;
import java.util.List;

import project.Project;
import project.Resource;
import project.Skill;
import project.Task;

public class CommonUtil {
    
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
}
