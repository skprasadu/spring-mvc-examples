package com.example.bookstore.web.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.format.FormatterRegistry;
import org.springframework.js.ajax.AjaxUrlBasedViewResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import org.springframework.web.servlet.view.tiles2.TilesConfigurer;
import org.springframework.webflow.mvc.view.FlowAjaxTilesView;

import com.example.bookstore.converter.StringToEntityConverter;
import com.example.bookstore.domain.Book;
import com.example.bookstore.domain.Category;
import com.example.bookstore.web.interceptor.CommonDataHandlerInterceptor;

/**
 * Spring MVC configuration
 * 
 * 
 * 
 * 
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = { "com.example.bookstore.web" })
@ImportResource("classpath:/spring/spring-security.xml")
public class WebMvcContextConfiguration extends WebMvcConfigurerAdapter {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/public/resources/**/*")
				.addResourceLocations("classpath:/META-INF/web-resources/");
	}

	// -- Start Locale Support (I18N) --//

	/**
	 * The {@link LocaleChangeInterceptor} allows for the locale to be changed. It provides a <code>paramName</code>
	 * property which sets the request parameter to check for changing the language, the default is <code>locale</code>.
	 * @return the {@link LocaleChangeInterceptor}
	 */
	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
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
		messageSource.setBasenames(new String[] { "messages", "org.springframework.security.messages" });
		messageSource.setUseCodeAsDefaultMessage(true);
		return messageSource;
	}

	// -- End Locale Support (I18N) --//

	@Bean
	public ViewResolver tilesViewResolver() {
		UrlBasedViewResolver urlBasedViewResolver = new AjaxUrlBasedViewResolver();
		urlBasedViewResolver.setOrder(1);
		urlBasedViewResolver.setViewClass(FlowAjaxTilesView.class);
		return urlBasedViewResolver;
	}

	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver internalResourceViewResolver = new InternalResourceViewResolver();
		internalResourceViewResolver.setOrder(2);
		internalResourceViewResolver.setPrefix("/WEB-INF/view/");
		internalResourceViewResolver.setSuffix(".jsp");
		internalResourceViewResolver.setViewClass(JstlView.class);
		return internalResourceViewResolver;
	}

	@Bean
	public StringToEntityConverter bookConverter() {
		return new StringToEntityConverter(Book.class);
	}

	@Bean
	public StringToEntityConverter categoryConverter() {
		return new StringToEntityConverter(Category.class);
	}

	@Bean
	public TilesConfigurer tilesConfigurer() {
		TilesConfigurer tilesConfigurer = new TilesConfigurer();
		tilesConfigurer.setDefinitions(new String[] { "/WEB-INF/tiles/tiles-configuration.xml" });
		return tilesConfigurer;
	}

	@Bean
	public CommonDataHandlerInterceptor commonDataHandlerInterceptor() {
		return new CommonDataHandlerInterceptor();
	}

	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverter(bookConverter());
		registry.addConverter(categoryConverter());
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(commonDataHandlerInterceptor());
		registry.addInterceptor(localeChangeInterceptor());
	}
}
