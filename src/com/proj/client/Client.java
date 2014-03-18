package com.proj.client;

import com.proj.gui.Login;
import com.proj.gui.MainWindow;
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
    private MainWindow mainWindow;

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
    	mainWindow = new MainWindow(model);
    }
    
    public static void main(String[] args) {
    	new Client(new Model());
    }
}
