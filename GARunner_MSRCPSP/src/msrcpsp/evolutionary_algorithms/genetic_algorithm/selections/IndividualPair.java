// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   IndividualPair.java

package msrcpsp.evolutionary_algorithms.genetic_algorithm.selections;

import msrcpsp.scheduling.BaseIntIndividual;

public class IndividualPair
{

	private BaseIntIndividual firstIndividual;
	private BaseIntIndividual secondIndividual;

	public IndividualPair(BaseIntIndividual first, BaseIntIndividual second)
	{
		firstIndividual = first;
		secondIndividual = second;
	}

	public BaseIntIndividual getFirstIndividual()
	{
		return firstIndividual;
	}

	public void setFirstIndividual(BaseIntIndividual firstIndividual)
	{
		this.firstIndividual = firstIndividual;
	}

	public BaseIntIndividual getSecondIndividual()
	{
		return secondIndividual;
	}

	public void setSecondIndividual(BaseIntIndividual secondIndividual)
	{
		this.secondIndividual = secondIndividual;
	}
}
