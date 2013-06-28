if (typeof jQuery !== 'undefined') {
    (function($) {
        $('#spinner').ajaxStart(function() {
            $(this).fadeIn();
        }).ajaxStop(function() {
            $(this).fadeOut();
        });
    })(jQuery);
}

function newContact(){
  $("#addContactModalBody").load("/contact/showNewForm", function(){
    $('#addContactModal').modal('show');    
    toggleEdit();           
  });  
}
