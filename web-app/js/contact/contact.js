var newValidator;
var updateValidator;
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
                  updateValidator = $("#contactForm_"+rowId).validate({
                     rules: {
                         firstName: {
                             minlength: 1,
                             required: true
                         },
                         lastName: {
                             minlength: 1,
                             required: true
                         },
                         website: {
                           url: true
                         },
                         emailAddress: {
                           email: true,
                           required: true
                         },
                         phoneNumber: {
                           required: true,
                           phoneUS: true
                         }
                     }                
                   });                             
                });
            });
        }                
    });  
      
    newValidator = $("#contactForm_new").validate({
       rules: {
           firstName: {
               minlength: 1,
               required: true
           },
           lastName: {
               minlength: 1,
               required: true
           },
           website: {
             url: true
           },
           contactEmailAddress: {
             email: true
           }
       }                
     });   
});

function displayMessages(data){
  $('#messages').show().html(data.message);
  $.pnotify({
      title: data.messageTitle,
      text: data.message,
      type: 'success',
      opacity: 0.8,
      delay: 10000,
      history:false
  });  
}

function displayErrors(request,textStatus,errorThrown){
    var data = JSON.parse(request.responseText);
    if(textStatus == '422'){
        for(i in data.errors){
            $.pnotify({
                title: 'Error Saving',
                text: data.errors[i],
                type: 'error',
                opacity: 0.8,
                delay: 4000,
                history:false
            });
        }
    }
}

function toggleEdit(){
  $(".contactShow").toggleClass("editHide");
  $(".contactEdit").toggleClass("editHide");
  $(".contactShow").toggleClass("editShow");
  $(".contactEdit").toggleClass("editShow");  
}

/*******contact details*****/
function saveContactDetails(){         
  var id = $("form").attr("id").replace("contactForm_","");
  var contact = buildContactForSave(id);
  
  if( $("#contactForm_"+id+" [name='firstName']").valid() &&
    $("#contactForm_"+id+" [name='lastName']").valid() &&
    $("#contactForm_"+id+" [name='website']").valid() ){
    toggleEdit();          
    $.ajax({
        url: "/contact/update/"+contact.id,
        type: "post",
        dataType: "json",
        data: JSON.stringify(contact),
        success: updateContact,
        error: displayErrors,
        contentType: "application/json; charset=utf-8"
    });    
  }
}

function saveNewContact(){
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

  if( $("#contactForm_new [name='firstName']").valid() &&
    $("#contactForm_new [name='lastName']").valid() &&
    $("#contactForm_new [name='website']").valid() &&
    $("#contactForm_new [name='contactEmailAddress']").valid() ){     

    $('#addContactModal').modal('hide');      
  
    $.ajax({
        url: "/contact/save/",
        type: "post",
        dataType: "json",
        data: JSON.stringify(contact),
        success: showEdit,
        error: displayErrors,        
        contentType: "application/json; charset=utf-8"
    });
  }
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
  displayMessages(data);
  $("#contactModalBody").empty();
  $("#contactModalBody").load("/contact/showForm/"+data.id, function(){
    $('#contactModal').modal('show');    
    updateValidator = $("#contactForm_"+data.id).validate({
       rules: {
           firstName: {
               minlength: 1,
               required: true
           },
           lastName: {
               minlength: 1,
               required: true
           },
           website: {
             url: true
           },
           emailAddress: {
             email: true,
             required: true
           },
           phoneNumber: {
             required: true,
             phoneUS: true
           }
       }                
     });               
  });  
}

function updateContact(data){
  displayMessages(data);  
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
  displayMessages(data);
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
  var emailInput;
  if($(event.currentTarget).hasClass("primaryRadio")){
    emailInput = $(event.currentTarget).parent().find('.emailInput');   
  }else{
    emailInput = $(event.currentTarget).prevAll("[name='emailAddress']");    
  }
  
  if( $(emailInput).valid()){
    var email = new Object();
    var route = '';
    if($(event.currentTarget).parent().hasClass("new")){
      email.emailAddress = $("#"+$(event.currentTarget).parent().attr("id")+" .emailInput").val();
      email.primaryEmailAddress = false;
      route = 'save';
      email.contact = {};
      email.contact.id = $("form").attr("id").replace("contactForm_","");    
    }else{
      if ($(event.currentTarget).hasClass("primaryRadio")){  
        email.primaryEmailAddress = $(event.currentTarget).is(":checked");
      }else{
        email.primaryEmailAddress = $("#"+$(event.currentTarget).parent().parent().attr("id")+" .primaryRadio").is(":checked");
      }
      email.id = $(emailInput).attr("id").replace("emailAddress_","");
      email.emailAddress = $(emailInput).val();
      route = 'update/'+email.id;
    }

    $.ajax({
      url: "/contactEmailAddress/"+route,
      type: "post",
      dataType: "json",
      data: JSON.stringify(email),
      success: updateEmail,
      error: displayErrors,      
      contentType: "application/json; charset=utf-8"
    });    
  }
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
    error: displayErrors,    
    contentType: "application/json; charset=utf-8"
  });  
}

function updateAddress(data){
  displayMessages(data);
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

function savePhone(){
  var phoneInput;
  if($(event.currentTarget).hasClass("primaryRadio")){
    phoneInput = $(event.currentTarget).parent().find('.phoneInput');   
  }else{
    phoneInput = $(event.currentTarget).prevAll("[name='phoneNumber']");    
  }
  
  if( $(phoneInput).valid()){  
    var phone = new Object();
    var route = '';
    if($(event.currentTarget).parent().hasClass("new")){
      phone.phoneNumber = $("#"+$(event.currentTarget).parent().attr("id")+" .phoneInput").val();
      phone.primaryPhoneNumber = false;
      route = 'save';
      phone.contact = {};
      phone.contact.id = $("form").attr("id").replace("contactForm_","");
    }else{
      if ($(event.currentTarget).hasClass("primaryRadio")){ 
        phone.primaryPhoneNumber = $(event.currentTarget).is(":checked");
      }else{
        phone.primaryPhoneNumber = $("#"+$(event.currentTarget).parent().parent().attr("id")+" .primaryRadio").is(":checked");
      }
      phone.id = $(phoneInput).attr("id").replace("phone_","");
      phone.phoneNumber = $(phoneInput).val();
      route = 'update/'+phone.id;
    }

    $.ajax({
      url: "/contactPhoneNumber/"+route,
      type: "post",
      dataType: "json",
      data: JSON.stringify(phone),
      success: updatePhone,
      error: displayErrors,      
      contentType: "application/json; charset=utf-8"
    });  
  }
}

function updatePhone(data){
  displayMessages(data);
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

