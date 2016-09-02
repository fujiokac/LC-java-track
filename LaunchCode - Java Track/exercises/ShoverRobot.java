
public class ShoverRobot extends Robot {
	int strength;
	
	public ShoverRobot(String name, int posX, int posY, int speed, int degrees, int strength) {
		super(name, posX, posY, speed, degrees);
		// TODO Auto-generated constructor stub
		this.strength = strength;
	}
	
	public void Shove(Robot other) {
		this.posX = other.getPosX();
		this.posY = other.getPosY();
		Robot temp = this;
		temp.move(strength);
		other.posX = temp.getPosX();
		other.posY = temp.getPosY();
		
		System.out.println("Pak Chooie Unf");
		return;
	}
	
}
