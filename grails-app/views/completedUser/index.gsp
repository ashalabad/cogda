<%@ page import="com.cogda.security.PendingUserStatus" contentType="text/html;charset=UTF-8" %>
<!doctype html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="kickstart"/>
    <title><g:message code = "completedUser.label"/></title>
    <link rel="stylesheet" type="text/css" href="/css/importedUser.css" />
    <g:set var="layout_nosecondarymenu" value="true" scope="request"/>
    <g:set var="layout_nomainmenu" value="true" scope="request"/>
    <g:javascript src="completedUser/index.js"/>
    <r:require module="dataTables"/>
    <r:require module="notifications"/>
</head>

<body>
<content tag="header">
    <!-- Empty Header -->
</content>

<section id="list-pendingUser" class="first">

    <h2><g:message code="completedUserAccounts.title" default="Completed User Accounts"/></h2>

    <div class="alert alert-error" id="errorMessages" style="display:none">
    </div>

    <div class="alert alert-success" id="messages" style="display:none">
    </div>

    <table class="table table-bordered" id='completedUserList'>
        <thead>
        <th>${message(code: 'pendingUser.firstName.label', default: 'First Name')}</th>
        <th>${message(code: 'pendingUser.lastName.label', default: 'Last Name')}</th>
        <th>${message(code: 'pendingUser.emailAddress.label', default: 'Email Address')}</th>
        <th>${message(code: 'pendingUser.securityRoles.label', default: 'Security Roles')}</th>
        <th>${message(code: 'pendingUser.loadedDate.label', default: 'Imported Date')}</th>
        <th>${message(code: 'pendingUser.loadedByUsername.label', default: 'Imported By')}</th>
        <th>${message(code: 'pendingUser.onboardedSuccessfully.label', default: 'Onboarded')}</th>
        <th>${message(code: 'pendingUser.onboardedDate.label', default: 'Onboarded Date')}</th>
        <th>${message(code: 'pendingUser.pendingUserStatus.label', default: "Status")}</></th>
        <th>${message(code: 'completedUser.username.label', default: "Username")}</></th>
        </thead>
        <tbody>
        </tbody>
    </table>


</section>
<g:render template="/pendingUser/pendingUserModal"/>
</body>
</html>