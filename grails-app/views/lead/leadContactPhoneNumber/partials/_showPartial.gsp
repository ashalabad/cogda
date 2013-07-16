<button class="btn btn-info btn-mini pull-right"
        type="button"
        data-ng-click="editContactPhoneNumber()"
        data-ng-hide="editContactPhoneNumber">
    <i class="icon-edit icon-white"></i>
    Edit Contact Phone Number
</button>
<button class="btn btn-danger btn-mini pull-right"
        type="button"
        data-ng-click="deleteContactPhoneNumber(contactPhoneNumber)"
        data-ng-hide="editingContactPhoneNumber">
    <i class="icon-remove icon-white"></i>
    Delete Contact Phone Number
</button>

<div class="control-group fieldcontain">
    <label class="control-label" for="contactPhoneNumber.primaryPhoneNumber"></label>

    <div class="controls readonly">
        <span data-ng-show="contactPhoneNumber.primaryPhoneNumber" id="contactPhoneNumber.primaryPhoneNumber">
            <br/>
            <span class="label label-info">Primary</span>
        </span>
    </div>
</div>

<div class="control-group fieldcontain">
    <label class="control-label" for="contactPhoneNumber.description">
        <g:message code="leadContactPhoneNumber.description.label" default="Description"/>:
    </label>

    <div class="controls readonly">

        <span data-ng-bind="contactPhoneNumber.description" id="contactPhoneNumber.description"></span>
    </div>
</div>

<div class="control-group fieldcontain">
    <label class="control-label" for="contactPhoneNumber.phoneNumber">
        <g:message code="leadContactPhoneNumber.phoneNumber.label" default="Phone Number"/>:
    </label>

    <div class="controls readonly">
        <span data-ng-bind="contactPhoneNumber.phoneNumber" id="contactPhoneNumber.phoneNumber"></span>
    </div>
</div>