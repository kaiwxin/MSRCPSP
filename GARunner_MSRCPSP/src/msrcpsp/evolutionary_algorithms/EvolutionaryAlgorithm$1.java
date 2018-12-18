// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   EvolutionaryAlgorithm.java

package msrcpsp.evolutionary_algorithms;

import msrcpsp.scheduling.schedule_builders.ScheduleBuilderType;

// Referenced classes of package msrcpsp.evolutionary_algorithms:
//			EvolutionaryAlgorithm

static class EvolutionaryAlgorithm$1
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
