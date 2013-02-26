package com.example.bookstore.web.controller;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;
import org.springframework.binding.validation.ValidationContext;
import org.springframework.format.annotation.DateTimeFormat;

import com.example.bookstore.domain.Book;
import com.example.bookstore.domain.Category;
import com.example.bookstore.domain.Order;

/**
 * Form to capture all elements of a order creation flow. When all mandatory elements are filled in a new {@link Order}
 * can be created based upon this information contained in this form
 * 
 * 
 * 
 * 
 */
public class OrderForm implements Serializable {

	private Map<Book, Integer> books = new HashMap<Book, Integer>();

	private Book book;

	@NotNull
	@Min(1)
	@Max(999)
	private Integer quantity;

	private Category category;

	@DateTimeFormat(pattern = "MM-dd-yyyy")
	private Date deliveryDate;
	@DateTimeFormat(pattern = "MM-dd-yyyy")
	private Date orderDate;

	// ---- Form validation methods triggered by webflow according to convention, see reference 5.10. Validating a model

	public void validateSelectCategory(ValidationContext context) {
		if (context.getUserEvent().equals("next")) {
			MessageContext messages = context.getMessageContext();
			if (category == null) {
				messages.addMessage(new MessageBuilder().error().source("category")
						.code("error.page.category.required").build());
			}
		}
	}

	public void validateSelectBooks(ValidationContext context) {
		if (context.getUserEvent().equals("next")) {
			MessageContext messages = context.getMessageContext();
			if (books.isEmpty()) {
				messages.addMessage(new MessageBuilder().error().source("books").code("error.page.books.required")
						.build());
			}
		}
	}

	public void resetSelectedBooks() {
		books.clear();
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Map<Book, Integer> getBooks() {
		return books;
	}

	public void setBooks(Map<Book, Integer> books) {
		this.books = books;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}
}