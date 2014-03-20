package com.proj.gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.Timer;

import com.proj.model.Appointment;
import com.proj.model.Notification;

public class NotificationToast extends JDialog {
	
	private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private static int
			MARGIN = 30,
			DELAY = 5000;

	public NotificationToast(Notification notification) {
		setAlwaysOnTop(true);
		setUndecorated(true);
		
		setSize(200, 100);
		setLocation(
			screenSize.width - getWidth() - MARGIN,
			MARGIN
		);
		
		setLayout(new GridLayout(0, 1));
		
		Appointment appointment = notification.getAppointment();
		
		if (appointment != null) {
			JLabel title = getCenteredLabel(notification.getAppointment().getDescription());
			title.setFont(title.getFont().deriveFont(16.0f));
			add(title);
		}
		add(getCenteredLabel(notification.getText()));
		
		setVisible(true);
		
		new Timer(DELAY, new TimerHandler()).start();
	}
	
	private JLabel getCenteredLabel(String s) {
		JLabel label = new JLabel(s);
		label.setHorizontalAlignment(JLabel.CENTER);
		return label;
	}
	
	private class TimerHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			dispose();			
		}
		
	}
}
