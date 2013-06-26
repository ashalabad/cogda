$(document).ready(function() {
    $('#suspectList').dataTable(
        {
            "bProcessing": true,
            "sAjaxSource": "/suspect/list",
            "aoColumns": [
                {"mDataProp":"clientId"},
                {"mDataProp":"clientName"},
                {"mDataProp":"businessType"},
                {"mDataProp":"contactName"},
                {"mDataProp":"phoneNumber"},
                {"mDataProp":"email"},
                {"mDataProp":"owner"},
                {"mDataProp":"createdOn"}
            ],
            "sDom": "<'row'<'span6'l><'span6'f>r>t<'row'<'span6'i><'span6'p>>",
            "sPaginationType": "bootstrap",
            "fnRowCallback": function( nRow, aData, iDisplayIndex ) {
                $(nRow).click( function() {
                    $('.row_selected').toggleClass('row_selected');
                    $(this).toggleClass('row_selected');
                });
//                $(nRow).dblclick( function() {
//                    var rowId = $(this).attr("id").replace("row_","");
//                    $.get("/suspect/show/"+rowId, function(data) {
//                        updateContact(JSON.parse(data));
//                        updateEmails(JSON.parse(data));
//                        updateMailingAddresses(JSON.parse(data));
//                        updatePhones(JSON.parse(data));
//                        $('#contactForm').attr("id","contactForm_"+rowId);
//                        $('#contactModal').modal('show');
//                    });
//                });
            }
        });
});