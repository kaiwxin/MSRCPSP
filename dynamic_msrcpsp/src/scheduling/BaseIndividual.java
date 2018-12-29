package scheduling;

import java.util.List;

import project.Resource;
import project.Skill;
import project.Task;

/**
 * 所有算法个体的父类
 * 描述个体的基本信息：调度方案、目标值
 * @author XiongKai
 *
 */
public class BaseIndividual implements Comparable<BaseIndividual> {
    //调度方案
    protected Schedule schedule;
    //项目工期
    protected int makespan;
    //项目总成本
    protected double cost;
    //资源对分配任务的不满意度
    protected double unwillingness;
    
    public static final int NUMBER_OF_OBJECT=3;
    //优化目标数组
    protected double[] objs=new double[NUMBER_OF_OBJECT];

    //拥挤度(在非支配解集中个体拥挤度)
    protected double crowdingDistance;

    public BaseIndividual(Schedule schedule) {
        this.schedule = schedule;

        //计算最终调度方案的各个目标值
        this.makespan = calMakespan();
        this.cost=calCost();
        this.unwillingness=calUnwillingness();
        objs[0]=makespan;
        objs[1]=cost;
        objs[2]=unwillingness;
    }

    public int calMakespan() {
        int result = 0;
        Resource[] resources = schedule.getProject().getResources();
        for (int i = 0; i < resources.length; i++) {
            Resource r = resources[i];
            if (r.getFinishTime() > result) {
                result = r.getFinishTime();
            }
        }
        return result;
    }

    public double calCost() {
        double result = 0.0;
        Resource[] resources=schedule.getProject().getResources();
        for(int i=0;i<resources.length;i++){
            Resource r=resources[i];
            List<Task> assignedTasks=r.getAssignedTasks();
            for(Task t:assignedTasks){
                result+=r.getSalary()*t.getSpecificDuration();
            }
        }
        return result;
    }
    /**
     * 为了使目标最小化，计算员工对所分配任务的不满意度来表示第三个优化目标
     * @return
     */
    public double calUnwillingness(){
        double sum=0.0;
        Resource[] resources=schedule.getProject().getResources();
        for(int i=0;i<resources.length;i++){
            Resource r=resources[i];
            List<Task> assignedTasks=r.getAssignedTasks();
            for(Task t:assignedTasks){
                Skill reqSkill=t.getReqSkill();
                int skillIndex=schedule.getSkillIndex(r, reqSkill);
                double ED=r.getPreferToSkills()[skillIndex];
                sum+=1-ED;
            }
        }
        return sum/resources.length;
    }
    
    /**
     * 两个个体之间支配关系
     * 返回值为0时，两者不存在支配关系
     * 返回值为1时，前者支配后者
     * 返回值为-1时，后者支配前者
     * @param indiv
     * @return
     */
    public int dominate(BaseIndividual indiv){
        int flag=0;
        int n=0,k=0;
        double[] objs2=indiv.getObjs();
        for(int i=0;i<objs.length;i++){
            if(objs[i]==objs2[i]){
                continue;
            }
            else if(objs[i]<objs2[i]){
                n++;
            }
            else{
                k++;
            }
        }
        if(k==0 && n>0){
            flag=1;
        }
        if(n==0 && k>0){
            flag=-1;
        }
        return flag;
    }
    
    @Override
    public int compareTo(BaseIndividual o) {
        if(this.makespan==o.makespan){
            if(this.cost==o.cost)
                return Double.compare(this.unwillingness, o.unwillingness);
            else
                return Double.compare(this.cost, o.cost);
        }      
        else 
            return Integer.compare(this.makespan, o.makespan) ;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public int getMakespan() {
        return makespan;
    }

    public void setMakespan(int makespan) {
        this.makespan = makespan;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getUnwillingness() {
        return unwillingness;
    }

    public void setUnwillingness(double unwillingness) {
        this.unwillingness = unwillingness;
    }

    /**
     * @return the objs
     */
    public double[] getObjs() {
        return objs;
    }

    /**
     * @param objs the objs to set
     */
    public void setObjs(double[] objs) {
        this.objs = objs;
    }

    /**
     * @return the crowdingDistance
     */
    public double getCrowdingDistance() {
        return crowdingDistance;
    }

    /**
     * @param crowdingDistance the crowdingDistance to set
     */
    public void setCrowdingDistance(double crowdingDistance) {
        this.crowdingDistance = crowdingDistance;
    }

}
