<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

   <div class="navbar navbar-fixed-top">
      <div class="navbar-inner">
        <div class="container">
          <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </a>
          <a class="brand" href="#">Bookstore</a>
          <div class="nav-collapse">
            <ul class="nav">
            <li class="selected"><a href="${homeUrl}"><spring:message code="nav.home"/></a></li>
            <li><a href="<c:url value="/book/search"/>"><spring:message code="nav.books"/></a></li>
            <li><a id="account" href="<c:url value="/customer/account"/>"><spring:message code="nav.account"/></a></li>
            <li><a href="<c:url value="/cart/checkout"/>"><spring:message code="nav.checkout"/></a></li>
            <c:if test="${sessionScope.account eq null}">
                <li><a href="<c:url value="/customer/register"/>"><spring:message code="nav.register"/></a></li>
                <li><a id="login" href="<c:url value="/login"/>"><spring:message code="nav.login"/></a></li>
            </c:if>
            <c:if test="${sessionScope.account ne null}">
                <li><a href="<c:url value="/logout"/>"><spring:message code="nav.logout"/></a></li>
            </c:if>
            </ul>
          </div><!--/.nav-collapse -->
        </div>
      </div>
    </div>
