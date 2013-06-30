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

/**
 * Serialize a form object with this method:
 * e.g. JSON.stringify($('#pendingUserForm').serializeObject())
 * @returns {{}}
 */
$.fn.serializeObject = function()
{
    var o = {};
    var a = this.serializeArray();
    $.each(a, function() {
        if (o[this.name] !== undefined) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};

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
