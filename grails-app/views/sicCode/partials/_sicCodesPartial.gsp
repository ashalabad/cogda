<div data-modal="shouldBeOpen" class="modal" data-autoscroll>
    <div class="modal-header">
        <h3><g:message code="sicCodes.label" default="SIC Codes"/></h3>
    </div>

    <div class="modal-body">
        <div data-sic-code-tree
             data-selected-nodes="lead.sicCodes"
             data-undetermined-nodes="undeterminedSicNodes"
             data-include-related-naics-codes="includeRelatedNaicsCodes"
             data-related-naics-codes="relatedNaicsCodes">
        </div>
    </div>

    <div class="modal-footer">
        <a class="btn btn-primary" data-dismiss="modal" data-ng-click="save()"><i
                class="icon-save"></i> <g:message code="default.button.save.label" default="Save"/></a>
        <a class="btn btn-danger" data-dismiss="modal" data-ng-click="close()"><i
                class="icon-cancel"></i> <g:message code="default.button.cancel.label" default="Cancel"/></a>
    </div>
</div>
