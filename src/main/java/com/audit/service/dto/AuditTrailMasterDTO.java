package com.audit.service.dto;

public class AuditTrailMasterDTO {

	private Long id;
	private String auditDate;
	private String auditEvent;
	private String auditType;
	private String description;

	public AuditTrailMasterDTO(Long id, String auditDate, String auditEvent, String auditType, String description) {
		this.id = id;
		this.auditDate = auditDate;
		this.auditEvent = auditEvent;
		this.auditType = auditType;
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAuditDate() {
		return auditDate;
	}

	public void setAuditDate(String auditDate) {
		this.auditDate = auditDate;
	}

	public String getAuditEvent() {
		return auditEvent;
	}

	public void setAuditEvent(String auditEvent) {
		this.auditEvent = auditEvent;
	}

	public String getAuditType() {
		return auditType;
	}

	public void setAuditType(String auditType) {
		this.auditType = auditType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((auditDate == null) ? 0 : auditDate.hashCode());
		result = prime * result + ((auditEvent == null) ? 0 : auditEvent.hashCode());
		result = prime * result + ((auditType == null) ? 0 : auditType.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AuditTrailMasterDTO other = (AuditTrailMasterDTO) obj;
		if (auditDate == null) {
			if (other.auditDate != null)
				return false;
		} else if (!auditDate.equals(other.auditDate))
			return false;
		if (auditEvent == null) {
			if (other.auditEvent != null)
				return false;
		} else if (!auditEvent.equals(other.auditEvent))
			return false;
		if (auditType == null) {
			if (other.auditType != null)
				return false;
		} else if (!auditType.equals(other.auditType))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AuditTrailMasterDTO [id=" + id + ", auditDate=" + auditDate + ", auditEvent=" + auditEvent
				+ ", auditType=" + auditType + ", description=" + description + "]";
	}

}
