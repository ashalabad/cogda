<%@ page import="com.cogda.multitenant.Lead" %>
<g:javascript src="prospect/edit.js"/>
<section id="create-lead" class="first">

    <g:hasErrors bean="${prospectInstance}">
        <div class="alert alert-error">
            <g:renderErrors bean="${prospectInstance}" as="list"/>
        </div>
    </g:hasErrors>

    <g:form action="save" class="form-horizontal" name="prospectForm">
        <fieldset class="form">
            <g:render template="/lead/form" model="['leadInstance': prospectInstance]"/>
        </fieldset>

        <div class="form-actions">
            <g:submitButton name="update" class="btn btn-primary"
                            value="${message(code: 'default.button.update.label', default: 'Update')}"/>
            <button class="btn" type="reset"><g:message code="default.button.reset.label" default="Reset"/></button>
        </div>
    </g:form>

</section>

<div id='newPhoneList'>
    <g:render template='/lead/leadContactPhoneNumber/form'
              model="['leadContactPhoneNumberInstance': null, 'i': '_clone', 'hidden': true, 'prefix': 'leadContacts[_clone].leadContactPhoneNumbers[_clone].']"/>
</div>

