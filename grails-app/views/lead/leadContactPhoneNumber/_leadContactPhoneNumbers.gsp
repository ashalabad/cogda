<div id="childList">
    <g:each var="leadPhoneNumberInstance" in="${leadContactInstance?.leadContactPhoneNumbers}" status="i">

        <!-- Render the phone template (_phone.gsp) here -->
        <g:render template='/lead/leadContactPhoneNumber/form'
                  model="['leadContactPhoneNumberInstance': leadPhoneNumberInstance, 'i': i, 'hidden': false, 'prefix': prefix + 'leadContactPhoneNumbers[' + i + '].']"/>
        <!-- Render the phone template (_phone.gsp) here -->

    </g:each>
</div>

<div id='newPhoneList'>
    <g:render template='/lead/leadContactPhoneNumber/form'
              model="['leadContactPhoneNumberInstance': null, 'i': '_clone', 'hidden': true, 'prefix': prefix + 'leadContactPhoneNumbers[' + i + '].']"/>
</div>
<label class="control-label"><g:message code="leadContact.leadContactPhoneNumbers.label"
                                        default="Phone Numbers:"/></label>

<div class="control-group">
    <div class="controls">


        <a class="btn btn-success add-field" onclick="addPhone()">
            <g:message code="leadContactEmailAddress.addPhoneNumber.label" default="Add Phone Number"/></a>
    </div>
</div>