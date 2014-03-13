package com.proj.network;

import com.proj.model.Appointment;

import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * Created with IntelliJ IDEA.
 * User: Alfredo
 * Date: 13.03.14
 * Time: 13:34
 * To change this template use File | Settings | File Templates.
 */
public class ChannelAttachment {

    public ConcurrentLinkedDeque<Appointment> queue;
    public ByteBufferHandler byteBufferHandler;

    public ChannelAttachment(Networking networking){
        queue = new ConcurrentLinkedDeque<Appointment>();
        byteBufferHandler = new ByteBufferHandler(networking);
    }

}
