package com.proj.gui;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.proj.model.Model;

public class MainCalendar extends JFrame {
	private Model model;

	/**
	 * Test code...
	 */
	public static void main(String[] args) {
		new MainCalendar(new Model());
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
			add(appArea2);
			appArea2.setBackground(Color.GREEN);
			appArea2.setText("Meeting with Lars");
			appArea2.setBounds(70, 140+timeStart*40, 115/2, (timeEnd-timeStart)*40);
		}
		else{
			JTextArea appArea = new JTextArea();
			add(appArea);
			appArea.setBackground(Color.ORANGE);
			appArea.setText("Meeting with Jane");
			appArea.setBounds(70, 140+timeStart*40, 115, (timeEnd-timeStart)*40);
			overlapping = true;
		}
	}
	
	public void showThisWeek() {
		
	}
	
	public void showNextWeek() {
		
	}
	
	public void showPreviousWeek() {
		
	}
	
	public void showWeek() {
		
	}

	/**
	 * Create the frame.
	 */
	public MainCalendar(Model model) {
		this.model = model;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		
		// Add toolbar
		add(new Toolbar(this));

		// Add calendarview
		JScrollPane scrollPane = new JScrollPane(new CalendarView());
		add(scrollPane);
		
		setVisible(true);
		
	}
}
