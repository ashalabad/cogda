<%@ page import="com.cogda.domain.Address" %>
<address class="inner-list-address">
    <strong>${addressInstance?.addressOne?.encodeAsHTML()}</strong>
    <g:if test="${addressInstance?.addressTwo}">
        <br/> ${addressInstance?.addressTwo?.encodeAsHTML()}
    </g:if>
    <g:if test="${addressInstance?.addressTwo}">
        <br/> ${addressInstance?.addressThree?.encodeAsHTML()}
    </g:if>
    <g:if test="${addressInstance?.zipcode}">
        <br/> ${addressInstance?.zipcode?.encodeAsHTML()}
    </g:if>
    <g:if test="${addressInstance?.city}">
        <br/> ${addressInstance?.city?.encodeAsHTML()}
    </g:if>
    <g:if test="${addressInstance?.state}">
        <br/> ${addressInstance?.state?.encodeAsHTML()}
    </g:if>
    <g:if test="${addressInstance?.country}">
        <br/> ${addressInstance?.country?.toUpperCase()?.encodeAsHTML()}
    </g:if>
</address>