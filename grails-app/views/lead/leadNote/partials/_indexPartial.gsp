%{--<div data-ng-controller="EditLeadNoteController">--}%
    %{--<div class="form-horizontal">--}%
        %{--<div class="well" data-ng-hide="editingNote">--}%
            %{--<g:render template="/lead/leadNote/partials/showPartial"/>--}%
        %{--</div>--}%
    %{--</div>--}%

    %{--<div class="well" data-ng-show="editingNote" data-ng-form="noteForm">--}%
        %{--<g:render template="/lead/leadNote/partials/editPartial"/>--}%
        %{--<div class="form-actions">--}%
            %{--<button type="submit"--}%
                    %{--class="btn btn-primary"--}%
                    %{--data-ng-click="updateNote(contact)">--}%
                %{--<i class="icon-pencil icon-white"></i>--}%
                %{--Update Note--}%
            %{--</button>--}%
            %{--<button class="btn btn-danger"--}%
                    %{--type="button"--}%
                    %{--data-ng-click="deleteNote(contact, $index)">--}%
                %{--<i class="icon-remove icon-white"></i>--}%
                %{--Delete Note--}%
            %{--</button>--}%
            %{--<button type="button"--}%
                    %{--class="btn"--}%
                    %{--data-ng-click="cancelEditNote()">--}%
                %{--<i class="icon-ban-circle"></i>--}%
                %{--Cancel</button>--}%
        %{--</div>--}%
    %{--</div>--}%

%{--</div>--}%

<table>
    <thead>
        <tr>
            <th>Created On</th>
            <th>Note</th>
            <th>Created By</th>
        </tr>
    </thead>
    <tbody>

    </tbody>
</table>