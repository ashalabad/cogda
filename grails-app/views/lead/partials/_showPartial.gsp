<legend>Details</legend>
<fieldset class="well">

    <button type="button"
            class="btn btn-info btn-mini pull-right"
            data-ng-click="editLead()"
            data-ng-hide="editingLead">
        <i class="icon-edit icon-white"></i>
        <g:message code="lead.edit.label"/> {{title}}
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

<div class="row-fluid">
    <div class="span6">
        <div class="row-fluid">
            <legend><g:message code="lineOfBusiness.label"/></legend>
            <g:render template="leadLineOfBusiness/partials/addEditShowPartial"/>
        </div>

        <div clas="row-fluid">

            <legend><g:message code="sicCodes.label"/></legend>
            <g:render template="/naicsCode/partials/addEditShowPartial"/>
        </div>

        <div class="row-fluid">
            <legend><g:message code="naicsCodes.label"/></legend>
            <g:render template="/naicsCode/partials/addEditShowPartial"/>
        </div>
    </div>

    <div class="span6">
        <div class="row-fuild">
            <legend><g:message code="address.label"/></legend>
            <g:render template="/lead/leadAddress/partials/addEditShowPartial"/>
        </div>

        <div class="row-fluid">
            <legend><g:message code="contact.label"/></legend>
            <g:render template="/lead/leadContact/partials/addEditShowPartial"/>
        </div>
    </div>

</div>