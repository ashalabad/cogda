<%@ page import="com.cogda.domain.security.PendingUser;" %>
<form class="form-horizontal" id="pendingUserForm" novalidate="novalidate">

    <g:hiddenField name="id" value="${pendingUserInstance?.id}"/>
    <g:hiddenField name="version" value="${pendingUserInstance?.version}"/>

    <fieldset class="form" id="pendingUserFieldset">

            <div class="control-group fieldcontain ${hasErrors(bean: pendingUserInstance, field: 'firstName', 'error')} ">
                <label for="firstName" class="control-label">
                    <g:message code="pendingUser.firstName.label" default="First Name"/>
                    <span class="required-indicator">*</span>
                </label>

                <div class="controls">
                    <g:textField name="firstName" value="${pendingUserInstance?.firstName}" required="required" class="input-xlarge"/>

                </div>
            </div>

            <div class="control-group fieldcontain ${hasErrors(bean: pendingUserInstance, field: 'lastName', 'error')} ">
                <label for="lastName" class="control-label">

                    <g:message code="pendingUser.lastName.label" default="Last Name"/>
                    <span class="required-indicator">*</span>
                </label>

                <div class="controls">
                    <g:textField name="lastName" value="${pendingUserInstance?.lastName}" required="required" class="input-xlarge"/>

                </div>
            </div>

            <div class="control-group fieldcontain ${hasErrors(bean: pendingUserInstance, field: 'emailAddress', 'error')} ">
                <label for="emailAddress" class="control-label">

                    <g:message code="pendingUser.emailAddress.label" default="Email Address"/>
                    <span class="required-indicator">*</span>
                </label>

                <div class="controls">
                    <g:textField name="emailAddress" value="${pendingUserInstance?.emailAddress}" class="input-xlarge"/>

                </div>
            </div>
            <div class="field">
                <label class="control-label">
                    <g:message code="pendingUser.securityRoles.label"/>
                </label>
                <g:each in = "${authorities}" status = "i" var = "authority">

                    <div class="controls">
                        <label class="checkbox">
                            <g:if test = "${pendingUserInstance?.securityRoles?.contains(authority)}">
                                <input type="checkbox" name="securityRoles" id = "securityRoles_${authority}" value="${authority}" checked = "checked"> ${authority}
                            </g:if>
                            <g:else>
                                <input type="checkbox" name="securityRoles" id = "securityRoles_${authority}" value="${authority}"> ${authority}
                            </g:else>
                        </label>
                    </div>
                </g:each>
            </div>

            <div class="form-actions">

            <g:if test="${pendingUserInstance?.id}">

                    <button type="button" id="pendingUserUpdateButton" class="btn btn-primary"  >
                        ${message(code: 'default.button.update.label')}
                    </button>
                    <button type="button" id="sendInviteButton" class="btn btn-primary"  >
                        <i class="icon-envelope"></i>
                        ${message(code: 'pendingUser.sendInvite.label')}
                    </button>

                    <button type="button"  id="pendingUserDeleteButton" class="btn btn-danger" >
                        ${message(code: 'default.button.delete.label')}
                    </button>

            </g:if>
            <g:else>
                <button type="button" id="pendingUserAddButton" class="btn btn-primary"  >
                    ${message(code: 'default.button.create.label')}
                </button>

            </g:else>


            </div>
    </fieldset>
</form>