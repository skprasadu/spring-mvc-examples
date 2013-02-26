package com.example.bookstore.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller for the homepage
 * 
 * 
 * 
 * 
 */
@Controller
public class MainController {
	@RequestMapping("public/main.htm")
	public ModelAndView main() {
		ModelAndView mov = new ModelAndView();
		mov.setViewName("main");
		return mov;
	}
}
