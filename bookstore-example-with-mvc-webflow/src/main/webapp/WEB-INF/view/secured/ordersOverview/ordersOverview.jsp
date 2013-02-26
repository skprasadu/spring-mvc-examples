<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

	<c:if test="${orderId !=null}">
		<div id="orderSuccess"
			style="color: green; display: block; margin-left: 15px; margin-bottom: 10px;">
			<table>
				<tr>
					<td>
						<ul style="list-style-type: disc">
							<li><h3>
									<spring:message code="label.page.ordersoverview.order.success"
										arguments="${orderId}" />
								</h3></li>
						</ul>
					</td>
				</tr>
			</table>
		</div>
	</c:if>

	<h3>Order overview:</h3>
	<table id="ordersTable" style="width: 100%;" rules="groups">
		<thead>
			<tr>
				<th align="left"><spring:message code="label.page.ordersoverview.order" /></th>
				<th align="left"><spring:message code="label.page.ordersoverview.books" /></th>
				<th align="left"><spring:message code="label.page.ordersoverview.order.date" /></th>
				<th align="left"><spring:message code="label.page.ordersoverview.total.price" /></th>
				<th align="left"><spring:message code="label.page.ordersoverview.total.detail" /></th>
			</tr>
		</thead>
		<tbody>
			<tr height="10px" />
			<c:forEach items="${orders}" var="order" varStatus="status">
				<tr>
					<td>${order.id}</td>
					<td>${fn:length(order.orderDetails)}</td>
					<td><spring:eval expression="order.orderDate" /></td>
					<td>${order.totalOrderPrice}</td>
					<td>
						<form:form id="orderDetailForm_${status.index}" action="${flowExecutionUrl}">
							<input type="hidden" name="index" value="${status.index}" />
							<a name="_eventId_showOrderDetail" id="orderDetailLink_${status.index}" href="#">View</a>
						</form:form>
					</td>
				</tr>
			</c:forEach>
			<tr height="20px" />
		</tbody>
	</table>

	<script>
		dojo.addOnLoad(function() {
			if (dojo.byId("orderSuccess")) {
				dojo.style("orderSuccess", "opacity", "1");
				var fadeOutArgs = {
					node : "orderSuccess",
					duration : 15000
				};
				dojo.fadeOut(fadeOutArgs).play();
			}
			dojo.query("[id^='orderDetailLink_']").forEach(function(element) {
				var index = element.id.substr(element.id.lastIndexOf('_')+1,element.id.length);
				
				Spring.addDecoration(new Spring.AjaxEventDecoration({
					elementId : element.id,
					event : "onclick",
					formId : "orderDetailForm_"+index
				}));
			});

		});
	</script>