package com.proj.gui;

import javax.swing.AbstractListModel;
import java.beans.*;
import java.util.ArrayList;

import com.proj.model.Appointment;
import com.proj.model.Employee;
import com.proj.model.Model;
import com.proj.model.Notification;

public class NotificationListModel extends AbstractListModel<Notification> implements PropertyChangeListener  {

	private Model model;
	private Employee me;
	private ArrayList<Notification> notif;
	
	
	
	public NotificationListModel(Model model, Employee me){
		this.model = model;
		this.me = me;
		notif=this.model.myNotifications(this.me);
		for(Appointment app: this.model.getMyAppointments(this.me)){
			app.addPropertyChangeListener(this);                      //Lytter paa dine egne avtaler
		}
	}
	
	
	
	@Override
	public int getSize() {
	
		return this.notif.size();
	}

	@Override
	public Notification getElementAt(int index) {
		return this.notif.get(index);
	}



	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		
			int index1=this.notif.size();
			this.notif.clear();
			fireIntervalRemoved(this, 0, index1);
			this.notif=this.model.myNotifications(this.me);
			fireIntervalAdded(this, 0,this.notif.size());

	}

	

	
	
}
