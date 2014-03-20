package com.proj.client;

import com.proj.gui.Login;
import com.proj.gui.MainCalendar;
import com.proj.model.Employee;
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
    private static Model model;
    public static ClientNetworking networking;
    private static String username;
    private static Employee currentEmployee;

    public static void main(String[] args){
        model = new Model();
        networking = new ClientNetworking(model);
        new Thread(networking).start();
        
        new Login();
    }

    public static boolean logIn(String username, String password) {
    	boolean success = networking.logIn(username, password);
    	if (success) {
    		Client.username = username;
    	}
    	return success;
    }
    
    public static void continueStartup() {
    	if (username == null) {
    		throw new IllegalStateException("Must be logged in to use this method.");
    	}
    	
    	Client.currentEmployee = model.getEmployee(username);
    	new MainCalendar(model, currentEmployee);
    }
    
    public static Employee getCurrentEmployee() {
    	return currentEmployee;
    }
}
