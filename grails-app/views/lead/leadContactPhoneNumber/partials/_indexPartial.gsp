<div data-ng-controller="EditLeadContactPhoneNumberController">
    <div class="form-horizontal">
        <div class="well" data-ng-hide="editingContactPhoneNumber">
            <div data-ng-include="" src="'/leadContactPhoneNumber/showPartial'"></div>
            %{--<g:render template="/lead/leadContactPhoneNumber/partials/showPartial"/>--}%
        </div>

        <div class="well" data-ng-show="editingContactPhoneNumber" data-ng-form="contactPhoneNumberForm">
            <div data-ng-include="" src="'/leadContactPhoneNumber/editPartial'"></div>
            %{--<g:render template="/lead/leadContactPhoneNumber/partials/editPartial"/>--}%
            <div class="form-actions">
                <button type="submit"
                        class="btn btn-primary"
                        data-ng-click="updateContactPhoneNumber(contactPhoneNumber)">
                    <i class="icon-pencil icon-white"></i>
                    <g:message code="default.button.update.label"/> <g:message code="contactPhoneNumber.label"/>
                </button>
                <button class="btn btn-danger"
                        type="button"
                        data-ng-click="deleteContactPhoneNumber(contactPhoneNumber)">
                    <i class="icon-remove icon-white"></i>
                    <g:message code="default.button.delete.label"/> <g:message code="contactPhoneNumber.label"/>
                </button>
                <button type="button"
                        class="btn"
                        data-ng-click="cancelEditContactPhoneNumber()">
                    <i class="icon-ban-circle"></i>
                    <g:message code="default.button.cancel.label"/></button>
            </div>
        </div>
    </div>
</div>


