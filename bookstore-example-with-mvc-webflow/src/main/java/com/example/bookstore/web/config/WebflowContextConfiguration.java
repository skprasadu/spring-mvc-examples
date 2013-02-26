package com.example.bookstore.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.webflow.definition.registry.FlowDefinitionRegistry;
import org.springframework.webflow.executor.FlowExecutor;
import org.springframework.webflow.mvc.servlet.FlowHandlerAdapter;
import org.springframework.webflow.mvc.servlet.FlowHandlerMapping;

import com.example.bookstore.web.interceptor.CommonDataHandlerInterceptor;

/**
 * The glue between Web Flow and Spring MVC, registers the {@link FlowHandlerAdapter} and {@link FlowHandlerMapping}
 * which will enable the {@link DispatcherServlet} to recognize Web Flow requests and send them to the
 * {@link FlowExecutor}
 * 
 * 
 * 
 * 
 */
@Configuration
@ImportResource("classpath:/spring/webflow-config.xml")
public class WebflowContextConfiguration {

	@Autowired
	private FlowExecutor flowExecutor;
	@Autowired
	private FlowDefinitionRegistry flowRegistry;
	@Autowired
	private CommonDataHandlerInterceptor commonDataHandlerInterceptor;
	@Autowired
	private LocaleChangeInterceptor localeChangeInterceptor;

	
	@Bean
	public FlowHandlerAdapter flowHandlerAdapter() {
		FlowHandlerAdapter flowHandlerAdapter = new FlowHandlerAdapter();
		flowHandlerAdapter.setFlowExecutor(flowExecutor);
		return flowHandlerAdapter;
	}

	@Bean
	public FlowHandlerMapping flowHandlerMapping() {
		FlowHandlerMapping flowHandlerMapping = new FlowHandlerMapping();
		flowHandlerMapping.setInterceptors(new Object[] { commonDataHandlerInterceptor,localeChangeInterceptor });
		flowHandlerMapping.setFlowRegistry(flowRegistry);
		flowHandlerMapping.setOrder(0);
		return flowHandlerMapping;
	}
}
