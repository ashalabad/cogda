<div class="control-group fieldcontain">
    <label class="control-label" for="leadContactPhoneNumber.primaryPhoneNumber">  </label>
    <div class="controls readonly">
        <span data-ng-show="leadContactPhoneNumber.primaryPhoneNumber" id="leadContactPhoneNumber.primaryPhoneNumber">
            <br/>
            <span class="label label-info">Primary</span>
        </span>
    </div>
</div>

<div class="control-group fieldcontain">
    <label class="control-label" for="leadContactPhoneNumber.description">
        <g:message code="leadContactPhoneNumber.description.label" default="Description"/>:
    </label>

    <div class="controls readonly">

        <span data-ng-bind="leadContactPhoneNumber.description" id="leadContactPhoneNumber.description"></span>
    </div>
</div>

<div class="control-group fieldcontain">
    <label class="control-label" for="leadContactPhoneNumber.phoneNumber">
        <g:message code="leadContactPhoneNumber.phoneNumber.label" default="Phone Number"/>:
    </label>

    <div class="controls readonly">
        <span data-ng-bind="leadContactPhoneNumber.phoneNumber" id="leadContactPhoneNumber.phoneNumber"></span>
    </div>
</div>