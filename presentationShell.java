import java.util.*;
import java.io.*;
/*
 * terminal testing
 * shows currently running process 
 */
public class presentationShell {
	
	static void printScanner(String[] value)
	{
		for(int i = 0; i < 3; i++)
			System.out.println(value[i]);
	}
	
	public static void main(String[] args)
	{

		listOfRCBS resources = new listOfRCBS();
		readyList RL = new readyList(); 
		
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Please input a command");
		System.out.println("Possible commands: init, cr, de, req, rel, to");
		String inputLine = "";
		String delim = "[ ,?!]+"; 
		
	
		while(!inputLine.equals("exit"))
		{
			inputLine = scanner.nextLine();			
			String[] splitArgs = inputLine.split(delim);
			
			switch(splitArgs[0]) {
			
			case "init": 
				resources.initReset(); 
				RL.initReset(); 
				System.out.print("\n" + RL.nextChosen().ID + " ");
				break;
			
			case "cr":
				Integer value = Integer.parseInt(splitArgs[2].trim());
				RL.nextChosen().CreateProcess(splitArgs[1],2,value,RL); 
				break;
				
			case "de":
				RL.destroy(RL.findProcess(RL.getInit(), splitArgs[1]));
				break;
				
			case "to":
				RL.nextChosen().Time_out(RL);
				break;
			
			case "req":
				String resourceRequested = splitArgs[1];
				RL.nextChosen().Request(resources.getResource(resourceRequested),Integer.parseInt(splitArgs[2].trim()),RL);  
				break;
		
			case "rel":
				String rnum = splitArgs[1];
				RL.nextChosen().Release(resources.getResource(rnum),Integer.parseInt(splitArgs[2].trim()),RL);
				break;
				
			case "\n":
				break;
			
			default:
				System.out.print("error \n");
				break;

			}
			
			
		}
		
		
		
	}
}
