<fieldset class="embedded">
    <div class="control-group fieldcontain"
         data-ng-class="{error: leadForm.subType.$invalid && leadForm.subType.$dirty, success: leadForm.subType.$valid && leadForm.subTYpe.$dirty }">
        <label class="control-label">
            <g:message code="lead.subType.label" default="Type"/>
            <span class="required-indicator">*</span>
        </label>

        <div class="controls">
            <div data-ng-repeat="leadSubType in leadSubTypes">
                <label class="radio inline" for='leadSubType[{{$index}}]'><span
                        class="radioSpan">{{leadSubType.label}}</span><input type="radio"
                                                                             data-ng-model="lead.subType"
                                                                             data-ng-value="leadSubType.label.toUpperCase()"
                                                                             name='subType'
                                                                             id="leadSubType[{{$index}}]"/>
                </label>
            </div>
        </div>
    </div>
    <div class="control-group fieldcontain"
         data-ng-class="{error: leadForm.businessType.$invalid && leadForm.businessType.$dirty, success: leadForm.businessType.$valid && leadForm.businessType.$dirty}">
        <label for="businessType" class="control-label"><g:message
                code="businessType.label"
                default="Business Type"/></label>

        <div class="controls">
            <select id="businessType" name='businessType' data-ng-model='lead.businessType'
                    data-ng-options="bt.description for bt in businessTypes" required></select>
            <span class="label label-important"
                  data-ng-show="leadForm.businessType.$invalid && leadForm.businessType.$dirty">${message(code: 'default.invalid.message')}</span>
            <span class="label label-important" data-ng-show="errors.businessType">{{ errors.businessType }}</span>
            <label class="error valid"
                   data-ng-show="leadForm.businessType.$valid && leadForm.businessType.$dirty">${message(code: 'default.ok.message')}</label>
        </div>
    </div>


    <div class="control-group fieldcontain"
         data-ng-class="{error: leadForm.clientId.$invalid && leadForm.clientId.$dirty, success: leadForm.clientId.$valid}">
        <label for="clientId" class="control-label"><g:message code="lead.clientId.label" default="Client Id"/><span
                class="required-indicator">*</span></label>

        <div class="controls">
            <input type="text" id="clientId" name="clientId" data-ng-model="lead.clientId" required/>
            <span class="label label-important"
                  data-ng-show="leadForm['clientId'].$invalid && leadForm.clientId.$dirty">${message(code: 'default.invalid.message')}</span>

            <span class="label label-important"
                  data-ng-show="errors.clientId">{{ errors.clientId}}</span>
            <label class="error valid"
                   data-ng-show="leadForm['clientId'].$valid ">${message(code: 'default.ok.message')}</label>
        </div>
    </div>

    <div class="control-group fieldcontain"
         data-ng-class="{error: leadForm.clientName.$invalid && leadForm.clientName.$dirty, success: leadForm.clientName.$valid}">
        <label for="clientName" class="control-label"><g:message code="lead.clientName.label"
                                                                 default="Client Name"/><span
                class="required-indicator">*</span></label>

        <div class="controls">
            <input type="text" id="clientName" name="clientName" data-ng-model="lead.clientName" required/>
            <span class="label label-important"
                  data-ng-show="leadForm['clientName'].$invalid && leadForm.clientName.$dirty">${message(code: 'default.invalid.message')}</span>
            <span class="label label-important"
                  data-ng-show="errors.clientName">{{ errors.clientName }}</span>

            <label class="error valid"
                   data-ng-show="leadForm['clientName'].$valid && leadForm.clientName.$dirty">${message(code: 'default.ok.message')}</label>
        </div>
    </div>
</fieldset>