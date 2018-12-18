// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   MSRCPSPIO.java

package msrcpsp.io;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import msrcpsp.scheduling.*;

public class MSRCPSPIO
{

	private static final Logger LOGGER = Logger.getLogger("msrcpsp/io/MSRCPSPIO.getName()");

	public MSRCPSPIO()
	{
	}

	public Schedule readDefinition(String fileName)
	{
		BufferedReader reader;
		try
		{
			reader = new BufferedReader(new FileReader(fileName));
		}
		catch (FileNotFoundException e)
		{
			LOGGER.log(Level.FINE, e.toString());
			return null;
		}
		Schedule schedule;
		String line = reader.readLine();
		int numTasks = readNumber(reader, line, "Tasks");
		int numResources = readNumber(reader, line, "Resources");
		skipTo(reader, line, "ResourceID");
		Resource resources[] = readResources(reader, numResources);
		skipTo(reader, line, "TaskID");
		Task tasks[] = readTasks(reader, numTasks);
		schedule = new Schedule(tasks, resources);
		closeReader(reader);
		break MISSING_BLOCK_LABEL_160;
		IOException e;
		e;
		Schedule schedule1;
		LOGGER.log(Level.FINE, e.toString());
		schedule1 = null;
		closeReader(reader);
		return schedule1;
		Exception exception;
		exception;
		closeReader(reader);
		throw exception;
		return schedule;
	}

	private int readNumber(BufferedReader reader, String line, String toRead)
		throws IOException
	{
		line = skipTo(reader, line, toRead);
		if (null == line)
		{
			LOGGER.log(Level.FINE, "No number specified for given type");
			return -1;
		} else
		{
			return Integer.parseInt(line.substring(line.lastIndexOf(' ') + 1));
		}
	}

	private Resource[] readResources(BufferedReader reader, int numResources)
		throws IOException
	{
		Resource resources[] = new Resource[numResources];
		for (int i = 0; i < numResources; i++)
		{
			String line = reader.readLine();
			String parts[] = line.split("\\s+");
			int id = Integer.parseInt(parts[0]);
			double salary = Double.parseDouble(parts[1]);
			Skill skills[] = new Skill[(parts.length - 1) / 2];
			skills = readSkills(skills, parts);
			resources[i] = new Resource(id, salary, skills);
		}

		return resources;
	}

	private Skill[] readSkills(Skill skills[], String parts[])
	{
		for (int i = 0; i < skills.length; i++)
		{
			String type = parts[2 + i * 2];
			skills[i] = new Skill(type.substring(0, type.length() - 1), Integer.parseInt(parts[3 + i * 2]));
		}

		return skills;
	}

	private Task[] readTasks(BufferedReader reader, int numTasks)
		throws IOException
	{
		Task tasks[] = new Task[numTasks];
		for (int i = 0; i < numTasks; i++)
		{
			String line = reader.readLine();
			String parts[] = line.split("\\s+");
			int id = Integer.parseInt(parts[0]);
			int duration = Integer.parseInt(parts[1]);
			Skill skill = new Skill(parts[2].substring(0, parts[2].length() - 1), Integer.parseInt(parts[3]));
			int predecessors[] = new int[parts.length - 4];
			predecessors = readPredecessors(predecessors, parts);
			tasks[i] = new Task(id, skill, duration, predecessors);
		}

		return tasks;
	}

	private int[] readPredecessors(int predecessors[], String parts[])
	{
		for (int i = 0; i < predecessors.length; i++)
			predecessors[i] = Integer.parseInt(parts[i + 4]);

		return predecessors;
	}

	private String skipTo(BufferedReader reader, String line, String desired)
		throws IOException
	{
		for (; null != line && !line.startsWith(desired); line = reader.readLine());
		return line;
	}

	public Schedule readSolution(String fileName, Schedule schedule)
	{
		BufferedReader reader;
		try
		{
			reader = new BufferedReader(new FileReader(fileName));
		}
		catch (FileNotFoundException e)
		{
			LOGGER.log(Level.FINE, e.toString());
			return null;
		}
		reader.readLine();
		for (String line = reader.readLine(); line != null; line = reader.readLine())
		{
			String parts[] = line.split("\\s");
			int timestamp = Integer.parseInt(parts[0]);
			for (int i = 1; i < parts.length; i++)
			{
				String idParts[] = parts[i].split("-");
				int resourceId = Integer.parseInt(idParts[0]);
				int taskId = Integer.parseInt(idParts[1]);
				schedule.assign(taskId, resourceId, timestamp);
			}

		}

		closeReader(reader);
		break MISSING_BLOCK_LABEL_182;
		IOException e;
		
		Schedule schedule1;
		LOGGER.log(Level.FINE, e.toString());
		schedule1 = null;
		closeReader(reader);
		return schedule1;
		Exception exception;
		exception;
		closeReader(reader);
		throw exception;
		return schedule;
	}

	protected void closeReader(BufferedReader reader)
	{
		try
		{
			reader.close();
		}
		catch (IOException e)
		{
			LOGGER.log(Level.FINE, e.toString());
		}
	}

	public void write(Schedule schedule, String filename)
		throws IOException
	{
		PrintWriter writer = new PrintWriter(new FileWriter(filename));
		Map map = new TreeMap();
		writer.write("Hour \t Resource assignments (resource ID - task ID) \n");
		Task atask[] = schedule.getTasks();
		int j = atask.length;
		for (int k = 0; k < j; k++)
		{
			Task t = atask[k];
			if (!map.containsKey(Integer.valueOf(t.getStart())))
				map.put(Integer.valueOf(t.getStart()), new LinkedList());
			((List)map.get(Integer.valueOf(t.getStart()))).add(t);
		}

		for (Iterator iterator = map.keySet().iterator(); iterator.hasNext(); writer.write("\n"))
		{
			int i = ((Integer)iterator.next()).intValue();
			writer.write((new StringBuilder()).append(i).append(" ").toString());
			Task t;
			for (Iterator iterator1 = ((List)map.get(Integer.valueOf(i))).iterator(); iterator1.hasNext(); writer.write((new StringBuilder()).append(t.getResourceId()).append("-").append(t.getId()).append(" ").toString()))
				t = (Task)iterator1.next();

		}

		writer.close();
	}

}
