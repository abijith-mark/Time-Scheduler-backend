package com.mark.waif.notification;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName="noti")
public class Notification {
	
	public Notification(String from, List<String> to, String eventId, String message) {
		super();
		this.from = from;
		this.to = to;
		this.eventId = eventId;
		this.message = message;
	}

	@Id
	private String id;
	
	@Field(type = FieldType.Text, name = "from")
	private String from;
	
	@Field(type = FieldType.Text, name = "to")
	private List<String> to;
	
	@Field(type = FieldType.Text, name = "eventId")
	private String eventId;
	
	@Field(type = FieldType.Text, name = "message")
	private String message;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public List<String> getTo() {
		return to;
	}

	public void setTo(List<String> to) {
		this.to = to;
	}

	public String getEventId() {
		return eventId;
	}

	public void setEvent_id(String eventId) {
		this.eventId = eventId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void delFromTo(String username) {
		this.to.remove(username);
	}
}
