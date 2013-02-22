package com.example.bookstore.web.method.support;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * {@code HandlerMethodReturnValueHandler} and {@code HandlerMethodArgumentResolver} implementation to 
 * handle a {@code SessionAttribute}.
 * 
 * 
 * 
 */
public class SessionAttributeProcessor implements HandlerMethodReturnValueHandler, HandlerMethodArgumentResolver {

    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return returnType.getMethodAnnotation(SessionAttribute.class) != null;
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest) throws Exception {

        SessionAttribute annotation = returnType.getMethodAnnotation(SessionAttribute.class);
        webRequest.setAttribute(annotation.value(), returnValue, WebRequest.SCOPE_SESSION);
        exposeModelAttribute(annotation, returnValue, mavContainer);
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(SessionAttribute.class);
    }

    private void exposeModelAttribute(SessionAttribute annotation, Object value, ModelAndViewContainer mavContainer) {
        if (annotation.exposeAsModelAttribute()) {
            mavContainer.addAttribute(annotation.value(), value);
        }
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        SessionAttribute annotation = parameter.getParameterAnnotation(SessionAttribute.class);

        Object value = webRequest.getAttribute(annotation.value(), WebRequest.SCOPE_SESSION);
        if (value == null && annotation.required()) {
            throw new MissingServletRequestParameterException(annotation.value(), parameter.getParameterType()
                    .getName());
        }
        exposeModelAttribute(annotation, value, mavContainer);

        return value;
    }
}
