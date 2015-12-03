package com.formbuilder.dto;

import lombok.Data;

@Data
public class UiFormLink {
	public final int uiFormId;
	
	public final int uiFormLinkId;
	
	public final String linkName;
	
	public final Boolean singleSelect;
}
