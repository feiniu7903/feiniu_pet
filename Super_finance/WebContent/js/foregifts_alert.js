$(function() {
	$("#result_table").grid({
		url: '/finance/settlement/foregifts/alert/search.json',
		pager: '#pagebar_div',
		colNames : [ '供应商ID', '供应商名称',  '押金','担保函','预警时间'],
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
			name : 'foregiftsBal',
			index : 'FOREGIFTS_BAL',
			align:"center",
			sorttype: "float",
			formatter:"number",
			formatoptions: {decimalSeparator:".", thousandsSeparator: ",",decimalPlaces:2}
		},{
			name : 'guaranteeLimit',
			index : 'GUARANTEE_LIMIT',
			align:"center",
			sorttype: "float",
			formatter:"number",
			formatoptions: {decimalSeparator:".", thousandsSeparator: ",",decimalPlaces:2}
		},{
			name : 'foregiftsAlertStr',
			index : 'FOREGIFTS_ALERT',
			align:"center",
			sorttype: "string"
		}]
	});

});