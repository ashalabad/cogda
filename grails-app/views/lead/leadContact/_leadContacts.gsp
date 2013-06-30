<%@ page import="com.cogda.multitenant.Lead" %>

<div id="childLeadContacts">
    <g:each var="leadContactInstance" in="${leadInstance?.leadContacts}" status="i">
        <g:render template='/lead/leadContact/form' model="['leadContactInstance': leadContactInstance, 'i': i, 'hidden': false, 'prefix': 'leadContacts[' + i + '].']"/>
    </g:each>
</div>