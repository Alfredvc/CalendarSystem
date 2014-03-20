package com.proj.network;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Alfredo
 * Date: 13.03.14
 * Time: 13:03
 * To change this template use File | Settings | File Templates.
 */
public class ByteBufferHandler {

    private Networking networking;
    private ByteBufferInputStream inStream;
    private ObjectInputStream in;
    private NotifyOnLoadListener listener;
    private int leftToNotify;

    public void setNotifyOnLoadListener(NotifyOnLoadListener listener, int left){
        if (listener == null || left < 1) return;
        this.listener = listener;
        this.leftToNotify = left;
    }

    public ByteBufferHandler(Networking networking){
        this.networking = networking;
        this.inStream = new ByteBufferInputStream(1024 * 64);
        this.listener = null;
    }

    public void handleByteBuffer(ByteBuffer byteBuffer) {
        byteBuffer.flip();
        if (byteBuffer.limit() == 0) return;
        inStream.addBuffer(byteBuffer);
        if (in == null){
            try {
                in = new ObjectInputStream(inStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        extractAppointments();
    }

    private void extractAppointments(){
        System.out.println("Extracting appointments");
        AppointmentEnvelope newAppointment = null;
        try {
            for(;;){
                newAppointment = (AppointmentEnvelope) in.readObject();
                if (newAppointment == null) throw new NullPointerException();
                networking.receivedAppointment(newAppointment);
                if (listener != null){
                    if (leftToNotify > 1){
                        leftToNotify--;
                    } else{
                        listener.onLoad();
                        listener = null;
                    }
                }
            }
        } catch(EOFException e){
            System.out.println("All appointments extracted");
        }
        catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public interface NotifyOnLoadListener {
        public void onLoad();
    }

}
