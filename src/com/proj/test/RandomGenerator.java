package com.proj.test;

import com.proj.model.*;

import java.util.*;


/**
 * Created with IntelliJ IDEA.
 * User: Alfredo
 * Date: 11.03.14
 * Time: 17:45
 * To change this template use File | Settings | File Templates.
 */
public class RandomGenerator {

	 private static Random rand= new Random();

    
	 public static Appointment generateAppointment(){
    	Participant leader = generateParticipant();
    	Date startTime = generateDate();
    	Date endTime = generateDate();
    	MeetingRoom meetingRoom = generateMeetingRoom();
        Appointment app= new Appointment( leader, startTime, endTime, meetingRoom );
        app.setDescription(generateString());
        for(int i = 0; i < rand.nextInt(10); i++ ){
        	app.addParticipant(generateParticipant());
        }
        return app;
    
    }
	
	
    public static ArrayList<Appointment> generateAppointments(int amount){
        ArrayList<Appointment> result = new ArrayList<Appointment>();
        for (int i = 0; i < amount; i++){
            result.add(generateAppointment());
        }
        return result;
    }
    
    public static Employee generateEmployee(){
    	int tel=rand.nextInt(80000000)+10000000;
    	Employee emp= new Employee(generateEmail(), generateName(),tel);
    	return emp;
    	
    }
    
    public static  Participant generateParticipant(){
    	return new Participant (generateEmployee(), Status.values()[rand.nextInt(3)] ,  rand.nextBoolean(),  rand.nextBoolean());

    }
    
    public static Date generateDate(){
    	Date d=new Date();
    	return d;
    }
    
    private static String generateName(){
    	String names[]={"Jacque Magee","Bev Seabury","Johanna Tisdale" ,"Iva Ellingson" ,"Vonda Chiou" ,"Gaylord Hutzler", "Laureen Brown",
    			"Lashandra Mollica", "Efrain Higgins", "Debora Bazemore",
    			"Chelsie Storm" ,
    			"Kiana Baucom", 
    			"Christopher Logsdon",
    			"Mei Parshall", "Kimberlee Ahumada", "Hoa Heimer" };
    	int randInt= rand.nextInt(names.length);
    	return names[randInt];
    	
    }

    
    public static MeetingRoom generateMeetingRoom(){
    	
    	return new MeetingRoom(String.valueOf( rand.nextInt(100000)+1), rand.nextInt(100)+2);
    }
    
    private static String generateString(){
    	String s="";
    	int length = rand.nextInt(10)+1;                     //Sannnsynligheten for aa generere 2 like email er 0.00000000.....02
    	for (int i = 0; i<length; i++ ){
    		char ch = (char) (rand.nextInt(57)+66);
    		s+=ch;
    	}
    	return s;
    }
    
    private static String generateEmail(){
    	String s=generateString();
    	s=s+'@';
    	s=s+generateString();
    	return s;
    }

}


