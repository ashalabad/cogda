<%@ page import="com.cogda.multitenant.Lead" %>
<g:javascript src="suspect/edit.js"/>
<section id="edit-lead" class="first">
    <g:set var="entityName" value="${message(code: 'suspect.label', default: 'Suspect')}" />
    <h4><g:message code="default.edit.label" args="[entityName]" /></h4>
    <g:hasErrors bean="${suspectInstance}">
        <div class="alert alert-error">
            <g:renderErrors bean="${suspectInstance}" as="list" />
        </div>
    </g:hasErrors>

    <g:form method="post" class="form-horizontal" name='suspectForm'>
        <g:hiddenField name="id" value="${suspectInstance?.id}"/>
        <g:hiddenField name="version" value="${suspectInstance?.version}"/>
        <fieldset class="form">
            <g:render template="form"/>
        </fieldset>
        <div class="form-actions">
            <g:actionSubmit class="btn btn-primary" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" />
            <g:actionSubmit class="btn btn-danger" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
            <button class="btn" type="reset"><g:message code="default.button.reset.label" default="Reset" /></button>
        </div>
    </g:form>

</section>