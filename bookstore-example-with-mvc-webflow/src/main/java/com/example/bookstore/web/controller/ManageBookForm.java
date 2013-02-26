package com.example.bookstore.web.controller;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.example.bookstore.domain.Category;

/**
 * Form used to capture data to add {@link Category} and {@link Book}s to the system
 * 
 * @see ManageBookController
 * 
 * 
 * 
 */
public class ManageBookForm {

	private List<Category> selectableCategories;

	@NotNull
	private Category category;
	@NotEmpty
	private String title;
	@NotEmpty
	private String description;
	@NotNull
	@Digits(integer = 4, fraction = 2)
	private BigDecimal price;
	@NotEmpty
	private String author;
	@Digits(integer = 4, fraction = 0)
	@NotNull
	private Integer year;

	public List<Category> getSelectableCategories() {
		return selectableCategories;
	}

	public void setSelectableCategories(List<Category> selectableCategories) {
		this.selectableCategories = selectableCategories;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public void clear() {
		category = null;
		title = null;
		description = null;
		price = null;
		author = null;
		year = null;
	}
}
