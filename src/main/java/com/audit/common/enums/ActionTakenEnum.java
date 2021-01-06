package com.audit.common.enums;

import java.util.Arrays;
import java.util.List;

public enum ActionTakenEnum {

	CORRECTION(1, "Correction"), 
	CORRECTIVE_ACTION(2, "Corrective Action"), 
	CONTROL_IN_PLACE(3, "Controls in Place"),
	MONITORING(4, "Monitoring"), 
	PROCESS_DEVELOPMENT(5, "Process Development"),
	PROCEDURE_DEVELOPMENT(6, "Procedure Development"), 
	NO_ACTION_TAKEN(7, "No Action Taken"),
	REPETITION(8, "Repetition"), 
	OTHERS(9, "Others (Type)");

	private int id;
	private String actionTaken;

	ActionTakenEnum(int id, String actionTaken) {
		this.id = id;
		this.actionTaken = actionTaken;
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
	
	public static List<ActionTakenEnum> getActionTakenEnum() {
		return Arrays.asList(ActionTakenEnum.values());
	}

	public static String getActionTakenById(int id) {
		return getActionTakenEnum().stream().filter(predicate -> predicate.getId() == id).findFirst().get()
				.getActionTaken();
	}

}
