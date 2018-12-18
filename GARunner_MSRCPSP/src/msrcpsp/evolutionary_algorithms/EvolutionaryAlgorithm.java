// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   EvolutionaryAlgorithm.java

package msrcpsp.evolutionary_algorithms;

import java.util.Random;
import msrcpsp.evaluation.BaseEvaluator;
import msrcpsp.scheduling.Schedule;
import msrcpsp.scheduling.schedule_builders.*;

public class EvolutionaryAlgorithm
{

	protected Schedule schedule;
	protected int populationSize;
	protected int generationLimit;
	protected int upperBounds[];
	protected boolean hasSuccesors[];
	protected BaseEvaluator evaluator;
	protected ScheduleBuilder scheduleBuilder;
	protected Random generator;

	public EvolutionaryAlgorithm()
	{
	}

	protected void fillSuccessors(Schedule schedule)
	{
		hasSuccesors = schedule.getSuccesors();
	}

	public ScheduleBuilder setScheduleBuilder(ScheduleBuilderType type)
	{
		static class 1
		{

			static final int $SwitchMap$msrcpsp$scheduling$schedule_builders$ScheduleBuilderType[];

			static 
			{
				$SwitchMap$msrcpsp$scheduling$schedule_builders$ScheduleBuilderType = new int[ScheduleBuilderType.values().length];
				try
				{
					$SwitchMap$msrcpsp$scheduling$schedule_builders$ScheduleBuilderType[ScheduleBuilderType.SCHEDULE_BULDER.ordinal()] = 1;
				}
				catch (NoSuchFieldError nosuchfielderror) { }
				try
				{
					$SwitchMap$msrcpsp$scheduling$schedule_builders$ScheduleBuilderType[ScheduleBuilderType.FORWARD_SCHEDULE_BUILDER.ordinal()] = 2;
				}
				catch (NoSuchFieldError nosuchfielderror1) { }
				try
				{
					$SwitchMap$msrcpsp$scheduling$schedule_builders$ScheduleBuilderType[ScheduleBuilderType.BACKWARD_SCHEDULE_BUILDER.ordinal()] = 3;
				}
				catch (NoSuchFieldError nosuchfielderror2) { }
			}
		}

		switch (1..SwitchMap.msrcpsp.scheduling.schedule_builders.ScheduleBuilderType[type.ordinal()])
		{
		case 1: // '\001'
			return new ScheduleBuilder();

		case 2: // '\002'
			return new ForwardScheduleBuilder(hasSuccesors);

		case 3: // '\003'
			return new BackwardScheduleBuilder();
		}
		return new ScheduleBuilder();
	}
}
