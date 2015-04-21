$(function() {

	$("#metaProductId").combox({
		source : "/finance/autocomplete/meta_product.json",
		select:function(event, ui){
			//获取采购产品类型
			var metaProductId = ui.item.value;
			$.post(
				"/finance/settlement/settle/getMetaBranchTypeByMetaProductId.json",
				{
					productId: metaProductId
				},
				function(data){
					$("#metaBranchIdSelect").val('');
					$("#metaBranchIdSelect").html("");
					$('#metaBranchIdSelect').append('<option value="">请选择</option>');
					for(var i in data){
						$('#metaBranchIdSelect').append('<option value="' + data[i].value + '">' + data[i].label + '</option>');
					}
				}
			);
		}
	});
	var gridcomplete = function(){  
	    var ids=$("#result_table").jqGrid('getDataIDs');
	    for(var i=0; i<ids.length; i++){
	        var id=ids[i];   
	        var rowData = $("#result_table").jqGrid("getRowData", id);
	        var operate = "<a href='../../detail/search/"+rowData.settlementId+"/" + id + ".htm' rowid = '" + id + "' target='_blank'  >查看订单</a>";
	        if(rowData.status == "UNSETTLEMENTED"){
	        	operate += "<a href='#' class = 'subsettlement_remove' rowid = '" + id + "' >删除</a>";
	        }
	        		
	        $("#result_table").jqGrid('setRowData', ids[i], { operate: operate });
	    }
	    var settlementId = $("#settlementId").val();
	 
	    $('.subsettlement_remove').click(function(){
	    	var rid = $(this).attr("rowid");
	    	art.dialog({
	    		id:'subsettlement-remove-confirm-dialog',
	    		fixed: true,
	    		lock:true,
	    		title:"确认信息",
	    	    content: '是否删除结算子单 - '+rid+'？',
	    	    cancelValue: '取消',
	    	    cancel: true,
	    	    okValue: '确认',
	    	    ok: function () {
	    	    	$.get("/finance/settlement/ordsettlement/sub/remove/"+settlementId+"/"+rid+".json",
    					function(data){
	    	    			art.dialog.get("subsettlement-remove-confirm-dialog").close();
    						if(data == 0 ){
    							$("#result_table").jqGrid('delRowData',rid);
    							$.msg("删除成功",1500);
    						}else if(data == -1){
    							$.msg("删除失败，打款金额小于结算金额");
    						}else if(data == -2){
    							$.msg("删除失败，结算单已结算");
    						}else if(data == -3){
    							$.msg("删除失败，结算单已确认");
    						}else{
    							$.msg("返回结果异常");
    						}
						});
	    	        return false;
	    	    }
	    	});
	    	
		});
	};
	$("#search_button").click(function(){
		var sett_id  =$("input[name=subSettlementId]").val();
		if(sett_id!=""){
			if(!/^[0-9]*$/.test(sett_id)){
				$.msg("子单编号输入错误",1500);
				return false;
			}
		}
		$("#result_table").grid({
			former: '#search_form',
			pager: '#pagebar_div',
			colNames : [ '子单ID','结算单ID', '结算单状态','采购产品ID', '采购产品','采购产品类别ID', '采购产品类别','应结算金额','操作'],
			colModel : [ {
				name : 'subSettlementId',
				index : 'SUBSETTLEMENTID',
				align:"center",
				sorttype: "int",
				width:38
			}, {
				name : 'settlementId',
				index : 'settlementId',
				align:"center",
				sorttype: "int",
				hidden:true
			}, {
				name : 'status',
				index : 'status',
				align:"center",
				sorttype: "string",
				hidden:true
			},{
				name : 'metaProductId',
				index : 'METAPRODUCTID',
				align:"center",
				sorttype: "int",
				width:38
			}, {
				name : 'metaProductName',
				index : 'METAPRODUCTID',
				align:"center",
				sorttype: "string",
				sortable:false
			}, {
				name : 'metaBranchId',
				index : 'METABRANCHID',
				align:"center",
				sorttype: "int",
				hidden:true
			}, {
				name : 'branchName',
				index : 'BRANCHNAME',
				align:"center",
				sorttype: "string",
				sortable:false
			}, {
				name : 'payAmount',
				index : 'PAYAMOUNT',
				align:"center",
				sorttype: "float",
				formatter:"number",
				formatoptions: {decimalSeparator:".", thousandsSeparator: ",",decimalPlaces:2},
				width:60
			},
			{
				name:'operate',
				index:'Id',
				align:"center",
				sortable:false,
				width:68
			}],
	        gridComplete:gridcomplete
		});
	});
});