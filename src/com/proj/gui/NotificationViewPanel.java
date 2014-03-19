package com.proj.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Scrollable;

import com.proj.model.Notification;
import java.beans.PropertyChangeListener;

public class NotificationViewPanel extends JPanel implements PropertyChangeListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	NotificationScrollPane scrollPane;
	JButton closeButton, addButton;
	ArrayList<Notification> notifications = new ArrayList<>();
	GridBagConstraints c = new GridBagConstraints();
	

	public NotificationViewPanel() {
		setLayout(new GridBagLayout());
		scrollPane = new NotificationScrollPane();
		c.gridx = 1;
		c.gridy = 0;
		add(scrollPane, c);
		closeButton = new JButton("CLose");
		c.gridx = 2;
		c.gridy = 1;
		c.anchor = GridBagConstraints.LAST_LINE_END;
		add(closeButton, c);
		closeButton.addActionListener(new CloseButtonListener());
		addButton = new JButton("Add");
		add(addButton);
		addButton.addActionListener(new CloseButtonListener());
		

	}

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

	public static void main(String[] args) {

		NotificationViewPanel viewPanel = new NotificationViewPanel();
		JFrame frame = new JFrame("Notifications");
		frame.getContentPane().add(viewPanel);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

	}

	class NotificationScrollPane extends JScrollPane {

		/**
		 * 
		 */

		GridBagConstraints x = new GridBagConstraints();
		private static final long serialVersionUID = 1L;

		public NotificationScrollPane() {
			JPanel panel = new JPanel();
			panel.setLayout(new GridBagLayout());
			notifications
					.add(new Notification(
							"Your are invited to fl�tepoteter"));
			notifications.add(new Notification("Your are invited to fl�jebf afdhakjfhjdsfjdkfhdaskjfh"));
			notifications.add(new Notification(
					"Your are invited to fl�teadsadbf"));
			notifications.add(new Notification(
					"Your are invited to fl�tepotefhjebf"));
			notifications.add(new Notification("what the fuck??!!!"));
			notifications.add(new Notification("what fuck??!!!"));
			notifications.add(new Notification("hmm??!!!"));
			notifications.add(new Notification("yo??!!!"));
			notifications.add(new Notification("??!!!"));
			notifications.add(new Notification("Is it working yet?"));
			notifications.add(new Notification("Is it working yet?"));
			notifications.add(new Notification("Is it working yet?"));
			notifications.add(new Notification("Is it working yet?"));
			notifications.add(new Notification("Message received?"));
			notifications.add(new Notification("Is it working yet?"));
			notifications.add(new Notification("Is it working yet?"));
			notifications.add(new Notification("It's working!!!"));
			
			for (int i = 0; i < notifications.size(); i++) {
				ButtonPane buttonPane = new ButtonPane(notifications.get(i));
				x.gridx = 0;
				x.gridy = i;
				x.ipady=50;
				x.anchor = GridBagConstraints.LAST_LINE_END;
				panel.add(buttonPane, x);

			}
			setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			setViewportView(panel);
			//viewport.setPreferredSize(getPreferredScrollableViewportSize());
			viewport.setPreferredSize(new Dimension(700,300));
			

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
		GridBagConstraints x = new GridBagConstraints();
		
		public String convertToMultiline(String orig)
		{
		    return "<html>" + orig.replaceAll("\n", "<br>  ");
		}
		
		public ButtonPane(Notification notification) {
			
			
			
			setLayout(new GridBagLayout());
			notificationString = notification.getText() + "\n "
					+ "notification.getTimestamp()" + "               ";
			label = new JLabel(convertToMultiline(notificationString), JLabel.LEFT);
//			x.anchor = GridBagConstraints.LINE_END;
	//		label.setText(convertToMultiline(notificationString));
			label.setPreferredSize(new Dimension(400,50));
			add(label);
			
			viewAppointmentsButton = new JButton("View Appointment");
			add(viewAppointmentsButton);
			viewAppointmentsButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					System.out.println(notificationString);

					// TODO Auto-generated method stub

				}
			});
//			setBackground(Color.RED);

		}

	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		
	}

}
