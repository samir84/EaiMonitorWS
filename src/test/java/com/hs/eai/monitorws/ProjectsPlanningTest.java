package com.hs.eai.monitorws;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;

import com.hs.eai.monitorws.model.ProjectsPlanning;
import com.hs.eai.monitorws.service.ProjectsPlanningService;
import com.hs.eai.monitorws.spring.AppConfig;

import junit.framework.Assert;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class, loader = AnnotationConfigContextLoader.class)
@WebAppConfiguration
public class ProjectsPlanningTest {

	@Autowired
	ProjectsPlanningService projectsPlanningtService;
	
	@Test
	public void saveProjectsPlanning(){
		
		
		ProjectsPlanning projectsPlanning = new ProjectsPlanning();
		projectsPlanning.setProjectName("projectName");
		projectsPlanning.setActivity("activity");
		projectsPlanning.setAssignee("samir.elazzouzi");
		projectsPlanning.setDescription("description");
		projectsPlanning.setJiraIssue("jiraIssue");
		projectsPlanning.setProgress("progress");
		projectsPlanning.setRemark("remark");
		projectsPlanning.setWeek("W34");
		
		projectsPlanningtService.SaveProjectsPlanning(projectsPlanning);
		
	}
	@Test
	public void findById(){
		ProjectsPlanning projectsPlanning = new ProjectsPlanning();
		
		projectsPlanning.setId(1);
		projectsPlanning.setProjectName("projectName");
		projectsPlanning.setActivity("activity");
		projectsPlanning.setAssignee("samir.elazzouzi");
		projectsPlanning.setDescription("description");
		projectsPlanning.setJiraIssue("jiraIssue");
		projectsPlanning.setProgress("progress");
		projectsPlanning.setRemark("remark");
		projectsPlanning.setWeek("W34");
	    
		String assignee = projectsPlanningtService.findById(projectsPlanning.getId()).getAssignee();
		
		Assert.assertEquals(projectsPlanning.getActivity(), projectsPlanningtService.findById(projectsPlanning.getId()).getActivity());
	}
	
	
}
