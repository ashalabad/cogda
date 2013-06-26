<div class="field data" id="editPhone_${phone.id}">
	<input type="radio" name="primary" class="primaryRadio" checked="checked">	
	<div id="emailShow" class="emailText showMode showMe">${phone.phoneNumber}</div>
	<div class="field hideMe editMode input-append">
		<input id="phone" type="text" class="phoneInput input-xlarge" placeholder="(123)555-1234" value="${phone.phoneNumber}">
		<button class="btn btn-primary savePhone" type="button" onclick="savePhone(event)"><i class="icon-save"></i> Save</button>
	</div>	
	<a class="btn showMe showMode" onclick="editPhone(event)" id="phoneEditBtn"><i class="icon-edit"></i> Edit</a>
</div>