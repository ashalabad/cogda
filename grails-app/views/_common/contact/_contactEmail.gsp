<div class="field data" id="editEmail_${emailAddress.id}">
	<g:if test="${emailAddress.primaryEmailAddress}">
		<input type="radio" name="primaryEmail" class="primaryRadio" checked="checked" onchange="saveEmail(event)" value="true">
	</g:if>
	<g:else>
		<input type="radio" name="primaryEmail" class="primaryRadio" onchange="saveEmail(event)" value="true">
	</g:else>
	<div id="emailShow" class="savedText showMode showMe">${emailAddress.emailAddress}</div>
	<div class="field hideMe editMode input-append">
		<input id="emailAddress_${emailAddress.id}" type="text" class="emailInput input-xlarge" placeholder="bsmith@example.com" value="${emailAddress.emailAddress}">
		<button class="btn btn-primary saveEmail" type="button" onclick="saveEmail(event)"><i class="icon-save"></i> Save</button>
	</div>	
	<a class="btn showMe showMode" onclick="editSaved(event)" id="saveEmail"><i class="icon-edit"></i> Edit</a>
</div>