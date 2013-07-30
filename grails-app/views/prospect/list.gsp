
<%@ page import="com.cogda.multitenant.Lead" %>
<!doctype html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="layout" content="kickstart" />
	<g:set var="entityName" value="${message(code: 'prospect.label', default: 'Prospect')}" />
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
<section id="list-prospect" class="first">

    <h2><g:message code="default.list.label" args="[entityName]" /></h2>

	<table class="table table-bordered" id='prospectList'>
		<thead>
			<tr>

				<th>${message(code: 'lead.clientId.label', default: 'Client Id')} <th/>
                <th>${message(code: 'lead.clientName.label', default: 'Client Name')} </th>
                <th>${message(code: 'lead.businessType.label', default: 'Business Type')}</th>
                <th>${message(code: 'lead.contactName.label', default: 'Contact Name')}</th>
                <th>${message(code: 'lead.phoneNumber.label', default: 'Phone Number')}</th>
                <th>${message(code: 'lead.contactEmailAddress.label', default: 'Email Address')}</th>
                <th>${message(code: 'lead.owner.label', default: 'Owner')}</th>
                <th>${message(code: 'lead.dateCreated.label', default: 'Created')}</th>
			</tr>
		</thead>
		<tbody>
		<g:each in="${leadInstanceList}" status="i" var="leadInstance">
			<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
			
				<td><g:link action="show" id="${leadInstance.id}">${fieldValue(bean: leadInstance, field: "clientId")}</g:link></td>
			
				<td>${fieldValue(bean: leadInstance, field: "clientName")}</td>

				<td>${fieldValue(bean: leadInstance, field: "account")}</td>
			
				<td>${fieldValue(bean: leadInstance, field: "businessType")}</td>
			
				<td>${fieldValue(bean: leadInstance, field: "naicsCode")}</td>
			
				<td>${fieldValue(bean: leadInstance, field: "sicCode")}</td>
			
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
