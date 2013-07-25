<%@ page import="com.cogda.multitenant.CustomerAccount;" %>
<html>

<head>
	<title><g:message code="default.tenant.welcome.title" args="[customerAccountInstance?.subDomain]"/> </title>
	<meta name="layout" content="kickstart" />
    <g:set var="layout_nomainmenu"		value="${true}" scope="request"/>
    <g:set var="layout_nosecondarymenu"	value="${true}" scope="request"/>
    %{--<sec:ifLoggedIn>--}%
        %{--<r:require module="dashboard"/>--}%
    %{--</sec:ifLoggedIn>--}%
</head>

<body>
    <mt:hasTenant>
        <sec:ifNotLoggedIn>
            <section id="intro" class="first">
                <h1>${companyInstance?.companyName?.encodeAsHTML()}</h1>
                <p>
                    Welcome message placeholder
                </p>
            </section>
        </sec:ifNotLoggedIn>
        <sec:ifLoggedIn>
            <div data-ng-app="dashboardApp">

                <div class="container-fluid">
                    <div class="row-fluid">
                        <div class="span2">
                            <!--Sidebar content-->
                        </div>
                        <div class="span10">
                            <!--Body content-->
                        </div>
                    </div>
                </div>

            </div>
        </sec:ifLoggedIn>
    </mt:hasTenant>


</body>

</html>
