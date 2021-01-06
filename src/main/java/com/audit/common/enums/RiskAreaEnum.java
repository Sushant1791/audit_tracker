package com.audit.common.enums;

import java.util.Arrays;
import java.util.List;

public enum RiskAreaEnum {

	PROCESS(1, "Process"), PROCEDURE(2, "Procedure"), GOVERNANCE(3, "Governance"), CONTROLS(4, "Controls");

	private int id;
	private String riskArea;

	RiskAreaEnum(int id, String riskArea) {
		this.id = id;
		this.riskArea = riskArea;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRiskArea() {
		return riskArea;
	}

	public void setRiskArea(String riskArea) {
		this.riskArea = riskArea;
	}

	public static List<RiskAreaEnum> getRiskAreaEnum() {
		return Arrays.asList(RiskAreaEnum.values());
	}
	
	public static String getRiskAreaById(int id) {
		return getRiskAreaEnum().stream().filter(predicate -> predicate.getId() == id).findFirst().get()
				.getRiskArea();
	}
}

