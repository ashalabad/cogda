<%@ page import="com.cogda.multitenant.Lead;com.cogda.multitenant.LeadContact" %>

<div id="childLeadContactEmailAddresses" class="control-group fieldcontain">
    <g:each var="leadNoteInstance" in="${leadInstance?.leadNotes}" status="i">
       <g:render template='/lead/leadNote/form'
                  model="[leadNoteInstance: leadNoteInstance, i: i, hidden: false, prefix: 'leadNotes[' + i + '].']"/>
    </g:each>
</div>