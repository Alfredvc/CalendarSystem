package com.proj.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectStreamConstants;
import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;

/**
 * Created with IntelliJ IDEA.
 * User: Alfredo
 * Date: 14.03.14
 * Time: 12:00
 * To change this template use File | Settings | File Templates.
 */
public class ByteBufferInputStream extends InputStream implements ObjectStreamConstants{

    private ByteBuffer internalBuffer;
    private boolean wasReading;
    int inBytes = 0;
    int outBytes = 0;

    public ByteBufferInputStream(int size){
        internalBuffer = ByteBuffer.allocate(size);
        internalBuffer.clear();

    }

    public void addBuffer(ByteBuffer buffer) throws BufferOverflowException{
        inBytes += buffer.limit();
        if (!(internalBuffer.limit() == internalBuffer.capacity() && internalBuffer.position() == 0)){
            internalBuffer.compact();
        }
        if (buffer.limit() > (internalBuffer.limit() - internalBuffer.position())){
            throw new BufferOverflowException();
        }
        internalBuffer.put(buffer);
        internalBuffer.flip();
        int currentBytes = internalBuffer.limit();
        int inOut = inBytes - outBytes;
        wasReading = false;
        return;
    }

    @Override
    public int read() throws IOException {
        if (!internalBuffer.hasRemaining()) return -1;
        outBytes++;
        if (!wasReading) wasReading = true;
        byte result = internalBuffer.get();
        return (result & 0xff);
    }

    @Override
    public int available() throws IOException {
        return internalBuffer.remaining();
    }

    //Removes stream header, must be called before each object read after the first
    public void removeStreamHeader(){
        internalBuffer.position(internalBuffer.position() + 4);
        byte[] header = new byte[8];
        for (int i = 0; i < 8; i++){
            header[i] = internalBuffer.get();
        }
        internalBuffer.position(internalBuffer.position() - 8);
    }

}
