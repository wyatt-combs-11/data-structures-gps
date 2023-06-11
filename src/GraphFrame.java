import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

/**
 * This GraphFrame class is a basic GUI to allow for user interaction of the Graph data
 * structure. It allows for users to choose symbols or addresses of Vertices and which type
 * of cost(time, distance, or lane) to calculate the shortest path and possible paths from
 * the start Vertex to end Vertex.
 * 
 * The GUI has buttons for calculating the paths with their respective costs and to display
 * instructions(both in the same text box). There are listeners in place for the buttons and
 * radio button choices. This GraphFrame class extends the JFrame class.
 * 
 * @author wyattcombs
 *
 */

public class GraphFrame extends JFrame {
	//=================================================================== Properties
	// Window size constants
	private static final int FRAME_WIDTH = 775;
	private static final int FRAME_HEIGHT = 435;
	public Graph map;
	
	// Arrays of symbols and addresses
	private String[] symbolArr;
	private String[] addressArr;
	
	// Window Elements
	private JPanel panel;
	private JPanel leftPanel;
	private JPanel centerPanel;
	private JPanel topButtons;
	private JPanel rightPanel;
	private JPanel radioPanels;
	private JPanel radioPanel1;
	private JPanel radioPanel2;
	private JButton pathButton;
	private JButton instructions;
	private JLabel startTitle;
	private JLabel endTitle;
	private JScrollPane scrollPaths;
	private JTextArea pathInfo;
	private JComboBox<String> startLocation;
	private JComboBox<String> endLocation;
	private DefaultComboBoxModel<String> option1;
	private DefaultComboBoxModel<String> option2;
	private DefaultComboBoxModel<String> option3;
	private DefaultComboBoxModel<String> option4;
	private JLabel radioInfo;
	private ButtonGroup returnAddressGroup;
	private ButtonGroup useDistGroup;
	private JRadioButton symbols;
	private JRadioButton addresses;
	private JRadioButton timeCost;
	private JRadioButton distCost;
	private JRadioButton laneCost;

