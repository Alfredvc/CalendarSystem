package com.proj.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import com.proj.model.Appointment;
import com.proj.model.Model;
import com.proj.test.RandomGenerator;
import com.sun.xml.internal.bind.v2.model.annotation.AbstractInlineAnnotationReaderImpl;

public class CalendarView extends JPanel{

	private int appSpacing = 10;
	private MouseListener ml = new ClickHandler();
	private MainCalendar mainCal;
	
	HashMap<Appointment, TransclucentTextArea> appoinmentMap = new HashMap<Appointment, TransclucentTextArea>();
	
	/**
	 * Display new appointment in the calendar.
	 */
	public void displayAppointment(Appointment app){
		
		int startTimePixel = getPixelFromDate(app.getStartTime());
		int endTimePixel = getPixelFromDate(app.getEndTime());
		
		
		TransclucentTextArea appArea = new TransclucentTextArea(app.getDescription(), Color.RED);
		appArea.addMouseListener(ml);
		add(appArea);
		appArea.setForeground(Color.WHITE);
		appArea.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		appArea.setEditable(false);
		appArea.setBounds(65, startTimePixel, 115, endTimePixel-startTimePixel);
		
		appoinmentMap.put(app, appArea);
		System.out.println(appoinmentMap);
		
	}
	
	/**
	 * Remove an appointment from the calendar
	 */
	public void removeAppointment(Appointment app){
		
		remove(appoinmentMap.get(app));
		appoinmentMap.remove(app);
		
	}
	
	/**
	 * Instructions: Each hour is 40 pixels long, and each minute is 2/3 pixels 
	 * The 00:00 time slot lays on pixel 30
	 */
	public int getPixelFromDate(Date d){
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		int hours = cal.get(Calendar.HOUR_OF_DAY);
		int minutes = cal.get(Calendar.MINUTE);
		
		int pixel = (int) (30 + (hours*40) + (minutes*(2.0/3)));
		return pixel;	
	}
	
	/**
	 * Creates the calendar view
	 */
	public CalendarView(MainCalendar mainCalendar) {
		
		mainCal = mainCalendar;
		
		/*// ***********	For testing purposes	***********
		Appointment appointment = RandomGenerator.generateAppointment();
		appointment.setEndTime(new Date(2014, 3, 18, 21, 48));
		//System.out.println(appointment.getStartTime());
				
		displayAppointment(appointment);*/
		
		
		/**
		 * Modifies the scrollpanel
		 */
		setMinimumSize(new Dimension(970, 1410));
		setPreferredSize(new Dimension(970, 1410));
		setMaximumSize(new Dimension(970, 1410));
		setLayout(null);
		
		
		/**
		 * Separators for weekdays
		 */
		int dayLineCoord=50;
		for(int i=0; i<7; i++){
			
			JSeparator dayLine1 = new JSeparator();
			dayLine1.setBounds(dayLineCoord, 0, 12, 1010);
			dayLine1.setOrientation(SwingConstants.VERTICAL);
			dayLine1.setForeground(Color.BLACK);
			add(dayLine1);
			dayLineCoord+=134;
			
		}
		
		/**
		 * Week day labels
		 */
		JLabel lblTime = new JLabel("Time");
		lblTime.setBounds(8, 0, 61, 16);
		add(lblTime);
		
		int weekdayLabelCoord=55;
		for(int i=0; i<7; i++){
			
			JLabel weekdayLabel = new JLabel(Weekdays.values()[i].name());
			weekdayLabel.setBounds(weekdayLabelCoord, 5, 134, 16);
			weekdayLabel.setFont(new Font("Lucida Grande", Font.BOLD, 13));
			weekdayLabel.setHorizontalAlignment(SwingConstants.CENTER);
			add(weekdayLabel);
			weekdayLabelCoord+=134;
			
		}
		
		/**
		 * Separators for time slots
		 */
		int lineCoord=24;
		for(int i=0; i<48; i++){
			
			JSeparator timeLine= new JSeparator();
			timeLine.setBounds(47, lineCoord, 950, 22);
			timeLine.setForeground(Color.BLACK);
			add(timeLine);
			lineCoord+=20;
			
		}
		
		JSeparator timeLine = new JSeparator();
		timeLine.setBounds(8, lineCoord, 990, 22);
		timeLine.setOrientation(SwingConstants.HORIZONTAL);
		timeLine.setForeground(Color.BLACK);
		add(timeLine);
		
		
		/**
		 * Time slots
		 */
		int timeCoord=20;
		for(int i=0; i<24; i++){
			if(i<10){
				JLabel time = new JLabel("0"+i+":00");
				time.setBounds(8, timeCoord, 40, 16);
				time.setFont(new Font("Lucida Grande", Font.BOLD, 13));
				add(time);
				JLabel time2 = new JLabel("0"+i+":30");
				time2.setBounds(8, timeCoord+20, 36, 16);
				add(time2);
			}
			else{
				JLabel time = new JLabel(i+":00");
				time.setBounds(8, timeCoord, 40, 16);
				time.setFont(new Font("Lucida Grande", Font.BOLD, 13));
				add(time);
				JLabel time2 = new JLabel(i+":30");
				time2.setBounds(8, timeCoord+20, 36, 16);
				add(time2);
			}
			timeCoord+=40;
		}
		
		
		
	}
	
	class ClickHandler extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {
			System.out.println("Clicked!");
			
			Iterator<Appointment> iter = appoinmentMap.keySet().iterator();
			Appointment key = null;
			while(iter.hasNext()) {
			    key = iter.next();
			    if (appoinmentMap.get(key) == e.getSource()){
			    	break;
			    }
			}
			if (key != null){
				mainCal.viewAppointment(key);
			}
			
		}
		
	}
	
	class TransclucentTextArea extends JTextArea {
		
		private Color color;

	    public TransclucentTextArea(String text, Color col) {
	        super(text);
	        setOpaque(false);
	        setLineWrap(true);
	        setWrapStyleWord(true);
	        color = new Color(col.getRed(), col.getGreen(), col.getBlue(), 128);
	    }

	    @Override
	    protected void paintComponent(Graphics g) {
	        Graphics2D g2d = (Graphics2D) g;
	        Insets insets = getInsets();
	        int x = insets.left;
	        int y = insets.top;
	        int width = getWidth() - (insets.left + insets.right);
	        int height = getHeight() - (insets.top + insets.bottom);
	        g2d.setColor(color);
	        g2d.fillRect(x, y, width, height);
	        super.paintComponent(g);
	    }
	}

}
