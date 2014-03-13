package com.proj.test;

import com.proj.client.Client;
import com.proj.model.Appointment;
import com.proj.model.Model;
import com.proj.server.Server;
import junit.framework.Test;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: Alfredo
 * Date: 13.03.14
 * Time: 11:02
 * To change this template use File | Settings | File Templates.
 */
public class NetworkingTest extends TestCase {

    @org.junit.Test
    public void testSending(){

        Model serverModel = new Model();
        serverModel.setAppointments(RandomGenerator.generateAppointments(5));

        Server server = new Server(serverModel);

        Client client = new Client(new Model());
        loginClient(client, "client", "clientPassword");
        /*Client client2 = new Client(new Model());
        loginClient(client2, "client2", "client2Password");
        Client client3 = new Client(new Model());
        loginClient(client3, "client2", "client2Password");
        */

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        System.out.println("Server");
        for (Appointment appointment : server.networking.getModel().getAppointments()) System.out.println(appointment);
        System.out.println("Client");
        for (Appointment appointment : client.networking.getModel().getAppointments()) System.out.println(appointment);
        /*System.out.println("Client2");
        for (Appointment appointment : client2.networking.getModel().getAppointments()) System.out.println(appointment);
        System.out.println("Client3");
        for (Appointment appointment : client3.networking.getModel().getAppointments()) System.out.println(appointment);
        */

        //assertTrue("Check all appointments have been sent", sameAppointments(
        //        client.networking.getModel().getAppointments(), server.networking.getModel().getAppointments()));

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
