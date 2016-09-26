package com.hs.eai.monitorws.service;

import java.io.File;
import java.util.List;

import com.hs.eai.monitorws.model.FileMeta;
import com.hs.eai.monitorws.model.ProjectsPlanning;

public interface ProjectsPlanningService {

	ProjectsPlanning findById(Integer Id);
	ProjectsPlanning findByIssue(String jiraIssue);
	List<ProjectsPlanning> findAll();
	List<ProjectsPlanning> findByAssignee(String assignee);
	List<ProjectsPlanning> findByAssignees(String[] assignees);
	List<ProjectsPlanning> findByfindByWeekNumber(String weekNumber);
	
	List<ProjectsPlanning> findByAssigneesAndWeekNumber(String[] assignees , String weekNumber);
	//Crud
	void SaveProjectsPlanning(ProjectsPlanning projectsPlanning);
	void updateProjectsPlanning(ProjectsPlanning projectsPlanning);
	void deleteProjectsPlanning(ProjectsPlanning projectsPlanning);
	
	
	
	boolean persistBatchInsert(List<ProjectsPlanning> ProjectsPlannings);
	List<ProjectsPlanning> readFromFile(FileMeta importFile);
}
