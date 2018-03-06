import java.util.ArrayList;

public class listOfRCBS {
	
	ArrayList<RCB> fourResources;
	
	listOfRCBS()
	{
		this.fourResources = new ArrayList<RCB>();
		
		fourResources.add(new RCB("R1",1));
		fourResources.add(new RCB("R2",2));
		fourResources.add(new RCB("R3",3));
		fourResources.add(new RCB("R4",4));
		
	}
	
	void initReset()
	{
		listOfRCBS toReset = new listOfRCBS();
		this.fourResources = toReset.fourResources;
	}
	
	
	RCB getResource(String resourceName)
	{
		for(RCB resource: fourResources)
			if(resource.RID.equals(resourceName))
				return resource;
		
		return null;
	}

	void printResourceAmount()
	{
		System.out.println("R1 has resources available: " + this.getResource("R1").u_available);
		System.out.println("R2 has resources available: " + this.getResource("R2").u_available);
		System.out.println("R3 has resources available: " + this.getResource("R3").u_available);
		System.out.println("R4 has resources available: " + this.getResource("R4").u_available);
	}
	
}
