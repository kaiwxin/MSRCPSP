// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SinglePointCrossover.java

package msrcpsp.evolutionary_algorithms.genetic_algorithm.crossovers;

import java.util.Random;
import msrcpsp.evolutionary_algorithms.genetic_algorithm.selections.IndividualPair;
import msrcpsp.scheduling.BaseIntIndividual;
import msrcpsp.scheduling.Schedule;

// Referenced classes of package msrcpsp.evolutionary_algorithms.genetic_algorithm.crossovers:
//			BaseCrossover

public class SinglePointCrossover extends BaseCrossover
{

	public SinglePointCrossover()
	{
	}

	public IndividualPair crossover(IndividualPair parents, Random generator)
	{
		BaseIntIndividual firstParent = parents.getFirstIndividual();
		BaseIntIndividual secondParent = parents.getSecondIndividual();
		int firstGenes[] = firstParent.getGenes();
		int secondGenes[] = secondParent.getGenes();
		int point = generator.nextInt(firstGenes.length);
		BaseIntIndividual firstChild = new BaseIntIndividual(firstParent.getSchedule(), firstGenes, firstParent.getSchedule().getEvaluator());
		BaseIntIndividual secondChild = new BaseIntIndividual(secondParent.getSchedule(), secondGenes, secondParent.getSchedule().getEvaluator());
		for (int i = 0; i < firstGenes.length; i++)
			if (i < point)
			{
				firstChild.getGenes()[i] = firstGenes[i];
				secondChild.getGenes()[i] = secondGenes[i];
			} else
			{
				firstChild.getGenes()[i] = secondGenes[i];
				secondChild.getGenes()[i] = firstGenes[i];
			}

		return new IndividualPair(firstChild, secondChild);
	}
}
