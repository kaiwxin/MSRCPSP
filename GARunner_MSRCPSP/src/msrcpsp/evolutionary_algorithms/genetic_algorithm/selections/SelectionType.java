// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SelectionType.java

package msrcpsp.evolutionary_algorithms.genetic_algorithm.selections;


public final class SelectionType extends Enum
{

	public static final SelectionType ROULETTE_WHEEL;
	private static final SelectionType $VALUES[];

	public static SelectionType[] values()
	{
		return (SelectionType[])$VALUES.clone();
	}

	public static SelectionType valueOf(String name)
	{
		return (SelectionType)Enum.valueOf(msrcpsp/evolutionary_algorithms/genetic_algorithm/selections/SelectionType, name);
	}

	private SelectionType(String s, int i)
	{
		super(s, i);
	}

	static 
	{
		ROULETTE_WHEEL = new SelectionType("ROULETTE_WHEEL", 0);
		$VALUES = (new SelectionType[] {
			ROULETTE_WHEEL
		});
	}
}
