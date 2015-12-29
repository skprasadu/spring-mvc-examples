package com.formbuilder.rule;

import java.util.Map;

import org.json.simple.JSONObject;

import lombok.Getter;
import lombok.Setter;

public abstract class Rule {
	@Getter @Setter
	protected String droolString;
	//@Getter @Setter
	//protected String formInputForDrool;
	@Getter @Setter
	protected Map<String, String> data;
	@Getter @Setter
	protected String nameListToValidate;

	public abstract String computeDroolsRule() ;
}
