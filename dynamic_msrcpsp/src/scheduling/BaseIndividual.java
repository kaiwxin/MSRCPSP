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
    protected Schedule schedule;
    //项目工期
    protected int makespan;
    //项目总成本
    protected double cost;
    //资源对分配任务的不满意度
    protected double unwillingness;

    public BaseIndividual(Schedule schedule) {
        this.schedule = schedule;

        //计算最终调度方案的各个目标值
        this.makespan = calMakespan();
        this.cost=calCost();
        this.unwillingness=calUnwillingness();
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

}
