<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<div id="selectedBooks" style="margin-top: 10px; margin-bottom: 10px;">
	<table style="width: 100%;" rules="groups">
		<thead>
			<tr>
				<th width="80%" align="left">
					<spring:message code="label.page.books.book.name"/>
				</th>
				<th width="20%" align="left">
					<spring:message code="label.page.books.book.quantity"/>
				</th>
			</tr>
		</thead>
		<tbody>
			<tr height="10px" />
			<c:forEach items="${orderForm.books}" var="book">
				<tr>
					<td>${book.key.title}</td>
					<td>${book.value}</td>
				</tr>
			</c:forEach>
			<tr height="20px" />
		</tbody>
	</table>
</div>
