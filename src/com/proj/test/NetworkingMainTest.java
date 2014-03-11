package com.proj.test;

import com.proj.client.Client;
import com.proj.model.Appointment;
import com.proj.model.Model;
import com.proj.server.Server;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Alfredo
 * Date: 11.03.14
 * Time: 23:49
 * To change this template use File | Settings | File Templates.
 */
public class NetworkingMainTest {

    public static void main (String args[]){
        Appointment testAppointment = RandomGenerator.newAppointment();
        Model model1 = new Model();
        model1.setAppointments(RandomGenerator.newAppointments(1));
        Server server = new Server(model1);
        Client client = new Client(new Model());
        client.logIn("username", "password");
        server.networking.sendAppointment(testAppointment);
        client.networking.sendAppointment(testAppointment);
    }

}
