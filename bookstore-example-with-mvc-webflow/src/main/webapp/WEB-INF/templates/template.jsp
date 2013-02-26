<!DOCTYPE HTML>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>Bookstore - </title>
		<spring:url value="/public/resources/css/style.css" var="bookstoreCss" />
		<link rel="stylesheet" type="text/css" href="${bookstoreCss}" />
		<spring:url value="/public/resources/dijit/themes/tundra/tundra.css"	var="tundraCss" />
		<link type="text/css" rel="stylesheet" href="${tundraCss}" />

		<spring:url value="/public/resources/dojo/dojo.js"	var="dojo" />
		<script type="text/javascript" src="${dojo}"><!----></script>
		<spring:url value="/public/resources/spring/Spring.js"	var="springJs" />
		<script type="text/javascript" src="${springJs}"> <!----></script>
		<spring:url value="/public/resources/spring/Spring-Dojo.js" var="springDojo" />
		<script type="text/javascript" src="${springDojo}"><!----></script>
	</head>

	<body class="tundra">
		<div id="wrap">
			<tiles:insertAttribute name="header"/>

       	<div class="center_content">
			<div class="left_content">
            	<tiles:insertAttribute name="content"/>            
        	</div><!--end of left content-->
        
        	<div class="right_content">
      	     <div class="right_box">
			       <div class="title">
						<spring:url value="/public/resources/images/bullet4.gif" var="bullet4"/>
						<img src="${bullet4}" alt="" title="" />
						<spring:message code="main.title.randombooks"/>
					</div> 
                    <c:forEach items="${randomBooks}" var="book">
                        <div class="new_prod_box" style="width: 100%">
                            <c:url value="/public/book/detail/${book.id}" var="bookUrl" />
                            <a href="${bookUrl}">${book.title}</a>
                            <div class="new_prod_img" style="width: 100%">
                            <c:url value="/public/resources/images/books/${book.isbn}/book_front_cover.png" var="bookImage"/>
                            <a href="${bookUrl}	"><img class="new_prod_img" src="${bookImage}" alt="${book.title}" title="${book.title}" class="thumb" border="0" width="100px"/></a>
                            </div>           
                        </div>
                    </c:forEach>
           	  		</div>
           	  </div>
			<div class="clear"></div>
			</div><!--end of center content-->
			<tiles:insertAttribute name="footer"/>         
    	</div>
	</body>
</html>