package com.mark.waif.mail;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.mark.waif.Event.EventService;
import com.mark.waif.user.AppUserRepo;
import com.mark.waif.Event.Event;

@Component
public class MailScheduling {
	
	@Autowired
	private EventService eventService;
	
	@Autowired
	private MailService mailService;
	
	private Hashtable<Integer,Long> minToEpoch = new Hashtable<Integer,Long>(){
	{
		put(30,1800000L);		// 30 minutes
		put(60,3600000L);		// 60 minutes / 1 hours
		put(120,7200000L);		// 2 hours
		put(180,10800000L);		// 3 hours
		put(240,14400000L);		// 4 hours
		put(360,21600000L);		// 6 hours
		put(720,43200000L);		// 12 hours
		put(1440,86400000L);	// 24 hours
	}};

	@Scheduled(initialDelay = 60000 ,fixedDelay=300000)
	public void testSchedule() {
		Long currEpoch = new Date().getTime();
		System.out.println("mailScheduling run on : " + currEpoch);
		List<Event> list = eventService.getAllEventsOfDay(new Date());
		for(Event e : list) {
			if(e.getRemind()==-1)
				continue;
			System.out.println(e.getTitle());
			Long remEpoch = e.getStart().getTime();
			remEpoch -= minToEpoch.get(e.getRemind());
			if(currEpoch > remEpoch) {		//send email
				for(String user : e.getUsers())
				{
					try {
						mailService.sendEmail(e,user);
					} catch (UnsupportedEncodingException e1) {
						e1.printStackTrace();
					} catch (MessagingException e1) {
						e1.printStackTrace();
					}
				}
				System.out.println(e.getTitle() + " is mailed");
				e.setRemind(-1);
				eventService.updateEvent(e);
			}
		}
	}
}
