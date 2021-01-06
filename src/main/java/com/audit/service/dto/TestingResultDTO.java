package com.audit.service.dto;

import com.audit.common.enums.TestingResultEnum;

public class TestingResultDTO {
	private int id;
	private String testinResult;

	public TestingResultDTO(TestingResultEnum testingResult) {
		this.id = testingResult.getId();
		this.testinResult = testingResult.getTestResult();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTestinResult() {
		return testinResult;
	}

	public void setTestinResult(String testinResult) {
		this.testinResult = testinResult;
	}

	@Override
	public String toString() {
		return "{id" + this.id + "::" + this.testinResult + "}";
	}
}
