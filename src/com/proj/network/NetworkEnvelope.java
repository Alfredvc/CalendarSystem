package com.proj.network;

import com.proj.model.Appointment;
import com.proj.model.Employee;
import com.proj.model.Group;
import com.proj.model.MeetingRoom;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Alfredo
 * Date: 20.03.14
 * Time: 17:25
 * To change this template use File | Settings | File Templates.
 */
public class NetworkEnvelope implements Serializable{

    public static enum Type{
        LoginRequest, LoginResponse, SendingAppointment, DisconnectionRequest, DoneSendingAppointments
    }

    private Type type;
    private String username;
    private String password;
    private String loginResponse;
    private Appointment appointment;
    private Appointment.Flag flag;
    private Employee[] employees;
    private Group[] groups;
    private MeetingRoom[] meetingRooms;

    public NetworkEnvelope doneSendingAppointments(){
        this.type = Type.DoneSendingAppointments;
        return this;
    }

    public NetworkEnvelope loginRequest(String username, String password){
        this.type = Type.LoginRequest;
        this.username = username;
        this.password = password;
        return this;
    }

    public NetworkEnvelope loginResponse(String loginResponse, Employee[] employees, Group[] groups,
                                         MeetingRoom[] meetingRooms){
        this.type = Type.LoginResponse;
        this.loginResponse = loginResponse;
        this.employees = employees;
        this.groups = groups;
        this.meetingRooms = meetingRooms;
        return this;
    }

    public NetworkEnvelope sendingAppointment(Appointment appointment, Appointment.Flag flag){
        this.type = Type.SendingAppointment;
        this.appointment = appointment;
        this.flag = flag;
        return this;
    }

    public NetworkEnvelope disconnectRequest(){
        this.type = Type.DisconnectionRequest;
        return this;
    }

    public Type getType() {
        return type;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getLoginResponse() {
        return loginResponse;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public Appointment.Flag getFlag() {
        return flag;
    }

    public Employee[] getEmployees() {
        return employees;
    }

    public Group[] getGroups() {
        return groups;
    }

    public MeetingRoom[] getMeetingRooms() {
        return meetingRooms;
    }
}
