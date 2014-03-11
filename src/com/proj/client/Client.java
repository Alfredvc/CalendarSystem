package com.proj.client;

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

    Model model;
    ClientNetworking networking;

    public Client(Model model){
        this.model = model;
        networking = new ClientNetworking(model);
        new Thread(networking).start();
    }

    public static void main(String args[]){
        Client client = new Client(new Model());
        client.logIn("username", "password");
    }

    public boolean logIn(String username, String password){
        return networking.logIn(username, password);
    }
}
