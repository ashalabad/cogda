<fieldset class="embedded"><legend><g:message code="lead.contact.label" default="Contact"/></legend>

    <div class="control-group fieldcontain">
        <label for="contact.primaryContact" class="control-label"><g:message code="leadContact.primaryContact.label"
                                                       default="Primary Contact"/></label>

        <div class="controls">
            <input type="checkbox" data-ng-model="contact.primaryContact" name="contact.primaryContact"
                   id="contact.primaryContact"/>
        </div>
    </div>

    <div class="control-group fieldcontain"
         data-ng-class="{error: contactForm['contact.firstName'].$invalid && contactForm['contact.firstName'].$dirty, success: contactForm.clientName.$valid} && contactForm['contact.firstName'].$dirty">
        <label for="contact.firstName" class="control-label"><g:message
                code="leadContact.firstName"
                default="First Name"/><span class="required-indicator">*</span></label>

        <div class="controls">
            <input type="text" id="contact.firstName" name="contact.firstName"
                   data-ng-model="contact.firstName" data-ng-required="true"/>
            <span class="label label-important"
                  data-ng-show="errors['contact.firstName']">{{ errors['contact.firstName'] }}</span>
            <span class="label label-important"
                  data-ng-show="contactForm['contact.firstName'].$invalid && contactForm['contact.firstName'].$dirty">${message(code: 'default.invalid.message')}</span>
            <label class="error valid"
                   data-ng-show="contactForm['contact.firstName'].$valid && contactForm['contact.firstName'].$dirty">${message(code: 'default.ok.message')}</label>
        </div>
    </div>

    <div class="control-group fieldcontain"
         data-ng-class="{error: contactForm['contact.firstName'].$invalid && contactForm['contact.firstName'].$dirty, success: contactForm.clientName.$valid} && contactForm['contact.firstName'].$dirty">
        <label for="contact.firstName" class="control-label"><g:message
                code="leadContact.MiddleName.label"
                default="Middle Name"/></label>

        <div class="controls">
            <input type="text" id="contact.middleName" name="contact.middleName"
                   data-ng-model="contact.middleName" data-ng-required="true"/>
            <span class="label label-important"
                  data-ng-show="errors['contact.middleName']">{{ errors['contact.middleName'] }}</span>
            <span class="label label-important"
                  data-ng-show="contactForm['contact.middleName'].$invalid && contactForm['contact.middleName'].$dirty">${message(code: 'default.invalid.message')}</span>
            <label class="error valid"
                   data-ng-show="contactForm['contact.middleName'].$valid && contactForm['contact.middleName'].$dirty">${message(code: 'default.ok.message')}</label>
        </div>
    </div>

    <div class="control-group fieldcontain"
         data-ng-class="{error: contactForm['contact.lastName'].$invalid && contactForm['contact.lastName'].$dirty, success: contactForm['contact.lastName'].$valid} && contactForm['contact.lastName'].$dirty">
        <label for="contact.lastName" class="control-label"><g:message
                code="leadContact.lastName"
                default="Last Name"/><span class="required-indicator">*</span></label>

        <div class="controls">
            <input type="text" id="contact.lastName" name="contact.lastName"
                   data-ng-model="contact.lastName" data-ng-required="true"/>
            <span class="label label-important"
                  data-ng-show="errors['contact.lastName']">{{ errors['contact.lastName'] }}</span>
            <span class="label label-important"
                  data-ng-show="contactForm['contact.lastName'].$invalid && contactForm['contact.lastName'].$dirty">${message(code: 'default.invalid.message')}</span>
            <label class="error valid"
                   data-ng-show="contactForm['contact.lastName'].$valid && contactForm['contact.lastName'].$dirty">${message(code: 'default.ok.message')}</label>
        </div>
    </div>

</fieldset>
