package com.hs.eai.monitorws.model;

import java.io.Serializable;
import java.sql.Date;

public class WeeklyReturn implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer employee;
	private Integer project;
	private Integer activity;
	private Date date;
	private Double hours;
	private String description;
	private String deletionFlag;
	private Integer reportingActivity;
		
    	
	public Integer getEmployee() {
		return employee;
	}

	public WeeklyReturn() {
		
	}

	public void setEmployee(Integer employee) {
		this.employee = employee;
	}

	public Integer getProject() {
		return project;
	}

	public void setProject(Integer project) {
		this.project = project;
	}

	public Integer getActivity() {
		return activity;
	}

	public void setActivity(Integer activity) {
		this.activity = activity;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Double getHours() {
		return hours;
	}

	public void setHours(Double hours) {
		this.hours = hours;
	}


	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDeletionFlag() {
		return deletionFlag;
	}

	public void setDeletionFlag(String deletionFlag) {
		this.deletionFlag = deletionFlag;
	}

	public Integer getReportingActivity() {
		return reportingActivity;
	}

	public void setReportingActivity(Integer reportingActivity) {
		this.reportingActivity = reportingActivity;
	}

	@Override
	public String toString() {
		return "WeeklyReturn [employee=" + employee + ", project=" + project + ", activity=" + activity + ", date="
				+ date + ", hours=" + hours + ", description=" + description + ", deletionFlag=" + deletionFlag
				+ ", reportingActivity=" + reportingActivity + "]";
	}

	

}
