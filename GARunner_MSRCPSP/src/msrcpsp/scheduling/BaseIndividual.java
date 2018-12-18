// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BaseIndividual.java

package msrcpsp.scheduling;

import java.util.Iterator;
import java.util.List;
import msrcpsp.evaluation.BaseEvaluator;

// Referenced classes of package msrcpsp.scheduling:
//			Schedule

public class BaseIndividual
	implements Comparable
{

	protected Schedule schedule;
	protected double evalValue;
	protected int duration;
	protected double cost;
	protected double normalDuration;
	protected double normalCost;

	public BaseIndividual(Schedule schedule, BaseEvaluator evaluator)
	{
		this.schedule = new Schedule(schedule);
		this.schedule.setEvaluator(evaluator.getCopy(this.schedule));
		setEvalValue(-1D);
	}

	public void setDurationAndCost()
	{
		duration = schedule.getEvaluator().getDuration();
		cost = schedule.getEvaluator().getCost();
	}

	public void setNormalDurationAndCost()
	{
		BaseEvaluator evaluator = schedule.getEvaluator();
		normalDuration = (double)evaluator.getDuration() / (double)evaluator.getMaxDuration();
		normalCost = evaluator.getCost() / (double)evaluator.getMaxCost();
	}

	public boolean dominates(BaseIndividual individual)
	{
		return getCost() <= individual.getCost() && getDuration() <= individual.getDuration();
	}

	public boolean dominates(List population)
	{
		for (Iterator iterator = population.iterator(); iterator.hasNext();)
		{
			BaseIndividual individual = (BaseIndividual)iterator.next();
			if (!dominates(individual))
				return false;
		}

		return true;
	}

	public boolean isDominated(BaseIndividual individual)
	{
		return individual.dominates(this);
	}

	public Schedule getSchedule()
	{
		return schedule;
	}

	public void setSchedule(Schedule schedule)
	{
		this.schedule = schedule;
	}

	public double getEvalValue()
	{
		return evalValue;
	}

	public void setEvalValue(double evalValue)
	{
		this.evalValue = evalValue;
	}

	public int getDuration()
	{
		return duration;
	}

	public void setDuration(int duration)
	{
		this.duration = duration;
	}

	public double getCost()
	{
		return cost;
	}

	public void setCost(double cost)
	{
		this.cost = cost;
	}

	public double getNormalDuration()
	{
		return normalDuration;
	}

	public void setNormalDuration(double normalDuration)
	{
		this.normalDuration = normalDuration;
	}

	public double getNormalCost()
	{
		return normalCost;
	}

	public void setNormalCost(double normalCost)
	{
		this.normalCost = normalCost;
	}

	public int compareTo(BaseIndividual o)
	{
		if (getNormalDuration() == o.getNormalDuration())
			return Double.compare(getNormalCost(), o.getNormalCost());
		else
			return Double.compare(getNormalDuration(), o.getNormalDuration());
	}

	public int compareTo(Object obj)
	{
		return compareTo((BaseIndividual)obj);
	}
}
