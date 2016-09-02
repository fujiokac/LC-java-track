import java.util.ArrayList;

public class VacuumRobot extends Robot {

	private int strength;
	
	public VacuumRobot(String name, int posX, int posY, int degrees, int strength) {
		super(name, posX, posY, 1, degrees);
		// TODO Auto-generated constructor stub
		this.strength = strength;
	}

	private void Vacuum(ArrayList<Robot> robots) {
		for(Robot robot : robots) {
			if(this.distance(robot) < strength) {
				robot.posX = this.posX;
				robot.posY = this.posY;
			}
		}
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
}
