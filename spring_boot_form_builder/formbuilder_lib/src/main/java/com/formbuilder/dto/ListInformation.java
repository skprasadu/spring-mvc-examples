package com.formbuilder.dto;

import lombok.Getter;
import lombok.experimental.Builder;

@Builder
public class ListInformation {
	@Getter
	private String id;
	
	@Getter
	private String name;
}
