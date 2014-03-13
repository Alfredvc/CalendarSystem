package com.proj.network;

import com.proj.model.Appointment;

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

    public ByteBufferHandler(Networking networking){
        internalBuffer = ByteBuffer.allocate(1024 * 32);
    }

    public void handleByteBuffer(ByteBuffer byteBuffer){

        if (internalBuffer.limit() < byteBuffer.limit()) throw new RuntimeException("Internal buffer is too small");
        System.out.println("Handing buffer " + byteBuffer);
        internalBuffer.put(byteBuffer);
        byteBuffer.clear();
        while (internalBuffer.limit() > 3){
            byte[] toLoadSize = new byte[4];
            internalBuffer.get(toLoadSize, 0, 4);
            int appointmentSize = new BigInteger(toLoadSize).intValue();
            System.out.println("Next appointment size " + appointmentSize);
            if (internalBuffer.limit() > appointmentSize){
                byte[] toLoad = new byte[appointmentSize];
                byteBuffer.get(toLoad, 0, appointmentSize);
                System.out.println("Handling appointment" + toLoad.length + ":" + toLoad);
                loadAppointment(toLoad);
            }
        }
        internalBuffer.compact();
    }

    private void loadAppointment(byte[] bytes){
        networking.receivedAppointment(bytes);
    }

}
