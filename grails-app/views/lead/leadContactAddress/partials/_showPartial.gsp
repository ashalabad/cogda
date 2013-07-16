<address>
    <div class="row">
        <button class="btn btn-danger btn-mini pull-right"
                type="button"
                data-ng-click="deleteContactAddress(contactAddress)"
                data-ng-hide="editingContactAddress">
            <i class="icon-remove icon-white"></i>
            <g:message code="default.button.delete.label"/> <g:message code="contactAddress.label"/>
        </button>
        <button class="btn btn-info btn-mini pull-right"
                type="button"
                data-ng-click="editContactAddress()"
                data-ng-hide="editContactAddress">
            <i class="icon-edit icon-white"></i>
            <g:message code="default.edit.label" args="[message(code: 'contactAddress.label')]"/>
        </button>

        <span data-ng-show="address.primaryAddress" class="span3">
            <br/>
            <span class="label label-info"><g:message code="leadContactAddress.primaryAddress.label" default="Primary Address"/></span>
        </span>
    </div>
    <div data-ng-include="" src="'/leadContactAddress/showAddressPartial'"></div>
    %{--<g:render template="/address/partials/showPartial"/>--}%
</address>