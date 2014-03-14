package com.proj.server;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import com.proj.model.Model;
import com.proj.model.Appointment;
import com.proj.database.Database;
import com.proj.network.ServerNetworking;

/**
 * Created with IntelliJ IDEA.
 * User: Alfredo
 * Date: 07.03.14
 * Time: 12:48
 * To change this template use File | Settings | File Templates.
 */
public class Server implements PropertyChangeListener {

    private Model model;
    private Database database;
    public ServerNetworking networking;

    public Server(Model model){
    	this.model = model;
    	
    	// Load data
    	System.out.println("Loading data from database...");
    	database = new Database(model);
    	database.load();
    	System.out.println("\tDONE!");
    	
    	// Start server
    	System.out.println("Starting server...");
        networking = new ServerNetworking(model);
        new Thread(networking).start();
    	System.out.println("\tDONE!");

    	// Bind to events
    	model.addPropertyChangeListener(this);
    }
    
    public static void main(String[] args) {
    	System.out.println("Welcome to Supreme Calendar!");
    	
    	
    	// Create model
    	System.out.println("Creating model...");
    	Model model = new Model();
    	System.out.println("\tDONE!");
    	
    	new Server(model);
    }

    /**
     * Listen to add/remove events
     */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		Object newValue = evt.getNewValue();
		Object oldValue = evt.getOldValue();
		Object source = evt.getSource();
		
		
		switch (evt.getPropertyName()){
			case "appointments":			
				if (newValue instanceof Appointment) {
					Appointment newAppointment = (Appointment) newValue;
					
					if (oldValue == null) {
						// New appointment - add listener
						newAppointment.addPropertyChangeListener(this);
					}
					
					// New appointment
					database.save(newAppointment);
					networking.sendAppointment(newAppointment);
				} else if (newValue == null && oldValue instanceof Appointment) {
					// Delete appointment :'(
					Appointment oldAppointment = (Appointment) oldValue;
					database.delete(oldAppointment);
					//TODO
					//networking.sendDeleteAppointment(oldAppointment);
				}
				break;
			
			case "participants":
				//TODO
				if (source instanceof Appointment) {
					System.out.println("The participants of appointment" + ((Appointment) source).getId().toString());
					// Send mails?
				}
				break;
		}
	}
}
