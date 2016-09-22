package robot;

import java.util.ArrayList;

import robot.Robot.Direction;

public class RunawayRobot extends Robot implements RobotBehavior {

	public RunawayRobot(String name, int posX, int posY, int speed, String direction) {
		super(name, posX, posY, speed, direction);
	}
	
	public RunawayRobot(String name, int posX, int posY, int speed, int degrees) {
		super(name, posX, posY, speed, degrees);
	}
	
	public RunawayRobot(Robot robot) {
		super(robot);
	}
	
	@Override
	public boolean doNextMove(ArrayList<Robot> robots) {
		// TODO Auto-generated method stub
		// Move away from other robots
		
		// Initialize all potential futures
		ArrayList<Robot> potential = new ArrayList<Robot>();
		potential.add(this.clone());
		potential.add(this.clone());
		potential.add(this.clone());
		potential.add(this.clone());
		
		// Turn and move once in all possible directions
		for(int i = 0; i < potential.size(); i++) {
			for(int j = i; j > 0; j--) {
				potential.get(i).rotate(true);
			}
			potential.get(i).move(1);
		}
		
		// Track total distances from other robots
		double[] distances = {0,0,0,0};
		// Calculate total distances
		for(Robot robot : robots) {
			for(int i = 0; i < potential.size(); i++) {
				distances[i] += robot.distance(potential.get(i));
			}
		}
		// Determine largest distance
		int max = 0;
		for(int i = 1; i < distances.length; i++) {
			if(distances[max] < distances[i]) {
				max = i;
			}
		}
		// "Become" robot with largest distance
		become(potential.get(max));
		
		return true;
	}

}
