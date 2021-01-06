package com.audit.service.dto;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.audit.common.enums.ActionTakenEnum;
import com.audit.common.enums.RiskAreaEnum;
import com.audit.common.enums.TestingResultEnum;
import com.audit.domain.AuditProcObjectiveMaster;
import com.audit.domain.AuditTestingPlanMaster;
import com.audit.service.FileOperationService;
import com.audit.service.mapper.AuditTestingPlanMasterMapping;

public class AuditTestingPlanMasterDTO {

	
	private Long id;
	private Long riskAreaId;
	private String expectedRevertDate;
	private Long auditPlanId;
	private String auditPlanEntity;
	private Long auditeeId;
	private String auditeeName;
	private Long auditorId;
	private String auditorName;
	private Long rating;
	private Long deptartment;
	private String departmentName;
	private Long costCentreId;
	private String riskArea;
	private String costCentreName;
	private List<TestingPlanObservationMasterDTO> testingPlanObservationData;
	private Long testingPlanObservationMasterId;
	private boolean isResponded;
	private String auditeeResponse;
	private List<AuditPlanObjectionMapDTO> testingAuditPlanObjectives;
	private String observation;
	private String inference;
	private String monetaryImpact;
	private Integer actionTakenId;
	private String actionTaken;
	private Integer testResultId;
	private String testResult;
	private String path;
	private List<String> files;
	private boolean isAuditPlanLinked;
	private String testDescription;
	private Boolean locked;
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

	public AuditTestingPlanMasterDTO(AuditTestingPlanMaster auditTestingPlanMaster) {
		try {
			this.id = auditTestingPlanMaster.getId();
			this.riskArea = RiskAreaEnum.getRiskAreaById(auditTestingPlanMaster.getRiskAreaId().intValue());
			this.riskAreaId = auditTestingPlanMaster.getRiskAreaId();
			this.expectedRevertDate = null != auditTestingPlanMaster.getExpectedRevertDate()
					? formatter.format(Date.from(auditTestingPlanMaster.getExpectedRevertDate()))
					: StringUtils.EMPTY;
			if (null != auditTestingPlanMaster.getAuditPlanId()) {
				this.auditPlanId = auditTestingPlanMaster.getAuditPlanId().getId();
				this.auditPlanEntity = auditTestingPlanMaster.getAuditPlanId().getAuditPlanEntity();
				List<AuditProcObjectiveMaster> procedureObjectives = null != auditTestingPlanMaster.getAuditPlanId()
						.getProcedureId()
								? auditTestingPlanMaster.getAuditPlanId().getProcedureId()
										.getAuditProcObjectiveMasterCollection()
								: new ArrayList<>();
				this.testingAuditPlanObjectives = AuditTestingPlanMasterMapping
						.getObjectiveDetailsFromAuditPlanAndProcedureMapping(
								auditTestingPlanMaster.getAuditPlanId().getAuditPlanObjectiveMapCollection(),
								procedureObjectives);
			}
			this.isAuditPlanLinked = null != auditTestingPlanMaster.getAuditPlanId() ? true : false;
			this.testDescription = auditTestingPlanMaster.getTestDescription();
			this.auditeeId = auditTestingPlanMaster.getAuditeeId().getId();
			this.auditeeName = auditTestingPlanMaster.getAuditeeId().getFirstName();
			this.auditorId = auditTestingPlanMaster.getAuditorId().getId();
			this.auditorName = auditTestingPlanMaster.getAuditorId().getFirstName();
			this.costCentreId = null != auditTestingPlanMaster.getCostCentreId()
					? auditTestingPlanMaster.getCostCentreId().getId()
					: null;
			this.deptartment = null != auditTestingPlanMaster.getDeptId() ? auditTestingPlanMaster.getDeptId().getId()
					: null;
			this.departmentName = null != auditTestingPlanMaster.getDeptId()
					? auditTestingPlanMaster.getDeptId().getDeptName()
					: null;
			this.rating = auditTestingPlanMaster.getRating();
			this.testResult = TestingResultEnum.getEnumById(auditTestingPlanMaster.getTestResult().intValue())
					.getTestResult();
			this.costCentreName = null != auditTestingPlanMaster.getCostCentreId().getId()
					? auditTestingPlanMaster.getCostCentreId().getCostCentreName()
					: StringUtils.EMPTY;
			this.isResponded = auditTestingPlanMaster.isResponded();
			this.testingPlanObservationData = AuditTestingPlanMasterMapping
					.extractTestingPlanObservations(auditTestingPlanMaster);
			this.actionTakenId = auditTestingPlanMaster.getActionTaken();
			this.actionTaken = ActionTakenEnum.getActionTakenById(auditTestingPlanMaster.getActionTaken());
			this.testResultId = auditTestingPlanMaster.getTestResult().intValue();
			this.files = FileOperationService.fetchFiles("AuditTestingplan", auditTestingPlanMaster.getId());
			this.locked = auditTestingPlanMaster.getLocked();
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}
	}

