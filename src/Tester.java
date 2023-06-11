import java.io.FileNotFoundException;

/**
 * This Tester Class is for testing the Graph data structure and GraphFrame GUI. It is set up to
 * launch the basic GUI.
 * 
 * @author wyattcombs
 *
 */

public class Tester {	public static void main(String[] args) throws FileNotFoundException {	new Tester();	}

	public Tester() throws FileNotFoundException {
		Graph gTest = new Graph("MapInformation-1.txt");
		GraphFrame test = new GraphFrame(gTest);
		System.out.println(test.map);
	}

}
