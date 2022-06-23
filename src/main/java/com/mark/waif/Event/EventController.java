package com.mark.waif.Event;

import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mark.waif.notification.Notification;
import com.mark.waif.notification.NotificationService;
import com.mark.waif.service.DataService;
import com.mark.waif.user.UserConfig;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("v1/event")
public class EventController
{
    @Autowired
    private EventService eventService;
    @Autowired
    private DataService dataService;
    @Autowired
    private NotificationService notificationService;
    
	@GetMapping("/test")						//A test url for debugging
    public void  test() {
		
    }
	
	@PostMapping("/create")				//Insert/update an entry in index
    public Event addEvent(@RequestBody Event event)
    {
		Event e = eventService.createEvent(event);
		if(e.getPending().size()>=1) {
		notificationService.saveNotification(new Notification(
																UserConfig.getUsername(),
																e.getPending(),
																e.getId(),
																e.getTitle()													
				));
		}
        return e;
    }

    @PostMapping("/createInBulk")		//Insert/update multiple entries in index
    public Iterable<Event> addEventsInBulk(@RequestBody List<Event> eventList)
    {
        return eventService.createBulkEvent(eventList);
    }
    
	@GetMapping("/getdaydata")			// Return entries in a day
    public List<Event> getDayEvent (@RequestParam("day")String day){
    	return eventService.getDayEvents(day);
    }
    
    @SuppressWarnings("deprecation")
	@GetMapping("/getintervaldata")		// Return entries in a date interval
    public Hashtable<String, List<Event>> getIntervalEvents (@RequestParam("start")String start,@RequestParam("end")String end){
    	Date s = dataService.stringToDate(start);
    	Date e = dataService.stringToDate(end);
    	Hashtable<String,List<Event>> data = new Hashtable<String, List<Event>>();
    	for(Date d = s;d.before(e); d.setDate(d.getDate()+1) ) {
    		List<Event> da = eventService.getDayEvents(dataService.dateToString(d));
    		if(!da.isEmpty()) {
    			data.put(dataService.dateToString(d).substring(0, 10), da);
    		}
    	}
    	return data;
    }
    
    @GetMapping("/searchall")			//(Debug)Return all entries in index
    public Iterable<Event> searchAllData (){
    	return eventService.returnAllEvents();
    }

    @GetMapping("/count")				//(Debug)Get no of entries
    public long getCount() {
    	return eventService.entryCount();
    }
    
    @PutMapping("/update")				//update an entry in index
    public void updateEvent(@RequestBody Event event)
    {
        eventService.updateEvent(event);
    }
    
    @DeleteMapping("/del")				//Delete entry
    public HttpStatus delById(@RequestParam("id")String id) {
    		eventService.delByID(id);
    		return HttpStatus.OK;
    }

	@DeleteMapping("/delday")			//Delete records of the specified day
    public HttpStatus deleteAllofDay (@RequestParam("day")String day) {
    	eventService.delAll(getDayEvent(day));
    	return HttpStatus.OK;
    }
}