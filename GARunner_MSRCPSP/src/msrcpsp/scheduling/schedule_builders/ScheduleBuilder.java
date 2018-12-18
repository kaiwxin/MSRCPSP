// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ScheduleBuilder.java

package msrcpsp.scheduling.schedule_builders;

import java.util.Arrays;
import java.util.List;
import msrcpsp.scheduling.*;

public class ScheduleBuilder
{

	public ScheduleBuilder()
	{
	}

	public Schedule build(Schedule schedule)
	{
		Task atask[] = schedule.getTasks();
		int i = atask.length;
		for (int j = 0; j < i; j++)
		{
			Task task = atask[j];
			build(schedule, task);
		}

		return schedule;
	}

	private Schedule build(Schedule schedule, Task task)
	{
		List capableResources = schedule.getCapableResources(task);
		Resource candidateResource = schedule.findCheapestResource(capableResources);
		task.setResourceId(candidateResource.getId());
		int earliestTime = schedule.getEarliestTime(task);
		task.setStart(Math.max(candidateResource.getFinish(), earliestTime));
		candidateResource.setFinish(task.getStart() + task.getDuration());
		return schedule;
	}

	public Schedule buildAssignments(Schedule schedule)
	{
		Arrays.sort(schedule.getTasks());
		Task atask[] = schedule.getTasks();
		int i = atask.length;
		for (int j = 0; j < i; j++)
		{
			Task task = atask[j];
			buildAssignments(schedule, task);
		}

		return schedule;
	}

	public Resource buildAssignments(Schedule schedule, Task task)
	{
		task.setStart(schedule.getEarliestTime(task));
		List capableResources = schedule.getCapableResources(task, task.getStart());
		Resource candidateResource = schedule.findCheapestResource(capableResources);
		if (null != candidateResource)
		{
			task.setResourceId(candidateResource.getId());
			candidateResource.setFinish(task.getStart() + task.getDuration());
		} else
		{
			capableResources = schedule.getCapableResources(task);
			candidateResource = schedule.findFirstFreeResource(capableResources);
			task.setResourceId(candidateResource.getId());
			task.setStart(candidateResource.getFinish());
			candidateResource.setFinish(task.getStart() + task.getDuration());
		}
		return candidateResource;
	}

	public Schedule buildTimestamps(Schedule schedule)
	{
		throw new IllegalStateException("Choose whether to use forward orbackward builder (ForwardScheduleBuilder / BackwardScheduleBuilder");
	}
}
