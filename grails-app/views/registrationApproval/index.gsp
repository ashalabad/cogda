<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

    <meta name="layout" content="angularLayout" />
    <g:set var="entityName" value="${message(code: 'registrationApproval.label')}" />

    <g:set var="layout_nosecondarymenu"	value="${true}" scope="request"/>
    <g:set var="layout_nomainmenu" value="${true}" scope="request"/>

    <title><g:message code="registrationApproval.label"/></title>
    <r:require module="registrationApproval"/>
</head>

<body>

<content tag="header">
    <!-- Empty Header -->
</content>

<div data-ng-app="registrationApprovalApp" data-base-url="${createLink(uri:'registrationApproval')}">
    <section id="list-registrationApproval" class="first">
        <div class="row">
            <div class = "span12">
                <!-- Placeholder for view - Injects the views dynamically into the div for data-ng-view -->
                <div data-ng-view=""></div>
            </div>
        </div>
    </section>
</div>
</body>
</html>