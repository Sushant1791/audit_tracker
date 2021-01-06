package com.audit.service.dto;

import com.audit.common.enums.RiskAreaEnum;

public class RiskAreaDTO {
	private int id;
	private String risk;
	
	public RiskAreaDTO(RiskAreaEnum risk){
		this.id = risk.getId();
		this.risk = risk.getRiskArea();
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRisk() {
		return risk;
	}
	public void setRisk(String risk) {
		this.risk = risk;
	}
	
	@Override
	public String toString() {
		return "[id::" + this.id + " risk ::" + this.risk + "]";
	}
}