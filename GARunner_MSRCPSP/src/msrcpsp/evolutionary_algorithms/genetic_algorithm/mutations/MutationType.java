// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   MutationType.java

package msrcpsp.evolutionary_algorithms.genetic_algorithm.mutations;


public final class MutationType extends Enum
{

	public static final MutationType RANDOM;
	private static final MutationType $VALUES[];

	public static MutationType[] values()
	{
		return (MutationType[])$VALUES.clone();
	}

	public static MutationType valueOf(String name)
	{
		return (MutationType)Enum.valueOf(msrcpsp/evolutionary_algorithms/genetic_algorithm/mutations/MutationType, name);
	}

	private MutationType(String s, int i)
	{
		super(s, i);
	}

	static 
	{
		RANDOM = new MutationType("RANDOM", 0);
		$VALUES = (new MutationType[] {
			RANDOM
		});
	}
}
