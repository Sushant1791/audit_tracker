package com.audit.service.dto;

public class AuditPlanSearchDTO {
	
	private String auditPlanEntity;
	private String assignTo;
	private String remarks;
	private String start_date;
	private String end_date;
	
	public String getAuditPlanEntity() {
		return auditPlanEntity;
	}
	public void setAuditPlanEntity(String auditPlanEntity) {
		this.auditPlanEntity = auditPlanEntity;
	}
	public String getAssignTo() {
		return assignTo;
	}
	public void setAssignTo(String assignTo) {
		this.assignTo = assignTo;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getStart_date() {
		return start_date;
	}
	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}
	public String getEnd_date() {
		return end_date;
	}
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
	
	 
}
