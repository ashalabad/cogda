
<%@ page import="com.cogda.domain.Address" %>
<!doctype html>
<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="layout" content="kickstart" />
	<g:set var="entityName" value="${message(code: 'address.label', default: 'Address')}" />
	<title><g:message code="default.show.label" args="[entityName]" /></title>
</head>

<body>

<section id="show-address" class="first">

    <h2><g:message code="default.show.label" args="[entityName]" /></h2>

	<table class="table">
		<tbody>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="address.addressOne.label" default="Address One" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: addressInstance, field: "addressOne")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="address.addressTwo.label" default="Address Two" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: addressInstance, field: "addressTwo")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="address.addressThree.label" default="Address Three" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: addressInstance, field: "addressThree")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="address.longitude.label" default="Longitude" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: addressInstance, field: "longitude")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="address.latitude.label" default="Latitude" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: addressInstance, field: "latitude")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="address.city.label" default="City" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: addressInstance, field: "city")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="address.state.label" default="State" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: addressInstance, field: "state")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="address.country.label" default="Country" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: addressInstance, field: "country")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="address.county.label" default="County" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: addressInstance, field: "county")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="address.zipcode.label" default="Zipcode" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: addressInstance, field: "zipcode")}</td>
				
			</tr>
		
		</tbody>
	</table>
</section>

</body>

</html>
