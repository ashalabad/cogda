$(document).ready(function() {

    $('#accountList').dataTable({
        "bProcessing": true,
        "sAjaxSource": "/account/list",
        "aoColumns": [
            {"mDataProp":"accountName"},
            {"mDataProp":"accountCode"},
            {"mDataProp":"accountType.code"},
            {"mDataProp":"primaryContact"},
            {"mDataProp":"action"}
        ],
        "aoColumnDefs" : [
            {
                'bSortable' : false,
                'aTargets' : [ 4] //remove sorting on action rows
            },
            {
                "aTargets": [4],
                "fnCreatedCell" : function(nTd, sData, oData, iRow, iCol){
                    var rowId = iRow + 1
                    var edit = $('<div class="btn btn-mini"><i class="icon-edit"></i> Edit</div>');
                    var show = $('<div class="btn btn-mini"><i class="icon-eye-open"></i> Show</div>');
                    edit.button();
                    show.button();
                    edit.on('click',function(){
                        $("#accountModalBody").load("/account/edit/"+rowId, function(){
                            $('#accountModal').modal('show');
                        });
                    });
                    show.on('click',function(){
                        $("#accountModalBody").load("/account/show/"+rowId, function(){
                            refreshAccountContacts(rowId);
                            refreshAccountNotes(rowId);
                            refreshAccountSubmissions(rowId);
                            $('#accountModal').modal('show');
                        });
                    });
                    $(nTd).empty();
                    $(nTd).prepend(show);
                    $(nTd).prepend(edit);
                }
            }
        ],
        "sDom": "<'row'<'span6'l><'span6'f>r>t<'row'<'span6'i><'span6'p>>",
        "sPaginationType": "bootstrap",
        "fnRowCallback": function( nRow, aData, iDisplayIndex ) {
            $(nRow).click( function() {
                $('.row_selected').toggleClass('row_selected');
                $(this).toggleClass('row_selected');
            });
        }
    });


});

function refreshAccountContacts(rowId){
    $('#accountContactList').dataTable({
        "bProcessing": true,
        "bDestroy": true,
        "sAjaxSource": "/account/getAccountContacts/"+rowId,
        "aoColumns": [
            {"mDataProp":"accountContactName"},
            {"mDataProp":"accountContactEmail"},
            {"mDataProp":"accountContactPhone"},
            {"mDataProp":"action"}
        ],
        "aoColumnDefs" : [
            {
                'bSortable' : false,
                'aTargets' : [3] //remove sorting on action rows
            },
            {
                "aTargets": [3],
                "fnCreatedCell" : function(nTd, sData, oData, iRow, iCol){
                    var rowId = iRow + 1;
                    var edit = $('<div class="btn btn-mini accountContactEditButton"><i class="icon-edit"></i> Edit</div>');
                    var show = $('<div class="btn btn-mini accountContactShowButton"><i class="icon-eye-open"></i> Show</div>');
                    edit.button();
                    show.button();
                    edit.on('click',function(){
                        accountContactEditTab(rowId);
                    });
                    show.on('click',function(){
                        accountContactShowTab(rowId);
                    });
                    $(nTd).empty();
                    $(nTd).prepend(show);
                    $(nTd).prepend(edit);
                }
            }
        ],
        "sPaginationType": "bootstrap"
    });

    $("#createContactButton").click(function() {

        $.get("/accountContact/add/"+rowId, function(data){
            $('#accountContactTabsContent').append(data);
        });

        setTimeout(function() {
            $("#accountContactTabs").append('<li><a id="createContactsTab" href="#createContactsContent" active- data-toggle="tab">Add Contact</a></li>');
            $("#createContactButton").hide();
            $("#closeCreateContactTab").click(function(){
                removeTab("#createContactsTab","#createContactsContent");
                $("#createContactButton").show();
                $("#accountContactTabs a:first").tab('show'); // Select first tab
            });
            $("#accountContactTabs a:last").tab('show'); // Select last tab
        }, 250);
    });

    $("#addContactButton").click(function() {
        $.get("/accountContact/addExisting/"+rowId, function(data){
            $('#accountContactTabsContent').append(data);
        });

        setTimeout(function() {
            $("#accountContactTabs").append('<li><a id="addContactsTab" href="#addContactsContent" active- data-toggle="tab">Add Existing Contact</a></li>');
            $("#addContactButton").hide();
            $("#closeAddContactTab").click(function(){
                removeTab("#addContactsTab","#addContactsContent");
                $("#addContactButton").show();
                $("#accountContactTabs a:first").tab('show'); // Select first tab
            });
            $("#accountContactTabs a:last").tab('show'); // Select last tab
        }, 250);
    });
}

