<html>
<head>
    <title><g:message code="userImport.title"/></title>
    <meta name="layout" content="kickstart" />
    <g:set var="layout_nomainmenu"		value="${true}" scope="request"/>
    <g:set var="layout_nosecondarymenu"	value="${true}" scope="request"/>
    <g:set var="layout_noflashmessage"	value="${true}" scope="request"/>
</head>

<body>
<section id="userImportSection" class="first">

    <g:if test = "${flash.error}">
        <div class="alert alert-error" id="errorMessages" >

        </div>
    </g:if>

    <g:if test = "${flash.message}">
        <div class="alert alert-success" id="messages">
            ${flash.message}
        </div>
    </g:if>

    <g:hasErrors bean="${command}">
        <div class="alert alert-error">
            <g:renderErrors bean="${command}" as="list" />
        </div>
    </g:hasErrors>

    <div class="alert alert-info" id="resetPasswordMessage" >
        <g:message code="spring.security.resetPassword.description"/>
    </div>

    <g:uploadForm class = "form-horizontal" name="userImportForm" autocomplete="off" novalidate="novalidate"
            action="importUserFile" controller="userImport">

        <fieldset class="form">
        <legend>
            <g:message code="userImport.title"/>
        </legend>

        <div class="control-group fieldcontain ${hasErrors(bean: userImportCommandInstance, field: 'importFile', 'error')} required">
            <label for="file" class="control-label">
                <g:message code="userImport.importFile.label" default="User File" /> <span class="required-indicator">*</span> </label>
            <div class="controls">
                <input type = "file" name="file" id="file" required=""/>
                <span class="help-inline"></span>
            </div>
        </div>
        <div class="form-actions">
            <g:submitButton name="userImportButton" class="btn btn-primary" value="${message(code: 'userImport.import.button', default: 'Import')}" />
        </div>
    </g:uploadForm>
</section>
</body>
</html>