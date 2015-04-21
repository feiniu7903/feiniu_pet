/**
 *  出团通知书批量发送处理
 *	@author taiqichao
 */

$(document).ready(function(){
	
	//全选/全不选控制
	$("#check-all").click(function(){
		$("input[name='orderCheckbox']").attr("checked",$(this).attr("checked"));
	});
	
	//获取选中的订单id,分号隔开
	function getSelectedOrderIds(){
		var orderIds="";
		$.each($("input[name='orderCheckbox']"), function(i, n){
			  var check=$(n).attr("checked");
			  if(check==true){
				  orderIds+=$(n).val()+";";
			  }
		});
		return orderIds;
	}
	
	var form=$("#batch-upload-form");
	
	//显示批量上传团通知面板
	$("#batchUploadShowBtn").click(function(){
		var ids=getSelectedOrderIds();
		if(ids==""){
			alert("请选择要处理的订单");
			return;
		}
		$("#batch-upload-form").find("input[name='objectIds']").val(ids);
		$("#batch-upload-panel").dialog({modal: true});
		return false;
	});
	
	
	$("#batch_usesystplbtn").click(function(){
		$(form).attr("action",path+"groupadvice/toBatchUseSysTpl.do");
		$(form).submit();
	});
	
	$("#batch_uploadbtn").click(function(){
		$(form).attr("action",path+"groupadvice/toBatchUploadFile.do");
		$(form).submit();
	});
	
	$("#batch_useusertplbtn").click(function(){
		$(form).attr("action",path+"groupadvice/toBatchUseUploadTpl.do");
		$(form).submit();
	});
	
	
	//批量发送团通知
	$("#batchSendNotifyBtn").click(function(){
		var btn=$(this);
		var ids=getSelectedOrderIds();
		if(ids==""){
			alert("请选择要处理的订单");
			return;
		}
		if(confirm("确定要批量发送出团通知吗?")){
			$(btn).attr("disabled",true);
			$.ajax({
			   type: "POST",
			   dataType:"json",
			   url: path+"groupadvice/doBatchSendGroupAdviceNote.do",
			   data: "objectIds="+ids,
			   error:function(){
				   alert('网络请求错误，请稍后再试');
				   $(btn).attr("disabled",false);
			   },
			   success: function(data){
			     alert(data.message);
			     $(btn).attr("disabled",false);
			   }
			});
		}
		return false;
	});
	
	
});