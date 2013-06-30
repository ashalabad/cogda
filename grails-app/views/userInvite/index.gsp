<!doctype html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="kickstart" />
    <g:set var="layout_nosecondarymenu"	value="${true}" scope="request"/>
    <g:set var="layout_nomainmenu"		value="${true}" scope="request"/>
    <g:set var="layout_noflashmessage"  value="${true}" scope="request"/>
    <title>${message(code: 'userInvite.label')}</title>
    <script src="${resource(dir:"js/userInvite", file:'index.js')}" type="text/javascript" ></script>
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

<h2 id="pageTitle"><g:message code="userInvite.welcome"/></h2>

<section id="createInvitedUser" class="first">

    <div class="alert alert-success" id="messages" style="display:none">
    </div>

    <div class="alert alert-error" id="errors" style="display:none">
    </div>

    <div id = "userInviteDiv">

        <g:form class="form-horizontal" name="userInviteForm" novalidate="novalidate" autocomplete="off">

            <fieldset class="form">
                <legend><g:message code="userInvite.form.label"/></legend>

                <div class="control-group fieldcontain ${hasErrors(bean: userInviteCommand, field: 'firstName', 'error')} required">
                    <label for="firstName" class="control-label"><g:message code="registration.firstName.label" default="First Name" />
                        <span class="required-indicator">*</span></label>
                    <div class="controls">
                        <g:textField name="firstName"  value="${userInviteCommand?.firstName}" required="required" class="input-xlarge"/>
                        <span class="help-inline">${hasErrors(bean: userInviteCommand, field: 'firstName', 'error')}</span>
                    </div>
                </div>

                <div class="control-group fieldcontain ${hasErrors(bean: userInviteCommand, field: 'lastName', 'error')} required">
                    <label for="lastName" class="control-label"><g:message code="registration.lastName.label" default="Last Name" />
                        <span class="required-indicator">*</span></label>
                    <div class="controls">
                        <g:textField name="lastName"  value="${userInviteCommand?.lastName}" required="required" class="input-xlarge"/>
                        <span class="help-inline">${hasErrors(bean: userInviteCommand, field: 'lastName', 'error')}</span>
                    </div>
                </div>

                <div class="control-group fieldcontain ${hasErrors(bean: userInviteCommand, field: 'username', 'error')} required">
                    <label for="username" class="control-label"><g:message code="registration.username.label" default="Username" /><span class="required-indicator">*</span></label>
                    <div class="controls">
                        <g:textField name="username" required="required" value="${userInviteCommand?.username}" class="input-xlarge"/>
                        <span class="help-inline">
                            ${hasErrors(bean: userInviteCommand, field: 'username', 'error')}
                        </span>
                    </div>
                </div>

                <div class="control-group fieldcontain ${hasErrors(bean: userInviteCommand, field: 'emailAddress', 'error')} required">
                    <label for="emailAddress" class="control-label"><g:message code="registration.emailAddress.label" default="Last Name" />
                        <span class="required-indicator">*</span></label>
                    <div class="controls">
                        <g:textField name="emailAddress"  value="${userInviteCommand?.emailAddress}" required="required" class="input-xlarge"/>
                        <span class="help-inline">${hasErrors(bean: userInviteCommand, field: 'emailAddress', 'error')}</span>
                    </div>
                </div>

                <div class="control-group fieldcontain ${hasErrors(bean: userInviteCommand, field: 'password', 'error')} required">
                    <label for="password" class="control-label"><g:message code="registration.password.label" default="Password" />
                        <span class="required-indicator">*</span>
                    </label>
                    <div class="controls">
                        <g:passwordField name="password" required="required" value="${userInviteCommand?.password}" class="input-xlarge"/>
                        <span class="help-inline">
                            ${hasErrors(bean: userInviteCommand, field: 'password', 'error')}
                        </span>
                    </div>
                </div>

                <div class="control-group fieldcontain ${hasErrors(bean: userInviteCommand, field: 'passwordTwo', 'error')} required">
                    <label for="passwordTwo" class="control-label"><g:message code="registration.passwordTwo.label" default="Re-Type Password" />
                        <span class="required-indicator">*</span>
                    </label>
                    <div class="controls">
                        <g:passwordField name="passwordTwo" required="required" value="${userInviteCommand?.passwordTwo}" class="input-xlarge"/>
                        <g:hiddenField name="t" value="${userInviteCommand.t}"/>
                        <span class="help-inline">
                            ${hasErrors(bean: userInviteCommand, field: 'passwordTwo', 'error')}
                        </span>
                    </div>
                </div>
                <div class="form-actions">
                    <g:submitButton name="create" class="btn btn-primary" value="${message(code: 'userInvite.button.submit.label', default: 'Submit')}" />
                </div>
            </fieldset>

        </g:form>
    </div>
</section>
</body>
</html>