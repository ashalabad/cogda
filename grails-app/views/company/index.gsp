<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

    <meta name="layout" content="angularLayout" />
    <g:set var="entityName" value="${message(code: 'company.label', default: 'Company')}" />

    <g:set var="layout_nosecondarymenu"	value="${true}" scope="request"/>
    <g:set var="layout_nomainmenu"		value="${true}" scope="request"/>

    <title>Company Manager</title>
    <r:require module="company"/>


</head>

<body    >

<content tag="header">
    <!-- Empty Header -->
</content>
<div class="pageHeader">
    <h3>Company Manager</h3>
</div>

<section id="list-company" class="first" data-ng-app="companyApp" data-base-url="${createLink(uri:'company')}">
    <div class="row">
        <div class = "span9">
            <!-- Placeholder for view - Injects the views dynamically into the div for data-ng-view -->
            <div data-ng-view=""></div>

        </div>
        <div class="span3">
            nothing
        </div>
    </div>
</section>

</body>
</html>