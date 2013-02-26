<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

	<c:if test="${not empty actionSuccess}">
		<div id="actionSuccess"
			style="color: green; display: block; margin-bottom: 10px;">
			<table>
				<tr>
					<td>
						<ul style="list-style-type: disc">
							<li>
								<h3>
									<spring:message code="label.page.managebooks.${actionSuccess}.added"/>
								</h3>
							</li>
						</ul>
					</td>
				</tr>
			</table>
		</div>
	</c:if>

	<sec:authorize access="hasRole('PERM_ADD_CATEGORIES')">
		<spring:url value="/secured/addCategory.htm" var="addCategory"/>
		<form:form  action="${addCategory}" commandName="manageCategoryForm">
			<table style="width: 100%">
				<tr>
					<td width="30%"><spring:message code="label.page.managebooks.category.add"/></td>
					<td>
						<input type="text" name="category" path="category"/>
						<form:errors path="category" cssClass="error"/>
						<form:errors/>
					</td>
				</tr>
			</table>
			<div align="right" style="margin-bottom: 20px; margin-top: 10px" >
				<button type="submit" id="add"><spring:message code="label.page.managebooks.add.category"/></button>
				<button type="reset" id="clear"><spring:message code="label.page.managebooks.clear.category"/></button>
			</div>
		</form:form>
	</sec:authorize>
	 <sec:authorize access="hasRole('PERM_ADD_BOOKS')">
		<spring:url value="/secured/addBooks.htm" var="addBook"/>
		<form:form modelAttribute="manageBookForm" action="${addBook}" method="POST">
			<table style="width: 100%">
				<tr>
					<td width="30%"><spring:message code="label.page.managebooks.book.category"/></td>
					<td>
						<form:select path="category" items="${manageBookForm.selectableCategories}" itemLabel="name" itemValue="id"/>
						<form:errors path="category" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td width="30%"><spring:message code="label.page.managebooks.book.title"/></td>
					<td>
						<form:input path="title"/>
						<form:errors path="title" cssClass="error"/>
					</td>
					</tr>
				<tr>
					<td width="30%"><spring:message code="label.page.managebooks.book.description"/></td>
					<td>
						<form:input path="description"/>
						<form:errors path="description" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td width="30%"><spring:message code="label.page.managebooks.book.price"/></td>
					<td>
						<form:input path="price"/>
						<form:errors path="price" cssClass="error"/>
					</td>	
				</tr>
				<tr>
					<td width="30%"><spring:message code="label.page.managebooks.book.year"/></td>
					<td>
						<form:input path="year"/>
						<form:errors path="year" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td width="30%"><spring:message code="label.page.managebooks.book.author"/></td>
					<td>
						<form:input path="author"/>
						<form:errors path="author" cssClass="error"/>
					</td>
				</tr>
			</table>
			<div align="right" style="margin-bottom: 20px; margin-top: 10px" >
				<button type="submit" id="add"><spring:message code="label.page.managebooks.book.add"/></button>
				<button type="reset" id="clear"><spring:message code="label.page.managebooks.book.clear"/></button>
			</div>
		</form:form>
	</sec:authorize>
	
	<script>
		dojo.addOnLoad(function(){
			function fadeIt() {
				if(dojo.byId("actionSuccess")){
  	    			dojo.style("actionSuccess", "opacity", "1");
					var fadeOutArgs = {node: "actionSuccess", duration: 15000};
           			dojo.fadeOut(fadeOutArgs).play();
				}
			}
   			fadeIt();
		});
	</script>