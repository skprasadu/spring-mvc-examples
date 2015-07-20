package org.hcl.test.repository;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.hcl.test.UiApplication;
import org.hcl.test.model.OrderDetails;
import org.hcl.test.model.Orders;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;

@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
		TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class })
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = UiApplication.class)
@DatabaseSetup(CartRepositoryTest.DATASET)
@DatabaseTearDown(type = DatabaseOperation.DELETE_ALL, value = { CartRepositoryTest.DATASET })
@DirtiesContext
public class CartRepositoryTest {
	protected static final String DATASET = "classpath:datasets/cart.xml";
	
	@Autowired
	private OrdersRepository repository;

	@Autowired
	private OrderDetailsRepository orderDetailsRepository;

	@Test
	public void testGetCart() {
		assertTrue(repository.getCart(1).isIsInCart());
	}
	
	@Test
	public void testAddItemToCart() {
		Orders orders = repository.getCart(1);

		OrderDetails orderDetail = new OrderDetails();
		orderDetail.setId(1);
		orderDetail.setQuantity(5);
		orderDetail.setOrdersId(orders.getId());
		orderDetailsRepository.save(orderDetail);
		
		List<OrderDetails> orderDetails1 = orderDetailsRepository.getOrderDetails(orders.getId());
		assertTrue(orderDetails1.size() == 1);
		orderDetailsRepository.delete(orderDetail);
	}
	
	@Test
	public void testCheckoutCart() {
		Orders orders = repository.getCart(1);
		OrderDetails orderDetail = new OrderDetails();
		orderDetail.setId(1);
		orderDetail.setQuantity(5);
		orderDetail.setOrdersId(orders.getId());
		orderDetailsRepository.save(orderDetail);
		
		List<OrderDetails> orderDetails1 = orderDetailsRepository.getOrderDetails(orders.getId());
		assertTrue(orderDetails1.size() == 1);
		repository.checkoutCart(1);
		Orders orders1 = repository.getCart(1);
		assertTrue(orders1 == null);
	}
	
}
