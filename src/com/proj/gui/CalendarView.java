package com.proj.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

public class CalendarView extends JPanel{

	public CalendarView() {
		
		/**
		 * Modifies the scrollpanel
		 */
		setMinimumSize(new Dimension(2000, 2000));
		setPreferredSize(new Dimension(2000, 2000));
		setMaximumSize(new Dimension(2000, 2000));
		setLayout(null);
		
		
		/**
		 * Separators for weekdays
		 */
		JSeparator dayLine1 = new JSeparator();
		dayLine1.setBounds(57, 95, 12, 877);
		dayLine1.setOrientation(SwingConstants.VERTICAL);
		dayLine1.setForeground(Color.BLACK);
		add(dayLine1);
		
		JSeparator dayLine2 = new JSeparator();
		dayLine2.setBounds(187, 95, 12, 877);
		dayLine2.setForeground(Color.BLACK);
		dayLine2.setOrientation(SwingConstants.VERTICAL);
		add(dayLine2);
		
		JSeparator dayLine3 = new JSeparator();
		dayLine3.setBounds(317, 95, 12, 877);
		dayLine3.setOrientation(SwingConstants.VERTICAL);
		dayLine3.setForeground(Color.BLACK);
		add(dayLine3);
		
		JSeparator dayLine4 = new JSeparator();
		dayLine4.setBounds(605, 95, 12, 877);
		dayLine4.setOrientation(SwingConstants.VERTICAL);
		dayLine4.setForeground(Color.BLACK);
		add(dayLine4);
		
		JSeparator dayLine5 = new JSeparator();
		dayLine5.setBounds(466, 95, 12, 877);
		dayLine5.setOrientation(SwingConstants.VERTICAL);
		dayLine5.setForeground(Color.BLACK);
		add(dayLine5);
		
		JSeparator dayLine6 = new JSeparator();
		dayLine6.setBounds(741, 95, 12, 877);
		dayLine6.setOrientation(SwingConstants.VERTICAL);
		dayLine6.setForeground(Color.BLACK);
		add(dayLine6);
		
		JSeparator dayLine7 = new JSeparator();
		dayLine7.setBounds(876, 95, 12, 877);
		dayLine7.setOrientation(SwingConstants.VERTICAL);
		dayLine7.setForeground(Color.BLACK);
		add(dayLine7);
		
		/**
		 * Week day labels
		 */
		JLabel lblTime = new JLabel("Time");
		lblTime.setBounds(8, 106, 61, 16);
		add(lblTime);
		
		JLabel lblMonday = new JLabel("Monday");
		lblMonday.setBounds(96, 106, 61, 16);
		lblMonday.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		lblMonday.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblMonday);
		
		JLabel lblTuesday = new JLabel("Tuesday");
		lblTuesday.setBounds(234, 106, 61, 16);
		lblTuesday.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		lblTuesday.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblTuesday);
		
		JLabel lblWednesday = new JLabel("Wednesday");
		lblWednesday.setBounds(353, 106, 86, 16);
		lblWednesday.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		lblWednesday.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblWednesday);
		
		JLabel lblThursday = new JLabel("Thursday");
		lblThursday.setBounds(503, 106, 70, 16);
		lblThursday.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		lblThursday.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblThursday);
		
		JLabel lblFriday = new JLabel("Friday");
		lblFriday.setBounds(642, 106, 61, 16);
		lblFriday.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		lblFriday.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblFriday);
		
		JLabel lblSaturday = new JLabel("Saturday");
		lblSaturday.setBounds(779, 106, 61, 16);
		lblSaturday.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		lblSaturday.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblSaturday);
		
		JLabel lblSunday = new JLabel("Sunday");
		lblSunday.setBounds(915, 106, 61, 16);
		lblSunday.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		lblSunday.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblSunday);
		
		/**
		 * Separators for time slots
		 */
		int lineCoord=134;
		for(int i=0; i<48; i++){
			
			JSeparator timeLine= new JSeparator();
			timeLine.setBounds(47, lineCoord, 950, 22);
			timeLine.setForeground(Color.BLACK);
			add(timeLine);
			lineCoord+=20;
			
		}
		/**
		 * Time slots
		 */
		int timeCoord=130;
		for(int i=0; i<24; i++){
			if(i<10){
				JLabel time = new JLabel("0"+i+":00");
				time.setBounds(8, timeCoord, 36, 16);
				add(time);
				JLabel time2 = new JLabel("0"+i+":30");
				time2.setBounds(8, timeCoord+20, 36, 16);
				add(time2);
			}
			else{
				JLabel time = new JLabel(i+":00");
				time.setBounds(8, timeCoord, 36, 16);
				add(time);
				JLabel time2 = new JLabel(i+":30");
				time2.setBounds(8, timeCoord+20, 36, 16);
				add(time2);
			}
			timeCoord+=40;
		}
		
	}

}
