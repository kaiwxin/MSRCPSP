// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Resource.java

package msrcpsp.scheduling;

import java.util.Arrays;

// Referenced classes of package msrcpsp.scheduling:
//			Skill

public class Resource
{

	private int id;
	private double salary;
	private Skill skills[];
	private int finish;

	public Resource(int id, double salary, Skill skills[], int finish)
	{
		this.id = id;
		this.salary = salary;
		this.skills = skills;
		this.finish = finish;
	}

	public Resource(double salary)
	{
		this(-1, salary, null, -1);
	}

	public Resource(int id, double salary, Skill skills[])
	{
		this.id = id;
		this.salary = salary;
		this.skills = skills;
	}

	public boolean hasSkill(Skill skill)
	{
		Skill askill[] = skills;
		int i = askill.length;
		for (int j = 0; j < i; j++)
		{
			Skill s = askill[j];
			if (s.getType().equals(skill.getType()) && s.getLevel() >= skill.getLevel())
				return true;
		}

		return false;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public double getSalary()
	{
		return salary;
	}

	public void setSalary(double salary)
	{
		this.salary = salary;
	}

	public Skill[] getSkills()
	{
		return skills;
	}

	public void setSkills(Skill skills[])
	{
		this.skills = skills;
	}

	public int getFinish()
	{
		return finish;
	}

	public void setFinish(int finish)
	{
		this.finish = finish;
	}

	public String toString()
	{
		String res = "";
		Skill askill[] = skills;
		int i = askill.length;
		for (int j = 0; j < i; j++)
		{
			Skill s = askill[j];
			res = (new StringBuilder()).append(res).append(s).append(" ").toString();
		}

		return (new StringBuilder()).append(id).append(", ").append(salary).append(", ").append(res).toString();
	}

	public boolean equals(Object r)
	{
		if (!(r instanceof Resource))
		{
			return false;
		} else
		{
			Resource resource = (Resource)r;
			return id == resource.id && salary == resource.salary && Arrays.equals(skills, resource.skills);
		}
	}
}
