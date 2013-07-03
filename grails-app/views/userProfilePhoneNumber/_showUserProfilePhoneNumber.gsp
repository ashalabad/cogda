<%@ page import="com.cogda.domain.UserProfilePhoneNumber" %>
<li id="userProfilePhoneNumber_${userProfilePhoneNumberInstance?.id}" style="padding-bottom:3px;">
    ${userProfilePhoneNumberInstance.phoneNumber.encodeAsHTML()}

    <button class="btn btn-info btn-mini editUserProfilePhoneNumberButton" id="userProfilePhoneNumberEditButton_${userProfilePhoneNumberInstance?.id}" data-id="${userProfilePhoneNumberInstance?.id}">
        <i class="icon-edit"></i> <g:message code="default.button.edit.label"/>
    </button>
    <button class="btn btn-danger btn-mini deleteUserProfilePhoneNumberButton" id="userProfilePhoneNumberDeleteButton_${userProfilePhoneNumberInstance?.id}" data-id="${userProfilePhoneNumberInstance?.id}">
        <i class="icon-remove"></i> <g:message code="default.button.delete.label"/>
    </button>

    <g:if test="${userProfilePhoneNumberInstance?.primaryPhoneNumber}">
        <span class="label label-info">
            <g:message code="label.primary"/>
        </span>
    </g:if>
</li>