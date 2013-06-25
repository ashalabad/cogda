$(document).ready(function() {
    $('#accountList').dataTable({
        "bProcessing": true,
        "sAjaxSource": "/account/list",
        "aoColumns": [
            {"mDataProp":"accountName"},
            {"mDataProp":"accountCode"},
            {"mDataProp":"accountType.code"},
            {"mDataProp":"primaryContactName"},
            {"mDataProp":"primaryEmailAddress"},
            {"mDataProp":"primaryPhoneNumber"},
            {"mDataProp":"dateCreated"}
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
                $.get("/account/get/"+rowId, function(data) {
                    updateAccount(data);
                    updateContacts(rowId);
                    //updateMailingAddresses(data);
                    //updatePhones(data);
                    $('#accountForm').attr("id","accountForm_"+rowId);
                    $('#accountModal').modal('show');
                });
            });
        }
    });
    $('#applicationNavBarAccountCreate').bind('click', function() {
        $('#accountAddModal').modal('show');
    });
});

function updateAccount(data){
    $("#accountHeader > h3").text(data.modelObject.accountName);// + " - " + data.modelObject.accountType.code + " - " + data.modelObject.accountCode );
    $(".accountName").text(data.modelObject.accountName).val(data.modelObject.accountName);
    $(".accountCode").text(data.modelObject.accountCode).val(data.modelObject.accountCode);
    $("#accountType").val(data.modelObject.accountType.id);
    $("#accountTypeLbl").text(data.modelObject.accountType.code);

}

function updateContacts(rowId){
    $('#accountContactList').dataTable({
        "bProcessing": true,
        "bDestroy": true,
        "sAjaxSource": "/account/contactList/"+rowId,
        "aoColumns": [
            {"mDataProp":"accountContactName"},
            {"mDataProp":"accountContactEmail"},
            {"mDataProp":"accountContactPhone"}
        ],
        "sPaginationType": "bootstrap"
    });
}



function toggleEdit(){
    $(".accountShow").toggleClass("editHide");
    $(".accountEdit").toggleClass("editHide");
    $(".accountShow").toggleClass("editShow");
    $(".accountEdit").toggleClass("editShow");
}




///*******account*****/
//function saveAccount(){
//    toggleEdit();
//    var account = new Object();
//    account.id = $("form").attr("id").replace("accountForm_","");
//    account.firstName = $("#firstName").val();
//    account.lastName = $("#lastName").val();
//    account.middleName = $("#middleName").val();
//    account.initials = $("#initials").val();
//    account.jobTitle = $("#jobT itle").val();
//    account.companyName = $("#companyName").val();
//    account.website = $("#website").val();
//    $.ajax({
//        url: "/account/update/"+account.id,
//        type: "post",
//        dataType: "json",
//        data: JSON.stringify(account),
//        contentType: "application/json; charset=utf-8"
//    });
//}
//
///******emails******/
//function updateEmails(data){
//    $("#emailFieldset .field").remove();
//    $.each(data.modelObject.accountEmailAddresses, function(ind,elt){
//        var field = $(document.createElement("div")).addClass("field");
//        $(field).append($(document.createElement("label")).append("Email Address"));
//        var span = $(document.createElement("span")).attr("id","emailAddress_"+ind);
//        $(span).text(elt.emailAddress);
//        var radio = $(document.createElement("input")).attr("type","radio").attr("name","primary").addClass("primaryRadio");
//
//        $(field).append(span);
//        $(field).append(radio);
//        $(field).insertBefore("#addEmail");
//    });
//}
//
//function addEmailAddressField(){
//    var count = $("#emailFieldset div.field").length;
//    var field = $(document.createElement("div")).addClass("field");
//    $(field).append($(document.createElement("label")).append("Email Address"));
//    $(field).append($(document.createElement("input")).attr("id","emailAddress_"+count).val("").attr("type","text").addClass("input-xlarge"));
//    var saveBtn = $(".saveEmail.template").clone();
//    $(saveBtn).removeClass("template");
//    $(saveBtn).appendTo($(field));
//    $("#emailFieldset").prepend($(field));
//    $(field).insertBefore("#addEmail");
//}
//
//function saveEmail(){
//    // ajax get to email update
//    // on success updateEmails
//}
//
///*******addresses*******/
//function addMailingAddressField(){
//    var count = $("#mailFieldset div.field").length;
//    var field = $(".address.template").clone();
//    $(field).removeClass("template");
//    $(field).show();
//    $(field).attr("visibility","visible");
//    $(field).attr("id","address_"+count);
//    $(field).insertBefore($("#addMail"));
//}
///*******addresses*******/
//function addContactMailingAddressField(){
//    var count = $("#contactMailFieldset div.field").length;
//    var field = $(".address.template").clone();
//    $(field).removeClass("template");
//    $(field).show();
//    $(field).attr("visibility","visible");
//    $(field).attr("id","address_"+count);
//    $(field).insertBefore($("#addMail"));
//}
//
//function saveAddress(){
//
//}
//
//function updateMailingAddresses(data){
//    $("#mailFieldset .data").remove();
//    $.each(data.modelObject.accountAddresses, function(ind,elt){
//        var field = $(document.createElement("div")).addClass("field data address");
//        $(field).append($(document.createElement("label")).addClass("viewAddressLbl").append("Mailing Address"));
//        /*    var button = $(".editAddress").clone().removeClass("template");
//         $(field).append(button);*/
//        var add1 = $(document.createElement("div")).addClass("addressOne");
//        $(add1).text(elt.address.addressOne);
//        var add2 = $(document.createElement("div")).addClass("addressTwo");
//        $(add2).text(elt.address.addressTwo);
//        var add3 = $(document.createElement("div")).addClass("addressThree");
//        $(add3).text(elt.address.addressThree);
//        var city = $(document.createElement("span")).addClass("city");
//        $(city).text(elt.address.city);
//        var state = $(document.createElement("span")).addClass("state");
//        $(state).text(elt.address.state);
//        var zip = $(document.createElement("span")).addClass("zipcode");
//        $(zip).text(elt.address.zipcode);
//
//        $(field).append(add1).append(add2).append(add3).append(city).append(", ").append(state).append(" ").append(zip);
//        $(field).insertBefore($("#addMail"));
//    });
//}
//
//function cancelNewAddress(el){
//    $(el).parent().parent().remove();
//}
//
///*******phones******/
//function updatePhones(data){
//    $("#phoneFieldset .field").remove();
//    $.each(data.modelObject.accountPhoneNumbers, function(ind,elt){
//        var field = $(document.createElement("div")).addClass("field");
//        $(field).append($(document.createElement("label")).append("Phone Number"));
//        var span = $(document.createElement("span")).attr("id","phone_"+ind);
//        $(span).text(elt.phoneNumber);
//        $(field).append(span);
//        $("#phoneFieldset").prepend($(field));
//    });
//}
//
//function addPhoneField(){
//    var count = $("#phoneFieldset div.field").length;
//    var field = $(document.createElement("div")).addClass("field");
//    $(field).append($(document.createElement("label")).append("Phone Number"));
//    $(field).append($(document.createElement("input")).attr("id","phone_"+count).val("").attr("type","text").addClass("input-xlarge"));
//    var saveBtn = $(".savePhone.template").clone();
//    $(saveBtn).removeClass("template");
//    $(saveBtn).appendTo($(field));
//    $("#phoneFieldset").prepend($(field));
//    $(field).insertBefore("#addPhone");
//}
//
//function savePhone(){
//
//}