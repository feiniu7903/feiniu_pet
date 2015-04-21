<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>审核材料</title>
	</head>
	<body>
		<div id="popDiv2" style="display: none"></div>
		<p class="lead"><span class="req">${visaApproval.name }</span> 的材料明细：(${visaApproval.cnOccupation})</p>
		<div class="box_content">
			<table class="p_table table_info">
				<tbody>
				<s:iterator value="visaApprovalDetailsList" var="details">
					<tr>
						<td class="p_label">${title}</td>
						<td class="tl">${content}</td>
						<s:if test='approvalStatus == "Y"'>
							<td class="status-yes" id="status_${detailsId}" width="5%">(√)</td>
						</s:if>
						<s:if test='approvalStatus == "N"'>
							<td class="status-no" id="status_${detailsId}" width="5%">(×)</td>
						</s:if>
						<s:if test='approvalStatus != "N" && approvalStatus != "Y"'>
							<td id="status_${detailsId}" width="5%">&nbsp;</td>
						</s:if>
						<td class="oper">
							<s:if test='approvalStatus == "Y"'><a class="J_oper_no" id="oper_${detailsId}" href="javascript:updateStatus(${detailsId},'N')">不通过</a> </s:if>
							<s:if test='approvalStatus == "N"'><a class="J_oper_yes gray" id="oper_${detailsId}" href="javascript:updateStatus(${detailsId},'Y')">通过</a> </s:if>
							<s:if test='approvalStatus != "N" && approvalStatus != "Y"||approvalStatus==null'>
								<span id="oper_${detailsId}">
									<a class="J_oper_yes gray" href="javascript:updateStatus(${detailsId},'Y')">通过</a>
									<a class="J_oper_no" href="javascript:updateStatus(${detailsId},'N')">不通过</a>
								</span> 
							</s:if>
							<a class="link-remark" href="javascript:addMemo(${detailsId})">备注</a>
						</td>
					</tr>
				</s:iterator>
				</tbody>
			</table>
			<table class="p_table mt20">
	            <thead>
	                <tr>
	                	<th colspan="4">保证金</th>
	                </tr>
	            </thead>
	            <tbody>
	                <tr>
	                	<td class="p_label w5">保证金形式</td>
	                	<td><s:select list="#{'':'--请选择--','NONE':'无需保证金','THIRD':'第三方保管','CASH':'押金入账'}" name="visaApproval.depositType"/></td>
	                	<td class="p_label w5">开户银行</td>
	                	<td><s:select list="#{'':'--请选择--','BOC':'中国银行','BEA':'东亚银行','SCB':'渣打银行','SPDB':'浦发银行黄浦营业部'}" name="visaApproval.bank"/></td>
	                </tr>
	                <tr>
	                	<td class="p_label w5">保证金金额</td>
	                	<td><s:textfield name="visaApproval.amount"/>(单位:元)</td>
	                	<td colspan="2"><button class="btn btn-small w3" onclick="javascript:saveDepositBtn()" type="button">保存</button></td>
	                </tr>
	            </tbody>
	        </table>
	        <p class="tc mt10">
        		<button class="btn btn-small w5" id="approvalBtn" type="button">审核通过</button>
        		<button class="btn btn-small w5" id="unApprovalBtn" type="button">审核不通过</button>
        		<button class="btn btn-small w5" id="closeApprovalBtn" type="button">关闭</button>
        	</p>
		</div>
	</body>
	<script type="text/javascript">
	 	$(function(){
	 		$("#closeApprovalBtn").click(function(){
	 			$("#popDiv").dialog("close");
	 		});
	 		
	 		$("#approvalBtn").click(function(){
	 			if (confirm("您确定需要审核通过 ${visaApproval.name }的全部审核材料吗?")) {
	 				$.ajax({
	 	    	 		url: "<%=basePath%>/visa/approval/updateApprovalStatus.do",
	 					type:"post",
	 	    	 		data: {
	 	    	 				"visaApproval.visaApprovalId":${visaApproval.visaApprovalId},
	 							"visaApproval.visaStatus":'PASS_APPROVAL'
	 						},
	 					contentType: "application/x-www-form-urlencoded; charset=utf-8",
	 	    	 		dataType:"json",
	 	    	 		success: function(result) {
	 						if (result.success) {
	 							alert("操作成功");
	 							$("#popDiv").dialog("close");
	 						} else {
	 							alert("操作失败，请重新尝试");
	 						}
	 	    	 		}
	 	    		}); 				
	 			}
	 		});
	 		
	 		$("#unApprovalBtn").click(function(){
	 			if (confirm("您确定需要审核不通过 ${visaApproval.name }的全部审核材料吗?")) {
	 				$.ajax({
	 	    	 		url: "<%=basePath%>/visa/approval/updateApprovalStatus.do",
	 					type:"post",
	 	    	 		data: {
	 	    	 				"visaApproval.visaApprovalId":${visaApproval.visaApprovalId},
	 							"visaApproval.visaStatus":'UNPASS_APPROVAL'
	 						},
	 					contentType: "application/x-www-form-urlencoded; charset=utf-8",
	 	    	 		dataType:"json",
	 	    	 		success: function(result) {
	 						if (result.success) {
	 							alert("操作成功");
	 							$("#popDiv").dialog("close");
	 						} else {
	 							alert("操作失败，请重新尝试");
	 						}
	 	    	 		}
	 	    		}); 				
	 			}
	 		});	 		
	 	});
	
		function updateStatus(detailsId, status) {
			$.ajax({
    	 		url: "<%=basePath%>/visa/approval/updateApprovalDetailsStatus.do",
				type:"post",
    	 		data: {
						"visaApprovalDetails.detailsId":detailsId,
						"visaApprovalDetails.approvalStatus":status
					},
				contentType: "application/x-www-form-urlencoded; charset=utf-8",
    	 		dataType:"json",
    	 		success: function(result) {
					if (result.success) {
						if (status == 'Y') {
							$("#status_" + detailsId).html("(√)");
							$("#status_" + detailsId).addClass("status-yes");
							$("#status_" + detailsId).removeClass("status-no");
							$("#oper_" + detailsId).html("<a class=\"J_oper_no\" href=\"javascript:updateStatus(" + detailsId + ",'N')\">不通过</a>");
						}
						if (status == 'N') {
							$("#status_" + detailsId).html("(×)");
							$("#status_" + detailsId).addClass("status-no");
							$("#status_" + detailsId).removeClass("status-yes");
							$("#oper_" + detailsId).html("<a class=\"J_oper_no\" href=\"javascript:updateStatus(" + detailsId + ",'Y')\">通过</a>");
						}
					} 
    	 		}
    		});			
		}
		
		function addMemo(detailsId) {
			$("#popDiv2").load("<%=basePath%>/visa/approval/addMemo.do?_=" + (new Date).getTime() + "&visaApprovalDetails.detailsId=" + detailsId,function() {
				$(this).dialog({
	            	modal:true,
	            	title:"备注",
	            	width:350,
	            	height:400
	            });
	        });		  
		}
		
		function saveDepositBtn() {
			if ($("#visaApproval_depositType").val() == '') {
				alert("请选择有效的保证金类型");
				$("#visaApproval_depositType").focus();
				return;
			}
			if ($("#visaApproval_bank").val() == '' && $("#visaApproval_depositType").val() != 'NONE') {
				alert("请选择开户银行");
				$("#visaApproval_bank").focus();
				return;
			}
			if ($("#visaApproval_amount").val() != '' && isNaN($("#visaApproval_amount").val())) {
				alert("请输入合法的保证金金额");
				$("#visaApproval_amount").focus();
				return;	
			}
			$.ajax({
    	 		url: "<%=basePath%>/visa/approval/saveDeposit.do",
				type:"post",
    	 		data: {
    	 				"visaApproval.visaApprovalId":${visaApproval.visaApprovalId},
						"visaApproval.depositType":$("#visaApproval_depositType").val(),
						"visaApproval.bank":$("#visaApproval_bank").val(),
						"visaApproval.amount":$("#visaApproval_amount").val()
					},
				contentType: "application/x-www-form-urlencoded; charset=utf-8",
    	 		dataType:"json",
    	 		success: function(result) {
					if (result.success) {
						alert("保存成功");
					} 
    	 		}
    		});			
		}
	</script>
</html>