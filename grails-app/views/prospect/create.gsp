<%@ page import="com.cogda.multitenant.Lead" %>
<!doctype html>
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="kickstart"/>
    <g:set var="entityName" value="${message(code: 'prospect.label', default: 'Suspect')}"/>
    <g:set var="layout_nosecondarymenu" value="true" scope="request"/>
    <g:set var="layout_nomainmenu" value="true" scope="request"/>
    <title><g:message code="default.create.label" args="[entityName]"/></title>
    <g:javascript src="prospect/edit.js"/>
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
<div class="alert alert-error" id="errorMessages" style="display:none">
    <h4>Errors!</h4>
</div>
<section id="create-lead" class="first">

    <g:hasErrors bean="${prospectInstance}">
        <div class="alert alert-error">
            <g:renderErrors bean="${prospectInstance}" as="list"/>
        </div>
    </g:hasErrors>

    <g:form action="save" class="form-horizontal new" name="prospectForm">
        <fieldset class="form">
            <g:render template="/lead/form" model="['leadInstance': prospectInstance]"/>
        </fieldset>

        <div class="form-actions">
            <g:submitButton name="create" class="btn btn-primary"
                            value="${message(code: 'default.button.create.label', default: 'Create')}"/>
            <button class="btn" type="reset"><g:message code="default.button.reset.label" default="Reset"/></button>
        </div>
    </g:form>

</section>

<div id='newPhoneList'>
    <g:render template='/lead/leadContactPhoneNumber/form'
              model="[leadContactPhoneNumberInstance: null, i: '_clone', hidden: true, prefix: 'leadContacts[_clone].leadContactPhoneNumbers[_clone].']"/>
</div>

</body>

</html>
