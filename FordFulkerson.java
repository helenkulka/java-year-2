import java.io.*;
import java.util.*;


public class FordFulkerson {

	
	public static ArrayList<Integer> pathDFS(Integer source, Integer destination, WGraph graph){
		ArrayList<Integer> Stack = new ArrayList<Integer>();
		ArrayList<Integer> returnStack = new ArrayList<Integer>();
		
		boolean[] visited = new boolean[graph.getNbNodes()];
		
		ArrayList<ArrayList<Integer>> p = new ArrayList<ArrayList<Integer>>();
		for(int i=0; i<graph.getNbNodes(); i++) {
			p.add(new ArrayList<Integer>());
		}
		
		visited[source] = true;
		returnStack.add(source);
		
		while(!returnStack.isEmpty()) {
			
			
			int current = returnStack.remove(Stack.size());
			
			if (current == destination) {
				break;
			}
			
			ArrayList<Integer> children = new ArrayList<Integer>();
			
			for(Edge e: graph.getEdges()) {
				if(e.nodes[0] == current) {
					children.add(e.nodes[1]);
				}
			}
			
			for(int c : children) {
				p.get(c).add(current);
				
				if(!visited[c]) {
					visited[c] = true;
					returnStack.add(c);
				}	
			}	
		}
		
		
		if(visited[destination]) {
		
			int node = destination;
			Stack.add(node);
			
			while(node != source) {
				
				Stack.add(0,p.get(node).get(0));
				node = p.get(node).get(0);
			}
			return Stack;
		}
			return Stack;
	}
	
	
	public static void fordfulkerson(Integer source, Integer destination, WGraph graph, String filePath){
		String answer="";
		String myMcGillID = "0000000000"; //Please initialize this variable with your McGill ID
		int maxFlow = 0;
		
		float bottleneck1 = Float.MAX_VALUE;
		int bottleneck = (int) bottleneck1;
		WGraph residualGraph = new WGraph(graph);
		
		
		WGraph flow = new WGraph(graph);
		for(Edge e : flow.getEdges()) {
			e.weight = 0;
		}
		
		ArrayList<Integer> path = pathDFS(source, destination, residualGraph);
		
		if (source == destination) {
			System.out.println(-1);
			
		}
		
		else {
			
		while(!path.isEmpty()) {
			
			for (int i=0; i<path.size()-1; i++) {
			
				if (residualGraph.getEdge(path.get(i), path.get(i+1)).weight < bottleneck) {
					bottleneck = residualGraph.getEdge(path.get(i), path.get(i+1)).weight;
				}
			}
			
			
			for(int i = 0; i<path.size()-1; i++) {
				
				
				if(flow.getEdge(path.get(i), path.get(i+1)) != null) {
					flow.getEdge(path.get(i), path.get(i+1)).weight+=bottleneck;
				}
				
				else if(flow.getEdge(path.get(i+1), path.get(i)) !=null) {
					flow.getEdge(path.get(i+1), path.get(i)).weight-=bottleneck;
				}
				
			}
			
			residualGraph = new WGraph();
			
			ArrayList<Edge> front = new ArrayList<Edge>();
			ArrayList<Edge> back = new ArrayList<Edge>();
			
			for(Edge e : graph.getEdges()) {
				int capacity = e.weight;
				int w = flow.getEdge(e.nodes[0], e.nodes[1]).weight;
				
				if (w > 0) {
					back.add(new Edge(e.nodes[1], e.nodes[0], capacity));
				}
				if(w < capacity) {
					front.add(new Edge(e.nodes[0], e.nodes[1], capacity-w));
				}
			}
			
			for(Edge e : back) {
				if(e.weight != 0) {
				residualGraph.addEdge(e);
				}
			}
			
			for(Edge e : front) {
				
				if(e.weight == 0) {
					residualGraph.getEdge(e.nodes[0], e.nodes[1]).weight += e.weight;
				}
				else {
					if (residualGraph.getEdge(e.nodes[0], e.nodes[1]) == null) {
						residualGraph.addEdge(e);
					}
				}
			}
			
		
			path = pathDFS(source, destination, residualGraph);
			
		}
		
		
		for( Edge edge : flow.getEdges()) {
			if(edge.nodes[0] == source) {
				maxFlow = maxFlow + edge.weight;
			}
		}
		}

		graph = flow;

		answer += maxFlow + "\n" + graph.toString();	
		writeAnswer(filePath+myMcGillID+".txt",answer);
		System.out.println(answer);
	}
	
	
	public static void writeAnswer(String path, String line){
		BufferedReader br = null;
		File file = new File(path);
		// if file doesnt exists, then create it
		
		try {
		if (!file.exists()) {
			file.createNewFile();
		}
		FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(line+"\n");	
		bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	 public static void main(String[] args){
		 String file = args[0];
		 File f = new File(file);
		 WGraph g = new WGraph(file);
		 fordfulkerson(g.getSource(),g.getDestination(),g,f.getAbsolutePath().replace(".txt",""));
	 }
}
