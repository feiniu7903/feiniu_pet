	var gridcomplete = function(){  
	    var ids=$("#finReconSummary_item_table").jqGrid('getDataIDs');
	    for(var i=0; i<ids.length; i++){
	        var id=ids[i];   
	        var rowData = $("#finReconSummary_item_table").jqGrid('getRowData',ids[i]);
	        var operate ="<a href='#' class = 'budget_see' onclick='javascript:showCashDetailFn(&quot;"+ rowData.gwName +"&quot;,&quot;"+ rowData.reconDateStr +"&quot;);'>查看详细</a>";
	        //if(rowData.reconStatus!=undefined && rowData.reconStatus!='SUCCESS'){
	        //	operate+="<a href='#' class = 'budget_see' onclick='javascript:reconciliationFn(&quot;"+ id +"&quot;,&quot;"+ rowData.gwName +"&quot;,&quot;"+ rowData.reconDateStr +"&quot;);'>手动对账</a>";
	        //}
	        $("#finReconSummary_item_table").jqGrid('setRowData', ids[i],
    	    	{ operate: operate,gwName: reconGwObj[rowData.gwName],reconStatus:reconStatusObj[rowData.reconStatus],reconType:reconTypeObj[rowData.reconType] });
	    }
	 };
	//查询结算队列项
	function searchFinReconSummaryHandler(){
		if(!$("#finReconSummary_item_form").validate().checkForm()){
			return false;
		}
		$("#gridDiv").show();
		$("#finReconSummary_item_table").grid({
			former:'#finReconSummary_item_form',
			pager:'#pagebar_div',
			colNames : [ 
			             '',
			             '对账日期', 
			             '收款平台', 
			             '驴妈妈总收款', 
			             '收款平台总收款', 
			             '对账状态',
			             '对账方式',
			             '操作'
			             ],
			colModel : [ 
			             {name:'reconSummaryId', index:'id',hidden:true},
			             {name:'reconDateStr',index:'RECON_DATE', sortable:true, width:100,formatter:"date",formatoptions: {newformat: 'Y-m-d'}} ,
			             {name:'gwName', sortable:false, width:150},
			             {name:'lvSumAmount', sortable:false, width:100,formatter:currencyFmatter,align:'right'},
			             {name:'gwSumAmount', sortable:false, width:100,formatter:currencyFmatter,align:'right'},
			             {name:'reconStatus', sortable:false, width:100},
			             {name:'reconType', sortable:false, width:100},
			             {name:'operate',width:150,align:'center',sortable:false}
			            ],
			autowidth: true,
			autoheight: true,
			rowNum:10,
			rownumbers: true,
			rowList:[10,20,30,50,100],
			sortname:'RECON_DATE',
			sortorder:'desc',
			gridComplete:gridcomplete
		});
	}