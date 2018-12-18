// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Skill.java

package msrcpsp.scheduling;


public class Skill
{

	private String type;
	private int level;

	public Skill(String type, int level)
	{
		this.type = type;
		this.level = level;
	}

	public Skill()
	{
		this("", 0);
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public int getLevel()
	{
		return level;
	}

	public void setLevel(int level)
	{
		this.level = level;
	}

	public String toString()
	{
		return (new StringBuilder()).append(type).append(" : ").append(level).toString();
	}

	public boolean equals(Object s)
	{
		if (!(s instanceof Skill))
		{
			return false;
		} else
		{
			Skill skill = (Skill)s;
			return skill.type.equals(type) && skill.level == level;
		}
	}
}
