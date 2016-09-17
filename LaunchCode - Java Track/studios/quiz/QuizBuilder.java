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
	
	public void testBuild() {
		ArrayList<String> choices = new ArrayList<String>();
		choices.add("Hi");
		choices.add("Bye");
		this.addQuestion("Hello?", choices.get(0), choices);
		this.addQuestion("Goodbye", "ok");
		
	}
	
	
	public Quiz finalQuiz() {
		return (Quiz)this;
	}
}
