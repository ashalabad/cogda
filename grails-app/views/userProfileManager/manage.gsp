<%@ page import="com.cogda.domain.UserProfileAddress; com.cogda.domain.UserProfile" %>
<!doctype html>
<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="layout" content="kickstart" />
	<g:set var="entityName" value="${message(code: 'userProfile.label', default: 'UserProfile')}" />
    <g:set var="layout_nosecondarymenu"	value="true" scope="request"/>
    <g:set var="layout_nomainmenu"		value="true" scope="request"/>
	<title><g:message code="default.edit.label" args="[entityName]" /></title>

</head>

<body>
<content tag="header">
    <!-- Empty Header -->
</content>

<section id="edit-userProfile" class="first">
    <h2><g:message code="userProfile.manage.label" args="[entityName]" /></h2>
	<g:hasErrors bean="${userProfileInstance}">
        <div class="alert alert-error">
            <g:renderErrors bean="${userProfileInstance}" as="list" />
        </div>
	</g:hasErrors>

    <div class = "row">
        <div class = "span3">
            <p>
                <a href="#" id="userProfileImage">
                    <img src="http://placehold.it/200x150" alt="${userProfileInstance.firstName}" class="img-polaroid">
                </a>
            </p>
            <p>
                <a class="btn btn-primary" id="userProfileEditLink">
                    Add Image
                </a>
            </p>
        </div>
        <div class="span9">
            <g:render template="userProfileForm"/>
        </div>
    </div>


</section>

<g:render template="/userProfileAddress/addressModal"/>
<g:render template="/userProfileEmailAddress/emailAddressModal"/>


<g:javascript src = "userProfile/manage.js"/>
<g:javascript src = "userProfileAddress/userProfileAddressForm.js"/>
<g:javascript src = "userProfileEmailAddress/userProfileEmailAddressForm.js"/>
</body>

</html>
