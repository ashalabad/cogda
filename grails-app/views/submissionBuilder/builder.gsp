<%@ page import="com.cogda.multitenant.Lead" %>
<!doctype html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="kickstart" />
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

    <div id="MenuRow" class="row">
        <div class="span12">
            &nbsp;
        </div>
    </div>

    <h3><g:message code="submissionBuilder.label"/></h3>
    <section id="list-lead" class="first" data-ng-app="submissionBuilderApp">

        <div data-ng-view=""></div>

    </section>
</body>

</html>