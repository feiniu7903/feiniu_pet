
$(function() {
	/**
	 * 供应商名称自动查询下拉框
	 */
	$("#supplier").combox("/pet_back/sup/searchSupplierJSON.do");
	
	$("#searchPaymentHistoryForm").validate({
        rules: {
        	settlementId: {
        		number: true
		   }
        },
        messages: {
        	settlementId: {
        		number:"请输入数字"
			}
		}
    });
});

//查询打款记录
function searchPaymentHistoryHandler(){
	if(!$("#searchPaymentHistoryForm").validate().checkForm()){
		return false;
	}
	$("#result_table").grid({
		former: '#searchPaymentHistoryForm',
		pager: '#pagebar_div',
		colNames : [ 
		             '团号',
		             '结算单号',
		             '结算单号',
		             '供应商',
		             '支付平台', 
		             '流水号', 
		             '金额', 
		             '币种',
		             '打款时间', 
		             '添加时间',
		             '行号'
		             ],
		colModel : [ 
		             {name:'travelGroupCode',align:"center", index:'travelGroupCode',sortable:false, width:150},
		             {name:'settlementIdStr',align:"center", index:'settlementId',sortable:false, width:40},
		             {name:'settlementId',align:"center", hidden:true},
		             {name:'supplierName',align:"center",sortable:false},
		             {name:'bank',align:"center",sortable:false, width:60},
		             {name:'serial',align:"center",sortable:false, width:60},
		             {name:'amountYuan',index:'amount', sortable:false, width:60},
		             {name:'zhCurrency',align:"center",index:'zhCurrency', sortable:false, width:60},
		             {name:'operatetimeStr',align:"center",index:'operatetime', sortable:false, width:90},
		             {name:'createtimeStr',align:"center",index:'createtime', sortable:false, width:90},
		             {name:'rowId',align:"center",key:true, width:60, hidden:true}
		            ]
	});
}