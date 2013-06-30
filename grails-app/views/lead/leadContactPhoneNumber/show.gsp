
<%@ page import="com.cogda.multitenant.LeadContactPhoneNumber" %>
<!doctype html>
<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="layout" content="kickstart" />
	<g:set var="entityName" value="${message(code: 'leadContactPhoneNumber.label', default: 'LeadContactPhoneNumber')}" />
	<title><g:message code="default.show.label" args="[entityName]" /></title>
</head>

<body>

<section id="show-leadContactPhoneNumber" class="first">

    <h2><g:message code="default.show.label" args="[entityName]" /></h2>

	<table class="table">
		<tbody>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="leadContactPhoneNumber.primaryPhoneNumber.label" default="Primary Phone Number" /></td>
				
				<td valign="top" class="value"><g:formatBoolean boolean="${leadContactPhoneNumberInstance?.primaryPhoneNumber}" /></td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="leadContactPhoneNumber.description.label" default="Description" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: leadContactPhoneNumberInstance, field: "description")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="leadContactPhoneNumber.phoneNumber.label" default="Phone Number" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: leadContactPhoneNumberInstance, field: "phoneNumber")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="leadContactPhoneNumber.leadContact.label" default="Lead Contact" /></td>
				
				<td valign="top" class="value"><g:link controller="leadContact" action="show" id="${leadContactPhoneNumberInstance?.leadContact?.id}">${leadContactPhoneNumberInstance?.leadContact?.encodeAsHTML()}</g:link></td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="leadContactPhoneNumber.tenantId.label" default="Tenant Id" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: leadContactPhoneNumberInstance, field: "tenantId")}</td>
				
			</tr>
		
		</tbody>
	</table>
</section>

</body>

</html>
