package quiz;

import java.util.ArrayList;

public class QuizBuilder extends Quiz{
	
	public QuizBuilder() {
		super();
	}
	
	public void addQuestion(String question, String answer) {
		questions.add(new Question(question, answer));
	}
	
	public void addQuestion(String question, String answer, ArrayList<String> choices) {
		questions.add(new QuestionMC(question, answer, choices));
	}
	
	public void addQuestion(Question question) {
		questions.add(question);
	}
	
	
	public Quiz finalQuiz() {
		return (Quiz)this;
	}
}
