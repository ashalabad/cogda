/**
 * Created with IntelliJ IDEA.
 * User: christopher
 * Date: 7/3/13
 * Time: 10:15 AM
 */
$(document).ready(function () {
    var targetContext = "/userProfileEmailAddress/";     // base context path
    var saveTarget = targetContext + "save"         // create
    var updateTarget = targetContext + "update/"     // update

    var validator = $("#userProfileEmailAddressForm").validate({
        rules: {
            emailAddress: {
                email: true,
                required: true
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

    $('#userProfileEmailAddressModal').on('click', '#userProfileEmailAddressAddButton', function(e){
        e.preventDefault();
        if($('#userProfileEmailAddressForm').valid()){
            addEmailAddress();
        }
    });

    $('#userProfileEmailAddressModal').on('click', '#userProfileEmailAddressUpdateButton', function(e){
        e.preventDefault();
        if($('#userProfileEmailAddressForm').valid()){
            var id = document.forms["userProfileEmailAddressForm"].elements["id"].value;
            updateEmailAddress(id);
        }
    });

    /**
     * Adds the User Profile Address
     */
    function addEmailAddress(){
        $.ajax(
            {
                type:'POST',
                data:JSON.stringify(form2js('userProfileEmailAddressForm', '.', true)),
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

        var addEvent = jQuery.Event("addUserProfileEmailAddressSuccessfulEvent");
        addEvent.userProfileEmailAddress = data;
        $("body").trigger(addEvent, [data]);

        $('#userProfileEmailAddressModalBody').load(targetContext+"form/"+data.id);
    }

    /**
     * Update the User Profile Address
     */
    function updateEmailAddress(id){
        $.ajax(
            {
                type:'POST',
                data:JSON.stringify(form2js('userProfileEmailAddressForm', '.', true)),
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

        var updateEvent = jQuery.Event("updateUserProfileEmailAddressSuccessfulEvent");
        updateEvent.userProfileEmailAddress = data;
        $("body").trigger(updateEvent, [data]);
    }



});
