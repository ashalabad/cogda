
<%@ page import="com.cogda.multitenant.LeadNote" %>
<!doctype html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="layout" content="kickstart" />
	<g:set var="entityName" value="${message(code: 'leadNote.label', default: 'LeadNote')}" />
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
<section id="list-leadNote" class="first">

    <h2><g:message code="default.list.label" args="[entityName]" /></h2>

	<table class="table table-bordered">
		<thead>
			<tr>
			
				<th><g:message code="leadNote.noteType.label" default="Note Type" /></th>
			
				<th><g:message code="leadNote.note.label" default="Note" /></th>
			
				<th><g:message code="leadNote.lead.label" default="Lead" /></th>
			
				<g:sortableColumn property="tenantId" title="${message(code: 'leadNote.tenantId.label', default: 'Tenant Id')}" />
			
			</tr>
		</thead>
		<tbody>
		<g:each in="${leadNoteInstanceList}" status="i" var="leadNoteInstance">
			<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
			
				<td><g:link action="show" id="${leadNoteInstance.id}">${fieldValue(bean: leadNoteInstance, field: "noteType")}</g:link></td>
			
				<td>${fieldValue(bean: leadNoteInstance, field: "note")}</td>
			
				<td>${fieldValue(bean: leadNoteInstance, field: "lead")}</td>
			
				<td>${fieldValue(bean: leadNoteInstance, field: "tenantId")}</td>
			
			</tr>
		</g:each>
		</tbody>
	</table>
	<div class="pagination">
		<bs:paginate total="${leadNoteInstanceTotal}" />
	</div>
</section>

</body>

</html>
