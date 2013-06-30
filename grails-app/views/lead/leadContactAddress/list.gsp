
<%@ page import="com.cogda.multitenant.LeadContactAddress" %>
<!doctype html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="layout" content="kickstart" />
	<g:set var="entityName" value="${message(code: 'leadContactAddress.label', default: 'LeadContactAddress')}" />
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
<section id="list-leadContactAddress" class="first">

    <h2><g:message code="default.list.label" args="[entityName]" /></h2>

	<table class="table table-bordered">
		<thead>
			<tr>
			
				<g:sortableColumn property="primaryAddress" title="${message(code: 'leadContactAddress.primaryAddress.label', default: 'Primary Address')}" />
			
				<th><g:message code="leadContactAddress.address.label" default="Address" /></th>
			
				<g:sortableColumn property="dateCreated" title="${message(code: 'leadContactAddress.dateCreated.label', default: 'Date Created')}" />
			
				<g:sortableColumn property="lastUpdated" title="${message(code: 'leadContactAddress.lastUpdated.label', default: 'Last Updated')}" />
			
				<th><g:message code="leadContactAddress.leadContact.label" default="Lead Contact" /></th>
			
				<g:sortableColumn property="tenantId" title="${message(code: 'leadContactAddress.tenantId.label', default: 'Tenant Id')}" />
			
			</tr>
		</thead>
		<tbody>
		<g:each in="${leadContactAddressInstanceList}" status="i" var="leadContactAddressInstance">
			<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
			
				<td><g:link action="show" id="${leadContactAddressInstance.id}">${fieldValue(bean: leadContactAddressInstance, field: "primaryAddress")}</g:link></td>
			
				<td>${fieldValue(bean: leadContactAddressInstance, field: "address")}</td>
			
				<td><g:formatDate date="${leadContactAddressInstance.dateCreated}" /></td>
			
				<td><g:formatDate date="${leadContactAddressInstance.lastUpdated}" /></td>
			
				<td>${fieldValue(bean: leadContactAddressInstance, field: "leadContact")}</td>
			
				<td>${fieldValue(bean: leadContactAddressInstance, field: "tenantId")}</td>
			
			</tr>
		</g:each>
		</tbody>
	</table>
	<div class="pagination">
		<bs:paginate total="${leadContactAddressInstanceTotal}" />
	</div>
</section>

</body>

</html>
