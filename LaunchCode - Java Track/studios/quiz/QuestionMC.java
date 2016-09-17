package quiz;

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
	    
		protected static int AtoI(char input) {
			return getNum(Character.toUpperCase(input));
		}
	}
	
	public QuestionMC(String Qtext, String answer, ArrayList<String> choices) {
		super(Qtext, answer);
		this.choices = new ArrayList<String>();
		for(int i = 0; i < choices.size(); i++) {
			String choice = choices.get(i);
			this.choices.add(choice.toLowerCase());
		}
	}
	
	@Override
	public Boolean hasAnswer() {
		System.out.println(choices);
		System.out.println(super.getAnswer());
		return choices.contains(super.getAnswer());
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
			return super.isCorrect(input);
		}
		else if(input instanceof Character) {
			return super.isCorrect(choices.get(Alphabet.AtoI((Character)input)).toLowerCase());
		}
		else return false;
	}
	
	protected ArrayList<String> getChoices() {
		return choices;
	}
}
