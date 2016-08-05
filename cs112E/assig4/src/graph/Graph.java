package graph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


//  represents a weighted undirected graph

public class Graph {
	Vertex[ ] adjLists;   // array of all vertices in the graph

	public Graph(String file) throws FileNotFoundException {
		Scanner sc = new Scanner(new File(file));

		// read number of vertices
		adjLists = new Vertex[sc.nextInt()];

		// read vertex names & create vertices
		for (int v=0; v < adjLists.length; v++) {
			adjLists[v] = new Vertex(sc.next(), null);
		}

		// read edges
		while (sc.hasNext()) {
			// read vertex names and translate to vertex numbers
			int v1 = indexForName(sc.next());
			int v2 = indexForName(sc.next());
			int weight = sc.nextInt( );

			// add v2 to front of v1's adjacency list and
			// add v1 to front of v2's adjacency list
			adjLists[v1].adjList = new AdjacencyNode(v2, adjLists[v1].adjList, weight);
			adjLists[v2].adjList = new AdjacencyNode(v1, adjLists[v2].adjList, weight);
		}
		sc.close( );
	}

	int indexForName(String name) {
		for (int v=0; v < adjLists.length; v++) {
			if (adjLists[v].name.equals(name)) {
				return v;
			}
		}
		return -1;
	}	
	
	// returns the number of vertices in the graph 
	public int numberOfVertices( ){
		return adjLists.length;
	}

	// print a summary of the graph
	public void summarize( ){
		for(int j = 0; j<numberOfVertices( ); j++){
			Vertex vj = adjLists[j];
			System.out.print(vj.name+": ");
			for(AdjacencyNode e = vj.adjList; e != null; e = e.next){
				System.out.print(adjLists[e.vertexNum].name+" "+e.weight+",  ");
			}
			System.out.println();
		}
	}
	
	public int shortestPath(String nameFrom, String nameTo){
		
		//makes the record where everything will be stored.
		////////////////////////////////////////
		SPRecord[] tree = new SPRecord[numberOfVertices()];
		
		//gets the first node of the graph and updates its neighbors
		////////////////////////////////////////
		tree[indexForName(nameFrom)] = new SPRecord(true, indexForName(nameFrom), 0);		
		updateNeighbors(indexForName(nameFrom), tree);		

		//helper method will keep loop running until the end is in the tree
		////////////////////////////////////////
		while(! isNodeInTree(nameTo, tree))
		{
			updateNeighbors(smallestNotInTree(tree),tree);
		}
	
		return tree[indexForName(nameTo)].distance;	
	}
	
	private void updateNeighbors(int vertexIndex, SPRecord [] spRecords){
		for(AdjacencyNode nbr = adjLists[vertexIndex].adjList; nbr != null; nbr = nbr.next){
			SPRecord nSPR = spRecords[nbr.vertexNum];
			if (nSPR == null){
				spRecords[nbr.vertexNum]= new SPRecord(false, vertexIndex, nbr.weight+spRecords[vertexIndex].distance);
			} else if (! nSPR.inTree){
				int altDistance = nbr.weight + spRecords[vertexIndex].distance;
				if (altDistance < nSPR.distance){
					spRecords[nbr.vertexNum].distance = altDistance;
					spRecords[nbr.vertexNum].link = vertexIndex;
				}

			}
		}
	}
	
	//helper method to run my loop
	////////////////////////////////////////
	private boolean isNodeInTree(String end, SPRecord[] spr)
	{
		if(spr[indexForName(end)] == null)
		{
			return false;
		}
		
		return spr[indexForName(end)].inTree;
	}
	
	
	//helper method finds the index of the smallest distance
	//that isn't in the tree, returns it and sets the .inTree to true
	////////////////////////////////////////
	private int smallestNotInTree(SPRecord[] tree){
		int smallest = -1;
		for(int i = 0; i < adjLists.length; i++)
		{
			//check so I don't throw a null pointer exception
			////////////////////////////////////////
			if(tree[i] == null)
			{
				continue;
			}
			else if(tree[i].inTree == false)
			{
				if(smallest == -1 && tree[i].distance != 0)
				{
					smallest = i;
				}
				else if(tree[smallest].distance > tree[i].distance && tree[i].distance != 0)
				{
					smallest = i;
				}
			}
		}
		//adds the smallest distance to the tree
		////////////////////////////////////////
		tree[smallest].inTree = true;
		return smallest;
	}
	
	public static void main(String[] args) throws FileNotFoundException {
	
		Graph g =  new Graph("graph.txt");
		Graph g2 =  new Graph("smallGraph.txt");
//		g.summarize( );
		System.out.println(g2.shortestPath("AA", "BBB"));
		System.out.println(g2.shortestPath("AA", "Cat"));
		System.out.println(g2.shortestPath("AA", "Dog"));
		System.out.println(g2.shortestPath("Dog", "BBB"));
		System.out.println(g.shortestPath("Alex", "Taylor"));
		System.out.println(g.shortestPath("Alex", "Anna"));
//		System.out.println(g.shortestPath("Alex", "Jackie"));
//		System.out.println(g.shortestPath("Alex", "Bobby"));		
//		System.out.println(g.shortestPath("Taylor", "Sid"));
//		System.out.println(g.shortestPath("Taylor", "Anna"));
//		System.out.println(g.shortestPath("Sid", "Alex"));
//		System.out.println(g.shortestPath("Sid", "Edith"));
}
	
}