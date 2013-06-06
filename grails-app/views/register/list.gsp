
<%@ page import="com.cogda.domain.onboarding.Registration" %>
<!doctype html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="layout" content="kickstart" />
	<g:set var="entityName" value="${message(code: 'registration.label', default: 'Registration')}" />
	<title><g:message code="default.list.label" args="[entityName]" /></title>
</head>

<body>
	
<section id="list-registration" class="first">

	<table class="table table-bordered">
		<thead>
			<tr>
			
				<g:sortableColumn property="firstName" title="${message(code: 'registration.firstName.label', default: 'First Name')}" />
			
				<g:sortableColumn property="lastName" title="${message(code: 'registration.lastName.label', default: 'Last Name')}" />
			
				<g:sortableColumn property="username" title="${message(code: 'registration.username.label', default: 'Username')}" />
			
				<g:sortableColumn property="emailAddress" title="${message(code: 'registration.emailAddress.label', default: 'Email Address')}" />
			
				<g:sortableColumn property="companyName" title="${message(code: 'registration.companyName.label', default: 'Company Name')}" />
			
			</tr>
		</thead>
		<tbody>
		<g:each in="${registrationInstanceList}" status="i" var="registrationInstance">
			<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
			
				<td><g:link action="show" id="${registrationInstance.id}">${fieldValue(bean: registrationInstance, field: "firstName")}</g:link></td>
			
				<td>${fieldValue(bean: registrationInstance, field: "lastName")}</td>
			
				<td>${fieldValue(bean: registrationInstance, field: "username")}</td>
			
				<td>${fieldValue(bean: registrationInstance, field: "emailAddress")}</td>
			
				<td>${fieldValue(bean: registrationInstance, field: "companyName")}</td>
			
			</tr>
		</g:each>
		</tbody>
	</table>
	<div class="pagination">
		<bs:paginate total="${registrationInstanceTotal}" />
	</div>
</section>

</body>

</html>
