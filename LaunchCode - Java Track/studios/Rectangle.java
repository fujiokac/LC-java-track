
public class Rectangle {
	private int length, width;
	
	public Rectangle(int length, int width) {
		this.length = length;
		this.width = width;
	}
	
	public int getLength() {
		return length;
	}
	public int getWidth() {
		return width;
	}
	
	public int getArea() {
		return length * width;
	}
	public int getPerimeter() {
		return 2 * length + 2 * width;
	}
	
	public boolean isSquare() {
		return length == width;
	}
	
	public boolean isSmaller(Rectangle rect) {
		return this.getArea() < rect.getArea();
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Rectangle one = new Rectangle(2,2);
		Rectangle two = new Rectangle(1,3);
		
		System.out.println(one.getArea() +" "+ one.getPerimeter() +" "+ one.isSquare() +" "+ one.isSmaller(two));
		System.out.println(two.getArea() +" "+ two.getPerimeter() +" "+ two.isSquare() +" "+ two.isSmaller(one));
	}

}
