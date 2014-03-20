package com.proj.model;

import java.beans.PropertyChangeEvent;
import java.io.Serializable;
import java.util.ArrayList;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Date;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

public class Model {

	private HashMap<UUID,Appointment> appointments = new HashMap<>();
	private HashMap<String, Employee> employees = new HashMap<>();
	private HashMap<String, MeetingRoom> meetingRooms = new HashMap<>();
	private ArrayList <Group> groups = new ArrayList<>();
	private PropertyChangeSupport pcs= new PropertyChangeSupport(this);
    private ModelChangeNotifier notifier = new ModelChangeNotifier();

    public Model(){
        pcs.addPropertyChangeListener(notifier);
    }
	
	public void deleteAppointment(UUID id){
        appointments.get(id).removeAppointmentChangeListener(notifier);
		Appointment oldValue=this.appointments.remove(id);
		pcs.firePropertyChange("appointments",oldValue ,null);
	}
	
    public void addAppointment(Appointment app){
        app.addAppointmentChangeListener(notifier);
        this.appointments.put(app.getId(), app);
		pcs.firePropertyChange("appointments", null, app);
	}

    //Does not fire a property change, copyFrom/addAppointment take care of that
    public void updateAppointment(Appointment app){
        if (appointments.containsKey(app.getId())){
            this.appointments.get(app.getId()).updateFrom(app);
        } else {
            addAppointment(app);
        }
    }
	

	/**
	 * Needed by tests. Adds all appointments in the provided collection.
	 * @param appointments
	 */
	public void setAppointments(Collection<Appointment> appointments) {
		for (Appointment appointment : appointments) {
			this.addAppointment(appointment);
		}
	}
	
	
	public Appointment[]  getAppointments() {
		return (Appointment[]) appointments.values().toArray(new Appointment[appointments.size()]);

	}


	
	public Appointment getAppointment(UUID id) {
		return appointments.get(id);
	}

	public void addMeetingRoom(MeetingRoom meetingRoom) {
		meetingRooms.put(meetingRoom.getRoomNr(), meetingRoom);
	}
	
	public MeetingRoom getMeetingRoom(String roomNr) {
		return meetingRooms.get(roomNr);

	}
	public MeetingRoom[] getMeetingRooms(){
		return this.meetingRooms.values().toArray((new MeetingRoom[meetingRooms.values().size()]));
		
	}

	public Employee[] getEmployees() {
		return (Employee[]) employees.values().toArray(new Employee[employees.size()]);
	}
	
	public Employee getEmployee(String email) {
		return employees.get(email);
	}

	public void addEmployee(Employee employee) {
		employees.put(employee.getEmail(), employee);
	}

	public Group[] getGroups() {
		return (Group[]) groups.toArray(new Group[groups.size()]);
	}
	
	public void addGroup(Group group) {
		groups.add(group);
	}
	
	public ArrayList<Notification> myNotifications(Employee emp){
		ArrayList<Notification> notif=new ArrayList<>();
	
		for(Appointment app: getMyAppointments(emp)){
			for(Notification n: app.getNotifications()){
				if(notif.size()<10){
					notif.add(n);
				}
				else{
					int oldest=0;
					for(int i=0; i<notif.size(); i++){
						if(notif.get(oldest).getTimestamp().after(notif.get(i).getTimestamp())){
							oldest=i;
						}
					}
				if(n.getTimestamp().after(notif.get(oldest).getTimestamp())){notif.set(oldest,n);}
				}
			}
		}
		return notif;
	}
	
	public ArrayList<MeetingRoom> getFreeMeetingRooms(Date startTime, Date endTime){
		ArrayList<MeetingRoom> freeRooms= new ArrayList<MeetingRoom>(Arrays.asList(this.getMeetingRooms()));
		for(UUID key: appointments.keySet()){
			Appointment app=appointments.get(key);
			if((app.getStartTime().before(endTime) && app.getEndTime().after(endTime)) ||
					(app.getStartTime().before(startTime) && app.getEndTime().after(startTime))){
				
				freeRooms.remove(app.getMeetingRoom());
			};
		}
		return freeRooms;
	}
	
	public ArrayList<Appointment> getMyAppointments(Employee emp){
		ArrayList<Appointment> myApps=new ArrayList<>();
		for(UUID key: appointments.keySet()){
			for (Participant parti: appointments.get(key).getParticipants()){
				if(((InternalParticipant)parti).getEmployee().equals(emp)){
					myApps.add(appointments.get(key));
				}
			}
		}
		return myApps;
	}

	
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(listener);
	}

    public void addModelChangeListener(ModelChangeSupport.ModelChangedListener listener){
        notifier.mcs.addModelChangedListener(listener);
    }

    private class ModelChangeNotifier implements PropertyChangeListener, AppointmentChangeSupport.AppointmentChangedListener, Serializable{

        public ModelChangeSupport mcs;


        public ModelChangeNotifier(){
            mcs = new ModelChangeSupport();
        }

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            if (evt.getPropertyName().equals("appointments")){
                if (evt.getOldValue() != null && evt.getOldValue() instanceof Appointment && evt.getNewValue() == null ){
                    mcs.fireModelChanged((Appointment) evt.getOldValue(), Appointment.Flag.DELETE, evt);
                } else if(evt.getNewValue() != null && evt.getNewValue() instanceof Appointment && evt.getOldValue() == null){
                    mcs.fireModelChanged((Appointment) evt.getNewValue(), Appointment.Flag.UPDATE, evt);
                }
            }
        }

        @Override
        public void appointmentChanged(Appointment appointment, PropertyChangeEvent evt) {
            mcs.fireModelChanged(appointment, Appointment.Flag.UPDATE, evt);
        }
    }

}
