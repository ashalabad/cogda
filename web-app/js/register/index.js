/**
 * Created with IntelliJ IDEA.
 * User: christopher
 * Date: 6/3/13
 * Time: 3:20 PM
 * To change this template use File | Settings | File Templates.
 * JavaScript associated with the register/index screen
 */

$(document).ready(function(){
    $("#registerForm").validate({
        rules: {
            firstName: {
                minlength: 1,
                required: true
            },
            lastName: {
                minlength: 1,
                required: true
            },
            username: {
                minlength: 3,
                required: true
            }
        },
        highlight: function(element) {
            $(element).closest('.control-group').removeClass('success').addClass('error');
        },
        success: function(element) {
            element.text('OK!').addClass('valid')
                .closest('.control-group').removeClass('error').addClass('success');
        },
        submitHandler: function(form) {
            $(form).ajaxSubmit();
        }
    });
});
