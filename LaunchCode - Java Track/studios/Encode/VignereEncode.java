package Encode;

public class VignereEncode implements Encodable {
	String output;
	char[] key;
	
	public VignereEncode(String key) {
		this.key = key.toLowerCase().toCharArray();
		output = "";
	}
	
	@Override
	public void encode(String input) {
		// TODO Auto-generated method stub
		char[] chars = input.toCharArray();
		int key_i = 0;
		for(char c : chars) {
			if(Character.isLetter(c)) {
				int k = key[key_i] - 'a';
				int adjust = 'a';
				if(Character.isUpperCase(c)) {
					adjust = 'A';
				}
				
				// Calculates new shifted letter by adding key & adjusting for ascii
	            c = (char) ((c + k - adjust) % 26 + adjust);
	            key_i = (key_i + 1) % key.length;
			}
		}
		
		output = new String(chars);
	}

	@Override
	public String getEncoded() {
		return output;
	}

}
