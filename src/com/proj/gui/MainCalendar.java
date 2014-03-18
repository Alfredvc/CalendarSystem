package com.proj.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import java.awt.FlowLayout;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JList;
import javax.swing.JToggleButton;
import javax.swing.JTable;
import javax.swing.JProgressBar;
import java.awt.Component;
import javax.swing.SpringLayout;
import javax.swing.JLayeredPane;
import javax.swing.JSeparator;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.UIManager;
import javax.swing.JTextField;
import javax.swing.JRadioButton;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.ScrollPane;
import java.awt.SystemColor;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.BoxLayout;

import sun.awt.HorizBagLayout;

public class MainCalendar extends JFrame {

	private JTextField weekTextField;
	private int week = 12; // Week that is displayed in the GUI

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new MainCalendar();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Display new appointment in the calendar.
	 * 
	 * Instructions: Each hour is 20 pixels long. In setbounds(), the second value corresponds 
	 * to the start time, and the fourth value to the end time.
	 * The 00:00 time slot lays on pixel 140
	 */
	public void displayAppointment(){
		
		int timeStart = 6;
		int timeEnd = 9;
		
		boolean overlapping = false;
		
		if(overlapping){
			JTextArea appArea2 = new JTextArea();
			getContentPane().add(appArea2);
			appArea2.setBackground(Color.GREEN);
			appArea2.setText("Meeting with Lars");
			appArea2.setBounds(70, 140+timeStart*40, 115/2, (timeEnd-timeStart)*40);
		}
		else{
			JTextArea appArea = new JTextArea();
			getContentPane().add(appArea);
			appArea.setBackground(Color.ORANGE);
			appArea.setText("Meeting with Jane");
			appArea.setBounds(70, 140+timeStart*40, 115, (timeEnd-timeStart)*40);
			overlapping = true;
		}
		
		
		
	}
	/**
	 * ButtonEvent for new appointment button
	 */
	class AppointmentButtonAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
            System.out.println("You pressed new appointment!");
            displayAppointment();
        }
	}
	
	/**
	 * Button events for week picker
	 */
	class nextWeekButtonAction implements ActionListener {
		public void actionPerformed(ActionEvent e){
			week++;
			weekTextField.setText(""+week);
		}
	}
	
	class prevWeekButtonAction implements ActionListener {
		public void actionPerformed(ActionEvent e){
			week--;
			weekTextField.setText(""+week);
		}
	}
	

	/**
	 * Create the frame.
	 */
	public MainCalendar() {
		
		setVisible(true);
		setResizable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1020, 1000);
		getContentPane().setBackground(UIManager.getColor("EditorPane.selectionBackground"));
		
		setBackground(UIManager.getColor("FormattedTextField.selectionBackground"));
		getContentPane().setLayout(null);
		
		
		
		/**
		 * Notification bubble
		 */
		JTextArea textArea = new JTextArea();
		textArea.setBounds(605, 22, 27, 22);
		textArea.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		textArea.setForeground(Color.WHITE);
		textArea.setText("10");
		textArea.setBackground(Color.RED);
		getContentPane().add(textArea);
		textArea.setVisible(false);
		
		
		/**
		 * Elements in the toolbar
		 */
		JButton btnThisWeek = new JButton("This week");
		btnThisWeek.setBounds(17, 30, 117, 29);
		getContentPane().add(btnThisWeek);
		
		JButton prevWeekBT = new JButton("<");
		prevWeekBT.setBounds(157, 30, 45, 29);
		getContentPane().add(prevWeekBT);
		prevWeekBT.addActionListener(new prevWeekButtonAction());
		
		JButton nextWeekBT = new JButton(">");
		nextWeekBT.setBounds(238, 30, 45, 29);
		getContentPane().add(nextWeekBT);
		nextWeekBT.addActionListener(new nextWeekButtonAction());
		
		JLabel lblWeek = new JLabel("Week");
		lblWeek.setBounds(191, 6, 61, 16);
		lblWeek.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(lblWeek);
		
		weekTextField = new JTextField();
		weekTextField.setBounds(202, 29, 36, 28);
		weekTextField.setText(""+week);
		getContentPane().add(weekTextField);
		weekTextField.setColumns(10);
		
		JLabel lblMarch = new JLabel("September 10 - December 16");
		lblMarch.setBounds(284, 22, 333, 37);
		lblMarch.setHorizontalAlignment(SwingConstants.CENTER);
		lblMarch.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		getContentPane().add(lblMarch);
		
		JButton NotificationBT = new JButton("Notifications");
		NotificationBT.setBounds(619, 30, 117, 29);
		getContentPane().add(NotificationBT);
		
		JButton chooseCalBT = new JButton("Choose calendars");
		chooseCalBT.setBounds(733, 30, 142, 29);
		getContentPane().add(chooseCalBT);
		
		/**
		 * New appointment
		 */
		JButton newAppBT = new JButton("New appointment");
		newAppBT.setBounds(870, 30, 150, 29);
		getContentPane().add(newAppBT);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.setForeground(Color.RED);
		btnNewButton.setBackground(Color.RED);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		/**
		 * Create scrollpanel
		 */
		CalendarView view = new CalendarView();
		JScrollPane scrollPane = new JScrollPane(view,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(10, 80, 1000, 1000);
		//scrollPane.getViewport().setBackground(UIManager.getColor("EditorPane.selectionBackground"));
		getContentPane().add(scrollPane);
		
	}
}
