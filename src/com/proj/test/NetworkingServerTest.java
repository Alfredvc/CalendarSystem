package com.proj.test;

import com.proj.client.Client;
import com.proj.model.Appointment;
import com.proj.model.Model;
import com.proj.server.Server;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Alfredo
 * Date: 12.03.14
 * Time: 11:36
 * To change this template use File | Settings | File Templates.
 */
public class NetworkingServerTest {

    public static void main(String args[]){
        Model model1 = new Model();
        ArrayList<Appointment> apps = new ArrayList<Appointment>();
        Appointment[] aps = RandomGenerator.generateAppointments(5);
        for (int i = 0; i < 5; i++) {
            apps.add(aps[i]);
        }
        model1.setAppointments(apps);
        Server server = new Server(model1);
    }

}
