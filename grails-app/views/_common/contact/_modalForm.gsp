<link rel="stylesheet" type="text/css" href="/css/contact.css" />
<%@ page import="com.cogda.common.GenderEnum; com.cogda.common.NamePrefix; com.cogda.domain.Contact; com.cogda.domain.ContactEmailAddress; com.cogda.domain.ContactPhoneNumber" %>

<g:if test="${contactInstance.id == null }">
<form class="form-inline" class="contactForm" id="contactForm_new" name="contactForm_new">	
</g:if>
<g:else>
<form class="form-inline" class="contactForm updateContact" id="contactForm_${contactInstance?.id}">
</g:else>
<div class="alert alert-success" id="contactMessages" style="display:none">
</div>

<div class="alert alert-error" id="errors" style="display:none">
</div>
    <fieldset id="contactFieldset">
        <div id="contactData">
            <div class="field">
                <label>Title</label>
                <span id="titleLbl" class="editShow title contactShow">${contactInstance?.title}</span>
                <g:select name="title" class="editHide input-mini title contactEdit" from="${NamePrefix.values()}" value="${contactInstance?.title}">
                </g:select>
            </div>
            <div class="field">
                <label>First Name</label>
                <span id="firstNameLbl" class="editShow firstName contactShow">${contactInstance?.firstName}</span>
                <input type="text" name="firstName" required id="firstName" class="editHide required firstName contactEdit input-xlarge" value="${contactInstance?.firstName}">
            </div>
            <div class="field">
                <label>Middle Name</label>
                <span id="middleNameLbl" class="editShow middleName contactShow">${contactInstance?.middleName}</span>
                <input type="text" name="middleName" id="middleName" class="editHide middleName contactEdit input-xlarge" value="${contactInstance?.middleName}">
            </div>
            <div class="field">
                <label>Last Name</label>
                <span id="lastNameLbl" class="editShow lastName contactShow">${contactInstance?.lastName}</span>
                <input type="text" name="lastName" required id="lastName" class="editHide required lastName contactEdit input-xlarge" value="${contactInstance?.lastName}">
            </div>
            <div class="field">
                <label>Initials</label>
                <span id="initialsLbl" class="editShow initials contactShow">${contactInstance?.initials}</span>
                <input type="text" name="initials" id="initials" class="editHide input-mini initials contactEdit" value="${contactInstance?.initials}">
            </div>
            <div class="field">
                <label>Gender</label>
                <span id="genderLbl" class="editShow gender contactShow">${contactInstance?.gender}</span>
                <g:select name="gender" class="editHide contactEdit" value="${contactInstance?.gender}" from="${GenderEnum.values()}">
                </g:select>
            </div>
            <div class="field">
                <label>Company Name</label>
                <span id="companyNameLbl" class="editShow companyName contactShow">${contactInstance?.companyName}</span>
                <input type="text" id="companyName" name="companyName" class="editHide companyName contactEdit input-xlarge" value="${contactInstance?.companyName}">
            </div>
            <div class="field">
                <label>Job Title</label>
                <span id="jobTitleLbl" class="editShow jobTitle contactShow">${contactInstance?.jobTitle}</span>
                <input type="text" id="jobTitle" name="jobTitle" class="editHide jobTitle contactEdit input-xlarge" value="${contactInstance?.jobTitle}">
            </div>
            <div class="field">
                <label>Website</label>
                <span id="websiteLbl" class="editShow website contactShow">${contactInstance?.website}</span>
                <input type="text" id="website" name="website" class="editHide website contactEdit input-xlarge" value="${contactInstance?.website}">
            </div>
			<g:if test="${contactInstance.id == null }">
				<g:set var="emailAddress" value="${new ContactEmailAddress()}" />
				<% contactInstance.contactEmailAddresses = [];
				   contactInstance.contactEmailAddresses.add(emailAddress); %>		
	           <div class="field">
	               <label>Email </label>
	               <input type="text" id="contactEmailAddress" name="contactEmailAddress" class="editHide website contactEdit input-xlarge" value="${emailAddress.emailAddress}">
	           </div>
				<g:set var="phoneNumber" value="${new ContactPhoneNumber()}" />
				<% contactInstance.contactPhoneNumbers = [];
				   contactInstance.contactPhoneNumbers.add(phoneNumber); %>		
	           <div class="field">
	               <label>Phone Number</label>
	               <input type="text" id="contactPhoneNumber" name="contactPhoneNumber" class="editHide website contactEdit input-xlarge" value="${phoneNumber.phoneNumber}">
	           </div>				

			</g:if>
        </div>

        <div class="add">
            <a class="btn add-field editShow contactShow" href="#" onclick="toggleEdit();"><i class="icon-edit"></i> Edit</a>
			<g:if test="${contactInstance.id == null }">
	            <a class="btn btn-primary add-field editHide contactEdit" href="#" id="saveNewContact" onclick="saveNewContact();"><i class="icon-save"></i> Save</a>			
			</g:if>
			<g:else>
	            <a class="btn btn-primary add-field editHide contactEdit" href="#" onclick="saveContactDetails();"><i class="icon-save"></i> Save</a>			
			</g:else>
        </div>

    </fieldset>
<g:if test="${contactInstance.id != null }">
    <fieldset id="emailFieldset">
        <legend>Email Addresses</legend>
        <div class="primaryHeader">Primary</div>
        <div class="labelHeader">Email Address</div>
	        <g:each in="${contactInstance.contactEmailAddresses}" var="emailAddress">
				<g:render template="/_common/contact/contactEmail" model="${[emailAddress:emailAddress]}"/>
	        </g:each>
	        <div class="add" id="addEmailBtn">
	            <a class="btn btn-success add-field" href="#" onclick="addEmailAddressField();"><i class="icon-plus"></i> Add Email Address</a>	
        </div>
    </fieldset>
    <fieldset id="phoneFieldset">
        <legend>Phone Numbers</legend>
        <div class="primaryHeader">Primary</div>
        <div class="labelHeader">Phone Number</div>
        <g:each in="${contactInstance.contactPhoneNumbers}" var="phone">
			<g:render template="/_common/contact/contactPhone" model="${[phone:phone]}"/>
        </g:each>	
        <div class="add" id="addPhoneBtn">
            <a class="btn btn-success add-field" href="#" onclick="addPhoneField();"><i class="icon-plus"></i> Add Phone Number</a>
		</div>
    </fieldset>
    <fieldset id="mailFieldset">
        <legend>Mailing Addresses</legend>
        <div class="primaryHeader">Primary</div>
        <div class="labelHeader">Mailing Address</div>
        <g:each in="${contactInstance.contactAddresses}" var="address">
			<g:render template="/_common/contact/contactAddress" model="${[address:address]}"/>
        </g:each>

        <div class="add" id="addMail">
            <a class="btn btn-success add-field" href="#" onclick="addMailingAddressField();"><i class="icon-plus"></i> Add Address</a>
        </div>		

    </fieldset>
</g:if>

</form>