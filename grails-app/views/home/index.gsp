<%@ page import="com.cogda.multitenant.CustomerAccount;" %>
<html>

    <head>
        <title><g:message code="default.tenant.welcome.title" args="[customerAccountInstance?.subDomain]"/> </title>
        <meta name="layout" content="kickstart" />
        <g:set var="layout_nomainmenu"		value="${true}" scope="request"/>
        <g:set var="layout_nosecondarymenu"	value="${true}" scope="request"/>
    </head>

    <body>
    <br>
        <mt:hasTenant>
            <h1>${companyInstance?.companyName?.encodeAsHTML()}</h1>
        </mt:hasTenant>
        <sec:ifNotLoggedIn>
            <p>&lt;Pre-Login information here&gt;</p>
        </sec:ifNotLoggedIn>
        <sec:ifLoggedIn>
            <p>&lt;Post-Login information here&gt;</p>
        </sec:ifLoggedIn>
    </body>
</html>
