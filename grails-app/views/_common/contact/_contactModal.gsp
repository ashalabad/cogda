<div class="modal hide fade" id="contactModal">
	<div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        <div id="contactHeader">
            <h3>Contact Details</h3>
        </div>
	</div>
	<div class="modal-body" id="contactModalBody">

	</div>
	<div class="modal-footer">
		<a href="#" class="btn btn-primary" data-dismiss="modal">Done</a>
	</div>
	<g:render template="/_common/contact/addEmail"/>				
	<g:render template="/_common/contact/editEmail"/>
	<g:render template="/_common/contact/addPhone"/>				
	<g:render template="/_common/contact/editPhone"/>
	<g:render template="/_common/contact/editMailingAddress"/>			
</div>
