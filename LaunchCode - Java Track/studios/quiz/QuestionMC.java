package quiz;

import java.util.HashMap;
import java.util.ArrayList;

public class QuestionMC extends Question {
	private ArrayList<String> choices;
	
	private enum Alphabet {
	    A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z;

	    protected static int getNum(String input) {
	        return valueOf(input).ordinal();
	    }

	    protected static int getNum(char input) {
	        return valueOf(String.valueOf(input)).ordinal();
	    }
	    protected static int AtoI(String input) {
			return getNum(input.substring(0,1).toUpperCase());
		}
		
		protected static int AtoI(char input) {
			return getNum(Character.toUpperCase(input));
		}
	}
	
	public QuestionMC(String Qtext, String answer, ArrayList<String> choices) {
		super(Qtext, answer);
		this.choices = choices;
	}
	
	public QuestionMC(String Qtext) {
		super(Qtext, "");
		choices = new ArrayList<String>();
	}
	
	@Override
	public Boolean hasAnswer() {
		return choices.contains(super.getAnswer());
	}
	protected void addChoice(String choice) {
		choices.add(choice);
	}
	
	// Returns number of choices
	public int getNum() {
		return choices.size();
	}
	
	// Accepts inputs of type String, char, & int
	@Override
	public Boolean isCorrect(Object input) {
		if(input instanceof Integer) {
			return super.isCorrect(choices.get((int)input).toLowerCase());
		}
		else if(input instanceof String) {
			return super.isCorrect(choices.get(Alphabet.AtoI((String)input)).toLowerCase());
		}
		else if(input instanceof Character) {
			return super.isCorrect(choices.get(Alphabet.AtoI((Character)input)).toLowerCase());
		}
		else return false;
	}
	
	
	@Override
	public HashMap<String, Object> output() {
		HashMap<String, Object> output = super.output();
		output.put("Choices", choices);
		return output;
	}
}
