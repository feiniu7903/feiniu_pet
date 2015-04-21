/**
 * ebk产品审核相关通用方法
 * 
 */

/**
 * 打开产品审核页面
 * @param  ebkProdProductId 产品id
 */
function prodAuditDetail(ebkProdProductId,status) {
	if($("#prodAuditDetail").length==0){
		$("body").append("<div id='prodAuditDetail' style='display: none'></div>");
		
	}
	if(status=='PENDING_AUDIT'){
		$("#prodAuditDetail").load("${basePath}/ebooking/prod/prodAuditApplyDetail.do?ebkProdProductId="+ ebkProdProductId+"&ebkProdProductStatus="+status, function() {
			var prodAuditApplyDetailDIV=this;
			$("#prodAuditDetail").dialog({
				modal : true,
				title : "供应商产品审核",
				width : 1130,
				buttons : [
				    {
				    	text:"提示:请审核完所有页面后再提交审核结果",
						style:"color:red;width:250px;height:45px;border:0px;background-image:url('');background-color:white;background:white;font-weight:bold;font-size:14em;cursor:text;",
						click:function(){
						}
				    },
				    {
						text:"提交审核结果",
						width:"200px",
						height:"45px",
						click : function() {
							if($("#prodAuditDetailResult").length==0){
								$("body").append("<div id='prodAuditDetailResult' style='display: none'></div>");
							}
							$("#prodAuditDetailResult").load("${basePath}/ebooking/prod/prodAuditApplyResult.do?ebkProdProductId="+ ebkProdProductId, function() {
								var prodAuditApplyResultDIV=this;
								$("#prodAuditDetailResult").dialog({
									modal : true,
									title : "提交审核结果",
									width : 460,
									height : 530,
									buttons : [
							           {
							        	   	text:"确认提交",
											width:"200px",
											height:"45px",
											click : function() {
												var auditRadio=$('input:radio[name="auditRadio"]:checked').val();
												var isOnline=$('input:radio[name="isOnline"]:checked').val();
												if(auditRadio=='pass' && isOnline=='true'){
													var onlineDateBegin=$("#onlineDateBegin").val();
													var onlineDateEnd=$("#onlineDateEnd").val();
													if(onlineDateBegin==''){
														alert('请选择上线开始时间');
														return false;
													}
													if(onlineDateEnd==''){
														alert('请选择上线结束时间');
														return false;
													}
													if(onlineDateBegin>onlineDateEnd){
														alert('上线开始时间不能大于结束时间');
														return false;
													}
												}
	
												var submitUrl="";
												if(auditRadio=='pass'){
													submitUrl="auditResultSubmitPass";
												}
												else{
													submitUrl="auditResultSubmitNoPass";
												}
	
												$.ajax({
														type:"POST", 
														url:'${basePath}/ebooking/prod/'+submitUrl+'.do' + '?random=' + Math.random(), 
														data:$("#prodApprovalAuditSubmitForm").serialize(), 
														async: false, 
														success:function (result) {
															var message=eval(result);
															if(message=='true'){
																alert('审核完毕!');
																$(prodAuditApplyResultDIV).dialog("close");
																$(prodAuditApplyDetailDIV).dialog("close");
																$("#prodApprovalAuditForm").submit();
															}
															else{
																alert(message);
															}
														}
												});
											} 
							           },
							           {
							        	   	text:"取消",
											width:"200px",
											height:"45px",
											click : function() {
												$(prodAuditApplyResultDIV).dialog("close");
											}
							           }
									]
								});
							});
						}	
					},
					{
						text:"取消",
						width:"200px",
						height:"45px",
						click : function() {
							$(prodAuditApplyDetailDIV).dialog("close");
						}	
					}
				]
			});
		});
	}
	else{
		$("#prodAuditDetail").load("${basePath}/ebooking/prod/prodAuditApplyDetail.do?ebkProdProductId="+ ebkProdProductId+"&ebkProdProductStatus="+status, function() {
			var prodAuditApplyDetailDIV=this;
			$("#prodAuditDetail").dialog({
				modal : true,
				title : "供应商产品审核-查看",
				width : 1130,
				buttons : {
					"取消" : function() {
						$(prodAuditApplyDetailDIV).dialog("close");
					}
				}
			});
		});
	}
}


$(function(){
	$("a.showDetail").click(function(){
		var supplierId=$(this).attr("data");
		$("#supplierDetail").showWindow({
			data:{"supplierId":supplierId},
			callBack:function(){
				$("#supplierDetail").find("link").remove();
			}
		});
	});
});



