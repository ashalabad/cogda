
<%@ page import="com.cogda.multitenant.LeadContact" %>
<!doctype html>
<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="layout" content="kickstart" />
	<g:set var="entityName" value="${message(code: 'leadContact.label', default: 'LeadContact')}" />
	<title><g:message code="default.show.label" args="[entityName]" /></title>
</head>

<body>

<section id="show-leadContact" class="first">

    <h2><g:message code="default.show.label" args="[entityName]" /></h2>

	<table class="table">
		<tbody>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="leadContact.firstName.label" default="First Name" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: leadContactInstance, field: "firstName")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="leadContact.middleName.label" default="Middle Name" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: leadContactInstance, field: "middleName")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="leadContact.lastName.label" default="Last Name" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: leadContactInstance, field: "lastName")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="leadContact.dateCreated.label" default="Date Created" /></td>
				
				<td valign="top" class="value"><g:formatDate date="${leadContactInstance?.dateCreated}" /></td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="leadContact.lastUpdated.label" default="Last Updated" /></td>
				
				<td valign="top" class="value"><g:formatDate date="${leadContactInstance?.lastUpdated}" /></td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="leadContact.lead.label" default="Lead" /></td>
				
				<td valign="top" class="value"><g:link controller="lead" action="show" id="${leadContactInstance?.lead?.id}">${leadContactInstance?.lead?.encodeAsHTML()}</g:link></td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="leadContact.leadContactAddresses.label" default="Lead Contact Addresses" /></td>
				
				<td valign="top" style="text-align: left;" class="value">
					<ul>
					<g:each in="${leadContactInstance.leadContactAddresses}" var="l">
						<li><g:link controller="leadContactAddress" action="show" id="${l.id}">${l?.encodeAsHTML()}</g:link></li>
					</g:each>
					</ul>
				</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="leadContact.leadContactEmailAddresses.label" default="Lead Contact Email Addresses" /></td>
				
				<td valign="top" style="text-align: left;" class="value">
					<ul>
					<g:each in="${leadContactInstance.leadContactEmailAddresses}" var="l">
						<li><g:link controller="leadContactEmailAddress" action="show" id="${l.id}">${l?.encodeAsHTML()}</g:link></li>
					</g:each>
					</ul>
				</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="leadContact.leadContactPhoneNumbers.label" default="Lead Contact Phone Numbers" /></td>
				
				<td valign="top" style="text-align: left;" class="value">
					<ul>
					<g:each in="${leadContactInstance.leadContactPhoneNumbers}" var="l">
						<li><g:link controller="leadContactPhoneNumber" action="show" id="${l.id}">${l?.encodeAsHTML()}</g:link></li>
					</g:each>
					</ul>
				</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="leadContact.primaryContact.label" default="Primary Contact" /></td>
				
				<td valign="top" class="value"><g:formatBoolean boolean="${leadContactInstance?.primaryContact}" /></td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="leadContact.tenantId.label" default="Tenant Id" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: leadContactInstance, field: "tenantId")}</td>
				
			</tr>
		
		</tbody>
	</table>
</section>

</body>

</html>
