<div data-modal="shouldBeOpen" class="modal" data-autoscroll>
    <div class="modal-header">
        <h3><g:message code="naicsCodes.label" default="NAICS Codes"/></h3>
    </div>

    <div class="modal-body">
        <div data-naics-code-tree
             data-selected-nodes="lead.naicsCodes"
             data-undetermined-nodes="undeterminedNaicsNodes"
             data-include-related-sic-codes="includeRelatedSicCodes"
             data-related-sic-codes="relatedSicCodes">
        </div>
    </div>

    <div class="modal-footer">
        <button class="btn btn-primary" data-dismiss="modal" data-ng-click="save()"><i
                class="icon-save"></i> <g:message code="default.button.save.label" default="Save"/></button>
        <button class="btn btn-danger" data-dismiss="modal" data-ng-click="close()"><i
                class="icon-cancel"></i> <g:message code="default.button.cancel.label" default="Cancel"/></button>
    </div>
</div>
