/*an algorithm to find the minimum number of tries needed 
to hit a group of balloons placed at different heights*/

import java.io.*;
import java.util.*;
import java.util.regex.*;
import java.math.*;
import static java.lang.System.out;


public class balloon {

	public static void main(String args[]) {
		
	String file = "testBalloons.txt";
	ArrayList<Integer> heights = new ArrayList<Integer>();
	ArrayList<Integer> sols = new ArrayList<Integer>();

	try {
        Scanner f = new Scanner(new File(file));
        
        int numbTrials = (int) Integer.parseInt(f.nextLine());
        String [] numbBalloonsString = f.nextLine().split("\\s+");
        
        
        while (f.hasNext()){
        	
    
        	String[] line = f.nextLine().split("\\s+");
        	
        	for (int i=0; i<line.length; i++) {
        		int j = Integer.parseInt(line[i]);
        		heights.add(j);
        	}
        	
        	sols.add(arrowsNeeded(heights));
        	heights.clear();
        	
        	
        }
   
        f.close();
		
	}
	
	catch (IOException e){
        System.out.println("something went wrong!");
        System.exit(1);
    }
		
	try {
		fileWriter(sols);
	}
	catch (IOException e) {
		e.printStackTrace();
	}
		

		
	}
	
	public static int arrowsNeeded(ArrayList<Integer> heights) {
		

		int max = (int) Collections.max(heights);
		
		int arrowHeight = max;
		int counter = 0;
		
		while (!heights.isEmpty()) {
			
			max = (int) Collections.max(heights);
			arrowHeight = max;
			
		for (int i=0; i<heights.size(); i++) {
			
			if (heights.get(i) == arrowHeight) {
				heights.remove(i);
				arrowHeight--;
				i--;
			
			}
			
		}
		
		counter++;
		
		}
		
		return counter;
		

	}
	
	public static void fileWriter(ArrayList<Integer> sols) throws IOException {
		File fout = new File("testBalloons_solution.txt");
		FileOutputStream fos = new FileOutputStream(fout);
	 
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
	 
		for (int i = 0; i < sols.size(); i++) {
			bw.write("" + sols.get(i));
			bw.newLine();
		}
	 
		bw.close();
	}
	
	
}

