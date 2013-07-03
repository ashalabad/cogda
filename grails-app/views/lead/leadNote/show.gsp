
<%@ page import="com.cogda.multitenant.LeadNote" %>
<!doctype html>
<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="layout" content="kickstart" />
	<g:set var="entityName" value="${message(code: 'leadNote.label', default: 'LeadNote')}" />
	<title><g:message code="default.show.label" args="[entityName]" /></title>
</head>

<body>

<section id="show-leadNote" class="first">

    <h2><g:message code="default.show.label" args="[entityName]" /></h2>

	<table class="table">
		<tbody>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="leadNote.noteType.label" default="Note Type" /></td>
				
				<td valign="top" class="value"><g:link controller="noteType" action="show" id="${leadNoteInstance?.noteType?.id}">${leadNoteInstance?.noteType?.encodeAsHTML()}</g:link></td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="leadNote.note.label" default="Note" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: leadNoteInstance, field: "note")}</td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="leadNote.lead.label" default="Lead" /></td>
				
				<td valign="top" class="value"><g:link controller="lead" action="show" id="${leadNoteInstance?.lead?.id}">${leadNoteInstance?.lead?.encodeAsHTML()}</g:link></td>
				
			</tr>
		
			<tr class="prop">
				<td valign="top" class="name"><g:message code="leadNote.tenantId.label" default="Tenant Id" /></td>
				
				<td valign="top" class="value">${fieldValue(bean: leadNoteInstance, field: "tenantId")}</td>
				
			</tr>
		
		</tbody>
	</table>
</section>

</body>

</html>
