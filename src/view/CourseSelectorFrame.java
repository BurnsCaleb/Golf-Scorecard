package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;

import model.Course;
import model.Hole;

/**
 * @author Caleb Burns
 * @version 1.0
 * @since 1.0
 */
// Create frame to select a course
public class CourseSelectorFrame {
	private static ArrayList<Course> allCourses = new ArrayList<Course>();
	
	private JFrame frame, popUp;
	
	private JPanel titlePanel = new JPanel();
	private JPanel listPanel = new JPanel();
	private JPanel buttonPanel = new JPanel();
	
	private JLabel title = new JLabel();
	
	private DefaultListModel<String> courseList = new DefaultListModel<>(); 
	private JList<String> listContainer;
	
	private JButton submitButton, backButton;
	
	// Create fonts
	private Font headingFont = new Font("Calisto MT", Font.BOLD, 40);
	private Font buttonFont = new Font("Candara", Font.BOLD, 25);
	private Font listFont = new Font("Georgia", Font.PLAIN, 22);
	
	// Create colors
	private Color darkest = new Color(58, 77, 57);
	private Color dark = new Color(79, 111, 82);
	private Color light = new Color(153, 176, 128);
	private Color clay = new Color(249, 181, 114);
	
	// Create borders
	private Border buttonBorder = BorderFactory.createLineBorder(darkest);
	
	private int nineHole = 0;
    private int eighteenHole = 0;
	
	public CourseSelectorFrame() {
		initialize();
	}
	
	private void initialize() {
		// No duplicates
		allCourses.removeAll(allCourses);
		
		// Read course information
		getNumCourses("courses.csv");
		getNumCourses("courses18.csv");

		for (int courseCount = 0; courseCount < nineHole; courseCount++) {
			readNineHole(courseCount);
		}
		for (int courseCount = 0; courseCount < eighteenHole; courseCount++) {
			readEighteenHole(courseCount);
		}
		
		frame = new JFrame();
		frame.setTitle("Golf Scorecard by Caleb Burns");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(600, 750);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		
		titlePanel.setLayout(new GridLayout(1, 1));
		titlePanel.setBackground(darkest);
		listPanel.setLayout(new GridLayout(1, 1));
		buttonPanel.setLayout(new GridLayout(1, 2));
		
		// Title
		title.setText("Select Course");
		title.setForeground(clay);
		title.setFont(headingFont);
		title.setHorizontalAlignment(0);
		
		// List
		for (Course courses : allCourses) {
			courseList.addElement(courses.getName());
		}
		
		listContainer = new JList<>(courseList);
		listContainer.setFont(listFont);
		listContainer.setBackground(light);
		listContainer.setForeground(darkest);
		
		submitClickListener submit = new submitClickListener();
		submitButton = new JButton();
		submitButton.setText("Submit");
		submitButton.setFont(buttonFont);
		submitButton.setForeground(clay);
		submitButton.setBorder(buttonBorder);
		submitButton.setBackground(dark);
		submitButton.addActionListener(submit);
		
		backClickListener back = new backClickListener();
		backButton = new JButton();
		backButton.setText("Back");
		backButton.setFont(buttonFont);
		backButton.setForeground(clay);
		backButton.setBorder(buttonBorder);
		backButton.setBackground(dark);
		backButton.addActionListener(back);
		
		
		
		frame.add(titlePanel);
		titlePanel.add(title);
		
		frame.add(listPanel);
		listPanel.add(listContainer);
		
		frame.add(buttonPanel);
		buttonPanel.add(backButton);
		buttonPanel.add(submitButton);
		
		frame.setLayout(new GridLayout(3, 1));
		
	}
	
	private void getNumCourses(String filename) {
		try {
			List<List<String>> records = new ArrayList<>();
			try (Scanner scanner = new Scanner(new File(filename))) {
			    while (scanner.hasNextLine()) {
			        records.add(getRecordFromLine(scanner.nextLine()));
			    }
			    // Count 9 and 18 hole courses respectively
			    int x = records.size();
			    for (int i = 0; i < x; i++) {
			    	List<String> innerList = records.get(i);
			    	if(Integer.parseInt(innerList.get(2)) == 9) {
			    		nineHole++;
			    	} else if(Integer.parseInt(innerList.get(2)) == 18) {
			    		eighteenHole++;
			    	}
			    }
			}
		} catch(FileNotFoundException e) {
			JOptionPane.showMessageDialog(popUp, "Could not find file \"courses.csv\".");
		}	
	}
	
