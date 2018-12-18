// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   EvaluatorType.java

package msrcpsp.evaluation;


public final class EvaluatorType extends Enum{

	public static final EvaluatorType COST_EVALUATOR;
	public static final EvaluatorType DURATION_EVALUATOR;
	public static final EvaluatorType WEIGHTED_EVALUATOR;
	public static final EvaluatorType GENERATIONAL_DISTANCE_EVALUATOR;
	private static final EvaluatorType $VALUES[];

	public static EvaluatorType[] values()
	{
		return (EvaluatorType[])$VALUES.clone();
	}

	public static EvaluatorType valueOf(String name)
	{
		return (EvaluatorType)Enum.valueOf(msrcpsp/evaluation/EvaluatorType, name);
	}

	private EvaluatorType(String s, int i)
	{
		super(s, i);
	}

	static 
	{
		COST_EVALUATOR = new EvaluatorType("COST_EVALUATOR", 0);
		DURATION_EVALUATOR = new EvaluatorType("DURATION_EVALUATOR", 1);
		WEIGHTED_EVALUATOR = new EvaluatorType("WEIGHTED_EVALUATOR", 2);
		GENERATIONAL_DISTANCE_EVALUATOR = new EvaluatorType("GENERATIONAL_DISTANCE_EVALUATOR", 3);
		$VALUES = (new EvaluatorType[] {
			COST_EVALUATOR, DURATION_EVALUATOR, WEIGHTED_EVALUATOR, GENERATIONAL_DISTANCE_EVALUATOR
		});
	}
}
