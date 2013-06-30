<g:javascript>
        init("${prefix}", ${leadContactInstance.leadContactPhoneNumbers.size()});
</g:javascript>
<div id="childLeadContactPhoneNumbers">
    <g:each var="leadPhoneNumberInstance" in="${leadContactInstance?.leadContactPhoneNumbers}" status="i">
        <g:render template='/lead/leadContactPhoneNumber/form'
                  model="['leadContactPhoneNumberInstance': leadPhoneNumberInstance, 'i': i, 'hidden': false, 'prefix': prefix + 'leadContactPhoneNumbers[' + i + '].']"/>
    </g:each>
</div>

<div class="control-group">
    <div class="controls">
        <a class="btn btn-success add-field" onclick="addPhone()">
            <g:message code="leadContactEmailAddress.addPhoneNumber.label" default="Add Phone Number"/></a>
    </div>
</div>