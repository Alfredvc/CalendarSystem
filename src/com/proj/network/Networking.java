package com.proj.network;

import com.proj.model.Appointment;
import com.proj.model.Model;

import java.io.*;
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
    Model model;
    boolean run;
    final int selectionTimeout = 1000;
    public static final String loginFailed = "fail";
    public static final String loginSuccessful = "success";

    public Networking(Model model){
        this.model = model;
        this.run = true;
    }

    public static byte[] appointmentToByteArray(Appointment appointment)
            throws IOException{
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
        return result;
    }

    public static Appointment byteArrayToAppointment(byte[] array)
    throws IOException, ClassNotFoundException{

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
        return result;
    }

    public void serveForever(){

    }

    public void close(){

    }


}
