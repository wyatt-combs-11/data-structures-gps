import java.util.LinkedList;

/**
 * This Dijkstra class is part of the Graph data structure. This class is based off of the
 * Dijkstra algorithm and is used to calculate the shortest path from one Vertex to another
 * as well as all possible paths found during the Dijkstra algorithm's run. There are two static
 * methods to calculate these and a totalCost property for the most recent shortest Path cost.
 * 
 * @author wyattcombs
 *
 */

public class Dijkstra {
	//=================================================================== Properties
	public static int totalCost;
	
	//=================================================================== Methods
	/**
	 * Returns the shortest path from one Vertex to another. Null is returned if no paths found.
	 * 
	 * @param map The Graph data structure for which this algorithms runs through
	 * @param start The start Vertex
	 * @param end The end Vertex
	 * @return The shortest path as a Path object
	 */
	public static Path shortestPath(Graph map, Vertex start, Vertex end) {
		HeapPriorityQ<Path> pq = new HeapPriorityQ<>();
		pq.add(new Path(start, start.getSymbol(), 0));
		LinkedList<Vertex> visited = new LinkedList<>();
		
		while(!pq.isEmpty()) {
			Path curr = pq.remove();
			visited.add(curr.getVertex());
			
			if(curr.getVertex().equals(end)) {
				totalCost = curr.getCost();
				return curr;
			} else {
				Vertex currVert = curr.getVertex();
				int currCost = curr.getCost();
				String currPath = curr.getPathStr();
				Edge edges = currVert.getEdges();
				
				while(edges != null) {
					Vertex nextVert = edges.getDestination();
					if(!visited.contains(nextVert)) {
						int nextCost = currCost + edges.getCost();
						String nextPath = currPath + nextVert.getSymbol();
						pq.add(new Path(nextVert, nextPath, nextCost));
					}
					edges = edges.getNextEdge();
				}
			}
		}
		
		return null;
	}
	
	/**
	 * Returns all possible paths found by the Dijkstra algorithm from one Vertex to another.
	 * Null is returned if no paths found.
	 * 
	 * @param map The Graph data structure for which this algorithms runs through
	 * @param start The start Vertex
	 * @param end The end Vertex
	 * @return All paths found as a LinkedList<Path> object
	 */
	public static LinkedList<Path> possiblePaths(Graph map, Vertex start, Vertex end) {
		HeapPriorityQ<Path> pq = new HeapPriorityQ<>();
		pq.add(new Path(start, start.getSymbol(), 0));
		LinkedList<Vertex> visited = new LinkedList<>();
		LinkedList<Path> ret = new LinkedList<>();
		
		while(!pq.isEmpty()) {
			Path curr = pq.remove();
			visited.add(curr.getVertex());
			
			if(curr.getVertex().equals(end)) {
				ret.add(curr);
			} else {
				Vertex currVert = curr.getVertex();
				int currCost = curr.getCost();
				String currPath = curr.getPathStr();
				Edge edges = currVert.getEdges();
				
				while(edges != null) {
					Vertex nextVert = edges.getDestination();
					if(!visited.contains(nextVert)) {
						int nextCost = currCost + edges.getCost();
						String nextPath = currPath + nextVert.getSymbol();
						pq.add(new Path(nextVert, nextPath, nextCost));
					}
					edges = edges.getNextEdge();
				}
			}
		}
		
		return ret;
	}
}
