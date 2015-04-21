$(function(){
	
	//修改批次状态
	$("a.changeStatus").click(function(){
		if(!confirm("确定要作废此批次卡吗?")){
			return false;
		}
		var $this=$(this);
		var result=$this.attr("result");
		$.post(stored_url+"/batchCancel.do",{"batchId":result},function(dt){
			var data=eval("("+dt+")");
			if(data.success){
				alert("操作成功!!");
				$this.remove();
			}else{
				alert(data.msg);
			}
		});
	});
})