package com.formbuilder.dto;

import java.util.List;

import lombok.Data;

@Data
public class QuickFormInformation {
	private String applicationName;
	private List<TableDetail> tableDetails;
	private List<UiRule> uiRules;
}
