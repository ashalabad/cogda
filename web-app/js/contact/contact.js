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
                $("#contactModalBody").load("/contact/showForm/"+rowId, function(){
                  $('#contactModal').modal('show');               
                });
            });
        }                
    });
});

function toggleEdit(){
  $(".contactShow").toggleClass("editHide");
  $(".contactEdit").toggleClass("editHide");
  $(".contactShow").toggleClass("editShow");
  $(".contactEdit").toggleClass("editShow");  
}

/*******contact details*****/
function saveContactDetails(){
  toggleEdit();             
  var id = $("form").attr("id").replace("contactForm_","");
  var contact = buildContactForSave(id);
    
  $.ajax({
      url: "/contact/update/"+contact.id,
      type: "post",
      dataType: "json",
      data: JSON.stringify(contact),
      success: updateContact,
      contentType: "application/json; charset=utf-8"
  });
}

function saveNewContact(){
  toggleEdit();
  var contact = buildContactForSave(''); 
  
  if($.trim($("#contactPhoneNumber").val()).length > 0){
    contact.contactEmailAddresses = [];
    var emailAddress = new Object();    
    emailAddress.emailAddress = $("#contactEmailAddress").val();
    emailAddress.primaryEmailAddress = true;
    contact.contactEmailAddresses.push(emailAddress);               
  }
  
  if($.trim($("#contactPhoneNumber").val()).length > 0){
    contact.contactPhoneNumbers = [];
    var phoneNumber = new Object();    
    phoneNumber.phoneNumber = $("#contactPhoneNumber").val();
    phoneNumber.primaryPhoneNumber = true;
    contact.contactPhoneNumbers.push(phoneNumber);    
  }

  $('#addContactModal').modal('hide');      
  
  $.ajax({
      url: "/contact/save/",
      type: "post",
      dataType: "json",
      data: JSON.stringify(contact),
      success: showEdit,
      contentType: "application/json; charset=utf-8"
  });
}

function buildContactForSave(id){
  var contact = new Object();
  if(id != ''){
    contact.id = id;
  }
  contact.title = $("#title").val();
  contact.firstName = $("#firstName").val();
  contact.lastName = $("#lastName").val();  
  contact.middleName = $("#middleName").val();    
  contact.initials = $("#initials").val();      
  contact.jobTitle = $("#jobTitle").val();        
  contact.companyName = $("#companyName").val();          
  contact.website = $("#website").val();
  return contact;
}

