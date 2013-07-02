<%@ page import="com.cogda.domain.UserProfile" %>
<g:form method="post" class="form-horizontal" >
    <g:hiddenField name="id" value="${userProfileInstance?.id}" />
    <g:hiddenField name="version" value="${userProfileInstance?.version}" />
    <fieldset class="form">
        <div class="control-group fieldcontain ${hasErrors(bean: userProfileInstance, field: 'published', 'error')} ">
            <label for="published" class="control-label"><g:message code="userProfile.published.label" default="Published" /></label>
            <div class="controls">
                <bs:checkBox name="published" value="${userProfileInstance?.published}" />
                <span class="help-inline">${hasErrors(bean: userProfileInstance, field: 'published', 'error')}</span>
            </div>
        </div>

        <div class="control-group fieldcontain ${hasErrors(bean: userProfileInstance, field: 'firstName', 'error')} required">
            <label for="firstName" class="control-label"><g:message code="userProfile.firstName.label" default="First Name" /><span class="required-indicator">*</span></label>
            <div class="controls">
                <g:textField name="firstName" required="" value="${userProfileInstance?.firstName}"/>
                <span class="help-inline">${hasErrors(bean: userProfileInstance, field: 'firstName', 'error')}</span>
            </div>
        </div>

        <div class="control-group fieldcontain ${hasErrors(bean: userProfileInstance, field: 'middleName', 'error')} ">
            <label for="middleName" class="control-label"><g:message code="userProfile.middleName.label" default="Middle Name" /></label>
            <div class="controls">
                <g:textField name="middleName" value="${userProfileInstance?.middleName}"/>
                <span class="help-inline">${hasErrors(bean: userProfileInstance, field: 'middleName', 'error')}</span>
            </div>
        </div>

        <div class="control-group fieldcontain ${hasErrors(bean: userProfileInstance, field: 'lastName', 'error')} required">
            <label for="lastName" class="control-label"><g:message code="userProfile.lastName.label" default="Last Name" /><span class="required-indicator">*</span></label>
            <div class="controls">
                <g:textField name="lastName" required="" value="${userProfileInstance?.lastName}"/>
                <span class="help-inline">${hasErrors(bean: userProfileInstance, field: 'lastName', 'error')}</span>
            </div>
        </div>

        <div class="control-group fieldcontain ">
            <label class="control-label"><g:message code="userProfile.company.label" default="Company" /></label>
            <div class="controls" >
                ${userProfileInstance?.company?.companyName}
                <span class="help-inline">${hasErrors(bean: userProfileInstance, field: 'aboutDesc', 'error')}</span>
            </div>
        </div>

        <div class="control-group fieldcontain ${hasErrors(bean: userProfileInstance, field: 'aboutDesc', 'error')} ">
            <label for="aboutDesc" class="control-label"><g:message code="userProfile.aboutDesc.label" default="About Desc" /></label>
            <div class="controls">
                <g:textArea name="aboutDesc" value="${userProfileInstance?.aboutDesc}" class="bigtextarea"/>
                <span class="help-inline">${hasErrors(bean: userProfileInstance, field: 'aboutDesc', 'error')}</span>
            </div>
        </div>

        <div class="control-group fieldcontain ${hasErrors(bean: userProfileInstance, field: 'businessSpecialtiesDesc', 'error')} ">
            <label for="businessSpecialtiesDesc" class="control-label"><g:message code="userProfile.businessSpecialtiesDesc.label" default="Business Specialties Desc" /></label>
            <div class="controls">
                <g:textArea name="businessSpecialtiesDesc" value="${userProfileInstance?.businessSpecialtiesDesc}" class="bigtextarea"/>
                <span class="help-inline">${hasErrors(bean: userProfileInstance, field: 'businessSpecialtiesDesc', 'error')}</span>
            </div>
        </div>

        <div class="control-group fieldcontain ${hasErrors(bean: userProfileInstance, field: 'associationsDesc', 'error')} ">
            <label for="associationsDesc" class="control-label"><g:message code="userProfile.associationsDesc.label" default="Associations Desc" /></label>
            <div class="controls">
                <g:textArea name="associationsDesc" value="${userProfileInstance?.associationsDesc}" class="bigtextarea"/>
                <span class="help-inline">${hasErrors(bean: userProfileInstance, field: 'associationsDesc', 'error')}</span>
            </div>
        </div>

        <div class="control-group fieldcontain ${hasErrors(bean: userProfileInstance, field: 'userProfileAddresses', 'error')} ">
            <label  class="control-label"><g:message code="userProfile.userProfileAddresses.label" default="User Profile Addresses" />
            </label>
            <div class="controls">
                <div id="userProfileAddressContainer">
                    <g:each in="${userProfileInstance?.userProfileAddresses?}" var="userProfileAddressInstance" status="i">
                        <g:render template="/userProfileAddress/showUserProfileAddress" model="${['userProfileAddressInstance':userProfileAddressInstance]}"/>
                    </g:each>
                </div>
                <span class="help-inline">
                    ${hasErrors(bean: userProfileInstance, field: 'userProfileAddresses', 'error')}
                </span>
                <div class="add-button">
                    <button class="btn btn-success btn-mini " data-userprofile-id="${userProfileInstance?.id}" id="addUserProfileAddressButton">
                        <i class="icon-plus"></i>
                        ${message(code: 'default.add.label', args: [message(code: 'userProfileAddress.label', default: 'UserProfileAddress')])}
                    </button>
                </div>
            </div>
        </div>

        <div class="control-group fieldcontain ${hasErrors(bean: userProfileInstance, field: 'userProfileEmailAddresses', 'error')} ">
            <label class="control-label"><g:message code="userProfile.userProfileEmailAddresses.label" default="User Profile Email Addresses" /></label>
            <div class="controls">

                <ul class="one-to-many">
                    <g:each in="${userProfileInstance?.userProfileEmailAddresses?}" var="u">
                        <li><g:link controller="userProfileEmailAddress" action="show" id="${u.id}">${u?.encodeAsHTML()}</g:link></li>
                    </g:each>
                </ul>
                <span class="help-inline">${hasErrors(bean: userProfileInstance, field: 'userProfileEmailAddresses', 'error')}</span>
                <div class="add-button">
                    <button class="btn btn-success btn-mini" data-userprofile-id="${userProfileInstance?.id}" id="addUserProfileEmailAddress">
                        <i class="icon-plus"></i>
                        ${message(code: 'default.add.label', args: [message(code: 'userProfileEmailAddress.label', default: 'UserProfileEmailAddress')])}
                    </button>
                </div>
            </div>
        </div>

        <div class="control-group fieldcontain ${hasErrors(bean: userProfileInstance, field: 'userProfilePhoneNumbers', 'error')} ">
            <label class="control-label"><g:message code="userProfile.userProfilePhoneNumbers.label" default="User Profile Phone Numbers" /></label>
            <div class="controls">

                <ul class="one-to-many">
                    <g:each in="${userProfileInstance?.userProfilePhoneNumbers?}" var="u">
                        <li><g:link controller="userProfilePhoneNumber" action="show" id="${u.id}">${u?.encodeAsHTML()}</g:link></li>
                    </g:each>
                </ul>
                <span class="help-inline">${hasErrors(bean: userProfileInstance, field: 'userProfilePhoneNumbers', 'error')}</span>
                <div class="add-button">
                    <button class="btn btn-success btn-mini" data-userprofile-id="${userProfileInstance?.id}" id="addUserProfilePhoneNumber">
                        <i class="icon-plus"></i>
                        ${message(code: 'default.add.label', args: [message(code: 'userProfilePhoneNumber.label', default: 'UserProfilePhoneNumber')])}
                    </button>
                </div>
            </div>
        </div>

    </fieldset>
</g:form>