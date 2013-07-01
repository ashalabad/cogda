<div class="field data" id="editPhone_${phone.id}">
	<g:if test="${phone.primaryPhoneNumber}">
		<input type="radio" name="primaryPhone" class="primaryRadio" checked="checked" onchange="savePhone(event)" value="true">
	</g:if>
	<g:else>
		<input type="radio" name="primaryPhone" class="primaryRadio" onchange="savePhone(event)" value="true">
	</g:else>
	<div id="phoneShow" class="savedText showMode showMe">${phone.phoneNumber}</div>
	<div class="field hideMe editMode input-append">
		<input id="phone_${phone.id}" type="text" name="phoneNumber" class="phoneInput input-xlarge" placeholder="(123)555-1234" value="${phone.phoneNumber}">
		<button class="btn btn-primary savePhone" type="button" onclick="savePhone(event)"><i class="icon-save"></i> Save</button>
	</div>	
	<a class="btn showMe showMode" onclick="editSaved(event)" id="phoneEditBtn"><i class="icon-edit"></i> Edit</a>
</div>