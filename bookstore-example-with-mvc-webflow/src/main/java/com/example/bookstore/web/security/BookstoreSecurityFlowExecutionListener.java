package com.example.bookstore.web.security;

import java.io.IOException;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ClassUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.expression.Expression;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.webflow.execution.RequestContextHolder;
import org.springframework.webflow.security.SecurityFlowExecutionListener;
import org.springframework.webflow.security.SecurityRule;

/**
 * Custom {@link SecurityFlowExecutionListener} falling back to the super behavior in case expressions are not used in
 * combination with Spring Security. If expressions are used, contacts the {@link AccessDecisionManager} the right way
 * so that the attributes can be treated as SpEL expressions.
 * <p/>
 * 
 * <b>Note</b>: Before using this class, please check <a href="https://jira.springsource.org/browse/SWF-1508"/> to make
 * sure this issue isn't resolved yet. This class will become obsolete once Web Flow has adapted to the latest Spring
 * Security features.
 * 
 * 
 * 
 * 
 */
public class BookstoreSecurityFlowExecutionListener extends SecurityFlowExecutionListener implements
		ApplicationContextAware {

	private DefaultWebSecurityExpressionHandler defaultWebSecurityExpressionHandler;

	@Override
	protected void decide(SecurityRule rule, Object object) {
		// If we don't have an AccessDecisionManager or we are not using
		// expressions
		// fall back to the the default behavior
		if (getAccessDecisionManager() == null || defaultWebSecurityExpressionHandler == null) {
			super.decide(rule, object);
			return;
		}

		// We are using expressions and want to contact the
		// AccessDecisionManager the right way
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		@SuppressWarnings("unchecked")
		Collection<ConfigAttribute> configAttributes = buildWebExpressionConfigAttribute(getConfigAttributes(rule));

		HttpServletRequest request = (HttpServletRequest) RequestContextHolder.getRequestContext().getExternalContext()
				.getNativeRequest();
		HttpServletResponse response = (HttpServletResponse) RequestContextHolder.getRequestContext()
				.getExternalContext().getNativeResponse();

		getAccessDecisionManager().decide(authentication, new FilterInvocation(request, response, new FilterChain() {
			@Override
			public void doFilter(ServletRequest request, ServletResponse response) throws IOException, ServletException {
				throw new UnsupportedOperationException();
			}
		}), configAttributes);

	}

	/**
	 * Convert the expressions which are entered in the "attributes" attribute of the flow &lt;security&gt; element to
	 * {@link ConfigAttribute}s. Unfortunately, the type of {@link ConfigAttribute} required is a class with package
	 * visibility (org.springframework.security.web.access.expression .WebExpressionConfigAttribute) so we need to use
	 * some reflection to instantiate an instance of that class. When done so, we pass along a Spring {@link Expression}
	 * created by the {@link DefaultWebSecurityExpressionHandler} from the "attributes" String provided by this flow
	 */
	private Collection<ConfigAttribute> buildWebExpressionConfigAttribute(Collection<ConfigAttribute> configAttributes) {
		Collection<ConfigAttribute> result = new ArrayList<ConfigAttribute>();

		for (ConfigAttribute configAttribute : configAttributes) {
			result.add(createWebExpressionConfigAttribute(configAttribute.getAttribute()));
		}
		return result;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

		this.defaultWebSecurityExpressionHandler = applicationContext
				.getBean(DefaultWebSecurityExpressionHandler.class);

		for (AccessDecisionManager accessDecisionManager : getAccessDecisionManager(applicationContext,
				new ArrayList<AccessDecisionManager>())) {
			try {
				if (accessDecisionManager.supports(createWebExpressionConfigAttribute(""))) {
					setAccessDecisionManager(accessDecisionManager);
				}
			} catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		}
	}

	private List<AccessDecisionManager> getAccessDecisionManager(ApplicationContext applicationContext,
			List<AccessDecisionManager> accessDecisionManagers) {
		accessDecisionManagers.addAll(applicationContext.getBeansOfType(AccessDecisionManager.class).values());

		if (applicationContext.getParent() != null) {
			getAccessDecisionManager(applicationContext.getParent(), accessDecisionManagers);
		}
		return accessDecisionManagers;
	}

	private ConfigAttribute createWebExpressionConfigAttribute(String expression) {
		try {
			Constructor<?> constructor = ClassUtils.getClass(
					"org.springframework.security.web.access.expression.WebExpressionConfigAttribute").getConstructor(
					Expression.class);
			AccessibleObject.setAccessible(new AccessibleObject[] { constructor }, true);
			return (ConfigAttribute) constructor.newInstance(defaultWebSecurityExpressionHandler.getExpressionParser()
					.parseExpression(expression));
		} catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}
}
