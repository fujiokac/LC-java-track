package Encode;

public class CaesarEncode implements Encodable {
	private String output;
	private int key;
	
	public CaesarEncode(int key) {
		this.key = key;
		output = "";
	}
	
	@Override
	public void encode(String input) {
		char[] chars = input.toCharArray();
		for(char c : chars) {
			 // If letter is within [A-Z,a-z]
	        if(Character.isLetter(c)) {
	            int adjust = (int) 'a';
	            
	            // Adjusts for uppercase
	            if(Character.isUpperCase(c)) {
	                adjust = (int)'A';
	            }
	            
	            // Calculates new shifted letter by adding key & adjusting for ascii
	            c = (char) ((c + key - adjust) % 26 + adjust);
	        }
		}
		output = new String(chars);
	}

	@Override
	public String getEncoded() {
		return output;
	}

}
