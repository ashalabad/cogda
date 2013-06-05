
<%@ page import="com.cogda.domain.onboarding.Registration" %>
<!doctype html>
<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="layout" content="kickstart" />
	<g:set var="entityName" value="${message(code: 'registration.label', default: 'Registration')}" />
	<title><g:message code="default.show.label" args="[entityName]" /></title>
</head>

<body>

<section id="show-registration" class="first">

	<table class="table">
		<tbody>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="registration.firstName.label" default="First Name" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: registrationInstance, field: "firstName")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="registration.lastName.label" default="Last Name" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: registrationInstance, field: "lastName")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="registration.username.label" default="Username" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: registrationInstance, field: "username")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="registration.emailAddress.label" default="Email Address" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: registrationInstance, field: "emailAddress")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="registration.password.label" default="Password" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: registrationInstance, field: "password")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="registration.companyName.label" default="Company Name" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: registrationInstance, field: "companyName")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="registration.newCompany.label" default="New Company" /></td>
				
				<td valign="top" class="value"><g:formatBoolean boolean="${registrationInstance?.newCompany}" /></td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="registration.companyType.label" default="Company Type" /></td>
				
				<td valign="top" class="value"><g:link controller="companyType" action="show" id="${registrationInstance?.companyType?.id}">${registrationInstance?.companyType?.encodeAsHTML()}</g:link></td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="registration.companyTypeOther.label" default="Company Type Other" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: registrationInstance, field: "companyTypeOther")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="registration.phoneNumber.label" default="Phone Number" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: registrationInstance, field: "phoneNumber")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="registration.streetAddressOne.label" default="Street Address One" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: registrationInstance, field: "streetAddressOne")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="registration.streetAddressTwo.label" default="Street Address Two" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: registrationInstance, field: "streetAddressTwo")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="registration.zipcode.label" default="Zipcode" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: registrationInstance, field: "zipcode")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="registration.city.label" default="City" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: registrationInstance, field: "city")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="registration.state.label" default="State" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: registrationInstance, field: "state")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="registration.county.label" default="County" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: registrationInstance, field: "county")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="registration.country.label" default="Country" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: registrationInstance, field: "country")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="registration.dateCreated.label" default="Date Created" /></td>
				
				<td valign="top" class="value"><g:formatDate date="${registrationInstance?.dateCreated}" /></td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="registration.lastUpdated.label" default="Last Updated" /></td>
				
				<td valign="top" class="value"><g:formatDate date="${registrationInstance?.lastUpdated}" /></td>
				
			</tr>
		
		</tbody>
	</table>
</section>

</body>

</html>
