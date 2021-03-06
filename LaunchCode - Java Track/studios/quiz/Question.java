package quiz;

public class Question {
	private String Qtext;
	private String answer;
	
	public Question(String Qtext, String answer) {
		this.Qtext = Qtext;
		this.answer = answer.toLowerCase();
	}
	protected void changeAnswer(String answer) {
		this.answer = answer;
	}
	protected void changeQuestion(String question) {
		this.Qtext = question;
	}
	protected String getAnswer() {
		return answer;
	}
	
	public Boolean hasAnswer() {
		return !answer.equals("");
	}
	public String getQtext() {
		return Qtext;
	}
	
	public Boolean isCorrect(Object input) {
		if(input instanceof java.lang.String) {
			return answer.equals(((String)input).toLowerCase());
		}
		return false;
	}
}
