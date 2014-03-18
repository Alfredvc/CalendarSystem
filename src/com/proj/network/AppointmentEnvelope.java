package com.proj.network;

import com.proj.model.Appointment;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Alfredo
 * Date: 18.03.14
 * Time: 14:53
 * To change this template use File | Settings | File Templates.
 */
public class AppointmentEnvelope implements Serializable {

    private Appointment.Flag flag;
    private Appointment appointment;

    public AppointmentEnvelope(Appointment appointment, Appointment.Flag flag) {
        this.flag = flag;
        this.appointment = appointment;
    }

    public Appointment.Flag getFlag() {
        return flag;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    @Override
    public String toString(){
        return flag.name() + " " + appointment;
    }

}
