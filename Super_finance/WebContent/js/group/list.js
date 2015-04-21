$(function() {
	/**
	 * 销售产品名称自动查询下拉框
	 */
	$("#product").combox("/finance/autocomplete/product.json");	
	var actProfitRate_formatter=function(cellVal){
		if(null==cellVal || ''==cellVal)return '';
		return cellVal+"%";
	};
	$(".orderDetails").live('click',function(){
		var travelGroupCode = $(this).attr("travelGroupCode");
		var prodBranchId = $(this).attr("prodBranchId");
		window.open("/finance/group/list/orderDetails/"+encodeURIComponent(travelGroupCode)+"/"+prodBranchId+".htm");
	});

	$("#group_print_btn").live('click',function(){
		window.open("/finance/group/list/print/"+encodeURIComponent($(this).attr("group_code"))+"/"+$(this).attr("group_type")+".htm");
	});
	var gridcomplete = function(){  
	    var ids=$("#result_table").jqGrid('getDataIDs');
	    for(var i=0; i<ids.length; i++){
	        var id=ids[i];   
	        var rowData = $("#result_table").jqGrid('getRowData',ids[i]);
	        var operate = "";
	        /*if(rowData.settlementStatus=='COSTED'){
		       operate+="<a href='javascript:void(0);' class = 'cost_confirmed' rowid = '" + id + "' >确认成本</a>";
		    }*/

	        if(rowData.settlementStatus=='COSTED' || rowData.settlementStatus =='CONFIRMED'|| rowData.settlementStatus=='CHECKED'  ){
	    	   operate+="<a href='javascript:void(0);' class = 'cost_see' rowid = '" + id + "' >团成本表</a>";
	        }
		    if(rowData.settlementStatus =='CONFIRMED'){
		       operate+="<a id='group_check_"+id+"' href='javascript:void(0);' class = 'group_check' rowid = '" + id + "' >核算</a>";
		    }
	       var hid ="";
	       if( rowData.settlementStatus !='CHECKED'){
	    	   hid =" hid";
	       }
	       operate+="<a id='check_seee_"+id+"' href='javascript:void(0);' class = 'check_see"+hid+"' rowid = '" + id + "' >核算表</a>";
	       $("#result_table").jqGrid('setRowData', ids[i], { operate: operate });
	    }
	  //实际成本
	    $('.cost_see').click(function(){
	    	var code = $(this).attr("rowid");
	    	$.get("/finance/group/list/budget/"+encodeURIComponent(code)+"/COST.htm",
    			function(data){
    				art.dialog({
    					title: "["+code+"]",
    				    content: data,
    				    lock:true
    			});
    		});
        });
	    //核算
	    $('.group_check').click(function(){
	    	var g_code = $(this).attr("rowid");
	    	$.get("/finance/group/list/budget/"+encodeURIComponent(g_code)+"/CHECK.htm",
    			function(data){
	    			
					art.dialog({
						id:"groupcheck-dialog",
						title: "["+g_code+"]",
					    content: data,
					    lock:true
					});
					//为核算按钮绑定事件
					$("#groupcheck_btn").click(function(){
						var code = $("#check_group_code").html();
						$.post("/finance/group/list/check.json",
								jQuery.param({"groupCode":code}),function(data){
							art.dialog.get("groupcheck-dialog").close();
							if(data == "-1" ){
								$.msg("核算失败，已经核算过，请刷新后重试",2500);
							}else if(data == "-2" ){
								$.msg("核算失败，打款金额不足",2500);
							}else if(data=='-3'){
								$.msg("核算失败，计算抵扣款失败",2500);
							}else if(data == "-10"){
								$.msg("核算失败，产品成本有打款申请但未打完款",2500);
							}else if(data == "-11"){
								$.msg("核算失败，没有做固定成本",2500);
							}else if(data == "-21"){
								$.msg("核算失败，没有确认成本",2500);
							}else if(data == "1"){
								$("#check_seee_"+code).removeClass("hid");
								$("#group_check_"+code).addClass("hid");
								$("#result_table").jqGrid('setRowData',code, { settlementStatus: 'CHECKED'});
								$.msg("核算成功",2500);
							}else{
								$.msg("出现了未知的错误",2500);
							}
						});
					});
    		});
        });
	  //实际成本
	    $('.check_see').click(function(){
	    	var code = $(this).attr("rowid");
	    	$.get("/finance/group/list/budget/"+encodeURIComponent(code)+"/CHECK.htm",
    			function(data){
    				art.dialog({
    					title: "["+code+"]",
    				    content: data,
    				    lock:true
    			});
    		});
        });
	};
	
	$("#search_button").click(function(){
		$("#result_table").grid({
			former: '#search_form',
			pager: '#pagebar_div',
			colNames : [ '团号', '产品ID', '产品名称', '出团日期', '回团日期','状态','结算状态','核算时间','产品经理','实际毛利率','操作','','','',''],
			colModel : [{
				name : 'travelGroupCode',
				index : 'TRAVELGROUPCODE',
				align:"center",
				sorttype: "string",
				width:190
			}, {
				name : 'productId',
				index : 'PRODUCTID',
				align:"center",
				sorttype: "number",
				width:90
			}, {
				name : 'productName',
				index : 'PRODUCTNAME',
				align:"center",
				sorttype: "string",
				sortable:false
			}, {
				name : 'visitTimeStr',
				index : 'VISITTIME',
				align:"center",
				sorttype: "string"
			}, {
				name : 'backTimeStr',
				index : 'VISIT_TIME',
				align:"center",
				sorttype: "string",
				sortable:false
			},{
				name : 'travelGroupStatusZH',
				index : 'TRAVEL_GROUP_STATUSZH',
				align:"center",
				sorttype: "string",
				width:90
			},{
				name : 'settlementStatusZH',
				index : 'SETTLEMENT_STATUSZH',
				align:"center",
				sorttype: "string",
				width:90
			},{
				name : 'checkTime',
				index : 'CHECKTIME',
				align:"center",
				sorttype: "string",
				width:90
			},{
				name : 'userName',
				index : 'USERNAME',
				align:"center",
				sorttype: "string",
				width:90
			},{
				name : 'actProfitRate',
				index : 'ACTPROFITRATE',
				align:"center",
				sorttype: "int",
				formatter:actProfitRate_formatter,
				width:90,
			},{
				name:'operate',
				index:'Id',
				align:"center",
				sortable:false,
				width:165
			},{
				name : 'travelGroupStatus',
				index : 'TRAVEL_GROUP_STATUS',
				hidden:true
			},{
				name : 'settlementStatus',
				index : 'SETTLEMENT_STATUS',
				hidden:true
			},{
				name:'groupType',
				index:'GROUPTYPE',
				hidden:true
			},{
				name:'groupTypeZh',
				index:'GROUPTYPEZH',
				hidden:true
			}],
			multiselect: true,
	        gridComplete:gridcomplete
		});
	});
	//验证是否有查询结果集
	function validateHasSearched(){
		if(jQuery("#result_table").jqGrid('getGridParam','records') == 0
				|| jQuery("#result_table").jqGrid('getGridParam','records') == undefined){
			$.msg("请先查询结算项",1500);
			return false;
		}
		return true;
	}
	$("#group_cost_button").click(function(){
		if(!validateHasSearched()){
			return false;
		}
		var grid = jQuery("#result_table");
		var ids = grid.jqGrid('getGridParam','selarrrow');
		if(ids.length == 0){
			$.msg('请选择团');
			return false;
		}
		art.dialog({
			fixed: true,
			lock:true,
			title:"确认信息",
		    content: '确定生成团成本入账信息？',
		    cancelValue: '取消',
		    cancel: true,
		    okValue: '确定',
		    ok: function () {
				var tipStr = "";
				var b = false;
				//获取订单子子项ID数组、结算队列项ID数组
				var params = new Array();
				var j = 0;
				for(var i in ids){
					var rowData = grid.getRowData(ids[i]);
					if((rowData.settlementStatus=='COSTED')&& rowData.groupType=="BYONESELF"){	//|| rowData.settlementStatus=='CONFIRMED'
						params.push(ids[i]);
					}else{
						b = true;
						tipStr = tipStr 
										+ '[团号：' + rowData.travelGroupCode
										+ ',结算状态：' + rowData.settlementStatusZH
										+ ',组团类型：' + rowData.groupTypeZh
										+ ']<br>';
					}
				}
				if(b){
					$.msg("以下团不符合确认成本条件(已做成本并为自主团)：<br/>" + tipStr + " 不能确认成本!");
					return false;
				}
				$.post(
						"/finance/group/list/finGLGroupCost.json",
						jQuery.param({"groupCodes":params.toString()}),
						function(data){
							if(data.resultInfo){
								$.msg('操作成功',1500);
							}else if(null!=data.errorCode&&''!=data.errorCode){
								$.msg("以下团成本入账失败："+data.errorCode);
							}else{
								$.msg("入账失败："+data.errorMsg);
							}
							jQuery("#result_table").trigger("reloadGrid");	//刷新grid
						}
				);
		    }
		});
	});
});