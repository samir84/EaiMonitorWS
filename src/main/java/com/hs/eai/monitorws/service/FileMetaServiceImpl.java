package com.hs.eai.monitorws.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.hs.eai.monitorws.dao.FileMetaDao;
import com.hs.eai.monitorws.model.FileMeta;

@Service
@Transactional
public class FileMetaServiceImpl implements FileMetaService{

	private static final String ROOT_PATH_TYPE_PROFILES = "rootPathProfiles";
	private static final String ROOT_PATH_TYPE_PROJECT_PLANNING = "rootPathProjectPlanning";
	private static final String PROJECT_OVERVIEW_IMPORT_FILE_TYPE="projectOverview";
	private static final String USER_PROFILE_IMPORT_FILE_TYPE="userProfile";
	
	private static final Logger logger = LoggerFactory.getLogger(FileMetaServiceImpl.class);
	
	@Autowired
	Environment env;
	@Autowired
	FileMetaDao FileMetaDao;
	
	@Override
	public List<FileMeta> findAllFiles() {
		// TODO Auto-generated method stub
		return FileMetaDao.findAllFiles();
	}

	@Override
	public List<FileMeta> findAllFilesByOwner(String owner) {
		// TODO Auto-generated method stub
		return FileMetaDao.findAllFilesByOwner(owner);
	}

	@Override
	public boolean saveFile(FileMeta fileMeta) {
		
		boolean result = false;
		
		try{
			File fileDir = null ;
			
			if (fileMeta.getFileType().equalsIgnoreCase(USER_PROFILE_IMPORT_FILE_TYPE)){
				//Creating the directory to store file
				fileDir = new File(env.getRequiredProperty(ROOT_PATH_TYPE_PROFILES)+ fileMeta.getOwner());
				if (!fileDir.exists()){
					fileDir.mkdirs();
				}
				// Create the file
				File file = new File(fileDir, fileMeta.getFilename());
				
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(file));
				stream.write(fileMeta.getContent());
				stream.close();

			}else if(fileMeta.getFileType().equalsIgnoreCase(PROJECT_OVERVIEW_IMPORT_FILE_TYPE)){
				//Creating the directory to store file
				fileDir = new File(env.getRequiredProperty(ROOT_PATH_TYPE_PROJECT_PLANNING) + fileMeta.getOwner());
				if (!fileDir.exists()){
					fileDir.mkdirs();
				}
				// Create the file
				File file = new File(fileDir, fileMeta.getFilename());
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(file));
				stream.write(fileMeta.getContent());
				stream.close();
				
			}else{
				throw new Exception("No root path configured for file: "+fileMeta.getFileType()+" . to save!!");
			}
			
		}catch(Exception ex){
			logger.error(ex.getMessage());
			ex.printStackTrace();
		}
		FileMetaDao.saveFile(fileMeta);
		result = true;
		
		return result;
		
	}

	@Override
	public void updateFile(FileMeta fileMeta) {
		FileMetaDao.updateFile(fileMeta);
		
	}

	@Override
	public void deleteFile(FileMeta fileMeta) {
		FileMetaDao.deleteFile(fileMeta);
		
	}

	@Override
	public FileMeta findById(Integer id) {
		// TODO Auto-generated method stub
		return FileMetaDao.findById(id);
		
	}

	@Override
	public FileMeta findByFilepath(String filepath) {
		// TODO Auto-generated method stub
		return FileMetaDao.findByFilepath(filepath);
	}

}
