<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

    <meta name="layout" content="angularLayout" />
<g:set var="entityName" value="${message(code: 'prospect.label', default: 'Prospect')}"/>

<g:set var="layout_nosecondarymenu" value="${true}" scope="request"/>
<g:set var="layout_nomainmenu" value="${true}" scope="request"/>

<title>${entityName}</title>
<r:require module="prospect"/>


</head>

<body>

<content tag="header">
    <!-- Empty Header -->
</content>

<div data-ng-app="prospectApp" data-base-url="${createLink(uri: 'prospect')}" data-ng-cloak>
    <!-- Placeholder for view - Injects the views dynamically into the div for data-ng-view -->
    <div data-ng-view=""></div>
</div>
</body>
</body>
</html>