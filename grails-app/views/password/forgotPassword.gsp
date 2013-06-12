<html>
    <head>
        <title><g:message code="forgotPassword.title"/></title>
        <meta name="layout" content="kickstart" />
        <g:set var="layout_nomainmenu"		value="${true}" scope="request"/>
        <g:set var="layout_nosecondarymenu"	value="${true}" scope="request"/>
        <g:set var="layout_noflashmessage"	value="${true}" scope="request"/>
    </head>

    <body>
        <section id="forgotPasswordSection" class="first">
            <g:if test = "${flash.error}">
                <div class="alert alert-error" id="errorMessages" >
                    ${flash.error}
                </div>
            </g:if>
            <g:if test = "${flash.message}">
                <div class="alert alert-success" id="messages">
                    ${flash.message}
                </div>
            </g:if>

            <g:if test="${!emailSent}">
                <div class="alert alert-info" id="passwordRecoveryInfo" >
                    <g:message code="spring.security.forgotPassword.description"/>
                </div>

                <g:form class = "form-horizontal" name="forgotPasswordForm" autocomplete="off" novalidate="novalidate"
                       controller="password" action="forgotPassword">
                    <fieldset class="form">
                        <legend>
                            <g:message code="forgotPassword.title"/>
                        </legend>

                        <i><g:message code="forgotPassword.username.known.label"/></i>

                        <div class="control-group fieldcontain ${hasErrors(bean: registrationInstance, field: 'firstName', 'error')} required">
                            <label for="username" class="control-label">
                                <g:message code="springSecurity.login.username.label" default="Username" /><span class="required-indicator">*</span></label>
                            <div class="controls">
                                <g:textField name="username" required="" value=""/>
                                <span class="help-inline"></span>
                            </div>
                        </div>
                        <div class="form-actions">
                            <g:submitButton name="resetPasswordButton" class="btn btn-primary" value="${message(code: 'spring.security.forgotPassword.sendRecoveryEmailButton', default: 'Send Password Recovery Email')}" />
                        </div>
                    </fieldset>
                </g:form>
            </g:if>
        </section>
    </body>
</html>