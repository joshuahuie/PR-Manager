import java.util.*;


public class Process {

	String ID; // Process ID(PID)	
	Map<RCB,Integer> allocatedResources;
	
	int StatusType; 
	readyList statuslist_ready; 
	RCB status_blocked; 
		
	Process Parent; 
	ArrayList<Process> Children; 
	int PriorityNumber; 
	
	Process(String ID,int ST,Process Parent,ArrayList<Process> Children, int PriorNum) 
	{
		this.ID = ID;
		
		this.allocatedResources = new HashMap<RCB,Integer>();
		this.StatusType = ST;
		
		this.Parent = Parent;
		this.Children = new ArrayList<Process>();
		
		this.PriorityNumber = PriorNum;
		
	}
	
	boolean hasChild(Process child)
	{
		return Children.contains(child);
	}
	void printAllocatedResources()
	{
		for(Map.Entry<RCB,Integer> x: this.allocatedResources.entrySet())
			System.out.println("Key: " +x.getKey().RID + ", Value: " + x.getValue());
	}

	void insertResource(RCB r1,Integer v1) 
	{
		this.allocatedResources.put(r1,v1);
	}
	
	void removeResource(RCB r1, Integer v1)
	{
		this.allocatedResources.remove(r1,v1);
	}
	
	
	
	void setStatusType(Integer s1) 
	{
		this.StatusType = s1;
	}
	
	
	void addChild(Process p1)
	{
		this.Children.add(p1);
		
	}
	
	void removeChild(Process p1)
	{
		this.Children.remove(p1);
	}
	
	void Scheduler(readyList rdList)
	{
		Process highestPriority = rdList.nextChosen();
		if(this.PriorityNumber < highestPriority.PriorityNumber || this.StatusType != 1 || this == null)
		{
			highestPriority.StatusType = 1;
			System.out.print(highestPriority.ID + " ");
		}
		else
			System.out.print(highestPriority.ID + " ");
			
		

	}
	
	
	void Time_out(readyList rdList)
	{
		Process highestPriority = rdList.nextChosen();
		rdList.removeProcess(highestPriority); 
		highestPriority.StatusType = 2; 
		rdList.insertProcess(highestPriority); 
		Scheduler(rdList);
		
	}
	
	
	
	void CreateProcess(String name, int statusNumber, int PriorNum, readyList ready_list) 
	{

		if(ready_list.findProcess(ready_list.getInit(), name) == null && PriorNum <= 2 && PriorNum >= 0)
		{
			Process baby = new Process(name,statusNumber,this,null,PriorNum);
			this.addChild(baby);	
			ready_list.insertProcess(baby);
			Scheduler(ready_list);
		}
		else
			System.out.print("error ");
		
	}
	
	Process findProcess(String id,Process starting)
	{
		if(starting.Children.isEmpty())
		{
			if(starting.ID == id)
				return starting;
			
		}
		
		else
		{
			ListIterator<Process> gathered = starting.Children.listIterator();
			while(gathered.hasNext())
			{
				Process next_value = gathered.next();
				if(next_value.ID == id)
					return next_value;
				findProcess(id,next_value);
			}
			
		}
		return null;
		
	}
	
	
 
	void print_every_child(Process itself)
	{
		if(itself.Children.isEmpty())
		{
			System.out.println("THIS PROCESS HAS NO CHILDREN" + itself.ID);
			
		}
		
		else
		{
			System.out.println(itself.ID);
			ListIterator<Process> gathered = itself.Children.listIterator();
			while(gathered.hasNext())
			{
				print_every_child(gathered.next());
			}
			
		}
	}
		
	
	void Request(RCB resource, int n_units, readyList container) 
	{
		try 
		{
			if(resource.k_starting >= n_units)
			{
				if(n_units <= resource.u_available)
				{
					resource.subtractU(n_units);
					this.insertResource(resource, n_units); 
					container.printnextChosen(); 
				}
				
				else
				{
					this.StatusType = 0; 
					this.status_blocked = resource; 
					container.removeProcess(this);
					resource.addProcess(this,n_units); 
					Scheduler(container);	
						
				}
			}
			
			else
				System.out.print("error ");
		
		}
	
		catch(Exception e)
		{	
			System.out.println("Invalid input, please retry");
		}
	}
	
	

	void Release(RCB resource, int n_units, readyList container)
	{
		if(resource.k_starting != resource.u_available && this.allocatedResources.containsKey(resource) && ((resource.u_available + n_units) <=  resource.k_starting))
		{
			resource.addU(n_units); 
			this.allocatedResources.remove(resource); 
			
			while(!resource.waitingList.isEmpty() && resource.u_available >= resource.getFirstWaiting().getValue()) 
			{
				Map.Entry<Process,Integer> pInWaitingList = resource.getFirstWaiting();
				resource.subtractU(pInWaitingList.getValue()); 
				resource.waitingList.remove(pInWaitingList.getKey()); 
				pInWaitingList.getKey().StatusType = 2; 
				
				pInWaitingList.getKey().statuslist_ready = container; 
				this.insertResource(resource,resource.u_available); 
				container.insertProcess(pInWaitingList.getKey()); 
			}
			
			Scheduler(container);
		}
		
		else 
			System.out.print("error ");
	}
		
	
	
	
	
	
	
	
	
	
	
	

}
