package com.proj.test;

import com.proj.model.Appointment;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Alfredo
 * Date: 11.03.14
 * Time: 17:45
 * To change this template use File | Settings | File Templates.
 */
public class RandomGenerator {

    public static Appointment newAppointment(){
        return new Appointment(null, null, null, "stuff stuff");
    }

    public static ArrayList<Appointment> newAppointments(int amount){
        ArrayList<Appointment> result = new ArrayList<Appointment>();
        for (int i = 0; i < amount; i++){
            result.add(newAppointment());
        }
        return result;
    }

}
