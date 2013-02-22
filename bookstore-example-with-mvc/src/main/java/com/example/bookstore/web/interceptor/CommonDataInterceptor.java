package com.example.bookstore.web.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;

import com.example.bookstore.service.BookstoreService;

/**
 * {@code WebRequestInterceptor} implementation to add common data (random books) to the model.
 * 
 * 
 * 
 */
public class CommonDataInterceptor implements WebRequestInterceptor {

    @Autowired
    private BookstoreService bookstoreService;

    @Override
    public void preHandle(WebRequest request) throws Exception {
    }

    @Override
    public void postHandle(WebRequest request, ModelMap model) throws Exception {
        if (model != null) {
            model.addAttribute("randomBooks", this.bookstoreService.findRandomBooks());
        }

    }

    @Override
    public void afterCompletion(WebRequest request, Exception ex) throws Exception {
    }

}
