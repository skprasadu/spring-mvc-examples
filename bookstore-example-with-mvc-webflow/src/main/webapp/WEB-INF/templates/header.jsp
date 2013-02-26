<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


	<jsp:directive.page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" />

	<div class="header">
		<div class="logo">
			<spring:url value="/index.jsp" var="home" />
			<a href="${home}">
				<spring:url value="/public/resources/images/logo.gif" var="logo" /> 
				<img src="${logo }" alt="" title="" border="0" />
			</a>
		</div>
		<div class="nav">
			<ul>
				<li>
					<a href="${home}"><spring:message code="nav.home"/></a>
				</li>
				<li>
				    <spring:url value="/public/createOrders" var="createOrder" />
					<a href="${createOrder}"><spring:message code="nav.books"/></a>
				</li>
				<li>
					<sec:authorize access="fullyAuthenticated">
						<spring:url value="/secured/ordersOverview" var="ordersOverview" />
						<a href="${ordersOverview}"><spring:message code="nav.ordersOverview"/></a>
					</sec:authorize>
				</li>
				<sec:authorize access="! authenticated">
					<li>
						<spring:url value="/public/authentication/login.htm" var="login" />
						<a href="${login}"><spring:message code="nav.login"/></a>
					</li>
				</sec:authorize>
				<sec:authorize access="hasRole('ROLE_ADMIN') or hasRole('ROLE_AUTHOR') ">
					<li>
						<spring:url value="/secured/manageBooks/manageBooks.htm" var="manageBooks" /> 
						<a href="${manageBooks}"><spring:message code="nav.manageBooks"/></a>
					</li>
				</sec:authorize>
				<sec:authorize access="fullyAuthenticated">
					<li>
	                	<spring:url value="/logout" var="logout" />
    	            	<a href="${logout}"><spring:message code="nav.logout"/></a>
        	        </li>
				</sec:authorize>
			</ul>
			<ul style="float: right;">
			 	<c:url value="/public/resources/images/gb.gif" var="gb"/>
			 	<c:url value="/public/resources/images/nl.gif" var="nl"/>
                <li><a href="?lang=en" class="selected"><img src="${gb}" alt="" title="" border="0" /></a></li>
                <li><a href="?lang=nl"><img src="${nl}" alt="" title="" border="0" /></a></li>
            </ul>
		</div>
	</div>