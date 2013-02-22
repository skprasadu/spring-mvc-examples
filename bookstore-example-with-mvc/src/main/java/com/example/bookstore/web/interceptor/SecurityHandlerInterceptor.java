package com.example.bookstore.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

import com.example.bookstore.domain.Account;
import com.example.bookstore.service.AuthenticationException;
import com.example.bookstore.web.controller.LoginController;

/**
 * {@code HandlerInterceptor} to apply security to controllers.
 * 
 * 
 * 
 */
public class SecurityHandlerInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Account account = (Account) WebUtils.getSessionAttribute(request, LoginController.ACCOUNT_ATTRIBUTE);
        if (account == null) {

            //Retrieve and store the original URL.
            String url = request.getRequestURL().toString();
            WebUtils.setSessionAttribute(request, LoginController.REQUESTED_URL, url);
            throw new AuthenticationException("Authentication required.", "authentication.required");
        }
        return true;
    }

}
