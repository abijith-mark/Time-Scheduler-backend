package com.mark.waif.notification;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mark.waif.Event.Event;
import com.mark.waif.Event.EventRepo;
import com.mark.waif.user.UserConfig;

@Service
public class NotificationService {

	@Autowired
	private NotificationRepo notificationRepo;
	
	@Autowired
	private EventRepo eventRepo;
	
	public void saveNotification(Notification noti) {
		notificationRepo.save(noti);
	}
	
	public List<Notification> getNotifications() {
		return notificationRepo.findAllByTo(UserConfig.getUsername());
	}
	
	public void acceptNotification(Notification noti) {
		Event event = eventRepo.findById(noti.getEventId()).get();
		event.delFromPending(UserConfig.getUsername());
		event.addUsers(UserConfig.getUsername());
		eventRepo.save(event);
		delNotification(noti.getId());
	}
	
	public void rejectNotification(Notification noti) {
		Event event = eventRepo.findById(noti.getEventId()).get();
		event.delFromPending(UserConfig.getUsername());
		eventRepo.save(event);
		delNotification(noti.getId());
	}
	
	public void delNotification(String id) {
		if(!notificationRepo.findById(id).isPresent()) {
			return;
		}
		Notification noti = notificationRepo.findById(id).get();
		if(noti.getTo().size()==1) {
			notificationRepo.delete(noti);		//if not working, delete by id
			return;
		}
		noti.delFromTo(UserConfig.getUsername());
		notificationRepo.save(noti);
	}
	
}
