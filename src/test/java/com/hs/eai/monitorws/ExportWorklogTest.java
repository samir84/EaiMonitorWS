package com.hs.eai.monitorws;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;

import com.hs.eai.monitorws.model.Employee;
import com.hs.eai.monitorws.model.GenericResponse;
import com.hs.eai.monitorws.model.WorklogDto;
import com.hs.eai.monitorws.service.ExportWorklogService;
import com.hs.eai.monitorws.spring.AppConfig;

import junit.framework.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class, loader = AnnotationConfigContextLoader.class)
@WebAppConfiguration
public class ExportWorklogTest {

	@Autowired
	ExportWorklogService exportWorklogService;
	
	@Test
	public void ImportWokrLogTest(){
		String dateFrom = "2016-06-01";
		String dateTo = "2016-06-08";
		List<WorklogDto> worklogs = exportWorklogService.importWorkLogs(dateFrom,dateTo);
		System.out.println(worklogs.size());//87
		Assert.assertEquals(87, worklogs.size());
	}
	@Test
	public void findAllEmployeeInAcces(){
		List<Employee> employees = exportWorklogService.findAllEmployeeInAcces();
		
		Assert.assertEquals(30, employees.size());
	}
	@Test
	public void ExportToAccesDB(){
		String dateFrom = "2016-06-02";
		String dateTo = "2016-06-03";
		
		GenericResponse genericRsp = exportWorklogService.ExportToAccesDB(dateFrom, dateTo);
	}
	
	@Test
	public void jiraHoursToAccesHours(){
		
		Integer jiraHours = 1800;
		double hours = jiraHoursToAccesHours(jiraHours);
		System.out.println("hours:"+hours);
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
}