	private void readNineHole(int courseCount) {
		// 9 Hole Course
		try {
			List<List<String>> records = new ArrayList<>();
			try (Scanner scanner = new Scanner(new File("courses.csv"))) {
			    while (scanner.hasNextLine()) {
			        records.add(getRecordFromLine(scanner.nextLine()));
			    }
			}

			// Gather hole information
			try {
				List<String> innerList = records.get(courseCount);
				String[][] holes = new String[9][3];
				int i = 1;
				while (i <= 9) {
				    int startIndex = i * 3 + 1; // Calculate the starting index for each record
				    holes[i - 1] = new String[]{
				        innerList.get(startIndex).toString(),
				        innerList.get(startIndex + 1).toString(),
				        innerList.get(startIndex + 2).toString()
				    };
				    i++;
				}
				
				// Make Hole object
				// Add to Array List
				ArrayList<Hole> holeObject = new ArrayList<Hole>();
				for (String[] item: holes) {
					holeObject.add(new Hole(item));
				}
				// Make Course object
				allCourses.add(new Course(innerList.get(0).toString(), innerList.get(1).toString(), Integer.parseInt(innerList.get(2).toString()), Integer.parseInt(innerList.get(3).toString()), holeObject));
				
			} catch(IndexOutOfBoundsException e) {
				e.printStackTrace();
			}
		} catch(FileNotFoundException e) {
			JOptionPane.showMessageDialog(popUp, "Could not find file \"courses.csv\".");
		}
	}
	
	private void readEighteenHole(int courseCount) {
		// 18 Hole Course
		try {
			List<List<String>> records = new ArrayList<>();
			try (Scanner scanner = new Scanner(new File("courses18.csv"))) {
			    while (scanner.hasNextLine()) {
			        records.add(getRecordFromLine(scanner.nextLine()));
			    }
			}

			// get hole information
			try {
				List<String> innerList = records.get(courseCount);
				String[][] holes = new String[18][3];
				int i = 1;
				while (i <= 18) {
				    int startIndex = i * 3 + 1; // Calculate the starting index for each record
				    holes[i - 1] = new String[]{
				        innerList.get(startIndex).toString(),
				        innerList.get(startIndex + 1).toString(),
				        innerList.get(startIndex + 2).toString()
				    };
				    i++;
				}
				
				// Make Hole object
				// Add to Array List
				ArrayList<Hole> holeObject = new ArrayList<Hole>();
				for (String[] item: holes) {
					holeObject.add(new Hole(item));
				}
				// Make Course object
				allCourses.add(new Course(innerList.get(0).toString(), innerList.get(1).toString(), Integer.parseInt(innerList.get(2).toString()), Integer.parseInt(innerList.get(3).toString()), holeObject));
				
			} catch(IndexOutOfBoundsException e) {
				e.printStackTrace();
			}
		} catch(FileNotFoundException e) {
			JOptionPane.showMessageDialog(popUp, "Could not find file \"courses18.csv\".");
		}
	}
	
	private static List<String> getRecordFromLine(String line) {
	    List<String> values = new ArrayList<String>();
	    try (Scanner rowScanner = new Scanner(line)) {
	        rowScanner.useDelimiter(",");
	        while (rowScanner.hasNext()) {
	            values.add(rowScanner.next());
	        }
	    }
	    return values;
	}
	
	class submitClickListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// Make sure course is selected
			if(listContainer.getSelectedValue() == null) {
				JOptionPane.showMessageDialog(popUp, "Please select a course.");
			} else {
				startScorecard(listContainer.getSelectedValue());
				frame.dispose();
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
	
	private void startScorecard(String name) {
		for(Course course: allCourses) {
			if(course.getName().equals(name)) {
				new ScorecardFrame(course);
			}
		}
	}
}
