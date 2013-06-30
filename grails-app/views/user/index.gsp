<%@ page import="com.cogda.domain.security.User" %>
<!doctype html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="kickstart" />
    <g:set var="entityName" value="${message(code: 'user.label', default: 'User')}" />
    <g:set var="layout_nosecondarymenu"	value="${true}" scope="request"/>
    <g:set var="layout_nomainmenu"		value="${true}" scope="request"/>
    <link rel="stylesheet" type="text/css" href="/css/contact.css" />
    <script type="text/javascript" src="/js/user/user.js" ></script>
    <title><g:message code="default.list.label" args="[entityName]" /></title>
    <r:require module="dataTables"/>

</head>

<body>

	<content tag="header">
	    <!-- Empty Header -->
	</content>

    <h2>${message(code: 'user.label', default: 'User')}</h2>

	<section id="list-user" class="first">


	    <table class="table table-bordered" id="userList">
	        <thead>
	        <tr>

	            <th>${message(code: 'user.username.label', default: 'Username')}</th>

	            <th>${message(code: 'user.enabled.label', default: 'Enabled')}</th>

	            <th>${message(code: 'user.accountExpired.label', default: 'Acct. Expired')}</th>

	            <th>${message(code: 'user.accountLocked.label', default: 'Locked')}</th>

	            <th>${message(code: 'user.passwordExpired.label', default: 'Pwd Expired')}</th>
	        </tr>
	        </thead>
	        <tbody>
 
	        </tbody>
	    </table>
	</section>
	<g:render template="/_common/contact/contactModal"/>
</body>

</html>
