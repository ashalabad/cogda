
<%@ page import="com.cogda.multitenant.Lead" %>
<!doctype html>
<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="layout" content="kickstart" />
	<g:set var="entityName" value="${message(code: 'lead.label', default: 'Lead')}" />
	<title><g:message code="default.show.label" args="[entityName]" /></title>
</head>

<body>

<section id="show-lead" class="first">

    <h2><g:message code="default.show.label" args="[entityName]" /></h2>

	<table class="table">
		<tbody>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="lead.clientId.label" default="Client Id" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: leadInstance, field: "clientId")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="lead.ownerName.label" default="Owner Name" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: leadInstance, field: "ownerName")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="lead.businessType.label" default="Business Type" /></td>
				
				<td valign="top" class="value"><g:link controller="businessType" action="show" id="${leadInstance?.businessType?.id}">${leadInstance?.businessType?.encodeAsHTML()}</g:link></td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="lead.naicsCode.label" default="Naics Code" /></td>
				
				<td valign="top" class="value"><g:link controller="naicsCode" action="show" id="${leadInstance?.naicsCode?.id}">${leadInstance?.naicsCode?.encodeAsHTML()}</g:link></td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="lead.sicCode.label" default="Sic Code" /></td>
				
				<td valign="top" class="value"><g:link controller="sicCode" action="show" id="${leadInstance?.sicCode?.id}">${leadInstance?.sicCode?.encodeAsHTML()}</g:link></td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="lead.leadType.label" default="Lead Type" /></td>
				
				<td valign="top" class="value">${leadInstance?.leadType?.encodeAsHTML()}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="lead.clientName.label" default="Client Name" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: leadInstance, field: "clientName")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="lead.dateCreated.label" default="Date Created" /></td>
				
				<td valign="top" class="value"><g:formatDate date="${leadInstance?.dateCreated}" /></td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="lead.files.label" default="Files" /></td>
				
				<td valign="top" style="text-align: left;" class="value">
					<ul>
					<g:each in="${leadInstance.files}" var="f">
						<li><g:link controller="fileReference" action="show" id="${f.id}">${f?.encodeAsHTML()}</g:link></li>
					</g:each>
					</ul>
				</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="lead.lastUpdated.label" default="Last Updated" /></td>
				
				<td valign="top" class="value"><g:formatDate date="${leadInstance?.lastUpdated}" /></td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="lead.leadAddresses.label" default="Lead Addresses" /></td>
				
				<td valign="top" style="text-align: left;" class="value">
					<ul>
					<g:each in="${leadInstance.leadAddresses}" var="l">
						<li><g:link controller="leadAddress" action="show" id="${l.id}">${l?.encodeAsHTML()}</g:link></li>
					</g:each>
					</ul>
				</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="lead.leadContacts.label" default="Lead Contacts" /></td>
				
				<td valign="top" style="text-align: left;" class="value">
					<ul>
					<g:each in="${leadInstance.leadContacts}" var="l">
						<li><g:link controller="leadContact" action="show" id="${l.id}">${l?.encodeAsHTML()}</g:link></li>
					</g:each>
					</ul>
				</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="lead.leadEmailAddresses.label" default="Lead Email Addresses" /></td>
				
				<td valign="top" style="text-align: left;" class="value">
					<ul>
					<g:each in="${leadInstance.leadEmailAddresses}" var="l">
						<li><g:link controller="leadEmailAddress" action="show" id="${l.id}">${l?.encodeAsHTML()}</g:link></li>
					</g:each>
					</ul>
				</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="lead.leadPhoneNumbers.label" default="Lead Phone Numbers" /></td>
				
				<td valign="top" style="text-align: left;" class="value">
					<ul>
					<g:each in="${leadInstance.leadPhoneNumbers}" var="l">
						<li><g:link controller="leadPhoneNumber" action="show" id="${l.id}">${l?.encodeAsHTML()}</g:link></li>
					</g:each>
					</ul>
				</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="lead.notes.label" default="Notes" /></td>
				
				<td valign="top" style="text-align: left;" class="value">
					<ul>
					<g:each in="${leadInstance.notes}" var="n">
						<li><g:link controller="note" action="show" id="${n.id}">${n?.encodeAsHTML()}</g:link></li>
					</g:each>
					</ul>
				</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="lead.tenantId.label" default="Tenant Id" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: leadInstance, field: "tenantId")}</td>
				
			</tr>
		
		</tbody>
	</table>
</section>

</body>

</html>
