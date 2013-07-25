<form class="form-horizontal" name="leadForm" novalidate>
<fieldset>
<legend>
    <g:message code="prospect.label"/>
</legend>

<div class="row">
    <div class="span4">
        <button class="btn btn-mini">Build Submission</button>
        <button class="btn btn-mini">View Contact Log</button>
        <button class="btn btn-danger btn-mini">Delete Prospect</button>
    </div>
    <div class="span4 pull-right">
        <form class="form-search">
            <div class="input-append pull-left">
                <input type="text" class="span3 search-query" data-ng-model="searchString.$"
                       placeholder="Search Prospect...">
            </div>
        </form>
    </div>
</div>
<br/>
<tabset>
    <tab heading="Dashboard">
        <div class="well">Coming soon....</div>
    </tab>
    <tab heading="Details">

        <div data-ng-hide="editingLead">
            <div data-ng-include="" src="'/lead/showPartial'"></div>
            %{--<g:render template="/lead/partials/showPartial"/>--}%

        </div>
        <div data-ng-show="editingLead">
            <div data-ng-include="" src="'/lead/editPartial'"></div>
            %{--<g:render template="/lead/partials/editPartial"/>--}%

        </div>
    </tab>
    <tab heading="Lines of Business ({{lead.linesOfBusiness.length}})">
        <g:render template="/lead/leadLineOfBusiness/partials/addEditShowPartial"/>
    </tab>
    <tab heading="NAICS & SIC Codes ({{lead.sicCodes ? lead.sicCodes.length : 0 + lead.naicsCodes ? lead.naicsCodes.length : 0}})">
        <legend><g:message code="sicCodes.label"/></legend>
        <g:render template="/sicCode/partials/addEditShowPartial"/>
        <legend><g:message code="naicsCodes.label"/> </legend>
        <g:render template="/naicsCode/partials/addEditShowPartial"/>
    </tab>
    <tab heading="Addresses ({{lead.leadAddresses.length}})">
        <g:render template="/lead/leadAddress/partials/addEditShowPartial"/>
    </tab>
    <tab heading="Contacts ({{lead.leadContacts.length}})">
        <g:render template="/lead/leadContact/partials/addEditShowPartial"/>
    </tab>
    <tab heading="Documents ({{lead.files.length}})">
        <div class="well">Coming Soon...</div>
    </tab>

    <tab heading="Notes ({{lead.leadNotes.length}})">
        <ul class="inline">
            <li data-ng-repeat="leadNote in lead.leadNotes | orderBy:'lastUpdated' | filter:searchString"
                class="span11 well well-small">
                <div data-ng-controller="EditLeadNoteController">
                    <address data-ng-hide="editingLeadNote">
                        <button type="button" class="btn btn-danger btn-mini pull-right"
                                data-ng-click="deleteLeadNote(leadNote)"><i
                                class="icon-edit"></i> <g:message code="default.button.delete.label"/></button>
                        <button type="button" class="btn btn-info btn-mini pull-right" data-ng-click="editLeadNote()"><i
                                class="icon-edit"></i> <g:message code="default.button.edit.label"/></button>
                        <strong>{{leadNote.lastUpdated | date:'MM/dd/yyyy @ h:mm a'}}<br> {{leadNote.noteType.code}}
                        </strong><br>

                        <p class="text-info"><em>{{leadNote.note.notes}}</em></p>
                    </address>

                    <div data-ng-show="editingLeadNote" data-ng-form="leadNoteForm">
                        <div data-ng-include src="'/leadNote/editPartial'"></div>
                        %{--<g:render template="/lead/leadNote/partials/editPartial"/>--}%
                        <div class="form-actions">
                            <button type="submit"
                                    class="btn btn-primary"
                                    data-ng-click="updateLeadNote(leadNote)">
                                <i class="icon-pencil icon-white"></i>
                                <g:message code="default.button.update.label" default="Update"/> <g:message
                                        code="note.label" default="Note"/>
                            </button>
                            <button class="btn btn-danger"
                                    type="button"
                                    data-ng-click="deleteLeadNote(leadNote)">
                                <i class="icon-remove icon-white"></i>
                                <g:message code="default.button.delete.label" default="Delete"/> <g:message
                                        code="note.label" default="Note"/>
                            </button>
                            <button type="button"
                                    class="btn"
                                    data-ng-click="cancelEditLeadNote()">
                                <i class="icon-ban-circle"></i>
                                <g:message code="default.button.cancel.label"/></button>
                        </div>
                    </div>
                </div>
            </li>
        </ul>
        <div data-ng-include="" src="'/leadNote/addPartial'"></div>
        %{--<g:render template="/lead/leadNote/partials/addPartial"/>--}%

    </tab>
</tabset>
</fieldset>
</form>

