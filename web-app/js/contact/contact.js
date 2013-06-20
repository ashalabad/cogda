$(document).ready(function() {
    $('#contactList').dataTable(
    {
        "bProcessing": true,
        "sAjaxSource": "contact/list",
        "aoColumns": [
            {"mDataProp":"companyName"},
            {"mDataProp":"lastName"},
            {"mDataProp":"firstName"},
            {"mDataProp":"jobTitle"},
            {"mDataProp":"primaryEmailAddress"}
        ],
        "sDom": "<'row'<'span6'l><'span6'f>r>t<'row'<'span6'i><'span6'p>>",
        "sPaginationType": "bootstrap",
        "fnRowCallback": function( nRow, aData, iDisplayIndex ) {
            $(nRow).click( function() {
                $('.row_selected').toggleClass('row_selected');
                $(this).toggleClass('row_selected');
            });
            $(nRow).dblclick( function() {
                $.get("contact/get/"+$(this).attr("id").replace("row_",""), function(data) {
                    $("#firstName").val(data.modelObject.firstName);
                    $("#lastName").val(data.modelObject.lastName);
                    $("#companyName").val(data.modelObject.companyName);
                    $("#jobTitle").val(data.modelObject.jobTitle);                    
                    $('#test_modal').modal('show');
                });
            });
        }                
    });
});