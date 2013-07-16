<fieldset class="embedded"><legend><g:message code="lead.contact.label" default="Contact"/></legend>

    <div data-ng-repeat="contact in lead.leadContacts">
        <input type="hidden" data-ng-value="lead.leadContacts[0].primaryContact" value="true"
               name="leadContacts[0].primaryContact"/>

        <div class="control-group fieldcontain"
             data-ng-class="{error: leadForm['leadContacts[0].firstName'].$invalid && leadForm['leadContacts[0].firstName'].$dirty, success: leadForm.clientName.$valid} && leadForm['leadContacts[0].firstName'].$dirty">
            <label for="leadContacts[0].firstName" class="control-label"><g:message
                    code="leadContact.firstName"
                    default="First Name"/><span class="required-indicator">*</span></label>

            <div class="controls">
                <input type="text" id="leadContacts[0].firstName" name="leadContacts[0].firstName"
                       data-ng-model="lead.leadContacts[$index].firstName" data-ng-required="true"/>
                <span class="label label-important"
                      data-ng-show="errors['leadContacts[0].firstName']">{{ errors['leadContacts[0].firstName'] }}</span>
                <span class="label label-important"
                      data-ng-show="leadForm['leadContacts[0].firstName'].$invalid && leadForm['leadContacts[0].firstName'].$dirty">${message(code: 'default.invalid.message')}</span>
                <label class="error valid"
                       data-ng-show="leadForm['leadContacts[0].firstName'].$valid && leadForm['leadContacts[0].firstName'].$dirty">${message(code: 'default.ok.message')}</label>
            </div>
        </div>

        <div class="control-group fieldcontain"
             data-ng-class="{error: leadForm['leadContacts[0].lastName'].$invalid && leadForm['leadContacts[0].lastName'].$dirty, success: leadForm['leadContacts[0].lastName'].$valid} && leadForm['leadContacts[0].lastName'].$dirty">
            <label for="leadContacts[0].lastName" class="control-label"><g:message
                    code="leadContact.lastName"
                    default="Last Name"/><span class="required-indicator">*</span></label>

            <div class="controls">
                <input type="text" id="leadContacts[0].lastName" name="leadContacts[0].lastName"
                       data-ng-model="lead.leadContacts[$index].lastName" data-ng-required="true"/>
                <span class="label label-important"
                      data-ng-show="errors['leadContacts[0].lastName']">{{ errors['leadContacts[0].lastName'] }}</span>
                <span class="label label-important"
                      data-ng-show="leadForm['leadContacts[0].lastName'].$invalid && leadForm['leadContacts[0].lastName'].$dirty">${message(code: 'default.invalid.message')}</span>
                <label class="error valid"
                       data-ng-show="leadForm['leadContacts[0].lastName'].$valid && leadForm['leadContacts[0].lastName'].$dirty">${message(code: 'default.ok.message')}</label>
            </div>
        </div>

        <div class="control-group fieldcontain"
             data-ng-class="{error: leadForm['leadContacts[0].leadContactEmailAddresses[0].emailAddress'].$invalid && leadForm['leadContacts[0].leadContactEmailAddresses[0].emailAddress'].$dirty, success: leadForm['leadContacts[0].leadContactEmailAddresses[0].emailAddress'].$valid} && leadForm['leadContacts[0].leadContactEmailAddresses[0].emailAddress'].$dirty">
            <label for="leadContacts[0].leadContactEmailAddresses[0].emailAddress" class="control-label"><g:message
                    code="leadContact.emailAddress"
                    default="Email Address"/><span class="required-indicator">*</span></label>

            <div class="controls">
                <input type="email" id="leadContacts[0].leadContactEmailAddresses[0].emailAddress"
                       name="leadContacts[0].leadContactEmailAddresses[0].emailAddress"
                       data-ng-model="lead.leadContacts[$index].leadContactEmailAddresses[0].emailAddress"
                       data-ng-required="true"/>
                <span class="label label-important"
                      data-ng-show="errors['leadContacts[0].leadContactEmailAddresses[0].emailAddress']">{{ errors['leadContacts[0].leadContactEmailAddresses[0].emailAddress'] }}</span>
                <span class="label label-important"
                      data-ng-show="leadForm['leadContacts[0].leadContactEmailAddresses[0].emailAddress'].$invalid && leadForm['leadContacts[0].leadContactEmailAddresses[0].emailAddress'].$dirty">${message(code: 'default.invalid.message')}</span>
                <label class="error valid"
                       data-ng-show="leadForm['leadContacts[0].leadContactEmailAddresses[0].emailAddress'].$valid && leadForm['leadContacts[0].leadContactEmailAddresses[0].emailAddress'].$dirty">${message(code: 'default.ok.message')}</label>
            </div>
        </div>

        <div class="control-group fieldcontain"
             data-ng-class="{error: leadForm['leadContacts[0].leadContactPhoneNumbers[0].phoneDescription'].$invalid && leadForm['leadContacts[0].leadContactPhoneNumbers[0].phoneDescription'].$dirty, success: leadForm['leadContacts[0].leadContactPhoneNumbers[0].phoneDescription'].$valid && leadForm['leadContacts[0].leadContactPhoneNumbers[0].phoneDescription'].$dirty}">
            <label for="leadContacts[0].leadContactPhoneNumbers[0].phoneDescription" class="control-label"><g:message
                    code="leadContactPhoneNumber.description.label"
                    default="Phone Description"/></label>

            <div class="controls">
                <input type="text" id="leadContacts[0].leadContactPhoneNumbers[0].phoneDescription"
                       name="leadContacts[0].leadContactPhoneNumbers[0].phoneDescription"
                       data-ng-model="lead.leadContacts[$index].leadContactPhoneNumbers[0].phoneDescription"/>
                <span class="label label-important"
                      data-ng-show="errors['leadContacts[0].leadContactPhoneNumbers[0].phoneDescription']">{{ errors['leadContacts[0].leadContactPhoneNumbers[0].phoneDescription'] }}</span>
                <span class="label label-important"
                      data-ng-show="leadForm['leadContacts[0].leadContactPhoneNumbers[0].phoneDescription'].$invalid && leadForm['leadContacts[0].leadContactPhoneNumbers[0].phoneDescription'].$dirty">${message(code: 'default.invalid.message')}</span>
                <label class="error valid"
                       data-ng-show="leadForm['leadContacts[0].leadContactPhoneNumbers[0].phoneDescription'].$valid && leadForm['leadContacts[0].leadContactPhoneNumbers[0].phoneDescription'].$dirty">${message(code: 'default.ok.message')}</label>
            </div>
        </div>

        <div class="control-group fieldcontain"
             data-ng-class="{error: leadForm['leadContacts[0].leadContactPhoneNumbers[0].phoneNumber'].$invalid && leadForm['leadContacts[0].leadContactPhoneNumbers[0].phoneNumber'].$dirty, success: leadForm['leadContacts[0].leadContactPhoneNumbers[0].phoneNumber'].$valid && leadForm['leadContacts[0].leadContactPhoneNumbers[0].phoneNumber'].$dirty}">
            <label for="leadContacts[0].leadContactPhoneNumbers[0].phoneNumber" class="control-label"><g:message
                    code="leadContactPhoneNumber.phoneNumber.label"
                    default="Phone Number"/><span class="required-indicator">*</span></label>

            <div class="controls">
                <input type="text" id="leadContacts[0].leadContactPhoneNumbers[0].phoneNumber"
                       name="leadContacts[0].leadContactPhoneNumbers[0].phoneNumber"
                       data-ng-model="lead.leadContacts[$index].leadContactPhoneNumbers[0].phoneNumber"
                       data-ng-required="true"/>
                <span class="label label-important"
                      data-ng-show="errors['leadContacts[0].leadContactPhoneNumbers[0].phoneNumber']">{{ errors['leadContacts[0].leadContactPhoneNumbers[0].phoneNumber']}}</span>
                <span class="label label-important"
                      data-ng-show="leadForm['leadContacts[0].leadContactPhoneNumbers[0].phoneNumber'].$invalid && leadForm['leadContacts[0].leadContactPhoneNumbers[0].phoneNumber'].$dirty">${message(code: 'default.invalid.message')}</span>
                <label class="error valid"
                       data-ng-show="leadForm['leadContacts[0].leadContactPhoneNumbers[0].phoneNumber'].$valid && leadForm['leadContacts[0].leadContactPhoneNumbers[0].phoneNumber'].$dirty">${message(code: 'default.ok.message')}</label>
            </div>
        </div>
    </div>
</fieldset>
