<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<sec:authorize access="fullyAuthenticated">
	<c:if test="${not empty param.authenticationOk}">
		<div id="authenticationOk"
			style="color: green; display: block; margin-left: 15px; margin-bottom: 10px;">
			<table>
				<tr>
					<td>
						<ul style="list-style-type: disc">
							<li>
								<h3>

									<sec:authentication property="principal.username"
										var="username" scope="request" />
									<spring:message code="label.page.main.authentication.ok"
										arguments="${username}" />
								</h3>
							</li>
						</ul>
					</td>
				</tr>
			</table>
		</div>
	</c:if>
</sec:authorize>

<script>
		dojo.addOnLoad(function(){
			function fadeIt() {
				if(dojo.byId("authenticationOk")){
  	    			dojo.style("authenticationOk", "opacity", "1");
					var fadeOutArgs = {node: "authenticationOk", duration: 15000};
           			dojo.fadeOut(fadeOutArgs).play();
				}
			}
   			fadeIt();
		});
	</script>