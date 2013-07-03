<%@ page import="com.cogda.multitenant.Lead;com.cogda.multitenant.LeadContact" %>

<div id="childLeadContactEmailAddresses" class="control-group fieldcontain">
    <g:each var="leadNoteInstance" in="${leadInstance?.notes}" status="i">
       <g:render template='/lead/leadNote/form'
                  model="[leadNoteInstance: leadNoteInstance, i: i, hidden: false, prefix: 'leadNoteInstance[' + i + '].']"/>
    </g:each>
</div>