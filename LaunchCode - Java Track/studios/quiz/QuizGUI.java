package quiz;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


public class QuizGUI {

	private JFrame frame;
	private Quiz quiz;
	private QuizBuilder QB;
	
	private class JQuiz extends JPanel {
		private Box box_stats;
		private JLabel lbl_question = new JLabel();
		private DefaultListModel<String> lst_answer = new DefaultListModel<String>();
		private JList<String> list = new JList<String>(lst_answer);
		private JTextField txt_answer = new JTextField(30);
		private JPanel pnl_question = new JPanel(new BorderLayout(0,0));
		private JButton btnSubmit = new JButton("Submit");
		
		// Initializes visual components, call update() to populate
		public JQuiz() {
			super(new BorderLayout(0,0));
			box_stats = Box.createVerticalBox();
			box_stats.add( new JLabel("Score: "));
			box_stats.add(new JLabel("Total: "));
			box_stats.add(new JLabel("Answered: "));
			box_stats.add(new JLabel("Remaining: "));
			this.add(box_stats, BorderLayout.LINE_START);
			this.add(pnl_question, BorderLayout.CENTER);
			JPanel pnl_answers = new JPanel(new BorderLayout(0,0));
			pnl_answers.add(txt_answer, BorderLayout.PAGE_START);
			pnl_answers.add(list, BorderLayout.CENTER);
			pnl_question.add(lbl_question, BorderLayout.PAGE_START);
			pnl_question.add(pnl_answers, BorderLayout.CENTER);
			pnl_question.add(btnSubmit, BorderLayout.PAGE_END);
			list.setAlignmentX(Component.LEFT_ALIGNMENT);
			list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			
			btnSubmit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if(quiz != null) {
						// Validate answer
						String answer = getAnswer();
						if(!answer.isEmpty()) {
							quiz.answerCurrent(answer);
							update();
						}
					}
					
				}
			});
		}
		
		// Reads and returns input answer
		private String getAnswer() {
			if(lst_answer.isEmpty()) {
				return txt_answer.getText();
			}
			int selected = getSelected(frame, list, "You must select an answer", "Invalid answer");
			if(selected >= 0) {
				return lst_answer.elementAt(selected);
			}
			else return "";
			
		}
		
		// Clear and update current data
		protected void update() {
			if(quiz != null) {
				if(!quiz.isComplete()) {
					// Loads multiple choice answers
					loadList();
					// Sets new question
					lbl_question.setText(quiz.getCurrentQ());
					// Blanks text input
					txt_answer.setText("");
					// Sets visibility of appropriate answer input
					if(lst_answer.isEmpty()) {
						list.setVisible(false);
						txt_answer.setVisible(true);
					}
					else {
						list.setVisible(true);
						txt_answer.setVisible(false);
					}
				}
				else {
					// When quiz is completed
					// Very basic, implement more here
					pnl_question.removeAll();
					pnl_question.revalidate();
					pnl_question.repaint();
					JLabel lbl_complete = new JLabel("Quiz Complete");
					//lbl_complete.setHorizontalTextPosition(SwingConstants.CENTER);
					pnl_question.add(lbl_complete, BorderLayout.PAGE_START);
				}
				loadStats();
				
			}
		}
		
		// Loads Jlist & DefaultListModel with available multiple choice answers
		private void loadList() {
			lst_answer.clear();
			ArrayList<String> choices;
			if((choices = quiz.getChoices()) != null) {
				for(String choice : choices) {
					lst_answer.addElement(choice);
				}
			}
		}
		
		// Updates Quiz statistics
		private void loadStats() {
			Component[] comps = box_stats.getComponents();
			((JLabel)comps[0]).setText("Score: " + Integer.toString(quiz.getScore()));
			((JLabel)comps[1]).setText("Total: " + Integer.toString(quiz.getNum()));
			((JLabel)comps[2]).setText("Answered: " + Integer.toString(quiz.getCompleted()));
			((JLabel)comps[3]).setText("Remaining: " + Integer.toString(quiz.getIncomplete()));
		}

	}

	private class JBuilder extends JPanel {
		private JLabel lblQuestion = new JLabel();
		private JLabel lblQTitle = new JLabel("Question: ");
		
		private DefaultListModel<String> listQuestion = new DefaultListModel<String>();
		private JList<String> preview = new JList<String>(listQuestion);
		private DefaultListModel<String> listAnswer = new DefaultListModel<String>();
		private JList<String> list = new JList<String>(listAnswer);
		
		private Box buttons = Box.createVerticalBox();
		private Box edit_menu = Box.createVerticalBox();
		private Box SubmitCancel = Box.createHorizontalBox();
		
		
		
		public JBuilder() {
			super(new CardLayout());
			this.setMinimumSize(frame.getSize());
			this.getLayout().minimumLayoutSize(this);
			
			JPanel pnl_edit = new JPanel(new BorderLayout(0, 0));
			JPanel pnl_addQ = new JPanel(new BorderLayout(0, 0));
			JPanel pnl_QAview = new JPanel(new BorderLayout(0, 0));
			JPanel pnl_container  = new JPanel(new BorderLayout(0, 0));
			
			Box labels = Box.createHorizontalBox();
			
			list.setAlignmentX(Component.LEFT_ALIGNMENT);
			list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			
			this.add(pnl_edit, "Quiz Builder");
			this.add(pnl_addQ, "New Question");
			this.add(new JPanel(), "Locked");
			
			pnl_edit.add(edit_menu, BorderLayout.LINE_START);
			pnl_edit.add(preview, BorderLayout.CENTER);
			
			labels.add(lblQTitle);
			labels.add(lblQuestion);
			init_buttons();
			pnl_QAview.add(labels, BorderLayout.PAGE_START);
			pnl_QAview.add(list, BorderLayout.CENTER);
			pnl_container.add(buttons, BorderLayout.LINE_START);
			pnl_container.add(pnl_QAview, BorderLayout.CENTER);
			pnl_addQ.add(pnl_container, BorderLayout.CENTER);
			pnl_addQ.add(SubmitCancel, BorderLayout.PAGE_END);
		}
		
		private void first_card() {
			CardLayout c = (CardLayout)this.getLayout();
			c.first(this);
		}
		
		private void next_card() {
			CardLayout c = (CardLayout)this.getLayout();
			c.next(this);
		}
		
		private void last_card() {
			CardLayout c = (CardLayout)this.getLayout();
			c.last(this);
		}
		
		private void init_buttons() {
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
					submit();
					// Clear data and return
					lblQuestion.setText("");
					listAnswer.clear();
					CardLayout c = (CardLayout)JBuilder.this.getLayout();
					c.first(JBuilder.this);
				}
			});
			
			// Clears input and returns to main card
			JButton btnCancel = new JButton("Cancel");
			btnCancel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					// Delete existing input
					lblQuestion.setText("");
					listAnswer.clear();
					// Return to main window
					first_card();
				}
			});
			
			// Button to add question
			JButton btnAddQ = new JButton("Add Question");
			btnAddQ.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					next_card();
				}
			});
			edit_menu.add(btnAddQ);
			
			// Button to finalize quiz and blank quizbuilder tab
			JButton btnFinal = new JButton("Finalize");
			btnFinal.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					quiz = QB.finalQuiz();
					last_card();
				}
			});
			edit_menu.add(btnFinal);

			SubmitCancel.add(btnSubmit);
			SubmitCancel.add(btnCancel);
			
		}
		
		private void submit() {
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
			}
		}
	}
	
	private JQuiz jquiz;
	private JBuilder jbuild;
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

	// Code to set default font by Romain Hippeau
	// https://stackoverflow.com/users/313137/romain-hippeau
	public static void setUIFont (javax.swing.plaf.FontUIResource f){
		java.util.Enumeration keys = UIManager.getDefaults().keys();
		while (keys.hasMoreElements()) {
			Object key = keys.nextElement();
			Object value = UIManager.get (key);
			if (value != null && value instanceof javax.swing.plaf.FontUIResource) {
				UIManager.put (key, f);
			}
	    }
	}
	
	/**
	 * Create the application.
	 */
	public QuizGUI() {
		initialize();
		QB = new QuizBuilder();
		// Fills QuizBuilder with test input
		QB.testBuild();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setUIFont (new javax.swing.plaf.FontUIResource("Verdana",Font.PLAIN,20));
		frame = new JFrame();
		
	    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	    frame.setSize(screenSize.width/3, screenSize.height/3);
	    Dimension windowSize = frame.getSize();
	    frame.setLocation(screenSize.width / 2 - windowSize.width / 2,
	            screenSize.height / 2 - windowSize.height / 2);
		// frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		
		frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		jquiz = new JQuiz();
		jbuild = new JBuilder();
		tabbedPane.addTab("Quiz Builder", null, jbuild, null);
		tabbedPane.addTab("Quiz", null, init_quiz(), null);
		
	}
	
	private JPanel init_quiz() {
		// Initialize visual components
		JPanel pnl_cards = new JPanel(new CardLayout());
		pnl_cards.setMinimumSize(frame.getSize());
		pnl_cards.getLayout().minimumLayoutSize(pnl_cards);
		
		JButton btnStart = new JButton("Start");
		pnl_cards.add(btnStart, BorderLayout.CENTER);
		pnl_cards.add(jquiz, "Quiz");
		
		btnStart.addActionListener(new ActionListener() {
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
					jquiz.update();
					CardLayout c = (CardLayout)pnl_cards.getLayout();
					c.next(pnl_cards);
				}
			}
		});
			
		return pnl_cards;
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
