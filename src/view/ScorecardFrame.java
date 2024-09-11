package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.Border;

import model.Course;

/**
 *@author Caleb Burns
 *@version 1.0
 *@since 1.0
 */
// Create frame for tracking each hole
public class ScorecardFrame {
	private List<String[]> holeRecap = new ArrayList<>();

	private JFrame frame, popUp;
	private JLabel courseName, holeInfo, teeShotsLabel, puttsLabel, totalLabel, notesLabel;

	private JPanel courseInfoPanel = new JPanel();

	private JPanel teeShotPanel = new JPanel();
	private JPanel teeButtonPanel = new JPanel();

	// Buttons for tee shot direction
	private String teeShotDirection = "";
	private JButton leftButton = new JButton("<<<");
	private JButton centerButton = new JButton("|  |  |");
	private JButton rightButton = new JButton(">>>");

	// combobox for number of putts
	private String numPutts;
	private JPanel puttsPanel = new JPanel();
	private String puttOptions[] = { "Select Number of Putts", "1", "2", "3", "4", "5+" };
	private JComboBox<Object> puttCombobox = new JComboBox<Object>(puttOptions);

	// combobox for number of strokes
	private String numStrokes;
	private JPanel totalPanel = new JPanel();
	private String totalOptions[] = { "Select Number of Strokes", "1", "2", "3", "4", "5", "6", "7+" };
	private JComboBox<Object> totalCombobox = new JComboBox<Object>(totalOptions);

	// text area for extra notes
	private JPanel notesPanel = new JPanel();
	private JTextArea notes = new JTextArea(3, 1);

	// back and next buttons
	private JPanel submitPanel = new JPanel();
	private JButton nextButton = new JButton("Next >>>");
	private JButton backButton = new JButton("Cancel");

	// create fonts
	private Font headingFont = new Font("Calisto MT", Font.BOLD, 30);
	private Font holeFont = new Font("Calisto MT", Font.PLAIN, 20);
	private Font buttonFont = new Font("Candara", Font.BOLD, 25);
	private Font textFont = new Font("Georgia", Font.PLAIN, 22);
	private Font comboboxFont = new Font("Georgia", Font.PLAIN, 25);
	
	// create colors
	private Color darkest = new Color(58, 77, 57);
	private Color dark = new Color(79, 111, 82);
	private Color light = new Color(153, 176, 128);
	private Color clay = new Color(249, 181, 114);
	
	// create borders
	private Border buttonBorder = BorderFactory.createLineBorder(darkest);
	private Border boxBorder = BorderFactory.createLineBorder(clay);
	
	private String space = "          ";

	private Course course;
	private int holeNum = 1;

	// Pass in Course Object, Current Hole Number, Number of Holes
	public ScorecardFrame(Course course) {
		initialize(course);
	}

	// Pass in Course
	private void initialize(Course course) {

		this.course = course;

		// set layout
		frame = new JFrame();
		frame.getContentPane().setBackground(light);

		frame.setTitle("Golf Scorecard by Caleb Burns");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(600, 750);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);

		// JPanel for Course & Hole Info
		courseInfoPanel.setLayout(new GridLayout(1, 2));
		courseInfoPanel.setBackground(darkest);

		courseName = new JLabel(course.getName());
		courseName.setHorizontalAlignment(0);
		courseName.setFont(headingFont);
		courseName.setForeground(clay);

		holeInfo = new JLabel(
				"Hole " + holeNum + space + course.printPar(holeNum - 1) + space + course.printYardage(holeNum - 1));
		holeInfo.setHorizontalAlignment(0);
		holeInfo.setFont(holeFont);
		holeInfo.setForeground(clay);

		// JPanel for Tee Shot Label
		teeShotPanel.setLayout(new GridLayout(1, 1));
		teeShotPanel.setBackground(light);
		teeShotsLabel = new JLabel("Tee Shot");
		teeShotsLabel.setHorizontalAlignment(0);
		teeShotsLabel.setFont(textFont);
		teeShotsLabel.setForeground(darkest);

