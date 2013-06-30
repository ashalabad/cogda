<%@ page import="com.cogda.multitenant.Lead" %>

<div id="childList">
    <g:each var="leadContactInstance" in="${leadInstance?.leadContacts}" status="i">

        <!-- Render the phone template (_phone.gsp) here -->
        <g:render template='/lead/leadContact/form' model="['leadContactInstance': leadContactInstance, 'i': i, 'hidden': false, 'prefix': 'leadContacts[' + i + '].']"/>
        <!-- Render the phone template (_phone.gsp) here -->
    </g:each>
</div>