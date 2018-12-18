// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Task.java

package msrcpsp.scheduling;

import java.util.Arrays;

// Referenced classes of package msrcpsp.scheduling:
//			Skill

public class Task
	implements Comparable
{

	private int id;
	private Skill requiredSkill;
	private int duration;
	private int start;
	private int predecessors[];
	private int resourceId;

	public Task(int id, Skill requiredSkill, int duration, int start, int predecessors[], int resourceId)
	{
		this.id = id;
		this.requiredSkill = requiredSkill;
		this.duration = duration;
		this.start = start;
		this.predecessors = predecessors;
		this.resourceId = resourceId;
	}

	public Task(int id, Skill skill, int duration, int predecessors[])
	{
		this(id, skill, duration, -1, predecessors, -1);
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public Skill getRequiredSkills()
	{
		return requiredSkill;
	}

	public void setRequiredSkills(Skill requiredSkill)
	{
		this.requiredSkill = requiredSkill;
	}

	public int getDuration()
	{
		return duration;
	}

	public void setDuration(int duration)
	{
		this.duration = duration;
	}

	public int getStart()
	{
		return start;
	}

	public void setStart(int start)
	{
		this.start = start;
	}

	public int[] getPredecessors()
	{
		return predecessors;
	}

	public void setPredecessors(int predecessors[])
	{
		this.predecessors = predecessors;
	}

	public int getResourceId()
	{
		return resourceId;
	}

	public void setResourceId(int resourceId)
	{
		this.resourceId = resourceId;
	}

	public String toString()
	{
		String p = "";
		int ai[] = predecessors;
		int j = ai.length;
		for (int k = 0; k < j; k++)
		{
			int i = ai[k];
			p = (new StringBuilder()).append(p).append(i).append(" ").toString();
		}

		return (new StringBuilder()).append(id).append(", duration: ").append(duration).append(", start: ").append(start).append(", required skills: ").append(requiredSkill).append(", predecessors: ").append(p).toString();
	}

	public boolean equals(Object t)
	{
		if (!(t instanceof Task))
		{
			return false;
		} else
		{
			Task task = (Task)t;
			return duration == task.duration && id == task.id && Arrays.equals(predecessors, task.predecessors) && requiredSkill == task.requiredSkill;
		}
	}

	public int compareTo(Object o)
	{
		if (!(o instanceof Task))
			throw new IllegalArgumentException("Parameter is not a Task");
		else
			return Integer.compare(start, ((Task)o).start);
	}
}
