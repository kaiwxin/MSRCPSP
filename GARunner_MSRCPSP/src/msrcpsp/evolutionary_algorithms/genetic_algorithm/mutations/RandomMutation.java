// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   RandomMutation.java

package msrcpsp.evolutionary_algorithms.genetic_algorithm.mutations;

import java.util.Random;
import msrcpsp.scheduling.BaseIntIndividual;

// Referenced classes of package msrcpsp.evolutionary_algorithms.genetic_algorithm.mutations:
//			BaseMutation

public class RandomMutation extends BaseMutation
{

	public RandomMutation()
	{
	}

	public BaseIntIndividual mutate(BaseIntIndividual individual, int upperBounds[], double mutationProbability, Random generator)
	{
		int genes[] = individual.getGenes();
		double probability = mutationProbability / (double)genes.length;
		for (int i = 0; i < genes.length; i++)
			if (generator.nextDouble() < probability)
				genes[i] = generator.nextInt(upperBounds[i]);

		return individual;
	}
}
