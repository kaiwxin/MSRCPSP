// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   GeneticAlgorithm.java

package msrcpsp.evolutionary_algorithms.genetic_algorithm;

import java.util.*;
import msrcpsp.evaluation.BaseEvaluator;
import msrcpsp.evolutionary_algorithms.EvolutionaryAlgorithm;
import msrcpsp.evolutionary_algorithms.genetic_algorithm.crossovers.BaseCrossover;
import msrcpsp.evolutionary_algorithms.genetic_algorithm.crossovers.CrossoverType;
import msrcpsp.evolutionary_algorithms.genetic_algorithm.crossovers.SinglePointCrossover;
import msrcpsp.evolutionary_algorithms.genetic_algorithm.initial_populations.BaseInitialPopulation;
import msrcpsp.evolutionary_algorithms.genetic_algorithm.initial_populations.InitialPopulationType;
import msrcpsp.evolutionary_algorithms.genetic_algorithm.initial_populations.RandomInitialPopulation;
import msrcpsp.evolutionary_algorithms.genetic_algorithm.mutations.BaseMutation;
import msrcpsp.evolutionary_algorithms.genetic_algorithm.mutations.MutationType;
import msrcpsp.evolutionary_algorithms.genetic_algorithm.mutations.RandomMutation;
import msrcpsp.evolutionary_algorithms.genetic_algorithm.selections.BaseSelection;
import msrcpsp.evolutionary_algorithms.genetic_algorithm.selections.IndividualPair;
import msrcpsp.evolutionary_algorithms.genetic_algorithm.selections.RouletteWheelSelection;
import msrcpsp.evolutionary_algorithms.genetic_algorithm.selections.SelectionType;
import msrcpsp.scheduling.*;
import msrcpsp.scheduling.schedule_builders.ScheduleBuilder;
import msrcpsp.scheduling.schedule_builders.ScheduleBuilderType;

public class GeneticAlgorithm extends EvolutionaryAlgorithm
{

	private List population;
	private int upperBounds[];
	private double mutationProbability;
	private BaseInitialPopulation initialPopulation;
	private BaseSelection selection;
	private BaseCrossover crossover;
	private BaseMutation mutation;
	private ScheduleBuilder scheduleBuilder;

	public GeneticAlgorithm(Schedule schedule, int populationSize, int generationLimit, InitialPopulationType initialPopulationType, SelectionType selectionType, CrossoverType crossoverType, MutationType mutationType, 
			BaseEvaluator evaluator, ScheduleBuilderType scheduleBuilderType, double mutationProbability)
	{
		this.schedule = schedule;
		this.populationSize = populationSize;
		this.generationLimit = generationLimit;
		this.mutationProbability = mutationProbability;
		upperBounds = schedule.getUpperBounds(schedule.getTasks().length);
		generator = new Random(System.currentTimeMillis());
		initialPopulation = setInitialPopulation(initialPopulationType);
		selection = setSelection(selectionType);
		crossover = setCrossover(crossoverType);
		mutation = setMutation(mutationType);
		this.evaluator = evaluator;
		fillSuccessors(schedule);
		scheduleBuilder = setScheduleBuilder(scheduleBuilderType);
	}

	public List schedule()
	{
		int generation = 0;
		population = initialPopulation.generate(schedule, populationSize, upperBounds, evaluator, generator);
		BaseIntIndividual individual;
		for (Iterator iterator = population.iterator(); iterator.hasNext(); buildSchedule(individual))
			individual = (BaseIntIndividual)iterator.next();

		BaseIntIndividual best = findBestIndividual(population);
		for (; generation < generationLimit; generation++)
		{
			List newPopulation = new ArrayList();
			for (int i = 0; i < populationSize; i++)
			{
				IndividualPair parents = selection.select(population, generator);
				IndividualPair offspring = crossover.crossover(parents, generator);
				offspring.setFirstIndividual(mutation.mutate(offspring.getFirstIndividual(), upperBounds, mutationProbability, generator));
				offspring.setSecondIndividual(mutation.mutate(offspring.getSecondIndividual(), upperBounds, mutationProbability, generator));
				buildSchedule(offspring.getFirstIndividual());
				buildSchedule(offspring.getSecondIndividual());
				newPopulation.add(offspring.getFirstIndividual());
				newPopulation.add(offspring.getSecondIndividual());
				if (offspring.getFirstIndividual().getEvalValue() < best.getEvalValue())
					best = offspring.getFirstIndividual();
				if (offspring.getSecondIndividual().getEvalValue() < best.getEvalValue())
					best = offspring.getSecondIndividual();
			}

			population = newPopulation;
		}

		schedule = best.getSchedule();
		List results = new ArrayList();
		results.add(best);
		return results;
	}

	private BaseIntIndividual buildSchedule(BaseIntIndividual individual)
	{
		Schedule schedule = individual.getSchedule();
		Task tasks[] = schedule.getTasks();
		int genes[] = individual.getGenes();
		for (int i = 0; i < genes.length; i++)
			tasks[i].setResourceId(genes[i] + 1);

		individual.getSchedule().buildTaskResourceAssignments();
		individual.setSchedule(scheduleBuilder.buildTimestamps(schedule));
		individual.setEvalValue(schedule.evaluate());
		individual.setDurationAndCost();
		return individual;
	}

	private BaseIntIndividual findBestIndividual(List population)
	{
		BaseIntIndividual best = (BaseIntIndividual)population.get(0);
		double eval = best.getEvalValue();
		for (int i = 1; i < population.size(); i++)
		{
			BaseIntIndividual trial = (BaseIntIndividual)population.get(i);
			if (trial.getEvalValue() < eval)
			{
				best = trial;
				eval = trial.getEvalValue();
			}
		}

		return best;
	}

	public BaseInitialPopulation setInitialPopulation(InitialPopulationType type)
	{
		static class 1
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

		switch (1..SwitchMap.msrcpsp.evolutionary_algorithms.genetic_algorithm.initial_populations.InitialPopulationType[type.ordinal()])
		{
		case 1: // '\001'
			return new RandomInitialPopulation();
		}
		return new RandomInitialPopulation();
	}

	public BaseSelection setSelection(SelectionType type)
	{
		switch (1..SwitchMap.msrcpsp.evolutionary_algorithms.genetic_algorithm.selections.SelectionType[type.ordinal()])
		{
		case 1: // '\001'
			return new RouletteWheelSelection();
		}
		return new RouletteWheelSelection();
	}

	public BaseCrossover setCrossover(CrossoverType type)
	{
		switch (1..SwitchMap.msrcpsp.evolutionary_algorithms.genetic_algorithm.crossovers.CrossoverType[type.ordinal()])
		{
		case 1: // '\001'
			return new SinglePointCrossover();
		}
		return new SinglePointCrossover();
	}

	public BaseMutation setMutation(MutationType type)
	{
		switch (1..SwitchMap.msrcpsp.evolutionary_algorithms.genetic_algorithm.mutations.MutationType[type.ordinal()])
		{
		case 1: // '\001'
			return new RandomMutation();
		}
		return new RandomMutation();
	}
}
