<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

	<form:form id="selectBookForm" modelAttribute="orderForm" action="${flowExecutionUrl}">
		<table style="width: 100%">
			<tr >
				<td colspan="2">
					<form:errors path="books" cssClass="error"/>
				</td>
			</tr>
			<tr style="height: 10px;"/>
			<tr>
				<td><spring:message code="label.page.books.select.book" /></td>
				<td>
					<form:select path="book" items="${selectableBooks}" itemLabel="title" itemValue="id"/>
				</td>
			</tr>
			<tr>
				<td><spring:message code="label.page.books.select.quantity"/></td>
				<td>
					<form:input path="quantity" />
					<span style="margin-left: 5px">
						<form:errors path="quantity" cssClass="error"/>
					</span>
				</td>
			</tr>
			<tr height="10px"/>
			<tr align="right">
				<td colspan="2">
					<button type="submit" id="add" name="_eventId_add">
						<spring:message code="label.page.books.add.book"/>
					</button>		
				</td>
			</tr>
		</table>
		
		<p/>
			<h3>
				<spring:message code="label.page.books.selected.books"/>
			</h3>
			<tiles:insertAttribute name="selectedBooks"/>
			
		<div align="right" style="margin-bottom: 20px;" >
			<button type="submit" id="previoys" name="_eventId_previous"><spring:message code="button.previous"/></button>
			<button type="submit" id="previoys" name="_eventId_cancel"><spring:message code="button.cancel"/></button>
			<button type="submit" id="next" name="_eventId_next"><spring:message code="button.next"/></button>
		</div>
	</form:form>
	
	<script type="text/javascript">
		dojo.addOnLoad(function() {
			Spring.addDecoration(new Spring.AjaxEventDecoration({
				elementId : "add",
				event : "onclick",
				formId: "selectBookForm"
			}));
		});
	</script>
	
		<!-- ============================== JQUERY ALTERNATIVE ============================== 
		<script type="text/javascript">
			$(function () {
				var submit = $('#add');

				submit.click(function() {
					var form = $('#selectBookForm');
					var event = submit.attr('name');
					var data = form.serialize() + '&amp;' + event + '=' + event + '&amp;ajaxSource=' + submit.attr('id');

					$.ajax({
						type: "POST",
						dataType: 'text',
				    	url: form.attr( 'action' ),
				    	data: data,
			        	success : function(result) {
				        	$('#selectedBooks').replaceWith(result);
				        }
					});
					return false;
				});
			});			
    	   </script>
		 -->