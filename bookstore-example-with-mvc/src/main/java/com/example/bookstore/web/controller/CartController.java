package com.example.bookstore.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.bookstore.domain.Book;
import com.example.bookstore.domain.Cart;
import com.example.bookstore.service.BookstoreService;

/**
 * 
 * 
 * 
 */
@Controller
@RequestMapping("/cart")
public class CartController {

    private final Logger logger = LoggerFactory.getLogger(CartController.class);

    @Autowired
    private Cart cart;

    @Autowired
    private BookstoreService bookstoreService;

    @RequestMapping("/add/{bookId}")
    public String addToCart(@PathVariable("bookId") long bookId, @RequestHeader("referer") String referer) {
        Book book = this.bookstoreService.findBook(bookId);
        this.cart.addBook(book);
        this.logger.info("Cart: {}", this.cart);
        return "redirect:" + referer;
    }

}
