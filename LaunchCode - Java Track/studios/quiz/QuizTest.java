package quiz;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class QuizTest {

	@Test
	public void testQ() {
		String question = "hello?";
		String answer = "yes";
		Question q = new Question(question, answer);
		assertTrue(q.hasAnswer());
		assertTrue(q.isCorrect(answer));
		assertTrue(answer.equals(q.getAnswer()));
		assertTrue(question.equals(q.getQtext()));
	}

	@Test
	public void testQEdit() {
		String question = "hello?";
		String answer = "yes";
		Question q = new Question(question, "");
		assertFalse(q.hasAnswer());
		question = "new question?";
		q.changeAnswer(answer);
		q.changeQuestion(question);
		assertTrue(q.isCorrect(answer));
		assertTrue(answer.equals(q.getAnswer()));
		assertTrue(question.equals(q.getQtext()));
	}
	
	@Test
	public void testQMC() {
		String question = "Hello?";
		String answer = "Hi.";
		ArrayList<String> choices = new ArrayList<String>();
		
		choices.add("placeholder1");
		choices.add("placeholder2");
		choices.add(answer);
		choices.add("placeholder4");
		
		QuestionMC q = new QuestionMC(question, answer, choices);
		
		assertTrue(q.hasAnswer());

		assertEquals(q.getNum(), choices.size());
		assertTrue(q.isCorrect(answer)); // int, char, string
		assertTrue(q.isCorrect(2));
		assertTrue(q.isCorrect('c'));
	}
	
	@Test
	public void testQuizMC() {
		QuizBuilder qb = new QuizBuilder();
		ArrayList<String> choices = new ArrayList<String>();
		choices.add("Hi");
		choices.add("Bye");
		String question = "Hello?";
		qb.addQuestion(question, choices.get(0), choices);
		
		Quiz q = qb.finalQuiz();
		assertTrue(question.equals(q.getCurrentQ()));
		ArrayList<String> getchoice = q.getChoices();
		assertEquals(getchoice.size(), choices.size());
		for(int i = 0; i < getchoice.size(); i++) {
			getchoice.get(i).equals(choices.get(i));
		}
		
		assertEquals(q.getCompleted(), 0);
		assertEquals(q.getIncomplete(), 1);
		assertEquals(q.getScore(), 0);
		assertFalse(q.isComplete());
		
		q.answerCurrent(choices.get(0));
		assertEquals(q.getScore(), 1);
		assertTrue(q.isComplete());
	}
	
	@Test
	public void testQuizQ() {
		QuizBuilder qb = new QuizBuilder();
		String q1 = "question1";
		String a1 = "answer1";
		String q2 = "question2";
		String a2 = "answer2";
		qb.addQuestion(q1, a1);
		qb.addQuestion(new Question(q2, a2));
		
		Quiz q = qb.finalQuiz();
		assertTrue(q1.equals(q.getCurrentQ()));
		
		q.answerCurrent("not the answer");
		assertEquals(q.getScore(), 0);
		
		assertTrue(q2.equals(q.getCurrentQ()));
		q.answerCurrent(a2);
		assertEquals(q.getScore(), 1);
		assertTrue(q.isComplete());
	}
	
}
