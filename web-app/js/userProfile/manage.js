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


    // Handles the successful add of new data in the Add Modal window
    $('body').on(addUserProfileAddressSuccessfulEvent, function(event, data) {
        var userProfileAddress = event.userProfileAddress;
        $.ajax({
            type:'GET',
            url:'/userProfileAddress/show/'+userProfileAddress.id,
            dataType: "text"
        }).success(function(data) {
//            var $firstDiv = $('#userProfileAddressContainer').children().first();
//            if($firstDiv.length != 0){
//                $(data).insertBefore($firstDiv);
//            }else{
//                $('#userProfileAddressContainer').html(data);
//            }
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


    function openUserProfileAddressAddModal(event){
        event.preventDefault();
        var $addButton = $(event.currentTarget);
        var userProfileId = $addButton.data("userprofile-id");

        retrieveAddressModalForAdd(userProfileId);
    }

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

});