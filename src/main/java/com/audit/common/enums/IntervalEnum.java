package com.audit.common.enums;

import java.util.Arrays;
import java.util.List;

public enum IntervalEnum {

	DAILY(1, "Daily"), 
	WEEKLY(7, "Weekly" ),
	MONTHLY(30, "Monthly"), 
	QUARTERLY(90 ,"Quarterly"), 
	HALF_YEARLY(180,"Half Yearly"), 
	ANNUAL(365, "Annual");

	private int id;
	private String interval;

	private IntervalEnum(int id, String interval) {
		this.id = id;
		this.interval = interval;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getInterval() {
		return interval;
	}

	public void setInterval(String interval) {
		this.interval = interval;
	}

	public static List<IntervalEnum> getIntervalEnums() {
		return Arrays.asList(IntervalEnum.values());
	}

	public static IntervalEnum getIntervalEnumById(int id) {
		return getIntervalEnums().stream().filter(interval -> interval.getId() == id).findFirst().get();
	}

}
