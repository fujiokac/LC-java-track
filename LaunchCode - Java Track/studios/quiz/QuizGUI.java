package quiz;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


public class QuizGUI {

	private JFrame frame;
	private Quiz quiz;
	private QuizBuilder QB;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					QuizGUI window = new QuizGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public QuizGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		
	    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	    frame.setSize(screenSize.width/3, screenSize.height/3);
	    Dimension windowSize = frame.getSize();
	    frame.setLocation(screenSize.width / 2 - windowSize.width / 2,
	            screenSize.height / 2 - windowSize.height / 2);
		// frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		QB = new QuizBuilder();
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		
		frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		tabbedPane.addTab("Quiz Builder", null, init_edit(), null);
		tabbedPane.addTab("Quiz", null, init_quiz(), null);
		
	}
	
	// Create the create & edit panel
	private JPanel init_edit() {
		// Initialize visual components
		JPanel pnl_cards = new JPanel(new CardLayout());
		pnl_cards.setMinimumSize(frame.getSize());
		pnl_cards.getLayout().minimumLayoutSize(pnl_cards);
		JPanel pnl_edit = new JPanel(new BorderLayout(0, 0));
		Box edit_menu = Box.createVerticalBox();
		DefaultListModel<String> listQuestion = new DefaultListModel<String>();
		JList<String> preview = new JList<String>(listQuestion);
		
		JPanel pnl_addQ = new JPanel(new BorderLayout(0, 0));
		Box SubmitCancel = Box.createHorizontalBox();
		Box buttons = Box.createVerticalBox();
		JPanel pnl_QAview = new JPanel(new BorderLayout(0, 0));
		JLabel lblQuestion = new JLabel();
		JLabel lblQTitle = new JLabel("Question: ");
		Box labels = Box.createHorizontalBox();
		DefaultListModel<String> listAnswer = new DefaultListModel<String>();
		JList<String> list = new JList<String>(listAnswer);
		list.setAlignmentX(Component.LEFT_ALIGNMENT);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		pnl_cards.add(pnl_edit, "Quiz Builder");
		pnl_cards.add(pnl_addQ, "New Question");
		pnl_cards.add(new JPanel(), "Locked");
		pnl_edit.add(edit_menu, BorderLayout.LINE_START);
		pnl_edit.add(preview, BorderLayout.CENTER);
		
		labels.add(lblQTitle);
		labels.add(lblQuestion);
		pnl_QAview.add(labels, BorderLayout.PAGE_START);
		pnl_QAview.add(list, BorderLayout.CENTER);
		JPanel pnl_container  = new JPanel(new BorderLayout(0, 0));
		pnl_container.add(buttons, BorderLayout.LINE_START);
		pnl_container.add(pnl_QAview, BorderLayout.CENTER);
		
		pnl_addQ.add(pnl_container, BorderLayout.CENTER);
		pnl_addQ.add(SubmitCancel, BorderLayout.PAGE_END);
		
		// Add/edit question
		JButton btnNewQ = new JButton("Edit Question");
		btnNewQ.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Prompt user
				String question =
						(String)JOptionPane.showInputDialog(frame,
								"Please enter a question.",
								"Add Question",
								JOptionPane.PLAIN_MESSAGE);
				// Update question
				lblQuestion.setText(question);
			}
		});
		buttons.add(btnNewQ);
		
		// Add answer
		JButton btnNewA = new JButton("Add Answer");
		btnNewA.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Prompt user
				String answer =
						(String)JOptionPane.showInputDialog(frame,
								"Please enter an answer.",
								"Add Answer",
								JOptionPane.PLAIN_MESSAGE);
				// Add answer to list
				listAnswer.add(listAnswer.size(), answer);
			}
		});
		buttons.add(btnNewA);
		
		// Edits existing answers
		JButton btnEdit = new JButton("Edit Answer");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int selected = getSelected(frame, list, "You must select an answer to edit", "Nothing selected");
				if(selected >= 0) {
					String previous = listAnswer.getElementAt(selected);
					// Prompt user
					String answer =
							(String)JOptionPane.showInputDialog(frame,
									"Current answer is: "+ previous +
									"\nReplace with: ",
									"Edit Answer",
									JOptionPane.PLAIN_MESSAGE);
					// Set new answer
					listAnswer.setElementAt(answer, selected);
				}
			}
		});
		buttons.add(btnEdit);
		
		// Validates input and adds question to quiz
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Get previously input values
				String question = lblQuestion.getText();
				ArrayList<String> answers = Collections.list(listAnswer.elements());
				// Check that question is completed
				if(question.isEmpty() || answers.isEmpty()) {
					JOptionPane.showMessageDialog(frame,
							"Question has not been completed.",
							"Submit failed",
						    JOptionPane.ERROR_MESSAGE);
				}
				else {
					// Check for multiple choice
					if(answers.size() > 1) {
						// Multiple Choice
						// prompt for correct answer
						int selected = getSelected(frame, 
								list, 
								"Please select the correct answer from the list", 
								"No answer selected");
						// User needs to select an answer
						if(selected < 0) return;
						int confirm = JOptionPane.showConfirmDialog(frame,
								"Is the correct answer selected?",
								"Answer Type",
								JOptionPane.YES_NO_OPTION);
						// User needs to select correct answer
						if(confirm != 0) return;
						
						String answer = listAnswer.getElementAt(selected);
						QB.addQuestion(question, answer, answers);
					}
					else {
						// Fill in the Blank
						QB.addQuestion(question, answers.get(0));
					}
					listQuestion.addElement(question);
					// Clear data and return
					lblQuestion.setText("");
					listAnswer.clear();
					CardLayout c = (CardLayout)pnl_cards.getLayout();
					c.first(pnl_cards);
				}
			}
		});
		
		// Clears input and returns to main card
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Delete existing input
				lblQuestion.setText("");
				listAnswer.clear();
				// Return to previous window
				CardLayout c = (CardLayout)pnl_cards.getLayout();
				c.first(pnl_cards);
			}
		});
		
		
		
		// Button to add question
		JButton btnAddQ = new JButton("Add Question");
		btnAddQ.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CardLayout c = (CardLayout)pnl_cards.getLayout();
				c.next(pnl_cards);
			}
		});
		edit_menu.add(btnAddQ);
		
		// Button to finalize quiz and blank quizbuilder tab
		JButton btnFinal = new JButton("Finalize");
		btnFinal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				quiz = QB.finalQuiz();
				CardLayout c = (CardLayout)pnl_cards.getLayout();
				c.last(pnl_cards);
			}
		});
		edit_menu.add(btnFinal);

		
		SubmitCancel.add(btnSubmit);
		SubmitCancel.add(btnCancel);
		
		
		return pnl_cards;
		
	}
	
	private JPanel init_quiz() {
		JPanel pnl_cards = new JPanel(new CardLayout());
		pnl_cards.setMinimumSize(frame.getSize());
		pnl_cards.getLayout().minimumLayoutSize(pnl_cards);
		
		JButton btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			private String str_answer;
			public void actionPerformed(ActionEvent arg0) {
				// Check if quiz has been finalized
				if(quiz == null) {
					quiz = QB.finalQuiz();
					JOptionPane.showMessageDialog(frame,
						    "Quiz has not been finalized.",
						    "Warning",
						    JOptionPane.WARNING_MESSAGE);
				}
				int num_Q = quiz.getNum();
				// Check that quiz has questions
				if(num_Q == 0) {
					JOptionPane.showMessageDialog(frame,
						    "Quiz is empty.",
						    "Error",
						    JOptionPane.ERROR_MESSAGE);
				}
				else {
					// Initialize visual components
					// pnl_quiz is quiz interface
					JPanel pnl_quiz = new JPanel(new BorderLayout(0,0));
					// pnl_question is question interface
					JPanel pnl_question = new JPanel(new BorderLayout(0,0));
					pnl_cards.add(pnl_quiz, "Quiz");
					pnl_cards.add(pnl_question, "Question");
					CardLayout c = (CardLayout)pnl_cards.getLayout();
					c.next(pnl_cards);
					
					JButton btnNext = new JButton("Next Question");
					btnNext.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							CardLayout c = (CardLayout)pnl_cards.getLayout();
							c.last(pnl_cards);
						}
					});
					pnl_quiz.add(btnNext, BorderLayout.PAGE_END);
					
					// This part needs to update
					Box box_stats = Box.createVerticalBox();
					JLabel lbl_score = new JLabel("Score: " + Integer.toString(quiz.getScore()));
					JLabel lbl_num = new JLabel("Total: " + Integer.toString(quiz.getNum()));
					JLabel lbl_answered = new JLabel("Answered: " + Integer.toString(quiz.getCompleted()));
					JLabel lbl_remaining = new JLabel("Remaining: " + Integer.toString(quiz.getIncomplete()));
					
					box_stats.add(lbl_score);
					box_stats.add(lbl_num);
					box_stats.add(lbl_answered);
					box_stats.add(lbl_remaining);
					pnl_quiz.add(box_stats, BorderLayout.CENTER);
					
					// Panel displays current question and choices
					str_answer = "";
					HashMap<String,Object> current = quiz.getCurrent();
					JPanel pnl_current = new JPanel(new BorderLayout(0,0));
					JLabel lbl_question = new JLabel((String)current.get("Question"));
					pnl_current.add(lbl_question, BorderLayout.PAGE_START);
					DefaultListModel<String> lst_answer = new DefaultListModel<String>();
					JTextField txt_answer = new JTextField(30);
					if(current.get("Choices") instanceof ArrayList<?>) {
						@SuppressWarnings (value="unchecked")
						ArrayList<String> choices = (ArrayList<String>)current.get("Choices");
						JList<String> list = new JList<String>(lst_answer);
						list.setAlignmentX(Component.LEFT_ALIGNMENT);
						list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
						for(String choice : choices) {
							lst_answer.addElement(choice);
						}
						pnl_current.add(list, BorderLayout.CENTER);
						int selected = list.getSelectedIndex();
						if(selected >= 0) {
							str_answer = lst_answer.elementAt(selected);
						}
					}
					else {
						pnl_current.add(txt_answer, BorderLayout.AFTER_LAST_LINE);
						str_answer = txt_answer.getText();
					}
					
					Box box_subcan = Box.createHorizontalBox();
					JButton btnSubmit = new JButton("Submit");
					btnSubmit.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							// Validate answer
							quiz.answerCurrent((Object)str_answer);
							// Clear current data
							lbl_question.setText("");
							lst_answer.clear();
							txt_answer.setText("");
							// Return to main card
							CardLayout c = (CardLayout)pnl_cards.getLayout();
							c.previous(pnl_cards);
						}
					});
					
					JButton btnCancel = new JButton("Cancel");
					btnCancel.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							CardLayout c = (CardLayout)pnl_cards.getLayout();
							c.previous(pnl_cards);
						}
					});
					box_subcan.add(btnSubmit);
					box_subcan.add(btnCancel);
					pnl_question.add(pnl_current, BorderLayout.CENTER);
					pnl_question.add(box_subcan, BorderLayout.PAGE_END);
					
				}
			}
		});
		pnl_cards.add(btnStart, BorderLayout.CENTER);
		
			
		return pnl_cards;
	}

	
	// Prompts user for integer value, does not handle bad input
	private int getInt(JFrame frame, String prompt, String title) {
		String input = "";
		int output = 0;
		// do {
			input = (String) JOptionPane.showInputDialog(frame, prompt, title, JOptionPane.PLAIN_MESSAGE);
			output = Integer.parseInt(input);
			// Catch exception here
		// } while(output < 1);
		
		return output;
	}
	// Returns index of selected JList item, Displays error with message & title if none selected
	private int getSelected(JFrame frame, JList list, String message, String title) {
		int selected = list.getSelectedIndex();
		if(selected < 0) {
			JOptionPane.showMessageDialog(frame,
					message,
					title,
				    JOptionPane.ERROR_MESSAGE);
		}
		return selected;
	}
}
