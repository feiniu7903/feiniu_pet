$(function(){
	var initProductBranch = function(metaProductId){
		$.post(
				"/super_back/meta/getMetaBranchJSON.do",
				{
					metaProductId: metaProductId
				},
				function(data){
					$("#metaBranchTypeSelect").val('');
					$("#metaBranchTypeSelect").html("");
					$('#metaBranchTypeSelect').append('<option value="">请选择</option>');
					var d = eval("("+data+")");
					var selected_str = "";
					var branchId = $('#metaBranchTypeSelect').attr("branchId");
					if(typeof d.list != "undefined"){
						$.each(d.list,function(i,n){
							if(branchId!="" && n.branchId == branchId){
								selected_str= 'selected="selected"';
							}
							$('#metaBranchTypeSelect').append('<option '+selected_str+'  value="' + n.branchId + '">' + n.branchName + '</option>');
						})
					}
				}
			);
	}
	if($("#metaProductId").val()!=""){
		initProductBranch($("#metaProductId").val());
	}
	
	/**
	 * 供应商
	 */
	$("#supplierInput").jsonSuggest({
		url:"/super_back/supplier/searchSupplier.do",
		maxResults: 10,
		minCharacters:1,
		onSelect:function(item){
			$("#comSupplierId").val(item.id);
		}
	});
	/**
	 * 采购产品
	 */
	$("#metaProductInput").jsonSuggest({
		url:"/super_back/meta/searchMetaList.do",
		maxResults: 10,
		minCharacters:1,
		onSelect:function(item){
			$("#metaProductId").val(item.id);
			initProductBranch(item.id);
		}
	});
	
	
	/**
	 * 结算对象
	 */
	$("#settlementTargetInput").jsonSuggest({
		url:"/pet_back/sup/target/settlementSearch.do",
		maxResults: 10,
		minCharacters:1,
		onSelect:function(item){
			$("#comTargetId").val(item.id);
		}
	});
	
	/**
	 * 查询修改结算价历史记录
	 */
	$("#searchBtn").click(function(){
		var orderId = $("input[name=orderId]").val();
		if(orderId!=""){
			if(!/^[0-9\s]*$/.test(orderId)){
				alert("订单号输入错误");
				return false;
			}
		}
		
		var _form = $("#search_form");
		_form.attr("action",_form.attr("grid_action"));
		_form.submit();
	});

	/**
	 * 导出结算价历史记录
	 */
	$("#exportBtn").click(function(){
		var orderId = $("input[name=orderId]").val();
		if(orderId!=""){
			if(!/^[0-9\,]*$/.test(orderId)){
				alert("订单号输入错误");
				return false;
			}
		}
		
		var _form = $("#search_form");
		_form.attr("action",_form.attr("excel_action"));
		_form.submit();
	});
	
})