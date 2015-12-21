package com.formbuilder.dto;

import java.util.List;

import lombok.Data;

@Data
public class TableDetail {
	private String columnNames;
	private List<NameValue> columnDisplayNames; 
	private String relationshipNames;
	private List<NameValue> relationshipDisplayNames;
	private List<NameValue> relationshipType;
	private List<NameValue> ruleDetails; 
	private String tableName;
	private String tableType;	
}
