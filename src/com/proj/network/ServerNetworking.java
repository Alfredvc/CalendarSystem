package com.proj.network;

import com.proj.model.Appointment;
import com.proj.server.Server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Alfredo
 * Date: 07.03.14
 * Time: 13:53
 * To change this template use File | Settings | File Templates.
 */
public class ServerNetworking extends Networking implements Runnable{

    String ipAddr = "127.0.0.1";
    int portNr = 8989;
    ServerSocketChannel serverSocketChannel;
    Server server;

    final Object awaitingLogin = new Object();
    final Object awaitingAllAppointments = new Object();


    public ServerNetworking(Server server){
        super(server.getModel());
        this.server = server;

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

    public void run(){

        System.out.println("Serving on " + serverSocketChannel.socket().getInetAddress()
                + ":" + serverSocketChannel.socket().getLocalPort());

        while(run){

            try {
                if (!selector.isOpen()){
                    System.out.println("Selector unexpectedly closed, shutting down...");
                    run = false;
                    continue;
                }
                refreshQueues();
                selector.select();
                Set selectedKeys = selector.selectedKeys();
                Iterator iterator = selectedKeys.iterator();

                while(iterator.hasNext()){

                    SelectionKey key = (SelectionKey) iterator.next();

                    iterator.remove();

                    if(key.isAcceptable()){

                        SocketChannel client = serverSocketChannel.accept();

                        //System.out.println("New client at " + client.socket().getInetAddress()
                                //+ ":" + client.socket().getLocalPort());

                        client.configureBlocking(false);
                        client.register(selector, SelectionKey.OP_READ, new ChannelAttachment(ChannelAttachment.Status.AwaitingLogin));

                    }
                    if(key.isReadable()){
                        SocketChannel client = (SocketChannel) key.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(4096);

                        //System.out.println("Reading from client at " + client.socket().getInetAddress()
                                //+ ":" + client.socket().getLocalPort());
                        int readBytes = -2;

                        try{
                            readBytes = client.read(buffer);
                            if (readBytes < 0) {
                            	throw new IOException("forcibly closed");
                            }
                        } catch (IOException e){
                            if (e.getMessage().contains("forcibly closed")){
                                disconnectClient(client);
                                continue;
                            } else{
                                e.printStackTrace();
                            }
                        }

                        System.out.println("Read " + readBytes + " bytes");

                        ((ChannelAttachment)key.attachment()).byteBufferHandler.handleByteBuffer(buffer);

                    }
                    if (key.isWritable()){
                        SocketChannel client = (SocketChannel) key.channel();

                        sendPendingEnvelopes(client, key);
                    }
                    handleReceivedEnvelopes();
                }
            } catch (IOException e) {
                e.printStackTrace();
                try {
                    selector.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

        }

        try {
            selector.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void disconnectClient(SocketChannel client){
        System.out.println("Client disconnected at " + client.socket().getInetAddress()
                + ":" + client.socket().getLocalPort());
        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void handleReceivedEnvelope(SelectionKey key) {
        if (key.attachment() != null && key.attachment() instanceof ChannelAttachment){
            NetworkEnvelope currentEnvelope = ((ChannelAttachment) key.attachment()).inQueue.poll();
            if (currentEnvelope == null) return;
            while (currentEnvelope != null){
                switch (((ChannelAttachment)key.attachment()).status){
                    case Established:
                        if (currentEnvelope.getType() == NetworkEnvelope.Type.SendingAppointment){
                            receivedAppointment(currentEnvelope.getAppointment(), currentEnvelope.getFlag());
                        } else if (currentEnvelope.getType() == NetworkEnvelope.Type.DisconnectionRequest){
                            disconnectClient((SocketChannel)key.channel());
                        } else throw new RuntimeException("Impossible combination");
                        break;
                    case AwaitingLogin:
                        if (currentEnvelope.getType() == NetworkEnvelope.Type.LoginRequest){
                            if (requestLogin(currentEnvelope.getUsername(), currentEnvelope.getPassword())){
                                sendSuccessResponse(key);
                                ((ChannelAttachment) key.attachment()).status = ChannelAttachment.Status.Established;
                            } else sendFailureResponse(key);
                        } else throw new RuntimeException("Not expecting login request");
                        break;
                }
                currentEnvelope = ((ChannelAttachment) key.attachment()).inQueue.poll();
            }
        }
    }

    private void sendSuccessResponse(SelectionKey key){
        ((ChannelAttachment) key.attachment()).channelOutputHandler.send((SocketChannel)key.channel(),
                new NetworkEnvelope().loginResponse(Networking.loginSuccessful, model.getEmployees(), model.getGroups(),
                        model.getMeetingRooms()));
        for (Appointment appointment : model.getAppointments()){
            ((ChannelAttachment) key.attachment()).channelOutputHandler.send((SocketChannel)key.channel(),
                    new NetworkEnvelope().sendingAppointment(new Appointment(appointment), Appointment.Flag.UPDATE));
        }
        ((ChannelAttachment) key.attachment()).channelOutputHandler.send((SocketChannel)key.channel(),
                new NetworkEnvelope().doneSendingAppointments());
    }

    private void sendFailureResponse(SelectionKey key){
        ((ChannelAttachment) key.attachment()).channelOutputHandler.send((SocketChannel)key.channel(),
                new NetworkEnvelope().loginResponse(Networking.loginFailed, null, null, null));
    }


    private boolean requestLogin(String username, String password){
        System.out.println("Registering " + username);
        return server.requestLogin(username, password);
    }

}
