import static org.junit.Assert.*;

import org.junit.Test;

public class FractionTester {

	@Test
	public void testPrint() {
		Fraction half = new Fraction(1,2);
		String expected = "1/2";
		
		assertEquals("Print Failed", expected, half.toString());
	}
	
	// All other tests will fail if this test fails
	@Test
	public void testEqual() {
		Fraction half = new Fraction(1,2);
		Fraction nothalf = new Fraction(2,3);
		assertTrue("Result should be true", half.isEqual(half));
		assertFalse("Result should be false", half.isEqual(nothalf));
		
	}
	
	@Test
	public void testAdd() {
		Fraction half = new Fraction(1,2);
		Fraction twothirds = new Fraction(2,3);
		Fraction expected = new Fraction(7,6);
		Fraction wrong = new Fraction(1,4);
		
		assertTrue("1/2 + 2/3 should equal 7/6", half.add(twothirds).isEqual(expected));
		assertFalse("1/2 + 2/3 should not equal 1/4", half.add(twothirds).isEqual(wrong));
	}

	@Test
	public void testMult() {
		Fraction half = new Fraction(1,2);
		Fraction twothirds = new Fraction(2,3);
		Fraction expected = new Fraction(1,3);
		Fraction wrong = new Fraction(1,4);
		
		assertTrue("2/3 * 1/2 should equal 1/3", half.mult(twothirds).isEqual(expected));
		assertFalse("2/3 * 1/2 should not equal 1/4", half.mult(twothirds).isEqual(wrong));
	}
	
	@Test
	public void testReciprocal() {
		Fraction twothirds = new Fraction(2,3);
		Fraction expected = new Fraction(3,2);
		Fraction wrong = new Fraction(1,2);
		
		assertTrue("Reciprocal of 2/3 is 3/2", twothirds.getReciprocal().isEqual(expected));
		assertFalse("Reciprocal of 2/3 is not 1/2", twothirds.getReciprocal().isEqual(wrong));
	}
	
	@Test
	public void testSimplify() {
		Fraction half = new Fraction(3,6);
		Fraction expected = new Fraction(1,2);
		Fraction wrong = new Fraction(2,3);
		
		assertTrue("Simplify Failed", half.simplify().isEqual(expected));
		assertFalse("Simplify Failed", half.simplify().isEqual(wrong));
	}
	
	
}
