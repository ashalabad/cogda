<fieldset class="embedded">
    <div class="control-group fieldcontain">
        <label for="contactEmailAddress.primaryEmailAddress" class="control-label"><g:message
                code="leadContactEmailAddress.primaryEmailAddress.label"
                default="Primary Email Address"/></label>

        <div class="controls">
            <input type="checkbox" data-ng-model="contactEmailAddress.primaryEmailAddress"
                   name="contactEmailAddress.primaryEmailAddress"
                   id="contactEmailAddress.primaryEmailAddress"/>
        </div>
    </div>

    <div class="control-group fieldcontain"
         data-ng-class="{error: contactEmailAddressForm['contactEmailAddress.emailAddress'].$invalid && contactEmailAddressForm['contactEmailAddress.emailAddress'].$dirty, success: contactEmailAddressForm['contactEmailAddress.emailAddress'].$valid} && contactEmailAddressForm['contactEmailAddress.emailAddress'].$dirty">
        <label for="contactEmailAddress.emailAddress" class="control-label"><g:message
                code="leadContact.emailAddress"
                default="Email Address"/><span class="required-indicator">*</span></label>

        <div class="controls">
            <input type="email" id="contactEmailAddress.emailAddress"
                   name="contactEmailAddress.emailAddress"
                   data-ng-model="contactEmailAddress.emailAddress"
                   data-ng-required="true"/>
            <span class="label label-important"
                  data-ng-show="errors['contactEmailAddress.emailAddress']">{{ errors['contactEmailAddress.emailAddress'] }}</span>
            <span class="label label-important"
                  data-ng-show="contactEmailAddressForm['contactEmailAddress.emailAddress'].$invalid && contactEmailAddressForm['contactEmailAddress.emailAddress'].$dirty">${message(code: 'default.invalid.message')}</span>
            <label class="error valid"
                   data-ng-show="contactEmailAddressForm['contactEmailAddress.emailAddress'].$valid && contactEmailAddressForm['contactEmailAddress.emailAddress'].$dirty">${message(code: 'default.ok.message')}</label>
        </div>
    </div>
</fieldset>