package com.hs.eai.monitorws;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;

import com.hs.eai.monitorws.model.FileMeta;
import com.hs.eai.monitorws.service.FileMetaService;
import com.hs.eai.monitorws.spring.AppConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class, loader = AnnotationConfigContextLoader.class)
@WebAppConfiguration

public class FileMetaServiceTest {

	@Autowired
	FileMetaService fileMetatService;
	
	@Test
	public void findByFilepath(){
		
		String filePath="/d/test/samir.png";
		
		FileMeta fileMeta = fileMetatService.findByFilepath(filePath);
		System.out.println("fileMeta:"+fileMeta);
		Assert.assertNotEquals(null, fileMeta);
		
	}
}
