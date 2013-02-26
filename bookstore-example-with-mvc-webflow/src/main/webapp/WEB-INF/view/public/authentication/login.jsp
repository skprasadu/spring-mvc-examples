<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<spring:url value="/j_spring_security_check" var="login" />
<form action="${login}" method="POST">
	<table style="width: 100%">
		<tr>
			<td><spring:message code="label.page.login.username" /></td>
			<td><input type="text" name="j_username" /></td>
			<td><spring:message code="label.page.login.password" /></td>
			<td><input type="password" name="j_password" /></td>
		</tr>
		<tr>
			<td colspan="4">
				<c:if test="${not empty param.authenticationNok}">
					<font color="red"> <spring:message code="label.login.failed" arguments="${SPRING_SECURITY_LAST_EXCEPTION.message}" />
					</font>
				</c:if>
			</td>
		</tr>
	</table>

	<div align="right" style="margin-bottom: 20px; margin-top: 10px">
		<button type="submit" id="login">
			<spring:message code="label.page.login.login" />
		</button>
	</div>
</form>