package com.proj.gui;


import java.awt.Component;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.proj.model.Notification;

public class NotificationPanel extends JPanel implements ListSelectionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JList<Notification> notificationList;
	JButton closeButton, viewAppointmentButton;
	DefaultListModel<Notification> model;
	GridBagConstraints c = new GridBagConstraints();
	JFrame frame;
	
	

	public NotificationPanel() {

		setLayout(new GridBagLayout());
		model = new DefaultListModel<Notification>();
		notificationList = new JList<Notification>(model);
		notificationList.setCellRenderer(new NotificationRenderer());
		
		JScrollPane myJScrollPane = new JScrollPane(notificationList);
		myJScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		notificationList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		notificationList.setModel(model);
		c.gridx = 0;
		c.gridy = 0;
		add(myJScrollPane, c);
		notificationList.addListSelectionListener(this);

		viewAppointmentButton = new JButton("View Appointment");
		c.gridx = 1;
		c.gridy = 0;
		add(viewAppointmentButton);
		viewAppointmentButton.addActionListener(new ButtonListener());

		closeButton = new JButton("Close");
		c.anchor = GridBagConstraints.FIRST_LINE_END;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 1;
		add(closeButton, c);
		closeButton.addActionListener(new ButtonListener());

	}

	class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == closeButton) {
				System.out.println("Closing Program");
				// frame.dispose();
				System.exit(ABORT);

			}
			if (e.getSource() == viewAppointmentButton &&
					notificationList.getSelectedValue() != null) {
				System.out.println(notificationList.getSelectedValue());

			}

		}
	}

	public void setModel(DefaultListModel<Notification> model) {
		this.model = model;
		notificationList.setModel(model);

	}

	public DefaultListModel<Notification> getModel() {
		return this.model;
	}

	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		// TODO Auto-generated method stub

	}

	public static void main(String args[]) {

		Notification notification1 = new Notification(
				"You are invited to 'Fløtepoteter' ");
		Notification notification2 = new Notification(
				"You are invited to 'More Bullshit' ");
		NotificationPanel panel = new NotificationPanel();
		DefaultListModel<Notification> model = panel.getModel();
		model.addElement(notification1);
		model.addElement(notification2);
		model.addElement(notification1);
		model.addElement(notification2);
		panel.setModel(model);
		JFrame frame = new JFrame("Notifications");
		frame.getContentPane().add(panel);
		frame.pack();
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

	}

	class NotificationRenderer implements ListCellRenderer<Notification> {

		@Override
		public Component getListCellRendererComponent(JList list,
				Notification notification, int index, boolean isSelected,
				boolean cellHasFocus) {

			String notificationString = notification.getText() + " "
					+ "notification.getTimestamp()  ";

			JLabel label = new JLabel(notificationString);
			if (cellHasFocus) {
				label.setOpaque(true);

			}
			return label;
		}
	}
}