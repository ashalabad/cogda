<%@ page import="com.cogda.multitenant.Lead" %>
<g:javascript src="suspect/edit.js"/>
<section id="create-lead" class="first">

    <g:hasErrors bean="${suspectInstance}">
        <div class="alert alert-error">
            <g:renderErrors bean="${suspectInstance}" as="list"/>
        </div>
    </g:hasErrors>

    <g:form action="save" class="form-horizontal new" name="suspectForm">
        <fieldset class="form">
            <g:render template="/lead/form" model="['leadInstance': suspectInstance]"/>
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
              model="['leadContactPhoneNumberInstance': null, 'i': '_clone', 'hidden': true, 'prefix': 'leadContacts[_clone].leadContactPhoneNumbers[_clone].']"/>
</div>