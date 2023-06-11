/**
 * This Edge class is meant to be a property of a Vertex object. It is a part of the 
 * Graph data structure which has the path the Vertex to a different Vertex. An Edge
 * has the destination Vertex with the associated timeCost, distanceCost, and laneCost
 * as well as a reference to the next Edge if there is one.
 * 
 * @author wyattcombs
 *
 */

public class Edge {
	//=================================================================== Properties
	private Vertex destination;
	private int timeCost;
	private int distanceCost;
	private int laneCost;
	private Edge nextEdge;
	
	//=================================================================== Constructors
	/** Creates an empty Edge. */
	//-- Empty Constructor
	public Edge() {
		clear();
	}

	/**
	 * Creates an Edge with its destination Vertex, timeCost, distanceCost and laneCost.
	 * 
	 * @param destination The destination Vertex
	 * @param timeCost The time cost of the Edge
	 * @param distanceCost The distance cost of the Edge
	 * @param lanes The number of lanes the Edge has
	 */
	public Edge(Vertex destination, int timeCost, int distanceCost, int lanes) {
		this();
		setDestination(destination);
		setTimeCost(timeCost);
		setDistanceCost(distanceCost);
		setLaneCost(lanes * distanceCost);
	}
	//=================================================================== Methods
	/** Clears the Edge properties. */
	public void clear() {
		setDestination(null);
		setTimeCost(0);
		setDistanceCost(0);
		setLaneCost(0);
		setNextEdge(null);
	}
	
	/**
	 * Adds a new Edge.
	 * 
	 * @param edge The Edge to be added
	 */
	public void addEdge(Edge edge) {
		if(nextEdge == null)
			nextEdge = edge;
		else
			nextEdge.addEdge(edge);
	}
	
	/**
	 * Returns the specified cost.
	 * 
	 * @return The laneCost, distanceCost, or timeCost
	 */
	public int getCost() {
		return Graph.useLaneCost ? laneCost: Graph.useDistCost ? distanceCost: timeCost;
		
	}
	
	@Override
	public String toString() {
		return "(" + destination.getReturnAddress() + ", " + getCost() + ")";
	}

	//=================================================================== Getters / Setters
	public Vertex getDestination() 					{	return destination;					}
	public int getTimeCost() 						{	return timeCost;					}
	public int getDistanceCost() 					{	return distanceCost;				}
	public int getLaneCost()						{	return laneCost;					}
	public Edge getNextEdge()						{	return nextEdge;					}

	public void setDestination(Vertex destination) 	{	this.destination = destination;		}
	public void setTimeCost(int timeCost) 			{	this.timeCost = timeCost;			}
	public void setDistanceCost(int distanceCost)	{	this.distanceCost = distanceCost;	}
	public void setNextEdge(Edge edge)				{	this.nextEdge = edge;				}
	public void setLaneCost(int laneCost)			{	this.laneCost = laneCost;			}
	
	

}
