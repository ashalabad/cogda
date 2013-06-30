$(document).ready(function() {
    $('#userList').dataTable(
    {
        "bProcessing": true,
        "sAjaxSource": "/user/dlist",
        "aoColumns": [
            {"mDataProp":"username"},
            {"mDataProp":"enabled"},
            {"mDataProp":"accountExpired"},
            {"mDataProp":"accountLocked"},
            {"mDataProp":"passwordExpired"}
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
                $("#userModalBody").load("/user/showForm/"+rowId, function(){
                  $('#userModal').modal('show');
                });
            });
        }                
    }); 
});

function toggleEdit(){
//  $(".contactShow").toggleClass("editHide");
//  $(".contactEdit").toggleClass("editHide");
//  $(".contactShow").toggleClass("editShow");
//  $(".contactEdit").toggleClass("editShow");
}

/*******contact*****/
function saveUser(){
//  toggleEdit();
//  var contact = new Object();
//  contact.id = $("form").attr("id").replace("contactForm_","");
//  contact.title = $("#title").val();
//  contact.firstName = $("#firstName").val();
//  contact.lastName = $("#lastName").val();
//  contact.middleName = $("#middleName").val();
//  contact.initials = $("#initials").val();
//  contact.jobTitle = $("#jobTitle").val();
//  contact.companyName = $("#companyName").val();
//  contact.website = $("#website").val();
//  $.ajax({
//      url: "/contact/update/"+contact.id,
//      type: "post",
//      dataType: "json",
//      data: JSON.stringify(contact),
//      success: updateContact,
//      contentType: "application/json; charset=utf-8"
//  });
}

function updateUser(data){
//  $(".title").text(data.title).val(data.title);
//  $(".firstName").text(data.firstName).val(data.firstName);
//  $(".middleName").text(data.middleName).val(data.middleName);
//  $(".lastName").text(data.lastName).val(data.lastName);
//  $(".initials").text(data.initials).val(data.initials);
//  $(".gender").text(data.gender);
//  $(".companyName").text(data.companyName).val(data.companyName);
//  $(".jobTitle").text(data.jobTitle).val(data.jobTitle);
//  $(".website").text(data.website).val(data.website);
}