package com.proj.network;

import com.proj.model.Appointment;
import com.proj.model.Model;
import com.proj.model.ModelChangeSupport;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Alfredo
 * Date: 06.03.14
 * Time: 14:44
 * To change this template use File | Settings | File Templates.
 */
public abstract class Storage implements ModelChangeSupport.ModelChangedListener {

    public abstract boolean save(Appointment appointment);

    public abstract boolean delete(Appointment appointment);

    @Override
    public void modelChanged(Appointment appointment, Appointment.Flag flag, PropertyChangeEvent evt) {
        System.out.println("\tAppointment " + appointment + " changed " + evt);
        switch (flag){
            case DELETE:
                delete(appointment);
                break;
            case UPDATE:
                save(appointment);
                break;
        }
    }
}
