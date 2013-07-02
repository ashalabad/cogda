/**
 * Created with IntelliJ IDEA.
 * User: christopher
 * Date: 7/1/13
 * Time: 12:14 PM
 * To change this template use File | Settings | File Templates.
 */
$(document).ready(function () {
    var targetContext = "/userProfileAddress/";     // base context path
    var saveTarget = targetContext + "save"         // create
    var updateTarget = targetContext + "update/"     // update



    var validator = $("#userProfileAddressForm").validate({
        rules: {
            streetAddressOne: {
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

    $('#userProfileAddressModal').on('click', '#userProfileAddressAddButton', function(e){
        e.preventDefault();
        if($('#userProfileAddressForm').valid()){
            addAddress();
        }
    });

    $('#userProfileAddressModal').on('click', '#userProfileAddressUpdateButton', function(e){
        e.preventDefault();
        if($('#userProfileAddressForm').valid()){
            var id = document.forms["userProfileAddressForm"].elements["id"].value;
            updateAddress(id);
        }
    });

    /**
     * Adds the User Profile Address
     */
    function addAddress(){
        $.ajax(
            {
                type:'POST',
                data:JSON.stringify(form2js('userProfileAddressForm', '.', true)),
                url:saveTarget,
                dataType:'json',
                contentType: "application/json; charset=utf-8",
                success: function(data, textStatus, request){
                    addSuccessHandler(data, textStatus, request);

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

    function addSuccessHandler(data){
        $.pnotify({
            title: 'Success',
            text: data.message,
            type: 'success',
            opacity: 0.8,
            delay: 1000,
            history: false
        });

        var addEvent = jQuery.Event("addUserProfileAddressSuccessfulEvent");
        addEvent.userProfileAddress = data;
        $("body").trigger(addEvent, [data]);

        $('#userProfileAddressModalBody').load(targetContext+"form/"+data.id);
    }

    /**
     * Update the User Profile Address
     */
    function updateAddress(id){
        $.ajax(
            {
                type:'POST',
                data:JSON.stringify(form2js('userProfileAddressForm', '.', true)),
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
            text: data.message,
            type: 'success',
            opacity: 0.8,
            delay: 1000,
            history: false
        });

        var updateEvent = jQuery.Event("updateUserProfileAddressSuccessfulEvent");
        updateEvent.userProfileAddress = data;
        $("body").trigger(updateEvent, [data]);
    }



});
