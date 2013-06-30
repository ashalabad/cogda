
<%@ page import="com.cogda.multitenant.Lead" %>
<!doctype html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="layout" content="kickstart" />
	<g:set var="entityName" value="${message(code: 'lead.label', default: 'Lead')}" />
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
<section id="list-lead" class="first">

    <h2><g:message code="default.list.label" args="[entityName]" /></h2>

	<table class="table table-bordered">
		<thead>
			<tr>
			
				<g:sortableColumn property="clientId" title="${message(code: 'lead.clientId.label', default: 'Client Id')}" />
			
				<g:sortableColumn property="ownerName" title="${message(code: 'lead.ownerName.label', default: 'Owner Name')}" />
			
				<th><g:message code="lead.businessType.label" default="Business Type" /></th>
			
				<th><g:message code="lead.naicsCode.label" default="Naics Code" /></th>
			
				<th><g:message code="lead.sicCode.label" default="Sic Code" /></th>
			
				<g:sortableColumn property="leadType" title="${message(code: 'lead.leadType.label', default: 'Lead Type')}" />
			
			</tr>
		</thead>
		<tbody>
		<g:each in="${leadInstanceList}" status="i" var="leadInstance">
			<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
			
				<td><g:link action="show" id="${leadInstance.id}">${fieldValue(bean: leadInstance, field: "clientId")}</g:link></td>
			
				<td>${fieldValue(bean: leadInstance, field: "ownerName")}</td>
			
				<td>${fieldValue(bean: leadInstance, field: "businessType")}</td>
			
				<td>${fieldValue(bean: leadInstance, field: "naicsCode")}</td>
			
				<td>${fieldValue(bean: leadInstance, field: "sicCode")}</td>
			
				<td>${fieldValue(bean: leadInstance, field: "leadType")}</td>
			
			</tr>
		</g:each>
		</tbody>
	</table>
	<div class="pagination">
		<bs:paginate total="${leadInstanceTotal}" />
	</div>
</section>

</body>

</html>
