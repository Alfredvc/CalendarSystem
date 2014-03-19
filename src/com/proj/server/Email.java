package com.proj.server;

import com.proj.model.Appointment;
import com.proj.model.ExternalParticipant;
import com.proj.model.Participant;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: Alfredo
 * Date: 19.03.14
 * Time: 11:29
 * To change this template use File | Settings | File Templates.
 */
public class Email {

    private static final String username ="company.email.notifier@gmail.com";
    private static final String password ="programvareutvikling21";
    private static final String emailHost ="smtp.gmail.com";
    private static final String sender ="calendar@company.com";
    private static final String senderName ="Company Name";

    private String to;
    private String subject;
    private String message;

    public Email(){

    }

    public Email(String to, String subject, String message) {
        this.to = to;
        this.subject = subject;
        this.message = message;
    }

    public Email invitation(Appointment appointment, ExternalParticipant externalParticipant){
        this.to = externalParticipant.getEmail();
        this.subject = "You have been invited to a meeting by " + appointment.getLeader()
                + " the " + new SimpleDateFormat("dd-MM-YY HH:mm").format(appointment.getStartTime());
        this.message = "Meeting leader: " + appointment.getLeader() + "\nDescription: "
                + appointment.getDescription() + "\nInvited: ";
        for (Participant participant : appointment.getParticipants()){
            if (participant.equals(externalParticipant)) continue;
            this.message += "\n" + participant.getDisplayName();
        }
        return this;
    }

    public Email disinvitation(Appointment appointment, ExternalParticipant externalParticipant){
        this.to = externalParticipant.getEmail();
        this.subject = "You are no longer invited to a meeting by " + appointment.getLeader()
                + " the " + new SimpleDateFormat("dd-MM-YY HH:mm").format(appointment.getStartTime());
        this.message = "Meeting leader: " + appointment.getLeader() + "\nDescription: "
                + appointment.getDescription() + "\nInvited: ";
        for (Participant participant : appointment.getParticipants()){
            if (participant.equals(externalParticipant)) continue;
            this.message += "\n" + participant.getDisplayName();
        }
        return this;
    }

    public void sendMail() {

        if (to == null || subject == null || message == null) throw new NullPointerException();

        Properties props = System.getProperties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", emailHost);
        props.put("mail.smtp.user", username);
        props.put("mail.smtp.password", password);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(sender, senderName));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(this.to));
            message.setSubject(this.subject);
            message.setText(this.message);

            Transport.send(message);

            System.out.println("The message has been sent");

        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

}
