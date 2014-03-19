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
    	InternalParticipant leader = generateInternalParticipant();
    	Date startTime = new Date(generateDate().getTime() - 1000*60*60*10);
    	Date endTime = new Date(startTime.getTime() + 1000*60*60*(rand.nextInt(48) + 1));
    	MeetingRoom meetingRoom = generateMeetingRoom();
        Appointment app= new Appointment( leader, startTime, endTime, meetingRoom );
        app.setDescription(generateString());
        for(int i = 0; i < rand.nextInt(10); i++ ){
        	app.addParticipant(generateInternalParticipant());
        }
        return app;
    
    }
	
	
    public static Appointment[] generateAppointments(int amount){
        Appointment[] result = new Appointment[amount];
        for (int i = 0; i < amount; i++){
            result[i] = generateAppointment();
        }
        return result;
    }
    
    public static Employee generateEmployee(){
    	int tel=rand.nextInt(80000000)+10000000;
    	Employee emp= new Employee(generateEmail(), generateName(),tel);
    	return emp;
    	
    }
    
    public static  InternalParticipant generateInternalParticipant(){
    	return new InternalParticipant (generateEmployee(), Status.values()[rand.nextInt(3)] ,  rand.nextBoolean(),  rand.nextBoolean());

    }
    
    public static Date generateDate(){
    	Calendar calendar = Calendar.getInstance();
    	calendar.set(Calendar.DAY_OF_MONTH, rand.nextInt(7));
    	calendar.set(Calendar.HOUR_OF_DAY, rand.nextInt(23));
    	calendar.set(Calendar.MINUTE, rand.nextInt(60));
    	return calendar.getTime();
    }
    
    private static String generateName(){
    	String names[]={"Jacque Magee","Bev Seabury","Johanna Tisdale" ,"Iva Ellingson" ,"Vonda Chiou" ,"Gaylord Hutzler", "Laureen Brown",
    			"Lashandra Mollica", "Efrain Higgins", "Debora Bazemore", "Chelsie Storm", "Kiana Baucom", 
    			"Christopher Logsdon", "Mei Parshall", "Kimberlee Ahumada", "Hoa Heimer", "Wiley Champ", 
    			"Karren Covell", "Gloria Jaimes", "Merrill Vasko", "Luvenia Classen", "Lela Fielder", 
    			"Clora Lesh", "Harriett Huyser", "Yvonne Wiers", "Wanetta Nardone", "Lucius Stolle" };
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

    
    public static Group generateGroup(){
    	String gName = generateString();
    	Group grp = new Group(gName);
    	for (int i=0; i<5; i++) {
    		grp.addEmployee(generateEmployee());
    	}
    	return grp;
    }
}


