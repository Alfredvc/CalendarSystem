package com.proj.network;

import com.proj.model.Appointment;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * Created with IntelliJ IDEA.
 * User: Alfredo
 * Date: 14.03.14
 * Time: 17:28
 * To change this template use File | Settings | File Templates.
 */
public class AppointmentOutputHandler {

    private ByteArrayOutputStream byteArrayOutputStream;
    private ObjectOutputStream objectOutputStream;
    private SocketChannel channel;

    public AppointmentOutputHandler() throws IOException {
        byteArrayOutputStream = new ByteArrayOutputStream();
        objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
    }

    public void sendAppointment(SocketChannel channel, AppointmentEnvelope appointmentEnvelope) throws IOException {
        System.out.println("Sending appointment " + appointmentEnvelope + "...");
        objectOutputStream.writeObject(appointmentEnvelope);
        ByteBuffer buff = ByteBuffer.wrap(byteArrayOutputStream.toByteArray());
        System.out.println("Sending " + buff.limit() + ":"+ buff);
        while (buff.hasRemaining()){
            channel.write(buff);
        }
        byteArrayOutputStream.reset();
    }

}
