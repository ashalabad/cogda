<div class="control-group fieldcontain">
    <label class="control-label" for="contactPhoneNumber.primaryPhoneNumber">  </label>
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