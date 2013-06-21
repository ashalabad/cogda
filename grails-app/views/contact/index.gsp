
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
						<label>Title</label>
						<span id="titleLbl" class="editShow title contactShow"></span>
						<select id="title" class="editHide input-mini title contactEdit">
							<option value="Mr">Mr</option>
							<option value="Mrs">Mrs</option>							
							<option value="Ms">Ms</option>														
							<option value="Miss">Miss</option>																					
							<option value="Dr">Dr</option>																					
						</select>
					</div>				
					<div class="field">
						<label>First Name</label>
						<span id="firstNameLbl" class="editShow firstName contactShow"></span>
						<input type="text" id="firstName" class="editHide firstName contactEdit">
					</div>
					<div class="field">
						<label>Middle Name</label>
						<span id="middleNameLbl" class="editShow middleName contactShow"></span>						
						<input type="text" id="middleName" class="editHide middleName contactEdit">
					</div>
					<div class="field">
						<label>Last Name</label>
						<span id="lastNameLbl" class="editShow lastName contactShow"></span>
						<input type="text" id="lastName" class="editHide lastName contactEdit">
					</div>
					<div class="field">
						<label>Initials</label>
						<span id="initialsLbl" class="editShow initials contactShow"></span>						
						<input type="text" id="initials" class="editHide input-mini initials contactEdit">
					</div>	
					<div class="field">
						<label>Gender</label>
						<span id="genderLbl" class="editShow gender contactShow"></span>						
						<select id="gender" class="editHide contactEdit">
							<option value="Male">Male</option>
							<option value="Female">Female</option>							
						</select>
					</div>									
					<div class="field">
						<label>Company Name</label>
						<span id="companyNameLbl" class="editShow companyName contactShow"></span>						
						<input type="text" id="companyName" class="editHide companyName contactEdit">
					</div>
					<div class="field">
						<label>Job Title</label>
						<span id="jobTitleLbl" class="editShow jobTitle contactShow"></span>						
						<input type="text" id="jobTitle" class="editHide jobTitle contactEdit">
					</div>   
					<div class="field">
						<label>Website</label>
						<span id="websiteLbl" class="editShow website contactShow"></span>						
						<input type="text" id="website" class="editHide website contactEdit">
					</div>					

				</div>
				<div class="add">
					<a class="btn btn-mini add-field editShow contactShow" href="#" onclick="toggleEdit();"><i class="icon-edit"></i> Edit</a>
					<a class="btn btn-mini btn-primary add-field editHide contactEdit" href="#" onclick="saveContact();"><i class="icon-save"></i> Save</a>				
				</div>
			</fieldset>
			<fieldset id="emailFieldset">
				<legend>Email Addresses</legend>
				<span id="testemail"></span>
				<div class="add">
					<a class="btn btn-mini add-field" href="#" onclick="addEmailAddressField();"><i class="icon-plus"></i> Add</a>
				</div>
			</fieldset>
			<fieldset id="mailFieldset">
				<legend>Mailing Addresses</legend>
				<div class="field address template edit">
					<label class="addressLbl">Mailing Address</label>
					<input type="text" class="addressOne" placeholder="address line 1">
					<input type="text" class="addressTwo" placeholder="address line 2">					
					<input type="text" class="addressThree" placeholder="address line 3">										
					<input type="text" class="city" placeholder="city">	
					<div class="stateContainer">
						<select class="state input-small">
							<option value="AL">AL</option>
							<option value="AK">AK</option>
							<option value="GA">GA</option>							
						</select>
					</div>
					<input type="text" class="zip input-small" placeholder="zipcode">
					<div class="save">
						<a class="btn btn-mini btn-primary" href="#" onclick="saveAddress();"><i class="icon-save"></i> Save</a>
					</div>																	
				</div>
				<div class="field address data show">
					<label class="viewAddressLbl">Mailing Address</label> <a class="btn btn-mini editAddress template" href="#" onclick="editAddress();"><i class="icon-edit"></i> Edit</a>
					<div class="addressOne">123 Childs st</div>
					<div class="addressTwo">Ste 213</div>
					<div class="addressThree"></div>
					<span class="city">Athens</span>, <span class="state">GA</span> <span class="zipcode">30601</span>															
				</div>				
				<div class="add" id="mailAdd">
					<a class="btn btn-mini add-field" href="#" onclick="addMailingAddressField();"><i class="icon-plus"></i> Add</a>
				</div>
			</fieldset>		
			<fieldset id="phoneFieldset">
				<legend>Phone Numbers</legend>
				<div class="field">
					<label>Phone</label>
					<input type="text" id="phone_0">
				</div>	
				<div class="add">
					<a class="btn btn-mini add-field" href="#" onclick="addPhoneField();"><i class="icon-plus"></i> Add</a>
				</div>
			</fieldset>				
		</form>
	</div>
	<div class="modal-footer">
		<a href="#" class="btn btn-primary" data-dismiss="modal">Done</a>
	</div>
</div>


</body>

</html>
