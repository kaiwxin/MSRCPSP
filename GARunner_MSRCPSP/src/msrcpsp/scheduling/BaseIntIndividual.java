// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BaseIntIndividual.java

package msrcpsp.scheduling;

import msrcpsp.evaluation.BaseEvaluator;

// Referenced classes of package msrcpsp.scheduling:
//			BaseIndividual, Schedule

public class BaseIntIndividual extends BaseIndividual
{

	private int genes[];

	public BaseIntIndividual(Schedule schedule, int genes[], BaseEvaluator evaluator)
	{
		super(schedule, evaluator);
		this.genes = new int[schedule.getTasks().length];
		System.arraycopy(genes, 0, this.genes, 0, genes.length);
	}

	public int[] getGenes()
	{
		return genes;
	}

	public void setGenes(int genes[])
	{
		this.genes = genes;
	}
}
