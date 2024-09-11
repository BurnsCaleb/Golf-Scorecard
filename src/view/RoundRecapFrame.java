package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;

import model.Course;

/**
 * @author Caleb Burns
 * @version 1.0
 * @since. 1.0
 */
// Create frame for end of round recap
public class RoundRecapFrame {
	private JFrame frameRecap, popUp;
	private JPanel recapPanel, buttonPanel;
	private JLabel courseLabel, scoreLabel, score;
	private JButton submit, back;
	
	// Set fonts
	private Font headingFont = new Font("Calisto MT", Font.BOLD, 30);
	private Font holeFont = new Font("Calisto MT", Font.PLAIN, 20);
	private Font buttonFont = new Font("Candara", Font.BOLD, 25);
	private Font textFont = new Font("Georgia", Font.PLAIN, 22);
	
	// Set colors
	private Color darkest = new Color(58, 77, 57);
	private Color dark = new Color(79, 111, 82);
	private Color light = new Color(153, 176, 128);
	private Color clay = new Color(249, 181, 114);
	
	// Set border
	private Border buttonBorder = BorderFactory.createLineBorder(clay);
	
	private Course course;
	private List<String[]> holeRecap;
	private int totalStrokes = 0;
	
	public RoundRecapFrame(Course course, List<String[]> holeRecap) {
		initialize(course, holeRecap);
		
	}
	
	private void initialize(Course course, List<String[]> holeRecap) {
		this.course = course;
		this.holeRecap = holeRecap;
		
		// Create gui layout
		frameRecap = new JFrame();
		frameRecap.setLayout(new GridLayout(2, 1));
		frameRecap.getContentPane().setBackground(light);
		
		recapPanel = new JPanel();
		recapPanel.setLayout(new GridLayout(3, 1));
		recapPanel.setBackground(darkest);
		
		courseLabel = new JLabel(course.getName());
		courseLabel.setFont(headingFont);
		courseLabel.setForeground(clay);
		courseLabel.setHorizontalAlignment(0);
		
		scoreLabel = new JLabel("Gross Score");
		scoreLabel.setHorizontalAlignment(0);
		scoreLabel.setFont(holeFont);
		scoreLabel.setForeground(clay);
		
		score = new JLabel();
		score.setHorizontalAlignment(0);
		score.setFont(textFont);
		score.setForeground(clay);
		
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1, 2));
		buttonPanel.setBackground(darkest);
		
		frameRecap.setTitle("Golf Scorecard by Caleb Burns");
		frameRecap.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frameRecap.setSize(600, 750);
		frameRecap.setVisible(true);
		frameRecap.setLocationRelativeTo(null);
		
		submitRecapClickListener submitListen = new submitRecapClickListener();
		submit = new JButton("Submit");
		submit.setFont(buttonFont);
		submit.setBackground(dark);
		submit.setForeground(clay);
		submit.setBorder(buttonBorder);
		submit.addActionListener(submitListen);
		
		previousRecapClickListener previous = new previousRecapClickListener();
		back = new JButton("Return");
		back.setFont(buttonFont);
		back.setBackground(dark);
		back.setForeground(clay);
		back.setBorder(buttonBorder);
		back.addActionListener(previous);
		
		
		// get total score
		for (String[] holeInfo2 : holeRecap) {
			totalStrokes += Integer.parseInt(holeInfo2[5]);
		}
		String scoreOutput = "";
		if(totalStrokes > course.getCoursePar()) {
			int netScore = totalStrokes - course.getCoursePar();
			scoreOutput = "(+" + netScore + ")";
		} else if(totalStrokes < course.getCoursePar()){
			int netScore = course.getCoursePar() - totalStrokes;
			scoreOutput = "(-" + netScore + ")";
		} else {
			scoreOutput = "(Even Par)";
		}
		score.setText(totalStrokes + " " + scoreOutput);
		
		frameRecap.add(recapPanel);
		recapPanel.add(courseLabel);
		recapPanel.add(scoreLabel);
		recapPanel.add(score);
		frameRecap.add(buttonPanel);
		buttonPanel.add(back);
		buttonPanel.add(submit);
	}
	
	class submitRecapClickListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			writeToFile();
			new MainMenuFrame();
			frameRecap.dispose();
		}
	}
	
	class previousRecapClickListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			new ScorecardFrame(course);
			frameRecap.dispose();
			
		}
		
	}
	// Store data
	private void writeToFile() {
			
			try {
				PrintWriter myWriter = new PrintWriter(new FileWriter("scores.txt", true));
				myWriter.println(course.getName());
				myWriter.println(course.getLocation());
				myWriter.println(course.getNumHoles() + " Holes");
				myWriter.println("Par " + course.getCoursePar());
				myWriter.println("Your score: " + totalStrokes);
				myWriter.println();
				for (String[] holeInfo2 : holeRecap) {
					myWriter.println("Hole " + holeInfo2[0] + ":");
					myWriter.println(holeInfo2[1]);
					myWriter.println("Distance: " + holeInfo2[2]);
					myWriter.println("Tee Shot Direction: " + holeInfo2[3]);
					myWriter.println("Number of Putts: " + holeInfo2[4]);
					myWriter.println("Number of Strokes: " + holeInfo2[5]);
					myWriter.println("Notes: " + holeInfo2[6]);
					myWriter.println("------------------------");
				}
				myWriter.println("\n\n\n");
				myWriter.close();
				
				JOptionPane.showMessageDialog(popUp, "Round info successfully saved to \"scores.txt\".");
			} catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(popUp, "Error saving round info to \"scores.txt\".");
			} catch (IOException e) {
				JOptionPane.showMessageDialog(popUp, "Error saving round info to \"scores.txt\".");
			}
		}
}
