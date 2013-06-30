
<%@ page import="com.cogda.multitenant.LeadContactAddress" %>
<!doctype html>
<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="layout" content="kickstart" />
	<g:set var="entityName" value="${message(code: 'leadContactAddress.label', default: 'LeadContactAddress')}" />
	<title><g:message code="default.show.label" args="[entityName]" /></title>
</head>

<body>

<section id="show-leadContactAddress" class="first">

    <h2><g:message code="default.show.label" args="[entityName]" /></h2>

	<table class="table">
		<tbody>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="leadContactAddress.primaryAddress.label" default="Primary Address" /></td>
				
				<td valign="top" class="value"><g:formatBoolean boolean="${leadContactAddressInstance?.primaryAddress}" /></td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="leadContactAddress.address.label" default="Address" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: leadContactAddressInstance, field: "address")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="leadContactAddress.dateCreated.label" default="Date Created" /></td>
				
				<td valign="top" class="value"><g:formatDate date="${leadContactAddressInstance?.dateCreated}" /></td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="leadContactAddress.lastUpdated.label" default="Last Updated" /></td>
				
				<td valign="top" class="value"><g:formatDate date="${leadContactAddressInstance?.lastUpdated}" /></td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="leadContactAddress.leadContact.label" default="Lead Contact" /></td>
				
				<td valign="top" class="value"><g:link controller="leadContact" action="show" id="${leadContactAddressInstance?.leadContact?.id}">${leadContactAddressInstance?.leadContact?.encodeAsHTML()}</g:link></td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="leadContactAddress.tenantId.label" default="Tenant Id" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: leadContactAddressInstance, field: "tenantId")}</td>
				
			</tr>
		
		</tbody>
	</table>
</section>

</body>

</html>
