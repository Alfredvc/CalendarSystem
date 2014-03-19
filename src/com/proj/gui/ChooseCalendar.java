package com.proj.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;

import com.proj.model.Employee;
import com.proj.model.Model;

public class ChooseCalendar extends JFrame {
	private FuzzyDropdown<Employee> fuzzyDropdown;
	private SelectedCalendarsListModel selectedCalendars;
	private SelectedCalendarsListModel shownCalendars;
	
	
	public ChooseCalendar(Model model, SelectedCalendarsListModel shownCalendars) {
		super("Choose Calendars");
		this.shownCalendars = shownCalendars;
		
		setLayout(new GridBagLayout());
		setSize(250, 300);
		
		// We need a "working model"
		selectedCalendars = shownCalendars.clone();

		// NOTICE: Only one constraints object
		GridBagConstraints constraints = createGridBagConstraints();

		fuzzyDropdown = getFuzzyDropdown(model);
		constraints.gridx = 0;
		constraints.gridy = 0;
		add(fuzzyDropdown, constraints);
		
		JList<Employee> list = new JList<>(selectedCalendars);
		constraints.gridy = 1;
		constraints.weighty = 1;
		add(new JScrollPane(list), constraints);
		
		ButtonPane buttonPane = new ButtonPane();
		constraints.gridy = 2;
		constraints.weighty = 0;
		add(buttonPane, constraints);
		
		
		
		setVisible(true);
	}

	private GridBagConstraints createGridBagConstraints() {
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.weightx = 1;
		return constraints;
	}
	
	private FuzzyDropdown<Employee> getFuzzyDropdown(Model model) {
		ListModel<Employee> listModel =  new ArrayListModel<Employee>(
				Arrays.asList(model.getEmployees())
			);
		
		FuzzyDropdown<Employee> fuzzyDropdown = new FuzzyDropdown<>(listModel);
		fuzzyDropdown.addActionListener(new FuzzyActionHandler());
		return fuzzyDropdown;
	}
	
	public void cancel() {
		dispose();
	}
	
	private void addCalendar() {
		Employee selected = fuzzyDropdown.getSelectedValue();
		if (selected != null && !shownCalendars.contains(selected)) {
			shownCalendars.add(selected);
			fuzzyDropdown.reset();
		}
	}
	
	private void save() {
		// Quick and dirty!
		shownCalendars.clear();
		shownCalendars.addAll(selectedCalendars);

		dispose();
	}
	
	private void reset() {
		selectedCalendars.reset();
	}
	
	/**
	 * Listens to actions from the fuzzydropdown
	 */
	private class FuzzyActionHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			addCalendar();
		}
	}
	
	/**
	 * Button pane at the bottom
	 */
	class ButtonPane extends JPanel implements ActionListener {
		public ButtonPane() {
			setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
			
			add(createButton("Reset", "reset"));
			
			add(Box.createHorizontalGlue());
			
			add(createButton("Cancel", "cancel"));
			add(createButton("Ok", "ok"));
		}
		
		private JButton createButton(String label, String actionCommand) {
			JButton button = new JButton(label);
			button.setActionCommand(actionCommand);
			button.addActionListener(this);
			return button;
		}
		
		@Override
		public void actionPerformed(ActionEvent event) {
			String action = event.getActionCommand();
			switch (action) {
			case "ok": save(); break;
			case "cancel": cancel(); break;
			case "reset": reset(); break;
			}
		}
	}

}
