package com.hs.eai.monitorws.service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.hs.eai.monitorws.controller.ProjectPanningsController;
import com.hs.eai.monitorws.dao.ProjectsPlanningDao;
import com.hs.eai.monitorws.model.FileMeta;
import com.hs.eai.monitorws.model.ProjectsPlanning;

@Service
@Transactional
public class ProjectsPlanningServiceImpl implements ProjectsPlanningService {

	private static final Logger logger = LoggerFactory.getLogger(ProjectsPlanningServiceImpl.class);

	private static final String FILE_IMPORT_PROJECTS_PLANNINGS_EXPECTED_HEADER = "fileImportProjectsPlanningsExpectedHeaderColumns";
	private static final String WEEK_REGEX = "^((W|w)[0-9]{2})$";

	@Autowired
	Environment env;
	@Autowired
	ProjectsPlanningDao projectsPlanningDao;

	@Override
	public List<ProjectsPlanning> findAll() {
		// TODO Auto-generated method stub
		return projectsPlanningDao.findAll();
	}

	@Override
	public List<ProjectsPlanning> findByAssignees(String[] assignees) {
		// TODO Auto-generated method stub
		return projectsPlanningDao.findByAssignees(assignees);
	}

	@Override
	public List<ProjectsPlanning> findByfindByWeekNumber(String weekNumber) {
		// TODO Auto-generated method stub
		return projectsPlanningDao.findByfindByWeekNumber(weekNumber);
	}

	@Override
	public List<ProjectsPlanning> findByAssigneesAndWeekNumber(String[] assignees, String weekNumber) {
		// TODO Auto-generated method stub
		return projectsPlanningDao.findByAssigneeAndWeekNumber(assignees, weekNumber);
	}

	@Override
	public void SaveProjectsPlanning(ProjectsPlanning projectsPlanning) {

		projectsPlanningDao.SaveProjectsPlanning(projectsPlanning);

	}

	@Override
	public void updateProjectsPlanning(ProjectsPlanning projectsPlanning) {
		projectsPlanningDao.updateProjectsPlanning(projectsPlanning);

	}

	@Override
	public void deleteProjectsPlanning(ProjectsPlanning projectsPlanning) {
		projectsPlanningDao.deleteProjectsPlanning(projectsPlanning);

	}

	@Override
	public ProjectsPlanning findById(Integer Id) {
		// TODO Auto-generated method stub
		return projectsPlanningDao.findById(Id);
	}

	@Override
	public List<ProjectsPlanning> findByAssignee(String assignee) {
		// TODO Auto-generated method stub
		return projectsPlanningDao.findByAssignee(assignee);
	}

	@Override
	public ProjectsPlanning findByIssue(String jiraIssue) {
		// TODO Auto-generated method stub
		return projectsPlanningDao.findByIssue(jiraIssue);
	}

	@Override
	public boolean persistBatchInsert(List<ProjectsPlanning> ProjectsPlannings) {
		// TODO Auto-generated method stub
		return projectsPlanningDao.persistBatchInsert(ProjectsPlannings);
	}

	@Override
	public List<ProjectsPlanning> readFromFile(FileMeta importFile) {

		List<ProjectsPlanning> plannings = new ArrayList<ProjectsPlanning>();

		try {
			InputStream is = new ByteArrayInputStream(importFile.getContent());
			// FileInputStream file = new FileInputStream(new
			// File(importFile.getFilepath() + importFile.getFilename()));
			// Get the workbook instance for XLS file
			XSSFWorkbook workbook = new XSSFWorkbook(is);
			for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
				// Get first sheet from the workbook
				XSSFSheet sheet = workbook.getSheetAt(i);
				try {
					if (validateSheet(sheet, importFile.getFileType())) {

						// Iterate through each rows from first sheet
						Iterator<Row> rowIterator = sheet.iterator();
						while (rowIterator.hasNext()) {
							Row row = rowIterator.next();
							plannings.addAll(createPlanning(row));
						}

						is.close();
					}

				} catch (IOException e) {
					e.printStackTrace();
				}

			}

		} catch (Exception ex) {
			logger.error(ex.getMessage());
			ex.printStackTrace();
		}

