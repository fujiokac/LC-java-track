package quiz;

import java.util.ArrayList;

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
	
	public String getCurrentQ() {
		return questions.get(current).getQtext();
	}
	public ArrayList<String> getChoices() {
		if(questions.get(current) instanceof QuestionMC) {
			return ((QuestionMC)questions.get(current)).getChoices();
		}
		else return null;
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
