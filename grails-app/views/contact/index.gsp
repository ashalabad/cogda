
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

<div class="modal hide fade" id="contactModal">
	<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	<div id="contactHeader">
		<h3>Contact Details</h3>
	</div>

	</div>
	<div class="modal-body">
		<form class="form-inline" id="contactForm">
			<fieldset>
				<div id="contactData">
					<div class="field">
						<label>First Name</label>
						<span id="firstNameLbl" class="editShow firstName contactShow"></span>
						<input type="text" id="firstName" class="editHide contactEdit">
					</div>
					<div class="field">
						<label>Last Name</label>
						<span id="lastNameLbl" class="editShow lastName contactShow"></span>
						<input type="text" id="lastName" class="editHide contactEdit">
					</div>
					<div class="field">
						<label>Company Name</label>
						<span id="companyNameLbl" class="editShow companyName contactShow"></span>						
						<input type="text" id="companyName" class="editHide contactEdit">
					</div>
					<div class="field">
						<label>Job Title</label>
						<span id="companyNameLbl" class="editShow jobTitle contactShow"></span>						
						<input type="text" id="jobTitle" class="editHide jobTitle contactEdit">
					</div>            
				</div>
				<a class="btn btn-mini add-field editShow contactShow" href="#" onclick="toggleEdit();"><i class="icon-edit"></i> Edit</a>
				<a class="btn btn-mini add-field editHide contactEdit" href="#" onclick="saveContact();"><i class="icon-save"></i> Save</a>				

			</fieldset>
			<fieldset id="emailFieldset">
				<legend>Email Addresses</legend>
				<div class="field">
					<label>Email Address</label>
					<span id="emailAddress_1">					
					<input type="text" id="emailAddress_0">
				</div>
				<a class="btn btn-mini add-field" href="#" onclick="addEmailAddressField();"><i class="icon-plus"></i> Add</a>
			</fieldset>
			<fieldset id="mailFieldset">
				<legend>Mailing Addresses</legend>
				<div class="field">
					<label>Mailing Address</label>
					<input type="text" id="mailingAddress_0">
				</div>
				<a class="btn btn-mini add-field" href="#" onclick="addMailingAddressField();"><i class="icon-plus"></i> Add</a>
			</fieldset>		
			<fieldset id="phoneFieldset">
				<legend>Phone Numbers</legend>
				<div class="field">
					<label>Phone</label>
					<input type="text" id="phone_0">
				</div>	
				<a class="btn btn-mini add-field" href="#" onclick="addPhoneField();"><i class="icon-plus"></i> Add</a>
			</fieldset>				
		</form>
	</div>
	<div class="modal-footer">
		<a href="#" class="btn btn-primary" data-dismiss="modal">Done</a>
	</div>
</div>


</body>

</html>