	public AuditTestingPlanMasterDTO() {
		super();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRiskAreaId() {
		return riskAreaId;
	}

	public void setRiskAreaId(Long riskAreaId) {
		this.riskAreaId = riskAreaId;
	}

	public String getExpectedRevertDate() {
		return expectedRevertDate;
	}

	public void setExpectedRevertDate(String expectedRevertDate) {
		this.expectedRevertDate = expectedRevertDate;
	}

	public Long getAuditPlanId() {
		return auditPlanId;
	}

	public void setAuditPlanId(Long auditPlanId) {
		this.auditPlanId = auditPlanId;
	}

	public String getAuditPlanEntity() {
		return auditPlanEntity;
	}

	public void setAuditPlanEntity(String auditPlanEntity) {
		this.auditPlanEntity = auditPlanEntity;
	}

	public Long getAuditeeId() {
		return auditeeId;
	}

	public void setAuditeeId(Long auditeeId) {
		this.auditeeId = auditeeId;
	}

	public String getAuditeeName() {
		return auditeeName;
	}

	public void setAuditeeName(String auditeeName) {
		this.auditeeName = auditeeName;
	}

	public Long getAuditorId() {
		return auditorId;
	}

	public void setAuditorId(Long auditorId) {
		this.auditorId = auditorId;
	}

	public String getAuditorName() {
		return auditorName;
	}

	public void setAuditorName(String auditorName) {
		this.auditorName = auditorName;
	}

	public Long getTestingPlanObservationMasterId() {
		return testingPlanObservationMasterId;
	}

	public void setTestingPlanObservationMasterId(Long testingPlanObservationMasterId) {
		this.testingPlanObservationMasterId = testingPlanObservationMasterId;
	}

	public List<TestingPlanObservationMasterDTO> getTestingPlanObservationData() {
		return testingPlanObservationData;
	}

	public void setTestingPlanObservationData(List<TestingPlanObservationMasterDTO> testingPlanObservationData) {
		this.testingPlanObservationData = testingPlanObservationData;
	}

	public Long getRating() {
		return rating;
	}

	public void setRating(Long rating) {
		this.rating = rating;
	}

	public Long getDeptartment() {
		return deptartment;
	}

	public void setDeptartment(Long deptartment) {
		this.deptartment = deptartment;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
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

	public String getRiskArea() {
		return riskArea;
	}

	public void setRiskArea(String riskArea) {
		this.riskArea = riskArea;
	}

	public boolean isResponded() {
		return isResponded;
	}

	public void setResponded(boolean isResponded) {
		this.isResponded = isResponded;
	}

	public String getAuditeeResponse() {
		return auditeeResponse;
	}

	public void setAuditeeResponse(String auditeeResponse) {
		this.auditeeResponse = auditeeResponse;
	}

	public List<AuditPlanObjectionMapDTO> getTestingAuditPlanObjectives() {
		return testingAuditPlanObjectives;
	}

	public void setTestingAuditPlanObjectives(List<AuditPlanObjectionMapDTO> testingAuditPlanObjectives) {
		this.testingAuditPlanObjectives = testingAuditPlanObjectives;
	}

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	public String getInference() {
		return inference;
	}

	public void setInference(String inference) {
		this.inference = inference;
	}

	public String getMonetaryImpact() {
		return monetaryImpact;
	}

	public void setMonetaryImpact(String monetaryImpact) {
		this.monetaryImpact = monetaryImpact;
	}

	public Integer getActionTakenId() {
		return actionTakenId;
	}

	public void setActionTakenId(Integer actionTakenId) {
		this.actionTakenId = actionTakenId;
	}

	public String getActionTaken() {
		return actionTaken;
	}

	public void setActionTaken(String actionTaken) {
		this.actionTaken = actionTaken;
	}

	public Integer getTestResultId() {
		return testResultId;
	}

	public void setTestResultId(Integer testResultId) {
		this.testResultId = testResultId;
	}

	public String getTestResult() {
		return testResult;
	}

	public void setTestResult(String testResult) {
		this.testResult = testResult;
	}

	public List<String> getFiles() {
		return files;
	}

	public void setFiles(List<String> files) {
		this.files = files;
	}

	public boolean isAuditPlanLinked() {
		return isAuditPlanLinked;
	}

	public void setAuditPlanLinked(boolean isAuditPlanLinked) {
		this.isAuditPlanLinked = isAuditPlanLinked;
	}

	public String getTestDescription() {
		return testDescription;
	}

	public void setTestDescription(String testDescription) {
		this.testDescription = testDescription;
	}

	public Boolean getLocked() {
		return locked;
	}

	public void setLocked(Boolean locked) {
		this.locked = locked;
	}

}
