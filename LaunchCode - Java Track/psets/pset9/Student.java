package pset9;

public class Student {
	private String first_name;
	private String last_name;
	private int ID;
	private int credits;
	private double GPA;
	
	public Student(String first_name, String last_name, int ID, int credits, double GPA) {
		this.first_name = first_name;
		this.last_name = last_name;
		this.ID = ID;
		this.credits = credits;
		this.GPA = GPA;
	}
	
	public Student(String first_name, String last_name, int ID) {
		this(first_name, last_name, ID, 0, 0.0);
	}
	
	
	public String getName() {
		return first_name +" "+ last_name;
	}
	public int getStudentID() {
		return ID;
	}
	public int getCredits() {
		return credits;
	}
	public double getGPA() {
		return (double)Math.round(GPA * 1000d) / 1000d;
	}
	
	public String getClassStanding() {
		if(credits >= 90) return "Senior";
		else if(credits >= 60) return "Junior";
		else if(credits >= 30) return "Sophomore";
		else return "Freshman";
	}
	
	public String toString() {
		return this.getName() +" "+ this.getStudentID();
	}
	
	public void submitGrade(double grade, int credits) {
		double tempGPA = this.credits * this.GPA;
		tempGPA += grade * credits;
		this.credits += credits;
		this.GPA = tempGPA / this.credits;
	}
	
	public double computeTuition() {
		int semesters = (credits + 14) / 15;
		return 20000.0 * semesters;
	}
	
	public Student createLegacy(Student parent) {
		String first = this.getName();
		String last = parent.getName();
		int ID = this.getStudentID() + parent.getStudentID();
		double GPA = (this.getGPA() + parent.getGPA()) / 2;
		int credits = 0;
		if(this.getCredits() > parent.getCredits()) {
			credits = this.getCredits();
		}
		else credits = parent.getCredits();
		
		return new Student(first, last, ID, credits, GPA);
	}
}
