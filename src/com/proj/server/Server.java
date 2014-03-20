package com.proj.server;

import java.beans.PropertyChangeEvent;

import com.proj.model.*;
import com.proj.database.Database;
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
        new Email().invitation(appointment, participant).sendMail();
    }

    private void sendDisinviteEmail(Appointment appointment, ExternalParticipant participant){
        new Email().disinvitation(appointment, participant).sendMail();
    }

    @Override
    public void modelChanged(Appointment appointment, Appointment.Flag flag, PropertyChangeEvent event) {
        System.out.println("Model changed: " + event);
        if (event.getPropertyName().equals("participants")){
            if (event.getNewValue() != null && event.getNewValue() instanceof ExternalParticipant && event.getOldValue() == null){
                sendInviteEmail(appointment, (ExternalParticipant) event.getNewValue());
            }
            else if (event.getOldValue() != null && event.getOldValue() instanceof ExternalParticipant && event.getNewValue() == null){
                sendDisinviteEmail(appointment, (ExternalParticipant) event.getOldValue());
            }
        } else if(event.getPropertyName().equals("appointments") && event.getOldValue() == null){
            for (Participant participant : appointment.getParticipants()){
                if (participant instanceof ExternalParticipant) sendInviteEmail(appointment, (ExternalParticipant) participant);
            }
        }
    }



}
