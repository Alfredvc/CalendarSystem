package com.proj.network;

import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created with IntelliJ IDEA.
 * User: Alfredo
 * Date: 13.03.14
 * Time: 13:34
 * To change this template use File | Settings | File Templates.
 */
public class ChannelAttachment implements ByteBufferHandler.NetworkEnvelopeListener {

    public static enum Status{
        ReadyToLogIn, AwaitingLoginResponse, AwaitingLogin, Established, AwaitingAllAppointments
    }

    public Status status;
    public ConcurrentLinkedQueue<NetworkEnvelope> outQueue;
    public ConcurrentLinkedQueue<NetworkEnvelope> inQueue;
    public ByteBufferHandler byteBufferHandler;
    public ChannelOutputHandler channelOutputHandler;

    public ChannelAttachment(Status status) throws IOException {
        this.status = status;
        outQueue = new ConcurrentLinkedQueue<NetworkEnvelope>();
        inQueue = new ConcurrentLinkedQueue<NetworkEnvelope>();
        byteBufferHandler = new ByteBufferHandler(this);
        channelOutputHandler = new ChannelOutputHandler();
    }

    @Override
    public void onNewEnvelope(NetworkEnvelope networkEnvelope) {
        inQueue.add(networkEnvelope);
    }
}
