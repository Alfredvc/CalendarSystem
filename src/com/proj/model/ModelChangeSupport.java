package com.proj.model;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Alfredo
 * Date: 18.03.14
 * Time: 16:21
 * To change this template use File | Settings | File Templates.
 */
public class ModelChangeSupport {

    private ArrayList<ModelChangedListener> listeners;

    public ModelChangeSupport() {
        this.listeners = new ArrayList<>();
    }

    public void addModelChangedListener(ModelChangedListener modelChangedListener){
        listeners.add(modelChangedListener);
    }

    public void removeAppointmentChangedListener(ModelChangedListener modelChangedListener){
        listeners.remove(modelChangedListener);
    }

    public void fireModelChanged(Appointment appointment, Appointment.Flag flag, PropertyChangeEvent event){
        for (ModelChangedListener listener : listeners){
            listener.modelChanged(appointment, flag, event);
        }
    }

    public interface ModelChangedListener{
        public void modelChanged(Appointment appointment, Appointment.Flag flag, PropertyChangeEvent event);
    }

}
