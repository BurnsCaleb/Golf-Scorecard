package view;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import model.Course;
import model.Hole;

/**
 * @author Caleb Burns
 * @version 1.0
 * @since 1.0
 */
// Create frame for adding a course
public class AddCourseFrame {
	private JFrame frame, addHoleFrame;
	private JFrame popUp = new JFrame();
	private JPanel titlePanel, namePanel, locationPanel, numHolesPanel, courseParPanel, buttonPanel;
	private JLabel nameLabel, locationLabel, holesLabel, parLabel, holeNumLabel, holeParLabel, distanceLabel, yardsLabel;
	private JLabel titleLabel = new JLabel();
	private JTextField nameText, locationText, parText, distanceText;
	private JButton back, submit;
	private JComboBox<Object> holes;
	
	private int numHoles;
	private int firstRound = 9;
	
	// Create fonts
	private Font headingFont = new Font("Calisto MT", Font.BOLD, 30);
	private Font holeFont = new Font("Calisto MT", Font.PLAIN, 20);
	private Font buttonFont = new Font("Candara", Font.BOLD, 25);
	private Font textFont = new Font("Georgia", Font.PLAIN, 22);
	
	// Create colors
	private Color darkest = new Color(58, 77, 57);
	private Color dark = new Color(79, 111, 82);
	private Color light = new Color(153, 176, 128);
	private Color clay = new Color(249, 181, 114);
	
	// Create borders
	private Border buttonBorder = BorderFactory.createLineBorder(clay);
	
	private int tempHoleNum = 1;
	
	private ArrayList<String> parList = new ArrayList<>();
	private ArrayList<String> distanceList = new ArrayList<>();
	
	private ArrayList<JComboBox<Integer>> parInput = new ArrayList<>();
	private ArrayList<JTextField> distanceInput = new ArrayList<>();
	
	public AddCourseFrame() {
		initialize();
	}
	
