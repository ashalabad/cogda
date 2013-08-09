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
    <span class="label label-important" data-ng-show="errors.expirationDate ">{{ errors.expirationDate }}</span>
    <span class="error" ng-show="leadLineOfBusinessForm['expirationDate'].$invalid"></span>
    <span class="success" ng-show="leadLineOfBusinessForm['expirationDate'].$valid"></span>
</td>
<td data-title="${message(code: 'leadLineOfBusiness.expirationDate.label')}">
    <div class="input-append">
        <input name="expirationDate" type="text" id="expirationDate"
               data-ng-model="leadLineOfBusiness.expirationDate"
               placeholder="<g:message code="leadLineOfBusiness.expirationDate.label"
                                       default="Expiration Date"/>"
               data-date-format="mm/dd/yyyy" data-start-date="new Date()"
               data-bs-datepicker style="width: 45%; height: 100%"
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
               data-bs-datepicker style="width: 45%; height: 100%" data-start-date="new Date()"
               data-end-date="{{leadLineOfBusiness.expirationDate | bsDateFilter | date:shortDate }}">
        <button type="button" class="btn" data-toggle="mydatepicker"><i class="icon-calendar"></i>
        </button>
    </div>
</td>
<td data-title="${message(code: 'leadLineOfBusiness.targetPremium.label')}">
    <input type="number" id="targetPremium" class="input-block-level" min="0"
           placeholder="<g:message code="leadLineOfBusiness.targetPremium.label" default="Target Premium"/>"
           data-ng-model="leadLineOfBusiness.targetPremium">
</td>
<td data-title="${message(code: 'leadLineOfBusiness.targetCommission.label')}">
    <input type="number" id="targetCommission" min="0" class="input-block-level"
           placeholder="<g:message code="leadLineOfBusiness.targetCommission.label"
                                   default="Target Commission"/>"
           data-ng-model="leadLineOfBusiness.targetCommission">
</td>
<td data-title="${message(code: 'leadLineOfBusiness.commissionRate.label')}">
    <input type="number" id="commissionRate" class="input-block-level"
           placeholder="<g:message code="leadLineOfBusiness.commissionRate.label"
                                   default="Commission Rate"/>"
           data-ng-model="leadLineOfBusiness.commissionRate">
</td>
<td data-title="${message(code: 'leadLineOfBusiness.currentCarrier.label')}">
    <input type="text" id="currentCarrier" class="input-block-level"
           placeholder="<g:message code="leadLineOfBusiness.currentCarrier.label"
                                   default="Current Carrier"/>"
           data-ng-model="leadLineOfBusiness.currentCarrier">
</td>
<td data-title="${message(code: 'leadLineOfBusiness.remarket.label')}">
    <input type="checkbox" id="remarket" class="input-medium"
           data-ng-model="leadLineOfBusiness.remarket">
</td>
<td>
    <button class="btn btn-danger btn-mini" data-ng-click="deleteLineOfBusiness($index)"><i class="icon-remove"></i>
    </button>
</td>