function currencyFmatter(cellvalue, options, rowObject){
		var newValue='&nbsp;';
		if(!isNaN(cellvalue) && cellvalue!=null)
		{
			newValue=Number(cellvalue)/100;
			newValue=newValue.toFixed(2);
		}
		return newValue;
};
function showdetailFn(gwName,reconDate) {
	var url="fin_recon_statement!load.do?finReconStatementModel.reconDate="+reconDate+"&finReconStatementModel.gwName="+gwName+"&finReconStatementModel.autoShowData=true";
	location.href=url;
}
function showCashDetailFn(gwName,reconDate) {
	var url="fin_recon_cash_statement!load.do?finReconStatementModel.reconDate="+reconDate+"&finReconStatementModel.gwName="+gwName+"&finReconStatementModel.autoShowData=true";
	location.href=url;
}
function detailChangeLogFn(id){
	var url = "fin_recon_statement!changeLog.do?finReconStatementModel.reconStatementId="+id;
	$.get(url, function(data){
		art.dialog({
			title: "操作日志",
		    id: "log-"+id +"-dialog",
		    content: data,
		    lock:true
		});
	});
}