package com.rihis.serviceImpl;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;

@Service
public class EmailService {

	public boolean sendEmail(String message, String subject, String to) {
		boolean f = false;

		//variable for gmail smtp(simple mail transfer protocol)
		String host = "smtp.gmail.com";
		
		//get the system properties
		Properties properties = System.getProperties();
		//System.out.println("PROPERTIES "+properties);
		
		//set important information to properties object
		
		// host set
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", "465");
		properties.put("mail.smtp.ssl.enable", true);
		properties.put("mail.smtp.auth", true);
		
		//Step 1:-  to get the session object
		Session session = Session.getInstance(properties,new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("testingrahul2553@gmail.com", "mwtrpjknpirulbqt");
			}
		});
		
		//session.setDebug(true);
		
		//Step 2:- compose the message [text,multimedia]
		
		MimeMessage mimeMessage = new MimeMessage(session);
		
		
		
		try {
			//from email id
			mimeMessage.setFrom("testingrahul2553@gmail.com");
			
			//add recipent
			mimeMessage.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
			
			//add subject to message
			mimeMessage.setSubject(subject);
			
			//add text to message
			mimeMessage.setContent(message,"text/html");
	
			
			//send 
			
			//Step 3:- send message using transport class
			
			Transport.send(mimeMessage);
			System.out.println("Sent Success.........");
			f = true;
			
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
		return f;
	}
	
	
}
