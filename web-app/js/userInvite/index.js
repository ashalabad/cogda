$(document).ready(function () {

    $.validator.addMethod("usernameRegex", function(value, element) {
        return this.optional(element) || /^[a-z0-9]+$/i.test(value);
    }, "Username must be letters or numbers with no spaces or special characters.");

    var validator = $("#userInviteForm").validate({
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
                minlength: 2,
                required: true,
                usernameRegex: true,
                remote: {
                    url:'/userInvite/availableUsername',
                    type: "POST",
                    data: {
                        username: function() {
                            return $("#username").val();
                        }
                    }
                }
            },
            emailAddress: {
                required: true,
                email: true
            },
            password: {
                minlength: 6,
                maxlength: 84,
                required: true
            },
            passwordTwo: {
                required: true,
                equalTo: "#password"
            }
        },
        highlight: function(element) {
            $(element).closest('.control-group').removeClass('success').addClass('error');
        },
        success: function(element) {
            element.text('OK!').addClass('valid')
                .closest('.control-group').removeClass('error').addClass('success');
        }
    });

    $('#userInviteForm').on("submit", function(e){
        e.preventDefault();
        if(validator.valid()){
            $.ajax(
                {
                    type:'POST',
                    data:$(this).serialize(),
                    url:'/userInvite/save',
                    success: function(data, textStatus, request){
                        inviteHandler(data, textStatus, request);
                    },
                    error:function(request,textStatus,errorThrown){
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
                }
            );
        }
    });

    function inviteHandler(data, textStatus, request){

        $('#messages').show().html(data.message);
        $.pnotify({
            title: data.messageTitle,
            text: data.message,
            type: 'success',
            opacity: 0.8,
            delay: 10000,
            history:false
        });
        console.log("textStatus: " + textStatus);
        console.log("HEADER LINK" + request.getResponseHeader("Location"));
        $('#pageTitle').hide();
        $('#userInviteDiv').load('/userInvite/success');
        window.scrollTo(0,0);
    }

    document.getElementById("firstName").focus();
});

