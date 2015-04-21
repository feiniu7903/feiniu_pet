$(function() {
	
	$("#export_button").click(function(){
		var _form = $("#search_form");
		_form.attr("action",_form.attr("excel_action"));
		_form.submit();
	});
	
//	var refreshSumPrice = function(){
//		var _form = $("#search_form");
//		var queryString = _form.formSerialize();
//		var _url = "/finance/group/settlement/orderInfoDetail/sumprice.json?"+queryString;
//		$.get(_url,function(data){
//			$("#sumOughtPay").html(data.sumOughtPay);
//			$("#sumActualPay").html(data.sumActualPay);
//			$("#subTotalCosts").html(data.subTotalCosts);
//			$("#payAmount").html(data.payAmount);
//			$("#surplusAmount").html(data.surplusAmount);
//		});
//	};
	
	$("#search_button").click(function(){
//		refreshSumPrice();
		//var _form = $("#search_form");
		//var queryString = _form.formSerialize();
		var _url = "/finance/group/settlement/orderInfoDetail/sumprice.json";//?+queryString
		$.get(_url,function(data){
			$("#sumOughtPay").html(data.sumOughtPay+data.unit);
			$("#sumActualPay").html(data.sumActualPay);
			$("#subTotalCosts").html(data.subTotalCosts+data.unit);
			$("#payAmount").html(data.payAmount+data.unit);
			$("#surplusAmount").html(data.surplusAmount+data.unit);

			var _form = $("#search_form");
			_form.attr("action",_form.attr("grid_action"));
			var currency = data.unit;
			var config = {
					former: '#search_form',
					pager: '#pagebar_div',
					colNames : ['产品经理', '供应商ID','供应商名称', '团号','订单号','游玩时间', '销售产品','销售产品ID', '采购产品','采购产品ID','结算周期','订购数量','销售单价（元）','实际结算价（'+ currency +'）','实收金额（元）','应付金额（'+ currency +'）','联系人','银行名','银行账号名','银行账号'],
					colModel : [ {
						name : 'userName',
						index : 'userName',
						width: '70px',
						align:"center"
					},{
						name : 'supplierId',
						index : 'supplierId',
						width: '70px',
						align:"center"
					},{
						name : 'supplierName',
						index : 'supplierName',
						align:"center"
					},{
						name : 'travelGroupCode',
						index : 'travelGroupCode',
						align:"center"
					}, {
						name : 'orderId',
						index : 'orderId',
						width: '80px',
						align:"center"
					}, {
						name : 'visitTime',
						index : 'visitTime',
						width: '80px',
						align:"center"
					}, {
						name : 'productName',
						index : 'productName',
						align:"center"
					}, {
						name : 'productId',
						index : 'productId',
						width: '80px',
						align:"center"
					}, {
						name : 'productName1',
						index : 'productName1',
						align:"center"
					}, {
						name : 'metaProductId',
						index : 'metaProductId',
						width: '80px',
						align:"center"
					}, {
						name : 'settlementPeriodStr',
						index : 'settlementPeriod',
						width: '80px',
						align:"center"
					}, {
						name : 'quantity',
						index : 'quantity',
						width: '70px',
						align:"center"
					}, {
						name : 'sellPrice',
						index : 'sellPrice',
						width: '80px',
						align:"center"
					}, {
						name : 'actualSettlementPrice',
						index : 'actualSettlementPrice',
						width: '80px',
						align:"center",
						width:68
					}, {
						name : 'actualPay',
						index : 'actualPay',
						align:"center",
						sortable:false,
						width:55
					}, {
						name : 'oughtPay',
						index : 'oughtPay',
						align:"center",
						width:75
					}, {
						name : 'name',
						index : 'name',
						align:"center",
						width:88
					}, {
						name : 'bankName',
						index : 'bankName',
						align:"center",
						width:58
					}, {
						name : 'bankAccountName',
						index : 'bankAccountName',
						align:"center",
						width:58
					}, {
						name : 'bankAccount',
						index : 'bankAccount',
						align:"center",
						width:83
					}]
				};
			$("#result_table").grid(config);
		});
	});
});