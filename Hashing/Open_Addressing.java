package Hashing;

import static Hashing.main.*;

import java.util.Random;

public class Open_Addressing {

    public int m; // number of SLOTS AVAILABLE
    public int A; // the default random number
    int w;
    int r;
    public int[] Table;

    //Constructor for the class. sets up the data structure for you
    protected Open_Addressing(int w, int seed) {

        this.w = w;
        this.r = (int) (w - 1) / 2 + 1;
        this.m = power2(r);
        this.A = generateRandom((int) power2(w - 1), (int) power2(w), seed);
        this.Table = new int[m];
        //empty slots are initalized as -1, since all keys are positive
        for (int i = 0; i < m; i++) {
            Table[i] = -1;
        }

    }

    /**
     * Implements the hash function g(k)
     */
    public int probe(int key, int i) {
        int powerW = power2(w);
    	int chainFunc = (int) ((A*key)%powerW >> (w-r));
    	int powerR = power2(r);
    	int probeFunc = (int) ((chainFunc + i)%powerR);
        return probeFunc;
    }

    /**
     * Checks if slot n is empty
     */
    public boolean isSlotEmpty(int hashValue) {
        return Table[hashValue] == -1;
    }

    /**
     * Inserts key k into hash table. Returns the number of collisions
     * encountered
     */
    public int insertKey(int key) {
    	int i = 0;
    	int collisions = 0;
    	int hashValue = probe(key, i);
    	
    	for (int j=0; j<Table.length; j++) {
	    		if (isSlotEmpty(hashValue)) {
	    			Table[hashValue] = key;
	    			break;
	    		}
	    		else {
	    			collisions++;
	    			
	    			i = (i+1)%m;
	    			hashValue = probe(key, i);
	    		}
    	}
    	
    
        return collisions;
    }

    /**
     * Removes key k from hash table. Returns the number of collisions
     * encountered
     */
    public int removeKey(int key) {
 
       int collisions = 0;
       int j=0;
       
       while (j < m-1) {
    	   
    	  int hashCheck = probe(key, j);
    	  
    	  if (Table[hashCheck] == key) {
    		  Table[hashCheck] = -2;
    		  return collisions;
    	  }
    	  
    	  else if (Table[hashCheck] == -1) {
    		  collisions++;
    		  return collisions;
    	  }
    	 
    	  else {
    		  j = (j+1)%m;
    		  collisions++;
    	  }
       }
 
       
      return collisions;
       
      
    }

}
