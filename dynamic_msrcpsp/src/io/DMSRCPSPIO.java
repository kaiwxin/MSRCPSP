package io;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

import project.Project;
import project.Resource;
import project.Skill;
import project.Task;

public class DMSRCPSPIO {
    
    public Project readDefinition(String fileName){
        Project project=null;
        try(
                BufferedReader reader=new BufferedReader(new FileReader(fileName))
                )
            {
                String line=reader.readLine(); 
                
                //案例任务数量
                int numTasks=readNumber(reader,line,"Tasks");
                //案例资源数量
                int numResources=readNumber(reader,line,"Resources");
                //案例技能种类
                int numberOfSkill=readNumber(reader,line,"Number of skill types");
                //定义技能的难度系数：每种技能的难度系数从[0,1]之间均匀采样
                //定义一个数组记录Q0,Q1,..等技能的难度系数
                double[] difficultFactors=new double[numberOfSkill];
                for(int s=0;s<numberOfSkill;s++){
                    difficultFactors[s]=Math.random();
                }
                
                //读取资源信息
                skipTo(reader,line,"ResourceID");
                Resource[] resources=readResorces(reader,numResources,difficultFactors);
                
                //读取任务信息
                skipTo(reader,line,"TaskID");
                Task[] tasks=readTasks(reader,numTasks,difficultFactors);
                
                project=new Project(tasks,resources,numberOfSkill);
            }catch(FileNotFoundException e){
                e.printStackTrace();
            }catch(IOException e2){
                e2.printStackTrace();
            }
        return project;
    }
    
    private Task[] readTasks(BufferedReader reader, int numTasks,double[] difficultFactors) throws IOException {
        Task[] tasks=new Task[numTasks];
        for(int i=0;i<numTasks;i++){
            String line=reader.readLine();
            String[] parts=line.trim().split(" {5}");   //以5个空格作为分割标记
            //获取任务id
            int id=Integer.parseInt(parts[0]);
            
            //获取任务要求的技能
            Skill[] skills=readSkills(parts[2],difficultFactors);
            Skill requiredSkill=skills[0];  //每个任务只需要一种技能
            
            //获取任务的多个完工工期
            //每种技能只有三种水平(1/2/3)，即入门级、普通级以及精通级。那么对应的任务完工工期最多有三个
            int[] durations=new int[3];
            for(int j=0;j<durations.length;j++){
                //默认值为-1，表示技能水平低于要求水平时，该技能对应的完工工期，说明该技能不可执行该任务
                durations[j]=-1;       
            }
            durations=readDurations(durations, requiredSkill, parts[1]);
            
            //获取任务的紧前任务
            if(parts.length>3){
                int[] predecessors=readPredecessors(parts[3]);
                //创建任务对象
                tasks[i]=new Task(id,requiredSkill,durations,predecessors);
            }else{
                tasks[i]=new Task(id,requiredSkill,durations);
            }
        }
        return tasks;
    }
    
    private int[] readDurations(int[] durations,Skill requiredSkill, String s){
        String[] _durations=s.split(" "); //以一个空格符分割
        int l=(int)requiredSkill.getLevel()-1;
        for(int j=0;j<_durations.length;j++){
            durations[l++]=Integer.parseInt(_durations[j]);
        }
        return durations;
    }
    
    private int[] readPredecessors(String s){
        String[] _predes=s.trim().split(" ");   //以一个空格符分割
        int[] predecessors=new int[_predes.length];
        for(int i=0;i<_predes.length;i++){
            predecessors[i]=Integer.parseInt(_predes[i]);
        }
        return predecessors;
    }
    
    private Resource[] readResorces(BufferedReader reader, int numResources,double[] difficultFactors) throws IOException {
        Resource[] resources=new Resource[numResources];
        for(int i=0;i<numResources;i++){
            String line=reader.readLine();
            String[] parts=line.trim().split(" {5}");   //以5个空格作为分割标记
            int id=Integer.parseInt(parts[0]);
            double salary=Double.parseDouble(parts[1]);
            Skill[] skills=readSkills(parts[2],difficultFactors);
            //资源对所掌握技能的从事意愿程度,从[0,1]之间均匀采样
            double[] preferToSkills=new double[skills.length];
            for(int s=0;s<preferToSkills.length;s++){
                preferToSkills[s]=Math.random();
            }
            //资源的学习能力，其值符合N(0.5,0.15)的正态分布
            Random random=new Random();
            double learingAbility=Math.sqrt(0.15)*random.nextGaussian()+0.5;
            
            resources[i]=new Resource(id,skills,preferToSkills,learingAbility,salary);
        }
        return resources;
    }
    
    private Skill[] readSkills(String s,double[] difficultFactors){
        String[] skillsStr=s.trim().split(" "); //以1个空格符作为分割
        Skill[] skills=new Skill[skillsStr.length];
        for(int j=0;j<skills.length;j++){
            String skillType=skillsStr[j].split(":")[0];
            //获取技能ID,如Q1中的1标记
            int id=Integer.parseInt(skillType.split("Q")[1]);
            //技能难度系数
            double sd=difficultFactors[id];
            //案例中技能水平用了0,1,2表示三种水平，程序中使用1,2,3分别表示三种技能水平
            int skillLevel=Integer.parseInt(skillsStr[j].split(":")[1])+1;
            skills[j]=new Skill(skillType,skillLevel,sd);
        }
        return skills;
    }
    
    
    private int readNumber(BufferedReader reader,String line,String toRead)throws IOException{
        line=skipTo(reader,line,toRead);
        if(line==null){
            return -1;
        }
        else{
            return Integer.parseInt(line.substring(line.lastIndexOf(":")+1));
        }
    }
    
    private String skipTo(BufferedReader reader, String line, String desired) throws IOException {
        for(;line!=null && !line.startsWith(desired);line=reader.readLine());
        return line;
    }
}
