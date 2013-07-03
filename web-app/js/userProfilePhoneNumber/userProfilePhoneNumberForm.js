/**
 * Created with IntelliJ IDEA.
 * User: christopher
 * Date: 7/3/13
 * Time: 10:15 AM
 */
$(document).ready(function () {
    var targetContext = "/userProfilePhoneNumber/";     // base context path
    var saveTarget = targetContext + "save"         // create
    var updateTarget = targetContext + "update/"     // update

    var validator = $("#userProfilePhoneNumberForm").validate({
        rules: {
            phoneNumber: {
                required: true,
                minlength: 7
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

    $('#userProfilePhoneNumberModal').on('click', '#userProfilePhoneNumberAddButton', function(e){
        e.preventDefault();
        if($('#userProfilePhoneNumberForm').valid()){
            addPhoneNumber();
        }
    });

    $('#userProfilePhoneNumberModal').on('click', '#userProfilePhoneNumberUpdateButton', function(e){
        e.preventDefault();
        if($('#userProfilePhoneNumberForm').valid()){
            var id = document.forms["userProfilePhoneNumberForm"].elements["id"].value;
            updatePhoneNumber(id);
        }
    });

    /**
     * Adds the User Profile Phone Number
     */
    function addPhoneNumber(){
        $.ajax(
            {
                type:'POST',
                data:JSON.stringify(form2js('userProfilePhoneNumberForm', '.', true)),
                url:saveTarget,
                dataType:'json',
                contentType: "application/json; charset=utf-8",
                success: function(data, textStatus, request){
                    addSuccessHandler(data, textStatus, request);
                },
                error:function(request,textStatus,errorThrown){
                    var data = JSON.parse(request.responseText);

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
        );
    }

    function addSuccessHandler(data){
        $.pnotify({
            title: 'Success',
            text: data.message || "Successfully Saved",
            type: 'success',
            opacity: 0.8,
            delay: 1000,
            history: false
        });

        var addEvent = jQuery.Event("addUserProfilePhoneNumberSuccessfulEvent");
        addEvent.userProfilePhoneNumber = data;
        $("body").trigger(addEvent, [data]);

        $('#userProfilePhoneNumberModalBody').load(targetContext+"form/"+data.id);
    }

    /**
     * Update the User Profile Phone Number
     */
    function updatePhoneNumber(id){
        $.ajax(
            {
                type:'POST',
                data:JSON.stringify(form2js('userProfilePhoneNumberForm', '.', true)),
                url:updateTarget+id,
                dataType:'json',
                contentType: "application/json; charset=utf-8",
                success: function(data, textStatus, request){
                    updateSuccessHandler(data, textStatus, request);
                },
                error:function(request,textStatus,errorThrown){
                    var data = JSON.parse(request.responseText);
                    if(textStatus == '422'){
                        for(i in data.errors){
                            $.pnotify({
                                title: 'Error Saving',
                                text: data.message,
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

    function updateSuccessHandler(data){

        $.pnotify({
            title: 'Success',
            text: data.message || "Saved Successfully",
            type: 'success',
            opacity: 0.8,
            delay: 1000,
            history: false
        });

        var updateEvent = jQuery.Event("updateUserProfilePhoneNumberSuccessfulEvent");
        updateEvent.userProfilePhoneNumber = data;
        $("body").trigger(updateEvent, [data]);
    }



});

