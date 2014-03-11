package com.proj.network;

import com.proj.model.Appointment;
import com.proj.model.Employee;
import com.proj.model.Model;
import com.proj.model.Participant;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Deque;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * Created with IntelliJ IDEA.
 * User: Alfredo
 * Date: 07.03.14
 * Time: 13:53
 * To change this template use File | Settings | File Templates.
 */
public class ServerNetworking extends Networking {

    String ipAddr = "127.0.0.1";
    int portNr = 8989;
    ServerSocketChannel serverSocketChannel;
    Deque<Appointment> appointmentQueue;

    final Object awaitingLogin = new Object();
    final Object awaitingAllAppointments = new Object();


    public ServerNetworking(Model model){
        super(model);

        try {
            System.out.println("Creating server socket");

            selector = Selector.open();
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            InetSocketAddress address = new InetSocketAddress(InetAddress.getByName(ipAddr), portNr);
            serverSocketChannel.socket().bind(address);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            System.out.println("Server socket created");

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void serveForever(){

        System.out.println("Serving on " + serverSocketChannel.socket().getInetAddress()
                + ":" + serverSocketChannel.socket().getLocalPort());

        while(run){

            try {
                selector.select();
                Set selectedKeys = selector.selectedKeys();
                Iterator iterator = selectedKeys.iterator();

                while(iterator.hasNext()){

                    SelectionKey key = (SelectionKey) iterator.next();

                    iterator.remove();

                    if(key.isAcceptable()){

                        SocketChannel client = serverSocketChannel.accept();

                        System.out.println("New client at " + client.socket().getInetAddress()
                                + ":" + client.socket().getLocalPort());

                        client.configureBlocking(false);
                        client.register(selector, SelectionKey.OP_READ, awaitingLogin);

                    }
                    if(key.isReadable()){

                        SocketChannel client = (SocketChannel) key.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(1024);

                        System.out.println("Reading from client at " + client.socket().getInetAddress()
                                + ":" + client.socket().getLocalPort());

                        client.read(buffer);

                        System.out.println("Finished reading from client at " + client.socket().getInetAddress()
                                + ":" + client.socket().getLocalPort());

                        if (key.attachment().equals(awaitingLogin)){
                            boolean successful = requestLogin(buffer);

                            if (successful){
                                key.attach(awaitingAllAppointments);
                                key.interestOps(SelectionKey.OP_WRITE);
                            }

                            //Sends response to client

                            String response = successful ? Networking.loginSuccessful : Networking.loginFailed;

                            System.out.println("Sending response " + response + " to client at " + client.socket().getInetAddress()
                                    + ":" + client.socket().getLocalPort());

                            client.write( ByteBuffer.wrap(response.getBytes()));
                        }
                        else{
                            newChangedAppointment(client, buffer.flip());
                        }

                    }
                    if (key.isWritable()){
                        SocketChannel client = (SocketChannel) key.channel();

                        System.out.println("Writing to client at " + client.socket().getInetAddress()
                                + ":" + client.socket().getLocalPort());

                        if (key.attachment() != null && key.attachment().equals(awaitingAllAppointments)){
                            key.attach(getAllAppointmentsAsQueue());
                        }

                        if (key.attachment() != null && key.attachment() instanceof ConcurrentLinkedDeque){
                            Appointment currentAppointment = (Appointment)((ConcurrentLinkedDeque) key.attachment()).poll();
                            while (currentAppointment != null){
                                sendAppointment(client, currentAppointment);
                                currentAppointment = (Appointment)((ConcurrentLinkedDeque) key.attachment()).poll();
                            }
                        }

                        System.out.println("Finished sending to client at " + client.socket().getInetAddress()
                                + ":" + client.socket().getLocalPort());

                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
                try {
                    selector.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

        }
    }

    private ConcurrentLinkedDeque<Appointment> getAllAppointmentsAsQueue(){
        //test data
        ConcurrentLinkedDeque<Appointment> currentList = new ConcurrentLinkedDeque<Appointment>();
        currentList.push(new Appointment(null, null, null, "test stuff"));
        return currentList;
    }

    private boolean requestLogin(ByteBuffer buffer){
        System.out.println("Starting log in");
        buffer.flip();
        byte[] array = new byte[buffer.limit()];
        buffer.get(array);
        System.out.println("Received: " + new String(array));

        return true;
    }

    private boolean sendAppointment(SocketChannel client, Appointment appointment){
        System.out.println("Sending appointment " + appointment + "...");
        boolean sent = false;
        while (!sent){
            try {
                client.write( ByteBuffer.wrap(Networking.appointmentToByteArray(appointment)));
                sent = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return true;
    }
    private boolean newChangedAppointment(SocketChannel client, Buffer buffer){
        return true;
    }

    private boolean sendChangedAppointments(SocketChannel client){
        System.out.println("Sending changed appointments");
        return true;
    }

}
