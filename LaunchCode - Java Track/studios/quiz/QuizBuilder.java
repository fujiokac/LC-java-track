package quiz;

import java.util.ArrayList;

public class QuizBuilder extends Quiz{
	private Boolean currentMC;
	
	public QuizBuilder() {
		super();
		currentMC = false;
	}
	
	public void addQuestion(String question, String answer) {
		questions.add(new Question(question, answer));
		currentMC = false;
	}
	
	public void addMC(String question, String answer, ArrayList<String> choices) {
		questions.add(new QuestionMC(question, answer, choices));
		currentMC = true;
	}
	public Boolean addQ(String question, Boolean isMC) {
		if(questions.isEmpty() || questions.get(questions.size()-1).hasAnswer()) {
			if(isMC) {
				questions.add(new QuestionMC(question));
				currentMC = true;
			}
			else {
				questions.add(new Question(question, ""));
				currentMC = false;
			}
			
			return true;
		}
		else return false;
	}
	
	public Boolean currentMC() {
		return currentMC;
	}
	
	public Boolean addC(String choice, Boolean correct) {
		if(!questions.isEmpty()) {
			int current = questions.size() - 1;
			if(currentMC) {
				((QuestionMC)questions.get(current)).addChoice(choice);
			}
			if(correct) {
				questions.get(current).changeAnswer(choice);
			}
			return true;
		}
		else return false;
	}
	
	public Quiz finalQuiz() {
		return (Quiz)this;
	}
}
