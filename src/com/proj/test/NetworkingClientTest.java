package com.proj.test;

import com.proj.client.Client;
import com.proj.model.Appointment;
import com.proj.model.Model;
import com.proj.server.Server;

/**
 * Created with IntelliJ IDEA.
 * User: Alfredo
 * Date: 12.03.14
 * Time: 11:36
 * To change this template use File | Settings | File Templates.
 */
public class NetworkingClientTest {

    public static void main(String args[]){
        Appointment testAppointment = RandomGenerator.generateAppointment();
        Model model1 = new Model();
        Client client = new Client(model1);
        client.networking.logIn("username", "password");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        client.networking.close();
        //server.networking.sendAppointment(testAppointment);
    }

}
