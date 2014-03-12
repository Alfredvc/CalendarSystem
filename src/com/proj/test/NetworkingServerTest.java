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
public class NetworkingServerTest {

    public static void main(String args[]){
        Appointment testAppointment = RandomGenerator.newAppointment();
        Model model1 = new Model();
        model1.setAppointments(RandomGenerator.newAppointments(1));
        Server server = new Server(model1);
        //server.networking.sendAppointment(testAppointment);
    }

}
