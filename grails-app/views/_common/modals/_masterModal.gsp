<div class="modal hide fade" id="${modalLabel}Modal">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        <div id="registrationHeader">
            <h3>${modalHeader}</h3>
        </div>
    </div>
    <div class="modal-body" id="${modalLabel}ModalBody">

    </div>
    <div class="modal-footer">
        <a href="#" class="btn btn-primary" data-dismiss="modal">Close</a>
    </div>
    <g:each in="${modalList}" var="modalTemplate">
        <g:render template="/_common/${modalLabel}/${modalTemplate}"/>
    </g:each>
</div>