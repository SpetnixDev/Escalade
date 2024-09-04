package com.escalade.model;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class Comment {
	private int id;
	private int userId;
	private int siteId;
	private String content;
	private Date date;
	private User user;
	
	public Comment(int id, int userId, int siteId, String content, Date date) {
		this.id = id;
		this.userId = userId;
		this.siteId = siteId;
		this.content = content;
		this.date = date;
	}

	public int getId() {
		return id;
	}

	public int getUserId() {
		return userId;
	}

	public int getSiteId() {
		return siteId;
	}

	public String getContent() {
		return content;
	}

	public String getDate() {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		String formattedDate = format.format(date);
		
		return formattedDate;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
}
