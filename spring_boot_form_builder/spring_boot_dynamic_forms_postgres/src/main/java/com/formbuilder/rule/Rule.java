package com.formbuilder.rule;

import org.json.simple.JSONObject;

import lombok.Getter;
import lombok.Setter;

public abstract class Rule {
	@Getter @Setter 
	protected String input;

	public abstract boolean evaluate(JSONObject inputJson);

	public abstract String getErrorMessage();
	
	protected Integer getId(Object value){
		Integer iId = null;
		if(value instanceof String){
			String id = (String) value;
			iId = Integer.valueOf(id);
		} else if(value instanceof Integer){
			iId = (Integer)value;
		}
		return iId;
	}
}
