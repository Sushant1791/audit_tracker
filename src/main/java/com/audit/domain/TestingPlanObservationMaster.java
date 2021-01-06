/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.audit.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 *
 * @author admin
 */
@Entity
@Table(name = "testing_plan_observation_master")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TestingPlanObservationMaster implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
	@SequenceGenerator(name = "sequenceGenerator")
	private Long id;
	@Size(max = 255)
	@Column(length = 255)
	private String observation;

	@Size(max = 255)
	@Column(length = 255)
	private String inference;

	@Size(max = 255)
	@Column(name = "monetary_impact", length = 255)
	private String monetaryImpact;

	@JoinColumn(name = "testing_plan_id", referencedColumnName = "id")
	@ManyToOne
	private AuditTestingPlanMaster testingPlanId;

	@JoinColumn(name = "document_id", referencedColumnName = "id")
	@ManyToOne
	private DocumentMaster documentId;

	@Column(name = "auditee_response")
	private String auditeeResponse;

	public TestingPlanObservationMaster() {
	}

	public TestingPlanObservationMaster(Long id) {
		this.id = id;
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

	public AuditTestingPlanMaster getTestingPlanId() {
		return testingPlanId;
	}

	public void setTestingPlanId(AuditTestingPlanMaster testingPlanId) {
		this.testingPlanId = testingPlanId;
	}

	public DocumentMaster getDocumentId() {
		return documentId;
	}

	public void setDocumentId(DocumentMaster documentId) {
		this.documentId = documentId;
	}

	public String getAuditeeResponse() {
		return auditeeResponse;
	}

	public void setAuditeeResponse(String auditeeResponse) {
		this.auditeeResponse = auditeeResponse;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof TestingPlanObservationMaster)) {
			return false;
		}
		TestingPlanObservationMaster other = (TestingPlanObservationMaster) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.audit.domain.TestingPlanObservationMaster[ id=" + id + " ]";
	}

}
