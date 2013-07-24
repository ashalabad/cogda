<div class="well" data-ng-controller="ModalEditCtrl">
    <button type="button"
            class="btn btn-info btn-mini pull-right"
            data-ng-click="open()">
        <i class="icon-edit icon-white"></i>
        <g:message code="default.edit.label" args="[message([code: 'naicsCodes.label'])]"/>
    </button>

    <div data-ng-repeat="naicsCode in lead.naicsCodes">
        <span data-ng-bind="naicsCode.description"></span>
    </div>
    <g:render template="/naicsCode/partials/naicsCodesPartial"/>
</div>