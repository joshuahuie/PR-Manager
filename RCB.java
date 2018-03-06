import java.util.*;

public class RCB {

	String RID;
	int k_starting;
	int u_available;
	
	Map<Process,Integer> waitingList;
	
	RCB(String id, int status)
	{
		this.RID = id;
		this.k_starting = status; 
		this.u_available = status;
		
		this.waitingList = new LinkedHashMap<Process,Integer>();
	}
	
	void Request(String rid, int n_units)
	{
		if(this.u_available >= n_units)
		{
			if(n_units <= this.u_available)
				u_available = u_available - n_units;
			
		}
	}
	
	
	void addU(int v1)
	{
		this.u_available += v1;
	}
	
	void subtractU(int v1)
	{
		this.u_available -= v1;
	}
	
	void addProcess(Process p,Integer v1) //adds to the waiting list
	{
		this.waitingList.put(p, v1);
	}
	
	Map.Entry<Process,Integer> getFirstWaiting() //adds to the waiting list
	{
		return this.waitingList.entrySet().iterator().next();
	}
	
	void removeFirstWaiting()
	{
		Process toRemove = this.waitingList.entrySet().iterator().next().getKey();
		this.waitingList.remove(toRemove);
	}
	
	void printWaitingList()
	{
		for(Map.Entry<Process, Integer> x: this.waitingList.entrySet())
			System.out.println(x.getKey().ID + " " + x.getValue());
	}
	
	
	
}