	public void initialize() {
		// Set layout
		frame = new JFrame();
		frame.setTitle("Golf Scorecard by Caleb Burns");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(600, 750);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setLayout(new GridLayout(0, 1));
		
		titleLabel.setText("Add Course");
		titleLabel.setForeground(clay);
		titleLabel.setFont(headingFont);
		titleLabel.setHorizontalAlignment(0);
		
		nameLabel = new JLabel("Name: ");
		nameLabel.setHorizontalAlignment(0);
		nameLabel.setFont(holeFont);
		nameLabel.setForeground(darkest);
		nameText = new JTextField();
		nameText.setBorder(buttonBorder);
		nameText.setBackground(dark);
		nameText.setForeground(clay);
		locationLabel = new JLabel("Location: ");
		locationLabel.setHorizontalAlignment(0);
		locationLabel.setFont(holeFont);
		locationLabel.setForeground(darkest);
		locationText = new JTextField();
		locationText.setBorder(buttonBorder);
		locationText.setBackground(dark);
		locationText.setForeground(clay);
		
		holesLabel = new JLabel("Holes: ");
		holesLabel.setHorizontalAlignment(0);
		holesLabel.setFont(holeFont);
		holesLabel.setForeground(darkest);
		
		Object[] choices = {9, 18};
		holes = new JComboBox<>(choices);
		holes.setBorder(buttonBorder);
		holes.setBackground(darkest);
		holes.setForeground(clay);
		
		parLabel = new JLabel("Par: ");
		parLabel.setHorizontalAlignment(0);
		parLabel.setFont(holeFont);
		parLabel.setForeground(darkest);
		parText = new JTextField();
		parText.setBorder(buttonBorder);
		parText.setBackground(dark);
		parText.setForeground(clay);
		
		
		// Panels for spacing
		titlePanel = new JPanel();
		titlePanel.setLayout(new FlowLayout());
		titlePanel.setBackground(darkest);
		namePanel = new JPanel();
		namePanel.setLayout(new GridLayout(1, 2));
		namePanel.setBackground(light);
		locationPanel = new JPanel();
		locationPanel.setLayout(new GridLayout(1, 2));
		locationPanel.setBackground(light);
		numHolesPanel = new JPanel();
		numHolesPanel.setLayout(new GridLayout(1, 2));
		numHolesPanel.setBackground(light);
		courseParPanel = new JPanel();
		courseParPanel.setLayout(new GridLayout(1, 2));
		courseParPanel.setBackground(light);
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1, 2));
		
		frame.add(titlePanel);
		titlePanel.add(titleLabel);	
		frame.add(namePanel);
		namePanel.add(nameLabel);
		namePanel.add(nameText);
		frame.add(locationPanel);
		locationPanel.add(locationLabel);
		locationPanel.add(locationText);
		frame.add(numHolesPanel);
		numHolesPanel.add(holesLabel);
		numHolesPanel.add(holes);
		frame.add(courseParPanel);
		courseParPanel.add(parLabel);
		courseParPanel.add(parText);
		frame.add(buttonPanel);
				
		// buttons
		submitClickListener submitClick = new submitClickListener();
		submit = new JButton("Add Holes");
		submit.setFont(buttonFont);
		submit.setBackground(darkest);
		submit.setForeground(clay);
		submit.setBorder(buttonBorder);
		submit.addActionListener(submitClick);
		
		backClickListener backClick = new backClickListener();
		back = new JButton("Back");
		back.setFont(buttonFont);
		back.setBackground(darkest);
		back.setForeground(clay);
		back.setBorder(buttonBorder);
		back.addActionListener(backClick);
		
		buttonPanel.add(back);
		buttonPanel.add(submit);
	}
	class submitClickListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// decide if 9 or 18 holes are needed
			if(holes.getSelectedIndex() == 0) {
				// only show 9 holes
				numHoles = 9;
			} else if(holes.getSelectedIndex() == 1) {
				// show 9 then button for back 9
				numHoles = 18;
			}
			// write to csv file based on hole info
			// make sure all info is entered
			if(nameText.getText().isEmpty() || locationText.getText().isEmpty() || parText.getText().isEmpty() || locationText.getText().contains(",") || nameText.getText().contains(",")) {
				JOptionPane.showMessageDialog(popUp, "Please enter all information without commas.");
			} else {
				try {
					Integer.parseInt(parText.getText());
					addHoles();
					frame.dispose();
				} catch(NumberFormatException b) {
					JOptionPane.showMessageDialog(popUp, "Please enter course par as a number.");
				}
			}
		}
	}
	
	class backClickListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			new MainMenuFrame();
			frame.dispose();
		}
	}
	
	public void addHoles() {
		addHoleFrame = new JFrame();
		JLabel titleLabel = new JLabel();
		JLabel blank1 = new JLabel();
		JLabel blank2 = new JLabel();
		JLabel blank3 = new JLabel();
		JLabel blank4 = new JLabel();
		JButton submitButton = new JButton();
		JButton backButton = new JButton();
		
		addHoleFrame.setTitle("Golf Scorecard by Caleb Burns");
		addHoleFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		addHoleFrame.setSize(600, 750);
		addHoleFrame.setVisible(true);
		addHoleFrame.setLocationRelativeTo(null);
		addHoleFrame.setLayout(new GridLayout(10, 6, 5, 5));
		addHoleFrame.getContentPane().setBackground(light);
		
		titleLabel.setText("Add Holes");
		titleLabel.setHorizontalAlignment(0);
		titleLabel.setFont(headingFont);
		titleLabel.setForeground(clay);
		

		if(numHoles == 18 && firstRound == 18) {
			tempHoleNum = 10;
		}
		for(int i = 1; i <= 9; i++) {
			holeNumLabel = new JLabel(tempHoleNum + "");
			holeNumLabel.setFont(textFont);
			holeNumLabel.setForeground(darkest);
			
			holeParLabel = new JLabel("Par:");
			holeParLabel.setFont(textFont);
			holeParLabel.setForeground(darkest);
			
			JComboBox<Integer> holePar = new JComboBox<Integer>();
			holePar.addItem(3);
			holePar.addItem(4);
			holePar.addItem(5);
			holePar.setFont(textFont);
			holePar.setBackground(darkest);
			holePar.setForeground(clay);
			holePar.setSelectedIndex(1);
			
			distanceLabel = new JLabel("Distance:");
			distanceLabel.setFont(textFont);
			distanceLabel.setForeground(darkest);
			

			distanceText = new JTextField();
			distanceText.setBackground(darkest);
			distanceText.setForeground(clay);

			
			yardsLabel = new JLabel("yards");
			yardsLabel.setFont(textFont);
			yardsLabel.setForeground(darkest);
			
			addHoleFrame.add(holeNumLabel);
			addHoleFrame.add(holeParLabel);
			addHoleFrame.add(holePar);
			addHoleFrame.add(distanceLabel);
			addHoleFrame.add(distanceText);
			addHoleFrame.add(yardsLabel);
			
			parInput.add(holePar);
			distanceInput.add(distanceText);
			
			tempHoleNum ++;
		}
		
		submitButton = new JButton();
		if(numHoles == 18 && numHoles != firstRound) {
			submitButton.setText("10 - 18");
		} else {
			submitButton.setText("Submit");
		}
		holeSubmitClickListener submitClickListener = new holeSubmitClickListener();
		submitButton.setFont(buttonFont);
		submitButton.setBackground(darkest);
		submitButton.setForeground(clay);
		submitButton.setBorder(buttonBorder);
		submitButton.addActionListener(submitClickListener);
		
		holeBackClickListener backClickListener = new holeBackClickListener();
		backButton = new JButton("Back");
		backButton.setFont(buttonFont);
		backButton.setBackground(darkest);
		backButton.setForeground(clay);
		backButton.setBorder(buttonBorder);
		backButton.addActionListener(backClickListener);
		
		addHoleFrame.add(backButton);
		addHoleFrame.add(blank1);
		addHoleFrame.add(blank2);
		addHoleFrame.add(blank3);
		addHoleFrame.add(blank4);
		addHoleFrame.add(submitButton);
		
	}
		class holeSubmitClickListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// check inputs
			if(numHoles == firstRound) {
				for (int i = 0; i < numHoles; i++) {
					try {
						String par = parInput.get(i).getSelectedItem() + "";
			            String distance = distanceInput.get(i).getText();
			            Integer.parseInt(distanceInput.get(i).getText());
			            parList.add(par);
			            distanceList.add(distance);
					} catch(NumberFormatException n) {
						JOptionPane.showMessageDialog(popUp, "Input all distances as a number");
						distanceInput.get(i).setText("");
						// break so message only shows once
						break;
					}
		            
		        }
				// check par match
				int coursePar = Integer.parseInt(parText.getText());
				int tempPar = 0;
				for(String par: parList) {
					tempPar += Integer.parseInt(par);
				}
				String[][] prepHole = new String[numHoles][3];
				if(coursePar != tempPar) {
					JOptionPane.showMessageDialog(popUp, "Course par and the hole par total do not match.");
				} else if(nameText.getText().isEmpty() || locationText.getText().isEmpty()){
					JOptionPane.showMessageDialog(popUp, "Must provide name and location");
				} else {
					for(int i = 0; i < numHoles; i++) {
						prepHole[i] = new String[] {
								(i + 1) + "",
								parList.get(i) + "",
								distanceList.get(i) + ""
						};
					}
					// Make Hole object
					// Add to Array List
					ArrayList<Hole> holeObject = new ArrayList<Hole>();
					for (String[] item: prepHole) {
						holeObject.add(new Hole(item));
					}
					Course course = new Course(nameText.getText(), locationText.getText(), numHoles, coursePar, holeObject);
					
					// write to csv
					writeToFile(course, holeObject);
					
					
					new MainMenuFrame();
					addHoleFrame.dispose();
				}
			} else {
				firstRound += 9;
				addHoleFrame.dispose();
				addHoles();
			}
		}
		
	}
		class holeBackClickListener implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e) {
				new MainMenuFrame();
				addHoleFrame.dispose();
				
			}
			
		}
		
		private void writeToFile(Course course, ArrayList<Hole> holeObject) {
			// make string[]
			String fileName = "courses.csv";
			String[] courseFinal = new String[(3 * numHoles) + 4];
			courseFinal[0] = course.getName();
			courseFinal[1] = course.getLocation();
			courseFinal[2] = course.getNumHoles() + "";
			courseFinal[3] = course.getCoursePar() + "";
			
			// add holes to the list
			int i = 4;
			for(Hole hole: holeObject) {
				courseFinal[i] = hole.getHoleNum()+"";
				i++;
				courseFinal[i] = hole.getPar()+"";
				i++;
				courseFinal[i] = hole.getYardage()+"";
				i++;
			}
			
			if(numHoles == 18) {
				fileName = "courses18.csv";
			}
			try {
				writeCourseToCSV(courseFinal, fileName);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(popUp, "Error writing to " + fileName + ".");
				e.printStackTrace();
			}
		}
		
		public static void writeCourseToCSV(String[] courseFinal, String fileName) throws IOException {
	        String csvFileName = fileName;
	        try (FileWriter writer = new FileWriter(csvFileName, true)) {
	            for (String data : courseFinal) {
	                writer.append(data).append(",");
	            }
	            writer.append("\n");
	        }
	    }

}

		
