<table class="table table-striped table-bordered" data-ng-hide="editingLineOfBusiness">
    <tr>
        <th><g:message code="lineOfBusiness.category.label"/></th>
        <th><g:message code="lineOfBusiness.label"/></th>
        <th><g:message code="leadLineOfBusiness.targetDate.label"/></th>
        <th><g:message code="leadLineOfBusiness.expirationDate.label"/></th>
        <th><g:message code="leadLineOfBusiness.targetPremium.label"/></th>
        <th><g:message code="leadLineOfBusiness.targetCommission.label"/></th>
        <th><g:message code="leadLineOfBusiness.writingCompany.label"/></th>
        <th><g:message code="leadLineOfBusiness.billingCompany.label"/></th>
        <th><g:message code="leadLineOfBusiness.renewal.label"/></th>
        <th><g:message code="leadLineOfBusiness.remarket.label"/></th>
        <th><g:message code="default.button.edit.label"/></th>
        <th><g:message code="default.button.delete.label"/></th>
    </tr>

    <tr data-ng-controller="EditLeadLineOfBusinessCtrl" data-ng-repeat="leadLineOfBusiness in lead.linesOfBusiness">
        <g:render template="/lead/leadLineOfBusiness/partials/showPartial"/>
        <td><button class="btn btn-info btn-mini"
                    type="button"
                    data-ng-click="editLineOfBusiness()"
                    data-ng-hide="editingLineOfBusiness">
            <i class="icon-edit icon-white"></i>
            <g:message code="default.button.edit.label"/>
        </button></td>
        <td><button class="btn btn-danger btn-mini"
                    type="button"
                    data-ng-click="deleteLineOfBusiness(leadLineOfBusiness)"
                    data-ng-hide="editingLineOfBusiness">
            <i class="icon-remove icon-white"></i>
            <g:message code="default.button.delete.label"/>
        </button></td>
    </tr>
</table>

<div data-ng-controller="EditLeadLineOfBusinessCtrl" data-ng-show="editingLineOfBusiness" data-ng-form="lineOfBusinessForm">
    <g:render template="/lead/leadLineOfBusiness/partials/editPartial"/>
    <div class="form-actions">
        <button type="submit"
                class="btn btn-primary"
                data-ng-click="updateLineOfBusiness(leadLineOfBusiness)">
            <i class="icon-pencil icon-white"></i>
            <g:message code="default.button.update.label"/> <g:message code="lineOfBusiness.label" default="Line of Business"/>
        </button>
        <button class="btn btn-danger"
                type="button"
                data-ng-click="deleteLineOfBusiness(lineOfBusiness)">
            <i class="icon-remove icon-white"></i>
            <g:message code="default.button.delete.label"/> <g:message code="lineOfBusiness.label" default="Line of Business"/>
        </button>
        <button type="button"
                class="btn"
                data-ng-click="cancelEditLineOfBusiness()">
            <i class="icon-ban-circle"></i>
            <g:message code="default.button.cancel.label"/></button>
    </div>
</div>

<div data-ng-controller="AddLeadLineOfBusinessCtrl" data-ng-hide="editingLineOfBusiness">
    <g:render template="/lead/leadLineOfBusiness/partials/addPartial"/>
</div>