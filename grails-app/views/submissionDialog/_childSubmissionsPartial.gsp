<h4>
    <g:message code="submissionDialog.childSubmissions.label"></g:message>
</h4>

<div data-ng-repeat = "childSubmission in childSubmissions">
    <div class="well childSubmissionBlock" data-ng-click="showChildSubmission(childSubmission)" >
        <strong>
            <a>
                <!-- AIM -->
                {{ childSubmission.accountName }}
            </a>
        </strong>
        <br/>
        {{childSubmission.contactName}}&nbsp;-&nbsp;{{childSubmission.rfaStatus}}
        <br/>
        Me: {{childSubmission.myStatus}}
    </div>
</div>