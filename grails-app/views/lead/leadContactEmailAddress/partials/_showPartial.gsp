<div class="control-group fieldcontain">
    <label class="control-label" for="leadContactEmailAddress.primaryEmailAddress"></label>

    <div class="controls readonly">
        <span data-ng-show="leadContactEmailAddress.primaryEmailAddress"
              id="leadContactEmailAddress.primaryEmailAddress">
            <br/>
            <span class="label label-info">Primary</span>
        </span>
    </div>
</div>

<div class="control-group fieldcontain">
    <label class="control-label" for="leadContactEmailAddress.emailAddress">
        <g:message code="leadContactEmailAddress.emailAddress.label" default="Email Address"/>:
    </label>

    <div class="controls readonly">

        <span data-ng-bind="leadContactEmailAddress.emailAddress" id="leadContactEmailAddress.emailAddress"></span>
    </div>
</div>