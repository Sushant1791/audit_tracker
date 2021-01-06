package com.audit.service.dto;

import java.util.ArrayList;
import java.util.List;

import com.audit.domain.AuditProcedureMaster;

public class AuditProcedureMasterDto {
	
	private Long id;
	private String procedureTitle;
	private String procedureDescription;
	private boolean isActive;
	private Long deptartment;
	private String departmentName;
	private Long costCentreId;
	private String costCentreName;
	private List<AuditProcedureObjectiveDto> auditProcedureObjectiveData;
	
	public  AuditProcedureMasterDto() {
		super();
	}
	
	public  AuditProcedureMasterDto(AuditProcedureMaster auditProcedureMaster) {
		this.id=auditProcedureMaster.getId();
		this.procedureTitle = auditProcedureMaster.getProcedureTitle();
		this.procedureDescription = auditProcedureMaster.getProcedureDescription();
		this.isActive= auditProcedureMaster.getIsActive();
		this.costCentreId = auditProcedureMaster.getCostCentreId().getId();
		this.costCentreName = auditProcedureMaster.getCostCentreId().getCostCentreName();
	    this.deptartment = auditProcedureMaster.getDeptId().getId();
	    this.departmentName = auditProcedureMaster.getDeptId().getDeptName();
	    
	    this.auditProcedureObjectiveData= new ArrayList<>();
	    auditProcedureMaster.getAuditProcObjectiveMasterCollection().forEach(auditProcdureObjectiveMaster -> {
	    	AuditProcedureObjectiveDto auditProcedureObjectiveDto=new AuditProcedureObjectiveDto();
	    	auditProcedureObjectiveDto.setId(auditProcdureObjectiveMaster.getId());
	    	auditProcedureObjectiveDto.setActive(auditProcdureObjectiveMaster.getIsActive());
	    	auditProcedureObjectiveDto.setObjectiveDescription(auditProcdureObjectiveMaster.getObjectiveDescription());
	    	auditProcedureObjectiveDto.setObjectiveName(auditProcdureObjectiveMaster.getObjectiveName());
	    	this.auditProcedureObjectiveData.add(auditProcedureObjectiveDto);
	    });
	}
	
	
	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getProcedureTitle() {
		return procedureTitle;
	}
	public void setProcedureTitle(String procedureTitle) {
		this.procedureTitle = procedureTitle;
	}
	public String getProcedureDescription() {
		return procedureDescription;
	}
	public void setProcedureDescription(String procedureDescription) {
		this.procedureDescription = procedureDescription;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public Long getDeptartment() {
		return deptartment;
	}
	public void setDeptartment(Long deptartment) {
		this.deptartment = deptartment;
	}
	public List<AuditProcedureObjectiveDto> getAuditProcedureObjectiveData() {
		return auditProcedureObjectiveData;
	}
	public void setAuditProcedureObjectiveData(List<AuditProcedureObjectiveDto> auditProcedureObjectiveData) {
		this.auditProcedureObjectiveData = auditProcedureObjectiveData;
	}

	public Long getCostCentreId() {
		return costCentreId;
	}

	public void setCostCentreId(Long costCentreId) {
		this.costCentreId = costCentreId;
	}

	public String getCostCentreName() {
		return costCentreName;
	}

	public void setCostCentreName(String costCentreName) {
		this.costCentreName = costCentreName;
	}



	
}
