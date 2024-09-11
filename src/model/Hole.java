package model;

/**
 * @author Caleb Burns
 * @version 1.0
 * @since 1.0
 */
// Create Hole Class
public class Hole{
	private int holeNum;
	private int par;
	private int yardage;
	
	// Constructor
	public Hole(int holeNum, int par, int yardage) {
		super();
		this.holeNum = holeNum;
		this.par = par;
		this.yardage = yardage;
	}
	
	public Hole(String[] hole) {
		this.holeNum = Integer.parseInt(hole[0]);
		this.par = Integer.parseInt(hole[1]);
		this.yardage = Integer.parseInt(hole[2]);
	}

	// Getters Setters
	public int getHoleNum() {
		return holeNum;
	}

	public void setHoleNum(int holeNum) {
		this.holeNum = holeNum;
	}

	public int getPar() {
		return par;
	}

	public void setPar(int par) {
		this.par = par;
	}

	public int getYardage() {
		return yardage;
	}

	public void setYardage(int yardage) {
		this.yardage = yardage;
	}
	
	// Helper Methods
	@Override
	public String toString() {
		return "Hole " + getHoleNum() + " Par " + getPar() + " " + getYardage() + " yds";
	}
}
