<address>
    <div class="row">
        <button class="btn btn-danger btn-mini pull-right"
                type="button"
                data-ng-click="deleteAddress(address)"
                data-ng-hide="editingAddress">
            <i class="icon-remove icon-white"></i>
            Delete Address
        </button>
        <button class="btn btn-info btn-mini pull-right"
                type="button"
                data-ng-click="editAddress()"
                data-ng-hide="editingAddress">
            <i class="icon-edit icon-white"></i>
            Edit Address
        </button>

        <span data-ng-show="address.primaryAddress" class="span3">
            <span class="label label-info">Primary</span>
        </span>
    </div>
    <g:render template="/address/partials/showPartial"/>
</address>

