<legend><g:message code="lineOfBusiness.label"/></legend>
<g:render template="/lead/leadLineOfBusiness/partials/addPartial"/>
<br/>
<br/>
<span>
    <section id="no-more-tables">
        <table class="table table-striped table-bordered" data-ng-show="lead.linesOfBusiness.length > 0">
            <thead>
            <tr>
                <th><g:message code="lineOfBusiness.category.label"/></th>
                <th><g:message code="lineOfBusiness.label"/></th>
                <th><g:message code="leadLineOfBusiness.targetDate.label"/></th>
                <th><g:message code="leadLineOfBusiness.expirationDate.label"/></th>
                <th><g:message code="leadLineOfBusiness.targetPremium.label"/></th>
                <th><g:message code="leadLineOfBusiness.targetCommission.label"/></th>
                <th><g:message code="leadLineOfBusiness.commissionRate.label"/></th>
                <th><g:message code="leadLineOfBusiness.currentCarrier.label"/></th>
                <th><g:message code="leadLineOfBusiness.remarket.label"/></th>
                <th></th>
                <th></th>
            </tr>
            </thead>
            <tr data-ng-repeat="leadLineOfBusiness in lead.linesOfBusiness">
                <g:render template="/lead/leadLineOfBusiness/partials/showPartial"/>
                <td><button class="btn btn-info btn-mini"
                            type="button"
                            data-ng-click="editLineOfBusiness($index)"
                            data-ng-hide="editingLineOfBusiness">
                    <i class="icon-edit icon-white"></i>
                    <g:message code="default.button.edit.label"/>
                </button></td>
                <td><button class="btn btn-danger btn-mini"
                            type="button"
                            data-ng-click="deleteLineOfBusiness($index)"
                            data-ng-hide="editingLineOfBusiness">
                    <i class="icon-remove icon-white"></i>
                    <g:message code="default.button.delete.label"/>
                </button></td>
            </tr>
        </table>

        <div data-ng-show="editingLineOfBusiness">
            <g:render template="/lead/leadLineOfBusiness/partials/editPartial"/>
            <div class="form-actions">
                <button type="submit"
                        class="btn btn-primary"
                        data-ng-click="updateLineOfBusiness(leadLineOfBusiness)">
                    <i class="icon-pencil icon-white"></i>
                    <g:message code="default.button.update.label"/> <g:message code="lineOfBusiness.label"
                                                                               default="Line of Business"/>
                </button>
                <button class="btn btn-danger"
                        type="button"
                        data-ng-click="deleteLineOfBusiness(leadLineOfBusiness)">
                    <i class="icon-remove icon-white"></i>
                    <g:message code="default.button.delete.label"/> <g:message code="lineOfBusiness.label"
                                                                               default="Line of Business"/>
                </button>
                <button type="button"
                        class="btn"
                        data-ng-click="cancelEditLineOfBusiness()">
                    <i class="icon-ban-circle"></i>
                    <g:message code="default.button.cancel.label"/></button>
            </div>
        </div>
    </section>
</span>

