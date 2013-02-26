package com.example.bookstore.web.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.webflow.action.EventFactorySupport;
import org.springframework.webflow.execution.Event;

import com.example.bookstore.domain.Book;
import com.example.bookstore.domain.Category;
import com.example.bookstore.domain.Order;
import com.example.bookstore.domain.support.OrderBuilder;
import com.example.bookstore.service.BookstoreService;
import com.example.bookstore.service.CategoryService;
import com.example.bookstore.web.security.SecurityContextSupport;

/**
 * Controller to be used to place and view orders using the {@link BookstoreService}. This controller can be used using
 * Spring MVC (view orders) or by POJO access (for example Web Flow) for placing orders
 * 
 * 
 * 
 * 
 */
@Component
public class OrderController {
	@Autowired
	private BookstoreService bookstoreService;

	@Autowired
	private CategoryService categoryService;

	public List<Order> retrieveOrders() {
		List<Order> orders = bookstoreService
				.findOrdersForAccount(SecurityContextSupport.getUserDetails().getAccount());
		return orders;
	}

	public OrderForm initializeForm() {
		OrderForm orderForm = new OrderForm();
		orderForm.setQuantity(1);
		orderForm.setOrderDate(new Date());
		return orderForm;
	}

	public List<Category> initializeSelectableCategories() {
		return categoryService.findAll();
	}

	public List<Book> initializeSelectableBooks(OrderForm orderForm) {
		return bookstoreService.findBooksByCategory(orderForm.getCategory());
	}

	public void addBook(OrderForm orderForm) {
		Book book = orderForm.getBook();
		if (orderForm.getBooks().containsKey(book)) {
			orderForm.getBooks().put(book, orderForm.getBooks().get(book) + orderForm.getQuantity());
		} else {
			orderForm.getBooks().put(book, orderForm.getQuantity());
		}
	}

	public Long placeOrder(final OrderForm orderForm) {

		Order order = new OrderBuilder() {
			{
				addBooks(orderForm.getBooks());
				deliveryDate(orderForm.getDeliveryDate());
				orderDate(orderForm.getOrderDate());
				account(SecurityContextSupport.getUserDetails().getAccount());
			}
		}.build(true);

		return bookstoreService.store(order).getId();
	}

	public Event validateDeliveryDate(OrderForm orderForm, MessageContext messageContext) {
		if (orderForm.getDeliveryDate() == null) {
			MessageBuilder errorMessageBuilder = new MessageBuilder().error();
			errorMessageBuilder.source("deliveryDate");
			errorMessageBuilder.code("error.page.selectdeliveryoptions.deliverydate.required");
			messageContext.addMessage(errorMessageBuilder.build());
			return new EventFactorySupport().error(this);
		}

		if (!orderForm.getDeliveryDate().after(DateUtils.truncate(orderForm.getOrderDate(), Calendar.DAY_OF_MONTH))) {
			MessageBuilder errorMessageBuilder = new MessageBuilder().error();
			errorMessageBuilder.source("deliveryDate");
			errorMessageBuilder.code("error.page.selectdeliveryoptions.deliverydate.in.past");
			messageContext.addMessage(errorMessageBuilder.build());
			return new EventFactorySupport().error(this);
		}
		return new EventFactorySupport().success(this);
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}

}
