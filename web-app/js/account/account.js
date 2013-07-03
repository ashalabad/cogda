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
                    var details = $('<div class="btn btn-mini"><i class="icon-eye-open"></i> Details</div>');
                    edit.button();
                    details.button();
                    edit.on('click',function(){
                        $("#accountModalBody").load("/account/edit/"+rowId, function(){
                            $('#accountModal').modal('show');
                        });
                    });
                    details.on('click',function(){
                        $("#accountModalBody").load("/account/show/"+rowId, function(){
                            updateContacts(rowId);
                            updateNotes(rowId);
                            updateSubmissions(rowId);
                            $('#accountModal').modal('show');
                        });
                    });
                    $(nTd).empty();
                    $(nTd).prepend(details);
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

//function modalDialogHandler(data) {
//    $('#accountModalBody').html(data)
//    $('#accountModal').modal('show')
//}

function toggleEdit(){
    $(".accountShow").toggleClass("editHide");
    $(".accountEdit").toggleClass("editHide");
    $(".accountShow").toggleClass("editShow");
    $(".accountEdit").toggleClass("editShow");
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

function updateAccount(data){
    $(".accountName").text(data.accountName).val(data.accountName);
    $(".accountCode").text(data.accountCode).val(data.accountCode);
    $("#accountType").val(data.accountType.id);
    $("#accountTypeLbl").text(data.accountType.code);
}




function updateContacts(rowId){
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
                    var edit = $('<div class="btn btn-mini"><i class="icon-edit"></i> Edit</div>');
                    var details = $('<div class="btn btn-mini"><i class="icon-eye-open"></i> Details</div>');
                    edit.button();
                    details.button();
                    edit.on('click',function(){
                        alert("account contact edit clicked");
//                        $("#accountModalBody").load("/account/edit/"+rowId, function(){
//                            $('#accountModal').modal('show');
//                        });
                    });
                    details.on('click',function(){
                        alert("account contact details clicked");
//                        $("#accountModalBody").load("/account/show/"+rowId, function(){
//                            updateContacts(rowId);
//                            updateNotes(rowId)
//                            $('#accountModal').modal('show');
//                        });
                    });
                    $(nTd).empty();
                    $(nTd).prepend(details);
                    $(nTd).prepend(edit);
                }
            }
        ],
        "sPaginationType": "bootstrap"
    });

    $("#createContactButton").click(function() {
        $("#createContactButton").hide();
        var tabs = $("#myTabs");
        tabs.append('<li><a id="createContactTab" href="#createContactContent" active- data-toggle="tab">Creating Contact</a></li>');
        var tabsContent = $('#myTabsContent');
        tabsContent.append('<div class="tab-pane" id="createContactContent"><div class="btn" id="closeCreateContactTab"><i class="icon-remove-circle"></i></div></div>')
        $("#myTabs a:last").tab('show'); // Select last tab
        $("#closeCreateContactTab").click(function(){
            removeTab("#createContactTab","#createContactContent");
            $("#createContactButton").show();
        })
    });

    $("#addContactButton").click(function() {
        $("#addContactButton").hide();
        var tabs = $("#myTabs");
        tabs.append('<li><a id="addContactTab" href="#addContactContent" active- data-toggle="tab">Adding Existing Contact</a></li>');
        var tabsContent = $('#myTabsContent');
        tabsContent.append('<div class="tab-pane" id="addContactContent"><div class="btn" id="closeAddContactTab"><i class="icon-remove-circle"></i></div></div>')
        $("#myTabs a:last").tab('show'); // Select last tab
        $("#closeAddContactTab").click(function(){
            removeTab("#addContactTab","#addContactContent");
            $("#addContactButton").show();
        })
    });
}

function updateNotes(rowId){
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
                    var edit = $('<div class="btn btn-mini"><i class="icon-edit"></i> Edit</div>');
                    var details = $('<div class="btn btn-mini"><i class="icon-eye-open"></i> Details</div>');
                    edit.button();
                    details.button();
                    edit.on('click',function(){
                        alert("account notes edit clicked");
//                        $("#accountModalBody").load("/account/edit/"+rowId, function(){
//                            $('#accountModal').modal('show');
//                        });
                    });
                    details.on('click',function(){
                        alert("account notes details clicked");
//                        $("#accountModalBody").load("/account/show/"+rowId, function(){
//                            updateContacts(rowId);
//                            updateNotes(rowId)
//                            $('#accountModal').modal('show');
//                        });
                    });
                    $(nTd).empty();
                    $(nTd).prepend(details);
                    $(nTd).prepend(edit);
                }
            }
        ],
        "sPaginationType": "bootstrap"
    });


    $("#addNoteButton").click(function() {
        $("#addNoteButton").hide();
        var tabs = $("#myTabs");
        tabs.append('<li><a id="addNotesTab" href="#addNotesContent" active- data-toggle="tab">Adding Notes</a></li>');
        var tabsContent = $('#myTabsContent');
        tabsContent.append('<div class="tab-pane" id="addNotesContent"><div class="btn" id="closeAddNoteTab"><i class="icon-remove-circle"></i></div></div>')
        $("#myTabs a:last").tab('show'); // Select last tab
        $("#closeAddNoteTab").click(function(){
            removeTab("#addNotesTab","#addNotesContent");
            $("#addNoteButton").show();
        })
    });

}

function updateSubmissions(rowId){
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
                    var edit = $('<div class="btn btn-mini"><i class="icon-edit"></i> Edit</div>');
                    var details = $('<div class="btn btn-mini"><i class="icon-eye-open"></i> Details</div>');
                    edit.button();
                    details.button();
                    edit.on('click',function(){
                        alert("account submissions edit clicked");
//                        $("#accountModalBody").load("/account/edit/"+rowId, function(){
//                            $('#accountModal').modal('show');
//                        });
                    });
                    details.on('click',function(){
                        alert("account submissions details clicked");
//                        $("#accountModalBody").load("/account/show/"+rowId, function(){
//                            updateContacts(rowId);
//                            updateNotes(rowId)
//                            $('#accountModal').modal('show');
//                        });
                    });
                    $(nTd).empty();
                    $(nTd).prepend(details);
                    $(nTd).prepend(edit);
                }
            }
        ],
        "sPaginationType": "bootstrap"
    });


    $("#addSubmissionButton").click(function() {
        $("#addSubmissionButton").hide();
        var tabs = $("#myTabs");
        tabs.append('<li><a id="addSubmissionsTab" href="#addSubmissionsContent" active- data-toggle="tab">Adding Submission</a></li>');
        var tabsContent = $('#myTabsContent');
        tabsContent.append('<div class="tab-pane" id="addSubmissionsContent"><div class="btn" id="closeAddSubmissionsTab"><i class="icon-remove-circle"></i></div></div>')
        $("#myTabs a:last").tab('show'); // Select last tab
        $("#closeAddSubmissionsTab").click(function(){
            removeTab("#addSubmissionsTab","#addSubmissionsContent");
            $("#addSubmissionButton").show();
        })
    });

}

function removeTab(tab,content) {
    $(tab).remove(); //remove li of tab
    $('#myTab a:last').tab('show'); // Select first tab
    $(content).remove(); //remove respective tab content
}