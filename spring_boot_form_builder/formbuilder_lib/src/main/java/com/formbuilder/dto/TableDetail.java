package com.formbuilder.dto;

import java.util.List;

import lombok.Data;

@Data
public class TableDetail {
	private String columnNames;
	private String tableName;
	private String tableType;
	
	private List<NameValue> displayNames; 
	private List<NameValue> ruleDetails; 
}
