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

	private MouseListener ml = new ClickHandler();
	private ListModel<Appointment> model;
	private ArrayList<TranslucentTextArea[]> textAreas = new ArrayList<>();
	private ActionSupport actionSupport = new ActionSupport(this, "itemSelected");
	private int selected = -1;
	
	/**
	 * Display new appointment in the calendar.
	 */
	public TranslucentTextArea[] getAppointmentTextArea(int index) {
		Appointment app = model.getElementAt(index);

		int[] startTimePixel = getPixelFromDate(app.getStartTime());
		int[] endTimePixel = getPixelFromDate(app.getEndTime());

		
		int numDays = ((endTimePixel[0]-startTimePixel[0])/134)+1;
		
		if (numDays < 0){
			numDays = 7+numDays;
		}
		
		int appStartPixelX = 65+startTimePixel[0]; // X coordinate for appointment start
		int appStartPixelY = startTimePixel[1]; // Y coordinate for appointment start
		int appWidth = 115; // Width of appointment
		int appLength = endTimePixel[1]-startTimePixel[1]; // Length of appointment
		
		TranslucentTextArea[] ttaArray = new TranslucentTextArea[numDays];
		
		//Support for appointments spanning multiple days
		
		// Normal appointment
		if(numDays==1){
			TranslucentTextArea appArea = new TranslucentTextArea(app.getDescription(), Color.BLUE);
			appArea.addMouseListener(ml);
			appArea.setForeground(Color.WHITE);
			appArea.setFont(new Font("Lucida Grande", Font.BOLD, 13));
			appArea.setEditable(false);
			appArea.setBounds(appStartPixelX, appStartPixelY, appWidth, appLength);
			ttaArray[0] = appArea;

		}else{
			for (int i=0; i<numDays; i++){
				// Ending at midnight
				if(i==0){
					appStartPixelX=65+startTimePixel[0];
					appStartPixelY=startTimePixel[1];
					appLength=989-startTimePixel[1];
					
				}
				// Starting at midnight
				else if(i==numDays-1){
					appStartPixelX = 65+endTimePixel[0];
					appStartPixelY = 30;
					appLength = endTimePixel[1] - 30;
				}
				// Occupied all day
				else{
					appStartPixelX=startTimePixel[0]+i*134+65;
					appStartPixelY=30;
					appLength=989-30;
				}
				
				TranslucentTextArea appArea = new TranslucentTextArea(app.getDescription(), Color.BLUE);
				appArea.addMouseListener(ml);
				appArea.setForeground(Color.WHITE);
				appArea.setFont(new Font("Lucida Grande", Font.BOLD, 13));
				appArea.setEditable(false);
				appArea.setBounds(appStartPixelX, appStartPixelY, appWidth, appLength);
				
				ttaArray[i] = appArea;
			}	
		}

		return ttaArray;
	}
	
	private void addListInterval(int index0, int index1) {
		for (int i = index0; i <= index1; i++) {
			TranslucentTextArea[] area = getAppointmentTextArea(i);
			textAreas.add(i, area);
			for (TranslucentTextArea t : area){
				add(t);
			}
		}
		repaint();
	}

	private void removeListInterval(int index0, int index1) {
		// Remember that remaining array elements shift left when element is removed.
		for (int i = index1; i >= index0; i--) {
			TranslucentTextArea[] removed = textAreas.remove(i);
			for (TranslucentTextArea t : removed){
				remove(t);	
			}
		}
		repaint();
	}
	
	private void updateListInterval(int index0, int index1) {
		for (int i = index0; i <= index1; i++) {
			
			TranslucentTextArea[] removed = textAreas.get(i);
			for (TranslucentTextArea t : removed){
				remove(t);
			}
			
			TranslucentTextArea[] area = getAppointmentTextArea(i);
			textAreas.set(i, area);
			
			for (TranslucentTextArea t : area){
				add(t);
			}
		}
		repaint();
	}

	/**
	 * Instructions: Each hour is 40 pixels long, and each minute is 2/3 pixels long
	 * The 00:00 time slot lays on pixel 30
	 * Each day is 134 pixels wide
	 * Monday starts on pixel 65
	 */
	public int[] getPixelFromDate(Date d){
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		int weekday = cal.get(Calendar.DAY_OF_WEEK);
		int hours = cal.get(Calendar.HOUR_OF_DAY);
		int minutes = cal.get(Calendar.MINUTE);
		
		int pixelX = (int) 134 * (weekday-2);
		int pixelY = (int) (30 + (hours*40) + (minutes*(2.0/3)));
		
		return new int[]{pixelX, pixelY};
	}
	
	/**
	 * Creates the calendar view
	 */
	public CalendarView(ListModel<Appointment> model) {
		this.model = model;		
		
		/**
		 * Modifies the scrollpanel
		 */
		setMinimumSize(new Dimension(1000, 1000));
		setPreferredSize(new Dimension(1000, 1000));
		setMaximumSize(new Dimension(1000, 1000));
		setLayout(null);
		
		
		/**
		 * Separators for weekdays
		 */
		int dayLineCoord=50;
		for(int i=0; i<8; i++){
			
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
		
		model.addListDataListener(new DataChangeHandler());
		if (model.getSize() > 0) {
			addListInterval(0, model.getSize() - 1);
		}
		repaint();

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
	 * Handles clicks on appointments (that is text areas)
	 */
	private class ClickHandler extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {
			for (int i=0; i<textAreas.size(); i++){
				TranslucentTextArea[] ta = textAreas.get(i);
				for (TranslucentTextArea t: ta){
					if (t == e.getSource()){
						selected = i;
						fireActionPerformed();
						return;
					}
				}
			}
			
			
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
	        color = new Color(col.getRed(), col.getGreen(), col.getBlue(), 64 );
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
