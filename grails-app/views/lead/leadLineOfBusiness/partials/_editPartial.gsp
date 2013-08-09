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

        <div class="control-group fieldcontain"
             data-ng-class="{error: leadLineOfBusinessForm['lineOfBusiness'].$invalid}">
            <label for="lineOfBusiness" class="control-label">
                <g:message code="lineOfBusiness.label"/>
            </label>

            <div class="controls">
                <select id="lineOfBusiness" data-ng-model="leadLineOfBusiness.lineOfBusiness.id"
                        data-ng-options="lob.id as lob.description for lob in linesOfBusiness | filter: {lineOfBusinessCategory.id: leadLineOfBusiness.lineOfBusiness.lineOfBusinessCategory.id}">
                    <option value="">-- choose Line Of Business --</option>
                </select>
                <span class="label label-important"
                      data-ng-show="errors.lineOfBusiness ">{{ errors.lineOfBusiness }}</span>
                <span class="error" ng-show="leadLineOfBusinessForm['lineOfBusiness'].$invalid"></span>
                <span class="success" ng-show="leadLineOfBusinessForm['lineOfBusiness'].$valid"></span>
            </div>
        </div>

        <div class="control-group fieldcontain"
             data-ng-class="{error: leadLineOfBusinessForm['expirationDate'].$invalid}">
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
                           data-date-format="mm/dd/yyyy" data-start-date="new Date()" data-bs-datepicker
                           required>
                    <button type="button" class="btn" data-toggle="mydatepicker"><i class="icon-calendar"></i>
                    </button>
                </div>
                <span class="label label-important"
                      data-ng-show="errors.expirationDate ">{{ errors.expirationDate }}</span>
                <span class="error" data-ng-show="leadLineOfBusinessForm['expirationDate'].$invalid"></span>
                <span class="success" data-ng-show="leadLineOfBusinessForm['expirationDate'].$valid"></span>
            </div>
        </div>

        <div class="control-group fieldcontain" data-ng-class="{error: leadLineOfBusinessForm['targetDate'].$invalid}">
            <label for="targetDate" class="control-label">
                <g:message code="leadLineOfBusiness.targetDate.label" default="Target Date"/><span
                    class="required-indicator">*</span>
            </label>

            <div class="controls">
                <div class="input-append">
                    <input type="text" id="targetDate" class="input-small"
                           data-ng-model="leadLineOfBusiness.targetDate"
                           data-date-format="mm/dd/yyyy" data-start-date="new Date()"
                           data-end-date="{{leadLineOfBusiness.expirationDate | bsDateFilter | date:shortDate }}"
                           placeholder="<g:message code="leadLineOfBusiness.targetDate.label"/>"
                           data-bs-datepicker>
                    <button type="button" class="btn" data-toggle="mydatepicker"><i class="icon-calendar"></i>
                    </button>
                </div>
                <span class="label label-important" data-ng-show="errors.targetDate ">{{ errors.targetDate }}</span>
                <span class="error" data-ng-show="leadLineOfBusinessForm['targetDate'].$invalid"></span>
                <span class="success" data-ng-show="leadLineOfBusinessForm['targetDate'].$valid"></span>
            </div>
        </div>

        <div class="control-group fieldcontain"
             data-ng-class="{error: leadLineOfBusinessForm['targetPremium'].$invalid}">
            <label for="targetPremium" class="control-label">
                <g:message code="leadLineOfBusiness.targetPremium.label" default="Target Premium"/><span
                    class="required-indicator">*</span>
            </label>

            <div class="controls">
                <div class="input-prepend">
                    <span class="add-on">$</span>
                    <input type="number" id="targetPremium" class="input-medium" min="0"
                           data-ng-pattern="/^\d+\.?\d{0,2}$/" data-ng-change="targetPremiumChangeHandler()"
                           placeholder="<g:message code="leadLineOfBusiness.targetPremium.label"
                                                   default="Target Premium"/>"
                           data-ng-model="leadLineOfBusiness.targetPremium">
                </div>
                <span class="label label-important"
                      data-ng-show="errors.targetPremium ">{{ errors.targetPremium }}</span>
                <span class="error" data-ng-show="leadLineOfBusinessForm['targetPremium'].$invalid"></span>
                <span class="success" data-ng-show="leadLineOfBusinessForm['targetPremium'].$valid"></span>
            </div>
        </div>

        <div class="control-group fieldcontain"
             data-ng-class="{error: leadLineOfBusinessForm['targetCommission'].$invalid}">
            <label for="targetCommission" class="control-label">
                <g:message code="leadLineOfBusiness.targetCommission.label" default="Target Commission"/><span
                    class="required-indicator">*</span>
            </label>

            <div class="controls">
                <div class="input-prepend">
                    <span class="add-on">$</span>
                    <input type="number" id="targetCommission" class="input-medium" min="0"
                           max="{{leadLineOfBusiness.targetPremium}}"
                           data-ng-pattern="/^\d+\.?\d{0,2}$/" data-ng-change="targetCommissionChangeHandler()"
                           placeholder="<g:message code="leadLineOfBusiness.targetCommission.label"
                                                   default="Target Commission"/>"
                           data-ng-model="leadLineOfBusiness.targetCommission">
                </div>
                <span class="label label-important"
                      data-ng-show="errors.targetCommission ">{{ errors.targetCommission }}</span>
                <span class="error" data-ng-show="leadLineOfBusinessForm['targetCommission'].$invalid"></span>
                <span class="success" data-ng-show="leadLineOfBusinessForm['targetCommission'].$valid"></span>
            </div>
        </div>

        <div class="control-group fieldcontain"
             data-ng-class="{error: leadLineOfBusinessForm['commissionRate'].$invalid}">
            <label for="commissionRate" class="control-label">
                <g:message code="leadLineOfBusiness.commissionRate.label" default="Commission Rate"/><span
                    class="required-indicator">*</span>
            </label>

            <div class="controls">
                <div class="input-prepend">
                    <span class="add-on">%</span>
                    <input type="number" id="commissionRate" class="input-medium" min="0" max="100"
                           data-ng-pattern="/^\d+\.?\d{0,2}$/" data-ng-change="commissionRateChangeHandler()"
                           placeholder="<g:message code="leadLineOfBusiness.commissionRate.label"
                                                   default="Commission Rate"/>"
                           data-ng-model="leadLineOfBusiness.commissionRate">
                </div>
                <span class="label label-important"
                      data-ng-show="errors.commissionRate ">{{ errors.commissionRate }}</span>
                <span class="error" data-ng-show="leadLineOfBusinessForm['commissionRate'].$invalid"></span>
                <span class="success" data-ng-show="leadLineOfBusinessForm['commissionRate'].$valid"></span>
            </div>
        </div>

        <div class="control-group fieldcontain"
             data-ng-class="{error: leadLineOfBusinessForm['currentCarrier'].$invalid}">
            <label for="currentCarrier" class="control-label">
                <g:message code="leadLineOfBusiness.currentCarrier.label" default="Current Carrier"/><span
                    class="required-indicator">*</span>
            </label>

            <div class="controls">
                <input type="text" id="currentCarrier" class="input-medium"
                       placeholder="<g:message code="leadLineOfBusiness.currentCarrier.label"
                                               default="Current Carrier"/>"
                       data-ng-model="leadLineOfBusiness.currentCarrier">
                <span class="label label-important"
                      data-ng-show="errors.currentCarrier ">{{ errors.currentCarrier }}</span>
                <span class="error" data-ng-show="leadLineOfBusinessForm['currentCarrier'].$invalid"></span>
                <span class="success" data-ng-show="leadLineOfBusinessForm['currentCarrier'].$valid"></span>
            </div>
        </div>

        <div class="control-group fieldcontain" data-ng-class="{error: leadLineOfBusinessForm['remarket'].$invalid}">
            <label for="remarket" class="control-label">
                <g:message code="leadLineOfBusiness.remarket.label" default="Remarket"/><span
                    class="required-indicator">*</span>
            </label>

            <div class="controls">
                <input type="checkbox" id="remarket" class="input-medium"
                       data-ng-model="leadLineOfBusiness.remarket">
                <span class="label label-important" data-ng-show="errors.remarket ">{{ errors.remarket }}</span>
                <span class="error" data-ng-show="leadLineOfBusinessForm['remarket'].$invalid"></span>
                <span class="success" data-ng-show="leadLineOfBusinessForm['remarket'].$valid"></span>
            </div>
        </div>
    </div>
</fieldset>