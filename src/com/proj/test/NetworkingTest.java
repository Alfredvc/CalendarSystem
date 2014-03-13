package com.proj.test;

import com.proj.client.Client;
import com.proj.model.Appointment;
import com.proj.model.Model;
import com.proj.server.Server;

import junit.framework.TestCase;

import java.util.Arrays;
import java.util.Collection;

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
        Model clientModel = new Model();

        Server server = new Server(serverModel);

        Client client = new Client(clientModel);
        client.logIn("TestUsername", "TestPassword");

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        assertTrue("Check all appointments have been sent", sameAppointments(
                Arrays.asList(client.networking.getModel().getAppointments()),
                Arrays.asList(server.networking.getModel().getAppointments()))
               );

    }

    private boolean sameAppointments(Collection<Appointment> list1, Collection<Appointment> list2){
        boolean result = true;
        if (list1.size() != list2.size()) result = false;

        for (Appointment appointment : list1){
            if (!list2.contains(appointment)) result = false;
        }
        return result;
    }

}
