
public class Robot {
	private String name;
	private int posX, posY;
	private int speed;
	
	private int[][] dirs = {
			{1,0},
			{0,1},
			{-1,0},
			{0,-1}
	};
	// index of dirs
	private int direction;

	public Robot(String name, int posX, int posY, int speed, int degrees) {
		this.name = name;
		this.posX = posX;
		this.posY = posY;
		this.speed = speed;
		this.direction = (degrees % 360) / 90;
	}
	
	
	public void move(int time) {
		this.posX = this.posX + (this.speed * time * this.dirs[direction][0]);
		this.posY = this.posY + (this.speed * time * this.dirs[direction][1]);
		return;
	}
	
	public void rotate(boolean clockwise) {
		if(clockwise) {
			this.direction = ((this.direction - 1) % 4 + 4) % 4;
		}
		else {
			this.direction = (this.direction + 1) % 4;
		}
		
		return;
	}
	
	public void changeSpeed(int speed) {
		this.speed = speed;
	}
	
	
	public double distance(Robot other) {
		int X = Math.abs(this.posX - other.posX);
		int Y = Math.abs(this.posY - other.posY);
		
		return Math.sqrt(X*X + Y*Y);
	}
	
	public String status() {
		return "Robot " + this.name + ": (" + this.posX + "," + this.posY + ") Speed: " + this.speed + " Direction: " + this.getDirection();
	}
	
	public String getDirection() {
		switch(this.direction) {
			case 0: return "east";
			case 1: return "north";
			case 2: return "west";
			case 3: return "south";
			default: return "error";
		}
	}
	public int getSpeed() {
		return speed;
	}
	public int getPosX() {
		return posX;
	}
	public int getPosY() {
		return posY;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Robot robot1 = new Robot("robot 1", 0, 0, 10, 0);
		Robot robot2 = new Robot("robot 2", 0, 0, 3, 90);
		
		System.out.println(robot1.status());
		System.out.println(robot2.status());
		System.out.println("Distance: " + robot1.distance(robot2));
		robot1.move(3);
		robot2.move(1);
		
		robot1.rotate(true);
		robot2.rotate(false);
	
		
		robot1.move(1);
		robot2.move(1);
		
		System.out.println(robot1.status());
		System.out.println(robot2.status());
		System.out.println("Distance: " + robot1.distance(robot2));
	}

}
