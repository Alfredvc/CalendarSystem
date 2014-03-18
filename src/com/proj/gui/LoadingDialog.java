package com.proj.gui;

import java.awt.Frame;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

/**
 * Dialog displayed while waiting for stuff to happen
 */
class LoadingDialog extends JDialog {
	
	public LoadingDialog(Frame owner, String text) {
		super(owner, "Please Wait");
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		JLabel label = new JLabel(text);
		label.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
		panel.add(label);
		JProgressBar progressBar = new JProgressBar();
		progressBar.setIndeterminate(true);
		panel.add(progressBar);
		setContentPane(panel);
		pack();
	
		setLocationRelativeTo(null); // Centers window
		setVisible(true);
	}
}
