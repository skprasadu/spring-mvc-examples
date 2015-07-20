package org.hcl.test;

import java.util.List;

import org.hcl.test.model.Book;
import org.hcl.test.model.Category;
import org.hcl.test.model.OrderDetails;
import org.hcl.test.model.Orders;
import org.hcl.test.repository.BookRepository;
import org.hcl.test.repository.CategoryRepository;
import org.hcl.test.repository.OrderDetailsRepository;
import org.hcl.test.repository.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class UiApplication {
	
	@Autowired
	CategoryRepository categoryRepository;
	@Autowired
	BookRepository bookRepository;
	@Autowired
	OrdersRepository ordersRepository;
	@Autowired
	OrderDetailsRepository orderDetailsRepository;
	
	private int userId = 1;
	
	@RequestMapping("/categories")
	public List<Category> categories() {
		
		return categoryRepository.findAll();
	}

	@RequestMapping("/books")
	public List<Book> books() {
		
		return bookRepository.findAll();
	}
	
	@RequestMapping(value = "/addToCart/{id}")
	public void addToCart(@PathVariable int bookId) {
		//Check if an cart exists, if not create a Cart
		//Check if the bookid is already added to the shopping cart, if no add it
		Orders order = ordersRepository.getCart(userId);
		if(order == null) {
			order = new Orders();
			order.setId(1);
			order.setIsInCart(true);;
			order.setAccountId(1);
			ordersRepository.save(order);
		}
		
		List<OrderDetails> bookInCart = orderDetailsRepository.getBookInCart(order.getId(), bookId);
		if(bookInCart.isEmpty()){
			OrderDetails orderDetails = new OrderDetails();
			orderDetails.setBookId(bookId);
			orderDetails.setOrdersId(order.getId());
			orderDetails.setQuantity(1);
			orderDetailsRepository.save(orderDetails);
		}
		return;
	}

	@RequestMapping(value = "/checkoutCart")
	public void checkoutCart() {
		ordersRepository.checkoutCart(1);
	}

	public static void main(String[] args) {
        SpringApplication.run(UiApplication.class, args);
    }
}
