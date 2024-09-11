package view;

import javax.swing.SwingUtilities;

public class ScorecardStarter {
	
	public static void main(String[] args) {  // Create main menu frame
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new MainMenuFrame();
			}
		});
	}
	
	
}
