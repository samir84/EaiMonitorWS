package com.hs.eai.monitorws.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hs.eai.monitorws.model.FileMeta;

@Repository
public class FileMetaDaoImpl implements FileMetaDao{

	
	
	private static final Logger logger = LoggerFactory.getLogger(FileMetaDaoImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	protected Session getSession(){
        return sessionFactory.getCurrentSession();
    }
	
	@Override
	public List<FileMeta> findAllFiles() {
		// TODO Auto-generated method stub
		List<FileMeta> listFiles = null;
		try{
			Query query =  getSession().getNamedQuery("FileMeta.findAll");
			listFiles =  query.list();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return listFiles;
	}

	@Override
	public List<FileMeta> findAllFilesByOwner(String owner) {
		
		List<FileMeta> listFiles = null;
		try{
			Query query =  getSession().getNamedQuery("FileMeta.findByOwner");
			query.setParameter("owner", owner);
			listFiles =  query.list();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return listFiles;
	}

	/**
	 * Save File to folder and save meta file data
	 */
	@Override
	public void saveFile(FileMeta fileMeta) {
		getSession().persist(fileMeta);
		
	}

	@Override
	public void updateFile(FileMeta fileMeta) {
		
		getSession().merge(fileMeta);
		
	}

	@Override
	public void deleteFile(FileMeta fileMeta) {
		getSession().delete(fileMeta);
		
	}

	@Override
	public FileMeta findById(Integer id) {
		// TODO Auto-generated method stub
		
		return (FileMeta) getSession().get(FileMeta.class, id);
	}

	@Override
	public FileMeta findByFilepath(String filepath) {
		FileMeta fileMeta = null;
		try{
			Query query =  getSession().getNamedQuery("FileMeta.findByFilepath");
			query.setParameter("filepath", filepath);
			fileMeta =  (FileMeta) query.list().get(0);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return fileMeta;
	}

}
