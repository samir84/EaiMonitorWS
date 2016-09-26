package com.hs.eai.monitorws.service;

import java.util.List;

import com.hs.eai.monitorws.model.FileMeta;

public interface FileMetaService {

	List<FileMeta> findAllFiles();
	List<FileMeta> findAllFilesByOwner(String owner);
	public boolean saveFile(FileMeta fileMeta);
	public void updateFile(FileMeta fileMeta);
	public void deleteFile(FileMeta fileMeta);
	
	public FileMeta findById(Integer id);
	public FileMeta findByFilepath(String filepath);
}
