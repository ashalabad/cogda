
<ul class="unstyled inline">
    <li><a href="#" class="btn btn-mini"><i class="icon-exchange"></i> Bind</a></li>
    <li><a href="#" class="btn btn-mini"><i class="icon-user"></i> Assign Owner</a></li>
    <li><a href="#" class="btn btn-mini"><i class="icon-remove-sign"></i> Decline</a></li>
    <li><a href="#" class="btn btn-mini"><i class="icon-remove"></i> Close</a></li>
    <li><a href="#" class="btn btn-mini"><i class="icon-share"></i> Share</a></li>
    <li><a href="#" class="btn btn-mini"><i class="icon-folder-open-alt"></i>  Document Viewer</a></li>
    <li><a href="#" class="btn btn-mini"><i class="icon-envelope"></i> Export to Email</a></li>
</ul>

    <div class="row">
        <span class="span2 offset1">
            <strong>
                Originator:
            </strong> <br/>
            Stacy Smith
        </span>
        <span class="span3">
            <strong>
                Recipient:
            </strong>  <br/>
            Me
        </span>
        <span class="span3">
            <strong>
                Target Date:
            </strong>  <br/>
            08/24/2013 <i class="icon-pencil"></i>
        </span>

    </div>

    <table class="table table-bordered table-striped table-hover">
        <thead>
            <tr>
                <th>
                    Line of Business
                </th>
                <th>
                    Current Carrier
                </th>
                <th>
                    Expiration Date
                </th>
                <th>
                    Target/Quoted Premium
                </th>
                <th>
                    % Diff
                </th>
                <th>
                    My Workflow
                </th>
                <th>
                    Market Activity
                </th>
            </tr>
        </thead>
        <tbody data-ng-repeat="leadLineOfBusiness in leadLineOfBusinesses">
            <tr>
                <td>{{ leadLineOfBusiness.description }}</td>
                <td>{{ leadLineOfBusiness.currentCarrier }} </td>
                <td>{{ leadLineOfBusiness.expirationDate }}</td>
                <td>{{ leadLineOfBusiness.targetPremium | currency }} / {{leadLineOfBusiness.quotedPremium }} </td>
                <td>{{ leadLineOfBusiness.commission }}</td>
                <td>{{ leadLineOfBusiness.myWorkflowStatus }}</td>
                <td>{{ leadLineOfBusiness.marketActivityStatus }}</td>
            </tr>
        </tbody>
    </table>

<ul class="nav nav-pills">
    <li data-ng-class="{active:isActive('conversations')}" ><a href="" data-ng-click="activate('conversations')">Conversations</a></li>
    <li data-ng-class="{active:isActive('quotes')}"><a href="" data-ng-click="activate('quotes')">Quotes</a></li>
    <li data-ng-class="{active:isActive('notes')}"><a href=""  data-ng-click="activate('notes')">Notes</a></li>
</ul>


<div data-ng-show="isActive('conversations')">
    <table class="table table-striped">
        <thead>
            <tr>
                <th>
                    <i class="icon-paper-clip"></i>
                </th>
                <th>Subject</th>
                <th>From</th>
                <th>Sent</th>
                <th>Due</th>
            </tr>
        </thead>
        <tbody data-ng-repeat="requestForAction in requestForActions">
           <tr >
               <td>
                &nbsp;
               </td>
               <td>
                   {{ requestForAction.subject }}
               </td>

               <td>
                   {{ requestForAction.from }}
               </td>

               <td>
                   {{ requestForAction.sentDate }}
               </td>

               <td>
                   {{ requestForAction.dueDate }}
               </td>
           </tr>

        </tbody>
    </table>
</div>

<div data-ng-show="isActive('quotes')" id="quotesDiv">

</div>

<div data-ng-show="isActive('notes')" id="notesDiv">

    <div data-ng-form = "noteForm" class="form-horizontal">
        <fieldset class="embedded">
            <legend>
                Add Note
            </legend>

            <label><input type = "radio" value="visit" name="noteType">Visit</label>
            <label><input type = "radio" value="call"  name="noteType">Call</label>
            <label><input type = "radio" value="other" name="noteType">Other</label>

            <textarea name="noteText">

            </textarea>


            <div class="form-actions">
                <button type="submit"
                        class="btn btn-primary"
                        data-ng-click="saveNote(noteForm)"
                        data-ng-disabled="!canSaveNote(notesForm)">
                    <i class="icon-plus icon-white"></i>
                   Save Note</button>
            </div>
        </fieldset>

    </div>

    <ul class="unstyled" data-ng-repeat="note in notes">
        <li>
            <strong>{{ note.noteType }}</strong>
            <strong>{{ note.dateCreated | date }}</strong>
            {{ note.noteText }}
        </li>
    </ul>

</div>




