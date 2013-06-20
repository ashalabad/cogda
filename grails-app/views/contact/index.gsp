
<%@ page import="com.cogda.domain.Contact" %>
<!doctype html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="kickstart" />
    <g:set var="entityName" value="${message(code: 'contact.label', default: 'Contact')}" />
    <g:set var="layout_nosecondarymenu"	value="${true}" scope="request"/>
    <g:set var="layout_nomainmenu"		value="${true}" scope="request"/>
    <link rel="stylesheet" type="text/css" href="css/contact.css" />
    <script type="text/javascript" src="js/contact/contact.js" ></script>
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
    <table class="table table-bordered" id="contactList" width="80%">
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

<div class="modal hide fade" id="test_modal">
	<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	<div id="contactHeader">
		<h3>Contact Details</h3>
	</div>
	<div class="btn-group">
		<button class="btn">View</button>
		<button class="btn">Edit</button>
	</div>	

	</div>
	<div class="modal-body">
		<form class="form-inline">
			<fieldset>
			<div id="contactData">
				<div class="field">
					<label>First Name</label>
					<input type="text" id="firstName">
				</div>
				<div class="field">
					<label>Last Name</label>
					<input type="text" id="lastName">
				</div>
				<div class="field">
					<label>Company Name</label>
					<input type="text" id="companyName">
				</div>
				<div class="field">
					<label>Job Title</label>
					<input type="text" id="jobTitle">
				</div>            
			</div>
			</fieldset>
			<fieldset>
				<legend>Email Addresses</legend>
				<div class="field">
					<label>Email Address</label>
					<input type="text" id="emailAddress_1">
				</div>
				<a class="btn btn-mini add-field" href="#" onclick="addEmailAddressField();"><i class="icon-plus"></i> Add</a>
			</fieldset>
			<fieldset>
				<legend>Mailing Addresses</legend>
				<div class="field">
					<label>Mailing Address</label>
					<input type="text" id="mailingAddress_1">
				</div>
				<a class="btn btn-mini add-field" href="#"><i class="icon-plus"></i> Add</a>
			</fieldset>		
			<fieldset>
				<legend>Phone Numbers</legend>
				<div class="field">
					<label>Phone Numbers</label>
					<input type="text" id="phoneNumbers_1">
				</div>	
				<a class="btn btn-mini add-field" href="#"><i class="icon-plus"></i> Add</a>
			</fieldset>				
		</form>
	</div>
	<div class="modal-footer">
		<a href="#" class="btn">Close</a>
		<a href="#" class="btn btn-primary">Save changes</a>
	</div>
</div>


</body>

</html>
