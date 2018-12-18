// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   MSRCPSPGARunner.java

package runners;

import java.io.PrintStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import msrcpsp.evaluation.BaseEvaluator;
import msrcpsp.evaluation.WeightedEvaluator;
import msrcpsp.evolutionary_algorithms.genetic_algorithm.GeneticAlgorithm;
import msrcpsp.evolutionary_algorithms.genetic_algorithm.crossovers.CrossoverType;
import msrcpsp.evolutionary_algorithms.genetic_algorithm.initial_populations.InitialPopulationType;
import msrcpsp.evolutionary_algorithms.genetic_algorithm.mutations.MutationType;
import msrcpsp.evolutionary_algorithms.genetic_algorithm.selections.SelectionType;
import msrcpsp.io.MSRCPSPIO;
import msrcpsp.scheduling.BaseIntIndividual;
import msrcpsp.scheduling.Schedule;
import msrcpsp.scheduling.schedule_builders.ScheduleBuilderType;
import msrcpsp.validation.*;

public class MSRCPSPGARunner
{

	private static final Logger LOGGER = Logger.getLogger(runners/MSRCPSPGARunner.getName());
	private static final String definitionFile = "assets/definitions/MSRCPSP/100_20_47_9.def";

	public MSRCPSPGARunner()
	{
	}

	public static void main(String args[])
	{
		run(args);
	}

	private static List run(String args[])
	{
		if (args.length < 10)
			throw new IllegalArgumentException("9 arguments required: PopulationSize, GenerationLimit, EvaluationRate, MutationProbability, InitialPopulationType, SelectionType, CrossoverType, MutationType, ScheduleBuilderType, DefinitionFile");
		MSRCPSPIO reader = new MSRCPSPIO();
		Schedule schedule = reader.readDefinition(args[9]);
		if (null == schedule)
		{
			LOGGER.log(Level.WARNING, (new StringBuilder()).append("Could not read the Definition ").append(args[9]).toString());
			return null;
		}
		int populationSize = Integer.parseInt(args[0]);
		int generationLimit = Integer.parseInt(args[1]);
		double evalRate = Double.parseDouble(args[2]);
		double mutationProbability = Double.parseDouble(args[3]);
		InitialPopulationType initialPopulationType = InitialPopulationType.valueOf(args[4]);
		SelectionType selectionType = SelectionType.valueOf(args[5]);
		CrossoverType crossoverType = CrossoverType.valueOf(args[6]);
		MutationType mutationType = MutationType.valueOf(args[7]);
		ScheduleBuilderType scheduleBuilderType = ScheduleBuilderType.valueOf(args[8]);
		BaseEvaluator evaluator = new WeightedEvaluator(schedule, evalRate);
		GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(schedule, populationSize, generationLimit, initialPopulationType, selectionType, crossoverType, mutationType, evaluator, scheduleBuilderType, mutationProbability);
		long startTime = System.nanoTime();
		List resultIndividuals = geneticAlgorithm.schedule();
		long endTime = System.nanoTime();
		String format = "%-15s %-15s %-15s %-15s %n";
		for (int i = 0; i < resultIndividuals.size(); i++)
		{
			Schedule resultSchedule = ((BaseIntIndividual)resultIndividuals.get(i)).getSchedule();
			System.out.println((new StringBuilder()).append("Result #").append(i).toString());
			System.out.printf(format, new Object[] {
				"Assignments", "Conflicts", "Relations", "Skills"
			});
			System.out.printf(format, new Object[] {
				testAssignments(resultSchedule), testConflicts(resultSchedule), testRelations(resultSchedule), testSkills(resultSchedule)
			});
			System.out.println((new StringBuilder()).append("Duration: ").append(resultSchedule.getEvaluator().getDuration()).toString());
			System.out.println((new StringBuilder()).append("Cost: ").append(resultSchedule.getEvaluator().getCost()).toString());
		}

		System.out.println((new StringBuilder()).append("Time elapsed: ").append((double)(endTime - startTime) / Math.pow(10D, 9D)).toString());
		return resultIndividuals;
	}

	private static ValidationResult testAssignments(Schedule schedule)
	{
		AssignmentValidator validator = new AssignmentValidator();
		return validator.validate(schedule);
	}

	private static ValidationResult testConflicts(Schedule schedule)
	{
		ConflictValidator validator = new ConflictValidator();
		return validator.validate(schedule);
	}

	private static ValidationResult testRelations(Schedule schedule)
	{
		RelationValidator validator = new RelationValidator();
		return validator.validate(schedule);
	}

	private static ValidationResult testSkills(Schedule schedule)
	{
		SkillValidator validator = new SkillValidator();
		return validator.validate(schedule);
	}

}
