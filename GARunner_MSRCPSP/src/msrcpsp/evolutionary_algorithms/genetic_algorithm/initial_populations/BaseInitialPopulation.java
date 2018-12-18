// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BaseInitialPopulation.java

package msrcpsp.evolutionary_algorithms.genetic_algorithm.initial_populations;

import java.util.List;
import java.util.Random;
import msrcpsp.evaluation.BaseEvaluator;
import msrcpsp.scheduling.Schedule;

public abstract class BaseInitialPopulation
{

	public BaseInitialPopulation()
	{
	}

	public abstract List generate(Schedule schedule, int i, int ai[], BaseEvaluator baseevaluator, Random random);
}
