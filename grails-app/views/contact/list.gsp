
<%@ page import="com.cogda.domain.Contact" %>
<!doctype html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="layout" content="kickstart" />
	<g:set var="entityName" value="${message(code: 'contact.label', default: 'Contact')}" />
	<title><g:message code="default.list.label" args="[entityName]" /></title>

    <g:javascript>

        $(document).ready(function() {
           $('#contactList').dataTable({
              sScrollY: '70%',
              bProcessing: true,
              bServerSide: true,
              sAjaxSource: '${request.contextPath + '/contact/list'}' ,
              bJQueryUI: true,
              sPaginationType: "full_numbers",
              aLengthMenu: [[100, 500, 1000, 5000, -1], [100, 500, 1000, 5000, "All"]],
              iDisplayLength: 500,
              aoColumnDefs: [{
                 fnRender: renderPriceWithCents,
                 aTargets: [2]
              }]
           });
        });

    </g:javascript>

</head>

<body>
	
<section id="list-contact" class="first">
	<table class="table table-bordered" id="contactList">
		<thead>
			<tr>
			
				<g:sortableColumn property="jobTitle" title="${message(code: 'contact.jobTitle.label', default: 'Job Title')}" />
			
				<g:sortableColumn property="website" title="${message(code: 'contact.website.label', default: 'Website')}" />
			
				<g:sortableColumn property="companyName" title="${message(code: 'contact.companyName.label', default: 'Company Name')}" />
			
				<g:sortableColumn property="firstName" title="${message(code: 'contact.firstName.label', default: 'First Name')}" />
			
				<g:sortableColumn property="middleName" title="${message(code: 'contact.middleName.label', default: 'Middle Name')}" />
			
				<g:sortableColumn property="lastName" title="${message(code: 'contact.lastName.label', default: 'Last Name')}" />
			
			</tr>
		</thead>
		<tbody>
		<g:each in="${contactInstanceList}" status="i" var="contactInstance">
			<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
			
				<td><g:link action="show" id="${contactInstance.id}">${fieldValue(bean: contactInstance, field: "jobTitle")}</g:link></td>
			
				<td>${fieldValue(bean: contactInstance, field: "website")}</td>
			
				<td>${fieldValue(bean: contactInstance, field: "companyName")}</td>
			
				<td>${fieldValue(bean: contactInstance, field: "firstName")}</td>
			
				<td>${fieldValue(bean: contactInstance, field: "middleName")}</td>
			
				<td>${fieldValue(bean: contactInstance, field: "lastName")}</td>
			
			</tr>
		</g:each>
		</tbody>
	</table>
</section>

<jqDT:resources/>
</body>

</html>
