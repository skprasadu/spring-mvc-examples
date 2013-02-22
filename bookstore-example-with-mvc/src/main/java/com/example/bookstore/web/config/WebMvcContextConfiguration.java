package com.example.bookstore.web.config;

import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.convert.converter.GenericConverter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import com.example.bookstore.converter.StringToEntityConverter;
import com.example.bookstore.domain.Cart;
import com.example.bookstore.domain.Category;
import com.example.bookstore.web.interceptor.CommonDataInterceptor;
import com.example.bookstore.web.interceptor.SecurityHandlerInterceptor;
import com.example.bookstore.web.method.support.SessionAttributeProcessor;

/**
 * Configures Spring MVC.
 * 
 * 
 * 
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = { "com.example.bookstore.web" })
public class WebMvcContextConfiguration extends WebMvcConfigurerAdapter {

	@Override
	public void addResourceHandlers(final ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**/*").addResourceLocations("classpath:/META-INF/web-resources/");
	}

	@Override
	public void addViewControllers(final ViewControllerRegistry registry) {
		registry.addViewController("/index.htm").setViewName("index");
	}

	@Override
	public void configureDefaultServletHandling(final DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(localeChangeInterceptor());
		registry.addWebRequestInterceptor(commonDataHandlerInterceptor());
		registry.addInterceptor(securityHandlerInterceptor()).addPathPatterns("/customer/account", "/cart/checkout",
				"/order/*", "/order.*");
	}

	// -- Start Locale Support (I18N) --//

	/**
	 * The {@link LocaleChangeInterceptor} allows for the locale to be changed. It provides a <code>paramName</code>
	 * property which sets the request parameter to check for changing the language, the default is <code>locale</code>.
	 * @return the {@link LocaleChangeInterceptor}
	 */
	@Bean
	public HandlerInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
		localeChangeInterceptor.setParamName("lang");
		return localeChangeInterceptor;
	}

	/**
	 * The {@link LocaleResolver} implementation to use. Specifies where to store the current selectd locale.
	 * 
	 * @return the {@link LocaleResolver}
	 */
	@Bean
	public LocaleResolver localeResolver() {
		return new CookieLocaleResolver();
	}

	/**
	 * To resolve message codes to actual messages we need a {@link MessageSource} implementation. The default
	 * implementations use a {@link java.util.ResourceBundle} to parse the property files with the messages in it.
	 * @return the {@link MessageSource}
	 */
	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("messages");
		messageSource.setUseCodeAsDefaultMessage(true);
		return messageSource;
	}

	// -- End Locale Support (I18N) --//

	@Bean
	public CommonDataInterceptor commonDataHandlerInterceptor() {
		return new CommonDataInterceptor();
	}

	@Bean
	public SecurityHandlerInterceptor securityHandlerInterceptor() {
		return new SecurityHandlerInterceptor();
	}

	@Bean
	public SessionAttributeProcessor sessionAttributeProcessor() {
		return new SessionAttributeProcessor();
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(sessionAttributeProcessor());
	}

	@Override
	public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
		returnValueHandlers.add(sessionAttributeProcessor());
	}

	@Bean
	public SimpleMappingExceptionResolver simpleMappingExceptionResolver() {
		SimpleMappingExceptionResolver exceptionResolver = new SimpleMappingExceptionResolver();
		Properties mappings = new Properties();
		mappings.setProperty("AuthenticationException", "login");

		Properties statusCodes = new Properties();
		mappings.setProperty("login", String.valueOf(HttpServletResponse.SC_UNAUTHORIZED));

		exceptionResolver.setExceptionMappings(mappings);
		exceptionResolver.setStatusCodes(statusCodes);
		return exceptionResolver;
	}

	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverter(categoryConverter());
	}

	@Bean
	public GenericConverter categoryConverter() {
		return new StringToEntityConverter(Category.class);
	}

	@Bean
	@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
	public Cart cart() {
		return new Cart();
	}
}
