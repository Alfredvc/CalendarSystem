package com.proj.network;

import com.proj.model.*;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Alfredo
 * Date: 07.03.14
 * Time: 13:53
 * To change this template use File | Settings | File Templates.
 */
public class ClientNetworking extends Networking implements Runnable {

    String ipAddr;
    int portNr;
    String username;
    String password;
    Thread loginThread;
    boolean loggedIn;
    ChannelAttachment attachment;
    SocketChannel clientChannel;

    final Object readyToLogIn = new Object();
    final Object awaitingLoginResponse = new Object();
    
    public ClientNetworking(Model model) {
    	this(model, new Properties());
    }

    public ClientNetworking(Model model, Properties props){
        super(model);
        this.ipAddr = props.containsKey("server") ?
        		props.getProperty("server") : "127.0.0.1";
        
        try {
	        this.portNr = props.containsKey("port") ?
	        		Integer.parseInt(props.getProperty("port")) : 8989;
        } catch (NumberFormatException e) {
        	System.out.println("Could not recognize port number! Running on 8989!");
        	this.portNr = 8989;
        }

        loggedIn = false;

        try {

            clientChannel = SocketChannel.open();

            clientChannel.configureBlocking(false);

            clientChannel.connect(new InetSocketAddress(ipAddr, portNr));

            selector = Selector.open();

            clientChannel.register(selector, SelectionKey.OP_CONNECT);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void run(){
        try{
            while (run){
                refreshQueues();
                selector.select();
                Set selectedKeys = selector.selectedKeys();
                Iterator iterator = selectedKeys.iterator();

                while(iterator.hasNext()){

                    SelectionKey key = (SelectionKey) iterator.next();

                    iterator.remove();

                    if(key.isConnectable()){
                        if (clientChannel.isConnectionPending()){
                            clientChannel.finishConnect();
                            key.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
                            attachment = new ChannelAttachment(ChannelAttachment.Status.ReadyToLogIn);
                            key.attach(attachment);
                        }
                    }

                    if (key.isWritable()){
                        sendPendingEnvelopes(clientChannel, key);

                    }

                    if (key.isReadable()){
                        //System.out.println("Reading envelope...");
                        ByteBuffer inBuffer = ByteBuffer.allocate(4098);
                        int readBytes = clientChannel.read(inBuffer);
                        if (readBytes == -1) throw new IOException("Server disconnected");
                        ((ChannelAttachment) key.attachment()).byteBufferHandler.handleByteBuffer(inBuffer);
                    }
                    handleReceivedEnvelopes();
                }
            }
        }
        catch (IOException e){
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
                            disconnect();
                        } else throw new RuntimeException("Impossible combination");
                        break;
                    case AwaitingLoginResponse:
                        if (currentEnvelope.getType() == NetworkEnvelope.Type.LoginResponse){
                            if (currentEnvelope.getLoginResponse().equals(Networking.loginSuccessful)){
                                loadModel(currentEnvelope.getEmployees(), currentEnvelope.getGroups(),
                                        currentEnvelope.getMeetingRooms());
                                loggedIn = true;
                                ((ChannelAttachment) key.attachment()).status = ChannelAttachment.Status.AwaitingAllAppointments;
                            } else {
                                ((ChannelAttachment) key.attachment()).status = ChannelAttachment.Status.ReadyToLogIn;
                                loginThread.interrupt();
                            }
                        } else throw new RuntimeException("Not expecting login response");
                        break;
                    case AwaitingAllAppointments:
                        if (currentEnvelope.getType() == NetworkEnvelope.Type.DoneSendingAppointments){
                            System.out.println("\tEnd of appointment stream detected");
                            ((ChannelAttachment) key.attachment()).status = ChannelAttachment.Status.Established;
                            loginThread.interrupt();
                        } else if (currentEnvelope.getType() == NetworkEnvelope.Type.SendingAppointment){
                            receivedAppointment(currentEnvelope.getAppointment(), currentEnvelope.getFlag());
                        } else if (currentEnvelope.getType() == NetworkEnvelope.Type.DisconnectionRequest){
                            disconnect();
                        } else throw new RuntimeException("Impossible combination");
                        break;
                    case ReadyToLogIn:
                        throw new RuntimeException("Not expecting envelopes");
                }
                currentEnvelope = ((ChannelAttachment) key.attachment()).inQueue.poll();
            }
        }
    }

    private void loadModel(Employee[] employees, Group[] groups, MeetingRoom[] meetingRooms){
        for (Employee employee : employees) model.addEmployee(employee);
        for (Group group : groups) model.addGroup(group);
        for (MeetingRoom meetingRoom : meetingRooms) model.addMeetingRoom(meetingRoom);
    }

    private void disconnect(){
        try {
            clientChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean logIn(String username, String password){

        System.out.println("Attempting login from thread " + Thread.currentThread());

        this.username = username;
        this.password = password;
        this.loginThread = Thread.currentThread();
        try{
            //System.out.println("Sleeping thread " + loginThread + " from thread " + Thread.currentThread());

            if (attachment.status == ChannelAttachment.Status.ReadyToLogIn
                    && username != null && password != null){
                System.out.println("Starting login with as: " + username + ":" + password);
                attachment.channelOutputHandler.send(clientChannel, new NetworkEnvelope().loginRequest(username, password));
                attachment.status = ChannelAttachment.Status.AwaitingLoginResponse;
            }
            loginThread.sleep(8000);
            System.out.println("Login attempt timed out");
        } catch (InterruptedException e){
            System.out.println(loggedIn ? "\tAll appointments loaded" : "Login failed");
        } finally {
            this.username = null;
            this.password = null;
            return loggedIn;
        }
    }

}
