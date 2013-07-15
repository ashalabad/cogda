<div class="control-group fieldcontain">
    <label class="control-label" for="contact.primaryContact">  </label>
    <div class="controls readonly">
    <span data-ng-show="contact.primaryContact" id="contact.primaryContact">
        <br/>
        <span class="label label-info">Primary</span>
    </span>
    </div>
</div>

<div class="control-group fieldcontain">
    <label class="control-label" for="contact.firstName">
        <g:message code="leadContact.firstName" default="First Name"/>:
    </label>

    <div class="controls readonly">

        <span data-ng-bind="contact.firstName" id="contact.firstName"></span>
    </div>
</div>

<div class="control-group fieldcontain">
    <label class="control-label" for="contact.middleName">
        <g:message code="leadContact.middleName" default="Middle Name"/>:
    </label>

    <div class="controls readonly">
        <span data-ng-bind="contact.middleName" id="contact.middleName"></span>
    </div>
</div>

<div class="control-group fieldcontain">
    <label class="control-label" for="contact.lastName">
        <g:message code="leadContact.lastName" default="Last Name"/>:
    </label>

    <div class="controls readonly">
        <span data-ng-bind="contact.lastName" id="contact.lastName"></span>
    </div>
</div>