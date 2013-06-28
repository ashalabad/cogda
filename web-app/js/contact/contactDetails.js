function toggleEdit(){
  $(".contactShow").toggleClass("editHide");
  $(".contactEdit").toggleClass("editHide");
  $(".contactShow").toggleClass("editShow");
  $(".contactEdit").toggleClass("editShow");  
}
function saveContactDetails(){
  toggleEdit();
  var contact = new Object();
  //contact.id = $("form").attr("id").replace("contactForm_","");
  contact.title = $("#title").val();
  contact.firstName = $("#firstName").val();
  contact.lastName = $("#lastName").val();  
  contact.middleName = $("#middleName").val();    
  contact.initials = $("#initials").val();      
  contact.jobTitle = $("#jobTitle").val();        
  contact.companyName = $("#companyName").val();          
  contact.website = $("#website").val();              
  
  $.ajax({
      url: "/contact/save/",
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