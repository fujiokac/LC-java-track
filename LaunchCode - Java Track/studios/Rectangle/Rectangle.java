package Rectangle;

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
	
	public String toString() {
		String output = "";
		for(int l = 0; l < length; l++) {
			for(int w = 0; w < width; w++) {
				output += 'X';
			}
			output += System.lineSeparator();
		}
		return output;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Rectangle one = new Rectangle(2,2);
		Rectangle two = new Rectangle(5,6);
		
		System.out.println(one);
		System.out.println(two);
	}

}
