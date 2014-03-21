package com.proj.client;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

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
    private static List<String> arguments;

    public static void main(String[] args){
    	arguments = Arrays.asList(args);
    	Properties props = new Properties();
        model = new Model();
        
        String host = getArgument(new String[] {"-a", "--address"});
        if (host != null) {
        	props.put("server", host);
        }
        
        String port = getArgument(new String[] {"-p", "--port"});
        if (port != null) {
        	props.put("port", port);
        }
        
        networking = new ClientNetworking(model, props);
        new Thread(networking).start();
        
        new Login();
    }
    
    private static String getArgument(String[] names) {
    	for (String n : names) {
    		int index = arguments.indexOf(n);
    		if (index > 0 && index < arguments.size() - 1) {
    			return arguments.get(index + 1);
    		}
    	}
    	return null;
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
