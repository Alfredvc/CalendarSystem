package com.proj.network;

import com.proj.model.Model;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
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
public class ClientNetworking extends Networking implements Runnable, ByteBufferHandler.NotifyOnLoadListener {

    String ipAddr = "127.0.0.1";
    int portNr = 8989;
    String username;
    String password;
    Thread loginThread;
    boolean loggedIn;
    int remainingAppointmentsToContinue = 0;
    SocketChannel clientChannel;

    final Object readyToLogIn = new Object();
    final Object awaitingLoginResponse = new Object();
    final Object readyToRequestAppointments = new Object();
    final Object awaitingAppointments = new Object();

    public ClientNetworking(Model model){
        super(model);

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
                            key.attach(readyToLogIn);
                        }
                    }

                    if (key.isWritable()){
                        if (key.attachment() != null && key.attachment().equals(readyToLogIn) && username != null && password != null){
                            System.out.println("Starting login with as: " + username + ":" + password);

                            try {
                                System.out.println("Sending login request...");
                                ByteBuffer outBuffer = ByteBuffer.wrap((username + ":" + password).getBytes());
                                while (outBuffer.hasRemaining()){
                                    clientChannel.write(outBuffer);
                                    System.out.println("Request sent");
                                }
                                key.attach(awaitingLoginResponse);
                            } catch (IOException e){
                                e.printStackTrace();
                            }

                        }

                        sendPendingAppointments(clientChannel, key);

                    }

                    if (key.isReadable()){

                        if (key.attachment()!= null && key.attachment().equals(awaitingLoginResponse)){
                            System.out.println("Reading response");
                            ByteBuffer inBuffer = ByteBuffer.allocate(128);
                            int read = clientChannel.read(inBuffer);
                            if (read == -1) throw new IOException("Server disconnected");
                            inBuffer.flip();
                            byte[] array = new byte[inBuffer.limit()];
                            inBuffer.get(array);
                            String received = new String(array);
                            System.out.println("Received: " + received);
                            if (received.contains(loginSuccessful)){
                                String[] recv = received.split(":");
                                loggedIn = true;
                                System.out.println("Login successful");
                                key.attach(new ChannelAttachment(this));
                                ((ChannelAttachment)key.attachment()).byteBufferHandler.setNotifyOnLoadListener(this, Integer.parseInt(recv[1]));
                                if (recv.length >2 ){
                                    ((ChannelAttachment)key.attachment()).byteBufferHandler.handleByteBuffer(ByteBuffer.wrap(recv[2].getBytes()));
                                }
                            } else{
                                System.out.println("Interrupting thread " + loginThread + " from thread "+ Thread.currentThread());
                                loginThread.interrupt();
                                System.out.println("Login failed, trying again");
                            }
                        }

                        if (key.attachment() != null && key.attachment() instanceof ChannelAttachment){
                            System.out.println("Reading appointment...");
                            ByteBuffer inBuffer = ByteBuffer.allocate(4098);
                            int readBytes = clientChannel.read(inBuffer);
                            if (readBytes == -1) throw new IOException("Server disconnected");
                            ((ChannelAttachment) key.attachment()).byteBufferHandler.handleByteBuffer(inBuffer);
                        }

                    }
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onLoad(){
        System.out.println("\tClientNetwork notified, all appointments loaded");
        loginThread.interrupt();
    }

    public boolean logIn(String username, String password){

        System.out.println("Attempting login from thread " + Thread.currentThread());

        this.username = username;
        this.password = password;
        this.loginThread = Thread.currentThread();
        try{
            System.out.println("Sleeping thread " + loginThread + " from thread "+ Thread.currentThread());
            loginThread.sleep(8000);
            System.out.println("Login attempt timed out");
        } catch (InterruptedException e){
            System.out.println("\tAll appointments loaded");
        } finally {
            this.username = null;
            this.password = null;
            return loggedIn;
        }
    }

}
