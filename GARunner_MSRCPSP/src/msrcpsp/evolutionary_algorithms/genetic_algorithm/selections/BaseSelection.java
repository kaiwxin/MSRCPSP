// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BaseSelection.java

package msrcpsp.evolutionary_algorithms.genetic_algorithm.selections;

import java.util.List;
import java.util.Random;

// Referenced classes of package msrcpsp.evolutionary_algorithms.genetic_algorithm.selections:
//			IndividualPair

public abstract class BaseSelection
{

	public BaseSelection()
	{
	}

	public abstract IndividualPair select(List list, Random random);
}
