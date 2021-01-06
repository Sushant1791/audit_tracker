package com.audit.service.dto;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Size;

import com.audit.domain.AuditPlanMaster;

public class AuditPlanMasterDTO {

	private String createdDate;

	private String endDate;
	private Long id;
	private boolean isActive;
	private Instant lastModifiedDate;
	@Size(max = 255)
	private String remarks;
	private String startDate;
	private UserDTO assignTo;
	private UserDTO createdBy;
	private UserDTO lastModifiedBy;
	private String auditPlanEntity;
	private Long department;
	private String departmentName;
	private Long costCentreId;
	private String costCentreName;
	private boolean showReminder;
	private Long locationId;
	private String locationName;
	
	private AuditProcedureMasterDto procedure;
	private List<AuditPlanObjectionMapDTO> auditPlanObjectiveData;
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

	public AuditPlanMasterDTO(AuditPlanMaster master) {
		this.id = master.getId();
		this.auditPlanEntity = master.getAuditPlanEntity();
		this.endDate = formatter.format(Date.from(master.getEndDate()));
		this.startDate = formatter.format(Date.from(master.getStartDate()));
		this.remarks = master.getRemarks();
		UserDTO assign = new UserDTO();
		assign.setFirstName(master.getAssignTo().getFirstName());
		assign.setId(master.getAssignTo().getId());
		this.assignTo = assign;
		this.isActive = master.getIsActive();
		this.createdDate = master.getCreatedDate().toString();
		UserDTO createdBy = new UserDTO();
		createdBy.setId(master.getCreatedBy().getId());
		this.createdBy = createdBy;
		AuditProcedureMasterDto proc = new AuditProcedureMasterDto();
		proc.setId(null != master.getProcedureId() ? master.getProcedureId().getId() : null);
		this.procedure = proc;
		this.department = null != master.getDeptId() ? master.getDeptId().getId() : null;
		this.costCentreId = null != master.getCostCentreId() ? master.getCostCentreId().getId() : null;
		this.locationId = null != master.getLocation() ?master.getLocation().getId() : null;
	    this.locationName = null != master.getLocation() ?master.getLocation().getLocationName() : null;
		if (null != master.getLastModifiedBy()) {
			UserDTO lastModifiedBy = new UserDTO();
			lastModifiedBy.setId(master.getLastModifiedBy().getId());
			lastModifiedBy.setFirstName(master.getLastModifiedBy().getFirstName());
			lastModifiedBy.setEmail(master.getLastModifiedBy().getEmail());
			this.lastModifiedBy = lastModifiedBy;
			this.lastModifiedDate = master.getLastModifiedDate();
		}
		this.auditPlanObjectiveData = new ArrayList<>();
		master.getAuditPlanObjectiveMapCollection().forEach(arg -> {
			AuditPlanObjectionMapDTO auditPlanObj = new AuditPlanObjectionMapDTO();
			auditPlanObj.setId(arg.getId());
			auditPlanObj.setActive(arg.getIsActive());
			auditPlanObj.setActive(arg.getIsActive());
			auditPlanObj.setObjectiveDescription(arg.getObjectiveDescription());
			auditPlanObj.setObjectiveName(arg.getObjectiveName());
			this.auditPlanObjectiveData.add(auditPlanObj);
		});
	}

	public UserDTO getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(UserDTO lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public Long getDepartment() {
		return department;
	}

	public void setDepartment(Long department) {
		this.department = department;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
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

	public AuditPlanMasterDTO() {
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public UserDTO getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(UserDTO createdBy) {
		this.createdBy = createdBy;
	}

	public UserDTO getAssignTo() {
		return assignTo;
	}

	public void setAssignTo(UserDTO assignTo) {
		this.assignTo = assignTo;
	}

	public Long getId() {
		return id;
	}

	public Instant getLastModifiedDate() {
		return lastModifiedDate;
	}

	public String getRemarks() {
		return remarks;
	}

	public boolean isActive() {
		return isActive;
	}

	public boolean isShowReminder() {
		return showReminder;
	}

	public void setShowReminder(boolean showReminder) {
		this.showReminder = showReminder;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setLastModifiedDate(Instant lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getAuditPlanEntity() {
		return auditPlanEntity;
	}

	public void setAuditPlanEntity(String auditPlanEntity) {
		this.auditPlanEntity = auditPlanEntity;
	}

	public AuditProcedureMasterDto getProcedure() {
		return procedure;
	}

	public void setProcedure(AuditProcedureMasterDto procedure) {
		this.procedure = procedure;
	}

	public List<AuditPlanObjectionMapDTO> getAuditPlanObjectiveData() {
		return auditPlanObjectiveData;
	}

	public void setAuditPlanObjectiveData(List<AuditPlanObjectionMapDTO> auditPlanObjectiveData) {
		this.auditPlanObjectiveData = auditPlanObjectiveData;
	}

	public Long getLocationId() {
		return locationId;
	}

	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	
	

}
