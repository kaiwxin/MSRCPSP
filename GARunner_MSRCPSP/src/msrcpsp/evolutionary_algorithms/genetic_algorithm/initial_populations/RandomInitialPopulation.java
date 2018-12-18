// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   RandomInitialPopulation.java

package msrcpsp.evolutionary_algorithms.genetic_algorithm.initial_populations;

import java.util.*;
import msrcpsp.evaluation.BaseEvaluator;
import msrcpsp.scheduling.BaseIntIndividual;
import msrcpsp.scheduling.Schedule;

// Referenced classes of package msrcpsp.evolutionary_algorithms.genetic_algorithm.initial_populations:
//			BaseInitialPopulation

public class RandomInitialPopulation extends BaseInitialPopulation
{

	public RandomInitialPopulation()
	{
	}

	public List generate(Schedule schedule, int populationSize, int upperBounds[], BaseEvaluator evaluator, Random generator)
	{
		List population = new ArrayList(populationSize);
		int numTasks = schedule.getTasks().length;
		int genes[] = new int[numTasks];
		for (int i = 0; i < populationSize; i++)
		{
			for (int j = 0; j < numTasks; j++)
				genes[j] = generator.nextInt(upperBounds[j]);

			population.add(new BaseIntIndividual(schedule, genes, evaluator));
		}

		return population;
	}
}