		// JPanel for Tee Buttons
		teeButtonPanel.setLayout(new GridLayout(1, 3, 50, 0));
		teeButtonPanel.setBackground(light);

		teeLeftClickListener teeLeft = new teeLeftClickListener();
		teeCenterClickListener teeCenter = new teeCenterClickListener();
		teeRightClickListener teeRight = new teeRightClickListener();

		leftButton.setFont(buttonFont);
		leftButton.setBackground(dark);
		leftButton.setForeground(clay);
		leftButton.setBorder(buttonBorder);
		leftButton.addActionListener(teeLeft);

		centerButton.setFont(buttonFont);
		centerButton.setBackground(dark);
		centerButton.setForeground(clay);
		centerButton.setBorder(buttonBorder);
		centerButton.addActionListener(teeCenter);

		rightButton.setFont(buttonFont);
		rightButton.setBackground(dark);
		rightButton.setForeground(clay);
		rightButton.setBorder(buttonBorder);
		rightButton.addActionListener(teeRight);

		// JPanel for Putts Label and ComboBox
		puttsPanel.setLayout(new GridLayout(2, 1));
		puttsPanel.setBackground(light);

		puttClickListener putts = new puttClickListener();

		puttCombobox.setFont(comboboxFont);
		puttCombobox.setBorder(boxBorder);
		puttCombobox.setBackground(dark);
		puttCombobox.setForeground(Color.BLACK);
		
		puttCombobox.addActionListener(putts);

		puttsLabel = new JLabel("Putts");
		puttsLabel.setHorizontalAlignment(0);
		puttsLabel.setFont(textFont);
		puttsLabel.setForeground(darkest);

		// JPanel for Total and ComboBox
		totalClickListener strokes = new totalClickListener();

		totalPanel.setLayout(new GridLayout(2, 1));
		totalPanel.setBackground(light);
		
		totalCombobox.setFont(comboboxFont);
		totalCombobox.setBorder(boxBorder);
		totalCombobox.setBackground(dark);
		totalCombobox.setForeground(Color.BLACK);
		totalCombobox.addActionListener(strokes);

		totalLabel = new JLabel("Total");
		totalLabel.setHorizontalAlignment(0);
		totalLabel.setFont(textFont);
		totalLabel.setForeground(darkest);

		// JPanel for Notes and InputField
		notesPanel.setLayout(new GridLayout(2, 1));
		notesPanel.setBackground(light);
		notesLabel = new JLabel("Notes:");
		notesLabel.setHorizontalAlignment(0);
		notesLabel.setFont(textFont);
		notes.setBorder(boxBorder);
		notes.setBackground(dark);
		notes.setForeground(Color.BLACK);

		// JPanel for Submit Buttons
		submitClickListener submit = new submitClickListener();
		submitPanel.setLayout(new GridLayout(1, 2, 50, 50));
		submitPanel.setBackground(light);
		
		nextButton.setFont(buttonFont);
		nextButton.setBackground(dark);
		nextButton.setForeground(clay);
		nextButton.addActionListener(submit);

		backClickListener back = new backClickListener();
		backButton.setFont(buttonFont);
		backButton.setBackground(dark);
		backButton.setForeground(clay);
		backButton.addActionListener(back);

		// ADD TO GUI
		frame.add(courseInfoPanel);
		courseInfoPanel.add(courseName);
		courseInfoPanel.add(holeInfo);

		frame.add(teeShotPanel);
		teeShotPanel.add(teeShotsLabel);

		frame.add(teeButtonPanel);
		teeButtonPanel.add(leftButton);
		teeButtonPanel.add(centerButton);
		teeButtonPanel.add(rightButton);

		frame.add(puttsPanel);
		puttsPanel.add(puttsLabel);
		puttsPanel.add(puttCombobox);

		frame.add(totalPanel);
		totalPanel.add(totalLabel);
		totalPanel.add(totalCombobox);

		frame.add(notesPanel);
		notesPanel.add(notesLabel);
		notesPanel.add(notes);

