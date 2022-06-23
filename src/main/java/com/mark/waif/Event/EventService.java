package com.mark.waif.Event;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import com.mark.waif.notification.Notification;
import com.mark.waif.notification.NotificationRepo;
import com.mark.waif.service.DataService;
import com.mark.waif.user.UserConfig;

@Service
public class EventService
{
	@Autowired
	private DataService dataService;
    @Autowired
    private EventRepo eventRepo;
    @Autowired
    private NotificationRepo notificationRepo;
    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;
    
    public List<Event> testy(String test){
    	return eventRepo.findAllByUsers(test);
    }

    public Event createEvent(Event event)	
    {
        return eventRepo.save(event);
    }

    public Iterable<Event> createBulkEvent(List<Event> eventList)	
    {
        return eventRepo.saveAll(eventList);
    }
    
    public List<Event> getEventsInRange(String startDate, String endDate) {
        Query query = new NativeSearchQueryBuilder()
            .withQuery(new BoolQueryBuilder()
        			.must(QueryBuilders.termQuery("users", UserConfig.getUsername()).caseInsensitive(true))
        			.must(QueryBuilders.rangeQuery("start").gte(startDate).lte(endDate)))
            .build();
        SearchHits<Event> searchHits = elasticsearchRestTemplate.search(query, Event.class);

        return searchHits.get().map(SearchHit::getContent).collect(Collectors.toList());
    }
    
    public List<Event> getAllEventsOfDay(Date startDate){
      	Date endDate = new Date();
       	endDate.setDate(endDate.getDate()+1);
       	return getAllEventsInRange(dataService.dateToString(startDate),dataService.dateToString(endDate));
    }
    
    public List<Event> getAllEventsInRange(String startDate, String endDate) {
        Query query = new NativeSearchQueryBuilder()
            .withQuery(QueryBuilders.rangeQuery("start").gte(startDate).lte(endDate))
            .build();
        SearchHits<Event> searchHits = elasticsearchRestTemplate.search(query, Event.class);

        return searchHits.get().map(SearchHit::getContent).collect(Collectors.toList());
    }
    
    @SuppressWarnings("deprecation")
	public List<Event> getDayEvents(String date){
    	Date date2 = dataService.stringToDate(date);
    	date2.setDate(date2.getDate()+1);
    	return getEventsInRange(date,dataService.dateToString(date2));
    }
    
    public Iterable<Event> returnAllEvents (){			
    	return eventRepo.findAll();
    }
    
    public long entryCount() {
    	return eventRepo.count();
    }
    
    public boolean ifTitleExists(String title) {
    	return eventRepo.existsByTitle(title);
    }

	public void updateEvent(Event event) {
		if(event.getPending().size()>0) {
			Notification noti = notificationRepo.findByEventId(event.getId());
			noti.setTo(event.getPending());
			notificationRepo.save(noti);
		}
		eventRepo.save(event);
	}
    
    public void delByTitle(String title) {
    	eventRepo.deleteAllByTitle(title);
    }
    
    public void delAll(List<Event> list) {
    	eventRepo.deleteAll(list);
    }
    
    public void delByID(String id) {
    	Event dat = eventRepo.findById(id).get();
    	if(dat.getUsers()==null || dat.getUsers().size() == 1) {
    		eventRepo.deleteById(id);
    		return;
    	}
    	dat.delUsers(UserConfig.getUsername());
    	eventRepo.save(dat);
    }
}
