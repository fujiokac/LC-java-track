package quiz;

import java.awt.*;
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
		JPanel QBtab = init_edit();
		//QBtab.setLayout(new BorderLayout(0, 0));
		tabbedPane.addTab("Quiz Builder", null, QBtab, null);
		
		
	}
	
	// Create the create & edit panel
	private JPanel init_edit() {
		JPanel cards = new JPanel(new CardLayout());
		
		JSplitPane tab_edit = new JSplitPane();
		Box edit_menu = Box.createVerticalBox();
		JPanel AddQDialog = new JPanel();
		Box SubmitCancel = Box.createHorizontalBox();
		JSplitPane splitPane = new JSplitPane();
		Box buttons = Box.createVerticalBox();
		Box preview = Box.createVerticalBox();
		JLabel lblQuestion = new JLabel();
		DefaultListModel<String> listAnswer = new DefaultListModel<String>();
		JList<String> list = new JList<String>(listAnswer);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		cards.add(tab_edit, "QuizBuilder");
		cards.add(AddQDialog, "New Question");
		
		
		
		JButton btnNewQ = new JButton("Question");
		btnNewQ.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Object[] options = {"Multiple Choice", "Fill in the Blank"};
				String question =
						(String)JOptionPane.showInputDialog(frame,
								"Please enter a question.",
								"Add Question",
								JOptionPane.PLAIN_MESSAGE);
				Boolean isMC = JOptionPane.showOptionDialog(frame,
							"Which kind of question is this?",
							"Question Type",
							JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE,
							null,
							options,
							options[1]) == 0 ? true:false;
				QB.addQ(question, isMC);
				lblQuestion.setText(question);
			}
		});
		JButton btnNewA = new JButton("Answer");
		btnNewA.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Boolean isMC = QB.currentMC();
				String answer =
						(String)JOptionPane.showInputDialog(frame,
								"Please enter an answer.",
								"Add Answer",
								JOptionPane.PLAIN_MESSAGE);
				Boolean correct = true;
				if(isMC) {
					Object[] options = {"Correct Answer", "Incorrect Answer"};
					correct = JOptionPane.showOptionDialog(frame,
							"Is this the correct answer?",
							"Answer Type",
							JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE,
							null,
							options,
							options[0]) == 0 ? true:false;
				}
				QB.addC(answer, correct);
				listAnswer.add(listAnswer.size(), answer);
			}
		});
		JButton btnEdit = new JButton("Edit");
		JButton btnSubmit = new JButton("Submit");
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
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
		
		tab_edit.setLeftComponent(edit_menu);
		// tab_edit.setRightComponent(preview);
		AddQDialog.add(splitPane, BorderLayout.CENTER);
		AddQDialog.add(SubmitCancel, BorderLayout.PAGE_END);
		splitPane.setLeftComponent(buttons);
		splitPane.setRightComponent(preview);
		buttons.add(btnNewQ);
		buttons.add(btnNewA);
		buttons.add(btnEdit);
		preview.add(lblQuestion);
		preview.add(list);
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
