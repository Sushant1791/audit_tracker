package com.audit.service.dto;

import com.audit.domain.DeptMaster;

public class DeptMasterDto {

	private Long id;
	private String deptName;

	public DeptMasterDto() {
	}

	public DeptMasterDto(DeptMaster deptMstr) {
		super();
		this.id = deptMstr.getId();
		this.deptName = deptMstr.getDeptName();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

}
