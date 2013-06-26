(function($, window, document) {


    if ( typeof $.fn.dataTable == "function" &&
        typeof $.fn.dataTableExt.fnVersionCheck == "function" &&
        $.fn.dataTableExt.fnVersionCheck('1.7.0') )
    {
        $.fn.dataTableExt.oApi.fnReloadAjax = function(oSettings, oUrl, oData)
        {
            if (oUrl)
                oSettings.sAjaxSource=oUrl;
            //oSettings.sAjaxSource = sNewSource;
            this.fnClearTable(this);
            this.oApi._fnProcessingDisplay(oSettings, true );
            var that = this;

            $.getJSON(oSettings.sAjaxSource, oData, function(json){
                /* Got the data - add it to the table */
                for (var i=0; i<json.aaData.length; i++)
                {
                    that.oApi._fnAddData(oSettings, json.aaData[i]);
                }

                oSettings.aiDisplay = oSettings.aiDisplayMaster.slice();
                that.fnDraw(that);
                that.oApi._fnProcessingDisplay(oSettings, false);
            });
        }
    }
})(jQuery, window, document);