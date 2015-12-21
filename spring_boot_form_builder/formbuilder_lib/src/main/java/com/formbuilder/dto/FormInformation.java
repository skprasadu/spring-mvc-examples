package com.formbuilder.dto;

import java.util.List;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class FormInformation {
	
	private String id;
	private String type;
	private String application;
	private Node rootnode;
	private String entryType;
	private List<NameValue> ruleDetails; 
}
