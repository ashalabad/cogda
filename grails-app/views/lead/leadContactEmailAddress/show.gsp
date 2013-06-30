
<%@ page import="com.cogda.multitenant.LeadContactEmailAddress" %>
<!doctype html>
<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="layout" content="kickstart" />
	<g:set var="entityName" value="${message(code: 'leadContactEmailAddress.label', default: 'LeadContactEmailAddress')}" />
	<title><g:message code="default.show.label" args="[entityName]" /></title>
</head>

<body>

<section id="show-leadContactEmailAddress" class="first">

    <h2><g:message code="default.show.label" args="[entityName]" /></h2>

	<table class="table">
		<tbody>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="leadContactEmailAddress.emailAddress.label" default="Email Address" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: leadContactEmailAddressInstance, field: "emailAddress")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="leadContactEmailAddress.primaryEmailAddress.label" default="Primary Email Address" /></td>
				
				<td valign="top" class="value"><g:formatBoolean boolean="${leadContactEmailAddressInstance?.primaryEmailAddress}" /></td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="leadContactEmailAddress.dateCreated.label" default="Date Created" /></td>
				
				<td valign="top" class="value"><g:formatDate date="${leadContactEmailAddressInstance?.dateCreated}" /></td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="leadContactEmailAddress.lastUpdated.label" default="Last Updated" /></td>
				
				<td valign="top" class="value"><g:formatDate date="${leadContactEmailAddressInstance?.lastUpdated}" /></td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="leadContactEmailAddress.leadContact.label" default="Lead Contact" /></td>
				
				<td valign="top" class="value"><g:link controller="leadContact" action="show" id="${leadContactEmailAddressInstance?.leadContact?.id}">${leadContactEmailAddressInstance?.leadContact?.encodeAsHTML()}</g:link></td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="leadContactEmailAddress.tenantId.label" default="Tenant Id" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: leadContactEmailAddressInstance, field: "tenantId")}</td>
				
			</tr>
		
		</tbody>
	</table>
</section>

</body>

</html>
