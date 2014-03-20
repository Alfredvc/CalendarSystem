package com.proj.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;

import com.proj.model.Notification;

public class Notifications extends JFrame {

	private static final long serialVersionUID = 1L;
	JScrollPane scrollPane;
	JButton closeButton, addButton;
	ArrayList<Notification> notifications = new ArrayList<>();
	

	public Notifications(ListModel<Notification> listModel) {
		super("Notifications");
	
		ActiveList<ButtonPane, Notification> list = new ActiveList<>(listModel, new ButtonPaneConstructor());

		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		
		scrollPane = new JScrollPane(list);
		scrollPane.setAlignmentX(RIGHT_ALIGNMENT);
		add(scrollPane);

		closeButton = new JButton("CLose");
		closeButton.setAlignmentX(RIGHT_ALIGNMENT);
		add(closeButton);
		
		closeButton.addActionListener(new CloseButtonListener());
		
		setSize(500,300);
		setLocationRelativeTo(null);
		setVisible(true);

	}

	/**
	 * Listens to clicks on close button
	 */
	class CloseButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == closeButton) {
				System.exit(ABORT);
				System.out.println("Closing");
				
			}
			if(e.getSource()==addButton){
				notifications.add(new Notification("adding new notification"));
			}

		}
	}

	
	/**
	 * Constructs the button pane for the list
	 */
	class ButtonPaneConstructor implements ActiveComponentConstructor<ButtonPane, Notification> {

		@Override
		public ButtonPane constructActiveListComponent(Notification notification) {
			return new ButtonPane(notification);
		}

		@Override
		public ButtonPane updateActiveListComponent(Component old, Notification notification) {
			if (old instanceof ButtonPane) {
				ButtonPane buttonPane = (ButtonPane) old;
				buttonPane.setNotification(notification);
				return buttonPane;
			}
			return constructActiveListComponent(notification);
		}
		
	}

	private static SimpleDateFormat sdformat = new SimpleDateFormat("d. MMMM, yyyy");

	
	/**
	 * Panel that displays a notification. This is duplicated down the list.
	 */
	class ButtonPane extends JPanel {
		private static final long serialVersionUID = 1L;
		private Notification notification;
		JLabel textLabel;
		JLabel dateLabel;
		JButton viewAppointmentsButton;
	
		public ButtonPane(Notification notification) {
			setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
			setLayout(new GridBagLayout());
			
			GridBagConstraints c = new GridBagConstraints();
			
			textLabel = new JLabel();
			textLabel.setFont(textLabel.getFont().deriveFont(14.0f));
			c.gridx = 0;
			c.gridy = 0;
			c.weightx = 1;
			c.weighty = 0;
			c.anchor = GridBagConstraints.WEST;
			add(textLabel, c);
			
			dateLabel = new JLabel();
			c.gridy = 1;
			add(dateLabel, c);
			
			c.weightx = 0;
			c.gridx = 1;
			c.gridy = 0;
			c.gridheight = 2;
			c.anchor = GridBagConstraints.EAST;
			viewAppointmentsButton = new JButton("View Appointment");
			add(viewAppointmentsButton, c);
			
			viewAppointmentsButton.addActionListener(new ActionHandler());
			
			setNotification(notification);
			
		}
		
		@Override
		public Dimension getMaximumSize() {
			return new Dimension(
					super.getMaximumSize().width,
					getPreferredSize().width
				);
		}
		
		public void setNotification(Notification notification) {
			this.notification = notification;
			textLabel.setText(notification.getText());
			dateLabel.setText(sdformat.format(notification.getTimestamp()));
		}
		
		/**
		 * Handles click on button
		 */
		private class ActionHandler implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println(notification.getAppointment());				
			}
			
		}

	}

}
