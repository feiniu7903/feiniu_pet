$(function(){
	$("a.editBCertificate").live("click",function(){
		var supplierId=$(this).attr("data");
		$("#editBCertificate").showWindow({
			data:{"supplierId":supplierId}
		});
	});
	var addBCertificateDiv;
	$("a.addBCertificateBtn").live("click",function(){
		var supplierId=$(this).attr("data");
		addBCertificateDiv= $("#addBCertificateDiv").showWindow({
			data:{"supplierId":supplierId}
		});
	});
	$("a.editBCertificateBtn").live("click",function(){
		var targetId=$(this).attr("data");
		addBCertificateDiv = $("#addBCertificateDiv").showWindow({
			url:"/pet_back/sup/target/toEditBCertificate.do",
			data:{"targetId":targetId}
		});
	});
	
	
	$("a.editBCertificateBtn").live("click",function(){
		var targetId=$(this).attr("data");
		addBCertificateDiv = $("#addBCertificateDiv").showWindow({
			url:"/pet_back/sup/target/toEditBCertificate.do",
			data:{"targetId":targetId}
		});
	});
	
	
	//免审配置全选控制
	$("#cfg-check-all").live("click",function(){
		var ckAll=$(this).attr("checked");
		$("input[name='cfgItem']").attr("checked",ckAll);
	});
	
	$("input.bcertificateSubmit").live("click",function(){
		var $form=$(this).parents("form");
		if($form.find("input[name='supBCertificateTarget.faxFlag']:checked").length < 1&&$form.find("input[name='supBCertificateTarget.dimensionFlag']:checked").length < 1 &&$form.find("input[name='supBCertificateTarget.supplierFlag']:checked").length < 1) {
			alert("B凭证方式必选！");
			return false;
		}
		if($.trim($form.find("input[name='supBCertificateTarget.faxNo']").val()) == "") {
			alert("传真号码不能为空！");
			return false;
		}
		$form.validateAndSubmit(function($form,dt){
			var supplierId=$form.find("input[name=supBCertificateTarget.supplierId]").val();	
			var data=eval("("+dt+")");
			if(data.success){
				alert("操作成功");
				addBCertificateDiv.dialog("close");
				$("#editBCertificate").resetWindow({
					data:{"supplierId":supplierId}
				});
			}else{
				alert(data.msg);
			}
		});		
	});
});