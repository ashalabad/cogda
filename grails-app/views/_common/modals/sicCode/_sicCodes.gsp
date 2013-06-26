<r:require module="sicCodeTree"/>

<div class="modal hide fade" id="sicCodeModal">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        <div id="sicCodeHeader">
            <h3>SIC Codes</h3>
        </div>

    </div>
    <div class="modal-body">
        <div class="form-inline">
            <input type="text" id="searchSic" placeholder="filter" class="input-small" />
            <button id="resetSic" class="btn">Collapse All</button>
        </div>
        <br>
        <div id="sicTree"></div>
    </div>
    <div class="modal-footer">
        <a class="btn btn-primary" href="#" data-dismiss="modal" onclick="processSicChecked();"><i class="icon-save"></i> Save</a>
    </div>
</div>

<div class="btn btn-mini" id="sicModalLauncher">SIC Codes</div>