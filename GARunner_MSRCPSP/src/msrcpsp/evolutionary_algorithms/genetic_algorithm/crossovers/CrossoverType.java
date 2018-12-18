// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CrossoverType.java

package msrcpsp.evolutionary_algorithms.genetic_algorithm.crossovers;


public final class CrossoverType extends Enum
{

	public static final CrossoverType SINGLE_POINT;
	private static final CrossoverType $VALUES[];

	public static CrossoverType[] values()
	{
		return (CrossoverType[])$VALUES.clone();
	}

	public static CrossoverType valueOf(String name)
	{
		return (CrossoverType)Enum.valueOf(msrcpsp/evolutionary_algorithms/genetic_algorithm/crossovers/CrossoverType, name);
	}

	private CrossoverType(String s, int i)
	{
		super(s, i);
	}

	static 
	{
		SINGLE_POINT = new CrossoverType("SINGLE_POINT", 0);
		$VALUES = (new CrossoverType[] {
			SINGLE_POINT
		});
	}
}
