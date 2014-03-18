package com.proj.test;

import com.proj.client.Client;
import com.proj.model.Appointment;
import com.proj.model.Model;
import com.proj.server.Server;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Alfredo
 * Date: 13.03.14
 * Time: 11:02
 * To change this template use File | Settings | File Templates.
 */
public class NetworkingTest extends TestCase {
    static Server server;


    public void init(){
        if (server != null) return;
        server = new Server(new Model());

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    @org.junit.Test
    public void testSendingToAllFromServer(){
        init();
        Client client = new Client(new Model());
        loginClient(client, "client", "clientPassword");
        Client client2 = new Client(new Model());
        loginClient(client2, "client2", "client2Password");
        Client client3 = new Client(new Model());
        loginClient(client3, "client2", "client2Password");

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


        System.out.println("Server");
        for (Appointment appointment : server.networking.getModel().getAppointments()) System.out.println(appointment);
        System.out.println("Client");
        for (Appointment appointment : client.networking.getModel().getAppointments()) System.out.println(appointment);
        System.out.println("Client2");
        for (Appointment appointment : client2.networking.getModel().getAppointments()) System.out.println(appointment);
        System.out.println("Client3");
        for (Appointment appointment : client3.networking.getModel().getAppointments()) System.out.println(appointment);


        assertTrue("Check all appointments have been sent", sameAppointments(
                client.networking.getModel().getAppointments(),
                server.networking.getModel().getAppointments()));

    }

    @Test
    public void testChangedAppointmentBroadcast(){
        init();
        Appointment sendingAppointment = RandomGenerator.generateAppointment();

        Client client = new Client(new Model());
        loginClient(client, "client", "clientPassword");
        Client client2 = new Client(new Model());
        loginClient(client2, "client2", "client2Password");
        Client client3 = new Client(new Model());
        loginClient(client3, "client2", "client2Password");

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        client.networking.sendAppointment(sendingAppointment);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        assertTrue("Server received appointment", sameAppointments(client.networking.getModel().getAppointments(), server.networking.getModel().getAppointments()));
        assertTrue("Other clients received appointment", sameAppointments(client.networking.getModel().getAppointments(), client2.networking.getModel().getAppointments())
        && sameAppointments(client.networking.getModel().getAppointments(), client3.networking.getModel().getAppointments()));
    }

private boolean sameAppointments(Appointment[] list1, Appointment[] list2){
        boolean result = true;
        boolean found;

        if (list1.length != list2.length) result = false;

        for (Appointment appointment1 : list1){
            found = false;
            for (Appointment appointment2 : list2){
                if (appointment1.equals(appointment2)) found = true;
            }
            if (!found) result = false;
        }
        return result;
    }

    private void loginClient(final Client client, final String username, final String password){
        new Thread(new Runnable() {
            @Override
            public void run() {
                client.logIn(username, password);
            }
        }).start();
    }

}
