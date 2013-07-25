<%@ page import="com.cogda.multitenant.Lead" %>
<!doctype html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="angularLayout" />
    <g:set var="layout_nosecondarymenu"	value="true" scope="request"/>
    <g:set var="layout_nomainmenu"		value="true" scope="request"/>
    <title>
        <g:message code="submissionBuilder.label"/>
    </title>
    <r:require module="submissionBuilder"/>
</head>

<body>
<content tag="header">
    <!-- Empty Header -->
</content>
<section id="list-lead" class="first" data-ng-app="submissionBuilderApp">

    <div data-ng-view=""></div>

</section>
</body>

</html>