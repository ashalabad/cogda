<div class="control-group fieldcontain"
     data-ng-class="{error: contactPhoneNumberForm['contactPhoneNumber.phoneDescription'].$invalid && contactPhoneNumberForm['contactPhoneNumber.phoneDescription'].$dirty, success: contactPhoneNumberForm['contactPhoneNumber.phoneDescription'].$valid && contactPhoneNumberForm['contactPhoneNumber.phoneDescription'].$dirty}">
    <label for="contactPhoneNumber.phoneDescription" class="control-label"><g:message
            code="leadContactPhoneNumber.description.label"
            default="Phone Description"/></label>

    <div class="controls">
        <input type="text" id="contactPhoneNumber.phoneDescription"
               name="contactPhoneNumber.phoneDescription"
               data-ng-model="contactPhoneNumber.phoneDescription"/>
        <span class="label label-important"
              data-ng-show="errors['contactPhoneNumber.phoneDescription']">{{ errors['contactPhoneNumber.phoneDescription'] }}</span>
        <span class="label label-important"
              data-ng-show="contactPhoneNumberForm['contactPhoneNumber.phoneDescription'].$invalid && contactPhoneNumberForm['contactPhoneNumber.phoneDescription'].$dirty">${message(code: 'default.invalid.message')}</span>
        <label class="error valid"
               data-ng-show="contactPhoneNumberForm['contactPhoneNumber.phoneDescription'].$valid && contactPhoneNumberForm['contactPhoneNumber.phoneDescription'].$dirty">${message(code: 'default.ok.message')}</label>
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