package com.hs.eai.monitorws.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
@Entity
@Table(name="project_palanning")
@NamedQueries({
	@NamedQuery(name = "ProjectsPlanning.findAll", query = "select pl from ProjectsPlanning pl "),
	@NamedQuery(name = "ProjectsPlanning.findById", query = "select pl from ProjectsPlanning pl where pl.id=:id"),
	@NamedQuery(name = "ProjectsPlanning.findByIssue", query = "select pl from ProjectsPlanning pl where pl.jiraIssue=:jiraIssue"),
	@NamedQuery(name = "ProjectsPlanning.findByAssignee", query = "select pl from ProjectsPlanning pl where pl.assignee=:assignee "),
	@NamedQuery(name = "ProjectsPlanning.findByAssignees", query = "select pl from ProjectsPlanning pl where pl.assignee in (:assignees) "),
	@NamedQuery(name = "ProjectsPlanning.findByweek", query = "select pl from ProjectsPlanning pl where pl.week=:week"),
	@NamedQuery(name = "ProjectsPlanning.findByAssigneeAndweek", query = "select pl from ProjectsPlanning pl where pl.assignee=:assignee and pl.week=:week")
	
})
public class ProjectsPlanning implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private Integer id;
	@Column(name="jira_issue")
	private String jiraIssue;
	@Column(name="jira_project_id")
	private String projectName;
	@Column(name="jira_activity_id")
	private String activity;
	@Column(name="assignee")
	private String assignee;
	private String description;
	private String remark;
	private String progress;
	private String week;
	@Column(name="excpected_hours")
	private String excpectedHours;
	
	@Column(name="priority")
	private String priority;
	
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public String getExcpectedHours() {
		return excpectedHours;
	}
	public void setExcpectedHours(String excpectedHours) {
		this.excpectedHours = excpectedHours;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getJiraIssue() {
		return jiraIssue;
	}
	public void setJiraIssue(String jiraIssue) {
		this.jiraIssue = jiraIssue;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getActivity() {
		return activity;
	}
	public void setActivity(String activity) {
		this.activity = activity;
	}
	public String getAssignee() {
		return assignee;
	}
	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getProgress() {
		return progress;
	}
	public void setProgress(String progress) {
		this.progress = progress;
	}
	public String getWeek() {
		return week;
	}
	public void setWeek(String week) {
		this.week = week;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((activity == null) ? 0 : activity.hashCode());
		result = prime * result + ((assignee == null) ? 0 : assignee.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((excpectedHours == null) ? 0 : excpectedHours.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((jiraIssue == null) ? 0 : jiraIssue.hashCode());
		result = prime * result + ((progress == null) ? 0 : progress.hashCode());
		result = prime * result + ((projectName == null) ? 0 : projectName.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result + ((week == null) ? 0 : week.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProjectsPlanning other = (ProjectsPlanning) obj;
		if (activity == null) {
			if (other.activity != null)
				return false;
		} else if (!activity.equals(other.activity))
			return false;
		if (assignee == null) {
			if (other.assignee != null)
				return false;
		} else if (!assignee.equals(other.assignee))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (excpectedHours == null) {
			if (other.excpectedHours != null)
				return false;
		} else if (!excpectedHours.equals(other.excpectedHours))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (jiraIssue == null) {
			if (other.jiraIssue != null)
				return false;
		} else if (!jiraIssue.equals(other.jiraIssue))
			return false;
		if (progress == null) {
			if (other.progress != null)
				return false;
		} else if (!progress.equals(other.progress))
			return false;
		if (projectName == null) {
			if (other.projectName != null)
				return false;
		} else if (!projectName.equals(other.projectName))
			return false;
		if (remark == null) {
			if (other.remark != null)
				return false;
		} else if (!remark.equals(other.remark))
			return false;
		if (week == null) {
			if (other.week != null)
				return false;
		} else if (!week.equals(other.week))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "ProjectsPlanning [id=" + id + ", jiraIssue=" + jiraIssue + ", projectName=" + projectName
				+ ", activity=" + activity + ", assignee=" + assignee + ", description=" + description + ", remark="
				+ remark + ", progress=" + progress + ", week=" + week + ", excpectedHours=" + excpectedHours + "]";
	}
	
	
}
