<div data-ng-controller="EditLeadContactEmailAddressController">
    <div class="form-horizontal">
        <div class="well" data-ng-hide="editingContactEmailAddress">
            <div data-ng-include src="'/leadContactEmailAddress/showPartial'"></div>
            %{--<g:render template="/lead/leadContactEmailAddress/partials/showPartial"/>--}%
        </div>

        <div class="well" data-ng-show="editingContactEmailAddress" data-ng-form="contactEmailAddressForm">
            <div data-ng-include src="'/leadContactEmailAddress/editPartial'"></div>
            %{--<g:render template="/lead/leadContactEmailAddress/partials/editPartial"/>--}%
            <div class="form-actions">
                <button type="submit"
                        class="btn btn-primary"
                        data-ng-click="updateContactEmailAddress(contactEmailAddress)">
                    <i class="icon-pencil icon-white"></i>
                    <g:message code="default.button.update.label"/> <g:message code="contactEmailAddress.label"/>
                </button>
                <button class="btn btn-danger"
                        type="button"
                        data-ng-click="deleteContactEmailAddress(contactEmailAddress, $index)">
                    <i class="icon-remove icon-white"></i>
                    <g:message code="default.button.delete.label"/> <g:message code="contactEmailAddress.label"/>
                </button>
                <button type="button"
                        class="btn"
                        data-ng-click="cancelEditContactEmailAddress()">
                    <i class="icon-ban-circle"></i>
                    <g:message code="default.button.cancel.label"/>
                </button>
            </div>
        </div>

    </div>
</div>


