<div class="field template" id="editEmail">
	<input type="radio" name="primaryEmail" class="primaryRadio" onchange="saveEmail(event)">
	<div id="emailShow" class="savedText showMode showMe"></div>
	<div class="field hideMe editMode input-append">
		<input id="emailAddress" type="text" class="emailInput input-xlarge" placeholder="bsmith@example.com">
		<button class="btn btn-primary saveEmail" type="button" onclick="saveEmail(event)"><i class="icon-save"></i> Save</button>
	</div>	
	<a class="btn showMe showMode" onclick="editSaved(event)" id="saveEmail"><i class="icon-edit"></i> Edit</a>
</div>