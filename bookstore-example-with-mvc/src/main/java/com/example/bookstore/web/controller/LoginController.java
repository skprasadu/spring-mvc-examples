package com.example.bookstore.web.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.bookstore.domain.Account;
import com.example.bookstore.service.AccountService;
import com.example.bookstore.service.AuthenticationException;

/**
 * Controller to handle login. 
 * 
 * 
 * 
 *
 */
@Controller
@RequestMapping(value = "/login")
public class LoginController {

    public static final String ACCOUNT_ATTRIBUTE = "account";
    public static final String REQUESTED_URL = "REQUESTED_URL";

    @Autowired
    private AccountService accountService;

    @RequestMapping(method = RequestMethod.GET)
    public void login() {
    }

    @RequestMapping(method = RequestMethod.POST)
    public String handleLogin(@RequestParam String username, @RequestParam String password, HttpSession session)
            throws AuthenticationException {
        Account account = this.accountService.login(username, password);
        session.setAttribute(ACCOUNT_ATTRIBUTE, account);
        String url = (String) session.getAttribute(REQUESTED_URL);
        session.removeAttribute(REQUESTED_URL); // Remove the attribute
        if (StringUtils.hasText(url) && !url.contains("login")) { // Prevent loops for the login page.
            return "redirect:" + url;
        } else {
            return "redirect:/index.htm";
        }
    }

}
