package com.hs.eai.monitorws.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hs.eai.monitorws.model.FileMeta;
import com.hs.eai.monitorws.model.GenericResponse;
import com.hs.eai.monitorws.model.ProjectsPlanning;
import com.hs.eai.monitorws.service.FileMetaService;
import com.hs.eai.monitorws.service.ProjectsPlanningService;

@RestController
public class ProjectPanningsController {

	private static final Logger logger = LoggerFactory.getLogger(ProjectPanningsController.class);
	
	@Autowired
	ProjectsPlanningService projectsPlanningService;
	@Autowired
	FileMetaService fileMetaService;
	/**
	 * Retrieve All ProjectsPlanning
	 * 
	 * @return
	 * 
	 */
	@RequestMapping(value = "/planning/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ProjectsPlanning>> listAllProjectsPlanning() {

		List<ProjectsPlanning> projectsPlanningsList = null;
		try {
			projectsPlanningsList = projectsPlanningService.findAll();
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			ex.printStackTrace();
		}

		if (projectsPlanningsList.isEmpty()) {
			return new ResponseEntity<List<ProjectsPlanning>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<ProjectsPlanning>>(projectsPlanningsList, HttpStatus.OK);
	}
	/**
	 * Retrieve All ProjectsPlanning by assignees
	 * 
	 * @return
	 */
	@RequestMapping(value = "/planning/{assignee}/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ProjectsPlanning>> listAllProjectsPlanningByAssignee(@PathVariable String assignee) {

		List<ProjectsPlanning> projectsPlanningsList = null;
		try {
			projectsPlanningsList = projectsPlanningService.findByAssignee(assignee);
		} catch (Exception sql) {
			logger.error(sql.getMessage());
			sql.printStackTrace();
		}

		if (projectsPlanningsList == null) {
			return new ResponseEntity<List<ProjectsPlanning>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<ProjectsPlanning>>(projectsPlanningsList, HttpStatus.OK);
	}

	/**
	 * Retrieve All ProjectsPlanning by assignees
	 * 
	 * @return
	 */
	@RequestMapping(value = "/planning/[{assignees}]/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ProjectsPlanning>> listAllProjectsPlanningByAssignees(@PathVariable String[] assignees) {

		List<ProjectsPlanning> projectsPlanningsList = null;
		try {
			projectsPlanningsList = projectsPlanningService.findByAssignees(assignees);
		} catch (Exception sql) {
			logger.error(sql.getMessage());
			sql.printStackTrace();
		}

		if (projectsPlanningsList == null) {
			return new ResponseEntity<List<ProjectsPlanning>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<ProjectsPlanning>>(projectsPlanningsList, HttpStatus.OK);
	}
	
	/**
	 * Retrieve All ProjectsPlanning by weekNumber
	 * 
	 * @return
	 */
	@RequestMapping(value = "/planning/{weekNumber}/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ProjectsPlanning>> listAllProjectsPlanningByWeekNumber(@PathVariable String weekNumber) {

		List<ProjectsPlanning> projectsPlanningsList = null;
		try {
			projectsPlanningsList = projectsPlanningService.findByfindByWeekNumber(weekNumber);
		} catch (Exception sql) {
			logger.error(sql.getMessage());
			sql.printStackTrace();
		}

		if (projectsPlanningsList == null) {
			return new ResponseEntity<List<ProjectsPlanning>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<ProjectsPlanning>>(projectsPlanningsList, HttpStatus.OK);
	}
	@RequestMapping(value = "/planning/[{assignees}]/{weekNumber}/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ProjectsPlanning>> listAllProjectsPlanningByAssigneeAndWeekNumber(@PathVariable String[] assignees,@PathVariable String weekNumber) {

		List<ProjectsPlanning> projectsPlanningsList = null;
		try {
			projectsPlanningsList = projectsPlanningService.findByAssigneesAndWeekNumber(assignees, weekNumber);
		} catch (Exception sql) {
			logger.error(sql.getMessage());
			sql.printStackTrace();
		}

		if (projectsPlanningsList == null) {
			return new ResponseEntity<List<ProjectsPlanning>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<ProjectsPlanning>>(projectsPlanningsList, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/planning/add/", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GenericResponse> addProjectsPlanning(@RequestBody ProjectsPlanning projectsPlanning) {

		GenericResponse genericResp = null;
		String message = null;
		try{
			ProjectsPlanning newProjectsPlanning = projectsPlanningService.findByIssue(projectsPlanning.getJiraIssue());
			
			if(newProjectsPlanning!= null){
				message = "Planning already exist!";
				genericResp = new GenericResponse();
				genericResp.setStatus(HttpStatus.CONFLICT);
				genericResp.setMessage(message);
				genericResp.setObject(newProjectsPlanning);
				logger.error(message.toString());
			}else{
				projectsPlanningService.SaveProjectsPlanning(projectsPlanning);
				message = "SUCCES";
				genericResp = new GenericResponse();
				genericResp.setStatus(HttpStatus.OK);
				genericResp.setMessage(message);
				genericResp.setObject(null);
			}
			
		}catch(Exception ex){
			logger.error(ex.getMessage());
			ex.printStackTrace();
			genericResp = new GenericResponse();
			genericResp.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			genericResp.setMessage(message);
			genericResp.setObject(null);
			ex.printStackTrace();
		}
		return new ResponseEntity<GenericResponse>(genericResp, genericResp.getStatus());
		
	
	}
	@RequestMapping(value = "/planning/update/", method = RequestMethod.POST)
	public ResponseEntity<GenericResponse> updateProjectsPlanning(@RequestBody ProjectsPlanning projectsPlanning) {
		
		GenericResponse genericResp = null;
		String message = null;
		try{
			ProjectsPlanning oldProjectsPlanning = projectsPlanningService.findByIssue(projectsPlanning.getJiraIssue());
			
			if(oldProjectsPlanning == null){
				message = "Planning does not exist!";
				genericResp = new GenericResponse();
				genericResp.setStatus(HttpStatus.CONFLICT);
				genericResp.setMessage(message);
				genericResp.setObject(oldProjectsPlanning);
				logger.error(message.toString());
			}else{
				//oldProjectsPlanning
				projectsPlanning.setId(oldProjectsPlanning.getId());
				projectsPlanningService.updateProjectsPlanning(projectsPlanning);
				message = "SUCCES";
				genericResp = new GenericResponse();
				genericResp.setStatus(HttpStatus.OK);
				genericResp.setMessage(message);
				genericResp.setObject(null);
			}
			
		}catch(Exception ex){
			logger.error(ex.getMessage());
			ex.printStackTrace();
			genericResp = new GenericResponse();
			genericResp.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			genericResp.setMessage(message);
			genericResp.setObject(null);
			ex.printStackTrace();
		}
		return new ResponseEntity<GenericResponse>(genericResp, genericResp.getStatus());
		
	}
	@RequestMapping(value = "/planning/delete/", method = RequestMethod.POST)
	public ResponseEntity<GenericResponse> deleteProjectsPlanning(@RequestBody ProjectsPlanning projectsPlanning) {
		
		GenericResponse genericResp = null;
		String message = null;
		try{
			ProjectsPlanning oldProjectsPlanning = projectsPlanningService.findByIssue(projectsPlanning.getJiraIssue());
			
			if(oldProjectsPlanning == null){
				message = "Planning does not exist!";
				genericResp = new GenericResponse();
				genericResp.setStatus(HttpStatus.CONFLICT);
				genericResp.setMessage(message);
				genericResp.setObject(oldProjectsPlanning);
				logger.error(message.toString());
			}else{
				projectsPlanningService.deleteProjectsPlanning(oldProjectsPlanning);
				message = "SUCCES";
				genericResp = new GenericResponse();
				genericResp.setStatus(HttpStatus.OK);
				genericResp.setMessage(message);
				genericResp.setObject(null);
			}
			
		}catch(Exception ex){
			logger.error(ex.getMessage());
			ex.printStackTrace();
			genericResp = new GenericResponse();
			genericResp.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			genericResp.setMessage(message);
			genericResp.setObject(null);
			ex.printStackTrace();
		}
		return new ResponseEntity<GenericResponse>(genericResp, genericResp.getStatus());
		
	}
	
	@RequestMapping(value = "/planning/import/", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GenericResponse> importProjectsPlanning(@RequestBody FileMeta fileMeta) {
		
		GenericResponse genericResp = null;
		String message = null;
		try{
			// first readFromFile
			List<ProjectsPlanning> plannings = projectsPlanningService.readFromFile(fileMeta);
			System.out.println("plannings:"+plannings);
			if(plannings == null){
				message = "Error occurd where reading Planning from file: "+fileMeta.getFilename();
				genericResp = new GenericResponse();
				genericResp.setStatus(HttpStatus.CONFLICT);
				genericResp.setMessage(message);
				genericResp.setObject(null);
				logger.error(message.toString());
			}else{
				boolean batchInsert = projectsPlanningService.persistBatchInsert(plannings);
				
				if(batchInsert){
					// save imported planning file to direction and save MetaFile entity
					boolean result = fileMetaService.saveFile(fileMeta);
					if(result){
						message = "SUCCES";
						genericResp = new GenericResponse();
						genericResp.setStatus(HttpStatus.OK);
						genericResp.setMessage(message);
						genericResp.setObject(null);
						logger.debug(message.toString());
					}else{
						message = "Error occurd where saving Planning file: "+fileMeta.getFilename();
						genericResp = new GenericResponse();
						genericResp.setStatus(HttpStatus.CONFLICT);
						genericResp.setMessage(message);
						genericResp.setObject(null);
						logger.debug(message.toString());
					}
						
					
					
				}else{
					message = "Error occurd where persisting persistBatchInsert!!";
					genericResp = new GenericResponse();
					genericResp.setStatus(HttpStatus.CONFLICT);
					genericResp.setMessage(message);
					genericResp.setObject(null);
					logger.error(message.toString());
				}
			}
			
			
			// read all planning from file
			
		}catch(Exception ex){
			logger.error(ex.getMessage());
			ex.printStackTrace();
			genericResp = new GenericResponse();
			genericResp.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			genericResp.setMessage(message);
			genericResp.setObject(null);
			ex.printStackTrace();
		}
		
		
		return new ResponseEntity<GenericResponse>(genericResp, genericResp.getStatus());
	}
	

}
