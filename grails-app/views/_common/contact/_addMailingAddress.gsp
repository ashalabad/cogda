<div class="field new addressInstance template" id="addAddress">
	<input type="text" class="addressOne input-xlarge" placeholder="address line 1">
	<input type="text" class="addressTwo input-xlarge" placeholder="address line 2">					
	<input type="text" class="addressThree input-xlarge" placeholder="address line 3">										
	<input type="text" class="city input-xlarge" placeholder="city">	
	<div class="stateContainer">
	    <g:select class="state input-small" name="state" from="${com.cogda.common.UsState.values()}" optionKey="key" noSelection="['': 'State']"/>	
	</div>
	<input type="text" class="zipcode input-small" placeholder="zipcode">
	<div class="save">
		<a href="#" class="btn cancel" onclick="cancelNewAddress($(this));">Cancel</a>					
		<a class="btn btn-primary" href="#" onclick="saveAddress();"><i class="icon-save"></i> Save</a>
	</div>
</div>