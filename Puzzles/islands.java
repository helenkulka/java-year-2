/* an algorithm to find all blocks inhabited with items that are not next to another item - below/above,
either side/ diagonal */ 

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class islands {
	
	int rows = 0;
	int cols = 0;
	boolean visited[][];
	
	public static void main(String[] args) {
		
		String file = "testIslands.txt";
		ArrayList<Integer> sols = new ArrayList<Integer>();

		try {
	        Scanner f = new Scanner(new File(file));
	        int numbTrials = (int) Integer.parseInt(f.nextLine());
	        
	        
	        while (f.hasNext()){
	        	
	    
	        	String[] line = f.nextLine().split("\\s+");
	        	int numbRows=Integer.parseInt(line[0]);
	        	int numbCols = Integer.parseInt(line[1]);
	        	
	        	int [][] pixels = new int[numbRows][numbCols];
	        	
	        	
	        		for (int i=0; i<numbRows; i++) {
	        			String[] pixelLine = f.nextLine().split("");
	        			
	        			for (int j=0; j<pixelLine.length; j++) {
	        				char k;
	        				if (pixelLine[j].equals("-")) {
	        					k = '-';
	        				}
	        				else {
	        					k = '+';
	        				}
	        				pixels[i][j] = k;
	        			}
	        		}
	        		sols.add(location(pixels));
	        		
	        			
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
	
	static int location(int[][] map) {
		int ans = 0;												
		int row = map.length;									
		int col = map[0].length;	
		
		boolean visited[][] = new boolean[row][col];			
		
		for (int x = 0; x < row; x++) {
			for (int y = 0; y < col; y++) {
				if (map[x][y] == '-' && !visited[x][y]) {
					layOut(x,y, map, visited, row, col);
					ans++;
				}
			}
		}
		return ans;
	}
	
	static void layOut (int x, int y, int[][] map, boolean[][] visited, int rows, int cols) {
		
		if (y-1 >= 0 && !visited[x][y-1] && map[x][y-1] == '-') {				
			visited[x][y-1] = true;
			layOut(x, y-1, map, visited, rows, cols);
		}
		if (y+1 < cols && !visited[x][y+1] && map[x][y+1] == '-') {			
			visited[x][y+1] = true;
			layOut(x, y+1, map, visited, rows, cols);
		}
		if (x-1 >= 0 && !visited[x-1][y] && map[x-1][y] == '-') {				
			visited[x-1][y] = true;
			layOut(x-1, y, map, visited, rows, cols);
		}
		if (x+1 < rows && !visited[x+1][y] && map[x+1][y] == '-') {			
			visited[x+1][y] = true;
			layOut(x+1, y, map, visited, rows, cols);
		}
	}
	
	public static void fileWriter(ArrayList<Integer> sols) throws IOException {
		File fout = new File("testIslands_solution.txt");
		FileOutputStream fos = new FileOutputStream(fout);
	 
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
	 
		for (int i = 0; i < sols.size(); i++) {
			bw.write("" + sols.get(i));
			bw.newLine();
		}
	 
		bw.close();
	}
	
	
}