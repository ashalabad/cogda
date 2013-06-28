$(document).ready(function() {
    $('#accountList').dataTable({
        "bProcessing": true,
        "sAjaxSource": "/account/list",
        "aoColumns": [
            {"mDataProp":"accountName"},
            {"mDataProp":"accountCode"},
            {"mDataProp":"accountType.code"},
            {"mDataProp":"primaryContact"},
            {"mDataProp":"details"},
            {"mDataProp":"edit"}
            ],
        "sDom": "<'row'<'span6'l><'span6'f>r>t<'row'<'span6'i><'span6'p>>",
        "sPaginationType": "bootstrap",
        "fnRowCallback": function( nRow, aData, iDisplayIndex ) {
            $(nRow).click( function() {
                $('.row_selected').toggleClass('row_selected');
                $(this).toggleClass('row_selected');
            });
            $(nRow).dblclick( function() {
                var rowId = $(this).attr("id").replace("row_","");
                $("#accountModalBody").load("/account/show/"+rowId, function(){
                    $('#accountModal').modal('show');
                });
            });
        }
    });
});

function modalDialogHandler(data) {
    $('#accountModalBody').html(data)
    $('#accountModal').modal('show')
}

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



//
//function updateContacts(rowId){
//    $('#accountContactList').dataTable({
//        "bProcessing": true,
//        "bDestroy": true,
//        "sAjaxSource": "/account/contactList/"+rowId,
//        "aoColumns": [
//            {"mDataProp":"accountContactName"},
//            {"mDataProp":"accountContactEmail"},
//            {"mDataProp":"accountContactPhone"}
//        ],
//        "sPaginationType": "bootstrap"
//    });
//}
//
//