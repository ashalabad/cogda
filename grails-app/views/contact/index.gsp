
<%@ page import="com.cogda.domain.Contact" %>
<!doctype html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="kickstart" />
    <g:set var="entityName" value="${message(code: 'contact.label', default: 'Contact')}" />
    <g:set var="layout_nosecondarymenu"	value="${true}" scope="request"/>
    <g:set var="layout_nomainmenu"		value="${true}" scope="request"/>
    <title><g:message code="default.list.label" args="[entityName]" /></title>
    <r:require module="dataTables"/>
    <g:javascript>

        /* Table initialisation */
        $(document).ready(function() {
            $('#contactList').dataTable(
            {
                "bProcessing": true,
                "sAjaxSource": "${request.contextPath + 'contact/list'}",
                "aoColumns": [
//                    {"mDataProp":"id"},
//                    {"mDataProp":"version"},
                    {"mDataProp":"companyName"},
                    {"mDataProp":"lastName"},
                    {"mDataProp":"firstName"},

                    {"mDataProp":"jobTitle"},
                    {"mDataProp":"primaryEmailAddress"}
                ],
                "sDom": "<'row'<'span6'l><'span6'f>r>t<'row'<'span6'i><'span6'p>>",
                "sPaginationType": "bootstrap"
            }
            );
        } );
    </g:javascript>

</head>

<body>

<content tag="header">
    <!-- Empty Header -->
</content>

<div id="MenuRow" class="row">
    <div class="span12">
        &nbsp;
    </div>
</div>

<section id="list-contact" class="first">
    <table class="table table-bordered" id="contactList">
        <thead>
        <tr>

            %{--<th>${message(code: 'contact.id.label', default: 'Id')}</th>--}%

            %{--<th>${message(code: 'contact.version.label', default: 'Version')}</th>--}%

            <th>${message(code: 'contact.companyName.label', default: 'Company Name')}</th>

            <th>${message(code: 'contact.lastName.label', default: 'Last Name')}</th>

            <th>${message(code: 'contact.firstName.label', default: 'First Name')}</th>

            <th>${message(code: 'contact.jobTitle.label', default: 'Job Title')}</th>

            <th>${message(code: 'contact.primaryEmailAddress.label', default: 'Primary Email')}</th>
        </tr>
        </thead>
        <tbody>
        <g:each in="${contactInstanceList}" status="i" var="contactInstance">
            <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">

                %{--<td>${fieldValue(bean: contactInstance, field: "id")}</td>--}%

                %{--<td>${fieldValue(bean: contactInstance, field: "version")}</td>--}%

                <td>${fieldValue(bean: contactInstance, field: "companyName")}</td>

                <td>${fieldValue(bean: contactInstance, field: "lastName")}</td>

                <td>${fieldValue(bean: contactInstance, field: "firstName")}</td>

                <td>${fieldValue(bean: contactInstance, field: "jobTitle")}</td>

                <td>${fieldValue(bean: contactInstance, field: "primaryEmailAddress")}</td>

            </tr>
        </g:each>
        </tbody>
    </table>
</section>


</body>

</html>
