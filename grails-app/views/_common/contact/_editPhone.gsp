<div class="field template" id="editPhone">
	<input type="radio" name="primaryPhone" class="primaryRadio" onchange="savePhone(event)" value="true">
	<div id="phoneShow" class="savedText showMode showMe"></div>
	<div class="field hideMe editMode input-append">
		<input id="phoneNumber" name="phoneNumber" type="text" class="phoneInput input-xlarge" placeholder="bsmith@example.com">
		<button class="btn btn-primary savePhone" type="button" onclick="savePhone(event)"><i class="icon-save"></i> Save</button>
	</div>	
	<a class="btn showMe showMode" onclick="editSaved(event)" id="phoneEditBtn"><i class="icon-edit"></i> Edit</a>
</div>