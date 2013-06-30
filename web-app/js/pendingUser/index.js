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

var oTable;
$(document).ready(function() {
    var anOpen = []; // used to determine whether or not a tr's row details is open
    oTable = $('#pendingUserList').dataTable(
        {
            "bProcessing": true,
            "sAjaxSource": "/pendingUser/list",
            "aoColumns": [
                {
                    "mDataProp": null,
                    "sClass": "control center",
                    "sDefaultContent": '<a class="control"><i class="icon-plus-sign"> </i></a>'
                },
                {"mDataProp":"firstName"},
                {"mDataProp":"lastName"},
                {"mDataProp":"emailAddress"},
                {"mDataProp":"securityRoles"},
                {"mDataProp":"loadedDate"},
                {"mDataProp":"loadedByUsername"},
                {"mDataProp":"onboardedSuccessfully"},
                {"mDataProp":"onboardedDate"},
                {"mDataProp":"pendingUserStatus"},
                {
                    "mData": null,
                    "sClass": "dataTableCheckbox",
                    "mRender": function (data, type, full) {
                        return "<input type='checkbox' name='sendNotification' value='"+full.id+"'/>";
                    }
                }
            ],
            "aoColumnDefs": [
                { "bSortable": false, "bSearchable": false, "aTargets": [ 0, 10 ]}
            ],
            "sDom": "Rlfrtip", //"<'row'<'span6'l><'span6'f>r>t<'row'<'span6'i><'span6'p>>",
            "sPaginationType": "bootstrap",
            "fnRowCallback": function( nRow, aData, iDisplayIndex ) {
                $(nRow).click( function() {
                    $('.row_selected').toggleClass('row_selected');
                    $(this).toggleClass('row_selected');
                });
                $(nRow).dblclick( function() {
                    var rowId = $(this).attr("id").replace("row_","");
                    $('#pendingUserModalBody').load("/pendingUser/form/"+rowId, function(data) {
                        applyValidation();
                        $('#pendingUserModal').modal('show');
                    });
                });
            }
        });
    $('#pendingUserModal').on('hidden', function () {
        refreshDataTable();
    });

    $('#list-pendingUser').on( 'click', '#pendingUserList td.control', function (event) {
        var nTr = this.parentNode;
        var i = $.inArray( nTr, anOpen );
        var $target = $(event.target);
        if ( i === -1 ) {
            $('i', this).removeClass('icon-plus-sign').addClass('icon-minus-sign');

            var nDetailsRow = oTable.fnOpen( nTr, fnFormatDetails(oTable, nTr), 'details' );
            $('div.innerDetails', nDetailsRow).show();
            anOpen.push( nTr );
        }
        else {
            $('i', this).removeClass('icon-minus-sign').addClass('icon-plus-sign');
            oTable.fnClose( nTr );
            anOpen.splice( i, 1 );
        }
    });

    function fnFormatDetails( oTable, nTr )
    {
        var oData = oTable.fnGetData( nTr );
        var sOut =
            '<div class="innerDetails">'+
                '<table class="table table-condensed" >'+
                '<tbody>'+
                '<tr class="info"><td>Notifications:</td><td></td></tr>'+
                '</tbody>'+
                '</table>'+
                '</div>';
        return sOut;
    }

    // Select All Checkbox Implementation
    $('#selectAllCheckbox').change(function(event) {
        var $target = $(event.target);
        if($target.checked){
            // check all available checkbox inputs
            $('#pendingUserList').find('td input[name="sendNotification"]').each(function(i, el){
                $(el, this).prop('checked', $target.prop('checked'));
            });
        }else{
            $('#pendingUserList').find('td input[name="sendNotification"]').each(function(i, el){
                $(el, this).prop('checked', $target.prop('checked'));
            });
        }
    });

    $('#sendNotificationsButton').click(function(){
        var checkedValues = getCheckedRowsValues();
        if(checkedValues.length > 0){
            $.ajax(
                {
                    type:'POST',
                    dataType: "json",
                    data:JSON.stringify(checkedValues),
                    url:'/pendingUser/sendUserInvitations',
                    contentType: "application/json; charset=utf-8",
                    success: successHandler,
                    error:function(XMLHttpRequest,textStatus,errorThrown){
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
        }else{
            $.pnotify({
                title: 'Nothing Selected',
                text: 'Please select at least one record',
                type: 'info',
                opacity: 0.8,
                delay: 1000
            });
        }
    });

    $('#deletePendingUsers').click(function(){
        var checkedValues = getCheckedRowsValues();
        if(checkedValues.length > 0){
            $.ajax(
                {
                    type:'POST',
                    dataType: "json",
                    data:JSON.stringify(checkedValues),
                    url:'/pendingUser/deleteUsers',
                    contentType: "application/json; charset=utf-8",
                    success: deleteSuccessHandler,
                    error:function(XMLHttpRequest,textStatus,errorThrown){
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
        }else{
            $.pnotify({
                title: 'Nothing Selected',
                text: 'Please select at least one record',
                type: 'info',
                opacity: 0.8,
                delay: 1000
            });
        }
    });

    $('#pendingUserModal').on('click', '#pendingUserUpdateButton', function(e){
        e.preventDefault();
        if($('#pendingUserForm').valid()){
            $.ajax(
                {
                    type:'POST',
                    data:JSON.stringify($('#pendingUserForm').serializeObject()),
                    url:'/pendingUser/update/'+$('#id').val(),
                    dataType:'json',
                    contentType: "application/json; charset=utf-8",
                    success: function(data, textStatus, request){
                        successHandler(data, textStatus, request);
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

    $('#pendingUserModal').on('click', '#pendingUserDeleteButton', function(e){
        e.preventDefault();
        var id = $('#id');
        if(confirm("Are you sure?")){
            deletePendingUser(id);
        }
    });

    function deletePendingUser(id){
        $.ajax(
            {
                type:'POST',
                url:'/pendingUser/delete/'+$('#id').val(),
                dataType:'json',
                contentType: "application/json; charset=utf-8",
                success: function(data, textStatus, request){
                    deleteSuccessHandler(data, textStatus, request);
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

    function applyValidation(){
        $("#pendingUserForm").validate({
            rules: {
                firstName: {
                    minlength: 1,
                    required: true
                },
                lastName: {
                    minlength: 1,
                    required: true
                },
                emailAddress: {
                    required: true,
                    email: true
                }
            },
            highlight: function(element) {
                $(element).closest('.control-group').removeClass('success').addClass('error');
            },
            success: function(element) {
                element.closest('.control-group').removeClass('error').addClass('success');
            }
        });
    }

    function getCheckedRowsValues(){
        var checkedValues = [];
        $('#pendingUserList').find('td input[name="sendNotification"]').each(function(i, el){
            if($(el, this).prop('checked')){
                checkedValues.push( $(el, this).val() );
            }
        });
        return checkedValues;
    }

    function deleteSuccessHandler(data){
        $('#pendingUserModal').modal('hide');
        refreshDataTable();
        successHandler(data);
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
});



function refreshDataTable() {
    oTable.fnReloadAjax();
}

/*******Imported User *****/
//function saveImportedUser(){
//    toggleEdit();
//    var pendingUser = new Object();
//    pendingUser.id = $("form").attr("id").replace("pendingUserForm_","");
//    pendingUser.firstName = $("#firstName").val();
//    pendingUser.lastName = $("#lastName").val();
//    pendingUser.emailAddress = $("#emailAddress").val();
//    pendingUser.securityRoles = $("#securityRoles").val();
//    $.ajax({
//        url: "/pendingUser/update/"+pendingUser.id,
//        type: "post",
//        dataType: "json",
//        data: JSON.stringify(pendingUser),
//        contentType: "application/json; charset=utf-8"
//    });
//}
//
//function updateImportedUser(data){
//    $(".firstName").text(data.firstName).val(data.firstName);
//    $(".lastName").text(data.lastName).val(data.lastName);
//    $(".emailAddress").text(data.initials).val(data.emailAddress);
//    $(".securityRoles").text(data.securityRoles).val(data.securityRoles);
//}