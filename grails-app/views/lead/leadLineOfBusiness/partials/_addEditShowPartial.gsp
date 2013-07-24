<div data-ng-repeat="lineOfBusiness in lead.linesOfBusiness">
    <div class="well">
        <button type="button"
                class="btn btn-info btn-mini pull-right"
                data-ng-click="editLineOfBusiness"
                data-ng-hide="editingLineOfBusiness">
            <i class="icon-edit icon-white"></i>
            <g:message code="default.edit.label" args="[message([code: 'lineOfBusiness.label'])]"/>
        </button>
    </div>
</div>
