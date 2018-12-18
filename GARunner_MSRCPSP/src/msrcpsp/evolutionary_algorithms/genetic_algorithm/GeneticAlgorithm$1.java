// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   GeneticAlgorithm.java

package msrcpsp.evolutionary_algorithms.genetic_algorithm;

import msrcpsp.evolutionary_algorithms.genetic_algorithm.crossovers.CrossoverType;
import msrcpsp.evolutionary_algorithms.genetic_algorithm.initial_populations.InitialPopulationType;
import msrcpsp.evolutionary_algorithms.genetic_algorithm.mutations.MutationType;
import msrcpsp.evolutionary_algorithms.genetic_algorithm.selections.SelectionType;

// Referenced classes of package msrcpsp.evolutionary_algorithms.genetic_algorithm:
//			GeneticAlgorithm

static class GeneticAlgorithm$1
{

	static final int $SwitchMap$msrcpsp$evolutionary_algorithms$genetic_algorithm$initial_populations$InitialPopulationType[];
	static final int $SwitchMap$msrcpsp$evolutionary_algorithms$genetic_algorithm$selections$SelectionType[];
	static final int $SwitchMap$msrcpsp$evolutionary_algorithms$genetic_algorithm$crossovers$CrossoverType[];
	static final int $SwitchMap$msrcpsp$evolutionary_algorithms$genetic_algorithm$mutations$MutationType[];

	static 
	{
		$SwitchMap$msrcpsp$evolutionary_algorithms$genetic_algorithm$mutations$MutationType = new int[MutationType.values().length];
		try
		{
			$SwitchMap$msrcpsp$evolutionary_algorithms$genetic_algorithm$mutations$MutationType[MutationType.RANDOM.ordinal()] = 1;
		}
		catch (NoSuchFieldError nosuchfielderror) { }
		$SwitchMap$msrcpsp$evolutionary_algorithms$genetic_algorithm$crossovers$CrossoverType = new int[CrossoverType.values().length];
		try
		{
			$SwitchMap$msrcpsp$evolutionary_algorithms$genetic_algorithm$crossovers$CrossoverType[CrossoverType.SINGLE_POINT.ordinal()] = 1;
		}
		catch (NoSuchFieldError nosuchfielderror1) { }
		$SwitchMap$msrcpsp$evolutionary_algorithms$genetic_algorithm$selections$SelectionType = new int[SelectionType.values().length];
		try
		{
			$SwitchMap$msrcpsp$evolutionary_algorithms$genetic_algorithm$selections$SelectionType[SelectionType.ROULETTE_WHEEL.ordinal()] = 1;
		}
		catch (NoSuchFieldError nosuchfielderror2) { }
		$SwitchMap$msrcpsp$evolutionary_algorithms$genetic_algorithm$initial_populations$InitialPopulationType = new int[InitialPopulationType.values().length];
		try
		{
			$SwitchMap$msrcpsp$evolutionary_algorithms$genetic_algorithm$initial_populations$InitialPopulationType[InitialPopulationType.RANDOM.ordinal()] = 1;
		}
		catch (NoSuchFieldError nosuchfielderror3) { }
	}
}