		frame.add(submitPanel);
		submitPanel.add(backButton);
		submitPanel.add(nextButton);

		frame.setLayout(new GridLayout(7, 1, 0, 20));
	}

	class teeLeftClickListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			leftButton.setBackground(clay);
			leftButton.setForeground(Color.BLACK);
			centerButton.setBackground(dark);
			centerButton.setForeground(clay);
			rightButton.setBackground(dark);
			rightButton.setForeground(clay);

			teeShotDirection = "left";
		}

	}

	class teeCenterClickListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			leftButton.setBackground(dark);
			leftButton.setForeground(clay);
			centerButton.setBackground(clay);
			centerButton.setForeground(Color.BLACK);
			rightButton.setBackground(dark);
			rightButton.setForeground(clay);

			teeShotDirection = "center";
		}

	}

	class teeRightClickListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			leftButton.setBackground(dark);
			leftButton.setForeground(clay);
			centerButton.setBackground(dark);
			centerButton.setForeground(clay);
			rightButton.setBackground(clay);
			rightButton.setForeground(Color.BLACK);

			teeShotDirection = "right";
		}

	}

	class puttClickListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String tempNumPutts;
			String puttInput;
			boolean badInput = true;
			tempNumPutts = (String) puttCombobox.getItemAt(puttCombobox.getSelectedIndex());

			if (tempNumPutts != null) {
				// Show 5-10
				if (tempNumPutts.equals("5+")) {
					puttCombobox.removeAllItems();
					puttCombobox.addItem("Select Number of Putts");
					puttCombobox.addItem("5");
					puttCombobox.addItem("6");
					puttCombobox.addItem("7");
					puttCombobox.addItem("8");
					puttCombobox.addItem("9");
					puttCombobox.addItem("10+");
					puttCombobox.addItem("Back");
				}

				// Show 1-5
				if (tempNumPutts.equals("Back")) {
					puttCombobox.removeAllItems();
					puttCombobox.addItem("Select Number of Putts");
					puttCombobox.addItem("1");
					puttCombobox.addItem("2");
					puttCombobox.addItem("3");
					puttCombobox.addItem("4");
					puttCombobox.addItem("5+");
				}

				if (!tempNumPutts.equals("Select Number of Putts") && !tempNumPutts.equals("Back")
						&& !tempNumPutts.equals("5+") && !tempNumPutts.equals("10+")) {
					numPutts = tempNumPutts;
				} else if (tempNumPutts.equals("10+")) {
					while (badInput) {

						puttInput = JOptionPane.showInputDialog(popUp, "Enter Number of Putts");

						if (puttInput == null) {
							puttCombobox.removeAllItems();
							puttCombobox.addItem("Select Number of Putts");
							puttCombobox.addItem("1");
							puttCombobox.addItem("2");
							puttCombobox.addItem("3");
							puttCombobox.addItem("4");
							puttCombobox.addItem("5+");
							badInput = false;
						} else {
							try {
								numPutts = puttInput;
								puttCombobox.removeAllItems();
								puttCombobox.addItem(puttInput);
								puttCombobox.addItem("Back");
								badInput = false;
							} catch (NumberFormatException n) {
								continue;
							}
						}
					}
				}
			}
		}
	}

	class totalClickListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String tempNumStrokes;
			String strokeInput;
			boolean badInput = true;
			tempNumStrokes = (String) totalCombobox.getItemAt(totalCombobox.getSelectedIndex());

			if(tempNumStrokes != null) {
				// Show 7-10
				if (tempNumStrokes.equals("7+")) {
					totalCombobox.removeAllItems();
					totalCombobox.addItem("Select Number of Strokes");
					totalCombobox.addItem("7");
					totalCombobox.addItem("8");
					totalCombobox.addItem("9");
					totalCombobox.addItem("10+");
					totalCombobox.addItem("Back");
				}

				// Show 1-7
				if (tempNumStrokes.equals("Back")) {
					totalCombobox.removeAllItems();
					totalCombobox.addItem("Select Number of Strokes");
					totalCombobox.addItem("1");
					totalCombobox.addItem("2");
					totalCombobox.addItem("3");
					totalCombobox.addItem("4");
					totalCombobox.addItem("5");
					totalCombobox.addItem("6");
					totalCombobox.addItem("7+");
				}

				if (!tempNumStrokes.equals("Select Number of Strokes") && !tempNumStrokes.equals("Back")
						&& !tempNumStrokes.equals("7+") && !tempNumStrokes.equals("10+")) {
					numStrokes = tempNumStrokes;
				} else if (tempNumStrokes.equals("10+")) {
					while (badInput) {

						strokeInput = JOptionPane.showInputDialog(popUp, "Enter Number of Strokes");

						if (strokeInput == null) {
							totalCombobox.removeAllItems();
							totalCombobox.addItem("Select Number of Strokes");
							totalCombobox.addItem("1");
							totalCombobox.addItem("2");
							totalCombobox.addItem("3");
							totalCombobox.addItem("4");
							totalCombobox.addItem("5");
							totalCombobox.addItem("6");
							totalCombobox.addItem("7+");
							badInput = false;
						} else {
							try {
								numStrokes = strokeInput;
								totalCombobox.removeAllItems();
								totalCombobox.addItem(strokeInput);
								totalCombobox.addItem("Back");
								badInput = false;
							} catch (NumberFormatException n) {
								continue;
							}
						}
					}
				}
			}
		}
	}

	class submitClickListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			// Make sure info is entered
			if(teeShotDirection.equals("") || numPutts == null  || numStrokes == null) {
				JOptionPane.showMessageDialog(popUp, "Please Enter: Tee Shot Direction, Number of Putts, and Number of Strokes.");
			} else if(Integer.parseInt(numPutts) > Integer.parseInt(numStrokes)) {
				JOptionPane.showMessageDialog(popUp, "Error: Number of Strokes should be larger than Number of Putts.");
			} else {
				// Get Hole Number, Hole Par, Hole Yardage, Tee Direction, Number of Putts,
				// Number of Strokes, and Notes
				// Store data for all holes to write to file
				String holeNumString = holeNum + "";
				holeRecap.add(new String[] { holeNumString, course.printPar(holeNum - 1), course.printYardage(holeNum - 1),
						teeShotDirection, numPutts, numStrokes, notes.getText() });
			
				// show round recap if final hole
				if (holeNum == course.getNumHoles()) {
					new RoundRecapFrame(course, holeRecap);
					frame.dispose();
				} else {
					// Reset input and start new hole
					holeNum++;
					newHole(course, holeNum);
					teeShotDirection = "";
				    puttCombobox.setSelectedIndex(0);
				    totalCombobox.setSelectedIndex(0); 
				    numPutts = null;
				    numStrokes = null;
				}
			}
		}
	}

	class backClickListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// Removes last hole entry
			if(holeNum != 1) {
				holeRecap.remove(holeRecap.size() - 1);

				holeNum--;
				newHole(course, holeNum);
			} else {
				new MainMenuFrame();
				frame.dispose();
			}
		}
	}

	private void updateHoleInfo(int holeNum) {

		courseName.setText(course.getName());
		holeInfo.setText(
				"Hole " + holeNum + space + course.printPar(holeNum - 1) + space + course.printYardage(holeNum - 1));

		leftButton.setBackground(dark);
		leftButton.setForeground(clay);
		centerButton.setBackground(dark);
		centerButton.setForeground(clay);
		rightButton.setBackground(dark);
		rightButton.setForeground(clay);

		puttCombobox.setSelectedIndex(0);
		totalCombobox.setSelectedIndex(0);

		notes.setText("");

		if (holeNum == course.getNumHoles()) {
			nextButton.setText("Submit");
		}

		if (holeNum != 1) {
			backButton.setText("<<< Back");
		}
	}

	private void newHole(Course course, int holeNum) {

		updateHoleInfo(holeNum);

		frame.revalidate();
		frame.repaint();
	}
}


