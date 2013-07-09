<ul class="nav nav-tabs" id="submissionTabs">
    <li class="active"><a href="#listSubmissions" data-toggle="tab">Submission List</a></li>
</ul>

<div class="tab-content" id="submissionTabsContent">
    <div class="tab-pane active" id="listSubmissions">

        <ul class="nav">
            <li class="btn" id="addSubmissionButton"><i class="icon-plus"></i> Create New Submission</li>
        </ul>
        <section id="list-submissions" class="first">
            <table class="table table-bordered" id="submissionsList">
                <thead>
                <tr>

                    <th>${message(code: 'submission.dateCreated.label')}</th>

                    <th>${message(code: 'submission.lead.label')}</th>

                    <th>${message(code: 'submission.lob.label')}</th>

                    <th>${message(code: 'submission.xdate.label')}</th>

                    <th><div class="span1"></div></th>

                </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
        </section>
    </div>
</div>