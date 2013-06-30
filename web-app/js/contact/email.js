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
