package com.hs.eai.monitorws.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hs.eai.monitorws.model.GenericResponse;
import com.hs.eai.monitorws.model.WorklogDto;
import com.hs.eai.monitorws.service.ExportWorklogService;

@RestController
public class ExportWorkLogController {

	private static final Logger logger = LoggerFactory.getLogger(ExportWorkLogController.class);

	@Autowired
	ExportWorklogService exportWorklogService;

	@RequestMapping(value = "/ExportWorkLog/fromDate/{fromDate}/toDate/{toDate}/", method = RequestMethod.GET , produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponse exportWorkLog(@PathVariable("fromDate") String fromDate,
			@PathVariable("toDate") String toDate) {

		GenericResponse genericResponse = null;

		try {
			genericResponse = exportWorklogService.ExportToAccesDB(fromDate, toDate);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return genericResponse;

	}
	@RequestMapping(value = "/ImpotWorkLog/fromDate/{fromDate}/toDate/{toDate}/", method = RequestMethod.GET , produces = MediaType.APPLICATION_JSON_VALUE)
	public List<WorklogDto> importWorkLog(@PathVariable("fromDate") String fromDate,
			@PathVariable("toDate") String toDate) {

		List<WorklogDto> worklogs = null;

		try {
			worklogs = exportWorklogService.importWorkLogs(fromDate, toDate);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return worklogs;

	}
}
