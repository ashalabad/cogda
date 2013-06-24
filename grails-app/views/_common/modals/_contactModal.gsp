<%@ page import="com.cogda.common.GenderEnum; com.cogda.domain.Contact" %>
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
						<g:select name="title" class="editHide input-mini title contactEdit" from="${["Mr", "Mrs", "Ms", "Miss", "Dr"]}" value="${contactInstance?.title}">
						</g:select>
					</div>				
					<div class="field">
						<label>First Name</label>
						<span id="firstNameLbl" class="editShow firstName contactShow"></span>
						<input type="text" id="firstName" class="editHide firstName contactEdit input-xlarge" value="${contactInstance?.firstName}">
					</div>
					<div class="field">
						<label>Middle Name</label>
						<span id="middleNameLbl" class="editShow middleName contactShow"></span>						
						<input type="text" id="middleName" class="editHide middleName contactEdit input-xlarge" value="${contactInstance?.middleName}">
					</div>
					<div class="field">
						<label>Last Name</label>
						<span id="lastNameLbl" class="editShow lastName contactShow"></span>
						<input type="text" id="lastName" class="editHide lastName contactEdit input-xlarge" value="${contactInstance?.lastName}">
					</div>
					<div class="field">
						<label>Initials</label>
						<span id="initialsLbl" class="editShow initials contactShow"></span>						
						<input type="text" id="initials" class="editHide input-mini initials contactEdit" value="${contactInstance?.initials}">
					</div>	
					<div class="field">
						<label>Gender</label>
						<span id="genderLbl" class="editShow gender contactShow"></span>
                        ${contactInstance?.gender}
						<g:select name="gender" class="editHide contactEdit" value="${contactInstance?.gender}" from="${GenderEnum.values()}">
						</g:select>
					</div>									
					<div class="field">
						<label>Company Name</label>
						<span id="companyNameLbl" class="editShow companyName contactShow"></span>						
						<input type="text" id="companyName" class="editHide companyName contactEdit input-xlarge" value="${contactInstance?.companyName}">
					</div>
					<div class="field">
						<label>Job Title</label>
						<span id="jobTitleLbl" class="editShow jobTitle contactShow"></span>						
						<input type="text" id="jobTitle" class="editHide jobTitle contactEdit input-xlarge" value="${contactInstance?.jobTitle}">
					</div>   
					<div class="field">
						<label>Website</label>
						<span id="websiteLbl" class="editShow website contactShow"></span>						
						<input type="text" id="website" class="editHide website contactEdit input-xlarge" value="${contactInstance?.website}">
					</div>					

				</div>
				<div class="add">
					<a class="btn btn-mini add-field editShow contactShow" href="#" onclick="toggleEdit();"><i class="icon-edit"></i> Edit</a>
					<a class="btn btn-mini btn-primary add-field editHide contactEdit" href="#" onclick="saveContact();"><i class="icon-save"></i> Save</a>				
				</div>
			</fieldset>
			<fieldset id="emailFieldset">
				<legend>Email Addresses</legend>
				<div id="emailPrimary">Primary</div>
				<div id="emailHeader">Email Address</div>
				<div class="add" id="addEmailBtn">
					<a class="btn btn-mini add-field" href="#" onclick="addEmailAddressField();"><i class="icon-plus"></i> Add</a>
				</div>
			</fieldset>
			<fieldset id="phoneFieldset">
				<legend>Phone Numbers</legend>
				<div class="add" id="addPhoneBtn">
					<a class="btn btn-mini add-field" href="#" onclick="addPhoneField();"><i class="icon-plus"></i> Add</a>
				</div>
			</fieldset>			
			<fieldset id="mailFieldset">
				<legend>Mailing Addresses</legend>
				<g:render template="/_common/contact/editMailingAddress"/>
				<div class="field address data show">
					<label class="viewAddressLbl">Mailing Address</label> 
						<a class="btn btn-mini editAddress template" href="#" onclick="editAddress();">
						<i class="icon-edit"></i> Edit</a>
					<div class="addressOne"></div>
					<div class="addressTwo"></div>
					<div class="addressThree"></div>
					<span class="city"></span>, <span class="state"></span> <span class="zipcode"></span>															
				</div>				
				<div class="add" id="addMail">
					<a class="btn btn-mini add-field" href="#" onclick="addMailingAddressField();"><i class="icon-plus"></i> Add</a>
				</div>
			</fieldset>		
				
		</form>
	</div>
	<div class="modal-footer">
		<a href="#" class="btn btn-primary" data-dismiss="modal">Done</a>
	</div>
	<g:render template="/_common/contact/addEmail"/>				
	<g:render template="/_common/contact/editEmail"/>
	<g:render template="/_common/contact/addPhone"/>				
	<g:render template="/_common/contact/editPhone"/>		
</div>
