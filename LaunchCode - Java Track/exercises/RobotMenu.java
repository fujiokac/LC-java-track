import java.util.ArrayList;
import java.util.Scanner;

public class RobotMenu {
	private ArrayList<Robot> robots;
	private Scanner input;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int x = 0;
		RobotMenu rm = new RobotMenu();
		do {
			x = rm.StartMenu();
			rm.ProcessInput(x);
		} while(x != 6);
		return;
	}
	
	public RobotMenu() {
		input = new Scanner(System.in);
		robots = new ArrayList<Robot>();
	}
	
	public int StartMenu() {
		System.out.println("1. Create a robot");
		System.out.println("2. Display available robots");
		System.out.println("3. Move a robot");
		System.out.println("4. Rotate a robot");
		System.out.println("5. Compute the distance between two robots");
		System.out.println("6. Exit");
		
		return getInt(1,6);
	}
	
	public void ProcessInput(int selection) {
		switch(selection) {
			case 1: CreateRobot(); break;
			case 2: DisplayRobots(); break;
			case 3: DisplayRobots(); AlterRobot(3); break;
			case 4: DisplayRobots(); AlterRobot(4); break;
			case 5: DisplayRobots(); DistanceRobots(); break;
			default: return;
		}
	}
	
	private int getInt(int lower, int upper) {
		int selection = lower - 1;
		do{
			System.out.print("Please enter a value between [" + lower +","+ upper +"]: ");
			if(input.hasNextInt()) {
				selection = input.nextInt();
			}
		} while(selection > upper || selection < lower);
		
		return selection;
	}
	private int getInt() {
		while(true) {
			if(input.hasNextInt()) {
				return input.nextInt();
			}
			input.next();
		}
	}
	
	private void CreateRobot() {
		System.out.print("Please enter name of robot: ");
		String name = input.next();
		
		System.out.println("Please enter initial x,y position of the robot: ");
		int x = getInt();
		int y = getInt();
		
		System.out.print("Please enter the speed of the robot: ");
		int speed = getInt();
		
		System.out.print("Please enter the direction the robot is facing in degrees: ");
		int degrees = getInt();
		
		Robot newBot = new Robot(name, x, y, speed, degrees);
		robots.add(newBot);
		
		return;
	}
	
	private void DisplayRobots() {
		for(int i = 0, n = robots.size(); i < n; i++) {
			System.out.println((i+1) + ") " + robots.get(i));
		}
	}
	
	private Robot SelectRobot() {
		int size = robots.size();
		if(size > 0) {
			return robots.get(getInt(1, size) - 1);
		}
		else return null;
	}
	
	private void AlterRobot(int selection) {
		System.out.print("Which robot? ");
		Robot altered = SelectRobot();
		if(selection == 3) {
			System.out.println("Enter the amount of time the robot should move: ");
			altered.move(getInt());
		}
		else if(selection == 4) {
			System.out.println("Rotate clockwise [0] or counterclockwise [1]: ");
			altered.rotate(getInt(0,1) == 0);
		}
		else {
			System.out.println("Error in AlterRobot, Illegal call");
		}
		
		System.out.println("Updated status: " + altered);
		return;
	}
	
	private void DistanceRobots() {
		System.out.println("Please select a robot: ");
		Robot first = SelectRobot();
		System.out.println("Please select another robot: ");
		Robot second = SelectRobot();
		System.out.println("Distance = " + first.distance(second));
		
		return;
	}
}
