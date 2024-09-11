package view;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;

/**
 * @author Caleb Burns
 * @version 1.0
 * @since 1.0
 */
// Create frame to select between new round, new course, view past rounds
public class MainMenuFrame {

	private JFrame frame, popUp;
	
	private JPanel panel = new JPanel();
	
	private JLabel title = new JLabel();
	
	private JButton roundButton, viewButton, courseButton;
	
	// Create fonts
	private Font headingFont = new Font("Calisto MT", Font.BOLD, 40);
	private Font buttonFont = new Font("Candara", Font.BOLD, 25);
	
	// create colors
	private Color darkest = new Color(58, 77, 57);
	private Color light = new Color(153, 176, 128);
	private Color clay = new Color(249, 181, 114);
	
	private Border buttonBorder = BorderFactory.createLineBorder(darkest);
	
	public MainMenuFrame() {
		initialize();
	}
	
	private void initialize() {
		// set layout
		frame = new JFrame();
		frame.setTitle("Golf Scorecard by Caleb Burns");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(600, 750);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		
		panel.setLayout(new GridLayout(4, 1));
		panel.setBackground(darkest);
		
		title.setText("Golf Scorecard");
		title.setForeground(clay);
		title.setFont(headingFont);
		title.setHorizontalAlignment(0);
		
		roundClickListener round = new roundClickListener();
		roundButton = new JButton();
		roundButton.setText("Enter New Round");
		roundButton.setFont(buttonFont);
		roundButton.setForeground(darkest);
		roundButton.setBorder(buttonBorder);
		roundButton.setBackground(light);
		roundButton.addActionListener(round);
		
		viewClickListener view = new viewClickListener();
		viewButton = new JButton();
		viewButton.setText("View Past Rounds");
		viewButton.setFont(buttonFont);
		viewButton.setForeground(darkest);
		viewButton.setBorder(buttonBorder);
		viewButton.setBackground(light);
		viewButton.addActionListener(view);
		
		courseClickListener addCourse = new courseClickListener();
		courseButton = new JButton();
		courseButton.setText("Add New Golf Course");
		courseButton.setFont(buttonFont);
		courseButton.setForeground(darkest);
		courseButton.setBorder(buttonBorder);
		courseButton.setBackground(light);
		courseButton.addActionListener(addCourse);
		
		frame.add(panel);
		panel.add(title);
		panel.add(roundButton);
		panel.add(viewButton);
		panel.add(courseButton);
	}
	// new round
	class roundClickListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			new CourseSelectorFrame();
			frame.dispose();
			
		}
		
	}
	// view past rounds
	class viewClickListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
		    try {
		        File file = new File("scores.txt");

		        if (file.exists()) {
		            FileInputStream fis = new FileInputStream(file);
		            Desktop desktop = Desktop.getDesktop();
		            desktop.open(file);
		            fis.close(); // Close the FileInputStream after opening the file
		        }
		    } catch (IOException ex) {
		        JOptionPane.showMessageDialog(popUp, "Error grabbing \"scores.txt\": " + ex.getMessage());
		    }
		}

	}
	// new course
	class courseClickListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			new AddCourseFrame();
			frame.dispose();
		}
	}
}
