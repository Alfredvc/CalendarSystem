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
public class ChannelOutputHandler {

    private ByteArrayOutputStream byteArrayOutputStream;
    private ObjectOutputStream objectOutputStream;

    public ChannelOutputHandler() throws IOException {
        byteArrayOutputStream = new ByteArrayOutputStream();
        objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
    }

    public void send(SocketChannel channel, NetworkEnvelope networkEnvelope) {
        try{
            System.out.println("Sending envelope " + networkEnvelope + "...");
            objectOutputStream.writeObject(networkEnvelope);
            ByteBuffer buff = ByteBuffer.wrap(byteArrayOutputStream.toByteArray());
            System.out.println("Sending " + buff.limit() + ":"+ buff);
            while (buff.hasRemaining()){
                channel.write(buff);
            }
            byteArrayOutputStream.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
