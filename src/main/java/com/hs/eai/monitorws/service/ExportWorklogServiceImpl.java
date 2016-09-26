package com.hs.eai.monitorws.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.hs.eai.monitorws.controller.ProjectPanningsController;
import com.hs.eai.monitorws.model.Employee;
import com.hs.eai.monitorws.model.GenericResponse;
import com.hs.eai.monitorws.model.WeeklyReturn;
import com.hs.eai.monitorws.model.WorklogDto;

@Service
public class ExportWorklogServiceImpl implements ExportWorklogService {

	@Autowired
	private Environment env;

	public static final String DEFAULT_REPORTING_ACTIVITY = "defaultReportingActivity";
	private static final String REST_URI_ALL_EMPLOYEES = "restUriGetAllEmployeesFromAccesDB";
	private static final String REST_URI_ALL_WORKLOGS_BY_DATE_BETWEEN = "restUriAllWorkLogsByDateBetween";
	private static final String REST_URI_INSERT_LIST_WEEKLYRETURN = "restUriInsertBatchListWeeklyReturn";
	private List<Employee> employees;
	private static final Logger logger = LoggerFactory.getLogger(ProjectPanningsController.class);

	@Override
	public List<WeeklyReturn> WorklogDtoToWeeklyReturn(List<WorklogDto> worklogs) {

		employees = findAllEmployeeInAcces();
		
		List<WeeklyReturn> weeklyReturns = new ArrayList<WeeklyReturn>();

		for (WorklogDto worklog :worklogs) {
			WeeklyReturn output = new WeeklyReturn();
			output.setEmployee(findEmployeeBywindowsUser(worklog.getAuthor()).getId());
			output.setProject(worklog.getProject());
			output.setActivity(worklog.getActivity());
			output.setDate(jiraDateToAccesDBDateFormate(worklog.getUpdated()));
			output.setHours(jiraHoursToAccesHours(worklog.getTimeworked()));
			output.setDescription(worklog.getDescription());
			output.setDeletionFlag(null);
			output.setReportingActivity(defaultReportingActivity());
			weeklyReturns.add(output);
		}
		return weeklyReturns;
	}

	public List<Employee> findAllEmployeeInAcces() {

		List<Employee> employees = null;
		try {
			RestTemplate restTemplate = new RestTemplate();
			String restUriGetAllEmployeesFromAccesDB = env.getRequiredProperty(REST_URI_ALL_EMPLOYEES);
			ResponseEntity<List<Employee>> employeesResponse = restTemplate.exchange(restUriGetAllEmployeesFromAccesDB,
					HttpMethod.GET, null, new ParameterizedTypeReference<List<Employee>>() {
					});
			employees = employeesResponse.getBody();
		} catch (Exception ex) {
			logger.error("Error occurs when getting employes, reason {}", ex.getMessage());
			ex.printStackTrace();
		}
		return employees;
	}

	private Employee findEmployeeBywindowsUser(String username) {
		for (int i = 0; i < employees.size(); i++) {
			
			Employee employee = employees.get(i);
			if (employee.getWindowsUser()!=null && employee.getWindowsUser().equals(username)) {
				return employee;
			}
		}
		return null;
	}

	@Override
	public GenericResponse ExportToAccesDB(String fromDate, String toDate) {

		GenericResponse genericResp = null;
		String message = null;
		List<WorklogDto> worklogs = importWorkLogs(fromDate, toDate);
		
		if(worklogs == null || worklogs.isEmpty() || worklogs.size()==0){
			message = "No worklogs to export, reason : no worklogs between dates: "+fromDate+ " and "+toDate+" .";
			genericResp = new GenericResponse();
			genericResp.setStatus(HttpStatus.BAD_REQUEST);
			genericResp.setMessage(message);
			genericResp.setObject(null);
		}else{
			try {
				RestTemplate restTemplate = new RestTemplate();
				String restUriInsertBatchListWeeklyReturn = env.getRequiredProperty(REST_URI_INSERT_LIST_WEEKLYRETURN);

				List<WeeklyReturn> weeklyReturns = WorklogDtoToWeeklyReturn(worklogs);
				for (WeeklyReturn wk :weeklyReturns){
					System.out.println(wk.toString());
				}
				HttpEntity<List<WeeklyReturn>> request = new HttpEntity<>(weeklyReturns);

				ResponseEntity<GenericResponse> worklogsResponse = restTemplate.exchange(restUriInsertBatchListWeeklyReturn,
						HttpMethod.POST, request, GenericResponse.class);
				genericResp = worklogsResponse.getBody();

			} catch (Exception ex) {
				logger.error("Errors occurs where exporting Worklogs to Acces DB , reason: {}", ex.getMessage());
				ex.printStackTrace();
			}
		}
		
		

		return genericResp;
	}

	@Override
	public List<WorklogDto> importWorkLogs(String fromDate, String toDate) {
		List<WorklogDto> AllWorkLogsByDateBetween = null;
		try {
			RestTemplate restTemplate = new RestTemplate();
			String restUriAllWorkLogsByDateBetween = env.getRequiredProperty(REST_URI_ALL_WORKLOGS_BY_DATE_BETWEEN);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("fromDate", fromDate);
			params.put("toDate", toDate);
			
			ResponseEntity<List<WorklogDto>> worklogsResponse = restTemplate.exchange(restUriAllWorkLogsByDateBetween,
					HttpMethod.GET, null, new ParameterizedTypeReference<List<WorklogDto>>() {
					}, params);

			AllWorkLogsByDateBetween = worklogsResponse.getBody();
		} catch (Exception ex) {
			logger.error("Errors occurs where importing Worklogs from jira , reason: {}", ex.getMessage());
			ex.printStackTrace();
		}
		return AllWorkLogsByDateBetween;
	}

	private java.sql.Date jiraDateToAccesDBDateFormate(java.util.Date javaDate) {

		
		java.sql.Date sqlDate = null;
		try {
			// javaDate = jiraDateFormat.parse(sdate);
			sqlDate = new java.sql.Date(javaDate.getTime());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return sqlDate;
	}

	private Double jiraHoursToAccesHours(Integer JiraHours) {

		double hours = 0.0;
		try {
			double jirahours = (double)(JiraHours);
			hours = (jirahours / 3600);
		} catch (Exception ex) {
			System.out.println("Faild to connvert jira hours to Acces hours, reason: " + ex.getMessage());
			ex.printStackTrace();
		}
		return hours;
	}
	
	private Integer defaultReportingActivity(){
		
		Integer reportingActivity = null ;
		String repActivity = null;
		try{
		    repActivity = env.getRequiredProperty(DEFAULT_REPORTING_ACTIVITY);
			if(repActivity == null || repActivity.isEmpty()){
				throw new Exception("No default reporting activity found in the property file!");
			}
			reportingActivity = Integer.parseInt(env.getRequiredProperty(DEFAULT_REPORTING_ACTIVITY));
		}catch(Exception ex){
			logger.error("error when converting reporting activity {}",repActivity," To Integer");
		}
		return reportingActivity;
		
	}

}
