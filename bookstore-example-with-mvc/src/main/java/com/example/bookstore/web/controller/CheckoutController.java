package com.example.bookstore.web.controller;

import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.example.bookstore.domain.Account;
import com.example.bookstore.domain.Cart;
import com.example.bookstore.domain.Order;
import com.example.bookstore.service.BookstoreService;
import com.example.bookstore.validation.OrderValidator;

/**
 * 
 * 
 * 
 */
@Controller
@SessionAttributes(types = { Order.class })
@RequestMapping("/cart/checkout")
public class CheckoutController {

    private final Logger logger = LoggerFactory.getLogger(CheckoutController.class);

    @Autowired
    private Cart cart;

    @Autowired
    private BookstoreService bookstoreService;

    @ModelAttribute("countries")
    public Map<String, String> countries(Locale currentLocale) {
        Map<String, String> countries = new TreeMap<String, String>();
        for (Locale locale : Locale.getAvailableLocales()) {
            countries.put(locale.getCountry(), locale.getDisplayCountry(currentLocale));
        }
        return countries;
    }

    @RequestMapping(method = RequestMethod.GET)
    public void show(HttpSession session, Model model) {
        Account account = (Account) session.getAttribute(LoginController.ACCOUNT_ATTRIBUTE);
        Order order = this.bookstoreService.createOrder(this.cart, account);
        model.addAttribute(order);
    }

    @RequestMapping(method = RequestMethod.POST, params = "order")
    public String checkout(SessionStatus status, @Validated @ModelAttribute Order order, BindingResult errors) {
        if (errors.hasErrors()) {
            return "cart/checkout";
        } else {
            this.bookstoreService.store(order);
            status.setComplete(); //remove order from session
            this.cart.clear(); // clear the cart
            return "redirect:/index.htm";
        }
    }

    @RequestMapping(method = RequestMethod.POST, params = "update")
    public String update(@ModelAttribute Order order) {
        order.updateOrderDetails();
        return "cart/checkout";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setValidator(new OrderValidator());
    }

}
