
public class PusherRobot extends Robot {

	public PusherRobot(String name, int posX, int posY, int speed, int degrees) {
		super(name, posX, posY, speed, degrees);
		// TODO Auto-generated constructor stub
	}
	
	public void Push(Robot other) {
		this.posX = other.getPosX();
		this.posY = other.getPosY();
		this.move(1);
		other.posX = this.posX;
		other.posY = this.posY;
		
		System.out.println("We are here to protect you.");
		return;
	}
	
}
