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
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.BoxLayout;

public class MainCalendar extends JFrame {

	private JPanel contentPane;
	private JTextField weekTextField;
	private int week = 11; // Week that is displayed in the GUI

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainCalendar frame = new MainCalendar();
					frame.setVisible(true);
					frame.setResizable(false);
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
			contentPane.add(appArea2);
			appArea2.setBackground(Color.GREEN);
			appArea2.setText("Meeting with Lars");
			appArea2.setBounds(70, 140+timeStart*40, 115/2, (timeEnd-timeStart)*40);
		}
		else{
			JTextArea appArea = new JTextArea();
			contentPane.add(appArea);
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
		//setBackground(Color.WHITE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1020, 1000);
		
		
		/*JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 101, 742, 276);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        getContentPane().add(scrollPane);*/
		
		contentPane = new JPanel();
		contentPane.setBackground(UIManager.getColor("FormattedTextField.selectionBackground"));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.isOptimizedDrawingEnabled();
		contentPane.setLayout(null);
		
		
		/**
		 * Notification bubble
		 */
		JTextArea textArea = new JTextArea();
		textArea.setBounds(605, 22, 27, 22);
		textArea.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		textArea.setForeground(Color.WHITE);
		textArea.setText("10");
		textArea.setBackground(Color.RED);
		contentPane.add(textArea);
		textArea.setVisible(false);
		
		/**
		 * Separators for calendar view
		 */
		JSeparator dayLine1 = new JSeparator();
		dayLine1.setBounds(57, 95, 12, 877);
		dayLine1.setOrientation(SwingConstants.VERTICAL);
		dayLine1.setForeground(Color.BLACK);
		contentPane.add(dayLine1);
		
		JSeparator dayLine2 = new JSeparator();
		dayLine2.setBounds(187, 95, 12, 877);
		dayLine2.setForeground(Color.BLACK);
		dayLine2.setOrientation(SwingConstants.VERTICAL);
		contentPane.add(dayLine2);
		
		JSeparator dayLine3 = new JSeparator();
		dayLine3.setBounds(317, 95, 12, 877);
		dayLine3.setOrientation(SwingConstants.VERTICAL);
		dayLine3.setForeground(Color.BLACK);
		contentPane.add(dayLine3);
		
		JSeparator dayLine4 = new JSeparator();
		dayLine4.setBounds(605, 95, 12, 877);
		dayLine4.setOrientation(SwingConstants.VERTICAL);
		dayLine4.setForeground(Color.BLACK);
		contentPane.add(dayLine4);
		
		JSeparator dayLine5 = new JSeparator();
		dayLine5.setBounds(466, 95, 12, 877);
		dayLine5.setOrientation(SwingConstants.VERTICAL);
		dayLine5.setForeground(Color.BLACK);
		contentPane.add(dayLine5);
		
		JSeparator dayLine6 = new JSeparator();
		dayLine6.setBounds(741, 95, 12, 877);
		dayLine6.setOrientation(SwingConstants.VERTICAL);
		dayLine6.setForeground(Color.BLACK);
		contentPane.add(dayLine6);
		
		JSeparator dayLine7 = new JSeparator();
		dayLine7.setBounds(876, 95, 12, 877);
		dayLine7.setOrientation(SwingConstants.VERTICAL);
		dayLine7.setForeground(Color.BLACK);
		contentPane.add(dayLine7);

	/*	JSeparator timeLine = new JSeparator();
		timeLine.setBounds(57, 134, 950, 22);
		timeLine.setForeground(Color.BLACK);
		contentPane.add(timeLine);
		
		JSeparator timeline1 = new JSeparator();
		timeline1.setForeground(Color.BLACK);
		timeline1.setBounds(57, 154, 950, 22);
		contentPane.add(timeline1);
		
		JSeparator timeline2 = new JSeparator();
		timeline2.setForeground(Color.BLACK);
		timeline2.setBounds(57, 174, 950, 22);
		contentPane.add(timeline2);
		
		JSeparator timeline3 = new JSeparator();
		timeline3.setForeground(Color.BLACK);
		timeline3.setBounds(57, 194, 950, 22);
		contentPane.add(timeline3);
		
		JSeparator timeline4 = new JSeparator();
		timeline4.setForeground(Color.BLACK);
		timeline4.setBounds(57, 214, 950, 22);
		contentPane.add(timeline4);
		
		JSeparator timeline5 = new JSeparator();
		timeline5.setForeground(Color.BLACK);
		timeline5.setBounds(57, 214, 950, 22);
		contentPane.add(timeline5);
		
		JSeparator timeline6 = new JSeparator();
		timeline6.setForeground(Color.BLACK);
		timeline6.setBounds(57, 234, 950, 22);
		contentPane.add(timeline6);
		
		JSeparator timeline7 = new JSeparator();
		timeline7.setForeground(Color.BLACK);
		timeline7.setBounds(57, 254, 950, 22);
		contentPane.add(timeline7);
		
		JSeparator timeline8 = new JSeparator();
		timeline8.setForeground(Color.BLACK);
		timeline8.setBounds(57, 274, 950, 22);
		contentPane.add(timeline8);
		
		JSeparator timeline9 = new JSeparator();
		timeline9.setForeground(Color.BLACK);
		timeline9.setBounds(57, 294, 950, 22);
		contentPane.add(timeline9);
		
		JSeparator timeline10 = new JSeparator();
		timeline10.setForeground(Color.BLACK);
		timeline10.setBounds(57, 314, 950, 22);
		contentPane.add(timeline10);
		
		JSeparator timeline11 = new JSeparator();
		timeline11.setForeground(Color.BLACK);
		timeline11.setBounds(57, 334, 950, 22);
		contentPane.add(timeline11);
		
		JSeparator timeline12 = new JSeparator();
		timeline12.setForeground(Color.BLACK);
		timeline12.setBounds(57, 354, 950, 22);
		contentPane.add(timeline12);
		
		JSeparator timeline13 = new JSeparator();
		timeline13.setForeground(Color.BLACK);
		timeline13.setBounds(57, 374, 950, 22);
		contentPane.add(timeline13);*/
		
		/**
		 * Week day labels
		 */
		JLabel lblTime = new JLabel("Time");
		lblTime.setBounds(17, 106, 61, 16);
		contentPane.add(lblTime);
		
		JLabel lblMonday = new JLabel("Monday");
		lblMonday.setBounds(96, 106, 61, 16);
		lblMonday.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		lblMonday.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblMonday);
		
		JLabel lblTuesday = new JLabel("Tuesday");
		lblTuesday.setBounds(234, 106, 61, 16);
		lblTuesday.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		lblTuesday.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblTuesday);
		
		JLabel lblWednesday = new JLabel("Wednesday");
		lblWednesday.setBounds(353, 106, 86, 16);
		lblWednesday.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		lblWednesday.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblWednesday);
		
		JLabel lblThursday = new JLabel("Thursday");
		lblThursday.setBounds(503, 106, 70, 16);
		lblThursday.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		lblThursday.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblThursday);
		
		JLabel lblFriday = new JLabel("Friday");
		lblFriday.setBounds(642, 106, 61, 16);
		lblFriday.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		lblFriday.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblFriday);
		
		JLabel lblSaturday = new JLabel("Saturday");
		lblSaturday.setBounds(779, 106, 61, 16);
		lblSaturday.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		lblSaturday.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblSaturday);
		
		JLabel lblSunday = new JLabel("Sunday");
		lblSunday.setBounds(915, 106, 61, 16);
		lblSunday.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		lblSunday.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblSunday);
		
		/**
		 * Elements in the toolbar
		 */
		JButton btnThisWeek = new JButton("This week");
		btnThisWeek.setBounds(17, 30, 117, 29);
		contentPane.add(btnThisWeek);
		
		JButton prevWeekBT = new JButton("<");
		prevWeekBT.setBounds(157, 30, 45, 29);
		contentPane.add(prevWeekBT);
		prevWeekBT.addActionListener(new prevWeekButtonAction());
		
		JButton nextWeekBT = new JButton(">");
		nextWeekBT.setBounds(238, 30, 45, 29);
		contentPane.add(nextWeekBT);
		nextWeekBT.addActionListener(new nextWeekButtonAction());
		
		JLabel lblWeek = new JLabel("Week");
		lblWeek.setBounds(191, 6, 61, 16);
		lblWeek.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblWeek);
		
		weekTextField = new JTextField();
		weekTextField.setBounds(202, 29, 36, 28);
		weekTextField.setText(""+week);
		contentPane.add(weekTextField);
		weekTextField.setColumns(10);
		
		JLabel lblMarch = new JLabel("September 10 - December 16");
		lblMarch.setBounds(284, 22, 333, 37);
		lblMarch.setHorizontalAlignment(SwingConstants.CENTER);
		lblMarch.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		contentPane.add(lblMarch);
		
		JButton NotificationBT = new JButton("Notifications");
		NotificationBT.setBounds(619, 30, 117, 29);
		contentPane.add(NotificationBT);
		
		JButton chooseCalBT = new JButton("Choose calendars");
		chooseCalBT.setBounds(733, 30, 142, 29);
		contentPane.add(chooseCalBT);
		
		/**
		 * New appointment
		 */
		JButton newAppBT = new JButton("New appointment");
		newAppBT.setBounds(870, 30, 150, 29);
		contentPane.add(newAppBT);
		newAppBT.addActionListener(new AppointmentButtonAction());
		
		
		
		
		
		/**
		 * Time labels
		 */
		/*JLabel label = new JLabel("00:00");
		label.setBounds(17, 130, 36, 16);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("00:30");
		label_1.setBounds(17, 150, 36, 16);
		contentPane.add(label_1);
		
		JLabel label_2 = new JLabel("01:00");
		label_2.setBounds(17, 170, 36, 16);
		contentPane.add(label_2);
		
		JLabel label_3 = new JLabel("01:30");
		label_3.setBounds(17, 190, 36, 16);
		contentPane.add(label_3);
		
		JLabel label_4 = new JLabel("02:00");
		label_4.setBounds(17, 210, 36, 16);
		contentPane.add(label_4);
		
		JLabel label_5 = new JLabel("02:30");
		label_5.setBounds(17, 230, 36, 16);
		contentPane.add(label_5);
		
		JLabel label_6 = new JLabel("03:00");
		label_6.setBounds(16, 250, 36, 16);
		contentPane.add(label_6);
		
		JLabel label_7 = new JLabel("03:30");
		label_7.setBounds(16, 270, 36, 16);
		contentPane.add(label_7);
		
		JLabel label_8 = new JLabel("04:00");
		label_8.setBounds(16, 290, 36, 16);
		contentPane.add(label_8);
		
		JLabel label_9 = new JLabel("04:30");
		label_9.setBounds(17, 310, 36, 16);
		contentPane.add(label_9);
		
		JLabel label_10 = new JLabel("05:00");
		label_10.setBounds(17, 330, 36, 16);
		contentPane.add(label_10);
		
		JLabel label_11 = new JLabel("05:30");
		label_11.setBounds(16, 350, 36, 16);
		contentPane.add(label_11);
		
		JLabel label_12 = new JLabel("06:00");
		label_12.setBounds(16, 370, 36, 16);
		contentPane.add(label_12);
		
		JLabel label_13 = new JLabel("06:30");
		label_13.setBounds(17, 390, 36, 16);
		contentPane.add(label_13);*/
		
		
		int lineCoord=134;
		for(int i=0; i<48; i++){
			
			JSeparator timeLine= new JSeparator();
			timeLine.setBounds(57, lineCoord, 950, 22);
			timeLine.setForeground(Color.BLACK);
			contentPane.add(timeLine);
			lineCoord+=20;
			
		}
		
		int timeCoord=130;
		for(int i=0; i<24; i++){
			if(i<10){
				JLabel time = new JLabel("0"+i+":00");
				time.setBounds(17, timeCoord, 36, 16);
				contentPane.add(time);
				JLabel time2 = new JLabel("0"+i+":30");
				time2.setBounds(17, timeCoord+20, 36, 16);
				contentPane.add(time2);
			}
			else{
				JLabel time = new JLabel(i+":00");
				time.setBounds(17, timeCoord, 36, 16);
				contentPane.add(time);
				JLabel time2 = new JLabel(i+":30");
				time2.setBounds(17, timeCoord+20, 36, 16);
				contentPane.add(time2);
			}
			timeCoord+=40;
		}
		
		
		
		
	}
}
