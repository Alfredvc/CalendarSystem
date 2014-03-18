package com.proj.network;

import com.proj.model.Appointment;

import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.math.BigInteger;
import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;

/**
 * Created with IntelliJ IDEA.
 * User: Alfredo
 * Date: 13.03.14
 * Time: 13:03
 * To change this template use File | Settings | File Templates.
 */
public class ByteBufferHandler {

    private ByteBuffer internalBuffer;
    private Networking networking;
    private ByteBufferInputStream inStream;
    private ObjectInputStream in;

    public ByteBufferHandler(Networking networking){
        this.internalBuffer = ByteBuffer.allocate(1024 * 32);
        this.networking = networking;
        this.inStream = new ByteBufferInputStream(1024 * 64);
    }

    public void handleByteBuffer(ByteBuffer byteBuffer) {
        byteBuffer.flip();
        if (byteBuffer.limit() == 0) return;
        //System.out.println("Pos: " + byteBuffer.position() + " Limit: " + byteBuffer.limit() + " Capacity: " + byteBuffer.capacity());
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

}
