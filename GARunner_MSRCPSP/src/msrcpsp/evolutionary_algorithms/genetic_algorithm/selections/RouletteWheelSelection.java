// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   RouletteWheelSelection.java

package msrcpsp.evolutionary_algorithms.genetic_algorithm.selections;

import java.util.*;
import msrcpsp.scheduling.BaseIntIndividual;

// Referenced classes of package msrcpsp.evolutionary_algorithms.genetic_algorithm.selections:
//			BaseSelection, IndividualPair

public class RouletteWheelSelection extends BaseSelection
{

	public RouletteWheelSelection()
	{
	}

	public IndividualPair select(List population, Random generator)
	{
		double sum = 0.0D;
		for (Iterator iterator = population.iterator(); iterator.hasNext();)
		{
			BaseIntIndividual individual = (BaseIntIndividual)iterator.next();
			sum += 1.0D - individual.getEvalValue();
		}

		double value = generator.nextDouble() * sum;
		BaseIntIndividual firstParent = chooseParent(population, value);
		value = generator.nextDouble() * sum;
		BaseIntIndividual secondParent = chooseParent(population, value);
		return new IndividualPair(firstParent, secondParent);
	}

	private BaseIntIndividual chooseParent(List population, double value)
	{
		double currentSum = 0.0D;
		for (Iterator iterator = population.iterator(); iterator.hasNext();)
		{
			BaseIntIndividual individual = (BaseIntIndividual)iterator.next();
			currentSum += 1.0D - individual.getEvalValue();
			if (currentSum > value)
				return individual;
		}

		return null;
	}
}
