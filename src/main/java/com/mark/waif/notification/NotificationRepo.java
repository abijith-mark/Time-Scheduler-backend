package com.mark.waif.notification;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface NotificationRepo extends ElasticsearchRepository<Notification,String>{
	List<Notification> findAllByTo(String to);
	Notification findByEventId(String eventId);
}
