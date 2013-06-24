
<%@ page import="com.cogda.domain.Contact" %>
<!doctype html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="kickstart" />
    <g:set var="entityName" value="${message(code: 'contact.label', default: 'Contact')}" />
    <g:set var="layout_nosecondarymenu"	value="${true}" scope="request"/>
    <g:set var="layout_nomainmenu"		value="${true}" scope="request"/>
    <link rel="stylesheet" type="text/css" href="/css/contact.css" />
    <script type="text/javascript" src="/js/contact/contact.js" ></script>
    <title><g:message code="default.list.label" args="[entityName]" /></title>
    <r:require module="dataTables"/>

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

	            <th>${message(code: 'contact.companyName.label', default: 'Company Name')}</th>

	            <th>${message(code: 'contact.lastName.label', default: 'Last Name')}</th>

	            <th>${message(code: 'contact.firstName.label', default: 'First Name')}</th>

	            <th>${message(code: 'contact.jobTitle.label', default: 'Job Title')}</th>

	            <th>${message(code: 'contact.primaryEmailAddress.label', default: 'Primary Email')}</th>
	        </tr>
	        </thead>
	        <tbody>
 
	        </tbody>
	    </table>
	</section>
	<g:render template="/_common/modals/contactModal"/>


</body>

</html>
