<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

    <meta name="layout" content="angularLayout" />
<g:set var="entityName" value="${message(code: 'suspect.label', default: 'Suspect')}"/>

<g:set var="layout_nosecondarymenu" value="${true}" scope="request"/>
<g:set var="layout_nomainmenu" value="${true}" scope="request"/>

<title>Suspects</title>
<r:require module="suspect"/>


</head>

<body>

<content tag="header">
    <!-- Empty Header -->
</content>

<div data-ng-app="suspectApp" data-base-url="${createLink(uri: 'suspect')}">
    <!-- Placeholder for view - Injects the views dynamically into the div for data-ng-view -->
    <div data-ng-view=""></div>
</div>
</body>
</body>
</html>