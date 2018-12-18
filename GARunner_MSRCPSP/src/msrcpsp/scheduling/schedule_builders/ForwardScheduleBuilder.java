// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ForwardScheduleBuilder.java

package msrcpsp.scheduling.schedule_builders;

import msrcpsp.scheduling.*;

// Referenced classes of package msrcpsp.scheduling.schedule_builders:
//			ScheduleBuilder

public class ForwardScheduleBuilder extends ScheduleBuilder
{

	private boolean hasSuccessors[];

	public ForwardScheduleBuilder(boolean hasSuccesors[])
	{
		setHasSuccessors(hasSuccesors);
	}

	public Schedule buildTimestamps(Schedule schedule)
	{
		Resource resources[] = schedule.getResources();
		Task tasks[] = resources;
		int j = tasks.length;
		for (int k = 0; k < j; k++)
		{
			Resource r = tasks[k];
			r.setFinish(0);
		}

		tasks = schedule.getTasks();
		for (int i = 0; i < tasks.length; i++)
			if (hasSuccessors[i])
			{
				Resource res = resources[tasks[i].getResourceId() - 1];
				int start = Math.max(schedule.getEarliestTime(tasks[i]), res.getFinish());
				tasks[i].setStart(start);
				res.setFinish(start + tasks[i].getDuration());
			}

		for (int i = 0; i < tasks.length; i++)
			if (!hasSuccessors[i])
			{
				Resource res = resources[tasks[i].getResourceId() - 1];
				int start = Math.max(schedule.getEarliestTime(tasks[i]), res.getFinish());
				tasks[i].setStart(start);
				res.setFinish(start + tasks[i].getDuration());
			}

		return schedule;
	}

	public boolean[] getHasSuccessors()
	{
		return hasSuccessors;
	}

	public void setHasSuccessors(boolean hasSuccessors[])
	{
		this.hasSuccessors = hasSuccessors;
	}
}
