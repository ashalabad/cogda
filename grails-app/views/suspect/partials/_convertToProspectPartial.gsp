<div data-modal="shouldConvertToProspect" class="modal" data-autoscroll>
    <div class="modal-header">
        <h3>Convert to Prospect</h3>
    </div>
    <div class="modal-body" style="max-height: 768px">
        <form>
            <div data-ng-include="" src="'/leadLineOfBusiness/createPartial'"></div>
        </form>
    </div>
    <div class="modal-footer">
        <button type="button" class="btn btn-primary" data-ng-click="performConvert(lead)"><i class="icon-save"></i> <g:message code="lead.convertToProspect.label"/></button>
        <button type="button" class="btn" data-ng-click="cancelConvertToProspect()"><i class="icon-remove"></i> <g:message code="default.button.cancel.label"/></button>
    </div>
</div>