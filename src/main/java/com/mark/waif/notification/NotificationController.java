package com.mark.waif.notification;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("v1/noti")
public class NotificationController {

	@Autowired
	private NotificationService notificationService;
	
	@Autowired
	private NotificationRepo notificationRepo;
	
	@PostMapping("/create")
	public void createNotification(@RequestBody Notification noti) {
		notificationService.saveNotification(noti);
	}
	
	@GetMapping("/get")
	public List<Notification> getNotifications(){
		return notificationService.getNotifications();
	}
	
	@PostMapping("/accept/{id}")
	public void acceptNotification(@PathVariable(value="id") String id) {
		notificationService.acceptNotification(notificationRepo.findById(id).get());
	}
	
	@PostMapping("/reject/{id}")
	public void rejectNotification(@PathVariable(value="id") String id) {
		notificationService.rejectNotification(notificationRepo.findById(id).get());
	}
	
}
