package com.hs.eai.monitorws.model;

import org.springframework.http.HttpStatus;

public class GenericResponse {

	private HttpStatus status;
	private Object Object;
	private String message;
	
	public HttpStatus getStatus() {
		return status;
	}
	public void setStatus(HttpStatus status) {
		this.status = status;
	}
	public Object getObject() {
		return Object;
	}
	public void setObject(Object object) {
		Object = object;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
