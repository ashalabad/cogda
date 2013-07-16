<address>
    <div class="row">
        <button class="btn btn-danger btn-mini pull-right"
                type="button"
                data-ng-click="deleteContactAddress(contactAddress)"
                data-ng-hide="editingContactAddress">
            <i class="icon-remove icon-white"></i>
            Delete Contact Address
        </button>
        <button class="btn btn-info btn-mini pull-right"
                type="button"
                data-ng-click="editContactAddress()"
                data-ng-hide="editContactAddress">
            <i class="icon-edit icon-white"></i>
            Edit Contact Address
        </button>

        <span data-ng-show="address.primaryAddress" class="span3">
            <br/>
            <span class="label label-info">Primary</span>
        </span>
    </div>
    <g:render template="/address/partials/showPartial"/>
</address>