
<%@ page import="com.cogda.multitenant.LeadAddress" %>
<!doctype html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="layout" content="kickstart" />
	<g:set var="entityName" value="${message(code: 'leadAddress.label', default: 'LeadAddress')}" />
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
<section id="list-leadAddress" class="first">

    <h2><g:message code="default.list.label" args="[entityName]" /></h2>

	<table class="table table-bordered">
		<thead>
			<tr>
			
				<g:sortableColumn property="primaryAddress" title="${message(code: 'leadAddress.primaryAddress.label', default: 'Primary Address')}" />
			
				<th><g:message code="leadAddress.address.label" default="Address" /></th>
			
				<g:sortableColumn property="dateCreated" title="${message(code: 'leadAddress.dateCreated.label', default: 'Date Created')}" />
			
				<g:sortableColumn property="lastUpdated" title="${message(code: 'leadAddress.lastUpdated.label', default: 'Last Updated')}" />
			
				<th><g:message code="leadAddress.lead.label" default="Lead" /></th>
			
				<th><g:message code="leadAddress.leadAddressType.label" default="Lead Address Type" /></th>
			
			</tr>
		</thead>
		<tbody>
		<g:each in="${leadAddressInstanceList}" status="i" var="leadAddressInstance">
			<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
			
				<td><g:link action="show" id="${leadAddressInstance.id}">${fieldValue(bean: leadAddressInstance, field: "primaryAddress")}</g:link></td>
			
				<td>${fieldValue(bean: leadAddressInstance, field: "address")}</td>
			
				<td><g:formatDate date="${leadAddressInstance.dateCreated}" /></td>
			
				<td><g:formatDate date="${leadAddressInstance.lastUpdated}" /></td>
			
				<td>${fieldValue(bean: leadAddressInstance, field: "lead")}</td>
			
				<td>${fieldValue(bean: leadAddressInstance, field: "leadAddressType")}</td>
			
			</tr>
		</g:each>
		</tbody>
	</table>
	<div class="pagination">
		<bs:paginate total="${leadAddressInstanceTotal}" />
	</div>
</section>

</body>

</html>
