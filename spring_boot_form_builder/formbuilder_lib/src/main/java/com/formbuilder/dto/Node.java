package com.formbuilder.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Node {
	private String datatype;
	private String id;
	private String label;
	private int lowerbound;
	private int upperbound;
	private String val;
	private List<Node> children;
}
