package com.audit.common.enums;

import java.util.Arrays;
import java.util.List;

public enum TestingResultEnum {
	
	PLANNED(1,"Planned"),
	IN_PROGRESS(2, "In Process"),
	NON_COMPLIANCE(3, "Non Compliance"),
	COMPLIED(4, "Complied"),
	FOR_INFO_ONLY(5, "For Information Only"),
	OTHERS(6, "Others (Type)");

	private Integer id;
	private String testResult;

	private TestingResultEnum(Integer id, String testResult) {
		this.id = id;
		this.testResult = testResult;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTestResult() {
		return testResult;
	}

	public void setTestResult(String testResult) {
		this.testResult = testResult;
	}

	public static List<TestingResultEnum> getTestingResultEnums() {
		return Arrays.asList(TestingResultEnum.values());
	}

	public static TestingResultEnum getEnumById(Integer id) {
		return getTestingResultEnums().stream().filter(testingResult -> testingResult.getId() == id).findFirst().get();
	}
}
