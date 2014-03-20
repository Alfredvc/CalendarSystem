package com.proj.network;

import com.proj.model.Employee;
import com.proj.model.Group;
import com.proj.model.MeetingRoom;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Alfredo
 * Date: 20.03.14
 * Time: 14:04
 * To change this template use File | Settings | File Templates.
 */
public class InitEnvelope implements Serializable {

    private Employee[] employees;
    private MeetingRoom[] meetingRooms;
    private Group[] groups;

    public InitEnvelope(Employee[] employees, MeetingRoom[] meetingRooms, Group[] groups) {
        this.employees = employees;
        this.meetingRooms = meetingRooms;
        this.groups = groups;
    }

    public Employee[] getEmployees() {
        return employees;
    }

    public MeetingRoom[] getMeetingRooms() {
        return meetingRooms;
    }

    public Group[] getGroups() {
        return groups;
    }
}
