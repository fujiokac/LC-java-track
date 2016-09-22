package Encode;

public interface Encodable {
	public static enum Alphabet {
	    A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z;

	    protected static int getNum(String input) {
	        return valueOf(input).ordinal();
	    }

	    protected static int getNum(char input) {
	        return valueOf(String.valueOf(input)).ordinal();
	    }
	    
		protected static int AtoI(char input) {
			return getNum(Character.toUpperCase(input));
		}
	}
	
	public void encode(String input);
	
	public String getEncoded();
}
