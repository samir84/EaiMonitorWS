package com.hs.eai.monitorws;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;

import com.hs.eai.monitorws.dao.ProjectsPlanningDao;
import com.hs.eai.monitorws.model.FileMeta;
import com.hs.eai.monitorws.model.ProjectsPlanning;

import com.hs.eai.monitorws.service.FileMetaService;
import com.hs.eai.monitorws.service.ProjectsPlanningService;
import com.hs.eai.monitorws.spring.AppConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class, loader = AnnotationConfigContextLoader.class)
@WebAppConfiguration
public class TestPoi {


	@Autowired
	Environment env;
	@Autowired
	ProjectsPlanningService ProjectsPlanningService;
	@Autowired
	FileMetaService FileMetaService;
	
	private static final String FILE_IMPORT_PROJECTS_PLANNINGS_EXPECTED_HEADER = "fileImportProjectsPlanningsExpectedHeaderColumns";
	private static final String WEEK_REGEX = "^((W|w)[0-9]{2})$";
	//
	@Test
	public void insertBatch() throws Exception{
		
		try{
			FileMeta importFile = new FileMeta();
			Path path = Paths.get("C:\\Users\\samir.elazzouzi\\Downloads\\Kopie van EAI Project overview.xlsm");
			byte[] data = Files.readAllBytes(path);
			importFile.setContent(data);
			List<ProjectsPlanning> projectsPlannings =  ProjectsPlanningService.readFromFile(importFile);
			System.out.println("projectsPlannings readed: "+projectsPlannings.size());
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}




}
