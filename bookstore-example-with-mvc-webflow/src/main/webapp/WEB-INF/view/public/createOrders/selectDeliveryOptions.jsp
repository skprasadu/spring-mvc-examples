<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

	<form:form modelAttribute="orderForm" action="${flowExecutionUrl}">
		<table style="width: 100%">
			<tr>
				<td><spring:message code="label.page.selectdeliveryoptions.select.order.date"/></td>
				<td><form:input path="orderDate" disabled="true" /></td>
			</tr>
			<tr>
				<td><spring:message code="label.page.selectdeliveryoptions.select.delivery.date" /></td>
				<td>
					<form:input path="deliveryDate" />
					<span style="margin-left: 5px">
						<form:errors path="deliveryDate" cssClass="error"/>
					</span>
					<script type="text/javascript">
							Spring.addDecoration(new Spring.ElementDecoration({
								elementId : "deliveryDate",
								widgetType : "dijit.form.DateTextBox",
								widgetAttrs : { datePattern : "MM-dd-yyyy", required : true }}));  
					</script>
				</td>
			</tr>
		</table>
		
		<div align="right" style="margin-bottom: 20px; margin-top: 10px" >
			<button type="submit" id="previous" name="_eventId_previous"><spring:message code="button.previous"/></button>
			<button type="submit" id="cancel" name="_eventId_cancel"><spring:message code="button.cancel"/></button>
			<button type="submit" id="finish" name="_eventId_finish"><spring:message code="label.page.selectdeliveryoptions.order"/></button>
		</div>
	</form:form>