$(function(){
	$("#supplierId").combox("/finance/autocomplete/supplier.json");
	
	// 【查询】
	$("#search_button").click(function(){
		$("#result_table").grid({
			former: '#search_form',
			pager: '#pagebar_div',
			colNames: ['团号','结算单号','供应商','成本项/采购产品','付款金额'],
			colModel: [{
				name: 'code',
				index: 'code',
				align: 'center',
				sorttype: 'string',
				width: 180
			},{
				name: 'settlementId',
				index: 'settlementId',
				align: 'center',
				sorttype: 'float',
				width: 80
			},{
				name: 'supplierName',
				index: 'supplierName',
				align: 'center',
				sorttype: 'string',
				width: 200
			},{
				name: 'productName',
				index: 'productName',
				align: 'center',
				sorttype: 'string',
				width: 180
			},{
				name:'payAmount',
				index:'payAmount',
				align:"center",
				sorttype: 'float',
				width:100
			}]
		});
	});
});