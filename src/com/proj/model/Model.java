package com.proj.model;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.sql.Date;
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
		Appointment oldValue=this.appointments.remove(id);
		pcs.firePropertyChange("appointments",oldValue ,null);
	}
	
    public void addAppointment(Appointment app){
		this.appointments.put(app.getId(), app);
		pcs.firePropertyChange("appointments", null, app);
	}

    //Does not fire a property change, copyFrom/addAppointment take care of that
    public void updateAppointment(Appointment app){
        if (appointments.containsKey(app.getId())){
            this.appointments.get(app.getId()).copyFrom(app);
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
					for(int i=0; i<notif.size(); i++){
						if(n.getTimestamp().after(notif.get(i).getTimestamp())){
							notif.set(i, n);
						}
					}
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

    private class ModelChangeNotifier implements PropertyChangeListener, AppointmentChangeSupport.AppointmentChangedListener{

        public ModelChangeSupport mcs;


        public ModelChangeNotifier(){
            mcs = new ModelChangeSupport();
        }

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            if (evt.getPropertyName().equals("appointments")){
                if (evt.getOldValue() != null && evt.getOldValue() instanceof Appointment && evt.getNewValue() == null ){
                    mcs.fireModelChanged((Appointment) evt.getOldValue(), Appointment.Flag.DELETE);
                } else if(evt.getNewValue() != null && evt.getNewValue() instanceof Appointment && evt.getOldValue() == null){
                    mcs.fireModelChanged((Appointment) evt.getNewValue(), Appointment.Flag.UPDATE);
                }
            }
        }

        @Override
        public void appointmentChanged(Appointment appointment) {
            mcs.fireModelChanged(appointment, Appointment.Flag.UPDATE);
        }
    }

}
