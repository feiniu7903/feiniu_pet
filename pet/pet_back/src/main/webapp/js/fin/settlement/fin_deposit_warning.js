$(function() {
	$("#result_table").grid({
		url: 'warningSearch.do',
		pager: '#pagebar_div',
		colNames : [ '供应商ID', '供应商名称',  '押金','担保函','预警时间'],
		colModel : [ {
			name : 'supplierId',
			index : 'supplierId',
			align:"center",
			sorttype: "string"
		}, {
			name : 'supplierName',
			index : 'supplierName',
			align:"center",
			sorttype: "string"
		},{
			name : 'depositAmountYuan',
			index : 'NVL(B.DEPOSIT_AMOUNT, 0)',
			align:"center",
			sorttype: "float",
			formatter:"number",
			formatoptions: {decimalSeparator:".", thousandsSeparator: ",",decimalPlaces:2}
		},{
			name : 'guaranteeLimitYuan',
			index : 'NVL(B.GUARANTEE_LIMIT, 0)',
			align:"center",
			sorttype: "float",
			formatter:"number",
			formatoptions: {decimalSeparator:".", thousandsSeparator: ",",decimalPlaces:2}
		},{
			name : 'depositAlertStr',
			index : 'depositAlert',
			align:"center",
			sorttype: "string"
		}]
	});

});