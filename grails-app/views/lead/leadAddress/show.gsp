
<%@ page import="com.cogda.multitenant.LeadAddress" %>
<!doctype html>
<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="layout" content="kickstart" />
	<g:set var="entityName" value="${message(code: 'leadAddress.label', default: 'LeadAddress')}" />
	<title><g:message code="default.show.label" args="[entityName]" /></title>
</head>

<body>

<section id="show-leadAddress" class="first">

    <h2><g:message code="default.show.label" args="[entityName]" /></h2>

	<table class="table">
		<tbody>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="leadAddress.primaryAddress.label" default="Primary Address" /></td>
				
				<td valign="top" class="value"><g:formatBoolean boolean="${leadAddressInstance?.primaryAddress}" /></td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="leadAddress.address.label" default="Address" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: leadAddressInstance, field: "address")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="leadAddress.dateCreated.label" default="Date Created" /></td>
				
				<td valign="top" class="value"><g:formatDate date="${leadAddressInstance?.dateCreated}" /></td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="leadAddress.lastUpdated.label" default="Last Updated" /></td>
				
				<td valign="top" class="value"><g:formatDate date="${leadAddressInstance?.lastUpdated}" /></td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="leadAddress.lead.label" default="Lead" /></td>
				
				<td valign="top" class="value"><g:link controller="lead" action="show" id="${leadAddressInstance?.lead?.id}">${leadAddressInstance?.lead?.encodeAsHTML()}</g:link></td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="leadAddress.leadAddressType.label" default="Lead Address Type" /></td>
				
				<td valign="top" class="value"><g:link controller="leadAddressType" action="show" id="${leadAddressInstance?.leadAddressType?.id}">${leadAddressInstance?.leadAddressType?.encodeAsHTML()}</g:link></td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="leadAddress.tenantId.label" default="Tenant Id" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: leadAddressInstance, field: "tenantId")}</td>
				
			</tr>
		
		</tbody>
	</table>
</section>

</body>

</html>
