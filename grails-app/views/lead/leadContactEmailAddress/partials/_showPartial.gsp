<button class="btn btn-danger btn-mini pull-right"
        type="button"
        data-ng-click="deleteContactEmailAddress(contactEmailAddress)"
        data-ng-hide="editingContactEmailAddress">
    <i class="icon-remove icon-white"></i>
    <g:message code="default.button.delete.label"/> <g:message code="contactEmailAddress.label"/>
</button>
<button class="btn btn-info btn-mini pull-right"
        type="button"
        data-ng-click="editContactEmailAddress()"
        data-ng-hide="editingContactEmailAddress">
    <i class="icon-edit icon-white"></i>
    <g:message code="default.edit.label" args="[message(code: 'contactEmailAddress.label')]"/>
</button>

<div class="control-group fieldcontain">
    <label class="control-label" for="contactEmailAddress.primaryEmailAddress"></label>

    <div class="controls readonly">
        <span data-ng-show="contactEmailAddress.primaryEmailAddress"
              id="contactEmailAddress.primaryEmailAddress">
            <br/>
            <span class="label label-info">Primary</span>
        </span>
    </div>
</div>

<div class="control-group fieldcontain">
    <label class="control-label" for="contactEmailAddress.emailAddress">
        <g:message code="leadContactEmailAddress.emailAddress.label" default="Email Address"/>:
    </label>

    <div class="controls readonly">

        <span data-ng-bind="contactEmailAddress.emailAddress" id="contactEmailAddress.emailAddress"></span>
    </div>
</div>