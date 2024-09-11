package model;

import java.util.ArrayList;

/**
 * @author Caleb Burns
 * @version 1.0
 * @since 1.0
 */
// Create course class
public class Course {
	private String name;
	private String location;
	private int numHoles;  // 9 or 18 hole course
	private int coursePar;  // Par for whole course
	private ArrayList<Hole> holes;  // Array of holes for the course with their info
	
	// Constructors
	public Course(String name, String location, int numHoles, int coursePar) {
		super();
		this.name = name;
		this.location = location;
		this.numHoles = numHoles;
		this.coursePar = coursePar;
		this.holes = new ArrayList<>();
	}
	public Course(String name, String location, int numHoles, int coursePar, ArrayList<Hole> holes) {
		this.name = name;
		this.location = location;
		this.numHoles = numHoles;
		this.coursePar = coursePar;
		this.holes = new ArrayList<Hole>(holes);
		
	}
	
	// Getters Setters
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public int getNumHoles() {
		return numHoles;
	}
	public void setNumHoles(int numHoles) {
		this.numHoles = numHoles;
	}
	public int getCoursePar() {
		return coursePar;
	}
	public void setCoursePar(int coursePar) {
		this.coursePar = coursePar;
	}
	
	//Helper Methods
	public void addHole(Hole hole) {
		holes.add(hole);
	}
	
	// Custom print methods
	public String printCourseInfo() {
		return getName() + "\n" + getLocation() + "\n" + getNumHoles() + " Hole\t Par " + getCoursePar();
	}
	
	StringBuilder str = new StringBuilder();
	public String printAllHoles() {
		for (Hole hole : holes) {
			str.append(hole);
			str.append("\n");
		}
		return str.toString();
	}
	
	StringBuilder str1 = new StringBuilder();
	public String printHole(int holeNum) {
		str1.delete(0, str1.length());
		str1.append("Hole " + holes.get(holeNum).getHoleNum());
		return str1.toString();
	}
	
	StringBuilder str2 = new StringBuilder();
	public String printPar(int holeNum) {
		str2.delete(0, str2.length());
		str2.append("Par " + holes.get(holeNum).getPar());
		return str2.toString();
	}
	
	StringBuilder str3 = new StringBuilder();
	public String printYardage(int holeNum) {
		str3.delete(0, str3.length());
		str3.append(holes.get(holeNum).getYardage() + "yds");
		return str3.toString();
	}
	
	public String printAllInfo() {
		return printCourseInfo() + "\n" + printAllHoles();
	}
	
}
