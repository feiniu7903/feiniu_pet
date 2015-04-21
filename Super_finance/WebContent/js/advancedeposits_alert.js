$(function() {
	$("#result_table").grid({
		url: '/finance/settlement/advancedeposits/alert/search.json',
		pager: '#pagebar_div',
		colNames : [ '供应商ID', '供应商名称',  '预存款余额','预警值'],
		colModel : [ {
			name : 'supplierId',
			index : 'SUPPLIER_ID',
			align:"center",
			sorttype: "string"
		}, {
			name : 'supplierName',
			index : 'SUPPLIER_NAME',
			align:"center",
			sorttype: "string"
		},{
			name : 'advancedepositsBal',
			index : 'ADVANCEDEPOSITS_BAL',
			align:"center",
			sorttype: "float",
			formatter:"number",
			formatoptions: {decimalSeparator:".", thousandsSeparator: ",",decimalPlaces:2}
		},{
			name : 'advancedepositsAlert',
			index : 'ADVANCEDEPOSITS_Alert',
			align:"center",
			sorttype: "float",
			formatter:"number",
			formatoptions: {decimalSeparator:".", thousandsSeparator: ",",decimalPlaces:2}
		}]
	});

});