function refreshAccountNotes(rowId){
    $('#accountNoteList').dataTable({
        "bProcessing": true,
        "bDestroy": true,
        "sAjaxSource": "/account/getAccountNotes/"+rowId,
        "aoColumns": [
            {"mDataProp":"accountNoteType"},
            {"mDataProp":"accountNote"},
            {"mDataProp":"accountNoteDate"},
            {"mDataProp":"action"}
        ],
        "aoColumnDefs" : [
            {
                'bSortable' : false,
                'aTargets' : [3] //remove sorting on action rows
            },
            {
                "aTargets": [3],
                "fnCreatedCell" : function(nTd, sData, oData, iRow, iCol){
                    var rowId = iRow + 1;
                    var edit = $('<div class="btn btn-mini accountNoteEditButton"><i class="icon-edit"></i> Edit</div>');
                    var show = $('<div class="btn btn-mini accountNoteShowButton"><i class="icon-eye-open"></i> Show</div>');
                    edit.button();
                    show.button();
                    edit.on('click',function(){
                        accountNoteEditTab(rowId);
                    });
                    show.on('click',function(){
                        accountNoteShowTab(rowId);
                    });
                    $(nTd).empty();
                    $(nTd).prepend(show);
                    $(nTd).prepend(edit);
                }
            }
        ],
        "sPaginationType": "bootstrap"
    });


    $("#addNoteButton").click(function() {
        $.get("/accountNote/add/"+rowId, function(data){
            $('#accountNoteTabsContent').append(data);
        });

        setTimeout(function() {
            $("#accountNoteTabs").append('<li><a id="addNotesTab" href="#addNotesContent" active- data-toggle="tab">Add Note</a></li>');


            $("#addNoteButton").hide();
            $("#closeAddNoteTab").click(function(){
                removeTab("#addNotesTab","#addNotesContent");
                $("#addNoteButton").show();
                $("#accountNoteTabs a:first").tab('show'); // Select first tab
            });
            $("#accountNoteTabs a:last").tab('show'); // Select last tab
        }, 250);
    });
}

function refreshAccountSubmissions(rowId){
    $('#submissionsList').dataTable({
        "bProcessing": true,
        "bDestroy": true,
        "sAjaxSource": "/account/getSubmissions/"+rowId,
        "aoColumns": [
            {"mDataProp":"submissionDate"},
            {"mDataProp":"submissionLead"},
            {"mDataProp":"submissionLOB"},
            {"mDataProp":"submissionxDate"},
            {"mDataProp":"action"}
        ],
        "aoColumnDefs" : [
            {
                'bSortable' : false,
                'aTargets' : [4] //remove sorting on action rows
            },
            {
                "aTargets": [4],
                "fnCreatedCell" : function(nTd, sData, oData, iRow, iCol){
                    var rowId = iRow + 1;
                    var edit = $('<div class="btn btn-mini accountNoteEditButton"><i class="icon-edit"></i> Edit</div>');
                    var show = $('<div class="btn btn-mini accountNoteShowButton"><i class="icon-eye-open"></i> Show</div>');
                    edit.button();
                    show.button();
                    edit.on('click',function(){
                        submissionEditTab(rowId);
                    });
                    show.on('click',function(){
                        submissionShowTab(rowId);
                    });
                    $(nTd).empty();
                    $(nTd).prepend(show);
                    $(nTd).prepend(edit);
                }
            }
        ],
        "sPaginationType": "bootstrap"
    });

    $("#addSubmissionButton").click(function() {
        alert("TBA"); //TODO: addSubmissionButton
    });

}

function toggleEdit(){
    $(".accountShow").toggleClass("editHide");
    $(".accountEdit").toggleClass("editHide");
    $(".accountShow").toggleClass("editShow");
    $(".accountEdit").toggleClass("editShow");
}

