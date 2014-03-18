package com.proj.server;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import com.proj.model.ExternalParticipant;
import com.proj.model.Model;
import com.proj.model.Appointment;
import com.proj.database.Database;
import com.proj.model.ModelChangeSupport;
import com.proj.network.AppointmentEnvelope;
import com.proj.network.ServerNetworking;

/**
 * Created with IntelliJ IDEA.
 * User: Alfredo
 * Date: 07.03.14
 * Time: 12:48
 * To change this template use File | Settings | File Templates.
 */
public class Server implements ModelChangeSupport.ModelChangedListener{

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
        networking = new ServerNetworking(this);
        new Thread(networking).start();
        model.addModelChangeListener(this);
    	System.out.println("\tDONE!");
    }
    
    public static void main(String[] args) {
    	System.out.println("Welcome to Supreme Calendar!");
    	
    	
    	// Create model
    	System.out.println("Creating model...");
    	Model model = new Model();
    	System.out.println("\tDONE!");
    	
    	new Server(model);
    }

    public Model getModel() {
        return model;
    }

    public boolean requestLogin(String username, String password){
        return database.checkLogin(username, password);
    }

    private void sendInviteEmail(Appointment appointment, ExternalParticipant participant){

    }

    private void sendDisinviteEmail(Appointment appointment, ExternalParticipant participant){

    }

    @Override
    public void modelChanged(Appointment appointment, Appointment.Flag flag, PropertyChangeEvent event) {
        if (event.getPropertyName().equals("participant")){
            if (event.getNewValue() != null && event.getNewValue() instanceof ExternalParticipant && event.getOldValue() == null){
                sendInviteEmail(appointment, (ExternalParticipant) event.getNewValue());
            }
            else if (event.getOldValue() != null && event.getOldValue() instanceof ExternalParticipant && event.getNewValue() == null){
                sendDisinviteEmail(appointment, (ExternalParticipant) event.getOldValue());
            }
        }
    }
}
