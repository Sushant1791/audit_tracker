package com.audit.service.dto;

public class AuditPlanTestingSearchDTO {
	
	private String auditPlanEntity;
	private String start_date;
	private String end_date;
	private String status;
	private String respond;
	private String auditee;
	private String auditor;
	
	public String getAuditPlanEntity() {
		return auditPlanEntity;
	}
	public void setAuditPlanEntity(String auditPlanEntity) {
		this.auditPlanEntity = auditPlanEntity;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRespond() {
		return respond;
	}
	public void setRespond(String respond) {
		this.respond = respond;
	}
	public String getAuditee() {
		return auditee;
	}
	public void setAuditee(String auditee) {
		this.auditee = auditee;
	}
	public String getAuditor() {
		return auditor;
	}
	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}
}
