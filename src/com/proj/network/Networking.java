package com.proj.network;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

/**
 * Created with IntelliJ IDEA.
 * User: Alfredo
 * Date: 07.03.14
 * Time: 13:25
 * To change this template use File | Settings | File Templates.
 */
public abstract class Networking {

    Selector selector;
    ServerSocketChannel serverSocketChannel;
    Model model;
    boolean run;
    public static final byte loginFailed = 0;
    public static final byte loginSuccessful = 1;

    public Networking(Model model){
        this.model = model;
        this.run = true;
    }

    public void serveForever(){

    }

    public void close(){

    }


}
