// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CostEvaluator.java

package msrcpsp.evaluation;

import msrcpsp.scheduling.Schedule;

// Referenced classes of package msrcpsp.evaluation:
//			BaseEvaluator, EvaluatorType

public class CostEvaluator extends BaseEvaluator
{

	public CostEvaluator(Schedule schedule)
	{
		super(schedule);
	}

	public double evaluate()
	{
		return super.getCost();
	}

	public BaseEvaluator getCopy(Schedule schedule)
	{
		return new CostEvaluator(schedule);
	}

	public EvaluatorType getType()
	{
		return EvaluatorType.COST_EVALUATOR;
	}
}
