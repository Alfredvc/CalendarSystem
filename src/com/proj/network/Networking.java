package com.proj.network;

import com.proj.model.Appointment;
import com.proj.model.Model;

import java.io.*;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created with IntelliJ IDEA.
 * User: Alfredo
 * Date: 07.03.14
 * Time: 13:25
 * To change this template use File | Settings | File Templates.
 */
public abstract class Networking extends Storage{

    Selector selector;
    Model model;
    boolean run;
    final int selectionTimeout = 1000;
    protected ConcurrentLinkedQueue<NetworkEnvelope> outgoingEnvelopes;
    public static final String loginFailed = "fail";
    public static final String loginSuccessful = "success";

    public Networking(Model model){
        this.model = model;
        model.addModelChangeListener(this);
        this.run = true;
        this.outgoingEnvelopes = new ConcurrentLinkedQueue<>();
    }

    //Must add a flag, either DELETE, CREATE or CHANGE to the appointment being sent.
    private boolean sendAppointment(Appointment appointment, Appointment.Flag flag){

        NetworkEnvelope toSend = new NetworkEnvelope().sendingAppointment(new Appointment(appointment), flag);

        //System.out.println("Pushing envelope " + toSend + " to outQueue");

        outgoingEnvelopes.add(toSend);

        selector.wakeup();

        return true;
    }

    @Override
    public boolean save(Appointment appointment){
        return sendAppointment(appointment, Appointment.Flag.UPDATE);
    }

    @Override
    public boolean delete(Appointment appointment){
        return sendAppointment(appointment, Appointment.Flag.DELETE);
    }

    protected void refreshQueues(){

        NetworkEnvelope currentEnvelope = outgoingEnvelopes.poll();
        if (currentEnvelope == null) return;
        //System.out.println("Refreshing queues for thread " + Thread.currentThread());
        for (SelectionKey key : selector.keys()){
            if (key.channel() instanceof ServerSocketChannel) continue;
            //System.out.println("Updating interestOps for key ");
            key.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
        }
        while (currentEnvelope != null){
            for (SelectionKey key : selector.keys()){
                if (key.attachment() != null && key.attachment() instanceof ChannelAttachment){
                    ((ChannelAttachment) key.attachment()).outQueue.add(currentEnvelope);
                }
            }
            currentEnvelope = outgoingEnvelopes.poll();
        }
    }

    protected void sendPendingEnvelopes(SocketChannel channel, SelectionKey key){

        if (key.attachment() != null && key.attachment() instanceof ChannelAttachment){
            //System.out.println("Sending pending envelopes to " + channel.socket().getInetAddress()
                    //+ ":" + channel.socket().getLocalPort());
            ConcurrentLinkedQueue<NetworkEnvelope> queue = ((ChannelAttachment) key.attachment()).outQueue;
            NetworkEnvelope currentEnvelope = queue.poll();
            while (currentEnvelope != null){
                if (!(key.attachment() instanceof ChannelAttachment)) throw new RuntimeException();
                ((ChannelAttachment) key.attachment()).channelOutputHandler.send(channel, currentEnvelope);
                currentEnvelope = queue.poll();
            }
            key.interestOps(SelectionKey.OP_READ);
            //System.out.println("Finished sending pending envelopes to " + channel.socket().getInetAddress()
                    //+ ":" + channel.socket().getLocalPort());
        }
    }

    protected void receivedAppointment(Appointment appointment, Appointment.Flag flag){
        System.out.println("Received appointment " + appointment + " " + flag );
        switch (flag){
            case UPDATE:
                model.updateAppointment(appointment);
                break;
            case DELETE:
                model.deleteAppointment(appointment.getId());
                break;
        }
    }

    protected void handleReceivedEnvelopes(){
        for (SelectionKey key : selector.keys()) handleReceivedEnvelope(key);
    }

    protected abstract void handleReceivedEnvelope(SelectionKey key);

    public void closeConnection(){
        outgoingEnvelopes.add(new NetworkEnvelope().disconnectRequest());
    }

    public void close(){
        run = false;
        selector.wakeup();
    }

    public Model getModel(){
        return this.model;
    }

}
