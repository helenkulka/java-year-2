package Tables;

import Tables.Chaining.*;
import Tables.Open_Addressing.*;
import java.io.*;
import java.util.*;


//An assignment to compare the efficiencies between chaining and open address tabling

public class main {

    /**
     * Calculate 2^w
     */
    public static int power2(int w) {
        return (int) Math.pow(2, w);
    }

    /**
     * Uniformly generate a random integer between min and max, excluding both
     */
    public static int generateRandom(int min, int max, int seed) {
        Random generator = new Random();
        //if the seed is equal or above 0, we use the input seed, otherwise not.
        if (seed >= 0) {
            generator.setSeed(seed);
        }
        int i = generator.nextInt(max - min - 1);
        return i + min + 1;
    }

    /**
     * export CSV file
     */
    public static void generateCSVOutputFile(String filePathName, ArrayList<Double> alphaList, ArrayList<Double> avColListChain, ArrayList<Double> avColListProbe) {
        File file = new File(filePathName);
        FileWriter fw;
        try {
            fw = new FileWriter(file);
            int len = alphaList.size();
            fw.append("Alpha");
            for (int i = 0; i < len; i++) {
                fw.append("," + alphaList.get(i));
            }
            fw.append('\n');
            fw.append("Chain");
            for (int i = 0; i < len; i++) {
                fw.append("," + avColListChain.get(i));
            }
            fw.append('\n');
            fw.append("Open Addressing");
            for (int i = 0; i < len; i++) {
                fw.append(", " + avColListProbe.get(i));
            }
            fw.append('\n');
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        /*===========PART 1 : Experimenting with n===================*/
        //Initializing the three arraylists that will go into the output 
        ArrayList<Double> alphaList = new ArrayList<Double>();
        ArrayList<Double> avColListChain = new ArrayList<Double>();
        ArrayList<Double> avColListProbe = new ArrayList<Double>();

        //Keys to insert into both hash tables
        int[] keysToInsert = {164, 127, 481, 132, 467, 160, 205, 186, 107, 179,
            955, 533, 858, 906, 207, 810, 110, 159, 484, 62, 387, 436, 761, 507,
            832, 881, 181, 784, 84, 133, 458, 36};

        //values of n to test for in the experiment
        int[] nList = {2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30, 32};
        //value of w to use for the experiment on n
        int w = 10;

        for (int n : nList) {

            //initializing two hash tables with a seed
            Chaining MyChainTable = new Chaining(w, 137);
            Open_Addressing MyProbeTable = new Open_Addressing(w, 137);

            /*Use the hash tables to compute the average number of 
                        collisions over keys keysToInsert, for each value of n. 
                        The format of the three arraylists to fillis as follows:
                        
                        alphaList = arraylist of all tested alphas 
                                   (corresponding to each tested n)
                        avColListChain = average number of collisions for each
                                         Chain experiment 
                                         (make sure the order matches alphaList)
                        avColListProbe =  average number of collisions for each
                                         Linear Probe experiment
                                           (make sure the order matches)
                        The CSV file will output the result which you can visualize
             */ 
            double colChain = 0;
            double colAddress = 0;

            for (int i=0; i<n; i++) {
            	
            	 colChain += MyChainTable.insertKey(keysToInsert[i]);
            	 colAddress += MyProbeTable.insertKey(keysToInsert[i]);
            	
            }
            avColListChain.add(colChain/n);
            avColListProbe.add(colAddress/n);
           
         double mA = MyChainTable.m;
       	 double alph = n/mA;
       	 alphaList.add(alph);
       	 
        }

        generateCSVOutputFile("n_comparison.csv", alphaList, avColListChain, avColListProbe);

        /*===========    PART 2 : Test removeKey  ===================*/
 /* In this exercise, you apply your removeKey method on an example.
        Make sure you use the same seed, 137, as you did in part 1. You will
        be penalized if you don't use the same seed.
         */
        //Please not the output CSV will be slightly wrong; ignore the labels.
        ArrayList<Double> removeCollisions = new ArrayList<Double>();
        ArrayList<Double> removeIndex = new ArrayList<Double>();
        int[] keysToRemove = {6, 8, 164, 180, 127, 3, 481, 132, 4, 467, 5, 160,
            205, 186, 107, 179};

       Open_Addressing probeTable = new Open_Addressing(w, 137);
       
       for (int i=0; i<keysToRemove.length; i++) {
    	 probeTable.insertKey(keysToRemove[i]);
       }
       
       
       for (int i=0; i<keysToRemove.length; i++) {
    	  removeCollisions.add((double) probeTable.removeKey(keysToRemove[i]));
    	  removeIndex.add((double) i);
       }
       
        generateCSVOutputFile("remove_collisions.csv", removeIndex, removeCollisions, removeCollisions);

        /*===========PART 3 : Experimenting with w===================*/

 /*In this exercise, the hash tables are random with no seed. You choose 
                values for the constant, then vary w and observe your results.
         */
        //generating random hash tables with no seed can be done by sending -1
        //as the seed. You can read the generateRandom method for detail.
        //randomNumber = generateRandom(0,55,-1);
        //Chaining MyChainTable = new Chaining(w, -1);
        //Open_Addressing MyProbeTable = new Open_Addressing(w, -1);
        //Lists to fill for the output CSV, exactly the same as in Task 1.
        ArrayList<Double> alphaList2 = new ArrayList<Double>();
        ArrayList<Double> avColListChain2 = new ArrayList<Double>();
        ArrayList<Double> avColListProbe2 = new ArrayList<Double>();

        int[] valuesofW = {5,8,10,15,20};
        
        
        for (int testW : valuesofW) {
 
        	double chainAvg = 0;
        	double probeAvg = 0;
        	double alpha = 0;
        	
        	
        	//going within 10 tries
        	
        	for (int j=0; j<10; j++) {
        		
        		double ChainCol = 0;
            	double ProbeCol = 0;
           
        		Chaining testChain = new Chaining(testW, -1);
            	Open_Addressing testProbe = new Open_Addressing(testW, -1);
        	
        		ArrayList<Integer> randomKeys = new ArrayList<Integer>();
            	
            	while (randomKeys.size() < 30) {
            		int r = generateRandom(0, 500, -1);
            		if (!randomKeys.contains(r)) {
            			randomKeys.add(r);
            		}
            	}
            	alpha = randomKeys.size()/testChain.m;
        	
    
        		for (int k: randomKeys) {
        		ChainCol += testChain.insertKey(k);
        		ProbeCol += testProbe.insertKey(k);

        		}
        		
        	chainAvg += ChainCol/randomKeys.size();
        	probeAvg += ProbeCol/randomKeys.size();
        	

       
        }
        	
        avColListChain2.add(chainAvg/10);
        avColListProbe2.add(probeAvg/10);
        alphaList2.add(alpha);
        
        	
        }
        
        
        generateCSVOutputFile("w_comparison.csv", alphaList2, avColListChain2, avColListProbe2);

    }

}
