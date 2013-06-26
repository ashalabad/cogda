<r:require module="naicsCodeTree"/>

<div class="modal hide fade" id="naicsCodeModal">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        <div id="naicsCodeHeader">
            <h3>NAICS Codes</h3>
        </div>

    </div>
    <div class="modal-body">
        <div class="form-inline">
            <input type="text" id="searchNaics" placeholder="filter" class="input-small" />
            <button id="resetNaics" class="btn">Collapse All</button>
        </div>
        <br>
        <div id="naicsTree"></div>
    </div>
    <div class="modal-footer">
        <a class="btn btn-primary" href="#" data-dismiss="modal" onclick="processNaicsChecked();"><i class="icon-save"></i> Save</a>
    </div>
</div>

<div class="btn btn-mini" id="naicsModalLauncher">NAICS Codes</div>