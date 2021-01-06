package com.audit.service.dto;

import com.audit.domain.TestingPlanObservationMaster;

public class TestingPlanObservationMasterDTO {

	private Long id;
	private String observation;
	private String inference;
	private String monetaryImpact;
	private String auditeeResponse;

	public TestingPlanObservationMasterDTO(TestingPlanObservationMaster testingPlanObservationMaster) {
		this.id = testingPlanObservationMaster.getId();
		this.observation = testingPlanObservationMaster.getObservation();
		this.inference = testingPlanObservationMaster.getInference();
		this.monetaryImpact = testingPlanObservationMaster.getMonetaryImpact();
		this.auditeeResponse = testingPlanObservationMaster.getAuditeeResponse();
	}

	public TestingPlanObservationMasterDTO() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getAuditeeResponse() {
		return auditeeResponse;
	}

	public void setAuditeeResponse(String auditeeResponse) {
		this.auditeeResponse = auditeeResponse;
	}

}
