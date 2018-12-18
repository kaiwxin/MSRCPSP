// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Schedule.java

package msrcpsp.scheduling;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import msrcpsp.evaluation.BaseEvaluator;

// Referenced classes of package msrcpsp.scheduling:
//			Task, Resource

public class Schedule
{

	private Task tasks[];
	private Resource resources[];
	private BaseEvaluator evaluator;

	public Schedule(Task tasks[], Resource resources[])
	{
		this.tasks = tasks;
		this.resources = resources;
		clear(true);
	}

	public Schedule(Schedule schedule)
	{
		tasks = new Task[schedule.getTasks().length];
		for (int i = 0; i < tasks.length; i++)
		{
			Task org = schedule.getTasks()[i];
			tasks[i] = new Task(org.getId(), org.getRequiredSkills(), org.getDuration(), org.getPredecessors());
		}

		resources = new Resource[schedule.getResources().length];
		for (int i = 0; i < resources.length; i++)
		{
			Resource org = schedule.getResources()[i];
			resources[i] = new Resource(org.getId(), org.getSalary(), org.getSkills());
		}

		Object aobj[] = tasks;
		int j = aobj.length;
		for (int k = 0; k < j; k++)
		{
			Task t = (Task) aobj[k];
			t.setStart(schedule.getTask(t.getId()).getStart());
			t.setResourceId(schedule.getTask(t.getId()).getResourceId());
		}

		aobj = resources;
		j = aobj.length;
		for (int l = 0; l < j; l++)
		{
			Resource r = (Resource) aobj[l];
			r.setFinish(schedule.getResource(r.getId()).getFinish());
		}

	}

	public int[] getUpperBounds(int numTasks)
	{
		int upperBounds[] = new int[numTasks];
		for (int i = 0; i < numTasks; i++)
			upperBounds[i] = getCapableResources(getTasks()[i]).size();

		return upperBounds;
	}

	public double evaluate()
		throws IllegalStateException
	{
		return evaluator.evaluate();
	}

	public void assign(Task t, Resource r, int timestamp)
	{
		t.setResourceId(r.getId());
		t.setStart(timestamp);
		r.setFinish(timestamp + t.getDuration());
	}

	public void assign(int taskId, int resourceId, int timestamp)
	{
		assign(getTask(taskId), getResource(resourceId), timestamp);
	}

	public void assign(Task t, int timestamp)
	{
		t.setStart(timestamp);
		int resourceId = t.getResourceId();
		if (-1 != resourceId)
			getResource(resourceId).setFinish(timestamp + t.getDuration());
	}

	public void assign(Task t, Resource r)
	{
		t.setResourceId(r.getId());
	}

	public void buildTaskResourceAssignments()
	{
		Task atask[] = getTasks();
		int i = atask.length;
		for (int j = 0; j < i; j++)
		{
			Task task = atask[j];
			task.setResourceId(((Resource)getCapableResources(task).get(task.getResourceId() - 1)).getId());
		}

	}

	public boolean[] getSuccesors()
	{
		Task tasks[] = getTasks();
		boolean hasSuccesors[] = new boolean[tasks.length];
		Task atask[] = tasks;
		int i = atask.length;
		for (int j = 0; j < i; j++)
		{
			Task task = atask[j];
			int ai[] = task.getPredecessors();
			int k = ai.length;
			for (int l = 0; l < k; l++)
			{
				int predecessor = ai[l];
				Task predecessorTask = getTask(predecessor);
				int tempId = getTaskIndex(predecessorTask);
				hasSuccesors[tempId] = true;
			}

		}

		return hasSuccesors;
	}

	private int getTaskIndex(Task task)
	{
		int index = 0;
		Task atask[] = getTasks();
		int i = atask.length;
		for (int j = 0; j < i; j++)
		{
			Task t = atask[j];
			if (t.getId() == task.getId())
				return index;
			index++;
		}

		return -1;
	}

	public Schedule(int numOfTasks, int numOfResources)
	{
		tasks = new Task[numOfTasks];
		resources = new Resource[numOfResources];
	}

	public void clear(boolean withAssignments)
	{
		if (withAssignments)
		{
			Task atask[] = tasks;
			int i = atask.length;
			for (int l = 0; l < i; l++)
			{
				Task t = atask[l];
				t.setStart(-1);
				t.setResourceId(-1);
			}

		} else
		{
			Task atask1[] = tasks;
			int j = atask1.length;
			for (int i1 = 0; i1 < j; i1++)
			{
				Task t = atask1[i1];
				t.setStart(-1);
			}

		}
		Resource aresource[] = resources;
		int k = aresource.length;
		for (int j1 = 0; j1 < k; j1++)
		{
			Resource r = aresource[j1];
			r.setFinish(-1);
		}

	}

