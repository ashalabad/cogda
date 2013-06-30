<%@ page import="com.cogda.security.PendingUserStatus" contentType="text/html;charset=UTF-8" %>
<!doctype html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="kickstart"/>
    <g:set var="entityName" value="${message(code: 'pendingUser.label', default: 'PendingUser')}"/>
    <title><g:message code="default.welcome.title" args="[meta(name: 'app.name')]"/></title>
    <link rel="stylesheet" type="text/css" href="/css/importedUser.css" />
    <g:set var="layout_nosecondarymenu" value="true" scope="request"/>
    <g:set var="layout_nomainmenu" value="true" scope="request"/>
    <g:javascript src="userImport/manageImportedUsers.js"/>
    <r:require module="dataTables"/>
    <r:require module="notifications"/>
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

<section id="list-importedUser" class="first">

    <g:if test = "${flash.error}">
        <div class="alert alert-error" id="errorMessages" >
            ${flash.error}
        </div>
    </g:if>

    <g:if test = "${flash.message}">
        <div class="alert alert-success" id="messages" >
            ${flash.message}
        </div>
    </g:if>

    <h2><g:message code="pendingUserAccounts.title" default="Pending User Accounts"/></h2>

    <div class="alert alert-error" style="display: none;" id="errorMessages"></div>

    <div class="alert alert-success" style="display: none;" id="messages"></div>

    <div id = "actionButtons" class = "row">
        <div class = "offset8 span4 text-right">
            <button class = "btn btn-primary" id="sendNotificationsButton">
                <i class="icon-envelope"></i>
                <g:message code="pendingUser.sendInvitations.label"/>
            </button>
            <button class = "btn btn-danger" id="deleteImportedUsers">
                <i class="icon-remove"></i>
                <g:message code="pendingUser.removePendingImports.label"/>
            </button>
        </div>
    </div>

    <div class="alert alert-error" id="errorMessages" style="display:none">
    </div>

    <div class="alert alert-success" id="messages" style="display:none">
    </div>

    <form id = "userImportListForm">

        <table class="table table-bordered" id='importedUserList'>
            <thead>
            <th> </th>
            <th>${message(code: 'pendingUser.firstName.label', default: 'First Name')}</th>
            <th>${message(code: 'pendingUser.lastName.label', default: 'Last Name')}</th>
            <th>${message(code: 'pendingUser.emailAddress.label', default: 'Email Address')}</th>
            <th>${message(code: 'pendingUser.securityRoles.label', default: 'Security Roles')}</th>
            <th>${message(code: 'pendingUser.loadedDate.label', default: 'Imported Date')}</th>
            <th>${message(code: 'pendingUser.loadedByUsername.label', default: 'Imported Date')}</th>
            <th>${message(code: 'pendingUser.onboardedSuccessfully.label', default: 'Onboarded')}</th>
            <th>${message(code: 'pendingUser.onboardedDate.label', default: 'Onboarded Date')}</th>
            <th>${message(code: 'pendingUser.pendingUserStatus.label', default: "PendingUser Status")}</></th>
            <th class="center"><input type = "checkbox" id="selectAllCheckbox" title="Select All"/></th>
            </thead>
            <tbody>
            </tbody>
        </table>

    </form>

</section>
<g:render template="/_common/modals/pendingUser/pendingUserModal"/>
</body>
</html>