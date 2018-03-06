import java.util.*;

public class readyList {
	
	ArrayList<ArrayList<Process>> OtherResources;
	Process start;
	
	readyList()
	{
		
		this.OtherResources = new ArrayList<ArrayList<Process>>();
		this.start = new Process("init",1,null,null,0); 
		
		for(int i = 0; i < 3; i++)
			this.OtherResources.add(new ArrayList<Process>());
		
		this.OtherResources.get(0).add(start);
		
		
	}
	
	void initReset()
	{
		readyList toReset = new readyList();
		this.OtherResources = toReset.OtherResources;
	}
	
	void destroy(Process p)  
	{
		if(this.findProcess(this.nextChosen(),p.ID) != null)
		{
			if(p.ID.equals("init"))
				System.out.print("error ");
			else
			{
				kill_tree(p); 
				p.Scheduler(this); 
			}
		}
		
		else
			System.out.print("error ");
	}
	
	void kill_tree(Process itself) 
	{
		Process to_remove = itself; 
		if(to_remove.Parent != null)
			to_remove.Parent.removeChild(to_remove); 
		this.removeProcess(to_remove); 
		
		Iterator<Map.Entry<RCB, Integer>> iter = to_remove.allocatedResources.entrySet().iterator(); 
		while(iter.hasNext())
		{
			Map.Entry<RCB, Integer> entry = iter.next();
			entry.getKey().addU(entry.getValue()); 

			if(entry.getKey().getFirstWaiting().getValue() <= entry.getKey().u_available) // if first waiting reserved value <= how much the resource has available
			{
				entry.getKey().getFirstWaiting().getKey().allocatedResources.put(entry.getKey(),entry.getKey().getFirstWaiting().getValue()); 
				entry.getKey().subtractU(entry.getKey().getFirstWaiting().getValue());
				this.insertProcess(entry.getKey().getFirstWaiting().getKey());
				
				entry.getKey().removeFirstWaiting(); //remove from waiting list
			}
				
			iter.remove(); 
			
		}
		
		if(itself.Children.isEmpty()) 
		{}
		
		else
		{
			List<Process> removing = new ArrayList<Process>();
			for(Process p1: itself.Children)
				removing.add(p1);
			
			for(Process i: removing)
				kill_tree(i);

			
		}
	
	}
	
	

	Process getInit()
	{
		return this.OtherResources.get(0).get(0);
	}
	
	Process findProcess(Process p_init, String find)
	{
		if(p_init.ID.equals(find))
			return p_init;
		
		ArrayList<Process> holder = new ArrayList<Process>();
		for(Process x: p_init.Children)
			holder.add(x);
		
		Process returnResult = null;
		for(int i = 0; returnResult == null && i < holder.size(); i++)
			returnResult = findProcess(holder.get(i),find);
		
		return returnResult;
			
	}
	
	void insertProcess(Process p1)
	{
		//Adds to correct spot in RCB, based on priority number
		if(p1.PriorityNumber == 2)
			this.OtherResources.get(2).add(p1);
		
		else if(p1.PriorityNumber == 1)
			this.OtherResources.get(1).add(p1);
		
		else if(p1.PriorityNumber == 0)
			this.OtherResources.get(0).add(p1);
		
	}
	
	void removeProcess(Process p1)
	{
		if(p1.PriorityNumber == 2)
			this.OtherResources.get(2).remove(p1);
		
		else if(p1.PriorityNumber == 1)
			this.OtherResources.get(1).remove(p1);
		
		else if(p1.PriorityNumber == 0)
			this.OtherResources.get(0).remove(p1);
		
	}
	
	Process nextChosen()  
	{
		if(OtherResources.get(2).size() >= 1)
			return OtherResources.get(2).get(0);
		
		else if(OtherResources.get(1).size() >= 1)
			return OtherResources.get(1).get(0);
		
		else 
			return OtherResources.get(0).get(0);
			
	}
	
	void printnextChosen()
	{
		System.out.print(nextChosen().ID + " ");
	}
	
}	
