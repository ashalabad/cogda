<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

    <meta name="layout" content="angularLayout" />
    <g:set var="entityName" value="${message(code: 'companyProfile.label', default: 'Company Profile')}" />

    <g:set var="layout_nosecondarymenu"	value="${true}" scope="request"/>
    <g:set var="layout_nomainmenu"		value="${true}" scope="request"/>

    <title>Company Profile Manager</title>
    <r:require module="companyProfile"/>

</head>

<body>

<content tag="header">
    <!-- Empty Header -->
</content>
<div class="pageHeader">
    <h3>
        <g:message code="companyProfile.label"/>
    </h3>
</div>

<section id="list-companyProfile" class="first" data-ng-app="companyProfileApp" data-base-url="${createLink(uri:'companyProfile')}">
    <div class="row">
        <div class="span3">
            <p>
                <a href="#" id="companyProfileImage">
                    <img src="http://placehold.it/200x150" class="img-polaroid">
                </a>
            </p>
            <p>
                <a class="btn btn-primary" id="addCompanyProfileImage">
                    Add Image
                </a>
            </p>
        </div>
        <div class = "span9">
            <!-- Placeholder for view - Injects the views dynamically into the div for data-ng-view -->
            <div data-ng-view="" ></div>
        </div>
    </div>
</section>

</body>
</html>