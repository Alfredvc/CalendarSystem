package com.proj.client;

import com.proj.gui.Login;
import com.proj.gui.MainCalendar;
import com.proj.model.Employee;
import com.proj.model.Model;
import com.proj.network.ClientNetworking;
import com.proj.test.RandomGenerator;

/**
 * Created with IntelliJ IDEA.
 * User: Alfredo
 * Date: 07.03.14
 * Time: 12:48
 * To change this template use File | Settings | File Templates.
 */
public class Client {

    Model model;
    public ClientNetworking networking;
    private Login login;
    private MainCalendar mainCalendar;

    public Client(Model model){
        this.model = model;
        networking = new ClientNetworking(model);
        new Thread(networking).start();
        
        login = new Login(this);
    }

    public boolean logIn(String username, String password) {
    	return networking.logIn(username, password);
    }
    
    public void continueStartup() {
    	mainCalendar = new MainCalendar(model, new Employee("Mock", "Mock", 0));
    }
    
    public static void main(String[] args) {
    	new Client(new Model());
    }
}
