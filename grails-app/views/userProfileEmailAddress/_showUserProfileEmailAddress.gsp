<%@ page import="com.cogda.domain.UserProfileEmailAddress" %>
<li id="userProfileEmailAddress_${userProfileEmailAddressInstance?.id}" style="padding-bottom:3px;">


    ${userProfileEmailAddressInstance.emailAddress.encodeAsHTML()}

    <button class="btn btn-info btn-mini editUserProfileEmailAddressButton" id="userProfileEmailAddressEditButton_${userProfileEmailAddressInstance?.id}" data-id="${userProfileEmailAddressInstance?.id}">
        <i class="icon-edit"></i> <g:message code="default.button.edit.label"/>
    </button>
    <button class="btn btn-danger btn-mini deleteUserProfileEmailAddressButton" id="userProfileEmailAddressDeleteButton_${userProfileEmailAddressInstance?.id}" data-id="${userProfileEmailAddressInstance?.id}">
        <i class="icon-remove"></i> <g:message code="default.button.delete.label"/>
    </button>

    <g:if test="${userProfileEmailAddressInstance?.primaryEmailAddress}">
        <span class="label label-info">
            <g:message code="label.primary"/>
        </span>
    </g:if>


</li>