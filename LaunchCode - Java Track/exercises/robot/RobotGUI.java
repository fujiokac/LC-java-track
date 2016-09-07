package robot;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;

public class RobotGUI {

	private JFrame frmRobotMakerV;
	private DefaultListModel<Robot> listModel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RobotGUI window = new RobotGUI();
					window.frmRobotMakerV.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public RobotGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmRobotMakerV = new JFrame();
		frmRobotMakerV.getContentPane().setFont(new Font("Verdana", Font.PLAIN, 20));
		frmRobotMakerV.setFont(new Font("Verdana", Font.PLAIN, 20));
		frmRobotMakerV.setTitle("Robot Maker v0.1");
		frmRobotMakerV.setBounds(100, 100, 450, 300);
		frmRobotMakerV.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmRobotMakerV.getContentPane().setLayout(new BoxLayout(frmRobotMakerV.getContentPane(), BoxLayout.X_AXIS));
		listModel = new DefaultListModel<Robot>();
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frmRobotMakerV.getContentPane().add(tabbedPane);
		tabbedPane.setFont(new Font("Verdana", Font.PLAIN, 20));
		tabbedPane.addTab("Create & Edit", null, init_edit(), null);
		tabbedPane.addTab("Distance", null, init_distance(), null);
		tabbedPane.addTab("View", null, init_view(), null);
		
		
	}
	
	// Create the create & edit panel
	private JComponent init_edit() {
		JSplitPane tab_edit = new JSplitPane();
		JList<Robot> robot_list = new JList<Robot>(listModel);
		robot_list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tab_edit.setRightComponent(robot_list);
		
		Box edit_menu = Box.createVerticalBox();
		tab_edit.setLeftComponent(edit_menu);
		
		// Button to add robots
		JButton btnCreate = new JButton("Create");
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String name = (String)JOptionPane.showInputDialog(frmRobotMakerV, "What is the robot's name?", "Create a Robot", JOptionPane.PLAIN_MESSAGE);
				int posX = getInt("Where is the robot located? (x coordinate)", "X coordinate");
				int posY = getInt("Where is the robot located? (y coordinate)", "Y coordinate");
				int speed = getInt("What is the speed of the robot?", "Speed");
				String[] directions = { "North", "South", "East", "West" };
				String direction = (String)JOptionPane.showInputDialog(frmRobotMakerV,
						"What direction is the robot facing?", "Direction", 
						JOptionPane.QUESTION_MESSAGE, null, directions, directions[0]);
				Robot rbt = new Robot(name, posX, posY, speed, direction);
				listModel.add(listModel.size(), rbt);
			}
		});
		edit_menu.add(btnCreate);
		
		// Button to move existing robots
		JButton btnMove = new JButton("Move");
		btnMove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int selected = getSelected(robot_list, "Please select a robot from the list.", "No robot selected");
				if(selected > 0) {
					int time = getInt("Enter the amount of time the robot should move: ", "Time");
					Robot rbt = listModel.getElementAt(selected);
					rbt.move(time);
				}
			}
		});
		edit_menu.add(btnMove);
		
		// Button to rotate existing robots
		JButton btnRotate = new JButton("Rotate");
		btnRotate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int selected = getSelected(robot_list, "Please select a robot from the list.", "No robot selected");
				if(selected > 0) {
					Robot rbt = listModel.getElementAt(selected);
					String[] options = {"Clockwise", "Anti-Clockwise"};
					String answer = (String)JOptionPane.showInputDialog(frmRobotMakerV,
							"Turn Clockwise or Anti-clockwise?", "Rotate", 
							JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
					rbt.rotate(answer.equals("Clockwise"));
				}
			}
		});
		edit_menu.add(btnRotate);
		
		return tab_edit;
	}

	// Create the panel for calculating distance between robots
	private JComponent init_distance() {
		JPanel tab_distance = new JPanel();
		tab_distance.setLayout(new BorderLayout(0, 0));
		
		// Display existing robots on two panels on left & right
		JList<Robot> list_left =  new JList<Robot>(listModel);
		JList<Robot> list_right = new JList<Robot>(listModel);
		JSplitPane robot_lists = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, list_left, list_right);
		// Keep divider centered
		robot_lists.setDividerLocation(.5);
		robot_lists.setResizeWeight(.5);
		robot_lists.setEnabled(false);
		tab_distance.add(robot_lists, BorderLayout.CENTER);
		
		// Button to calculate distance between two selected elements
		JButton btnDistance = new JButton("Determine Distance");
		btnDistance.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int left = getSelected(list_left, "Please select a robot from the left list.", "No robot selected");
				int right = getSelected(list_right, "Please select a robot from the right list.", "No robot selected");
				if(left > 0 && right > 0) {
					Robot robot1 = listModel.getElementAt(left);
					Robot robot2 = listModel.getElementAt(right);
					double dist = robot1.distance(robot2);
					JOptionPane.showMessageDialog(frmRobotMakerV,
						    "The distance from robot "+ robot1.getName() +" to robot "+ robot2.getName() +" is "+ dist +".");
				}
			}
		});
		btnDistance.setFont(new Font("Verdana", Font.PLAIN, 20));
		tab_distance.add(btnDistance, BorderLayout.SOUTH);
		
		
		return tab_distance;
	}
	
	// Creates the view panel, displaying all robots on an xy plane
	// Overrides JPanel paintComponent method
	private JComponent init_view() {
		JPanel tab_view = new JPanel() {
			// Override paintComponent method
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				// maxX : width/2 :: maxY : height/2 
				//System.out.println(getWidth() +" "+ getHeight());
				int dimension = 20;
				g.setFont(new Font("SansSerif", Font.PLAIN, dimension));
				
				// Calculate center point on panel
				int originX = getWidth()/2;
				int originY = getHeight()/2;
				
				// Extract existing robots
				ArrayList<Robot> robots = Collections.list(listModel.elements());
				
				// Iterate through existing robots for x,y position furthest from origin
				int maxX = 0, maxY = 0;
				for(Robot robot : robots) {
					if(Math.abs(robot.getPosX()) > maxX) {
						maxX = Math.abs(robot.getPosX());
					}
					if(Math.abs(robot.getPosY()) > maxY) {
						maxY = Math.abs(robot.getPosY());
					}
				}
				// Add padding
				maxX += 5;
				maxY += 5;
				
				// Calculate the scale of x,y to panelx,panely
				float scale = (float) originX/maxX < (float) originY/maxY ? (float) originX/maxX : (float) originY/maxY;
				
				// Iterate through robots, paint marker at coordinate
				for(Robot robot : robots) {
					int x = Math.round(scale * robot.getPosX()) + originX;
					int y = Math.round(scale * robot.getPosY()) + originY;
					//System.out.println("Painting at ("+ x +" "+ y +")");
					//g.fillOval(x, y, dimension, dimension);
					// Paint triangle pointing in correct direction
					fillTriangle(g, x, y, dimension, robot.getDirection());
					// Label marker with robot name
					g.drawString(robot.getName(), x + dimension, y + dimension);
				}
			}
			
			// Calculate & draw equilateral triangle centering at x,y, with side dimension, pointing in direction
			// Expects "North", "South", "East", "West" as directions as output by Robot.getDirection()
			private void fillTriangle(Graphics g, int x, int y, int dimension, String direction) {
				int[] xPoints = new int[3];
				int[] yPoints = new int[3];
				
				// Calculate height of triangle
				double h = Math.sqrt(3)/2 * dimension;
				// Calculate midpoint of triangle to line up with x,y
				int h3 = (int) Math.round(h / 3);
				int h2 = (int) Math.round(2*h / 3);
				
				// I'm sure I could simplify this by making it more complicated
				switch(direction) {
					case "East": 
						xPoints[0] = x + h2;
						yPoints[0] = y;
						xPoints[1] = x - h3;
						xPoints[2] = x - h3;
						yPoints[1] = y + (dimension / 2);
						yPoints[2] = y - (dimension / 2);
						break;
					case "South": 
						xPoints[0] = x;
						yPoints[0] = y + h2;
						xPoints[1] = x + (dimension / 2);
						xPoints[2] = x - (dimension / 2);
						yPoints[1] = y - h3;
						yPoints[2] = y - h3;
						break;
					case "West": 
						xPoints[0] = x - h2;
						yPoints[0] = y;
						xPoints[1] = x + h3;
						xPoints[2] = x + h3;
						yPoints[1] = y + (dimension / 2);
						yPoints[2] = y - (dimension / 2);
						break;
					case "North": 
						xPoints[0] = x;
						yPoints[0] = y - h2;
						xPoints[1] = x + (dimension / 2);
						xPoints[2] = x - (dimension / 2);
						yPoints[1] = y + h3;
						yPoints[2] = y + h3;
						break;
					default: return;
				}
				
				// Use 3 vertices to draw a filled triangle
				g.fillPolygon(xPoints, yPoints, 3);
			}
		};
		
		return tab_view;
	}
	// Prompts user for integer value, does not handle bad input
	private int getInt(String prompt, String title) {
		String input = "";
		int output = 0;
		// do {
			input = (String) JOptionPane.showInputDialog(frmRobotMakerV, prompt, title, JOptionPane.PLAIN_MESSAGE);
			output = Integer.parseInt(input);
			// Catch exception here
		// } while(output < 1);
		
		return output;
	}
	// Returns index of selected JList item, Displays error with message & title if none selected
	private int getSelected(JList list, String message, String title) {
		int selected = list.getSelectedIndex();
		if(selected < 0) {
			JOptionPane.showMessageDialog(frmRobotMakerV,
					message,
					title,
				    JOptionPane.ERROR_MESSAGE);
		}
		return selected;
	}
}
