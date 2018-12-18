// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   InitialPopulationType.java

package msrcpsp.evolutionary_algorithms.genetic_algorithm.initial_populations;


public final class InitialPopulationType extends Enum
{

	public static final InitialPopulationType RANDOM;
	private static final InitialPopulationType $VALUES[];

	public static InitialPopulationType[] values()
	{
		return (InitialPopulationType[])$VALUES.clone();
	}

	public static InitialPopulationType valueOf(String name)
	{
		return (InitialPopulationType)Enum.valueOf(msrcpsp/evolutionary_algorithms/genetic_algorithm/initial_populations/InitialPopulationType, name);
	}

	private InitialPopulationType(String s, int i)
	{
		super(s, i);
	}

	static 
	{
		RANDOM = new InitialPopulationType("RANDOM", 0);
		$VALUES = (new InitialPopulationType[] {
			RANDOM
		});
	}
}
