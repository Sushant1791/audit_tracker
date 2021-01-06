package com.audit.service.dto;

import com.audit.common.enums.IntervalEnum;

public class IntervalDTO {

	private int id;
	private String interval;

	public IntervalDTO(IntervalEnum interval) {
		this.id = interval.getId();
		this.interval = interval.getInterval();
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

}
