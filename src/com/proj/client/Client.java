package com.proj.client;

import com.proj.gui.Login;
import com.proj.gui.MainCalendar;
import com.proj.model.Model;
import com.proj.network.ClientNetworking;

/**
 * Created with IntelliJ IDEA.
 * User: Alfredo
 * Date: 07.03.14
 * Time: 12:48
 * To change this template use File | Settings | File Templates.
 */
public class Client {
    private Model model;
    public ClientNetworking networking;
    private String username;

    public Client(Model model){
        this.model = model;
        networking = new ClientNetworking(model);
        new Thread(networking).start();
        
        new Login(this);
    }

    public boolean logIn(String username, String password) {
    	boolean success = networking.logIn(username, password);
    	if (success) {
    		this.username = username;
    	}
    	return success;
    }
    
    public void continueStartup() {
    	if (username == null) {
    		throw new IllegalStateException("Must be logged in to use this method.");
    	}
    	
    	new MainCalendar(model, model.getEmployee(username));
    }
    
    public static void main(String[] args) {
    	new Client(new Model());
    }
}
