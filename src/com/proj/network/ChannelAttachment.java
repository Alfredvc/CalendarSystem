package com.proj.network;

import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedDeque;

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
    public ConcurrentLinkedDeque<NetworkEnvelope> outQueue;
    public ConcurrentLinkedDeque<NetworkEnvelope> inQueue;
    public ByteBufferHandler byteBufferHandler;
    public ChannelOutputHandler channelOutputHandler;

    public ChannelAttachment(Status status) throws IOException {
        this.status = status;
        outQueue = new ConcurrentLinkedDeque<NetworkEnvelope>();
        inQueue = new ConcurrentLinkedDeque<NetworkEnvelope>();
        byteBufferHandler = new ByteBufferHandler(this);
        channelOutputHandler = new ChannelOutputHandler();
    }

    @Override
    public void onNewEnvelope(NetworkEnvelope networkEnvelope) {
        inQueue.push(networkEnvelope);
    }
}
