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
import com.hs.eai.monitorws.service.FileMetaService;

/**
 * 
 * @author samir.elazzouzi
 * @param request : MultipartHttpServletRequest auto passed
 * @return LinkedList<FileMeta> as json format
 */

@RestController
public class FileMetaController {

private static final Logger logger = LoggerFactory.getLogger(FileMetaController.class);
	
	@Autowired
	FileMetaService FileMetaService;
	
	/**
	 * Retrieve All FileMeta
	 * 
	 * @return
	 * 
	 */
	@RequestMapping(value = "/FileMeta/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<FileMeta>> listAllFileMeta() {

		List<FileMeta> FileMetasList = null;
		try {
			FileMetasList = FileMetaService.findAllFiles();
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			ex.printStackTrace();
		}

		if (FileMetasList.isEmpty()) {
			return new ResponseEntity<List<FileMeta>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<FileMeta>>(FileMetasList, HttpStatus.OK);
	}
	
	/**
	 * Retrieve All FileMeta by owner
	 * 
	 * @return
	 */
	@RequestMapping(value = "/FileMeta/{owner}/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<FileMeta>> listAllFileMetaByAssignee(@PathVariable String owner) {

		List<FileMeta> FileMetasList = null;
		try {
			FileMetasList = FileMetaService.findAllFilesByOwner(owner);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			ex.printStackTrace();
		}

		if (FileMetasList == null) {
			return new ResponseEntity<List<FileMeta>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<FileMeta>>(FileMetasList, HttpStatus.OK);
	}
	@RequestMapping(value = "/FileMeta/save/", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GenericResponse> addFileMeta(@RequestBody FileMeta fileMeta) {
		
		GenericResponse genericResp = null;
		String message = null;
		
		try{
			FileMeta oldFileMeta = FileMetaService.findByFilepath(fileMeta.getFilepath());
			
			if(oldFileMeta == null){
				
				FileMetaService.saveFile(fileMeta);
				message = "SUCCES";
				genericResp = new GenericResponse();
				genericResp.setStatus(HttpStatus.OK);
				genericResp.setMessage(message);
				genericResp.setObject(null);
				
			}else{
				message = "File already exist!";
				genericResp = new GenericResponse();
				genericResp.setStatus(HttpStatus.CONFLICT);
				genericResp.setMessage(message);
				genericResp.setObject(oldFileMeta);
				logger.error(message.toString());
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
	@RequestMapping(value = "/FileMeta/update/", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GenericResponse> updateFileMeta(@RequestBody FileMeta fileMeta){
		
		GenericResponse genericResp = null;
		String message = null;
		try{
			FileMeta oldFileMeta = FileMetaService.findByFilepath(fileMeta.getFilepath());
			
			if(oldFileMeta == null){
				message = "File does not exist!";
				genericResp = new GenericResponse();
				genericResp.setStatus(HttpStatus.CONFLICT);
				genericResp.setMessage(message);
				genericResp.setObject(oldFileMeta);
				logger.error(message.toString());
			}else{
				//oldFileMeta
				//FileMeta.setId(oldFileMeta.getId());
				FileMetaService.updateFile(fileMeta);
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
	@RequestMapping(value = "/FileMeta/delete/", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GenericResponse> deleteFileMeta(@RequestBody FileMeta fileMeta){
		
		GenericResponse genericResp = null;
		String message = null;
		try{
			FileMeta oldFileMeta = FileMetaService.findByFilepath(fileMeta.getFilepath());
			
			if(oldFileMeta == null){
				message = "File does not exist!";
				genericResp = new GenericResponse();
				genericResp.setStatus(HttpStatus.CONFLICT);
				genericResp.setMessage(message);
				genericResp.setObject(oldFileMeta);
				logger.error(message.toString());
			}else{
				FileMetaService.deleteFile(fileMeta);
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
}
