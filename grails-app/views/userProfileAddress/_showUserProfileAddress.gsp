<%@ page import="com.cogda.domain.UserProfileAddress" %>
<div id="userProfileAddress_${userProfileAddressInstance?.id}" class="address-list-item">
    <g:if test="${userProfileAddressInstance?.primaryAddress}">
        <span class="label label-info">
            <g:message code="label.primary" default="Primary"/>
        </span>
        <br/>
    </g:if>
    <g:if test="${userProfileAddressInstance?.addressType}">
        ${userProfileAddressInstance.addressType?.encodeAsHTML()}
    </g:if>

    <g:render template = "/address/showAddress" model="${['addressInstance':userProfileAddressInstance?.address]}"/>

    <button class="btn btn-info btn-mini editUserProfileAddressButton" id="userProfileAddressEditButton_${userProfileAddressInstance?.id}" data-id="${userProfileAddressInstance?.id}">
         <i class="icon-edit"></i> <g:message code="default.button.edit.label"/>
    </button>
    <button class="btn btn-danger btn-mini deleteUserProfileAddressButton" id="userProfileAddressDeleteButton_${userProfileAddressInstance?.id}" data-id="${userProfileAddressInstance?.id}">
         <i class="icon-remove"></i> <g:message code="default.button.delete.label"/>
    </button>

</div>

