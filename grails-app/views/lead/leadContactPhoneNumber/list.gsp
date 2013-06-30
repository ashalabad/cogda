
<%@ page import="com.cogda.multitenant.LeadContactPhoneNumber" %>
<!doctype html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="layout" content="kickstart" />
	<g:set var="entityName" value="${message(code: 'leadContactPhoneNumber.label', default: 'LeadContactPhoneNumber')}" />
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
<section id="list-leadContactPhoneNumber" class="first">

    <h2><g:message code="default.list.label" args="[entityName]" /></h2>

	<table class="table table-bordered">
		<thead>
			<tr>
			
				<g:sortableColumn property="primaryPhoneNumber" title="${message(code: 'leadContactPhoneNumber.primaryPhoneNumber.label', default: 'Primary Phone Number')}" />
			
				<g:sortableColumn property="description" title="${message(code: 'leadContactPhoneNumber.description.label', default: 'Description')}" />
			
				<g:sortableColumn property="phoneNumber" title="${message(code: 'leadContactPhoneNumber.phoneNumber.label', default: 'Phone Number')}" />
			
				<th><g:message code="leadContactPhoneNumber.leadContact.label" default="Lead Contact" /></th>
			
				<g:sortableColumn property="tenantId" title="${message(code: 'leadContactPhoneNumber.tenantId.label', default: 'Tenant Id')}" />
			
			</tr>
		</thead>
		<tbody>
		<g:each in="${leadContactPhoneNumberInstanceList}" status="i" var="leadContactPhoneNumberInstance">
			<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
			
				<td><g:link action="show" id="${leadContactPhoneNumberInstance.id}">${fieldValue(bean: leadContactPhoneNumberInstance, field: "primaryPhoneNumber")}</g:link></td>
			
				<td>${fieldValue(bean: leadContactPhoneNumberInstance, field: "description")}</td>
			
				<td>${fieldValue(bean: leadContactPhoneNumberInstance, field: "phoneNumber")}</td>
			
				<td>${fieldValue(bean: leadContactPhoneNumberInstance, field: "leadContact")}</td>
			
				<td>${fieldValue(bean: leadContactPhoneNumberInstance, field: "tenantId")}</td>
			
			</tr>
		</g:each>
		</tbody>
	</table>
	<div class="pagination">
		<bs:paginate total="${leadContactPhoneNumberInstanceTotal}" />
	</div>
</section>

</body>

</html>
