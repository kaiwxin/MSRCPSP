// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BaseEvaluator.java

package msrcpsp.evaluation;

import msrcpsp.scheduling.*;

// Referenced classes of package msrcpsp.evaluation:
//			EvaluatorType

public abstract class BaseEvaluator
{

	private Schedule schedule;

	public BaseEvaluator(Schedule schedule)
	{
		this.schedule = schedule;
	}

	public abstract double evaluate();

	public abstract BaseEvaluator getCopy(Schedule schedule1);

	public abstract EvaluatorType getType();

	public int getDuration()
	{
		int result = 0;
		Resource resources[] = schedule.getResources();
		Resource aresource[] = resources;
		int i = aresource.length;
		for (int j = 0; j < i; j++)
		{
			Resource r = aresource[j];
			if (r.getFinish() > result)
				result = r.getFinish();
		}

		return result;
	}

	public double getCost()
	{
		double cost = 0.0D;
		Task tasks[] = schedule.getTasks();
		Task atask[] = tasks;
		int i = atask.length;
		for (int j = 0; j < i; j++)
		{
			Task t = atask[j];
			if (t.getResourceId() != -1)
				cost += schedule.getResource(t.getResourceId()).getSalary() * (double)t.getDuration();
		}

		return cost;
	}

	public int getMaxDuration()
	{
		int duration = 0;
		Task atask[] = schedule.getTasks();
		int i = atask.length;
		for (int j = 0; j < i; j++)
		{
			Task t = atask[j];
			duration += t.getDuration();
		}

		return duration;
	}

	public int getMaxCost()
	{
		Resource resources[] = schedule.getResources();
		Resource expRes = resources[0];
		Resource aresource[] = resources;
		int i = aresource.length;
		for (int j = 0; j < i; j++)
		{
			Resource r = aresource[j];
			if (r.getSalary() > expRes.getSalary())
				expRes = r;
		}

		int maxCost = 0;
		Task atask[] = schedule.getTasks();
		int k = atask.length;
		for (int l = 0; l < k; l++)
		{
			Task t = atask[l];
			maxCost = (int)((double)maxCost + (double)t.getDuration() * expRes.getSalary());
		}

		return maxCost;
	}

	public double getDurationNormalized()
	{
		return (double)getDuration() / (double)getMaxDuration();
	}

	public double getCostNormalized()
	{
		return getCost() / (double)getMaxCost();
	}
}
