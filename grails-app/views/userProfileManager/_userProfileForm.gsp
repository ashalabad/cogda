<%@ page import="com.cogda.domain.UserProfile" %>

<g:form method="post" class="form-horizontal" name="userProfileDetailsForm" novalidate="novalidate" >
    <fieldset class="form" id="userProfileDetailsFieldset">
        <g:render template="/userProfileManager/userProfileShowForm" model="[userProfileInstance:userProfileInstance]"/>

    </fieldset>
</g:form>

<form id = "upaForm" class="form-horizontal">
        <fieldset class="embedded">
            <legend class="embedded">
                <g:message code="userProfileAddress.label"/>

                <span class="pull-right">
                    <button class="btn btn-success  " data-userprofile-id="${userProfileInstance?.id}" id="addUserProfileAddressButton">
                        <i class="icon-plus"></i>
                        ${message(code: 'default.add.label', args: [message(code: 'userProfileAddress.label', default: 'UserProfileAddress')])}
                    </button>
                </span>
            </legend>


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
                </div>
            </div>
        </fieldset>
</form>
<form id = "upeaForm" class="form-horizontal">
        <fieldset class="embedded">
            <legend >
                <g:message code="userProfileEmailAddress.label"/>

                <span class="pull-right">
                    <button class="btn btn-success" data-userprofile-id="${userProfileInstance?.id}" id="addUserProfileEmailAddress">
                        <i class="icon-plus"></i>
                        ${message(code: 'default.add.label', args: [message(code: 'userProfileEmailAddress.label', default: 'UserProfileEmailAddress')])}
                    </button>
                </span>
            </legend>
            <div class="control-group fieldcontain ${hasErrors(bean: userProfileInstance, field: 'userProfileEmailAddresses', 'error')} ">
                <label class="control-label"><g:message code="userProfile.userProfileEmailAddresses.label" default="User Profile Email Addresses" /></label>
                <div class="controls">

                    <ul class = "unstyled" id="userProfileEmailAddressContainer">
                    <g:each in="${userProfileInstance?.userProfileEmailAddresses?}" var="userProfileEmailAddressInstance">
                        <g:render template="/userProfileEmailAddress/showUserProfileEmailAddress" model="${['userProfileEmailAddressInstance':userProfileEmailAddressInstance]}"/>
                    </g:each>
                    </ul>
                    <span class="help-inline">${hasErrors(bean: userProfileInstance, field: 'userProfileEmailAddresses', 'error')}</span>
                </div>
            </div>
        </fieldset>
</form>

<form id = "uppnForm" class="form-horizontal">
        <fieldset class="embedded">
            <legend>
                <g:message code="userProfilePhoneNumber.label"/>
                <span class="pull-right">
                <button class="btn btn-success " data-userprofile-id="${userProfileInstance?.id}" id="addUserProfilePhoneNumber">
                    <i class="icon-plus"></i>
                    ${message(code: 'default.add.label', args: [message(code: 'userProfilePhoneNumber.label', default: 'UserProfilePhoneNumber')])}
                </button>
                </span>

            </legend>


            <div class="control-group fieldcontain ${hasErrors(bean: userProfileInstance, field: 'userProfilePhoneNumbers', 'error')} ">
                <label class="control-label"><g:message code="userProfile.userProfilePhoneNumbers.label" default="User Profile Phone Numbers" /></label>
                <div class="controls">

                    <ul class="one-to-many">
                        <g:each in="${userProfileInstance?.userProfilePhoneNumbers?}" var="u">
                            <li><g:link controller="userProfilePhoneNumber" action="show" id="${u.id}">${u?.encodeAsHTML()}</g:link></li>
                        </g:each>
                    </ul>
                    <span class="help-inline">${hasErrors(bean: userProfileInstance, field: 'userProfilePhoneNumbers', 'error')}</span>
                </div>
            </div>
        </fieldset>

</form>
