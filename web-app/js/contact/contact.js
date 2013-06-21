function toggleEdit(){
  $(".contactShow").toggleClass("editHide");
  $(".contactEdit").toggleClass("editHide");
  $(".contactShow").toggleClass("editShow");
  $(".contactEdit").toggleClass("editShow");  
}
function saveContact(){
  toggleEdit();
  var contactDeets = new Object();
  var contact = new Object();
  contactDeets.contact = contact;
  contact.id = $("form").attr("id").replace("contactForm_","");
  contact.firstName = $("#firstName").val();
  contact.lastName = $("#lastName").val();  
  $.post("/contact/update", JSON.stringify(contactDeets), function(data){updateContact(data)});
}

function addEmailAddressField(){
  var count = $("#emailFieldset div.field").length;
  var field = $(document.createElement("div")).addClass("field");
  $(field).append($(document.createElement("label")).append("Email Address"));
  $(field).append($(document.createElement("input")).attr("id","emailAddress_"+count).val("").attr("type","text"));  
	$("#emailFieldset").prepend($(field));
}

function addMailingAddressField(){
  var count = $("#mailFieldset div.field").length;  
  var field = $(document.createElement("div")).addClass("field");
  $(field).append($(document.createElement("label")).append("Mailing Address"));
  $(field).append($(document.createElement("input")).attr("id","mailingAddress_"+count).val("").attr("type","text"));  
	$("#mailFieldset").prepend($(field));
}

function addPhoneField(){
  var count = $("#phoneFieldset div.field").length;  
  var field = $(document.createElement("div")).addClass("field");
  $(field).append($(document.createElement("label")).append("Phone"));
  $(field).append($(document.createElement("input")).attr("id","phone_"+count).val("").attr("type","text"));  
	$("#phoneFieldset").prepend($(field));
}

function updateContact(data){
  $("#firstName").val(data.modelObject.firstName);
  $(".firstName").text(data.modelObject.firstName);                    
  $("#lastName").val(data.modelObject.lastName);
  $(".lastName").text(data.modelObject.lastName);                    
  $("#companyName").val(data.modelObject.companyName);
  $(".companyName").text(data.modelObject.companyName);                    
  $("#jobTitle").val(data.modelObject.jobTitle);                    
  $(".jobTitle").text(data.modelObject.jobTitle);  
}

function updateEmails(data){
  $("#emailFieldset .field").remove();
  $.each(data.modelObject.contactEmailAddresses, function(ind,elt){
    var field = $(document.createElement("div")).addClass("field");
    $(field).append($(document.createElement("label")).append("Email Address"));
    var span = $(document.createElement("span")).attr("id","emailAddress_"+ind);
    $(span).text(elt.id);
    $(field).append(span);    
  	$("#emailFieldset").prepend($(field));
  });
}

function updateMailingAddresses(data){
  $("#mailFieldset .field").remove();  
  $.each(data.modelObject.contactAddresses, function(ind,elt){
    var field = $(document.createElement("div")).addClass("field");
    $(field).append($(document.createElement("label")).append("Mailing Address"));
    var span = $(document.createElement("span")).attr("id","mailingAddress_"+ind);
    $(span).text(elt.id);
    $(field).append(span);    
  	$("#mailFieldset").prepend($(field));
  });
}

function updatePhones(data){
  $("#phoneFieldset .field").remove();  
  $.each(data.modelObject.contactPhoneNumbers, function(ind,elt){
    var field = $(document.createElement("div")).addClass("field");
    $(field).append($(document.createElement("label")).append("Phone Number"));
    var span = $(document.createElement("span")).attr("id","phone_"+ind);
    $(span).text(elt.id);
    $(field).append(span);    
  	$("#phoneFieldset").prepend($(field));
  });
}

$(document).ready(function() {
    $('#contactList').dataTable(
    {
        "bProcessing": true,
        "sAjaxSource": "/contact/list",
        "aoColumns": [
            {"mDataProp":"companyName"},
            {"mDataProp":"lastName"},
            {"mDataProp":"firstName"},
            {"mDataProp":"jobTitle"},
            {"mDataProp":"primaryEmailAddress"}
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
                $.get("/contact/get/"+rowId, function(data) {
                    updateContact(data);
                    updateEmails(data);
                    updateMailingAddresses(data);      
                    updatePhones(data);                                        
                    $('#contactForm').attr("id","contactForm_"+rowId);
                    $('#contactModal').modal('show');
                });
            });
        }                
    });
    
});