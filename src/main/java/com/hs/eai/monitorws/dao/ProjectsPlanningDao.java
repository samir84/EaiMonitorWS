package com.hs.eai.monitorws.dao;

import java.util.Collection;
import java.util.List;

import com.hs.eai.monitorws.model.ProjectsPlanning;

public interface ProjectsPlanningDao {

	//
	ProjectsPlanning findById(Integer id);
	ProjectsPlanning findByIssue(String jiraIssue);
	List<ProjectsPlanning> findAll();
	List<ProjectsPlanning> findByAssignee(String assignee);
	List<ProjectsPlanning> findByAssignees(String[] assignees);
	List<ProjectsPlanning> findByfindByWeekNumber(String weekNumber);
	List<ProjectsPlanning> findByAssigneeAndWeekNumber(String[] assignees , String weekNumber);
	
	//Crud
	
	void SaveProjectsPlanning(ProjectsPlanning projectsPlanning);
	void updateProjectsPlanning(ProjectsPlanning projectsPlanning);
	void deleteProjectsPlanning(ProjectsPlanning projectsPlanning);
	
	
	
	boolean persistBatchInsert(List<ProjectsPlanning> ProjectsPlannings);
	
	
	
}