	//=================================================================== Constructors
	/**
	 * Creates a GUI based off the Graph data structure.
	 * 
	 * @param map The data structure to be used
	 */
	//-- Workhorse Constructor
	public GraphFrame(Graph map) {
		this.map = map;
		createComponents();
		
		setName("GPS V1.0");
		setBounds(350, 200, FRAME_WIDTH, FRAME_HEIGHT);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	//=================================================================== Methods
	/** Creates all components of the GUI. */
	private void createComponents() {
		// Create all elements to go inside panels with layouts
		pathButton = new JButton("Calculate Paths");
		instructions = new JButton("Instructions");
		startTitle = new JLabel("Choose Start Location:");
		endTitle = new JLabel("Choose End Location:");
		pathInfo = new JTextArea("Click Instructions", 10, 18);
		pathInfo.setEditable(false);
		scrollPaths = new JScrollPane(pathInfo);
		symbolArr = map.toSymbolsArray();
		addressArr = map.toAddressArray();
		option1 = new DefaultComboBoxModel<>(addressArr);
		option2 = new DefaultComboBoxModel<>(symbolArr);
		option3 = new DefaultComboBoxModel<>(addressArr);
		option4 = new DefaultComboBoxModel<>(symbolArr);
		startLocation = new JComboBox<>(Graph.returnAddress ? addressArr: symbolArr);
		endLocation = new JComboBox<>(Graph.returnAddress ? addressArr: symbolArr);
		
		radioInfo = new JLabel("Options:");
		returnAddressGroup = new ButtonGroup();
		symbols = new JRadioButton("Symbols");
		addresses = new JRadioButton("Addresses");
		useDistGroup = new ButtonGroup();
		timeCost = new JRadioButton("Time");
		distCost = new JRadioButton("Distance");
		laneCost = new JRadioButton("Lane");
		
		// Design GUI
		addGUIListeners();
		createPanels();
		setBordersAndLayouts();
		addElements();
		addPanels();
		customizeGUI();
		add(panel);
	}

	//=================== GUI Functionality Methods ========================
	/** Adds the necessary ActionListeners to the GUI. */
	private void addGUIListeners() {
		pathButton.addActionListener(new pathListener());
		instructions.addActionListener(new instructionsListener());
		timeCost.addActionListener(new useDistCostListener());
		distCost.addActionListener(new useDistCostListener());
		laneCost.addActionListener(new useDistCostListener());
		symbols.addActionListener(new returnAddressListener());
		addresses.addActionListener(new returnAddressListener());
	}
	
	/** Creates panels needed to design the GUI and incorporate all elements. */
	private void createPanels() {
		panel = new JPanel();
		leftPanel = new JPanel();
		centerPanel = new JPanel();
		topButtons = new JPanel();
		radioPanels = new JPanel();
		radioPanel1 = new JPanel();
		radioPanel2 = new JPanel();
		rightPanel = new JPanel();
	}
	
	//=================== GUI Design Methods ========================
	/** Sets borders and layouts for certain panels for design and readability. */
	private void setBordersAndLayouts() {
		panel.setLayout(new GridLayout(1, 3, 5, 5));
		panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		leftPanel.setLayout(new GridLayout(3, 1, 5, 5));
		leftPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		radioPanels.setLayout(new GridLayout(3, 1, 5, 5));
		radioPanel1.setLayout(new GridLayout(1, 2, 5, 5));
		radioPanels.setBorder(new LineBorder(Color.BLACK));
		radioPanel1.setBorder(new TitledBorder(new EtchedBorder(), "Location Type"));
		radioPanel2.setBorder(new TitledBorder(new EtchedBorder(), "Cost Type"));
		rightPanel.setLayout(new GridLayout(3, 1, 5, 5));
		rightPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
	}
	
	/** Adds all elements to GUI. */
	private void addElements() {
		leftPanel.add(startTitle);
		leftPanel.add(startLocation);
		startTitle.setHorizontalAlignment(JLabel.CENTER);
		startTitle.setVerticalAlignment(JLabel.BOTTOM);
		topButtons.add(pathButton);
		topButtons.add(instructions);
		centerPanel.add(topButtons);
		centerPanel.add(scrollPaths);
		returnAddressGroup.add(symbols);
		returnAddressGroup.add(addresses);
		radioPanel1.add(symbols);
		radioPanel1.add(addresses);
		useDistGroup.add(timeCost);
		useDistGroup.add(distCost);
		useDistGroup.add(laneCost);
		radioPanel2.add(timeCost);
		radioPanel2.add(distCost);
		radioPanel2.add(laneCost);
		rightPanel.add(endTitle);
		rightPanel.add(endLocation);
		endTitle.setHorizontalAlignment(JLabel.CENTER);
		endTitle.setVerticalAlignment(JLabel.BOTTOM);
	}
	
	/** Adds all panels to GUI. */
	private void addPanels() {
		panel.add(leftPanel);
		radioPanels.add(radioInfo);
		radioPanels.add(radioPanel1);
		radioPanels.add(radioPanel2);
		centerPanel.add(radioPanels);
		panel.add(centerPanel);
		panel.add(rightPanel);
	}

	/** Customizes panels, radio buttons, and labels for GUI. */
	private void customizeGUI() {
		// Create light gray panels
		Color c = Color.LIGHT_GRAY;
		centerPanel.setBackground(c);
		topButtons.setBackground(c);
		panel.setBackground(c);
		
		// Create gray panels
		c = Color.GRAY;
		leftPanel.setBackground(c);
		rightPanel.setBackground(c);
		
		// Button Group Info
		radioInfo.setHorizontalAlignment(JLabel.CENTER);
		radioInfo.setFont(new Font("Times New Roman", Font.ROMAN_BASELINE, 15));
		symbols.setSelected(true);
		timeCost.setSelected(true);
		
		// Format the titles over JComboBoxes
		startTitle.setForeground(Color.WHITE);
		endTitle.setForeground(Color.WHITE);
		startTitle.setFont(new Font("Times New Roman", Font.BOLD, 18));
		endTitle.setFont(new Font("Times New Roman", Font.BOLD, 18));
	}
	
	//=================== Listener Inner Classes ========================
	class pathListener implements ActionListener  {

		@Override
		public void actionPerformed(ActionEvent e) {
			String startChoice = (String) startLocation.getSelectedItem();
			String endChoice = (String) endLocation.getSelectedItem();
			
			StringBuilder ret = new StringBuilder();
			Path shortPath = map.findShortestPath(startChoice, endChoice);
			ret.append("Shortest Path:\n").append((shortPath == null) ? "No Path": shortPath.toString());
			ret.append("\n\n").append("Possible Paths:\n");
			
			LinkedList<Path> paths = map.findAllPaths(startChoice, endChoice);
			if(paths.isEmpty())
				ret.append("No Path\n");
			for(Path p: paths) {
				ret.append(p.toString()).append("\n");
			}
			
			pathInfo.setText(ret.toString());
		}
		
	}
	
	class useDistCostListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if(distCost.isSelected()) {
				Graph.useDistCost = true;
				Graph.useLaneCost = false;
			} else if(timeCost.isSelected()) {
				Graph.useDistCost = false;
				Graph.useLaneCost = false;
			} else
				Graph.useLaneCost = true;
		}
		
	}
	
	class returnAddressListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if(symbols.isSelected())
				Graph.returnAddress = false;
			else if(addresses.isSelected())
				Graph.returnAddress = true;
			
			startLocation.setModel(Graph.returnAddress ? option1: option2);
			endLocation.setModel(Graph.returnAddress ? option3: option4);
		}
		
	}
	
	class instructionsListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			pathInfo.setText(
					"Data Structures GPS:\n"
					+ "1. Choose symbols/addresses\n"
					+ "2. Choose path cost\n"
					+ "3. Calculate shortest path!"
					);
		}
		
	}
}
