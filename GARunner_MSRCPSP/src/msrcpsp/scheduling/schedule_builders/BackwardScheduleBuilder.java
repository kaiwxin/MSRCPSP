// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BackwardScheduleBuilder.java

package msrcpsp.scheduling.schedule_builders;

import msrcpsp.scheduling.*;

// Referenced classes of package msrcpsp.scheduling.schedule_builders:
//			ScheduleBuilder

public class BackwardScheduleBuilder extends ScheduleBuilder
{

	public BackwardScheduleBuilder()
	{
	}

	public Schedule buildTimestamps(Schedule schedule)
	{
		Task atask[] = schedule.getTasks();
		int i = atask.length;
		for (int j = 0; j < i; j++)
		{
			Task task = atask[j];
			buildTimestamps(schedule, task);
		}

		return schedule;
	}

	public Task buildTimestamps(Schedule schedule, Task task)
	{
		buildTimestampsForPredecessors(schedule, task);
		if (-1 == task.getStart())
			setEarliestPossibleTime(schedule, task);
		return task;
	}

	public Task setEarliestPossibleTime(Schedule schedule, Task task)
	{
		int lastPredecessorDone = schedule.getEarliestTime(task);
		Resource resource = schedule.getResource(task.getResourceId());
		int resourceAvailable = resource.getFinish();
		int timestamp = lastPredecessorDone <= resourceAvailable ? resourceAvailable : lastPredecessorDone;
		task.setStart(timestamp);
		resource.setFinish(timestamp + task.getDuration());
		return task;
	}

	private Task buildTimestampsForPredecessors(Schedule schedule, Task task)
	{
		int ai[] = task.getPredecessors();
		int i = ai.length;
		for (int j = 0; j < i; j++)
		{
			int predecessorsId = ai[j];
			Task t = schedule.getTask(predecessorsId);
			buildTimestamps(schedule, t);
		}

		return task;
	}
}
