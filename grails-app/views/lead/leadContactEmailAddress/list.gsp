
<%@ page import="com.cogda.multitenant.LeadContactEmailAddress" %>
<!doctype html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="layout" content="kickstart" />
	<g:set var="entityName" value="${message(code: 'leadContactEmailAddress.label', default: 'LeadContactEmailAddress')}" />
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
<section id="list-leadContactEmailAddress" class="first">

    <h2><g:message code="default.list.label" args="[entityName]" /></h2>

	<table class="table table-bordered">
		<thead>
			<tr>
			
				<g:sortableColumn property="emailAddress" title="${message(code: 'leadContactEmailAddress.emailAddress.label', default: 'Email Address')}" />
			
				<g:sortableColumn property="primaryEmailAddress" title="${message(code: 'leadContactEmailAddress.primaryEmailAddress.label', default: 'Primary Email Address')}" />
			
				<g:sortableColumn property="dateCreated" title="${message(code: 'leadContactEmailAddress.dateCreated.label', default: 'Date Created')}" />
			
				<g:sortableColumn property="lastUpdated" title="${message(code: 'leadContactEmailAddress.lastUpdated.label', default: 'Last Updated')}" />
			
				<th><g:message code="leadContactEmailAddress.leadContact.label" default="Lead Contact" /></th>
			
				<g:sortableColumn property="tenantId" title="${message(code: 'leadContactEmailAddress.tenantId.label', default: 'Tenant Id')}" />
			
			</tr>
		</thead>
		<tbody>
		<g:each in="${leadContactEmailAddressInstanceList}" status="i" var="leadContactEmailAddressInstance">
			<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
			
				<td><g:link action="show" id="${leadContactEmailAddressInstance.id}">${fieldValue(bean: leadContactEmailAddressInstance, field: "emailAddress")}</g:link></td>
			
				<td><g:formatBoolean boolean="${leadContactEmailAddressInstance.primaryEmailAddress}" /></td>
			
				<td><g:formatDate date="${leadContactEmailAddressInstance.dateCreated}" /></td>
			
				<td><g:formatDate date="${leadContactEmailAddressInstance.lastUpdated}" /></td>
			
				<td>${fieldValue(bean: leadContactEmailAddressInstance, field: "leadContact")}</td>
			
				<td>${fieldValue(bean: leadContactEmailAddressInstance, field: "tenantId")}</td>
			
			</tr>
		</g:each>
		</tbody>
	</table>
	<div class="pagination">
		<bs:paginate total="${leadContactEmailAddressInstanceTotal}" />
	</div>
</section>

</body>

</html>
