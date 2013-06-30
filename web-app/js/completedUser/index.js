/**
 * Created with IntelliJ IDEA.
 * User: christopher
 * Date: 6/29/13
 * Time: 8:18 PM
 * To change this template use File | Settings | File Templates.
 */
var oTable;
$(document).ready(function() {
    var anOpen = []; // used to determine whether or not a tr's row details is open
    oTable = $('#completedUserList').dataTable(
        {
            "bProcessing": true,
            "sAjaxSource": "/completedUser/dlist",
            "aoColumns": [
                {"mDataProp":"firstName"},
                {"mDataProp":"lastName"},
                {"mDataProp":"emailAddress"},
                {"mDataProp":"securityRoles"},
                {"mDataProp":"loadedDate"},
                {"mDataProp":"loadedByUsername"},
                {"mDataProp":"onboardedSuccessfully"},
                {"mDataProp":"onboardedDate"},
                {"mDataProp":"pendingUserStatus"},
                {"mDataProp":"username"}
            ],
            "sDom": "Rlfrtip", //"<'row'<'span6'l><'span6'f>r>t<'row'<'span6'i><'span6'p>>",
            "sPaginationType": "bootstrap"
        });
});