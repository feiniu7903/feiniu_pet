$(function() {
	$("#result_table").grid({
		url: 'warningSearch.do',
		pager: '#pagebar_div',
		colNames : [ '供应商ID', '供应商名称',  '预存款余额','预警值'],
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
			name : 'advanceDepositAmountYuan',
			index : 'advanceDepositAmount',
			align:"center",
			sorttype: "float",
			formatter:"number",
			formatoptions: {decimalSeparator:".", thousandsSeparator: ",",decimalPlaces:2}
		},{
			name : 'advanceDepositAlertYuan',
			index : 'advanceDepositAlert',
			align:"center",
			sorttype: "float",
			formatter:"number",
			formatoptions: {decimalSeparator:".", thousandsSeparator: ",",decimalPlaces:2}
		}]
	});

});