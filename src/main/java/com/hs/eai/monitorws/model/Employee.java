package com.hs.eai.monitorws.model;

public class Employee {


	private Integer id;
	private String name;
	private String windowsUser;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWindowsUser() {
		return windowsUser;
	}

	public void setWindowsUser(String windowsUser) {
		this.windowsUser = windowsUser;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", windowsUser=" + windowsUser + "]";
	}

}
