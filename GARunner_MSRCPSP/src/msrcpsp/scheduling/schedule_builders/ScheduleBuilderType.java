// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ScheduleBuilderType.java

package msrcpsp.scheduling.schedule_builders;


public final class ScheduleBuilderType extends Enum
{

	public static final ScheduleBuilderType SCHEDULE_BULDER;
	public static final ScheduleBuilderType FORWARD_SCHEDULE_BUILDER;
	public static final ScheduleBuilderType BACKWARD_SCHEDULE_BUILDER;
	private static final ScheduleBuilderType $VALUES[];

	public static ScheduleBuilderType[] values()
	{
		return (ScheduleBuilderType[])$VALUES.clone();
	}

	public static ScheduleBuilderType valueOf(String name)
	{
		return (ScheduleBuilderType)Enum.valueOf(msrcpsp/scheduling/schedule_builders/ScheduleBuilderType, name);
	}

	private ScheduleBuilderType(String s, int i)
	{
		super(s, i);
	}

	static 
	{
		SCHEDULE_BULDER = new ScheduleBuilderType("SCHEDULE_BULDER", 0);
		FORWARD_SCHEDULE_BUILDER = new ScheduleBuilderType("FORWARD_SCHEDULE_BUILDER", 1);
		BACKWARD_SCHEDULE_BUILDER = new ScheduleBuilderType("BACKWARD_SCHEDULE_BUILDER", 2);
		$VALUES = (new ScheduleBuilderType[] {
			SCHEDULE_BULDER, FORWARD_SCHEDULE_BUILDER, BACKWARD_SCHEDULE_BUILDER
		});
	}
}
