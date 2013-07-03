
<ul class="nav nav-tabs" id="myTabs">
    <li><a href="#submissions" data-toggle="tab">Submissions</a></li>
    <li><a href="#contacts" data-toggle="tab">Contacts</a></li>
    <li><a href="#notes" data-toggle="tab">Notes</a></li>
</ul>

<div class="tab-content" id="myTabsContent">
    <div class="tab-pane" id="submissions">
        <ul class="nav">
            <li class="btn" id="addSubmissionButton"><i class="icon-plus"></i> Create New Submission</li>
        </ul>
        <section id="list-submissions" class="first">
            <table class="table table-bordered" id="submissionsList">
                <thead>
                <tr>

                    <th>${message(code: 'account.submission.date.label')}</th>

                    <th>${message(code: 'account.submission.lead.label')}</th>

                    <th>${message(code: 'account.submission.lob.label')}</th>

                    <th>${message(code: 'account.submission.xdate.label')}</th>

                    <th><div class="span1"></div></th>

                </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
        </section>
    </div>
    <div class="tab-pane" id="contacts">
        <ul class="nav">
            <li class="btn" id="createContactButton"><i class="icon-plus"></i> Create New Contact</li>
            <li class="btn" id="addContactButton"><i class="icon-plus"></i> Add Existing Contact</li>
        </ul>
        <section id="list-accountContacts" class="first">
            <table class="table table-bordered" id="accountContactList">
                <thead>
                <tr>

                    <th>${message(code: 'account.accountContact.name.label')}</th>

                    <th>${message(code: 'account.accountContact.email.label')}</th>

                    <th>${message(code: 'account.accountContact.phone.label')}</th>

                    <th><div class="span1"></div></th>

                </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
        </section>
    </div>
    <div class="tab-pane" id="notes">
        <ul class="nav">
            <li class="btn" id="addNoteButton"><i class="icon-plus"></i> Add Note</li>
        </ul>
        <section id="list-accountNotes" class="first">
            <table class="table table-bordered" id="accountNoteList">
                <thead>
                <tr>

                    <th>${message(code: 'account.accountNote.noteType.label')}</th>

                    <th>${message(code: 'account.accountNote.note.notes.label')}</th>

                    <th>${message(code: 'account.accountNote.note.dateCreated.label')}</th>

                    <th><div class="span1"></div></th>


                </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
        </section>
    </div>
</div>
