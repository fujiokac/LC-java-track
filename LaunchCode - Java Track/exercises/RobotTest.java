import static org.junit.Assert.*;
import java.util.ArrayList;

import org.junit.Test;

public class RobotTest {

	@Test
	public void testConstructor() {
		int x = 1, y = 2, speed = 4, deg = 90;
		Robot test = new Robot("test", x, y, speed, deg);
		assertEquals("PosX improperly set", x, test.getPosX());
		assertEquals("PosY improperly set", y, test.getPosY());
		assertEquals("Speed improperly set", speed, test.getSpeed());
		assertEquals("Direction improperly set/relayed", "north", test.getDirection());
	}
	
	@Test
	public void testMove() {
		int x = 1, y = 2, speed = 4, deg = 90;
		Robot test = new Robot("test", x, y, speed, deg);
		int time = 2;
		test.move(time);
		assertEquals("PosX improperly set", x, test.getPosX());
		assertEquals("PosY improperly set", y + time*speed, test.getPosY());
	}
	
	@Test
	public void testRotate() {
		Robot test = new Robot("test", 0, 0, 1, 0);
		test.rotate(true); // Should face south
		assertEquals("Rotate clockwise failed", "south", test.getDirection());
		test.rotate(false); // Should face east
		assertEquals("Rotate counterclockwise failed", "east", test.getDirection());
	}
	
	@Test
	public void testChangeSpeed() {
		int newSpeed = 3;
		Robot test = new Robot("test", 0, 0, 1, 0);
		test.changeSpeed(newSpeed);
		assertEquals("Speed improperly changed", newSpeed, test.getSpeed());
	}
	
	@Test
	public void testDistance() {
		Robot test1 = new Robot("test", 0, 0, 1, 0);
		Robot test2 = new Robot("test", 3, 4, 1, 0);
		assertTrue("Distance failed calculation", 5 == test1.distance(test2));
	}
	
	@Test
	public void testPusher() {
		PusherRobot pusher = new PusherRobot("pusher", 0, 0, 3, 1);
		Robot test = new Robot("test", 3, 4, 1, 0);
		pusher.Push(test);
		assertTrue("Distance should be 0", pusher.distance(test) == 0);
	}
	
	@Test
	public void testShover() {
		int strength = 10;
		ShoverRobot shover = new ShoverRobot("shover", 0, 0, 3, 1, strength);
		Robot test = new Robot("test", 3, 4, 1, 0);
		shover.Shove(test);
		assertTrue("Robot improperly shoved", shover.distance(test) == shover.getSpeed() * strength);
	}
	
	@Test
	public void testVacuum() {
		int range = 5;
		ArrayList<Robot> robots = new ArrayList<Robot>();
		robots.add(new Robot("out", range + 1, range + 1, 1, 0));
		robots.add(new Robot("in1", 1, 1, 1, 0));
		robots.add(new Robot("in2", -3, -4, 1, 0));
		VacuumRobot roomba = new VacuumRobot("roomba", 0, 0, 3, 1);
		roomba.move(robots, 0);
		//assertTrue("Distance should be 0", roomba.distance(test) == 0);
	}
}
