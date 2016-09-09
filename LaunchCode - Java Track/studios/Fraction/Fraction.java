package Fraction;

public class Fraction {
	private int num, den;
	
	public Fraction(int num, int den) {
		this.num = num;
		this.den = den;
	}
	
	public Fraction add(Fraction other) {
		return new Fraction(this.num * other.den + other.num * this.den, this.den * other.den).simplify();
	}
	
	public Fraction mult(Fraction other) {
		return new Fraction(this.num * other.num, this.den * other.den).simplify();
	}
	
	public Fraction getReciprocal() {
		return new Fraction(den, num).simplify();
	}
	
	public Fraction simplify() {
		int gcd = getGCD(num, den);
		return gcd > 1 ? new Fraction(num / gcd, den / gcd) : this;
	}
	
	private int getGCD(int a, int b) {
		return b == 0 ? a : getGCD(b, a%b);
	}
	
	public String toString() {
		return num +"/"+ den;
	}
	
	public boolean isEqual(Fraction other) {
		return this.num == other.num && this.den == other.den;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Fraction fiveten = new Fraction(5, 10);
		Fraction half = fiveten.simplify();
		Fraction recip = fiveten.getReciprocal();
		
		System.out.println(fiveten +" + "+ half +" = "+ fiveten.add(half));
		System.out.println(fiveten +" * "+ recip +" = "+ fiveten.mult(recip));
		
	}

}
