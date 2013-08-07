%{--<div data-ng-form="leadLineOfBusinessForm">--}%
    %{--<td data-title="${message(code: 'lineOfBusiness.category.label')}" data-ng-show="editingLineOfBusiness">--}%
        %{--<select id="lineOfBusinessCategory"--}%
                %{--data-ng-model="leadLineOfBusiness.lineOfBusiness.lineOfBusinessCategory.id"--}%
                %{--data-ng-options="lob.lineOfBusinessCategory.id as lob.lineOfBusinessCategory.description for lob in linesOfBusiness | unique:'lineOfBusinessCategory.id'"--}%
                %{--style="padding: 3px; width: 100%;">--}%
            %{--<option value="">-- choose Category --</option>--}%
        %{--</select>--}%
    %{--</td>--}%
    %{--<td data-title="${message(code: 'lineOfBusiness.label')}" data-ng-show="editingLineOfBusiness">--}%
        %{--<select id="lineOfBusiness" data-ng-model="leadLineOfBusiness.lineOfBusiness.id"--}%
                %{--data-ng-options="lob.id as lob.description for lob in linesOfBusiness | filter: {lineOfBusinessCategory.id: leadLineOfBusiness.lineOfBusiness.lineOfBusinessCategory.id}"--}%
                %{--style="padding: 3px; width: 100%;">--}%
            %{--<option value="">-- choose Line Of Business --</option>--}%
        %{--</select>--}%
    %{--</td>--}%
    %{--<td data-title="${message(code: 'lineOfBusiness.category.label')}" data-ng-show="editingLineOfBusiness">--}%
        %{--<div class="input-append" style="padding: 3px; width: 100%;">--}%
            %{--<input type="text" id="targetDate" class="input-small"--}%
                   %{--data-ng-model="leadLineOfBusiness.targetDate"--}%
                   %{--data-date-format="mm/dd/yyyy"--}%
                   %{--placeholder="<g:message code="leadLineOfBusiness.targetDate.label"/>"--}%
                   %{--data-bs-datepicker>--}%
            %{--<button type="button" class="btn" data-toggle="mydatepicker"><i class="icon-calendar"></i>--}%
            %{--</button>--}%
        %{--</div>--}%
    %{--</td>--}%
    %{--<td data-title="${message(code: 'leadLineOfBusiness.expirationDate.label')}" data-ng-show="editingLineOfBusiness">--}%
        %{--<div class="input-append" style="padding: 3px; width: 100%;">--}%
            %{--<input type="text" id="expirationDate" class="input-small"--}%
                   %{--data-ng-model="leadLineOfBusiness.expirationDate"--}%
                   %{--placeholder="<g:message code="leadLineOfBusiness.expirationDate.label"--}%
                                           %{--default="Expiration Date"/>"--}%
                   %{--data-date-format="mm/dd/yyyy" data-bs-datepicker>--}%
            %{--<button type="button" class="btn" data-toggle="mydatepicker"><i class="icon-calendar"></i>--}%
            %{--</button>--}%
        %{--</div>--}%
    %{--</td>--}%
    %{--<td data-title="${message(code: 'leadLineOfBusiness.targetPremium.label')}" data-ng-show="editingLineOfBusiness">--}%
        %{--<div style="padding: 3px; width: 100%;">--}%
            %{--<input type="number" id="targetPremium" class="input-medium" min="0"--}%
                   %{--placeholder="<g:message code="leadLineOfBusiness.targetPremium.label" default="Target Premium"/>"--}%
                   %{--data-ng-model="leadLineOfBusiness.targetPremium">--}%
        %{--</div>--}%
    %{--</td>--}%
    %{--<td data-title="${message(code: 'leadLineOfBusiness.targetCommission.label')}" data-ng-show="editingLineOfBusiness">--}%
        %{--<div style="padding: 3px; width: 100%;">--}%
            %{--<input type="number" id="targetCommission" class="input-medium" min="0"--}%
                   %{--placeholder="<g:message code="leadLineOfBusiness.targetCommission.label"--}%
                                           %{--default="Target Commission"/>"--}%
                   %{--data-ng-model="leadLineOfBusiness.targetCommission">--}%
        %{--</div>--}%
    %{--</td>--}%
    %{--<td data-title="${message(code: 'leadLineOfBusiness.commissionRate.label')}" data-ng-show="editingLineOfBusiness">--}%
        %{--<div style="padding: 3px; width:100px;">--}%
            %{--<input type="number" id="commissionRate" class="input-medium" min="0"--}%
                   %{--placeholder="<g:message code="leadLineOfBusiness.commissionRate.label"--}%
                                           %{--default="Commission Rate"/>"--}%
                   %{--data-ng-model="leadLineOfBusiness.commissionRate">--}%
        %{--</div>--}%
    %{--</td>--}%
    %{--<td data-title="${message(code: 'leadLineOfBusiness.currentCarrier.label')}" data-ng-show="editingLineOfBusiness">--}%
        %{--<div style='padding: 3px; width: 100px;'>--}%
            %{--<input type="text" id="currentCarrier" class="input-medium"--}%
                   %{--placeholder="<g:message code="leadLineOfBusiness.currentCarrier.label"--}%
                                           %{--default="Current Carrier"/>"--}%
                   %{--data-ng-model="leadLineOfBusiness.currentCarrier">--}%
        %{--</div>--}%
    %{--</td>--}%
    %{--<td data-title="${message(code: 'leadLineOfBusiness.remarket.label')}" data-ng-show="editingLineOfBusiness">--}%
        %{--<input type="checkbox" id="remarket" class="input-medium"--}%
               %{--style="width: 100%;padding: 3px;"--}%
               %{--data-ng-model="leadLineOfBusiness.remarket">--}%
    %{--</td>--}%
%{--</div>--}%

<div data-ng-form="leadLineOfBusinessForm">
    <td data-title="${message(code: 'lineOfBusiness.category.label')}" data-ng-show="editingLineOfBusiness">
        <select id="lineOfBusinessCategory"
                data-ng-model="leadLineOfBusiness.lineOfBusiness.lineOfBusinessCategory.id"
                data-ng-options="lob.lineOfBusinessCategory.id as lob.lineOfBusinessCategory.description for lob in linesOfBusiness | unique:'lineOfBusinessCategory.id'"
                >
            <option value="">-- choose Category --</option>
        </select>
    </td>
    <td data-title="${message(code: 'lineOfBusiness.label')}" data-ng-show="editingLineOfBusiness">
        <select id="lineOfBusiness" data-ng-model="leadLineOfBusiness.lineOfBusiness.id"
                data-ng-options="lob.id as lob.description for lob in linesOfBusiness | filter: {lineOfBusinessCategory.id: leadLineOfBusiness.lineOfBusiness.lineOfBusinessCategory.id}"
                >
            <option value="">-- choose Line Of Business --</option>
        </select>
    </td>
    <td data-title="${message(code: 'lineOfBusiness.category.label')}" data-ng-show="editingLineOfBusiness">
        <div class="input-append" >
            <input type="text" id="targetDate" class="input-small"
                   data-ng-model="leadLineOfBusiness.targetDate"
                   data-date-format="mm/dd/yyyy"
                   placeholder="<g:message code="leadLineOfBusiness.targetDate.label"/>"
                   data-bs-datepicker>
            <button type="button" class="btn" data-toggle="mydatepicker"><i class="icon-calendar"></i>
            </button>
        </div>
    </td>
    <td data-title="${message(code: 'leadLineOfBusiness.expirationDate.label')}" data-ng-show="editingLineOfBusiness">
        <div class="input-append" >
            <input type="text" id="expirationDate" class="input-small"
                   data-ng-model="leadLineOfBusiness.expirationDate"
                   placeholder="<g:message code="leadLineOfBusiness.expirationDate.label"
                                           default="Expiration Date"/>"
                   data-date-format="mm/dd/yyyy" data-bs-datepicker>
            <button type="button" class="btn" data-toggle="mydatepicker"><i class="icon-calendar"></i>
            </button>
        </div>
    </td>
    <td data-title="${message(code: 'leadLineOfBusiness.targetPremium.label')}" data-ng-show="editingLineOfBusiness">
        <div >
            <input type="number" id="targetPremium" class="input-medium" min="0"
                   placeholder="<g:message code="leadLineOfBusiness.targetPremium.label" default="Target Premium"/>"
                   data-ng-model="leadLineOfBusiness.targetPremium">
        </div>
    </td>
    <td data-title="${message(code: 'leadLineOfBusiness.targetCommission.label')}" data-ng-show="editingLineOfBusiness">
        <div >
            <input type="number" id="targetCommission" class="input-medium" min="0"
                   placeholder="<g:message code="leadLineOfBusiness.targetCommission.label"
                                           default="Target Commission"/>"
                   data-ng-model="leadLineOfBusiness.targetCommission">
        </div>
    </td>
    <td data-title="${message(code: 'leadLineOfBusiness.commissionRate.label')}" data-ng-show="editingLineOfBusiness">
            <input type="number" id="commissionRate" class="input-medium" min="0"
                   placeholder="<g:message code="leadLineOfBusiness.commissionRate.label"
                                           default="Commission Rate"/>"
                   data-ng-model="leadLineOfBusiness.commissionRate">
    </td>
    <td data-title="${message(code: 'leadLineOfBusiness.currentCarrier.label')}" data-ng-show="editingLineOfBusiness">
            <input type="text" id="currentCarrier" class="input-medium"
                   placeholder="<g:message code="leadLineOfBusiness.currentCarrier.label"
                                           default="Current Carrier"/>"
                   data-ng-model="leadLineOfBusiness.currentCarrier">
    </td>
    <td data-title="${message(code: 'leadLineOfBusiness.remarket.label')}" data-ng-show="editingLineOfBusiness">
        <input type="checkbox" id="remarket" class="input-medium"
               data-ng-model="leadLineOfBusiness.remarket">
    </td>
</div>
