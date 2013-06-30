
<%@ page import="com.cogda.domain.Address" %>
<!doctype html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="layout" content="kickstart" />
	<g:set var="entityName" value="${message(code: 'address.label', default: 'Address')}" />
	<title><g:message code="default.list.label" args="[entityName]" /></title>
    <g:set var="layout_nosecondarymenu"	value="true" scope="request"/>
    <g:set var="layout_nomainmenu"		value="true" scope="request"/>
    <r:require module="dataTables"/>
</head>

<body>
<content tag="header">
    <!-- Empty Header -->
</content>

<div id="MenuRow" class="row">
    <div class="span12">
        &nbsp;
    </div>
</div>
<section id="list-address" class="first">

    <h2><g:message code="default.list.label" args="[entityName]" /></h2>

	<table class="table table-bordered">
		<thead>
			<tr>
			
				<g:sortableColumn property="addressOne" title="${message(code: 'address.addressOne.label', default: 'Address One')}" />
			
				<g:sortableColumn property="addressTwo" title="${message(code: 'address.addressTwo.label', default: 'Address Two')}" />
			
				<g:sortableColumn property="addressThree" title="${message(code: 'address.addressThree.label', default: 'Address Three')}" />
			
				<g:sortableColumn property="longitude" title="${message(code: 'address.longitude.label', default: 'Longitude')}" />
			
				<g:sortableColumn property="latitude" title="${message(code: 'address.latitude.label', default: 'Latitude')}" />
			
				<g:sortableColumn property="city" title="${message(code: 'address.city.label', default: 'City')}" />
			
			</tr>
		</thead>
		<tbody>
		<g:each in="${addressInstanceList}" status="i" var="addressInstance">
			<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
			
				<td><g:link action="show" id="${addressInstance.id}">${fieldValue(bean: addressInstance, field: "addressOne")}</g:link></td>
			
				<td>${fieldValue(bean: addressInstance, field: "addressTwo")}</td>
			
				<td>${fieldValue(bean: addressInstance, field: "addressThree")}</td>
			
				<td>${fieldValue(bean: addressInstance, field: "longitude")}</td>
			
				<td>${fieldValue(bean: addressInstance, field: "latitude")}</td>
			
				<td>${fieldValue(bean: addressInstance, field: "city")}</td>
			
			</tr>
		</g:each>
		</tbody>
	</table>
	<div class="pagination">
		<bs:paginate total="${addressInstanceTotal}" />
	</div>
</section>

</body>

</html>
