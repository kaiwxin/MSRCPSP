// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BaseCrossover.java

package msrcpsp.evolutionary_algorithms.genetic_algorithm.crossovers;

import java.util.Random;
import msrcpsp.evolutionary_algorithms.genetic_algorithm.selections.IndividualPair;

public abstract class BaseCrossover
{

	public BaseCrossover()
	{
	}

	public abstract IndividualPair crossover(IndividualPair individualpair, Random random);
}
