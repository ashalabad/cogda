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
                $("#contactModalBody").load("http://rais.cogdalocal.com:8090/contact/showForm/"+rowId, function(){
                  $('#contactModal').modal('show');               
                });
            });
        }                
    }); 
});

function validateWebsite(){
  
}

function toggleEdit(){
  $(".contactShow").toggleClass("editHide");
  $(".contactEdit").toggleClass("editHide");
  $(".contactShow").toggleClass("editShow");
  $(".contactEdit").toggleClass("editShow");  
}

/*******contact*****/
function saveContact(){
  toggleEdit();
  var contact = new Object();
  contact.id = $("form").attr("id").replace("contactForm_","");
  contact.firstName = $("#firstName").val();
  contact.lastName = $("#lastName").val();  
  contact.middleName = $("#middleName").val();    
  contact.initials = $("#initials").val();      
  contact.jobTitle = $("#jobTitle").val();        
  contact.companyName = $("#companyName").val();          
  contact.website = $("#website").val();            
  $.ajax({
      url: "/contact/update/"+contact.id,
      type: "post",
      dataType: "json",
      data: JSON.stringify(contact),
      success: updateContact,
      contentType: "application/json; charset=utf-8"
  });
}

function updateContact(data){
  $(".title").text(data.title).val(data.title);                    
  $(".firstName").text(data.firstName).val(data.firstName);  
  $(".middleName").text(data.middleName).val(data.middleName); 
  $(".lastName").text(data.lastName).val(data.lastName);       
  $(".initials").text(data.initials).val(data.initials);                      
  $(".gender").text(data.gender);                      
  $(".companyName").text(data.companyName).val(data.companyName);                    
  $(".jobTitle").text(data.jobTitle).val(data.jobTitle);  
  $(".website").text(data.website).val(data.website);    
}

/******emails******/
function updateEmails(data){
  $("#emailFieldset .data").remove();
  $.each(data.contactEmailAddresses, function(ind,elt){
    var field = $("#editEmail").clone().removeClass("template").addClass("data");
    $(field).attr("id","emailField_"+elt.id);
    $(field).find(".emailText").text(elt.emailAddress);
    $(field).find(".emailInput").val(elt.emailAddress);    
  	$(field).insertBefore("#addEmailBtn");

  });
}

function editEmail(event){
  var id = $(event.currentTarget).parent().attr("id");
  $("#"+id+" .showMode").toggleClass("showMe").toggleClass("hideMe");
  $("#"+id+" .editMode").toggleClass("showMe").toggleClass("hideMe");
}

function addEmailAddressField(){
  var count = $("#emailFieldset div.field").length;
  var field = $("#addEmail").clone();
  $(field).removeClass("template").attr("id","emailAddress_"+count).addClass("data");  
  var btn = $("#addEmailBtn");
	$(field).insertBefore("#addEmailBtn");
}

function saveEmail(event){
  // ajax get to email update
  // on success updateEmails
  var id = $(event.currentTarget).parent().parent().attr("id");
  $("#"+id+" .showMode").toggleClass("showMe").toggleClass("hideMe");
  $("#"+id+" .editMode").toggleClass("showMe").toggleClass("hideMe");
  /*$.ajax({
      url: "/contact/update/"+contact.id,
      type: "post",
      dataType: "json",
      data: JSON.stringify(contact),
      contentType: "application/json; charset=utf-8",
      success: updateEmails(data)
  });*/


}

/*******addresses*******/
function addMailingAddressField(){
  var count = $("#mailFieldset div.field").length;  
  var field = $(".address.template").clone();
  $(field).removeClass("template").addClass("data");
  $(field).show();
  $(field).attr("visibility","visible");
  $(field).attr("id","address_"+count);
	$(field).insertBefore($("#addMail"));
}

function saveAddress(){
  
}

function updateMailingAddresses(data){
  $("#mailFieldset .data").remove();  
  $.each(data.contactAddresses, function(ind,elt){
    var field = $(document.createElement("div")).addClass("field data address");
    $(field).append($(document.createElement("label")).addClass("viewAddressLbl").append("Mailing Address"));
/*    var button = $(".editAddress").clone().removeClass("template");
    $(field).append(button);*/
    var add1 = $(document.createElement("div")).addClass("addressOne");
    $(add1).text(elt.address.addressOne);
    var add2 = $(document.createElement("div")).addClass("addressTwo");
    $(add2).text(elt.address.addressTwo);
    var add3 = $(document.createElement("div")).addClass("addressThree");
    $(add3).text(elt.address.addressThree);    
    var city = $(document.createElement("span")).addClass("city");
    $(city).text(elt.address.city);    
    var state = $(document.createElement("span")).addClass("state");
    $(state).text(elt.address.state);
    var zip = $(document.createElement("span")).addClass("zipcode");
    $(zip).text(elt.address.zipcode);
            
    $(field).append(add1).append(add2).append(add3).append(city).append(", ").append(state).append(" ").append(zip);    
  	$(field).insertBefore($("#addMail"));  	
  });
}

function cancelNewAddress(el){
  $(el).parent().parent().remove();
}

/*******phones******/
function updatePhones(data){
  $("#phoneFieldset .field").remove();  
  $.each(data.contactPhoneNumbers, function(ind,elt){
    var field = $(document.createElement("div")).addClass("field");
    $(field).append($(document.createElement("label")).append("Phone Number"));
    var span = $(document.createElement("span")).attr("id","phone_"+ind);
    $(span).text(elt.phoneNumber);
    $(field).append(span);    
	  $(field).insertBefore("#addPhoneBtn");  	
  });
}

function addPhoneField(){
  var count = $("#phoneFieldset div.field").length;
  var field = $("#addPhone").clone();
  $(field).removeClass("template").attr("id","phone_"+count).addClass("data");  
	$(field).insertBefore("#addPhoneBtn");
}

function savePhone(){
  
}


