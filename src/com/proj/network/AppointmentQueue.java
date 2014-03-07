package com.proj.network;

import java.util.ArrayList;
import java.util.Deque;

/**
 * Created with IntelliJ IDEA.
 * User: Alfredo
 * Date: 07.03.14
 * Time: 15:09
 * To change this template use File | Settings | File Templates.
 */
public class AppointmentQueue {

    private Deque<Appointment> queue1;
    private Deque<Appointment> queue2;

    private boolean locked;
    private boolean queue1IsMain;
    private boolean unlocking;

    public AppointmentQueue(){
        this.unlocking = false;
        this.locked = false;
        this.queue1IsMain = true;
    }

    public void lock(){
        this.locked = true;
    }

    public void unlock(){
        this.unlocking = true;
        this.locked = false;
        if (queue1IsMain){
            queue1IsMain = false;
        }
        this.unlocking = false;
    }

    public Appointment[] getNewAppointments(){
        Apppointment[] result;
        if (queue1IsMain){
            result = queue1.toArray(result);
            queue1.clear();
        }
        else {
            result = queue2.toArray(result);
            queue2.clear();
        }

    }

    public Appointment pop(){
        if (queue1IsMain) return queue1.pop();
        else return queue2.pop();
    }

    public boolean push(Appointment appointment){
        if (unlocking) return false;
        if (locked){
            if (queue1IsMain) queue2.push(appointment);
            else queue1.push(appointment);
        }
        else {
            if (queue1IsMain) queue1.push(appointment);
            else queue2.push(appointment);
        }

        return true;
    }


}
