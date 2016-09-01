package pset9;
import java.util.ArrayList;

public class Course {
	private String name;
	private int credits;
	private int seats;
	private ArrayList<Student> students;
	
	public Course(String name, int credits, int seats) {
		this.name = name;
		this.credits = credits;
		this.seats = seats;
		this.students = new ArrayList<Student>();
	}
	
	public String getName() {
		return name;
	}
	public int getRemainingSeats() {
		return seats - students.size();
	}
	public String toString() {
		return "Course name: "+ name +" Credits: "+ credits;
	}
	
	public boolean addStudent(Student s) {
		if(!students.contains(s)) {
			if(students.size() != seats) {
				students.add(s);
				return true;
			}
		}
		return false;
	}

	public String generateRoster() {
		String roster = "";
		for(Student s : students) {
			roster += s.toString() +System.lineSeparator();
		}
		return roster;
	}
	
	public double averageGPA() {
		double sum = 0.0;
		for(Student s : students) {
			sum += s.getGPA();
		}
		return sum / students.size();
	}
}
