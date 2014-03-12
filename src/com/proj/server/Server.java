package com.proj.server;

import com.proj.model.Model;
import com.proj.network.ServerNetworking;

/**
 * Created with IntelliJ IDEA.
 * User: Alfredo
 * Date: 07.03.14
 * Time: 12:48
 * To change this template use File | Settings | File Templates.
 */
public class Server {

    Model model;
    public ServerNetworking networking;

    public Server(Model model){
        this.model = model;
        networking = new ServerNetworking(model);
        new Thread(networking).start();
    }
}