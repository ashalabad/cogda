<ul class="unstyled">
    <li>
        <strong>
            <g:message code="submissionDialog.rootSubmission.fromCompany.label"></g:message>:
        </strong>
            The Gaudreau Group
    </li>
    <li>
        <strong>
            <g:message code="submissionDialog.rootSubmission.fromUser.label"></g:message>
            <a href = "#">{{ userProfile.firstName }} {{ userProfile.lastName }}</a>
        </strong>
    </li>
    <li>
        <strong>
            <g:message code="submissionDialog.rootSubmission.prospect.label"></g:message>:
        </strong>
         {{lead.clientName}}
    </li>
    <li>
        <strong>
            <g:message code="submissionDialog.rootSubmission.status.label"></g:message>:
        </strong>
        Submitted: (6)
    </li>
</ul>

<table class="table table-bordered">
    <thead>
        <tr>
            <th>
                <g:message code="submissionDialog.rootSubmission.lineOfBusiness.label"></g:message>
            </th>
            <th>
                <g:message code="submissionDialog.rootSubmission.sentReceived.label"></g:message>
            </th>
        </tr>
    </thead>
    <tbody data-ng-repeat="lob in lead.linesOfBusiness">
        <tr>
            <td>
                <!-- lineOfBusiness -->
                <span popover-title="{{lob.code}}" popover="{{lob.description}}" popover-trigger="hover" popover-placement="top">
                    {{ lob.code }}
                </span>
            </td>
            <td>
                <!-- sentReceived -->
                {{ lead.linesOfBusiness.length }} / 0
            </td>
        </tr>
    </tbody>
</table>
