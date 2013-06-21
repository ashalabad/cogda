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

function toggleEdit(){
  $(".contactShow").toggleClass("editHide");
  $(".contactEdit").toggleClass("editHide");
  $(".contactShow").toggleClass("editShow");
  $(".contactEdit").toggleClass("editShow");  
}
function saveContact(){
  toggleEdit();
  var contact = new Object();
  contact.id = $("form").attr("id").replace("contactForm_","");
  contact.firstName = $("#firstName").val();
  contact.lastName = $("#lastName").val();  
  contact.middleName = $("#middleName").val();    
  contact.initials = $("#initials").val();      
  contact.jobTitle = $("#jobtitle").val();        
  contact.companyName = $("#companyName").val();          
  contact.website = $("#website").val();            
  $.post("/contact/update", JSON.stringify(contact), function(data){updateContact(data)});
}


function addPhoneField(){
  var count = $("#phoneFieldset div.field").length;  
  var field = $(document.createElement("div")).addClass("field");
  $(field).append($(document.createElement("label")).append("Phone"));
  $(field).append($(document.createElement("input")).attr("id","phone_"+count).val("").attr("type","text"));  
	$("#phoneFieldset").prepend($(field));
}

function updateContact(data){
  $(".title").text(data.modelObject.title).val(data.modelObject.title);                    
  $(".firstName").text(data.modelObject.firstName).val(data.modelObject.firstName);  
  $(".middleName").text(data.modelObject.middleName).val(data.modelObject.middleName); 
  $(".lastName").text(data.modelObject.lastName).val(data.modelObject.lastName);       
  $(".initials").text(data.modelObject.initials).val(data.modelObject.initials);                      
  $(".gender").text(data.modelObject.gender);                      
  $(".companyName").text(data.modelObject.companyName).val(data.modelObject.companyName);                    
  $(".jobTitle").text(data.modelObject.jobTitle).val(data.modelObject.jobTitle);  
  $(".website").text(data.modelObject.website).val(data.modelObject.website);    
}

/******emails******/
function updateEmails(data){
  $("#emailFieldset .field").remove();
  $.each(data.modelObject.contactEmailAddresses, function(ind,elt){
    var field = $(document.createElement("div")).addClass("field");
    $(field).append($(document.createElement("label")).append("Email Address"));
    var span = $(document.createElement("span")).attr("id","emailAddress_"+ind);
    $(span).text(elt.emailAddress);
    $(field).append(span);    
  	$("#emailFieldset").prepend($(field));
  });
}

function addEmailAddressField(){
  var count = $("#emailFieldset div.field").length;
  var field = $(document.createElement("div")).addClass("field");
  $(field).append($(document.createElement("label")).append("Email Address"));
  $(field).append($(document.createElement("input")).attr("id","emailAddress_"+count).val("").attr("type","text").addClass("input-xlarge"));
  var saveBtn = $(".saveEmail.template").clone();
  $(saveBtn).removeClass("template");
  $(saveBtn).appendTo($(field));
	$("#emailFieldset").prepend($(field));
	$(field).insertBefore("#addEmail");
}

function saveEmail(){
  // ajax get to email update
  // on success updateEmails
}

/*******addresses*******/
function addMailingAddressField(){
  var count = $("#mailFieldset div.field").length;  
  var field = $(".address.template").clone();
  $(field).removeClass("template");
  $(field).show();
  $(field).attr("visibility","visible");
  $(field).attr("id","address_"+count);
	$(field).insertBefore($("#addMail"));
}

function saveAddress(){
  
}

function updateMailingAddresses(data){
  $("#mailFieldset .data").remove();  
  $.each(data.modelObject.contactAddresses, function(ind,elt){
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
  	$("#mailFieldset").prepend($(field));
  });
}

function cancelNewAddress(el){
  $(el).parent().parent().remove();
}

/*******phones******/
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