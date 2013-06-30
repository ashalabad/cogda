
<%@ page import="com.cogda.multitenant.LeadContact" %>
<!doctype html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="layout" content="kickstart" />
	<g:set var="entityName" value="${message(code: 'leadContact.label', default: 'LeadContact')}" />
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
<section id="list-leadContact" class="first">

    <h2><g:message code="default.list.label" args="[entityName]" /></h2>

	<table class="table table-bordered">
		<thead>
			<tr>
			
				<g:sortableColumn property="firstName" title="${message(code: 'leadContact.firstName.label', default: 'First Name')}" />
			
				<g:sortableColumn property="middleName" title="${message(code: 'leadContact.middleName.label', default: 'Middle Name')}" />
			
				<g:sortableColumn property="lastName" title="${message(code: 'leadContact.lastName.label', default: 'Last Name')}" />
			
				<g:sortableColumn property="dateCreated" title="${message(code: 'leadContact.dateCreated.label', default: 'Date Created')}" />
			
				<g:sortableColumn property="lastUpdated" title="${message(code: 'leadContact.lastUpdated.label', default: 'Last Updated')}" />
			
				<th><g:message code="leadContact.lead.label" default="Lead" /></th>
			
			</tr>
		</thead>
		<tbody>
		<g:each in="${leadContactInstanceList}" status="i" var="leadContactInstance">
			<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
			
				<td><g:link action="show" id="${leadContactInstance.id}">${fieldValue(bean: leadContactInstance, field: "firstName")}</g:link></td>
			
				<td>${fieldValue(bean: leadContactInstance, field: "middleName")}</td>
			
				<td>${fieldValue(bean: leadContactInstance, field: "lastName")}</td>
			
				<td><g:formatDate date="${leadContactInstance.dateCreated}" /></td>
			
				<td><g:formatDate date="${leadContactInstance.lastUpdated}" /></td>
			
				<td>${fieldValue(bean: leadContactInstance, field: "lead")}</td>
			
			</tr>
		</g:each>
		</tbody>
	</table>
	<div class="pagination">
		<bs:paginate total="${leadContactInstanceTotal}" />
	</div>
</section>

</body>

</html>
