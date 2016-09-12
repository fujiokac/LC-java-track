package quiz;

import java.awt.*;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Collections;

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
		JPanel QBtab = init_edit();
		//QBtab.setLayout(new BorderLayout(0, 0));
		tabbedPane.addTab("Quiz Builder", null, QBtab, null);
		
		
	}
	
	// Create the create & edit panel
	private JPanel init_edit() {
		// Initialize visual components
		JPanel cards = new JPanel(new CardLayout());
		cards.setMinimumSize(frame.getSize());
		cards.getLayout().minimumLayoutSize(cards);
		JSplitPane tab_edit = new JSplitPane();
		Box edit_menu = Box.createVerticalBox();
		
		JPanel AddQDialog = new JPanel();
		AddQDialog.setLayout(new BorderLayout(0, 0));
		Box SubmitCancel = Box.createHorizontalBox();
		Box buttons = Box.createVerticalBox();
		Box QAview = Box.createVerticalBox();
		JLabel lblQuestion = new JLabel();
		DefaultListModel<String> listAnswer = new DefaultListModel<String>();
		JList<String> list = new JList<String>(listAnswer);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		cards.add(tab_edit, "QuizBuilder");
		cards.add(AddQDialog, "New Question");
		tab_edit.setLeftComponent(edit_menu);
		// tab_edit.setRightComponent(preview);
		
		QAview.add(lblQuestion);
		QAview.add(list);
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, buttons, QAview);
		// Keep divider centered
		splitPane.setDividerLocation(.1);
		splitPane.setResizeWeight(.1);
		splitPane.setEnabled(false);
		
		AddQDialog.add(splitPane, BorderLayout.CENTER);
		AddQDialog.add(SubmitCancel, BorderLayout.PAGE_END);
		
		
		JButton btnNewQ = new JButton("Question");
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
		
		JButton btnNewA = new JButton("Answer");
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
		
		JButton btnEdit = new JButton("Edit");
		buttons.add(btnEdit);
		
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
						if(JOptionPane.showConfirmDialog(frame,
								"Is the correct answer selected?",
								"Answer Type",
								JOptionPane.YES_NO_OPTION
								) == 0) {
							int selected = getSelected(frame, 
									list, 
									"Please select the correct answer from the list", 
									"No answer selected");
							if(selected > -1) {
								String answer = listAnswer.getElementAt(selected);
								QB.addQuestion(question, answer, answers);
							}
						}
						
						// User needs to select correct answer
						// Early exit
						return;
					}
					else {
						// Fill in the Blank
						QB.addQuestion(question, answers.get(0));
					}
					// Clear data and return
					lblQuestion.setText("");
					listAnswer.clear();
					CardLayout c = (CardLayout)cards.getLayout();
					c.first(cards);
				}
			}
		});
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Delete existing input
				lblQuestion.setText("");
				listAnswer.clear();
				// Return to previous window
				CardLayout c = (CardLayout)cards.getLayout();
				c.first(cards);
			}
		});
		
		
		
		// Button to add question
		JButton btnAddQ = new JButton("Add Question");
		btnAddQ.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CardLayout c = (CardLayout)cards.getLayout();
				c.next(cards);
			}
		});
		edit_menu.add(btnAddQ);
		

		
		SubmitCancel.add(btnSubmit);
		SubmitCancel.add(btnCancel);
		
		
		return cards;
		
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
