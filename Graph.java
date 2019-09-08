
public class Graph {

	boolean[][] adjacency;
	int nbNodes;

	public Graph(int nb) {
		this.nbNodes = nb;
		this.adjacency = new boolean[nb][nb];
		for (int i = 0; i < nb; i++) {
			for (int j = 0; j < nb; j++) {
				this.adjacency[i][j] = false;
			}
		}
	}

	public void addEdge(int i, int j) {
		this.adjacency[i][j] = true;
		this.adjacency[j][i] = true;
	}

	public void removeEdge(int i, int j) {
		this.adjacency[i][j] = false;
		this.adjacency[j][i] = false;
	}

	public int nbEdges() {
		int counter = 0;
		for (int i = 0; i < adjacency.length; i++) {
			for (int j = i; j < adjacency[i].length; j++) {
				if (adjacency[i][j] == true) {
					counter++;
				}
			}

		}
		return counter; 
	}

	public boolean cycle(int start) {
		boolean[] nodesVisited = new boolean[this.nbNodes];
		boolean[][] edgesVisited = new boolean[this.nbNodes][this.nbNodes];

		for (int i = 0; i < this.nbNodes; i++) {
			for (int j = 0; j < this.nbNodes; j++) {
				edgesVisited[i][j] = false;
			}
		}
		for (int i = 0; i < this.nbNodes; i++) {
			if (i == start) {
				nodesVisited[i] = true;
			} else {
				nodesVisited[i] = false;
			}
		}
		depthFirstSearch(edgesVisited, nodesVisited, start, -1);
		if (nodesVisited[start] == true) {
			return true;
		} else {
			return false;
		}
	}

	public void depthFirstSearch(boolean[][] edgesVisited, boolean[] nodesVisited, int i, int from) {
		if (nodesVisited[i] == true) {
			nodesVisited[i] = false;
		} else {
			nodesVisited[i] = true;
			if (from != -1) {
				edgesVisited[i][from] = true;
				edgesVisited[from][i] = true;
			}
		}
		for (int j = 0; j < this.nbNodes; j++) {
			if (nodesVisited[j] != true && (this.adjacency[i][j] == true) && (!edgesVisited[i][j])
					&& (!edgesVisited[j][i]) && (j != i)) {
				depthFirstSearch(edgesVisited, nodesVisited, j, i);
			}
		}
	}
	public int shortestPath(int start, int end) {
		Boolean[][] n = new Boolean[nbNodes][nbNodes];

		for (int i = 0; i < nbNodes; i++) {
			for (int j = 0; j < nbNodes; j++) {
				n[i][j] = adjacency[i][j];
			}
		}

		int counter = 0;
		int[] check = new int[nbNodes * nbNodes];
		check[0] = start;

		int a = 0;
		int b = 0;
		int c = 1;

		while (counter != nbNodes + 1) {
			a = b;
			b = c;
			counter++;

			for (int i = a; i < b; i++) {
				for (int j = 0; j < nbNodes; j++) {
					if (n[check[i]][j] == true) {
						check[c] = j;
						c++;

						if (j == end) {
							return counter;
						}
					}
					n[j][check[i]] = false;
				}
			}
		}
		return counter;
	}

	
}
