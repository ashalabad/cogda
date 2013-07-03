
<%@ page import="com.cogda.multitenant.Account" %>
<!doctype html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="kickstart" />
    <g:set var="entityName" value="${message(code: 'account.label', default: 'Account')}" />
    <g:set var="layout_nosecondarymenu"	value="${true}" scope="request"/>
    <g:set var="layout_nomainmenu"		value="${true}" scope="request"/>
    <title><g:message code="default.list.label" args="[entityName]" /></title>
    <r:require module="account"/>

</head>

<body>
%{--<g:render template="/_common/modals/naicsCode/naicsCodes"/>--}%
%{--<g:render template="/_common/modals/sicCode/sicCodes"/>--}%

<content tag="header">
    <!-- Empty Header -->
</content>

<div id="MenuRow" class="row">
    <div class="span12">
        &nbsp;
    </div>
</div>

<table class="table table-bordered" id="accountList">
    <thead>
    <tr>

        <th>${message(code: 'account.accountName.label')}</th>

        <th>${message(code: 'account.accountCode.label')}</th>

        <th>${message(code: 'account.accountType.label')}</th>

        <th>${message(code: 'account.primaryContact.label')}</th>

        <th><div class="span1"></div></th>

    </tr>
    </thead>
    <tbody>
    </tbody>
</table>

<g:render template="/_common/modals/masterModal" model="[modalLabel:'account',templatePath: 'modals/account',modalHeader:'Account',modalList:[]]"/>


</body>
</html>
