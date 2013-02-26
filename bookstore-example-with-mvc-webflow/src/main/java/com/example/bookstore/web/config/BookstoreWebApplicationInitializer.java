package com.example.bookstore.web.config;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.security.config.BeanIds;
import org.springframework.web.SpringServletContainerInitializer;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

import com.example.bookstore.config.InfrastructureContextConfiguration;
import com.example.bookstore.config.TestDataContextConfiguration;

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
 * {@link AnnotationConfigWebApplicationContext} configured with the Spring MVC {@link Configuration} classes
 * {@link WebMvcContextConfiguration} and {@link WebflowContextConfiguration}. This Servlet will be registered using
 * JEE's programmatical API support.
 * <p/>
 * 
 * Finally we also register the Spring {@link DelegatingFilterProxy} filter which will be used by Spring security to add
 * the security filters.
 * </p>
 * 
 * Note: the {@link OpenEntityManagerInViewFilter} is only enabled for pages served soley via Spring MVC. For pages
 * being served via WebFlow we configured WebFlow to use the JpaFlowExecutionListener.
 * 
 * 
 * 
 * 
 */
public class BookstoreWebApplicationInitializer implements WebApplicationInitializer {

	private static final Class<?>[] configurationClasses = new Class<?>[] { TestDataContextConfiguration.class,
			WebMvcContextConfiguration.class, InfrastructureContextConfiguration.class,
			WebflowContextConfiguration.class };

	private static final String DISPATCHER_SERVLET_NAME = "dispatcher";

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		registerListener(servletContext);
		registerDispatcherServlet(servletContext);
		// We are using JpaFlowExecutionListener instead, but we enable it for Spring MVC served pages
		registerOpenEntityManagerInViewFilter(servletContext);
		registerSpringSecurityFilterChain(servletContext);
	}

	private void registerDispatcherServlet(ServletContext servletContext) {
		AnnotationConfigWebApplicationContext dispatcherContext = createContext(WebMvcContextConfiguration.class);
		ServletRegistration.Dynamic dispatcher = servletContext.addServlet(DISPATCHER_SERVLET_NAME,
				new DispatcherServlet(dispatcherContext));
		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping("/");
	}

	private void registerListener(ServletContext servletContext) {
		AnnotationConfigWebApplicationContext rootContext = createContext(configurationClasses);
		servletContext.addListener(new ContextLoaderListener(rootContext));
		servletContext.addListener(new RequestContextListener());
	}

	private void registerOpenEntityManagerInViewFilter(ServletContext servletContext) {
		FilterRegistration.Dynamic registration = servletContext.addFilter("openEntityManagerInView",
				new OpenEntityManagerInViewFilter());
		registration.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD), false,
				"*.htm");
		registration.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD), false,
				"/j_spring_security_check");
	}

	private void registerSpringSecurityFilterChain(ServletContext servletContext) {
		FilterRegistration.Dynamic springSecurityFilterChain = servletContext.addFilter(
				BeanIds.SPRING_SECURITY_FILTER_CHAIN, new DelegatingFilterProxy());
		springSecurityFilterChain.addMappingForUrlPatterns(null, false, "/*");
	}

	/**
	 * Factory method to create {@link AnnotationConfigWebApplicationContext} instances.
	 * @param annotatedClasses
	 * @return
	 */
	private AnnotationConfigWebApplicationContext createContext(final Class<?>... annotatedClasses) {
		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
		context.register(annotatedClasses);
		return context;
	}
}
