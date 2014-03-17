package com.proj.gui;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutionException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;

import com.proj.client.Client;


public class Login extends JFrame{

    static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); // Dimensions of client screen
    
    private JTextField emailInput;
	private JPasswordField passwordInput;
	private Client client;
	private LoadingDialog loadingDialog;

	
	
	public Login(Client client) {
		this.client = client;
		
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
		emailInput.setName("emailInput");
		emailInput.setBounds(100, 20, 160, 25);
		add(emailInput);
		
		passwordInput = new JPasswordField(); // NB JPasswordField, use getPassword()
		passwordInput.setName("passwordInput");
		passwordInput.setBounds(100, 50, 260, 25);
		add(passwordInput);

		// Creating both the login and cancel buttons
		ActionListener buttonActionHandler = new ButtonActionHandler();
		
		JButton loginButton = new JButton("Login");
		loginButton.setName("loginButton");
		loginButton.setBounds(100, 80, 100, 30);
		loginButton.setActionCommand("login");
		loginButton.addActionListener(buttonActionHandler);	
		add(loginButton);
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setName("cancelButton");
		cancelButton.setBounds(260, 80, 100, 30);
		cancelButton.setActionCommand("cancel");
		cancelButton.addActionListener(buttonActionHandler);	
		add(cancelButton);

		setVisible(true);
	}
	
	/**
	 * Tries to log the user in.
	 */
	private void logIn() {
		loadingDialog = new LoadingDialog(this);
		
		// Let's avoid freezing the ui
		new Thread(new SwingWorker<Boolean, Void>() {

			@Override
			protected Boolean doInBackground() throws Exception {
				return client.logIn(
						emailInput.getText(),
						new String(passwordInput.getPassword())
					);
			}
			
			@Override
			public void done() {
				Boolean successful = false;
				try {
					 successful = get();
				} catch (InterruptedException | ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finishLogin(successful);
			}
		}).start();
	}
	

	/**
	 * This method is called when the login has finished.
	 * @param successful Whether the login was successful or not
	 */
	private void finishLogin(boolean successful) {
		loadingDialog.dispose();
		
		if (successful) {
			dispose();
			client.continueStartup();
		} else {
			showLoginError();
		}
	}
	
	
	/**
	 * Displays a dialog that informs user about unsuccessful login.
	 */
	private void showLoginError() {
		JOptionPane.showMessageDialog(
				this,
				"The username or password was wrong or \nyou are not connected to the network.",
				"Login Unsuccessful",
				JOptionPane.ERROR_MESSAGE
			);
	}
	
	private void cancel() {
		dispose();
	}
	
	
	/**
	 * Handles the actions for the buttons.
	 */
	private class ButtonActionHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String actionCommand = e.getActionCommand();
			switch (actionCommand) {
			case "login":	logIn();	break;
			case "cancel":	cancel();	break;
			}
		}	
	}
	
	/**
	 * Dialog displayed while loggin user in
	 */
	private class LoadingDialog extends JDialog {
		
		public LoadingDialog(Frame owner) {
			super(owner, "Please Wait");
			JPanel panel = new JPanel();
			panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
			panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
			
			JLabel label = new JLabel("We are trying to log you in!");
			label.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
			panel.add(label);
			JProgressBar progressBar = new JProgressBar();
			progressBar.setIndeterminate(true);
			panel.add(progressBar);
			setContentPane(panel);
			pack();
			
			int xLocation = (int) (screenSize.getWidth() - getWidth()) / 2;
			int yLocation = (int) (screenSize.getHeight() - getHeight()) / 2;		
			setLocation(xLocation, yLocation);
			setVisible(true);
		}
	}
}

