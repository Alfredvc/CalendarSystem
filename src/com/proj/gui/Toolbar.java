package com.proj.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

class Toolbar extends JPanel {
	private ActionListener actionHandler = new ActionHandler();
	private MainCalendar parent;
	
	public Toolbar(MainCalendar parent) {
		super();
		this.parent = parent;
		
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		add(createButton("This week", "thisWeek"));
		add(createButton("<", "previousWeek"));
		add(new WeekPanel());	
		add(createButton(">", "nextWeek"));
		
		
		add(Box.createHorizontalGlue());
		
		JLabel lblMarch = new JLabel("September 10 - December 16");
		lblMarch.setHorizontalAlignment(SwingConstants.CENTER);
		lblMarch.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		add(lblMarch);
		
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
	
	private class WeekPanel extends JPanel {
		
		private JTextField textField;
		private JLabel label;

		public WeekPanel() {
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			
			label = new JLabel("Week");
			label.setHorizontalAlignment(SwingConstants.CENTER);
			label.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 5));
			add(label);
			
			textField = new JTextField(2);
			textField.setColumns(2);
			textField.setMaximumSize(new Dimension(20,20));
			textField.setAlignmentX(CENTER_ALIGNMENT);
			textField.setHorizontalAlignment(JTextField.CENTER);
			add(textField);
			
		}

		@Override
		public Dimension getMaximumSize() {
			return new Dimension(label.getPreferredSize().width, super.getPreferredSize().height);
		}
	}
	
	private class ActionHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			String command = arg0.getActionCommand();
			switch (command) {
		
			}
		}
	}
}