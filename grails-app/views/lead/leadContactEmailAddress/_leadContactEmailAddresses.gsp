<%@ page import="com.cogda.multitenant.Lead;com.cogda.multitenant.LeadContact" %>

<div id="childList">
    <g:each var="leadContactEmailAddress" in="${leadContactInstance?.leadContactEmailAddresses}" status="i">

        <g:render template='/lead/leadContactEmailAddress/form'
                  model="['leadContactEmailAddress': leadContactEmailAddress, 'i': i, 'hidden': false, 'prefix': prefix + 'leadContactEmailAddresses[' + i + '].']"/>
    </g:each>
</div>