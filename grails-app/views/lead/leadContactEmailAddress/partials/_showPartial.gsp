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