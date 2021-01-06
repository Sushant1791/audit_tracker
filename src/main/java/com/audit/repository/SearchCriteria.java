package com.audit.repository;

import java.util.List;

public class SearchCriteria {
    public SearchCriteria(String key, String operation, List<Object> value) {
		super();
		this.key = key;
		this.operation = operation;
		this.value = value;
	}
	private String key;
    private String operation;
    private List<Object> value;
    
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public List<Object> getValue() {
		return value;
	}
	public void setValue(List<Object> value) {
		this.value = value;
	}
}