package com.formbuilder.dto;

import lombok.Data;

@Data
public class UiForm {
	private final String id;
	
	private final String formTableName;

	private final String displayName;
	
	private final int orderBy;
	
	private final String nameColumnDisplayName;
	
	private final String groupBy;
	
	private final String validator;
	
	private final String validatorInputs;
}
