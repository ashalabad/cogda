
<ul class="nav nav-pills">
    <li><a href="#">Bind</a></li>
    <li><a href="#">Assign Owner</a></li>
    <li><a href="#">Decline</a></li>
    <li><a href="#">Close</a></li>
    <li><a href="#">Share</a></li>
    <li><a href="#">Document Viewer</a></li>
    <li><a href="#">Export to Email</a></li>
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
    <li class="active"><a href="#">Conversations</a></li>
    <li><a href="">Quotes</a></li>
    <li><a href="">Notes</a></li>
</ul>

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





