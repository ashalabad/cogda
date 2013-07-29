<fieldset>
    <div data-ng-form="leadLineOfBusinessForm">
        <div class="control-group fieldcontain">
            <label for="lineOfBusinessCategory" class="control-label">
                <g:message code="lineOfBusiness.category.label"/>
            </label>

            <div class="controls">
                <select id="lineOfBusinessCategory"
                        data-ng-model="leadLineOfBusiness.lineOfBusiness.lineOfBusinessCategory.id"
                        data-ng-options="lob.lineOfBusinessCategory.id as lob.lineOfBusinessCategory.description for lob in linesOfBusiness | unique:'lineOfBusinessCategory.id'">
                    <option value="">-- choose Category --</option>
                </select>
            </div>
        </div>

        <div class="control-group fieldcontain">
            <label for="lineOfBusiness" class="control-label">
                <g:message code="lineOfBusiness.label"/>
            </label>

            <div class="controls">
                <select id="lineOfBusiness" data-ng-model="leadLineOfBusiness.lineOfBusiness.id"
                        data-ng-options="lob.id as lob.description for lob in linesOfBusiness | filter: {lineOfBusinessCategory.id: leadLineOfBusiness.lineOfBusiness.lineOfBusinessCategory.id}">
                    <option value="">-- choose Line Of Business --</option>
                </select>
            </div>
        </div>

        <div class="control-group fieldcontain">
            <label for="targetDate" class="control-label">
                <g:message code="leadLineOfBusiness.targetDate.label" default="Target Date"/><span
                    class="required-indicator">*</span>
            </label>

            <div class="controls">
                <div class="input-append">
                    <input type="text" id="targetDate" class="input-medium"
                           data-ng-model="leadLineOfBusiness.targetDate"
                           data-date-format="mm/dd/yyyy" data-bs-datepicker>
                    <button type="button" class="btn" data-toggle="mydatepicker"><i class="icon-calendar"></i>
                    </button>
                </div>
            </div>
        </div>

        <div class="control-group fieldcontain">
            <label for="expirationDate" class="control-label">
                <g:message code="leadLineOfBusiness.expirationDate.label" default="Expiration Date"/><span
                    class="required-indicator">*</span>
            </label>

            <div class="controls">
                <div class="input-append">
                    <input type="text" id="expirationDate" class="input-medium"
                           data-ng-model="leadLineOfBusiness.expirationDate"
                           data-date-format="mm/dd/yyyy" data-bs-datepicker>
                    <button type="button" class="btn" data-toggle="mydatepicker"><i class="icon-calendar"></i>
                    </button>
                </div>
            </div>
        </div>

        <div class="control-group fieldcontain">
            <label for="targetPremium" class="control-label">
                <g:message code="leadLineOfBusiness.targetPremium.label" default="Target Premium"/><span
                    class="required-indicator">*</span>
            </label>

            <div class="controls">
                <input type="number" id="targetPremium" class="input-medium" min="0"
                       data-ng-model="leadLineOfBusiness.targetPremium">
            </div>
        </div>

        <div class="control-group fieldcontain">
            <label for="targetCommission" class="control-label">
                <g:message code="leadLineOfBusiness.targetCommission.label" default="Target Commission"/><span
                    class="required-indicator">*</span>
            </label>

            <div class="controls">
                <input type="number" id="targetCommission" class="input-medium" min="0"
                       data-ng-model="leadLineOfBusiness.targetCommission">
            </div>
        </div>

        <div class="control-group fieldcontain">
            <label for="writingCompany" class="control-label">
                <g:message code="leadLineOfBusiness.writingCompany.label" default="Writing Company"/><span
                    class="required-indicator">*</span>
            </label>

            <div class="controls">
                <input type="text" id="writingCompany" class="input-medium"
                       data-ng-model="leadLineOfBusiness.writingCompany">
            </div>
        </div>

        <div class="control-group fieldcontain">
            <label for="billingCompany" class="control-label">
                <g:message code="leadLineOfBusiness.billingCompany.label" default="Billing Company"/><span
                    class="required-indicator">*</span>
            </label>

            <div class="controls">
                <input type="text" id="billingCompany" class="input-medium"
                       data-ng-model="leadLineOfBusiness.billingCompany">
            </div>
        </div>

        <div class="control-group fieldcontain">
            <label for="renewal" class="control-label">
                <g:message code="leadLineOfBusiness.renewal.label" default="Renewal"/><span
                    class="required-indicator">*</span>
            </label>

            <div class="controls">
                <input type="checkbox" id="renewal" class="input-medium"
                       data-ng-model="leadLineOfBusiness.renewal">
            </div>
        </div>

        <div class="control-group fieldcontain">
            <label for="remarket" class="control-label">
                <g:message code="leadLineOfBusiness.remarket.label" default="Remarket"/><span
                    class="required-indicator">*</span>
            </label>

            <div class="controls">
                <input type="checkbox" id="remarket" class="input-medium"
                       data-ng-model="leadLineOfBusiness.remarket">
            </div>
        </div>
    </div>
</fieldset>