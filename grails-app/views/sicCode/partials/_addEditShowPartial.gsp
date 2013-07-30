<div class="well" data-ng-controller="ModalEditCtrl">
    <button type="button"
            class="btn btn-info btn-mini pull-right"
            data-ng-click="open()">
        <i class="icon-edit icon-white"></i>
        <g:message code="default.edit.label" args="[message([code: 'sicCodes.label'])]"/>
    </button>
    <g:render template="/sicCode/partials/sicReadOnlyPartial"/>
    <g:render template="/sicCode/partials/sicCodesPartial"/>
</div>