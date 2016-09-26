package com.hs.eai.monitorws.model;

import java.io.Serializable;
import java.util.Date;

public class WorklogDto implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6469931418570278943L;
	private long id;
	private String author;
	private int issueid;
	private int project;
	private int activity;
	private Date  updated;
	private Integer timeworked;
	private String description;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public int getIssueid() {
		return issueid;
	}
	public void setIssueid(int issueid) {
		this.issueid = issueid;
	}
	public int getProject() {
		return project;
	}
	public void setProject(int project) {
		this.project = project;
	}
	public int getActivity() {
		return activity;
	}
	public void setActivity(int activity) {
		this.activity = activity;
	}
	public Date getUpdated() {
		return updated;
	}
	public void setUpdated(Date updated) {
		this.updated = updated;
	}
	public Integer getTimeworked() {
		return timeworked;
	}
	public void setTimeworked(Integer timeworked) {
		this.timeworked = timeworked;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
	
}
