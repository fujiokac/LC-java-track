package robot;
import java.util.ArrayList;
import java.util.Collections;

public class VacuumRobot extends Robot implements RobotBehavior{

	private int strength;
	
	public VacuumRobot(String name, int posX, int posY, int degrees, int strength) {
		super(name, posX, posY, 1, degrees);
		this.strength = strength;
	}
	
	public VacuumRobot(String name, int posX, int posY, String direction, int strength) {
		super(name, posX, posY, 1, direction);
		this.strength = strength;
	}
	
	public VacuumRobot(Robot robot, int strength) {
		super(robot);
		this.strength = strength;
	}
	
	public VacuumRobot(VacuumRobot robot) {
		super(robot);
		this.strength = robot.strength;
	}

	private void Vacuum(ArrayList<Robot> robots) {
		for(Robot robot : robots) {
			if(this.distance(robot) < strength) {
				robot.posX = this.posX;
				robot.posY = this.posY;
			}
		}
	}
	
	public VacuumRobot clone() {
		return new VacuumRobot(this);
	}
	
	public void move(ArrayList<Robot> robots, int time) {
		this.Vacuum(robots);
		for(int i = 0; i < time; i++) {
			this.move(1);
			this.Vacuum(robots);
		}
		System.out.println("I am the terrible secret of space.");
		
		return;
	}

	@Override
	public boolean doNextMove(ArrayList<Robot> robots) {
		
		// Initialize all potential futures
		ArrayList<VacuumRobot> potential = new ArrayList<VacuumRobot>();
		potential.add(this.clone());
		potential.add(this.clone());
		potential.add(this.clone());
		potential.add(this.clone());
		
		// Track total distances from other robots
		double[] distances = {0,0,0,0};
		// ArrayList to temporarily hold new robot positions
		// Turn and move once in all possible directions
		for(int i = 0; i < potential.size(); i++) {
			ArrayList<Robot> vacuumed = new ArrayList<Robot>();
			for(Robot robot : robots) {
				vacuumed.add(robot.clone());
			}
			for(int j = i; j > 0; j--) {
				potential.get(i).rotate(true);
			}
			// Vacuum & Calculate total distances
			potential.get(i).move(vacuumed, 1);
			for(Robot robot : vacuumed) {
				distances[i] += robot.distance(potential.get(i));
			}
		}
			
		// Determine smallest distance
		int min = 0;
		for(int i = 1; i < distances.length; i++) {
			if(distances[min] > distances[i]) {
				min = i;
			}
		}
		// "Become" robot with largest distance
		become(potential.get(min));
		
		// Check all robots vacuumed
		return distances[min] == 0.0;
	}
}
