package com.proj.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.ListModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import com.proj.model.Appointment;

public class CalendarView extends JPanel{

	private int appSpacing = 10;
	private MouseListener ml = new ClickHandler();
	private ListModel<Appointment> model;
	private ArrayList<TranslucentTextArea> textAreas = new ArrayList<>();
	private ActionSupport actionSupport = new ActionSupport(this, "itemSelected");
	private int selected = -1;
	
	/**
	 * Display new appointment in the calendar.
	 */
	public TranslucentTextArea getAppointmentTextArea(int index) {
		Appointment app = model.getElementAt(index);

		int startTimePixel = getPixelFromDate(app.getStartTime());
		int endTimePixel = getPixelFromDate(app.getEndTime());
		
		//TODO: Support for appointments spanning multiple days
		//TODO: Support for any other day than monday...
		TranslucentTextArea appArea = new TranslucentTextArea(app.getDescription(), Color.RED);
		appArea.addMouseListener(ml);
		appArea.setForeground(Color.WHITE);
		appArea.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		appArea.setEditable(false);
		appArea.setBounds(65, startTimePixel, 115, endTimePixel-startTimePixel);

		return appArea;
	}
	
	private void addListInterval(int index0, int index1) {
		for (int i = index0; i <= index1; i++) {
			TranslucentTextArea area = getAppointmentTextArea(i);
			textAreas.add(i, area);
			add(area);
		}
	}

	private void removeListInterval(int index0, int index1) {
		// Remember that remaining array elements shift left when element is removed.
		for (int i = index1; i >= index0; i--) {
			remove(textAreas.remove(i));
		}
	}
	
	private void updateListInterval(int index0, int index1) {
		for (int i = index0; i <= index1; i++) {
			remove(textAreas.get(i));
			TranslucentTextArea area = getAppointmentTextArea(i);
			textAreas.set(i, area);
			add(area);
		}
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
	public CalendarView(ListModel<Appointment> model) {
		this.model = model;
		model.addListDataListener(new DataChangeHandler());
		if (model.getSize() > 0) {
			addListInterval(0, model.getSize() - 1);
		}
		
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
	
	public void setActionCommand(String actionCommand) {
		actionSupport.setActionCommand(actionCommand);
	}
	
	public String getActionCommand() {
		return actionSupport.getActionCommand();
	}

	public void addActionListener(ActionListener listener) {
		actionSupport.addActionListener(listener);
	}
	
	public void fireActionPerformed() {
		actionSupport.fireActionPerformed();
	}
	
	public Appointment getSelectedItem() {
		return selected >= 0 ? model.getElementAt(selected) : null;
	}
	
	/**
	 * Listens for changes in the list model so the view can be
	 * updated.
	 */
	private class DataChangeHandler implements ListDataListener {

		@Override
		public void contentsChanged(ListDataEvent arg0) {
			updateListInterval(arg0.getIndex0(), arg0.getIndex1());
		}

		@Override
		public void intervalAdded(ListDataEvent arg0) {
			addListInterval(arg0.getIndex0(), arg0.getIndex1());
		}

		@Override
		public void intervalRemoved(ListDataEvent arg0) {
			removeListInterval(arg0.getIndex0(), arg0.getIndex1());
		}
		
	}
	
	/**
	 * Handles clicks on appointments (that is textareas)
	 */
	private class ClickHandler extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {
			selected = textAreas.indexOf(e.getSource());
			fireActionPerformed();
		}
		
	}
	
	/**
	 * We need translucent text areas! Let's override the paintComponent
	 * method.
	 */
	private class TranslucentTextArea extends JTextArea {
		
		private Color color;

	    public TranslucentTextArea(String text, Color col) {
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
