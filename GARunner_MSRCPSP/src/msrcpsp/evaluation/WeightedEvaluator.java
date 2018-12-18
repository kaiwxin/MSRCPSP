// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   WeightedEvaluator.java

package msrcpsp.evaluation;

import msrcpsp.scheduling.Schedule;

// Referenced classes of package msrcpsp.evaluation:
//			BaseEvaluator, EvaluatorType

public class WeightedEvaluator extends BaseEvaluator
{

	private double evalRate;

	public WeightedEvaluator(Schedule schedule, double evalRate)
	{
		super(schedule);
		this.evalRate = evalRate;
	}

	public double evaluate()
	{
		if (evalRate > 1.0D || evalRate < 0.0D)
		{
			throw new IllegalArgumentException("Cannot provide the evalRate smaller than 0 or bigger than 1!");
		} else
		{
			double durationPart = (double)getDuration() / (double)getMaxDuration();
			double costPart = getCost() / (double)getMaxCost();
			return durationPart * evalRate + costPart * (1.0D - evalRate);
		}
	}

	public BaseEvaluator getCopy(Schedule schedule)
	{
		return new WeightedEvaluator(schedule, evalRate);
	}

	public EvaluatorType getType()
	{
		return EvaluatorType.WEIGHTED_EVALUATOR;
	}

	public double getEvalRate()
	{
		return evalRate;
	}

	public void setEvalRate(double evalRate)
	{
		this.evalRate = evalRate;
	}
}
