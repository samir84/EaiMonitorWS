package com.hs.eai.monitorws.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.hs.eai.monitorws.model.ProjectsPlanning;

@Repository
public class ProjectsPlanningDaoImpl implements ProjectsPlanningDao {

	@Value("${hibernate.jdbc.batch_size}")
	private int batchSize;
	
	@Autowired
	private SessionFactory sessionFactory;

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public List<ProjectsPlanning> findAll() {

		List<ProjectsPlanning> projectsPlanningList = null;
		try {

			Query query = getSession().getNamedQuery("ProjectsPlanning.findAll");
			projectsPlanningList = query.list();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return projectsPlanningList;
	}

	@Override
	public List<ProjectsPlanning> findByAssignee(String assignee) {

		List<ProjectsPlanning> projectsPlanningList = null;
		try {
			Query query = getSession().getNamedQuery("ProjectsPlanning.findByAssignee");
			query.setParameter("assignee", assignee);
			projectsPlanningList = query.list();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return projectsPlanningList;
	}

	@Override
	public List<ProjectsPlanning> findByAssignees(String[] assignees) {

		List<ProjectsPlanning> projectsPlanningList = null;
		try {
			Query query = getSession().getNamedQuery("ProjectsPlanning.findByAssignees");
			query.setParameter("assignees", assignees);
			projectsPlanningList = query.list();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return projectsPlanningList;
	}

	@Override
	public List<ProjectsPlanning> findByfindByWeekNumber(String weekNumber) {

		List<ProjectsPlanning> projectsPlanningList = null;
		try {
			Query query = getSession().getNamedQuery("ProjectsPlanning.findByWeekNumber");
			query.setParameter("weekNumber", weekNumber);
			projectsPlanningList = query.list();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return projectsPlanningList;
	}

	@Override
	public List<ProjectsPlanning> findByAssigneeAndWeekNumber(String[] assignees, String weekNumber) {
		List<ProjectsPlanning> projectsPlanningList = null;
		try {
			Query query = getSession().getNamedQuery("ProjectsPlanning.findByAssigneeAndWeekNumber");
			query.setParameter("assignees", assignees);
			query.setParameter("weekNumber", weekNumber);
			projectsPlanningList = query.list();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return projectsPlanningList;
	}

	@Override
	public void SaveProjectsPlanning(ProjectsPlanning projectsPlanning) {
		getSession().save(projectsPlanning);

	}

	@Override
	public void updateProjectsPlanning(ProjectsPlanning projectsPlanning) {
		getSession().saveOrUpdate(projectsPlanning);

	}

	@Override
	public void deleteProjectsPlanning(ProjectsPlanning projectsPlanning) {
		getSession().delete(projectsPlanning);

	}

	@Override
	public ProjectsPlanning findById(Integer id) {
		// TODO Auto-generated method stub
		return (ProjectsPlanning) getSession().get(ProjectsPlanning.class, id);
	}

	@Override
	public ProjectsPlanning findByIssue(String jiraIssue) {

		ProjectsPlanning projectsPlanning = null;
		try {
			Query query = getSession().getNamedQuery("ProjectsPlanning.findByIssue");
			query.setParameter("jiraIssue", jiraIssue);
			projectsPlanning = (ProjectsPlanning) query.list().get(0);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return projectsPlanning;
	}

	@Override
	public boolean persistBatchInsert(List<ProjectsPlanning> projectsPlannings) {

		final List<ProjectsPlanning> savedEntities = new ArrayList<ProjectsPlanning>();
		
		int i = 0;
		for (ProjectsPlanning pl : projectsPlannings) {
			
			SaveProjectsPlanning(pl);
			savedEntities.add(pl);
			i++;
			if (i % batchSize == 0) {
				// Flush a batch of inserts and release memory.
				getSession().flush();
				getSession().clear();
			}
		}

		
		return savedEntities.size() == projectsPlannings.size() ? true : false;
	}

}
