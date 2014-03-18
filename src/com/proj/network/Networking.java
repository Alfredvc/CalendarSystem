package com.proj.network;

import com.proj.model.Appointment;
import com.proj.model.Model;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * Created with IntelliJ IDEA.
 * User: Alfredo
 * Date: 07.03.14
 * Time: 13:25
 * To change this template use File | Settings | File Templates.
 */
public abstract class Networking {

    Selector selector;
    Model model;
    boolean run;
    final int selectionTimeout = 1000;
    protected ConcurrentLinkedDeque<Appointment> outgoingAppointments;
    public static final String loginFailed = "fail";
    public static final String loginSuccessful = "success";

    public Networking(Model model){
        this.model = model;
        this.run = true;
        this.outgoingAppointments = new ConcurrentLinkedDeque<Appointment>();
    }

    public static byte[] appointmentToByteArray(Appointment appointment, SocketChannel channel)
            throws IOException{

        System.out.println("Transforming appointment " + appointment + " to byte array");

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = null;
        byte[] result;
        try {
            out = new ObjectOutputStream(bos);
            out.writeObject(appointment);
            result = bos.toByteArray();

        } catch (IOException e){
            throw e;
        }

        finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException ex) {
            }
            try {
                bos.close();
            } catch (IOException ex) {
            }
        }
        System.out.println("Transformed into " +result.length +":"+ result);
        return result;
    }


    public void sendAppointment(Appointment appointment){

        System.out.println("Pushing appointment " + appointment + " to outgoing queue");

        outgoingAppointments.push(appointment);
    }

    protected void refreshQueues(){

        Appointment currentAppointment = outgoingAppointments.poll();
        if (currentAppointment == null) return;
        System.out.println("Refreshing queues for thread " + Thread.currentThread());
        for (SelectionKey key : selector.keys()){
            if (key.channel() instanceof ServerSocketChannel) continue;
            System.out.println("Updating interestOps for key ");
            key.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
        }
        while (currentAppointment != null){
            for (SelectionKey key : selector.keys()){
                if (key.attachment() != null && key.attachment() instanceof ChannelAttachment){
                    ((ChannelAttachment) key.attachment()).queue.push(currentAppointment);
                }
            }
            currentAppointment = outgoingAppointments.poll();
        }
    }

    protected void sendPendingAppointments(SocketChannel channel, SelectionKey key){

        if (key.attachment() != null && key.attachment() instanceof ChannelAttachment){
            System.out.println("Sending pending appointments to " + channel.socket().getInetAddress()
                    + ":" + channel.socket().getLocalPort());
            ConcurrentLinkedDeque<Appointment> queue = ((ChannelAttachment) key.attachment()).queue;
            Appointment currentAppointment = queue.poll();
            while (currentAppointment != null){
                if (!(key.attachment() instanceof ChannelAttachment)) throw new RuntimeException();
                try {
                    ((ChannelAttachment) key.attachment()).appointmentOutputHandler.sendAppointment(channel, currentAppointment);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                currentAppointment = queue.poll();
            }
            key.interestOps(SelectionKey.OP_READ);
            System.out.println("Finished sending pending appointments to " + channel.socket().getInetAddress()
                    + ":" + channel.socket().getLocalPort());
        }
    }

    public static Appointment byteArrayToAppointment(byte[] array)
    throws IOException, ClassNotFoundException{


        System.out.println("Transforming " +array.length +":"+ array + " into appointment");
        ByteArrayInputStream bis = new ByteArrayInputStream(array);
        ObjectInput in = null;
        Appointment result;
        try {
            in = new ObjectInputStream(bis);
            result = (Appointment) in.readObject();

        } catch (IOException e){
            throw e;
        } catch (ClassNotFoundException a){
            throw a;
        }

        finally {
            try {
                bis.close();
            } catch (IOException ex) {
                // ignore close exception
            }
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                // ignore close exception
            }
        }

        System.out.println("Transformed to " + result);
        return result;
    }

    protected void receivedAppointment(Appointment appointment){
        System.out.println("Received appointment " + appointment);
        model.addAppointment(appointment);
        //Do sutff with it
    }

    public void receivedAppointment(byte[] bytes){
        System.out.println("Received appointment " + bytes);
        try {
            model.addAppointment(byteArrayToAppointment(bytes));
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (ClassNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        //Do sutff with it
    }

    public void close(){
        run = false;
        selector.wakeup();
    }

    public Model getModel(){
        return this.model;
    }


}
