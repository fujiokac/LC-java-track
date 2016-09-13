package quiz;

import java.util.ArrayList;
import java.util.HashMap;

public class Quiz {
	protected ArrayList<Question> questions;
	private int score, current;
	private Boolean completed, previous;
	
	public Quiz() {
		questions = new ArrayList<Question>();
		score = 0;
		current = 0;
		completed = false;
	}
	
	public HashMap<String, Object> getCurrent() {
		return questions.get(current).output();
	}
	
	public void answerCurrent(Object input) {
		Boolean correct = false;
		
		if(!completed) {
			correct = questions.get(current).isCorrect(input);
			if(correct) {
				score++;
			}
			current++;
			if(current == questions.size()) {
				completed = true;
			}
		}
		
		previous = correct;
	}
	
	public int getScore() {
		return score;
	}
	
	public int getNum() {
		return questions.size();
	}
	
	public int getCompleted() {
		return current;
	}
	
	public int getIncomplete() {
		return getNum() - current;
	}
	
	public Boolean isComplete() {
		return completed;
	}
	public Boolean lastAnswer() {
		return previous;
	}
}
