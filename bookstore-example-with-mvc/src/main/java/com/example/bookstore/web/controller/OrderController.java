package com.example.bookstore.web.controller;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.bookstore.domain.Order;
import com.example.bookstore.service.BookstoreService;

/**
 * Controller to render a page with the order 
 */
@Controller
public class OrderController {

    @Autowired
    private BookstoreService bookstoreService;

    @RequestMapping(value = "/order/{orderId}", method = RequestMethod.GET)
    public ModelAndView indexWithContentType(@PathVariable("orderId") long orderId) {
        return handleOrderInternal(orderId);
    }

    @RequestMapping(value = "/order.*", method = RequestMethod.GET)
    public ModelAndView indexWithRequestParameter(@RequestParam("orderId") long orderId) {
        return handleOrderInternal(orderId);
    }

    private ModelAndView handleOrderInternal(long orderId) {
        ModelAndView mav = new ModelAndView("order");
        Order order = this.bookstoreService.findOrder(orderId);
        // Do some ugly hibernate stuff here
        Hibernate.initialize(order);
        if (order instanceof HibernateProxy) {
            order = (Order) ((HibernateProxy) order).getHibernateLazyInitializer().getImplementation();
        }
        mav.addObject("order", order);
        return mav;

    }

}
