<legend><g:message code="lineOfBusiness.label"/></legend>
<button type="button"
        class="btn"
        data-ng-click="addLeadLineOfBusiness()"
        data-ng-hide="addingLeadLineOfBusiness">
    <i class="icon-plus"></i>
    <g:message code="default.add.label" args="[message(code: 'leadLineOfBusiness.label')]"/>
</button>
<br/>
<br/>

<div>

    <table id="no-more-tables" class="table table-striped table-bordered table-nonfluid table-input"
           data-ng-show="lead.linesOfBusiness.length > 0">
        <thead>
        <tr>
            <th style="width: 12.5%"><g:message code="lineOfBusiness.category.label"/></th>
            <th style="width: 12.5%"><g:message code="lineOfBusiness.label"/><span class="required-indicator">*</span></th>
            <th style="width: 13.5%"><g:message code="leadLineOfBusiness.expirationDate.label"/><span class="required-indicator">*</span></th>
            <th style="width: 13.5%"><g:message code="leadLineOfBusiness.targetDate.label"/></th>
            <th style="width: 11.5%"><g:message code="leadLineOfBusiness.targetPremium.label"/></th>
            <th style="width: 11.5%"><g:message code="leadLineOfBusiness.targetCommission.label"/></th>
            <th style="width: 10%"><g:message code="leadLineOfBusiness.commissionRate.label"/></th>
            <th style="width: 10%"><g:message code="leadLineOfBusiness.currentCarrier.label"/></th>
            <th style="width: 8%"><g:message code="leadLineOfBusiness.remarket.label"/></th>
            <th style="width: 4%"></th>
        </tr>
        </thead>
        <tr data-ng-repeat="leadLineOfBusiness in lead.linesOfBusiness" data-ng-form="leadLineOfBusinessForm" data-ng-controller="CreateLobCtrl" class="css-form">
            <g:render template="/lead/leadLineOfBusiness/partials/editInRowPartial"/>
        </tr>
    </table>
</div>