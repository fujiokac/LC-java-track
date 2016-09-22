package robot;

public class Robot {
	private String name;
	protected int posX, posY;
	protected int speed;
	private Direction direction;
	
	protected enum Direction {
		EAST(1,0),
		NORTH(0,1),
		WEST(-1,0),
		SOUTH(0,-1);
		
		private int x,y;
		private Direction(int x, int y) {
			this.x = x;
			this.y = y;
		}

		private int getX() {
			return x;
		}
		private int getY() {
			return y;
		}
		
		@Override public String toString() {
			return name().substring(0,1).toUpperCase() + name().substring(1).toLowerCase();
		}
	};

	public Robot(String name, int posX, int posY, int speed, int degrees) {
		this.name = name;
		this.posX = posX;
		this.posY = posY;
		this.speed = speed;
		this.direction = Direction.values()[(degrees % 360) / 90];
	}
	
	public Robot(String name, int posX, int posY, int speed, String direction) {
		this.name = name;
		this.posX = posX;
		this.posY = posY;
		this.speed = speed;
		this.direction = Direction.valueOf(direction.toUpperCase());
	}
	
	protected Robot(Robot copy) {
		this.name = copy.name;
		this.posX = copy.posX;
		this.posY = copy.posY;
		this.speed = copy.speed;
		this.direction = copy.direction;
	}
	
	protected void become(Robot target) {
		this.name = target.name;
		this.posX = target.posX;
		this.posY = target.posY;
		this.speed = target.speed;
		this.direction = target.direction;
	}
	
	public void move(int time) {
		this.posX = this.posX + (this.speed * time * this.direction.getX());
		this.posY = this.posY + (this.speed * time * this.direction.getY());
		return;
	}
	
	public void rotate(boolean clockwise) {
		if(clockwise) {
			this.direction = Direction.values()[((this.direction.ordinal() - 1) % 4 + 4) % 4];
		}
		else {
			this.direction = Direction.values()[(this.direction.ordinal() + 1) % 4];
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
		return direction.toString();
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
	public String getName() {
		return name;
	}

	public Robot clone() {
		return new Robot(this);
	}
}
