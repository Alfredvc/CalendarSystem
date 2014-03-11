package com.proj.gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;



public class Login extends JFrame{

    static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); // Dimensions of client screen
    
    private JTextField emailInput;
	private JPasswordField passwordInput;

	
	
	public Login() {
		
		
		// Setting up the Frame, setting the size, position and making it fixed size
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(420, 170);
		int xLocation = (int) (screenSize.getWidth() - getWidth()) / 2;
		int yLocation = (int) (screenSize.getHeight() - getHeight()) / 2;		
		setLocation(xLocation, yLocation);
		setResizable(false);
		setLayout(null);
		
		// Adding the different labels
		JLabel emailLabel = new JLabel("Email:", SwingConstants.RIGHT);
		emailLabel.setBounds(10, 20, 80, 25);
		add(emailLabel);
		JLabel emailPostfix = new JLabel("@company.com", SwingConstants.LEFT);
		emailPostfix.setBounds(260, 20, 120, 25);
		add(emailPostfix);
		JLabel passwordLabel = new JLabel("Password:", SwingConstants.RIGHT);
		passwordLabel.setBounds(10, 50, 80, 25);
		add(passwordLabel);
		
		// Adding Username/Email and Password fields
		emailInput = new JTextField();
		emailInput.setColumns(20);
		emailInput.setBounds(100, 20, 160, 25);;
		add(emailInput);
		passwordInput = new JPasswordField(); // NB JPasswordField, use getPassword()
		passwordInput.setBounds(100, 50, 260, 25);
		add(passwordInput);

		// Creating both the login and cancel buttons
		JButton loginButton = new JButton("Login");
		loginButton.setBounds(100, 80, 100, 30);
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//System.out.println(emailInput.getText() + "." + new String(passwordInput.getPassword()));
			}
		});	
		add(loginButton);
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setBounds(260, 80, 100, 30);
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				dispose(); // Closes the window and hopefully stops it all
			}
		});	
		add(cancelButton);

		setVisible(true);
	}
}

