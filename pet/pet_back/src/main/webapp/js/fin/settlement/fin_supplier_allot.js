$(function(){
	/**
	 * 供应商名称自动查询下拉框
	 */
	$("#supplierId").combox("/pet_back/sup/searchSupplierJSON.do");

	// 【查询】
	$("#search_button").click(function(){
		var _form = $("#search_form");
		_form.attr("action",_form.attr("search_action"));
		var flag = $("#flag").val();
		if(flag == "Y"){
			$("#result_table").grid({
				former: '#search_form',
				pager: '#pagebar_div',
				multiselect: true,
				colNames: ['ID', '供应商名称','supplier_name','供应商ID','指派状态','用户名','姓名', '指派时间', '我方结算主体','操作'],
				colModel: [{
					name: 'supplierId',
					index: 'supplierId',
					align: 'center',
					hidden: true
				},{
					name: 'supplierName',
					index: 'supplierName',
					align: 'center',
					sorttype: 'string',
					width: 200
				},{
					name: 'sup_name',
					index: 'sup_name',
					hidden: true
				},{
					name: 'supplierId',
					index: 'supplierId',
					align: 'center',
					width: 50
				},{
					name: 'allotStatus',
					index: 'allotStatus',
					align: 'center',
					width: 50,
					sortable: false
				},{
					name: 'userName',
					index: 'userName',
					align: 'center',
					width: 50
				},{
					name: 'realName',
					index: 'realName',
					align: 'center',
					width: 50
				},{
					name: 'createTimeStr',
					index: 'createTime',
					align: 'center',
					width: 80
				},{
					name: 'zhCompanyName',
					index: 'zhCompanyName',
					align: 'center',
					width: 180,
					sortable: false
				},{
					name:'operate',
					index:'Id',
					align:"center",
					sortable:false,
					width:100
				}],
		        gridComplete:gridcomplete
			});
		} else {
			$("#result_table").grid({
				former: '#search_form',
				pager: '#pagebar_div',
				multiselect: true,
				colNames: ['ID', '供应商名称','supplier_name','供应商ID','指派状态','用户名','姓名', '指派时间', '我方结算主体', '操作'],
				colModel: [{
					name: 'supplierId',
					index: 'supplierId',
					align: 'center',
					hidden: true
				},{
					name: 'supplierName',
					index: 'supplierName',
					align: 'center',
					sorttype: 'string',
					width: 200
				},{
					name: 'sup_name',
					index: 'sup_name',
					hidden: true
				},{
					name: 'supplierId',
					index: 'supplierId',
					align: 'center',
					width: 50
				},{
					name: 'allotStatus',
					index: 'allotStatus',
					align: 'center',
					width: 50,
					sortable: false
				},{
					name: 'userName',
					index: 'userName',
					align: 'center',
					width: 50
				},{
					name: 'realName',
					index: 'realName',
					align: 'center',
					width: 50
				},{
					name: 'createTimeStr',
					index: 'createTime',
					align: 'center',
					width: 80
				},{
					name: 'zhCompanyName',
					index: 'zhCompanyName',
					align: 'center',
					width: 180,
					sortable: false
				},{
					name:'operate',
					index:'Id',
					align:"center",
					sortable:false,
					hidden: true
				}],
		        gridComplete:gridcomplete
			});
		}
		
		document.getElementById("batch_btn_div").style.display = "block";
	});
	
	var gridcomplete = function(){
		var ids = $("#result_table").jqGrid("getDataIDs");
		for(var i=0; i<ids.length; i++){
			var id=ids[i];  
			
			// 操作
	        var operate = "<a href='javascript:void(0)' rowid = '" + id + "' class='update_allot'>修改</a>" +
	        "<a href='javascript:void(0)' rowid = '" + id + "' class='delete_allot'>删除</a>" +
	        "<a href='javascript:void(0)' class='allot_log' rowid='" + id + "'>查看日志</a>";
	        
	        // 指派状态
	    	var rowData = $("#result_table").jqGrid("getRowData", id);
	    	var allotStatus;
	    	if(rowData.userName == null || rowData.userName == ""){
	    		allotStatus = "未指派";
	    	} else {
	    		allotStatus = "已指派";
	    	}
	    	
	    	// 供应商名称
	    	var supplierName = "<a href='#' rowid = '" + id + "' sup_name='"+rowData.supplierName+"'"
	    						+"class = 'supplierName_class'>"+rowData.supplierName+"</a>";
			var sup_name = rowData.supplierName;
			
	        $("#result_table").jqGrid('setRowData', id, { operate: operate, allotStatus: allotStatus, supplierName: supplierName, sup_name: sup_name });
		}
		
		/**
		 * 供应商详情
		 */
		$(".supplierName_class").click(function(){
			var id = $(this).attr("rowid");
			$.get("searchSupplierDetail.do?supplierId=" + id,
					function(data){
						art.dialog({
							title: "供应商详情",
						    content: data,
						    lock:true
					});
				});
		});
		
		/**
		 * 点击[修改]超链接时触发事件
		 */ 
		$(".update_allot").click(function(){
			$("#update_allot_form").resetForm();
			var id = $(this).attr("rowid");
	    	var rowData = $("#result_table").jqGrid("getRowData", id);
	    	$("#supplierName").html(rowData.sup_name);
	    	$("#supplierId_add").html(rowData.supplierId);
	    	$("#supplierIdAdd").val(rowData.supplierId);
	    	$("#userName_add_txt").val("");
	    	$("#realName").html("");
	    	art.dialog({
	    		id:"add-allot-dialog",
	    		fixed: true,
	    		title: "修改指派人",
	    	    content: document.getElementById("update_allot_div"),
	    	    lock:true
	    	});
		});
		
		/**
		 * 【取消】
		 */ 
		$("#cancel_btn").click(function(){
			art.dialog.get("add-allot-dialog").close();
		});
		
		/**
		 * 用户名校验
		 */ 
		$("#update_allot_form").validate({
			rules: {
				userNameAdd: {required:true}
			},
			messages: {
				userNameAdd: {required:"请输入用户名"}
			}
		});
		
		/**
		 * 执行更新或是添加
		 */ 
		$("#update_allot_form").ajaxForm({
			type: 'post',
			url:  'update.do',
			success: function(data) {
				art.dialog.get("add-allot-dialog").close();
				$("#search_button").click();
			}
		});
		
		/**
		 * 点击[删除]超链接时触发事件
		 */ 
		$(".delete_allot").click(function(){
			var rid = $(this).attr("rowid");
	    	var rowData = $("#result_table").jqGrid("getRowData", rid);
	    	var supplierId = rowData.supplierId;
	    	var userName = rowData.userName;
	    	// 只有指派状态为已指派的才能删除
	    	if(userName == "" || userName == null){
	    		$.msg("该供应商没有分单信息，不能删除！", 1000);
	    		return;
	    	}
			
			art.dialog({
	    		id:'delete-allot-confirm-dialog',
	    		fixed: true,
	    		lock:true,
	    		title:"删除",
	    	    content: '删除后该供应商的状态将变为“未指派”，确认删除？',
	    	    cancelValue: '取消',
	    	    cancel: true,
	    	    okValue: '确认',
	    	    ok: function () {
	    	    	$.post("delete.do", { supplierId: supplierId },
						function(data){
    	    				art.dialog.get("delete-allot-confirm-dialog").close();
    	    				$("#search_button").click();
					});
	    	    	return false;
	    	    }
	    	});
		});

		// 查看日志
		$('.allot_log').click(function(){
	    	var id = $(this).attr("rowid");
	    	Utils.showLog("FIN_SUPPLIER_ALLOT", id);
	    });
		
	};
	
	// 鼠标离开时，根据用户名查询姓名并显示在页面上
	$("#userName_add_txt").blur(function(){
		// 用户名
		var userName = $("#userName_add_txt").val();
		// 当用户名不为空时，鼠标离开执行查询
		if(userName != null && userName != ""){
			$.post("queryUser.do", { userName: userName },
				function(data){
					if(data == null){
				    	$("#update_btn").attr("disabled", "disabled");
						$.msg("该用户不存在！", 1000);
						return;
					} else {
				    	$("#realName").html(data.realName);
				    	$("#update_btn").removeAttr("disabled");
					}
			});
		}
	});
	
	// 鼠标离开时，根据用户名查询姓名并显示在页面上
	$("#userName_add_batch").blur(function(){
		// 用户名
		var userName = $("#userName_add_batch").val();
		// 当用户名不为空时，鼠标离开执行查询
		if(userName != null && userName != ""){
			$.post("queryUser.do", { userName: userName },
				function(data){
					if(data == null){
				    	$("#update_batch_btn").attr("disabled", "disabled");
						$.msg("该用户不存在！", 1000);
						return;
					} else {
				    	$("#realNameBatch").html(data.realName);
				    	$("#update_batch_btn").removeAttr("disabled");
					}
			});
		}
	});
	
	// 批量修改
	$("#updateBatch").click(function(){
		$("#update_batch_form").resetForm();
		
		var ids = $("#result_table").jqGrid('getGridParam','selarrrow');
		var params = "";
		$.each(ids,function(inx,item){
			params+=item+",";
		});
		$("#supplierIds").val(params);
		
		if(ids.length > 0){
			$("#userName_add_batch").val("");
			$("#realNameBatch").html("");
			art.dialog({
	    		id:"add-batch-dialog",
	    		fixed: true,
	    		title: "批量修改指派人",
	    	    content: document.getElementById("update_batch_div"),
	    	    lock:true
	    	});
			
			// 【取消】
			$("#cancel_batch_btn").click(function(){
				art.dialog.get("add-batch-dialog").close();
			});
			
			// 用户名校验
			$("#update_batch_form").validate({
				rules: {
					userNameBatch: {required:true}
				},
				messages: {
					userNameBatch: {required:"请输入用户名"}
				}
			});
			
			// 执行更新或是添加
			$("#update_batch_form").ajaxForm({
				type: 'post',
				url:  'updateBatch.do',
				success: function(data) {
					art.dialog.get("add-batch-dialog").close();
					$("#search_button").click();
				}
			});
			
		} else {
			$.msg("请选择修改的数据！", 1000);
		}
	});
	
	// 批量删除
	$("#deleteBatch").click(function(){
    	// 获取选中的供应商id
    	var ids = $("#result_table").jqGrid('getGridParam','selarrrow');
		if(ids.length > 0){
			
			art.dialog({
	    		id:'delete-batch-confirm-dialog',
	    		fixed: true,
	    		lock:true,
	    		title:"删除",
	    	    content: '删除后该供应商的状态将变为“未指派”，确认删除？',
	    	    cancelValue: '取消',
	    	    cancel: true,
	    	    okValue: '确认',
	    	    ok: function () {
	    	    	var params = "";
					$.each(ids,function(inx,item){
						params+=item+",";
					});
	    	    	$.post("deleteBatch.do", { supplierIds: params },
						function(data){
    	    				art.dialog.get("delete-batch-confirm-dialog").close();
    	    				$("#search_button").click();
					});
	    	    	return false;
	    	    	
	    	    }
	    	});
		} else {
			$.msg("请选择删除的数据！", 1000);
		}
	});
	
	// 导出数据
	$("#export_button").click(function(){
		var _form = $("#search_form");
		_form.attr("action",_form.attr("excel_action")+"?exporttype=2");
		_form.submit();
	});
	
});