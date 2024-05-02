package com.escalade.model;

import java.sql.Date;

public class Comment {
	private int id;
	private int userId;
	private int siteId;
	private String content;
	private Date date;
	
	public Comment(int id, int userId, int siteId, String content, Date date) {
		super();
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

	public Date getDate() {
		return date;
	}
}
