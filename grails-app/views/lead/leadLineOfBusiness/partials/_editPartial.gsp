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
            <label for="expirationDate" class="control-label">
                <g:message code="leadLineOfBusiness.expirationDate.label" default="Expiration Date"/><span
                    class="required-indicator">*</span>
            </label>

            <div class="controls">
                <div class="input-append">
                    <input type="text" id="expirationDate" class="input-small"
                           data-ng-model="leadLineOfBusiness.expirationDate"
                           placeholder="<g:message code="leadLineOfBusiness.expirationDate.label"
                                                   default="Expiration Date"/>"
                           data-date-format="mm/dd/yyyy" data-start-date="new Date()" data-bs-datepicker>
                    <button type="button" class="btn" data-toggle="mydatepicker"><i class="icon-calendar"></i>
                    </button>
                </div>
            </div>
        </div>

        <div class="control-group fieldcontain">
            <label for="targetDate" class="control-label">
                <g:message code="leadLineOfBusiness.targetDate.label" default="Target Date"/><span
                    class="required-indicator">*</span>
            </label>

            <div class="controls">
                <div class="input-append">
                    <input type="text" id="targetDate" class="input-small"
                           data-ng-model="leadLineOfBusiness.targetDate"
                           data-date-format="mm/dd/yyyy" data-start-date="new Date()" data-end-date="{{leadLineOfBusiness.expirationDate | bsDateFilter | date:shortDate }}"
                           placeholder="<g:message code="leadLineOfBusiness.targetDate.label"/>"
                           data-bs-datepicker>
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
                       placeholder="<g:message code="leadLineOfBusiness.targetPremium.label" default="Target Premium"/>"
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
                       placeholder="<g:message code="leadLineOfBusiness.targetCommission.label"
                                               default="Target Commission"/>"
                       data-ng-model="leadLineOfBusiness.targetCommission">
            </div>
        </div>

        <div class="control-group fieldcontain">
            <label for="commissionRate" class="control-label">
                <g:message code="leadLineOfBusiness.commissionRate.label" default="Commission Rate"/><span
                    class="required-indicator">*</span>
            </label>

            <div class="controls">
                <input type="number" id="commissionRate" class="input-medium" min="0"
                       placeholder="<g:message code="leadLineOfBusiness.commissionRate.label"
                                               default="Commission Rate"/>"
                       data-ng-model="leadLineOfBusiness.commissionRate">
            </div>
        </div>

        <div class="control-group fieldcontain">
            <label for="currentCarrier" class="control-label">
                <g:message code="leadLineOfBusiness.currentCarrier.label" default="Current Carrier"/><span
                    class="required-indicator">*</span>
            </label>

            <div class="controls">
                <input type="text" id="currentCarrier" class="input-medium"
                       placeholder="<g:message code="leadLineOfBusiness.currentCarrier.label"
                                               default="Current Carrier"/>"
                       data-ng-model="leadLineOfBusiness.currentCarrier">
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
        <pre class="container">
            leadLineOfBusinessForm: {{leadLineOfBusinessForm.$error|json}}
        </pre>
    </div>
</fieldset>