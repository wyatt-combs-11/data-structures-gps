import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * This Graph class is a data structure built to help aid in creating a basic GPS based off file
 * input. The node data is stored as vertices and the edge data is stored as edges. The Graph 
 * object has a property that has a reference to the first Vertex as well as three static 
 * properties to aid in user choices for the GPS.
 * 
 * @author wyattcombs
 *
 */
public class Graph {
	//=================================================================== Properties
	public static Vertex vertices;
	public static boolean useDistCost;
	public static boolean useLaneCost;
	public static boolean returnAddress;
	private int size;
	
	//=================================================================== Constructors
	//-- Workhorse Constructor
	/**
	 * Creates a new Graph object.
	 * 
	 * @param filename The name of the file being scanned
	 * @throws FileNotFoundException
	 */
	public Graph(String filename) throws FileNotFoundException {
		clear();
		addData(filename);
	}
	
	//=================================================================== Methods
	/** Clears the Graph object; used for constructor. */
	public void clear() {
		size = 0;
		vertices = null;
		useDistCost = false;
		useLaneCost = false;
		returnAddress = false;
	}
	
	/**
	 * Populates the Graph object with the Vertices and Edges. Reads in a file and scans line
	 * by line with certain flags to add either Vertex or Edge with following information on
	 * same line. Scanning ends when all Edge information has been used.
	 * 
	 * @param filename The name of the file being scanned
	 * @throws FileNotFoundException
	 */
	public void addData(String filename) throws FileNotFoundException {
		String line = "";
		try(Scanner fin = new Scanner(new File(filename))) {
			while(fin.hasNext()) {
				line = fin.nextLine();
				if(line.contains("<Nodes>")) {
					break;
				}
			}
			fin.nextLine();
			while(fin.hasNext()) {
				line = fin.nextLine();
				if(line.contains("</Nodes>")) {
					break;
				}
				addVertex(line);
			}
			while(fin.hasNext()) {
				line = fin.nextLine();
				if(line.contains("<Edges>")) {
					break;
				}
			}
			fin.nextLine();
			while(fin.hasNext()) {
				line = fin.nextLine();
				if(line.contains("</Edges>")) {
					break;
				}
				addEdge(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Adds a Vertex based off file input after checking that it does not currently exist.
	 * 
	 * @param line The current line read in the file
	 */
	private void addVertex(String line) {
		// Check if vertex exists
		if(findVertex(line.substring(0, 1)) != null) {
			return;
		}
		
		if(vertices == null) {
			vertices = new Vertex(line);
		} else {
			vertices.add(new Vertex(line));
		}
		size++;
	}

	/**
	 * Creates an Edge from file input and adds it to the source Vertex.
	 * 
	 * @param line The current line read in the file
	 */
	private void addEdge(String line) {
		String[] parts = line.split("\t");
		Vertex tmp = findVertex(parts[0]);
		Edge tmpEdge = new Edge(
				findVertex(parts[1]),
				Integer.parseInt(parts[2]), 
				Integer.parseInt(parts[3]),
				Integer.parseInt(parts[4])
				);
		if(tmp.getEdges() == null) {
			tmp.setEdges(tmpEdge);
		} else {
			tmp.getEdges().addEdge(tmpEdge);
		}
	}
	
	/**
	 * Finds the reference to the Vertex in question and returns it or null if Vertex can 
	 * not be found. Searches for Vertex through a symbol.
	 * 
	 * @param symbol The symbol of the Vertex in question
	 * @return the Vertex reference if found
	 */
	private Vertex findVertex(String symbol) {
		Vertex tmp = vertices;
		while(tmp != null) {
			if(tmp.getSymbol().equals(symbol)) {
				return tmp;
			}
			tmp = tmp.getNextVert();
		}
		
		return null;
	}
	
	/**
	 * Finds the reference to the Vertex in question and returns it or null if Vertex can 
	 * not be found. Searches for Vertex through an address.
	 * 
	 * @param address The address of the Vertex in question
	 * @return the Vertex reference if found
	 */
	private Vertex findVertexAddress(String address) {
		Vertex tmp = vertices;
		while(tmp != null) {
			if(tmp.getAddress().equals(address)) {
				return tmp;
			}
			tmp = tmp.getNextVert();
		}
		
		return null;
	}
	
	/**
	 * Finds the shortest path inside the Graph data structure from Vertex start
	 * to Vertex end.
	 * 
	 * @param start The symbol or address of the beginning Vertex
	 * @param end The symbol or address of the ending Vertex
	 * @return the shortest path as a Path object
	 */
	public Path findShortestPath(String start, String end) {
		Vertex beg = !returnAddress ? findVertex(start): findVertexAddress(start);
		Vertex goal = !returnAddress ? findVertex(end): findVertexAddress(end);
		
		return Dijkstra.shortestPath(this, beg, goal);
	}

	/**
	 * Finds all possible paths inside the Graph data structure from Vertex start
	 * to Vertex end.
	 * 
	 * @param start The symbol or address of the beginning Vertex
	 * @param end The symbol or address of the ending Vertex
	 * @return all possible paths as a LinkedList of Path objects
	 */
	public LinkedList<Path> findAllPaths(String start, String end) {
		Vertex beg = !returnAddress ? findVertex(start): findVertexAddress(start);
		Vertex goal = !returnAddress ? findVertex(end): findVertexAddress(end);
		
		return Dijkstra.possiblePaths(this, beg, goal);
	}
	
	/**
	 * Creates an array of all Vertex addresses/symbols based off current user choice.
	 * 
	 * @return an array of all symbols or addresses of the vertices
	 */
	public String[] toArray() {
		return Graph.returnAddress ? toSymbolsArray(): toAddressArray();
	}
	
	/**
	 * Creates an array of all Vertex symbols.
	 * 
	 * @return an array of all symbols of the vertices
	 */
	public String[] toSymbolsArray() {
		String[] ret = new String[size];
		Vertex tmp = vertices;
		for(int i = 0; i < ret.length; i++) {
			ret[i] = tmp.getSymbol();
			tmp = tmp.getNextVert();
		}
		
		return ret;
	}
	
	/**
	 * Creates an array of all Vertex addresses.
	 * 
	 * @return an array of all addresses of the vertices
	 */
	public String[] toAddressArray() {
		String[] ret = new String[size];
		Vertex tmp = vertices;
		for(int i = 0; i < ret.length; i++) {
			ret[i] = tmp.getAddress();
			tmp = tmp.getNextVert();
		}
		
		return ret;
	}
	
	@Override
	public String toString() {
		String ret = "";
		Vertex tmp = vertices;
		while(tmp != null) {
			ret += tmp.toString() + "\n";
			tmp = tmp.getNextVert();
		}
		
		return ret;
	}
	
	//=================================================================== Class Testing
	public static void main(String[] args) throws FileNotFoundException {
		// Test Graph object below //
		
		Graph test = new Graph("MapInformation-1.txt");
		System.out.println(test);

	}
}
