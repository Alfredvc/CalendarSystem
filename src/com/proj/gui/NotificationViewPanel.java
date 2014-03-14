package com.proj.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


import com.proj.model.Notification;

public class NotificationViewPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	NotificationScrollPane scrollPane;
	JButton closeButton;
	ArrayList<Notification> notifications = new ArrayList<>();
	GridBagConstraints x = new GridBagConstraints();
	
	

	public NotificationViewPanel() {
		setLayout(new GridBagLayout());
		scrollPane = new NotificationScrollPane();
		x.gridx=0;
		x.gridy=0;
		add(scrollPane,x);
		closeButton = new JButton("CLose");
		x.gridx=1;
		x.gridy=0;
		add(closeButton,x);
		closeButton.addActionListener(new CloseButtonListener());

	}

	class CloseButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == closeButton) {
				System.exit(ABORT);
				System.out.println("Closing");
			}

		}
	}

	public static void main(String[] args) {
	
		NotificationViewPanel viewPanel = new NotificationViewPanel();
		JFrame frame = new JFrame("Notifications");
		frame.getContentPane().add(viewPanel);
		frame.setResizable(true);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

	}

	class NotificationScrollPane extends JScrollPane {

		/**
		 * 
		 */
		
		private static final long serialVersionUID = 1L;

		public NotificationScrollPane() {

			
			setSize(500, 500);
			notifications.add(new Notification("Your are invited to fløtepotetefhjebf"));
			notifications.add(new Notification("Your are invited to fløjebf"));
			notifications.add(new Notification("Your are invited to fløteadsadbf"));
			notifications.add(new Notification("Your are invited to fløtepotefhjebf"));
			notifications.add(new Notification("what the fuck??!!!"));
			notifications.add(new Notification("what fuck??!!!"));
			notifications.add(new Notification("hmm??!!!"));
			notifications.add(new Notification("yo??!!!"));
			notifications.add(new Notification("??!!!"));
			notifications.add(new Notification("Is it working yet?"));
			notifications.add(new Notification("Is it working yet?"));
			notifications.add(new Notification("Is it working yet?"));
			notifications.add(new Notification("Is it working yet?"));
			notifications.add(new Notification("Is it working yet?"));
			for (int i = 0; i < notifications.size(); i++) {
				ButtonPane buttonPane = new ButtonPane(notifications.get(i));
				add(buttonPane);
				
		}
			setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			
			
		}

	}

	class ButtonPane extends JPanel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		JLabel label;
		JButton viewAppointmentsButton;
		String notificationString;
		GridBagConstraints c = new GridBagConstraints();

		public ButtonPane(Notification notification) {
			setLayout(new GridBagLayout());
		
		
				
				notificationString = notification.getText() + " "
						+ "notification.getTimestamp()";
				label = new JLabel(notificationString);
				c.gridx = 0;
				c.gridy = 0;
				add(label, c);
				viewAppointmentsButton = new JButton("View Appointment");
				c.gridx = 1;
				c.gridy = 0;
				add(viewAppointmentsButton, c);
				viewAppointmentsButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						System.out.println("hmmmmmm");

						// TODO Auto-generated method stub

					}
				});

			}

		}

	}


