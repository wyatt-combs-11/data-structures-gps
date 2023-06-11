/**
 * This Path class is part of the Graph data structure. A Path is the Edges and associated
 * costs taken to get from one Vertex to another. A Path has the current Vertex visited,
 * the path taken from the start Vertex, and the total cost of the Path. This Path class
 * implements the Comparable Interface.
 * 
 * @author wyattcombs
 *
 */

public class Path implements Comparable<Path> {
	//=================================================================== Properties
	private Vertex vertex;
	private String pathStr;
	private int cost;
	
	//=================================================================== Constructors
	/** Creates an empty Path. */
	//-- Empty Constructor
	public Path() {
		clear();
	}
	
	/**
	 * Creates a Path with its current visited Vertex, the path taken, and the total cost.
	 * 
	 * @param vertex The current visited Vertex
	 * @param pathStr The path of vertices taken
	 * @param cost The cost of the path
	 */
	//-- Workhorse Constructor
	public Path(Vertex vertex, String pathStr, int cost) {
		setVertex(vertex);
		setPathStr(pathStr);
		setCost(cost);
	}

	//=================================================================== Methods
	/** Clears the Path properties. */
	private void clear() {
		setVertex(null);
		setPathStr("");
		setCost(0);
	}
	
	@Override
	public int compareTo(Path other) {
		return cost - other.cost;
	}
	
	@Override
	public String toString() {
		return "Path: " + pathStr + ", " + (Graph.useLaneCost ? "Lane": Graph.useDistCost ? "Distance": "Time")
				+ " cost: " + cost;
	}

	//=================================================================== Getters / Setters
	public Vertex getVertex() 				{	return vertex;			}
	public String getPathStr() 				{	return pathStr;			}
	public int getCost() 					{	return cost;			}

	public void setVertex(Vertex vertex) 	{	this.vertex = vertex;	}
	public void setPathStr(String pathStr)	{	this.pathStr = pathStr;	}
	public void setCost(int cost) 			{	this.cost = cost;		}
}
