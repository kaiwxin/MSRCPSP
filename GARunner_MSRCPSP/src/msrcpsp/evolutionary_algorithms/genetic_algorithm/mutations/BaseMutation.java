// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BaseMutation.java

package msrcpsp.evolutionary_algorithms.genetic_algorithm.mutations;

import java.util.Random;
import msrcpsp.scheduling.BaseIntIndividual;

public abstract class BaseMutation
{

	public BaseMutation()
	{
	}

	public abstract BaseIntIndividual mutate(BaseIntIndividual baseintindividual, int ai[], double d, Random random);
}
