package com.httpbasics.model;

public class Student {

	private Integer studId;
	private String fName;
	private String lName;

	public Student() {
		super();
	}

	public Student(Integer studId, String fName, String lName) {
		super();
		this.studId = studId;
		this.fName = fName;
		this.lName = lName;
	}

	public Integer getStudId() {
		return studId;
	}

	public void setStudId(Integer studId) {
		this.studId = studId;
	}

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getlName() {
		return lName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}

	@Override
	public String toString() {
		return "Student [studId=" + studId + ", fName=" + fName + ", lName=" + lName + "]";
	}

	

}
