package com.proj.model;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Alfredo
 * Date: 18.03.14
 * Time: 16:21
 * To change this template use File | Settings | File Templates.
 */
public class AppointmentChangeSupport {

    private ArrayList<AppointmentChangedListener> listeners;
    private Appointment appointment;

    public AppointmentChangeSupport(Appointment appointment) {
        this.listeners = new ArrayList<>();
        this.appointment = appointment;
    }

    public void addAppointmentChangedListener(AppointmentChangedListener appointmentChangedListener){
        listeners.add(appointmentChangedListener);
    }

    public void removeAppointmentChangedListener(AppointmentChangedListener appointmentChangedListener){
        listeners.remove(appointmentChangedListener);
    }

    public void fireAppointmentChanged(){
        for (AppointmentChangedListener listener : listeners){
            listener.appointmentChanged(appointment);
        }
    }

    public interface AppointmentChangedListener{
        public void appointmentChanged(Appointment appointment);
    }

}
