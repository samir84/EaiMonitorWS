package com.hs.eai.monitorws.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Transient;

@Entity
@NamedQueries({
	@NamedQuery(name = "FileMeta.findAll", query = "select fm from FileMeta fm "),
	@NamedQuery(name = "FileMeta.findById", query = "select fm from FileMeta fm where fm.id=:id"),
	@NamedQuery(name = "FileMeta.findByOwner", query = "select fm from FileMeta fm where fm.owner=:owner"),
	@NamedQuery(name = "FileMeta.findByFilepath", query = "select fm from FileMeta fm where fm.filepath=:filepath")
	
})
public class FileMeta {

	@Id
	@GeneratedValue
	private Integer id;
	@Column(name="file_name")
	private String filename;
	@Column(name="file_path",unique=true)
	private String filepath;
	@Column(name="file_type")
	private String fileType;
	@Transient
	private byte[] content;
	@Column(name="owner",nullable= false)
	private String owner;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getFilepath() {
		return filepath;
	}
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public byte[] getContent() {
		return content;
	}
	public void setContent(byte[] content) {
		this.content = content;
	}
	
}
