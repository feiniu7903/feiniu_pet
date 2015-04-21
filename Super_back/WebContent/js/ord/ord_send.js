//重发订单
function reSendOrder(id){
	//return confirm("是否确定重发此EBK订单给供应商？");
	if(confirm("是否确定重发此EBK订单给供应商？")){
		$.ajax({
			type : "post",
			url : "ordItem/reSendOrder.do?id="+id,
			cache : false,
			success : function(data) {
				if(data=="SUCCESS"){
					alert("操作成功！")
					location.reload();
				}else{
					alert(data);
				}
			},
			error : function(data) {
				alert("操作错误！");
			}
		});
	}
}
//发送传真
function sendFax(id){ 
	if(confirm("是否确定立即发送传真？")){
		$.ajax({
			type : "post",
			url : "ordItem/sendFax.do?id="+id,
			cache : false,
			success : function(data) {
				if(data=="SUCCESS"){
					alert("操作成功！")
					location.reload();
				}else{
					alert(data);
				}
			},
			error : function(data) {
				alert("操作错误！");
			}
		});
	}
}