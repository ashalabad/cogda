if (typeof jQuery !== 'undefined') {
    (function($) {
        $('#spinner').ajaxStart(function() {
            $(this).fadeIn();
        }).ajaxStop(function() {
            $(this).fadeOut();
        });
    })(jQuery);
}

//CONTACT FORM: ajax request
$('.submit').click(function(){
//FORM VALIDATION: validates on submit
     $("#contactForm_new").validate({
        submitHandler: function(form) {
               $(form).ajaxSubmit({
                        url:"/contact/save",
                        type:"post",
                        success: function(){
                          alert('success');
                        }
                });
      }
    });  
});


function newContact(){
  $("#addContactModalBody").load("/contact/showNewForm", function(){
    $('#addContactModal').modal('show');    
    toggleEdit();           
  });  
}
