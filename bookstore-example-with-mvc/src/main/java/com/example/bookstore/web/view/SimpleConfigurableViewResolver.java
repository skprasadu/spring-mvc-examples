package com.example.bookstore.web.view;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

/**
 * Very simple implementation of a {@link ViewResolver}. It uses an internal {@link Map} to link the name of a view to a concrete {@link View} implementation.
 * 
 * 
 * 
 *
 */
public class SimpleConfigurableViewResolver implements ViewResolver {

    private Map<String, ? extends View> views = new HashMap<String, View>();

    @Override
    public View resolveViewName(String viewName, Locale locale) throws Exception {
        return this.views.get(viewName);
    }

    public void setViews(Map<String, ? extends View> views) {
        this.views = views;
    }
}
