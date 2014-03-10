package com.proj.gui;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;



public class Login extends JPanel{


    static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private JTextField emailInput;
	private JTextField passwordInput;

	public Login() {
		
		setLayout(null);
		
		JLabel emailLabel = new JLabel("Email:", SwingConstants.RIGHT);
		emailLabel.setBounds(10, 20, 80, 25);
		add(emailLabel);
		JLabel emailPostfix = new JLabel("@company.com", SwingConstants.LEFT);
		emailPostfix.setBounds(260, 20, 120, 25);
		add(emailPostfix);
		JLabel passwordLabel = new JLabel("Password:", SwingConstants.RIGHT);
		passwordLabel.setBounds(10, 50, 80, 25);
		add(passwordLabel);
		emailInput = new JTextField();
		emailInput.setColumns(20);
		emailInput.setBounds(100, 20, 160, 25);;
		add(emailInput);
		passwordInput = new JPasswordField();
		passwordInput.setBounds(100, 50, 260, 25);
		add(passwordInput);

		JButton loginButton = new JButton("Login");
		loginButton.setBounds(100, 80, 100, 30);
		add(loginButton);
		
		JButton registerButton = new JButton("Cancel");
		registerButton.setBounds(260, 80, 100, 30);
		add(registerButton);
	}

	public static void main(String args[]) {

		JFrame frame = new JFrame("Calender System - Log In");
		frame.setSize(420, 170);
		int xLocation = (int) (screenSize.getWidth() - frame.getWidth()) / 2;
		int yLocation = (int) (screenSize.getHeight() - frame.getHeight()) / 2;		
		frame.setLocation(xLocation, yLocation);
		frame.setResizable(false);
		frame.add(new Login());
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	
}
