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

import org.eclipse.wb.swing.FocusTraversalOnArray;

import com.proj.model.Model;

import java.awt.Component;

import javax.swing.SpringLayout;
import javax.swing.JLayeredPane;
import javax.swing.JSeparator;

import java.awt.Color;

import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.UIManager;
import javax.swing.JTextField;
import javax.swing.JRadioButton;

public class MainWindow extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTable table;
	private Model model;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow frame = new MainWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public MainWindow(Model model) {
		this.model = model;
	}

	/**
	 * Create the frame.
	 */
	public MainWindow() {
		setBackground(UIManager.getColor("EditorPane.selectionBackground"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1020, 1000);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(605, 22, 27, 22);
		textArea.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		textArea.setForeground(Color.WHITE);
		textArea.setText("10");
		textArea.setBackground(Color.RED);
		contentPane.add(textArea);
		textArea.setVisible(false);
		
		JTextArea txtrCalendarEvent = new JTextArea();
		txtrCalendarEvent.setBounds(204, 260, 123, 208);
		txtrCalendarEvent.setEditable(false);
		txtrCalendarEvent.setLineWrap(true);
		txtrCalendarEvent.setText("Calendar Event - meeting with Jane");
		txtrCalendarEvent.setForeground(Color.WHITE);
		txtrCalendarEvent.setBackground(Color.RED);
		contentPane.add(txtrCalendarEvent);
		
		JSeparator dayLine2 = new JSeparator();
		dayLine2.setBounds(191, 95, 12, 877);
		dayLine2.setForeground(Color.BLACK);
		dayLine2.setOrientation(SwingConstants.VERTICAL);
		contentPane.add(dayLine2);
		
		JSeparator dayLine1 = new JSeparator();
		dayLine1.setBounds(57, 95, 12, 877);
		dayLine1.setOrientation(SwingConstants.VERTICAL);
		dayLine1.setForeground(Color.BLACK);
		contentPane.add(dayLine1);
		
		JSeparator dayLine3 = new JSeparator();
		dayLine3.setBounds(329, 95, 12, 877);
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
		
		JSeparator timeLine = new JSeparator();
		timeLine.setBounds(6, 124, 1008, 22);
		timeLine.setForeground(Color.BLACK);
		contentPane.add(timeLine);
		
		JLabel lblTime = new JLabel("Time");
		lblTime.setBounds(17, 106, 61, 16);
		contentPane.add(lblTime);
		
		JButton btnThisWeek = new JButton("This week");
		btnThisWeek.setBounds(17, 30, 117, 29);
		contentPane.add(btnThisWeek);
		
		JButton button = new JButton("<");
		button.setBounds(157, 30, 45, 29);
		contentPane.add(button);
		
		JButton button_1 = new JButton(">");
		button_1.setBounds(238, 30, 45, 29);
		contentPane.add(button_1);
		
		JLabel lblWeek = new JLabel("Week");
		lblWeek.setBounds(191, 6, 61, 16);
		lblWeek.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblWeek);
		
		textField = new JTextField();
		textField.setBounds(202, 29, 36, 28);
		textField.setText("11");
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblMarch = new JLabel("September 10 - December 16");
		lblMarch.setBounds(284, 22, 333, 37);
		lblMarch.setHorizontalAlignment(SwingConstants.CENTER);
		lblMarch.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		contentPane.add(lblMarch);
		
		JButton btnNewButton = new JButton("Notifications");
		btnNewButton.setBounds(619, 30, 117, 29);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Choose calendars");
		btnNewButton_1.setBounds(733, 30, 142, 29);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("New appointment");
		btnNewButton_2.setBounds(870, 30, 150, 29);
		contentPane.add(btnNewButton_2);
		
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
		
		JSeparator separator = new JSeparator();
		separator.setBounds(6, 158, 1008, 22);
		separator.setForeground(Color.BLACK);
		contentPane.add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(6, 192, 1008, 22);
		separator_1.setForeground(Color.BLACK);
		contentPane.add(separator_1);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(6, 226, 1008, 22);
		separator_2.setForeground(Color.BLACK);
		contentPane.add(separator_2);
		
		JSeparator separator_3 = new JSeparator();
		separator_3.setBounds(6, 260, 1008, 22);
		separator_3.setForeground(Color.BLACK);
		contentPane.add(separator_3);
		
		JSeparator separator_4 = new JSeparator();
		separator_4.setBounds(6, 294, 1008, 22);
		separator_4.setForeground(Color.BLACK);
		contentPane.add(separator_4);
		
		JSeparator separator_5 = new JSeparator();
		separator_5.setBounds(6, 328, 1008, 22);
		separator_5.setForeground(Color.BLACK);
		contentPane.add(separator_5);
		
		JSeparator separator_6 = new JSeparator();
		separator_6.setBounds(6, 362, 1008, 22);
		separator_6.setForeground(Color.BLACK);
		contentPane.add(separator_6);
		
		JSeparator separator_7 = new JSeparator();
		separator_7.setBounds(6, 396, 1008, 22);
		separator_7.setForeground(Color.BLACK);
		contentPane.add(separator_7);
		
		JSeparator separator_8 = new JSeparator();
		separator_8.setBounds(6, 430, 1008, 22);
		separator_8.setForeground(Color.BLACK);
		contentPane.add(separator_8);
		
		JSeparator separator_9 = new JSeparator();
		separator_9.setBounds(6, 464, 1008, 22);
		separator_9.setForeground(Color.BLACK);
		contentPane.add(separator_9);
		
		JSeparator separator_10 = new JSeparator();
		separator_10.setBounds(6, 498, 1008, 22);
		separator_10.setForeground(Color.BLACK);
		contentPane.add(separator_10);
		
		JSeparator separator_11 = new JSeparator();
		separator_11.setBounds(6, 532, 1008, 22);
		separator_11.setForeground(Color.BLACK);
		contentPane.add(separator_11);
		
		JSeparator separator_12 = new JSeparator();
		separator_12.setBounds(6, 566, 1008, 22);
		separator_12.setForeground(Color.BLACK);
		contentPane.add(separator_12);
		
		JSeparator separator_13 = new JSeparator();
		separator_13.setBounds(6, 600, 1008, 22);
		separator_13.setForeground(Color.BLACK);
		contentPane.add(separator_13);
		
		JSeparator separator_14 = new JSeparator();
		separator_14.setBounds(6, 634, 1008, 22);
		separator_14.setForeground(Color.BLACK);
		contentPane.add(separator_14);
		
		JSeparator separator_15 = new JSeparator();
		separator_15.setBounds(6, 668, 1008, 22);
		separator_15.setForeground(Color.BLACK);
		contentPane.add(separator_15);
		
		JSeparator separator_16 = new JSeparator();
		separator_16.setBounds(6, 702, 1008, 22);
		separator_16.setForeground(Color.BLACK);
		contentPane.add(separator_16);
		
		JSeparator separator_17 = new JSeparator();
		separator_17.setBounds(6, 736, 1008, 22);
		separator_17.setForeground(Color.BLACK);
		contentPane.add(separator_17);
		
		JSeparator separator_18 = new JSeparator();
		separator_18.setBounds(6, 770, 1008, 22);
		separator_18.setForeground(Color.BLACK);
		contentPane.add(separator_18);
		
		JSeparator separator_19 = new JSeparator();
		separator_19.setBounds(6, 804, 1008, 22);
		separator_19.setForeground(Color.BLACK);
		contentPane.add(separator_19);
		
		JSeparator separator_20 = new JSeparator();
		separator_20.setBounds(6, 838, 1008, 22);
		separator_20.setForeground(Color.BLACK);
		contentPane.add(separator_20);
		
		JSeparator separator_21 = new JSeparator();
		separator_21.setBounds(6, 872, 1008, 22);
		separator_21.setForeground(Color.BLACK);
		contentPane.add(separator_21);
		
		JSeparator separator_22 = new JSeparator();
		separator_22.setBounds(6, 906, 1008, 22);
		separator_22.setForeground(Color.BLACK);
		contentPane.add(separator_22);
		
		JSeparator separator_23 = new JSeparator();
		separator_23.setBounds(6, 940, 1008, 22);
		separator_23.setForeground(Color.BLACK);
		contentPane.add(separator_23);
		
		JLabel label = new JLabel("00:00");
		label.setBounds(16, 134, 36, 16);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("00:00");
		label_1.setBounds(16, 174, 36, 16);
		contentPane.add(label_1);
		
		JLabel label_2 = new JLabel("00:00");
		label_2.setBounds(16, 202, 36, 16);
		contentPane.add(label_2);
		
		JLabel label_3 = new JLabel("00:00");
		label_3.setBounds(17, 242, 36, 16);
		contentPane.add(label_3);
		
		JLabel label_4 = new JLabel("00:00");
		label_4.setBounds(17, 276, 36, 16);
		contentPane.add(label_4);
		
		JLabel label_5 = new JLabel("00:00");
		label_5.setBounds(17, 304, 36, 16);
		contentPane.add(label_5);
		
		JLabel label_6 = new JLabel("00:00");
		label_6.setBounds(16, 343, 36, 16);
		contentPane.add(label_6);
		
		table = new JTable();
		table.setBounds(594, 869, -92, -655);
		contentPane.add(table);
		
		JLabel label_7 = new JLabel("00:00");
		label_7.setBounds(16, 380, 36, 16);
		contentPane.add(label_7);
		
		JLabel label_8 = new JLabel("00:00");
		label_8.setBounds(16, 408, 36, 16);
		contentPane.add(label_8);
		
		JLabel label_9 = new JLabel("00:00");
		label_9.setBounds(17, 447, 36, 16);
		contentPane.add(label_9);
		
		JLabel label_10 = new JLabel("00:00");
		label_10.setBounds(17, 482, 36, 16);
		contentPane.add(label_10);
		
		JLabel label_11 = new JLabel("00:00");
		label_11.setBounds(16, 510, 36, 16);
		contentPane.add(label_11);
		
		JLabel label_12 = new JLabel("00:00");
		label_12.setBounds(16, 548, 36, 16);
		contentPane.add(label_12);
		
		JLabel label_13 = new JLabel("00:00");
		label_13.setBounds(17, 585, 36, 16);
		contentPane.add(label_13);
	}
}
