package com.proj.network;

import com.proj.model.Appointment;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * Created with IntelliJ IDEA.
 * User: Alfredo
 * Date: 13.03.14
 * Time: 13:34
 * To change this template use File | Settings | File Templates.
 */
public class ChannelAttachment {

    public ConcurrentLinkedDeque<AppointmentEnvelope> queue;
    public ByteBufferHandler byteBufferHandler;
    public AppointmentOutputHandler appointmentOutputHandler;

    public ChannelAttachment(Networking networking) throws IOException {
        queue = new ConcurrentLinkedDeque<AppointmentEnvelope>();
        byteBufferHandler = new ByteBufferHandler(networking);
        appointmentOutputHandler = new AppointmentOutputHandler();
    }

}