	public Task getTask(int taskId)
	{
		Task atask[] = tasks;
		int i = atask.length;
		for (int j = 0; j < i; j++)
		{
			Task t = atask[j];
			if (t.getId() == taskId)
				return t;
		}

		return null;
	}

	public Resource getResource(int resourceId)
	{
		Resource aresource[] = resources;
		int i = aresource.length;
		for (int j = 0; j < i; j++)
		{
			Resource r = aresource[j];
			if (r.getId() == resourceId)
				return r;
		}

		return null;
	}

	public List getCapableResources(Task t)
	{
		List result = new LinkedList();
		Resource aresource[] = resources;
		int i = aresource.length;
		for (int j = 0; j < i; j++)
		{
			Resource r = aresource[j];
			if (canDoTask(t, r))
				result.add(r);
		}

		return result;
	}

	public List getCapableResources(Task t, int timestamp)
	{
		List result = new LinkedList();
		Resource aresource[] = resources;
		int i = aresource.length;
		for (int j = 0; j < i; j++)
		{
			Resource r = aresource[j];
			if (canDoTask(t, r) && timestamp >= r.getFinish())
				result.add(r);
		}

		return result;
	}

	public Resource findFirstFreeResource(List tempR)
	{
		if (tempR == null)
			return null;
		Resource result = (Resource)tempR.get(0);
		int firstFree = ((Resource)tempR.get(0)).getFinish();
		Iterator iterator = tempR.iterator();
		do
		{
			if (!iterator.hasNext())
				break;
			Resource r = (Resource)iterator.next();
			if (r.getFinish() < firstFree)
			{
				result = r;
				firstFree = r.getFinish();
			}
		} while (true);
		return result;
	}

	public Resource findCheapestResource()
	{
		double salary = resources[0].getSalary();
		Resource result = resources[0];
		Resource aresource[] = resources;
		int i = aresource.length;
		for (int j = 0; j < i; j++)
		{
			Resource r = aresource[j];
			if (r.getSalary() < salary)
			{
				salary = r.getSalary();
				result = r;
			}
		}

		return result;
	}

	public Resource findCheapestResource(List tempR)
	{
		if (tempR.isEmpty())
			return null;
		double salary = ((Resource)tempR.get(0)).getSalary();
		Resource result = (Resource)tempR.get(0);
		Iterator iterator = tempR.iterator();
		do
		{
			if (!iterator.hasNext())
				break;
			Resource r = (Resource)iterator.next();
			if (r.getSalary() < salary)
			{
				salary = r.getSalary();
				result = r;
			}
		} while (true);
		return result;
	}

	public int getEarliestTime(Task task)
	{
		int earliest = 0;
		int pred[] = task.getPredecessors();
		int ai[] = pred;
		int i = ai.length;
		for (int j = 0; j < i; j++)
		{
			int p = ai[j];
			Task t = getTask(p);
			if (t.getStart() + t.getDuration() > earliest)
				earliest = t.getStart() + t.getDuration();
		}

		return earliest;
	}

	public List tasksCapableByResource(Resource r)
	{
		List tasks = new LinkedList();
		Task atask[] = getTasks();
		int i = atask.length;
		for (int j = 0; j < i; j++)
		{
			Task t = atask[j];
			if (canDoTask(t, r))
				tasks.add(t);
		}

		return tasks;
	}

	public boolean canDoTask(Task t, Resource r)
	{
		if (null == r)
			return false;
		else
			return r.hasSkill(t.getRequiredSkills());
	}

	public int getNumberOfAssignments(int resourceId)
	{
		int num = 0;
		Task atask[] = tasks;
		int i = atask.length;
		for (int j = 0; j < i; j++)
		{
			Task t = atask[j];
			if (t.getResourceId() == resourceId)
				num++;
		}

		return num;
	}

	public Task[] getTasks()
	{
		return tasks;
	}

	public void setTasks(Task tasks[])
	{
		this.tasks = tasks;
	}

	public Resource[] getResources()
	{
		return resources;
	}

	public void setResources(Resource resources[])
	{
		this.resources = resources;
	}

	public BaseEvaluator getEvaluator()
	{
		return evaluator;
	}

	public void setEvaluator(BaseEvaluator evaluator)
	{
		this.evaluator = evaluator;
	}
}
