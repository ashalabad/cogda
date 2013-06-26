
<%@ page import="com.cogda.multitenant.Lead" %>
<!doctype html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="kickstart" />
    <g:set var="entityName" value="${message(code: 'suspect.label', default: 'Lead')}" />
    <g:set var="layout_nosecondarymenu"	value="true" scope="request"/>
    <g:set var="layout_nomainmenu"		value="true" scope="request"/>
    <g:javascript src="suspect/suspect.js"/>
    <title><g:message code="default.list.label" args="[entityName]" /></title>
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

    <table class="table table-bordered" id='leadList'>
        <thead>
        <tr>
            <th>${message(code: 'lead.clientId.label', default: 'Client Id')} </th>
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
        </tbody>
    </table>
</section>

</body>

</html>
