<fieldset class="well">
    <button type="button"
            class="btn btn-info btn-mini pull-right"
            data-ng-click="editLead()"
            data-ng-hide="editingLead" >
        <i class="icon-edit icon-white"></i>
        Edit Suspect
    </button>
    <div class="control-group fieldcontain">
        <label class="control-label" for="lead.businessType.code">
            <g:message code="lead.businessType.label"
                       default="Business Type"/>:
        </label>

        <div class="controls readonly">
            <span data-ng-bind="lead.businessType.code" id="lead.businessType.code"></span>
        </div>
    </div>

    <div class="control-group fieldcontain">
        <label class="control-label" for="lead.clientId">
            <g:message code="lead.clientId.label"
                       default="Client Id"/>:
        </label>

        <div class="controls readonly">
            <span data-ng-bind="lead.clientId" id="lead.clientId"></span>
        </div>
    </div>

    <div class="control-group fieldcontain">
        <label class="control-label" for="lead.clientName">
            <g:message code="lead.clientName.label" default="Client Name"/>:
        </label>

        <div class="controls readonly">
            <span data-ng-bind="lead.clientName" id="lead.clientName"></span>
        </div>
    </div>

    <div class="control-group fieldcontain">
        <label class="control-label" for="lead.subType">
            <g:message code="lead.subType.label" default="Sub Type"/>:
        </label>

        <div class="controls readonly">
            <span data-ng-bind="lead.subType" id="lead.subType"></span>
        </div>
    </div>

    <div class="control-group fieldcontain">
        <label class="control-label" for="lead.customerServiceRepresentative">
            <g:message code="lead.customerServiceRepresentative.label" default="Customer Service Representative"/>:
        </label>

        <div class="controls readonly">
            <span data-ng-bind="lead.customerServiceRepresentative" id="lead.customerServiceRepresentative"></span>
        </div>
    </div>


</fieldset>