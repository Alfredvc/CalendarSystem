package com.proj.network;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
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

    private ByteBufferInputStream inStream;
    private ObjectInputStream in;
    private NetworkEnvelopeListener listener;

    public ByteBufferHandler(NetworkEnvelopeListener listener){
        this.inStream = new ByteBufferInputStream(1024 * 64);
        this.listener = listener;
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

        extractEnvelopes();
    }

    private void extractEnvelopes(){
        //System.out.println("Extracting envelopes");
        NetworkEnvelope envelope = null;
        try {
            for(;;){
                envelope = (NetworkEnvelope) in.readObject();
                listener.onNewEnvelope(envelope);
            }
        } catch(EOFException e){
            //System.out.println("\tAll envelopes extracted");
        } catch (StreamCorruptedException e){
            //e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public interface NetworkEnvelopeListener {
        public void onNewEnvelope(NetworkEnvelope networkEnvelope);
    }

}