function showEdit(data){
  $("#contactModalBody").empty();
  $("#contactModalBody").load("/contact/showForm/"+data.id, function(){
    $('#contactModal').modal('show');               
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

function editSaved(event){
  var id = $(event.currentTarget).parent().attr("id");
  $("#"+id+" .showMode").toggleClass("showMe").toggleClass("hideMe");
  $("#"+id+" .editMode").toggleClass("showMe").toggleClass("hideMe");
}

function showMode(event){
  var id = $(event.currentTarget).parent().parent().attr("id");
  $("#"+id+" .showMode").toggleClass("showMe").toggleClass("hideMe");
  $("#"+id+" .editMode").toggleClass("showMe").toggleClass("hideMe");
}

/******emails******/
function updateEmail(data){
  var field = $("#editEmail").clone().removeClass("template").addClass("data");
  if(data.primaryEmailAddress == true){
    var radio = $(field).find(".primaryRadio");
    $(radio).attr("checked","checked");
  }
  $(field).attr("id","editEmail_"+data.id);
  $(field).find(".savedText").text(data.emailAddress);
  $(field).find(".emailInput").val(data.emailAddress).attr("id","emailAddress_"+data.id);    
  
  if($("#editEmail_"+data.id).length > 0){
    var toRemove = $("#editEmail_"+data.id);
    $(field).insertBefore(toRemove);
    toRemove.remove();
  }else{
    $(field).insertBefore($("#emailFieldset .new")[0]);    
    $("#emailFieldset .new")[0].remove();
  }
}

function addEmailAddressField(){
  var count = $("#emailFieldset div.field").length;
  var field = $("#addEmail").clone();
  $(field).removeClass("template").attr("id","editEmail_"+count).addClass("data");  
  $(field).removeClass("template").attr("id","editEmail_"+count).addClass("data");    
  var btn = $("#addEmailBtn");
	$(field).insertBefore("#addEmailBtn");
}

function saveEmail(){
  var email = new Object();
  var route = '';
  if($(event.currentTarget).parent().hasClass("new")){
    email.emailAddress = $("#"+$(event.currentTarget).parent().attr("id")+" .emailInput").val();
    email.primaryEmailAddress = false;
    route = 'save';
    email.contact = {};
    email.contact.id = $("form").attr("id").replace("contactForm_","");    
  }else{
    var input;
    if ($(event.currentTarget).hasClass("primaryRadio")){
      input = $(event.currentTarget).parent().find('.emailInput');      
      email.primaryEmailAddress = $(event.currentTarget).is(":checked");
    }else{
      input = $(event.currentTarget).prev();      
      email.primaryEmailAddress = $("#"+$(event.currentTarget).parent().parent().attr("id")+" .primaryRadio").is(":checked");
    }
    email.id = $(input).attr("id").replace("emailAddress_","");
    email.emailAddress = $(input).val();
    route = 'update/'+email.id;
  }

  $.ajax({
    url: "/contactEmailAddress/"+route,
    type: "post",
    dataType: "json",
    data: JSON.stringify(email),
    success: updateEmail,
    contentType: "application/json; charset=utf-8"
  });  
}

/*******addresses*******/
function addMailingAddressField(){
  var count = $("#mailFieldset div.field").length;  
  var field = $("#addAddress").clone();
  $(field).removeClass("template").addClass("data");
  $(field).show();
  $(field).attr("visibility","visible");
  $(field).attr("id","address_"+count);
	$(field).insertBefore($("#addMail"));
}

function saveAddress(){
  var data = {};
  var route;
  var elt;
  if($(event.currentTarget).hasClass("primaryRadio")){
    elt = $(event.currentTarget).parent();  
    data.primaryAddress = $(event.currentTarget).is(":checked");    
  }else{
    elt = $(event.currentTarget).parent().parent();  
    data.primaryAddress = $(elt).find(".primaryRadio").is(":checked");
  }
  
  if($(elt).hasClass("new")){
     data.primaryAddress = false;  
     route = "save";
  }else{
    data.id = $(elt).attr("id").replace("editAddress_","");  
    route = "update/"+data.id;    
  }
  
  data.address = {};
  data.address.addressOne = $(elt).find("input.addressOne").val();
  data.address.addressTwo = $(elt).find("input.addressTwo").val();
  data.address.addressThree = $(elt).find("input.addressThree").val();          
  data.address.city = $(elt).find("input.city").val();          
  data.address.state = $(elt).find("select.state").val();               
  data.address.zipcode = $(elt).find("input.zipcode").val();
  data.contact = {};
  data.contact.id = $("form").attr("id").replace("contactForm_","");

  $.ajax({
    url: "/contactAddress/"+route,
    type: "post",
    dataType: "json",
    data: JSON.stringify(data),
    success: updateAddress,
    contentType: "application/json; charset=utf-8"
  });  
}

function updateAddress(data){
  var field = $("#editAddress").clone().removeClass("template").addClass("data");
  if(data.primaryAddress == true){
    var radio = $(field).find(".primaryRadio");
    $(radio).attr("checked","checked");
  }
  $(field).attr("id","editAddress_"+data.id);
  $(field).find("div.addressOne").text(data.address.addressOne);
  $(field).find("input.addressOne").val(data.address.addressOne);    
  $(field).find("div.addressTwo").text(data.address.addressTwo);
  $(field).find("input.addressTwo").val(data.address.addressTwo);
  $(field).find("div.addressThree").text(data.address.addressThree);
  $(field).find("input.addressThree").val(data.address.addressThree);
  $(field).find("span.city").text(data.address.city);
  $(field).find("input.city").val(data.address.city);
  $(field).find("span.state").text(data.address.state);
  $(field).find("select.state").val(data.address.state);
  $(field).find("span.zipcode").text(data.address.zipcode);
  $(field).find("input.zipcode").val(data.address.zipcode);          
  
  // if there is already a block with that id, that's the one we saved. 
  // insert the new data before it and remove the old one.
  if($("#editAddress_"+data.id).length > 0){
    var toRemove = $("#editAddress_"+data.id);
    $(field).insertBefore(toRemove);
    toRemove.remove();    
  // else, this was a new address, so append to the bottom and remove the new field (okay, this is not perfect...)
  }else{
    $(field).insertBefore($("#mailFieldset .new")[0]);    
    $("#mailFieldset .new")[0].remove();
  }  
}

function cancelNewAddress(el){
  $(el).parent().parent().remove();
}

/*******phones******/
function addPhoneField(){
  var count = $("#phoneFieldset div.field").length;
  var field = $("#addPhone").clone();
  $(field).removeClass("template").attr("id","phone_"+count).addClass("data");  
	$(field).insertBefore("#addPhoneBtn");
}

function savePhones(){
  var contact = new Object();
  contact.id = $("form").attr("id").replace("contactForm_","");
  contact.contactPhoneNumbers = [];
  
  $("#phoneFieldset .phoneInput").each(function(ind, elt){
    var phone = new Object();
    if($(elt).attr("id").indexOf("_") < 0){
      phone.phoneNumber = $(elt).val();
      phone.primaryPhoneNumber = false;      
    }else{
      var phoneId = $(elt).attr("id").replace("phone_","");
      phone.id = phoneId;
      phone.phoneNumber = $(elt).val();
      phone.primaryPhoneNumber = false;      
    }
    contact.contactPhoneNumbers.push(phone);
  });
  saveContact(contact,updatePhones);
}

function savePhone(){
  var phone = new Object();
  var route = '';
  if($(event.currentTarget).parent().hasClass("new")){
    phone.phoneNumber = $("#"+$(event.currentTarget).parent().attr("id")+" .phoneInput").val();
    phone.primaryPhoneNumber = false;
    route = 'save';
    phone.contact = {};
    phone.contact.id = $("form").attr("id").replace("contactForm_","");
  }else{
    var input;
    if ($(event.currentTarget).hasClass("primaryRadio")){
      input = $(event.currentTarget).parent().find('.phoneInput');      
      phone.primaryPhoneNumber = $(event.currentTarget).is(":checked");
    }else{
      input = $(event.currentTarget).prev();      
      phone.primaryPhoneNumber = $("#"+$(event.currentTarget).parent().parent().attr("id")+" .primaryRadio").is(":checked");
    }
    phone.id = $(input).attr("id").replace("phone_","");
    phone.phoneNumber = $(input).val();
    route = 'update/'+phone.id;
  }

  $.ajax({
    url: "/contactPhoneNumber/"+route,
    type: "post",
    dataType: "json",
    data: JSON.stringify(phone),
    success: updatePhone,
    contentType: "application/json; charset=utf-8"
  });  
}

function updatePhone(data){
  var field = $("#editPhone").clone().removeClass("template").addClass("data");
  if(data.primaryPhoneNumber == true){
    var radio = $(field).find(".primaryRadio");
    $(radio).attr("checked","checked");
  }
  $(field).attr("id","editPhone_"+data.id);
  $(field).find(".savedText").text(data.phoneNumber);
  $(field).find(".phoneInput").val(data.phoneNumber).attr("id","phone_"+data.id);    
  
  if($("#editPhone_"+data.id).length > 0){
    var toRemove = $("#editPhone_"+data.id);
    $(field).insertBefore(toRemove);
    toRemove.remove();    
  }else{
    $(field).insertBefore($("#phoneFieldset .new")[0]);    
    $("#phoneFieldset .new")[0].remove();
  }
}

