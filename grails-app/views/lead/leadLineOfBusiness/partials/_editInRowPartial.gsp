<td data-title="${message(code: 'lineOfBusiness.category.label')}">
    <select id="lineOfBusinessCategory"
            data-ng-model="leadLineOfBusiness.lineOfBusiness.lineOfBusinessCategory.id"
            data-ng-options="lob.lineOfBusinessCategory.id as lob.lineOfBusinessCategory.description for lob in linesOfBusiness | unique:'lineOfBusinessCategory.id'"
            style="width: 100%;">
        <option value="">-- choose Category --</option>
    </select>
</td>
<td data-title="${message(code: 'lineOfBusiness.label')}">
    <select id="lineOfBusiness" name="lineOfBusiness"
            data-ng-model="leadLineOfBusiness.lineOfBusiness.id"
            data-ng-options="lob.id as lob.description for lob in linesOfBusiness | filter: {lineOfBusinessCategory.id: leadLineOfBusiness.lineOfBusiness.lineOfBusinessCategory.id}"
            style="width: 100%;"
            required>
        <option value="">-- choose Line Of Business --</option>
    </select>
    <span class="label label-important" data-ng-show="errors.lineOfBusiness ">{{ errors.lineOfBusiness }}</span>
    <span class="error" ng-show="leadLineOfBusinessForm['lineOfBusiness'].$invalid"></span>
    <span class="success" ng-show="leadLineOfBusinessForm['lineOfBusiness'].$valid"></span>
</td>
<td data-title="${message(code: 'leadLineOfBusiness.expirationDate.label')}">
    <div class="input-append">
        <input name="expirationDate" type="text" id="expirationDate"
               data-ng-model="leadLineOfBusiness.expirationDate"
               placeholder="<g:message code="leadLineOfBusiness.expirationDate.label"
                                       default="Expiration Date"/>"
               data-date-format="mm/dd/yyyy" data-start-date="new Date()"
               data-bs-datepicker class="input-block-level"
               required>
        <button type="button" class="btn" data-toggle="mydatepicker"><i class="icon-calendar"></i>
        </button>
    </div>
    <span class="label label-important" data-ng-show="errors.expirationDate ">{{ errors.expirationDate }}</span>
    <span class="error" data-ng-show="leadLineOfBusinessForm['expirationDate'].$invalid"></span>
    <span class="success" data-ng-show="leadLineOfBusinessForm['expirationDate'].$valid"></span>
</td>
<td data-title="${message(code: 'leadLineOfBusiness.targetDate.label')}">
    <div class="input-append">
        <input type="text" id="targetDate"
               data-ng-model="leadLineOfBusiness.targetDate"
               data-date-format="mm/dd/yyyy"
               placeholder="<g:message code="leadLineOfBusiness.targetDate.label"/>"
               data-bs-datepicker data-start-date="new Date()" class="input-block-level"
               data-end-date="{{leadLineOfBusiness.expirationDate | bsDateFilter | date:shortDate }}">
        <button type="button" class="btn" data-toggle="mydatepicker"><i class="icon-calendar"></i>
        </button>
    </div>
    <span class="label label-important" data-ng-show="errors.targetDate ">{{ errors.targetDate }}</span>
    <span class="error" data-ng-show="leadLineOfBusinessForm['targetDate'].$invalid"></span>
    <span class="success" data-ng-show="leadLineOfBusinessForm['targetDate'].$valid"></span>
</td>
<td data-title="${message(code: 'leadLineOfBusiness.targetPremium.label')}">
    <div class="input-prepend">
        <span class="add-on">$</span>
        <input type="number" id="targetPremium" min="0" class="input-block-level"
               data-ng-pattern="/^\d+\.?\d{0,2}$/" data-ng-change="targetPremiumChangeHandler()"
               placeholder="<g:message code="leadLineOfBusiness.targetPremium.label"
                                       default="Target Premium"/>"
               data-ng-model="leadLineOfBusiness.targetPremium">
    </div>
    <span class="label label-important" data-ng-show="errors.targetPremium ">{{ errors.targetPremium }}</span>
    <span class="error" data-ng-show="leadLineOfBusinessForm['targetPremium'].$invalid"></span>
    <span class="success" data-ng-show="leadLineOfBusinessForm['targetPremium'].$valid"></span>
</td>
<td data-title="${message(code: 'leadLineOfBusiness.targetCommission.label')}">
    <div class="input-prepend">
        <span class="add-on">$</span>
        <input type="number" id="targetCommission" min="0" max="{{leadLineOfBusiness.targetPremium}}" class="input-block-level"
               data-ng-pattern="/^\d+\.?\d{0,2}$/" data-ng-change="targetCommissionChangeHandler()"
               placeholder="<g:message code="leadLineOfBusiness.targetCommission.label"
                                       default="Target Commission"/>"
               data-ng-model="leadLineOfBusiness.targetCommission">
    </div>
    <span class="label label-important" data-ng-show="errors.targetCommission ">{{ errors.targetCommission }}</span>
    <span class="error" data-ng-show="leadLineOfBusinessForm['targetCommission'].$invalid"></span>
    <span class="success" data-ng-show="leadLineOfBusinessForm['targetCommission'].$valid"></span>
</td>
<td data-title="${message(code: 'leadLineOfBusiness.commissionRate.label')}">
    <div class="input-prepend">
        <span class="add-on">%</span>
        <input type="number" min="0" max="100" id="commissionRate" class="input-block-level"
               data-ng-pattern="/^\d+\.?\d{0,2}$/" data-ng-change="commissionRateChangeHandler()"
               placeholder="<g:message code="leadLineOfBusiness.commissionRate.label"
                                       default="Commission Rate"/>"
               data-ng-model="leadLineOfBusiness.commissionRate">
    </div>
    <span class="label label-important" data-ng-show="errors.commissionRate ">{{ errors.commissionRate }}</span>
    <span class="error" data-ng-show="leadLineOfBusinessForm['commissionRate'].$invalid"></span>
    <span class="success" data-ng-show="leadLineOfBusinessForm['commissionRate'].$valid"></span>
</td>
<td data-title="${message(code: 'leadLineOfBusiness.currentCarrier.label')}">
    <div>
        <input type="text" id="currentCarrier" class="input-block-level"
               placeholder="<g:message code="leadLineOfBusiness.currentCarrier.label"
                                       default="Current Carrier"/>"
               data-ng-model="leadLineOfBusiness.currentCarrier">
    </div>
    <span class="label label-important" data-ng-show="errors.currentCarrier ">{{ errors.currentCarrier }}</span>
    <span class="error" data-ng-show="leadLineOfBusinessForm['currentCarrier'].$invalid"></span>
    <span class="success" data-ng-show="leadLineOfBusinessForm['currentCarrier'].$valid"></span>
</td>
<td data-title="${message(code: 'leadLineOfBusiness.remarket.label')}">
    <div>
        <input type="checkbox" id="remarket"
               data-ng-model="leadLineOfBusiness.remarket">
    </div>
    <span class="label label-important" data-ng-show="errors.remarket ">{{ errors.remarket }}</span>
    <span class="error" data-ng-show="leadLineOfBusinessForm['remarket'].$invalid"></span>
    <span class="success" data-ng-show="leadLineOfBusinessForm['remarket'].$valid"></span>
</td>
<td>
    <button class="btn btn-danger btn-mini" data-ng-click="deleteLineOfBusiness($index)" data-ng-disabled="cannotDeleteLineOfBusiness()"><i class="icon-remove"></i>
    </button>
</td>