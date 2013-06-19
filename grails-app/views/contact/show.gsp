
<%@ page import="com.cogda.domain.Contact" %>
<!doctype html>
<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="layout" content="kickstart" />
	<g:set var="entityName" value="${message(code: 'contact.label', default: 'Contact')}" />
	<title><g:message code="default.show.label" args="[entityName]" /></title>
</head>

<body>

<section id="show-contact" class="first">

	<table class="table">
		<tbody>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="contact.jobTitle.label" default="Job Title" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: contactInstance, field: "jobTitle")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="contact.website.label" default="Website" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: contactInstance, field: "website")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="contact.companyName.label" default="Company Name" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: contactInstance, field: "companyName")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="contact.firstName.label" default="First Name" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: contactInstance, field: "firstName")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="contact.middleName.label" default="Middle Name" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: contactInstance, field: "middleName")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="contact.lastName.label" default="Last Name" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: contactInstance, field: "lastName")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="contact.dateOfBirth.label" default="Date Of Birth" /></td>
				
				<td valign="top" class="value"><g:formatDate date="${contactInstance?.dateOfBirth}" /></td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="contact.gender.label" default="Gender" /></td>
				
				<td valign="top" class="value">${contactInstance?.gender?.encodeAsHTML()}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="contact.initials.label" default="Initials" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: contactInstance, field: "initials")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="contact.title.label" default="Title" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: contactInstance, field: "title")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="contact.userProfile.label" default="User Profile" /></td>
				
				<td valign="top" class="value"><g:link controller="userProfile" action="show" id="${contactInstance?.userProfile?.id}">${contactInstance?.userProfile?.encodeAsHTML()}</g:link></td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="contact.contactAddresses.label" default="Contact Addresses" /></td>
				
				<td valign="top" style="text-align: left;" class="value">
					<ul>
					<g:each in="${contactInstance.contactAddresses}" var="c">
						<li><g:link controller="contactAddress" action="show" id="${c.id}">${c?.encodeAsHTML()}</g:link></li>
					</g:each>
					</ul>
				</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="contact.contactEmailAddresses.label" default="Contact Email Addresses" /></td>
				
				<td valign="top" style="text-align: left;" class="value">
					<ul>
					<g:each in="${contactInstance.contactEmailAddresses}" var="c">
						<li><g:link controller="contactEmailAddress" action="show" id="${c.id}">${c?.encodeAsHTML()}</g:link></li>
					</g:each>
					</ul>
				</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="contact.contactPhoneNumbers.label" default="Contact Phone Numbers" /></td>
				
				<td valign="top" style="text-align: left;" class="value">
					<ul>
					<g:each in="${contactInstance.contactPhoneNumbers}" var="c">
						<li><g:link controller="contactPhoneNumber" action="show" id="${c.id}">${c?.encodeAsHTML()}</g:link></li>
					</g:each>
					</ul>
				</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="contact.dateCreated.label" default="Date Created" /></td>
				
				<td valign="top" class="value"><g:formatDate date="${contactInstance?.dateCreated}" /></td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="contact.lastUpdated.label" default="Last Updated" /></td>
				
				<td valign="top" class="value"><g:formatDate date="${contactInstance?.lastUpdated}" /></td>
				
			</tr>
		
		</tbody>
	</table>
</section>

</body>

</html>
