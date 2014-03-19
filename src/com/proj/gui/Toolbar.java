package com.proj.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


class Toolbar extends JPanel {
	private ActionListener actionHandler = new ActionHandler();
	private ActionSupport actionSupport = new ActionSupport(this, "toolbar");
	private JLabel dateLabel;
	private CalendarModel model;
	
	public Toolbar(CalendarModel model) {
		super();
		this.model = model;
		
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		add(createButton("This week", "thisWeek"));
		add(createButton("<", "previousWeek"));
		add(new WeekPanel());	
		add(createButton(">", "nextWeek"));
		
		
		add(Box.createHorizontalGlue());
		
		dateLabel = new JLabel();
		dateLabel.setHorizontalAlignment(SwingConstants.CENTER);
		refreshWeekDates();
		add(dateLabel);
		
		add(Box.createHorizontalGlue());
		
		add(createButton("Notifications", "notifications"));
		add(createButton("Choose calendars", "chooseCalendars"));
		add(createButton("New appointment", "newAppointment"));			
	}

	private JButton createButton(String text, String actionCommand) {
		JButton button = new JButton(text);
		button.setActionCommand(actionCommand);
		button.addActionListener(actionHandler);
		return button;
	}
	
	private void refreshWeekDates() {
		SimpleDateFormat format = new SimpleDateFormat("d. MMM yyyy");
		int week = model.getWeek();
		int year = model.getYear();
		
		Calendar calendar = Calendar.getInstance();
		calendar.setWeekDate(year, week, Calendar.MONDAY);
		String newText = format.format(calendar.getTime());
		calendar.setWeekDate(year, week, Calendar.SUNDAY);
		newText += " - " + format.format(calendar.getTime());
		
		dateLabel.setText(newText);
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
	
	private class WeekPanel extends JPanel implements PropertyChangeListener, ActionListener, FocusListener {
		
		private JTextField textField;
		private JLabel label;

		public WeekPanel() {
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			model.addPropertyChangeListener(this);
			
			label = new JLabel("Week");
			label.setHorizontalAlignment(SwingConstants.CENTER);
			label.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 5));
			add(label);
			
			textField = new JTextField(2);
			textField.setColumns(2);
			textField.setHorizontalAlignment(JTextField.CENTER);
			textField.setBorder(null);
			textField.setBackground(null);
			textField.addActionListener(this);
			textField.setFont(textField.getFont().deriveFont(20.0f));
			refreshWeek();
			add(textField);
			
		}
		
		private void updateWeek() {
			String text = textField.getText();
			if (text.matches("^\\d{1,2}$")) {
				try {
					model.setWeek(Integer.parseInt(text));
				} catch (IllegalArgumentException e) {
					JOptionPane.showMessageDialog(
							this,
							"Please enter a week number between 1 and 52!",
							"Wierd Week Number o.O",
							JOptionPane.ERROR_MESSAGE
						);
				}
			} else {
				refreshWeek();
			}
		}
		
		public void refreshWeek() {
			textField.setText(Integer.toString(model.getWeek()));
		}

		@Override
		public Dimension getMaximumSize() {
			return new Dimension(label.getPreferredSize().width, super.getPreferredSize().height);
		}

		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			// Update the week field when the model changes
			if (evt.getPropertyName().equals(CalendarModel.WEEK_PROP)) {
				refreshWeek();
				refreshWeekDates();
			}
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			updateWeek();
		}

		@Override
		public void focusLost(FocusEvent arg0) {
			updateWeek();			
		}

		@Override
		public void focusGained(FocusEvent arg0) {}
	}
	
	
	/**
	 * Passes action events on to all action listeners!
	 */
	private class ActionHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			String command = arg0.getActionCommand();
			switch (command) {
			case "nextWeek":
				model.setWeekRelative(1);
				break;
				
			case "previousWeek":
				model.setWeekRelative(-1);
				break;
				
			case "thisWeek":
				model.resetWeek();
				break;
				
			default:
				actionSupport.fireActionPerformed(arg0);					
			}
		}
	}
}