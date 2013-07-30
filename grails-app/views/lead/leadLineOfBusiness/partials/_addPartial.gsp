<div class="well" data-ng-show="addingLeadLineOfBusiness">
    <fieldset class="embedded">
        <legend>
            <g:message code="default.add.label" args="[message(code: 'leadLineOfBusiness.label')]"/>
        </legend>
        <g:render template="/lead/leadLineOfBusiness/partials/editPartial"/>
    </fieldset>

    <div class="form-actions">
        <button type="submit"
                class="btn btn-primary"
                data-ng-click="saveLeadLineOfBusiness(leadLineOfBusiness)">
            <i class="icon-plus icon-white"></i>
            <g:message code="default.add.label" args="[message(code: 'leadLineOfBusiness.label')]"/>
        </button>
        <button type="button"
                class="btn"
                data-ng-click="cancelAddLeadLineOfBusiness()">
            <i class="icon-ban-circle"></i>
            <g:message code="default.button.cancel.label"/></button>
    </div>
</div>
<button type="button"
        class="btn"
        data-ng-click="addLeadLineOfBusiness()"
        data-ng-hide="addingLeadLineOfBusiness">
    <i class="icon-plus"></i>
    <g:message code="default.add.label" args="[message(code: 'leadLineOfBusiness.label')]"/>
</button>
