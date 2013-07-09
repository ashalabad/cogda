<ul class="nav nav-tabs" id="accountNoteTabs">
    <li class="active"><a href="#listAccountNotes" data-toggle="tab">Note List</a></li>
</ul>

<div class="tab-content" id="accountNoteTabsContent">
    <div class="tab-pane active" id="listAccountNotes">
        <ul class="nav">
            <li class="btn" id="addNoteButton"><i class="icon-plus"></i> Add Note</li>
        </ul>
        <section id="list-accountNotes" class="first">
            <table class="table table-bordered" id="accountNoteList">
                <thead>
                <tr>

                    <th>${message(code: 'accountNote.noteType.label')}</th>

                    <th>${message(code: 'accountNote.note.notes.label')}</th>

                    <th>${message(code: 'accountNote.dateCreated.label')}</th>

                    <th><div class="span1"></div></th>


                </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
        </section>
    </div>
</div>