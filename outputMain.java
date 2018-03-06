import java.util.Scanner;
import java.io.*;

/*
 * MAIN PROGRAM
 */
public class outputMain {

	public static void main(String[] args)
	{
		listOfRCBS resources = new listOfRCBS();
		readyList RL = new readyList(); 
		
		String fileName;
		Scanner scanner = new Scanner(System.in);
		System.out.println("type file to open:");
		fileName = scanner.nextLine().trim();
		
		BufferedReader br = null;
		String line;
		String delim = "[ ,?!]+"; 
	
		
		String writeTo;
		System.out.println("input file to write to:");
		writeTo = scanner.nextLine().trim();
		
		File file = new File(writeTo);
	
		try {
			file.createNewFile();
		} catch (IOException e1) {
			System.out.println("BAD FILE");
		}
		
		PrintStream out = null;
		try {
			out = new PrintStream(file);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
		System.setOut(out);
		System.out.print("init ");
		try
		{
			br = new BufferedReader(new FileReader(fileName));
			while((line = br.readLine()) != null)
			{
				String[] splitArgs = line.split(delim);
				
				if(splitArgs.length > 0 && !line.isEmpty())
				{
					switch(splitArgs[0]) {
					case "init": // error checked
						resources.initReset(); // resets list of resources
						RL.initReset(); // resets readylist
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
						System.out.print("error ");
						break;
				}
			}
				
		}
	}
		
	catch(Exception e)
	{
		System.out.println("error1 ");
	}
	scanner.close();
}
	
}
