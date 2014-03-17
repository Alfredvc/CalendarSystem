package com.proj.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.proj.model.*;

public class ChooseCalendar extends JFrame {
	
	public ChooseCalendar() {
		super("Choose Calendars");
		
		setLayout(new GridBagLayout());
		setSize(250, 300);

		// NOTICE: Only one constraints object
		GridBagConstraints constraints = createGridBagConstraints();

		//TODO: This is only an example list!
		DefaultListModel<Employee> listModel = new DefaultListModel<Employee>();
		listModel.addElement(new Employee("Truls", "Truls", 787));

		FuzzySearchDropdown<Employee> calendarSelector = new FuzzySearchDropdown<>(listModel);
		constraints.gridx = 0;
		constraints.gridy = 0;
		add(calendarSelector, constraints);
		
		//TODO: This is only an example list! (Even wrong datatype)
		JList<Employee> list = new JList<>(listModel);
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
	
	public void cancel() {
		dispose();
	}
	
	
	public static void main(String[] args) {
		JFrame f = new ChooseCalendar();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
			case "ok": /* TODO */ cancel(); break;
			case "cancel": cancel(); break;
			case "reset": /*TODO */ cancel(); break;
			}
		}
	}

}
