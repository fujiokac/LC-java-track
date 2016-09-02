
public class Robot {
	private String name;
	protected int posX, posY;
	protected int speed;
	
	private int[][] dirs = {
			{1,0},
			{0,1},
			{-1,0},
			{0,-1}
	};
	// index of dirs
	protected int direction;

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
	
	public String toString() {
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

	

}
