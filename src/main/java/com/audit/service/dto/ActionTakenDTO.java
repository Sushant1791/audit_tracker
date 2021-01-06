package com.audit.service.dto;

import com.audit.common.enums.ActionTakenEnum;

public class ActionTakenDTO {
	private int id;
	private String actionTaken;

	public ActionTakenDTO(ActionTakenEnum actionTaken) {
		this.id = actionTaken.getId();
		this.actionTaken = actionTaken.getActionTaken();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getActionTaken() {
		return actionTaken;
	}

	public void setActionTaken(String actionTaken) {
		this.actionTaken = actionTaken;
	}

}
