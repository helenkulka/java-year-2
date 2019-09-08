import java.util.*;

public class Kruskal{

    public static WGraph kruskal(WGraph g){
    	DisjointSets p = new DisjointSets(g.getNbNodes());
    	WGraph mst = new WGraph();

    	for (int i=0; i<g.listOfEdgesSorted().size(); i++) {
    		Edge check = g.listOfEdgesSorted().get(i);
    		if (IsSafe(p, check)) {
    			mst.addEdge(check);
    			p.union(check.nodes[0], check.nodes[1]);
    		}
    		
    	}
        
        return mst;
    }

    public static Boolean IsSafe(DisjointSets p, Edge e){
    	int par1 = p.find(e.nodes[0]);
    	int par2 = p.find(e.nodes[1]);
    	
    	if (par1 != par2) {	
    		return true;
    	}

       
        return false;
    
    }

    public static void main(String[] args){
        String file = args[0];
        WGraph g = new WGraph(file);
        WGraph t = kruskal(g);
        System.out.println(t);

   } 
}
