<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:if test="${exception ne null}">
    <div class="error">
        <spring:message code="${exception.code}" text="${exception.message}" htmlEscape="true"/>
    </div>
</c:if>
<form action="<c:url value="/login"/>" method="post">
    <fieldset>
        <legend><spring:message code="login.title" /></legend>
        <table>
        <tr>
            <td><spring:message code="account.username"/></td>
            <td><input type="text" id="username" name="username" placeholder="<spring:message code="account.username"/>"/></td>
        </tr>
        <tr>
            <td><spring:message code="account.password"/></td>
            <td><input type="password" id="password" name="password" placeholder="<spring:message code="account.password"/>"/></td>
        </tr>
        <tr><td colspan="2" align="center"><button id="loginButton"><spring:message code="button.login"/></button></td></tr>
        </table>
    </fieldset>
</form>
