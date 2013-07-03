/**
 * Created with IntelliJ IDEA.
 * User: christopher
 * Date: 7/1/13
 * Time: 11:09 AM
 * To change this template use File | Settings | File Templates.
 */


$(document).ready(function () {

    var addUserProfileAddressSuccessfulEvent = "addUserProfileAddressSuccessfulEvent";
    var updateUserProfileAddressSuccessfulEvent = "updateUserProfileAddressSuccessfulEvent";
    var validator = $("#userProfileDetailsForm").validate({
        rules: {
            firstName: {
                minlength: 1,
                required: true
            },
            lastName: {
                minlength: 1,
                required: true
            },
            aboutDesc: {
                maxlength: 5000
            },
            businessSpecialtiesDesc: {
                maxlength: 5000
            },
            associationsDesc: {
                maxlength: 5000
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

    // Handles the successful add of new data in the Add Modal window
    $('body').on(addUserProfileAddressSuccessfulEvent, function(event, data) {
        var userProfileAddress = event.userProfileAddress;
        $.ajax({
            type:'GET',
            url:'/userProfileAddress/show/'+userProfileAddress.id,
            dataType: "text"
        }).success(function(data) {

              findFirstAndInsertBefore($('#userProfileAddressContainer'), data);
        });
    });

    $('body').on(updateUserProfileAddressSuccessfulEvent, function(event, data) {
        var userProfileAddress = event.userProfileAddress;
        $.ajax({
            type:'GET',
            url:'/userProfileAddress/show/'+userProfileAddress.id,
            dataType: "text"
        }).success(function(htmlData) {
                // Find the proper UserProfileAddress in the #userProfileAddressContainer
                // and update its contents.
                var elementId = "#userProfileAddress_"+userProfileAddress.id;
                var $updateDiv = $(elementId);
                if($updateDiv.length != 0){
                    $updateDiv.html(htmlData);
                }else{
                    findFirstAndInsertBefore($('#userProfileAddressContainer'), data);
                }
            });
    });

    $('body').on("deleteUserProfileAddressRequestEvent", function(event, data) {
        var id = data.userProfileAddressId;
        deleteUserProfileAddress(id);
    });

    /**
     * Finds the first element in the given container element object
     * and inserts the HTML(data) before this element.
     * If no element is found it will insert the data into the container
     * element object.
     * @param container
     * @param data
     */
    function findFirstAndInsertBefore(container, data){
        var $first = container.children().first();
        if($first.length != 0){
            $(data).insertBefore($first);
        }else{
            container.html(data);
        }
    }

    $('#edit-userProfile').on('click', '.editUserProfileAddressButton', function(event) {
        // handle the click of the Edit User Profile Address Button
        openUserProfileAddressEditModal(event);

    });

    // handle the click on the addUserProfileAddressButton
    $('#addUserProfileAddressButton').click(openUserProfileAddressAddModal);

    function openUserProfileAddressEditModal(event){
        event.preventDefault();
        var editButton = $(event.currentTarget);
        var userProfileAddressId = editButton.data('id');
        retrieveAddressModalForEdit(userProfileAddressId);
    }


    /**
     * Opens the User Profile Address Modal for an Add
     * @param event
     */
    function openUserProfileAddressAddModal(event){
        event.preventDefault();
        var $addButton = $(event.currentTarget);
        var userProfileId = $addButton.data("userprofile-id");

        retrieveAddressModalForAdd(userProfileId);
    }

    /**
     * Retrieves the Address Modal for an Edit based on the passed
     * in userProfileAddressId
     * @param userProfileAddressId
     */
    function retrieveAddressModalForEdit(userProfileAddressId){
        $.ajax(
            {
                type:'POST',
                dataType: "text",  // the data coming back from the server is text or html
                url:'/userProfileAddress/form/'+userProfileAddressId,
                contentType: "application/json; charset=utf-8",
                success: function(data, textStatus, request){
                    $('#userProfileAddressModalBody').html(data);
                    showAddressModal();
                },
                error:function(request, textStatus, errorThrown){
                    $.pnotify({
                        title: 'Error',
                        text: textStatus + " " + errorThrown,
                        type: 'error',
                        opacity: 0.8,
                        delay: 10000
                    });
                }
            }
        );
    }

    function retrieveAddressModalForAdd(userProfileId){
        var obj = new Object();
        obj.userProfile = {id: userProfileId};
        obj.id = null;
        var jsonData = JSON.stringify(obj);

        $.ajax(
            {
                type:'POST',
                dataType: "text",  // the data coming back from the server is text or html
                data:jsonData,
                url:'/userProfileAddress/formAdd',
                contentType: "application/json; charset=utf-8",
                success: function(data, textStatus, request){
                    $('#userProfileAddressModalBody').html(data);
                    showAddressModal();
                },
                error:function(request, textStatus, errorThrown){
                    $.pnotify({
                        title: 'Error',
                        text: textStatus + " " + errorThrown,
                        type: 'error',
                        opacity: 0.8,
                        delay: 10000
                    });
                }
            }
        );
    }

    function showAddressModal(){
        $('#userProfileAddressModal').modal('show');
        $("#userProfileAddressForm :input:visible:enabled:first").focus();
    }

    function hideAddressModal(){
        $('#userProfileAddressModal').modal('hide');
    }

    $('#edit-userProfile').on('click', '.deleteUserProfileAddressButton', function(event) {
        event.preventDefault();
        // handle the click of the Delete User Profile Address Button
        var $deleteButton = $(event.currentTarget);
        var id = $deleteButton.attr("id").replace("userProfileAddressDeleteButton_", "");
        deleteUserProfileAddress(id);
    });

    $('body').on('click', '#userProfileAddressDeleteButton', function(event) {
        event.preventDefault();
        // handle the click of the Delete User Profile Address Button
        var id = document.forms["userProfileAddressForm"].elements["id"].value;
        deleteUserProfileAddress(id);

    });


    /**
     * Delete the User Profile address
     * @param id
     */
    function deleteUserProfileAddress(id){
        if(confirm("Are you sure?")){
            $.ajax(
                {
                    type:'POST',
                    url:'/userProfileAddress/delete/'+id,
                    contentType: "application/json; charset=utf-8",
                    success: function(data, textStatus, request){
                        $('#userProfileAddress_'+id).remove();
                        $('#userProfileAddressModal').modal('hide');
                        successHandler(data, textStatus, request);
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
    }

    function successHandler(data){
        $.pnotify({
            title: 'Success',
            text: data.message,
            type: 'success',
            opacity: 0.8,
            delay: 1000,
            history: false
        });
    }



    // Edit User Details Button
    // $('#editUserProfileDetails')
    // on click show userProfileEditForm
    $('#edit-userProfile').on('click', '#editUserProfileDetails', function(event) {
        event.preventDefault();
        retrieveEditUserProfileDetailsForm();
    });

    // Cancel Editing Button
    // $('#cancelEditUserProfileDetails')
    // on click show userProfileShowForm
    $('#edit-userProfile').on('click', '.userProfileEditFormCancel', function(event) {
        event.preventDefault();
        retrieveShowUserProfileDetailsForm();
    });


    function getUserProfileId(){
        var id = document.forms["userProfileDetailsForm"].elements["id"].value;
        return id;
    }

    function retrieveEditUserProfileDetailsForm(){
        $('#userProfileDetailsFieldset').load("/userProfileManager/editForm/"+getUserProfileId());
    }

    function retrieveShowUserProfileDetailsForm(){
        $('#userProfileDetailsFieldset').load("/userProfileManager/showForm/"+getUserProfileId());
    }

    $('#edit-userProfile').on('click', '#userProfileEditFormSave', function(event) {
        event.preventDefault();
        if($('#userProfileEditFormSave').valid()){
            updateUserProfileDetails(getUserProfileId());
        }
    });

    /**
     * Update the User Profile Address
     */
    function updateUserProfileDetails(id){
        var json = JSON.stringify(form2js('userProfileDetailsForm', '.', true));
        $.ajax(
            {
                type:'POST',
                data:json,
                url:"/userProfileManager/update/"+id,
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

    /**
     * the success handler for the successful update from updateUserProfileDetails
     * @param data
     * @param textStatus
     * @param request
     */
    function updateSuccessHandler(data, textStatus, request){
        $.pnotify({
            title: 'Success',
            text: data.message || "Save Successful",
            type: 'success',
            opacity: 0.8,
            delay: 1000,
            history: false
        });
        retrieveShowUserProfileDetailsForm();
        window.scrollTo(0,0);
    }

/**********************************************************************************************************************
 * User Profile Email Address Stuff
 *
 **********************************************************************************************************************/

    /**
     * handle the delete of a user profile email address
     */
    $('#edit-userProfile').on('click', '.deleteUserProfileEmailAddressButton', function(event) {
        event.preventDefault();
        // handle the click of the Delete User Profile Address Button
        var $deleteButton = $(event.currentTarget);
        var id = $deleteButton.attr("id").replace("userProfileEmailAddressDeleteButton_", "");
        deleteUserProfileEmailAddress(id);
    });

    /**
     * handle the edit request from the container of email addresses
     */
    $('#edit-userProfile').on('click', '.editUserProfileEmailAddressButton', function(event) {
        event.preventDefault();
        // handle the click of the Delete User Profile Address Button
        var $deleteButton = $(event.currentTarget);
        var id = $deleteButton.attr("id").replace("userProfileEmailAddressEditButton_", "");
        retrieveEmailAddressModalForEdit(id);
    });

    /**
     * Handle the Event that is triggered when a new UserProfileEmailAddress is added.
     * addUserProfileEmailAddressSuccessfulEvent
     */
    $('body').on("addUserProfileEmailAddressSuccessfulEvent", function(event, data) {
        var userProfileEmailAddress = event.userProfileEmailAddress;
        $.ajax({
            type:'GET',
            url:'/userProfileEmailAddress/show/'+userProfileEmailAddress.id,
            dataType: "text"
        }).success(function(data) {
            findFirstAndInsertBefore($('#userProfileEmailAddressContainer'), data);
        });
    });

    // updateUserProfileEmailAddressSuccessfulEvent
    $('body').on("updateUserProfileEmailAddressSuccessfulEvent", function(event, data) {
        var userProfileEmailAddress = event.userProfileEmailAddress;
        $.ajax({
            type:'GET',
            url:'/userProfileEmailAddress/show/'+userProfileEmailAddress.id,
            dataType: "text"
        }).success(function(htmlData) {
                // Find the proper UserProfileAddress in the #userProfileEmailAddressContainer
                // and update its contents.
                var elementId = "#userProfileEmailAddress_"+userProfileEmailAddress.id;
                var $updateLi = $(elementId);
                if($updateLi.length != 0){
                    $updateLi.html(htmlData);
                }else{
                    findFirstAndInsertBefore($('#userProfileEmailAddressContainer'), data);
                }
            });
    });

    $('body').on("deleteUserProfileEmailAddressRequestEvent", function(event, data) {
        var id = data.userProfileAddressId;
        deleteUserProfileAddress(id);
        deleteUserProfileEmailAddress(id);

    });

    /**
     * Handle the click on the addUserProfileEmailAddress button
     */
    $('#edit-userProfile').on('click', '#addUserProfileEmailAddress', function(event) {
        event.preventDefault();
        openUserProfileEmailAddressAddModal(event)
    });

    $('body').on('click', '#userProfileEmailAddressDeleteButton', function(event) {
        event.preventDefault();
        // handle the click of the Delete User Profile Address Button
        var id = document.forms["userProfileEmailAddressForm"].elements["id"].value;
        deleteUserProfileEmailAddress(id);
    });

    /**
     * Retrieves the Address Modal for an Edit based on the passed
     * in userProfileAddressId
     * @param userProfileAddressId
     */
    function retrieveEmailAddressModalForEdit(userProfileEmailAddressId){
        $.ajax(
            {
                type:'POST',
                dataType: "text",  // the data coming back from the server is text or html
                url:'/userProfileEmailAddress/form/'+userProfileEmailAddressId,
                contentType: "application/json; charset=utf-8",
                success: function(data, textStatus, request){
                    $('#userProfileEmailAddressModalBody').html(data);
                    showEmailAddressModal();
                },
                error:function(request, textStatus, errorThrown){
                    $.pnotify({
                        title: 'Error',
                        text: textStatus + " " + errorThrown,
                        type: 'error',
                        opacity: 0.8,
                        delay: 10000
                    });
                }
            }
        );
    }

    /**
     * Opens the User Profile Address Modal for an Add
     * @param event
     */
    function openUserProfileEmailAddressAddModal(){
        retrieveEmailAddressModalForAdd(getUserProfileId());
    }

    function retrieveEmailAddressModalForAdd(userProfileId){
        var obj = new Object();
        obj.userProfile = {id: userProfileId};
        obj.id = null;
        var jsonData = JSON.stringify(obj);

        $.ajax(
            {
                type:'POST',
                dataType: "text",  // the data coming back from the server is text or html
                data:jsonData,
                url:'/userProfileEmailAddress/formAdd',
                contentType: "application/json; charset=utf-8",
                success: function(data, textStatus, request){
                    $('#userProfileEmailAddressModalBody').html(data);
                    showEmailAddressModal();
                },
                error:function(request, textStatus, errorThrown){
                    $.pnotify({
                        title: 'Error',
                        text: textStatus + " " + errorThrown,
                        type: 'error',
                        opacity: 0.8,
                        delay: 10000
                    });
                }
            }
        );
    }

    function showEmailAddressModal(){
        $('#userProfileEmailAddressModal').modal('show');
        $("#userProfileEmailAddressForm :input:visible:enabled:first").focus();
    }

    function hideEmailAddressModal(){
        $('#userProfileEmailAddressModal').modal('hide');
    }

    /**
     * Delete the User Profile address
     * @param id
     */
    function deleteUserProfileEmailAddress(id){
        if(confirm("Are you sure?")){
            $.ajax(
                {
                    type:'POST',
                    url:'/userProfileEmailAddress/delete/'+id,
                    contentType: "application/json; charset=utf-8",
                    success: function(data, textStatus, request){
                        $('#userProfileEmailAddress_'+id).remove();
                        $('#userProfileEmailAddressModal').modal('hide');
                        successHandler(data, textStatus, request);
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
    }

});