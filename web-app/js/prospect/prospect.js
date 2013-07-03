var oTable;
$(document).ready(function() {
    oTable = $('#prospectList').dataTable(
        {
            "bProcessing": true,
            "sAjaxSource": "/prospect/list",
            "aoColumns": [
                {"mDataProp":"clientId"},
                {"mDataProp":"clientName"},
                {"mDataProp":"businessType"},
                {"mDataProp":"contactName"},
                {"mDataProp":"phoneNumber"},
                {"mDataProp":"email"},
                {"mDataProp":"createdOn"},
                {"mDataProp":"details"},
                {"mDataProp":"edit"}
            ],
            "sDom": "Rlfrtip",//"<'row'<'span6'l><'span6'f>r>t<'row'<'span6'i><'span6'p>>",
            "sPaginationType": "bootstrap",
            "fnRowCallback": function( nRow, aData, iDisplayIndex ) {
                $(nRow).click( function() {
                    $('.row_selected').toggleClass('row_selected');
                    $(this).toggleClass('row_selected');
                });
                $(nRow).dblclick( function() {
                    var rowId = $(this).attr("id").replace("row_","");
                    $('#prospectModalBody').load("/prospect/show/"+rowId, function(data) {
                        $('#prospectModal').modal('show');
                    });
                });
            }
        });

    $('#prospectModal').on('hidden', function () {
        refreshDataTable()
    });
});

function refreshDataTable() {
    oTable.fnReloadAjax()
}

function modalDialogHandler(data) {
    $('#prospectModalBody').html(data)
    $('#prospectModal').modal('show')
}