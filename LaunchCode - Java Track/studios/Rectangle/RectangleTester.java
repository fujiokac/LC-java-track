package Rectangle;
import static org.junit.Assert.*;

import org.junit.Test;

public class RectangleTester {

	@Test
	public void testConstructor() {
		int x = 1, y = 2;
		Rectangle test = new Rectangle(x, y);
		assertEquals("Length improperly set", x, test.getLength());
		assertEquals("Width improperly set", y, test.getWidth());
	}
	
	@Test
	public void testGetArea() {
		int x = 2, y = 3;
		Rectangle test = new Rectangle(x, y);
		assertEquals("Area improperly calculated", x * y, test.getArea());
	}
	
	@Test
	public void testGetPerimeter() {
		int x = 2, y = 3;
		Rectangle test = new Rectangle(x, y);
		assertEquals("Perimeter improperly calculated", 2*(x + y), test.getPerimeter());
	}
	
	@Test
	public void testIsSquare() {
		int x = 2, y = 3;
		Rectangle test = new Rectangle(x, y);
		assertEquals("isSquare should return false", x == y, test.isSquare());
		test = new Rectangle(x, x);
		assertEquals("isSquare should return true", true, test.isSquare());
	}
	
	@Test
	public void testIsSmaller() {
		int x = 2, y = 3, z = 4;
		Rectangle test1 = new Rectangle(x, y);
		Rectangle test2 = new Rectangle(y, z);
		assertEquals("test1 should be smaller than test2", true, test1.isSmaller(test2));
		assertEquals("test2 should be larger than test1", false, test2.isSmaller(test1));
		assertEquals("test1 should not be smaller than itself", false, test1.isSmaller(test1));
	}
	
	
	
}
