package com.example.bookstore.web;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.web.SpringServletContainerInitializer;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import com.example.bookstore.config.InfrastructureContextConfiguration;
import com.example.bookstore.config.TestDataContextConfiguration;
import com.example.bookstore.web.config.WebMvcContextConfiguration;

/**
 * {@link WebApplicationInitializer} that will be called by Spring's {@link SpringServletContainerInitializer} as part
 * of the JEE {@link ServletContainerInitializer} pattern. This class will be called on application startup and will
 * configure our JEE and Spring configuration.
 * <p/>
 * 
 * It will first initializes our {@link AnnotationConfigWebApplicationContext} with the common {@link Configuration}
 * classes: {@link InfrastructureContextConfiguration} and {@link TestDataContextConfiguration} using a typical JEE
 * {@link ContextLoaderListener}.
 * <p/>
 * 
 * Next it creates a {@link DispatcherServlet}, being a normal JEE Servlet which will create on its turn a child
 * {@link AnnotationConfigWebApplicationContext} configured with the Spring MVC {@link Configuration} class
 * {@link WebMvcContextConfiguration}. This Servlet will be registered using JEE's programmatical API support.
 * <p/>
 * 
 * Finally it will also register a JEE listener for enabling the open entity manager in view pattern:
 * {@link OpenEntityManagerInViewFilter}
 * 
 * 
 * 
 * 
 */
public class BookstoreWebApplicationInitializer implements WebApplicationInitializer {

	private static final String DISPATCHER_SERVLET_NAME = "dispatcher";

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		registerListener(servletContext);
		registerDispatcherServlet(servletContext);
		registerOpenEntityManagerInViewFilter(servletContext);

	}

	private void registerDispatcherServlet(ServletContext servletContext) {
		AnnotationConfigWebApplicationContext dispatcherContext = createContext(WebMvcContextConfiguration.class);
		ServletRegistration.Dynamic dispatcher = servletContext.addServlet(DISPATCHER_SERVLET_NAME,
				new DispatcherServlet(dispatcherContext));
		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping("/");
	}

	private void registerListener(ServletContext servletContext) {
		AnnotationConfigWebApplicationContext rootContext = createContext(InfrastructureContextConfiguration.class,
				TestDataContextConfiguration.class);
		servletContext.addListener(new ContextLoaderListener(rootContext));
	}

	private void registerOpenEntityManagerInViewFilter(ServletContext servletContext) {
		FilterRegistration.Dynamic registration = servletContext.addFilter("openEntityManagerInView",
				new OpenEntityManagerInViewFilter());
		registration.addMappingForServletNames(EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD), true,
				DISPATCHER_SERVLET_NAME);
	}

	/**
	 * Factory method to create {@link AnnotationConfigWebApplicationContext} instances.
	 */
	private AnnotationConfigWebApplicationContext createContext(final Class<?>... annotatedClasses) {
		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
		context.register(annotatedClasses);
		return context;
	}
}