package com.hs.eai.monitorws.service;

import java.util.List;

import com.hs.eai.monitorws.model.Employee;
import com.hs.eai.monitorws.model.GenericResponse;
import com.hs.eai.monitorws.model.WeeklyReturn;
import com.hs.eai.monitorws.model.WorklogDto;


public interface ExportWorklogService {

	//input
	List<WorklogDto> importWorkLogs(String fromDate, String toDate);
	//Mapping
	List<Employee> findAllEmployeeInAcces();
	List<WeeklyReturn> WorklogDtoToWeeklyReturn(List<WorklogDto> worklogs);
	//output total exported
	public GenericResponse ExportToAccesDB(String fromDate, String toDate);
}
