package com.proj.network;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Deque;
import java.util.Iterator;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Alfredo
 * Date: 07.03.14
 * Time: 13:53
 * To change this template use File | Settings | File Templates.
 */
public class ServerNetworking extends Networking {

    String ipAddr = "127.0.0.1";
    int portNr = 8989;
    Deque<Appointment> appointmentQueue;
    final int selectionTimeout = 1000;
    final Object awaitingLogin = new Object();
    final Object awaitingAllAppointments = new Object();


    public ServerNetworking(Model model){
        super(model);

        try {
            System.out.println("Creating server socket");

            selector = Selector.open();
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            InetSocketAddress address = new InetSocketAddress(InetAddress.getByName(ipAddr), portNr);
            serverSocketChannel.socket().bind(address);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            System.out.println("Server socket created");

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void serveForever(){

        System.out.println("Serving on " + serverSocketChannel.socket().getInetAddress()
                + ":" + serverSocketChannel.socket().getLocalPort());

        while(run){

            try {
                selector.select(selectionTimeout);
                Set selectedKeys = selector.selectedKeys();
                Iterator iterator = selectedKeys.iterator();

                while(iterator.hasNext()){

                    SelectionKey key = (SelectionKey) iterator.next();

                    iterator.remove();

                    if(key.isAcceptable()){

                        SocketChannel client = serverSocketChannel.accept();

                        System.out.println("New client at " + client.socket().getInetAddress()
                                + ":" + client.socket().getLocalPort());

                        client.configureBlocking(false);
                        client.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE, awaitingLogin);

                    }
                    if(key.isReadable()){

                        SocketChannel client = (SocketChannel) key.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(1024);

                        System.out.println("Reading from client at " + client.socket().getInetAddress()
                                + ":" + client.socket().getLocalPort());

                        client.read(buffer);

                        System.out.println("Finished reading from client at s" + client.socket().getInetAddress()
                                + ":" + client.socket().getLocalPort());

                        if (key.attachment().equals(awaitingLogin)){
                            boolean successful = requestLogin(buffer.flip());

                            if (successful) key.attach(awaitingAllAppointments);

                            //Sends response to client

                            byte response = successful ? Networking.loginSuccessful : Networking.loginFailed;

                            System.out.println("Sending response " + response + " to client at " + client.socket().getInetAddress()
                                    + ":" + client.socket().getLocalPort());

                            client.write( ByteBuffer.wrap(new byte[] {response}));
                        }
                        else{
                            newChangedAppointment(client, buffer.flip());
                        }

                    }
                    if (key.isWritable()){
                        SocketChannel client = (SocketChannel) key.channel();

                        System.out.println("Writing to client at " + client.socket().getInetAddress()
                                + ":" + client.socket().getLocalPort());

                        if (key.attachment().equals(awaitingAllAppointments)){
                            boolean successful = sendAllApointments(client);
                            if (successful) key.attach(null);
                        }
                        else{
                            sendChangedAppointments(client);
                        }

                        System.out.println("Finished sending to client at " + client.socket().getInetAddress()
                                + ":" + client.socket().getLocalPort());

                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private boolean requestLogin(Buffer buffer){
        return true;
    }

    private boolean sendAllApointments(SocketChannel client){
        return true;
    }
    private boolean newChangedAppointment(SocketChannel client, Buffer buffer){
        return true;
    }

    private boolean sendChangedAppointments(SocketChannel client){
        return true;
    }

}