function updateAccount(data){
    $(".accountName").text(data.accountName).val(data.accountName);
    $(".accountCode").text(data.accountCode).val(data.accountCode);
    $("#accountType").val(data.accountType.id);
    $("#accountTypeLbl").text(data.accountType.code);
}

function saveAccount(){
    toggleEdit();
    var account = new Object();
    account.id = $("form").attr("id").replace("accountForm_","");
    account.accountName = $("#accountName").val();
    account.accountCode = $("#accountCode").val();
    account.accountType = new Object();
    account.accountType.id = $("#accountType").val();
    console.log(account);
    $.ajax({
        url: "/account/update/"+account.id,
        type: "post",
        dataType: "json",
        data: JSON.stringify(account),
        success: updateAccount,
        contentType: "application/json; charset=utf-8"
    });
}

function saveAccountNote(){
    var note = new Object();
    note.account = new Object();
    note.account.id = $("#accountId").val();
    note.noteType = $("#noteType").val();
    note.note = new Object();
    note.note.notes = $("#note.notes").val();
    alert("here");
    $.ajax({
        url: "/accountNote/save",
        type: "post",
        dataType: "json",
        data: JSON.stringify(note),
        success: updateNotes,
        contentType: "application/json; charset=utf-8"
    });
}

function accountContactEditTab(rowId){
    $(".accountContactEditButton").hide();

    $.get("/accountContact/edit/"+rowId, function(data){
        $('#accountContactTabsContent').append(data);
    });

    setTimeout(function() {
        $("#accountContactTabs").append('<li><a id="editContactsTab" href="#editContactsContent" active- data-toggle="tab">Edit Contact</a></li>');
        $("#closeEditContactTab").click(function(){
            removeTab("#editContactsTab","#editContactsContent");
            $(".accountContactEditButton").show();
            $("#accountContactTabs a:first").tab('show'); // Select first tab
        });
        $("#accountContactTabs a:last").tab('show'); // Select last tab
    }, 500);

}

function accountContactShowTab(rowId){
    $(".accountContactShowButton").hide();

    $.get("/accountContact/show/"+rowId, function(data){
        $('#accountContactTabsContent').append(data);
    });

    setTimeout(function() {
        $("#accountContactTabs").append('<li><a id="showContactsTab" href="#showContactsContent" active- data-toggle="tab">Show Contact</a></li>');
        $("#closeShowContactTab").click(function(){
            removeTab("#showContactsTab","#showContactsContent");
            $(".accountContactShowButton").show();
            $("#accountContactTabs a:first").tab('show'); // Select first tab
        });
        $("#accountContactTabs a:last").tab('show'); // Select last tab
    }, 500);

}

function accountNoteEditTab(rowId){
    $(".accountNoteEditButton").hide();

    $.get("/accountNote/edit/"+rowId, function(data){
        $('#accountNoteTabsContent').append(data);
    });

    setTimeout(function() {
        $("#accountNoteTabs").append('<li><a id="editNotesTab" href="#editNotesContent" active- data-toggle="tab">Edit Note</a></li>');
        $("#closeEditNoteTab").click(function(){
            removeTab("#editNotesTab","#editNotesContent");
            $(".accountNoteEditButton").show();
            $("#accountNoteTabs a:first").tab('show'); // Select first tab
        });
        $("#accountNoteTabs a:last").tab('show'); // Select last tab
    }, 500);

}

function accountNoteShowTab(rowId){
    $(".accountNoteShowButton").hide();

    $.get("/accountNote/show/"+rowId, function(data){
        $('#accountNoteTabsContent').append(data);
    });

    setTimeout(function() {
        $("#accountNoteTabs").append('<li><a id="showNotesTab" href="#showNotesContent" active- data-toggle="tab">Show Note</a></li>');
        $("#closeShowNoteTab").click(function(){
            removeTab("#showNotesTab","#showNotesContent");
            $(".accountNoteShowButton").show();
            $("#accountNoteTabs a:first").tab('show'); // Select first tab
        });
        $("#accountNoteTabs a:last").tab('show'); // Select last tab
    }, 500);

}

function submissionEditTab(rowId){
    alert("TBA");// TODO: submissionEditTab
}

function submissionShowTab(rowId){
    alert("TBA"); //TODO: submissionShowTab
}

function removeTab(tab,content) {
    $(tab).remove(); //remove li of tab
    $(content).remove(); //remove respective tab content
}
