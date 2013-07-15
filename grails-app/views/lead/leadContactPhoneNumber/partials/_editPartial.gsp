<div class="control-group fieldcontain"
     data-ng-class="{error: contactPhoneNumberForm['contactPhoneNumber.description'].$invalid && contactPhoneNumberForm['contactPhoneNumber.description'].$dirty, success: contactPhoneNumberForm['contactPhoneNumber.description'].$valid && contactPhoneNumberForm['contactPhoneNumber.description'].$dirty}">
    <label for="contactPhoneNumber.description" class="control-label"><g:message
            code="leadContactPhoneNumber.description.label"
            default="Phone Description"/></label>

    <div class="controls">
        <input type="text" id="contactPhoneNumber.description"
               name="contactPhoneNumber.description"
               data-ng-model="contactPhoneNumber.description"/>
        <span class="label label-important"
              data-ng-show="errors['contactPhoneNumber.description']">{{ errors['contactPhoneNumber.description'] }}</span>
        <span class="label label-important"
              data-ng-show="contactPhoneNumberForm['contactPhoneNumber.description'].$invalid && contactPhoneNumberForm['contactPhoneNumber.description'].$dirty">${message(code: 'default.invalid.message')}</span>
        <label class="error valid"
               data-ng-show="contactPhoneNumberForm['contactPhoneNumber.description'].$valid && contactPhoneNumberForm['contactPhoneNumber.description'].$dirty">${message(code: 'default.ok.message')}</label>
    </div>
</div>

<div class="control-group fieldcontain"
     data-ng-class="{error: contactPhoneNumberForm['contactPhoneNumber.phoneNumber'].$invalid && contactPhoneNumberForm['contactPhoneNumber.phoneNumber'].$dirty, success: contactPhoneNumberForm['contactPhoneNumber.phoneNumber'].$valid && contactPhoneNumberForm['contactPhoneNumber.phoneNumber'].$dirty}">
    <label for="contactPhoneNumber.phoneNumber" class="control-label"><g:message
            code="leadContactPhoneNumber.phoneNumber.label"
            default="Phone Number"/><span class="required-indicator">*</span></label>

    <div class="controls">
        <input type="text" id="contactPhoneNumber.phoneNumber"
               name="contactPhoneNumber.phoneNumber"
               data-ng-model="contactPhoneNumber.phoneNumber"
               data-ng-required="true"/>
        <span class="label label-important"
              data-ng-show="errors['contactPhoneNumber.phoneNumber']">{{ errors['contactPhoneNumber.phoneNumber']}}</span>
        <span class="label label-important"
              data-ng-show="contactPhoneNumberForm['contactPhoneNumber.phoneNumber'].$invalid && contactPhoneNumberForm['contactPhoneNumber.phoneNumber'].$dirty">${message(code: 'default.invalid.message')}</span>
        <label class="error valid"
               data-ng-show="contactPhoneNumberForm['contactPhoneNumber.phoneNumber'].$valid && contactPhoneNumberForm['contactPhoneNumber.phoneNumber'].$dirty">${message(code: 'default.ok.message')}</label>
    </div>
</div>