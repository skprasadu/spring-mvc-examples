package com.example.bookstore.web.controller;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Form for adding {@link Category} enitities to the system
 * 
 * @See ManageBookController
 * 
 * 
 * 
 */
public class ManageCategoryForm {

	@NotEmpty
	private String category;

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
}
