package com.mark.waif.mail;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mark.waif.Event.Event;
import com.mark.waif.user.AppUserRepo;
import com.mark.waif.user.UserConfig;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

@Service
public class MailService {
	
	@Autowired
	JavaMailSender mailSender;

	@Autowired
	private AppUserRepo appUserRepo;
	
	public void sendEmail(Event e, String user) throws MessagingException, UnsupportedEncodingException{
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		helper.setFrom("abijithgame@gmail.com","WAIF-NotificationService");
	    helper.setTo(appUserRepo.findByUsername(user).get().getEmail());
	    String subject = "Event Remainder"; 
	    
	    String content = "<p>Hello <b>" + user + "</b></p>"
                + "<p>Your Event \"" + e.getTitle() + "\" is in " + e.getRemind()/60 + " hours!"
                + "<p> Event Scheduled on : " + e.getStart() + " till " + e.getEnd()
                ;
	    helper.setSubject(subject);
        
        helper.setText(content, true);
         
        mailSender.send(message); 
	}
	
}