		return plannings;
	}

	public List<ProjectsPlanning> createPlanning(Row row) {

		List<ProjectsPlanning> plannings = new ArrayList<>();
		List<String> weeksColumns = getWeeksColumns(row.getSheet());

		String[] expectedColumnNames = env.getRequiredProperty(FILE_IMPORT_PROJECTS_PLANNINGS_EXPECTED_HEADER)
				.split(",");

		for (int i = 0; i < weeksColumns.size(); i++) {
			if (!checkIfRowIsEmpty(row) && !row.getCell(0).toString().equalsIgnoreCase(expectedColumnNames[0])) {

				ProjectsPlanning planning = new ProjectsPlanning();
				/*
				 * if(firstRow.getCell(i).getCellType() ==
				 * Cell.CELL_TYPE_STRING){
				 * 
				 * }
				 */
				planning.setJiraIssue(CellValueToString(row.getCell(0)));
				planning.setProjectName( CellValueToString(row.getCell(1)));
				planning.setActivity(CellValueToString(row.getCell(2)));
				planning.setAssignee(CellValueToString(row.getCell(3)));
				planning.setDescription(CellValueToString(row.getCell(4)));
				planning.setRemark(CellValueToString(row.getCell(5)));
				planning.setProgress(CellValueToString(row.getCell(6)));
				planning.setWeek(weeksColumns.get(i));
				planning.setExcpectedHours(CellValueToString(row.getCell(7 + i)));
				planning.setPriority(CellValueToString(row.getCell(8 + i)));
				plannings.add(planning);
			}
		}

		return plannings;

	}

	public String CellValueToString(Cell cell) {

		String value = "";

		if(cell == null){
			System.out.println("cell is null!");
			return value;
		}
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_BOOLEAN:
			//System.out.print(cell.getBooleanCellValue() + "\t\t");
			break;
		case Cell.CELL_TYPE_NUMERIC:
			//System.out.print(cell.getNumericCellValue() + "\t\t");
			break;
		case Cell.CELL_TYPE_STRING:
			 //if(cell)
			//System.out.print(cell + "\t\t");
			value = cell.getStringCellValue();
			break;
		}
		//System.out.println("value:"+value+"*");
		return value;
	}

	public List<String> getWeeksColumns(Sheet sheet) {

		List<String> weeksColumns = new ArrayList<String>();
		int noOfColumns = sheet.getRow(0).getLastCellNum();
		Row firstRow = sheet.getRow(0);

		for (int i = 0; i < noOfColumns; i++) {

			if (firstRow.getCell(i).getCellType() == Cell.CELL_TYPE_STRING
					&& firstRow.getCell(i).getStringCellValue().matches(WEEK_REGEX)) {
				weeksColumns.add(firstRow.getCell(i).getStringCellValue());
			}
		}

		return weeksColumns;
	}

	public boolean validateSheet(Sheet sheet, String type) throws Exception {

		String[] expectedColumnNames = env.getRequiredProperty(FILE_IMPORT_PROJECTS_PLANNINGS_EXPECTED_HEADER)
				.split(",");

		if (expectedColumnNames.length < 1) {
			throw new Exception("Missing property: " + FILE_IMPORT_PROJECTS_PLANNINGS_EXPECTED_HEADER);
		}

		StringBuilder errorString = new StringBuilder();
		int noOfColumns = sheet.getRow(0).getLastCellNum();
		if (noOfColumns != expectedColumnNames.length) {
			errorString.append("Sheet contains more than excpected cells: ").append("found: ").append(noOfColumns)
					.append(" but excpected:").append(expectedColumnNames.length);
			throw new Exception(errorString.toString());

		}

		if (!errorString.toString().isEmpty()) {
			throw new Exception(errorString.toString());
		} else {
			return true;
		}
	}

	private boolean checkIfRowIsEmpty(Row row) {
		if (row == null) {
			return true;
		}
		if (row.getLastCellNum() <= 0) {
			return true;
		}
		boolean isEmptyRow = true;
		for (int cellNum = row.getFirstCellNum(); cellNum < row.getLastCellNum(); cellNum++) {
			Cell cell = row.getCell(cellNum);
			if (cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK && !StringUtils.isEmpty(cell.toString())) {
				isEmptyRow = false;
			}
		}
		return isEmptyRow;
	}

}
