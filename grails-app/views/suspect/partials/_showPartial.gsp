<div class="row-fluid">
    <div class="span6">
        <div clas="row-fluid">
            <legend><g:message code="sicCodes.label"/></legend>
            <g:render template="/sicCode/partials/addEditShowPartial"/>
        </div>

        <div class="row-fluid">
            <legend><g:message code="naicsCodes.label"/></legend>
            <g:render template="/naicsCode/partials/addEditShowPartial"/>
        </div>
    </div>

    <div class="span6">
        <div class="row-fluid">
            <legend><g:message code="address.label"/></legend>
            <g:render template="/lead/leadAddress/partials/addEditShowPartial"/>
        </div>

        <div class="row-fluid">
            <legend><g:message code="contact.label"/></legend>
            <g:render template="/lead/leadContact/partials/addEditShowPartial"/>
        </div>
    </div>
</div>