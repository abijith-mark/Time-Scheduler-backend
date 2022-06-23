package com.mark.waif.Event;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "event") 
public class Event
{ 	
	@Id
    private String id;
	
	@Field(type = FieldType.Text, name="users")
	private List<String> users;

	@Field(type = FieldType.Text, name = "title")
    private String title;
    
    @Field(type = FieldType.Text, name = "desc")
    private String desc;

    @Field(type = FieldType.Date, name = "start")
    private Date start;

    @Field(type = FieldType.Date, name = "end")
    private Date end;

	@Field(type = FieldType.Long, name = "status")
    private Integer status;

    @Field(type = FieldType.Text, name = "pending")
    private List<String> pending;
    
    @Field(type = FieldType.Long, name="remind")
    private Integer remind;

	public List<String> getUsers() {
		return users;
	}

	public void setUsers(List<String> username) {
		this.users = username;
	}
    
    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public List<String> getPending() {
		return pending;
	}

	public void setPending(List<String> pending) {
		this.pending = pending;
	}
	
    public Integer getRemind() {
		return remind;
	}

	public void setRemind(Integer remind) {
		this.remind = remind;
	}
	
	// Extra
	
	public void addUsers(String username) {
		this.users.add(username);
	}
	
	public void delUsers(String username) {
		this.users.remove(username);
	}
	
	public void addToPending(String username) {
		this.pending.add(username);
	}
	
	public void delFromPending(String username) {
		this.pending.remove(username);
	}
}
