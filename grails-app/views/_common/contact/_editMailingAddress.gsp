<div class="field addressInstance template show" id="editAddress">
	<g:if test="">
		<input type="radio" name="primaryAddress" class="primaryRadio" checked="checked" onchange="saveAddress(event)" value="true">
	</g:if>
	<g:else>
		<input type="radio" name="primaryAddress" class="primaryRadio" onchange="saveAddress(event)" value="true">
	</g:else>	
	<div class="addressOne showMe showMode savedText"></div>
	<div class="field hideMe addressLine editMode">
		<input type="text" class="addressOne input-xlarge" placeholder="address line 1" value="">
	</div>
	<div class="addressTwo addressLine showMe showMode savedText"></div>
	<div class="field hideMe addressLine editMode">
		<input type="text" class="addressTwo input-xlarge" placeholder="address line 2" value="">					
	</div>
	<div class="addressThree addressLine showMe showMode savedText"></div>
	<div class="field hideMe addressLine editMode">
		<input type="text" class="addressThree input-xlarge" placeholder="address line 3" value="">										
	</div>
	<div class="addressLine">
		<span class="city showMe showMode"></span><span class="showMe showMode">,</span>
		<div class="city hideMe editMode">
			<input type="text" class="city input-xlarge" placeholder="city" value="">	
		</div>
		<span class="state showMe showMode"></span>
		<div class="stateContainer hideMe editMode">
		    <g:select class="state input-small" name="state" from="${com.cogda.common.UsState.values()}" optionKey="key" noSelection="['': 'State']" value=""/>	
		</div>
		<span class="zipcode showMe showMode"></span>
		<div class="hideMe editMode zipcode">
			<input type="text" class="zip input-small" placeholder="zipcode" value="">
		</div>
	</div>
	<a class="btn showMe showMode" onclick="editSaved(event)" id="editAddressBtn"><i class="icon-edit"></i> Edit</a>		

	<div class="save hideMe editMode zipcode">
		<a href="#" class="btn cancel" onclick="showMode(event);">Cancel</a>					
		<a class="btn btn-primary" href="#" onclick="saveAddress();"><i class="icon-save"></i> Save</a>
	</div>
</div>