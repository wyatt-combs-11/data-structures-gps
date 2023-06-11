/**
 * This Vertex class is meant to be a property of a Graph object. A Vertex is a part of the
 * Graph data structure which has the symbol and address of each location. Each Vertex also has 
 * Edges and a reference to the next Vertex if there is one.
 * 
 * @author wyattcombs
 *
 */

public class Vertex {
	//=================================================================== Properties
	private String symbol;
	private String address;
	private Vertex nextVert;
	private Edge edges;
	public static int vertices = 0;
	
	//=================================================================== Constructors
	//-- Empty Constructor
	/** Creates an empty Vertex. */
	public Vertex() {
		clear();
	}
	
	/**
	 * Creates a Vertex with its symbol and address.
	 * 
	 * @param line A scanned line from a file
	 */
	public Vertex(String line) {
		this(line.split("\t"));
	}
	
	/**
	 * Creates a Vertex with its symbol and address.
	 * 
	 * @param parts A String array of the symbol and address
	 */
	public Vertex(String[] parts) {
		this(parts[0], parts[1]);
	}
	
	//-- Workhorse Constructor
	/**
	 * Creates a Vertex with its symbol and address.
	 * 
	 * @param symbol The symbol of the Vertex
	 * @param address The address of the Vertex
	 */
	public Vertex(String symbol, String address) {
		this();
		setSymbol(symbol);
		setAddress(address);
	}
	
	//=================================================================== Methods
	/** Clears the vertex properties. Mostly to help constructors look cleaner. */
	public void clear() {
		setSymbol("");
		setAddress("");
		setNextVert(null);
		setEdges(null);
		vertices = 0;
	}
	
	/**
	 * Adds a new Vertex.
	 * 
	 * @param vert The Vertex to be added
	 */
	public void add(Vertex vert) {
		if(nextVert == null) {
			nextVert = vert;
		} else {
			nextVert.add(vert);
		}
	}
	
	/**
	 * Returns the symbol or address of the Vertex.
	 * 
	 * @return the Vertex symbol or address
	 */
	public String getReturnAddress() {
		return Graph.returnAddress ? symbol : address;
	}
	
	@Override
	public String toString() {
		String ret = getReturnAddress() + " - Edges: ";
		Edge tmp = edges;
		while(tmp != null) {
			ret += tmp.toString() + ", ";
			tmp = tmp.getNextEdge();
		}
		
		return  ret.substring(0, ret.length()-2);
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Vertex))	return false;
		Vertex v = (Vertex) obj;
		
		return toString().equals(v.toString());
	}

	//=================================================================== Getters / Setters
	public String getSymbol() 					{	return symbol;				}
	public String getAddress() 					{	return address;				}
	public Vertex getNextVert() 				{	return nextVert;			}
	public Edge getEdges() 						{	return edges;				}

	public void setSymbol(String symbol) 		{	this.symbol = symbol;		}
	public void setAddress(String address) 		{	this.address = address;		}
	public void setNextVert(Vertex nextVert)	{	this.nextVert = nextVert;	}
	public void setEdges(Edge edges) 			{	this.edges = edges;			}
}
