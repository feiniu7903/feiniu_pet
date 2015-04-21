<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
	</head>
	<script type="text/javascript"
		src="<%=basePath%>js/base/jquery-1.4.4.min.js"></script>
	<script type="text/javascript"
		src="<%=basePath%>js/base/lvmama_dialog.js"></script>
	<script type="text/javascript"
		src="<%=basePath%>js/base/remoteUrlLoad.js"></script>
	<body>

		<strong>申请售后服务</strong>
		<table style="font-size: 12px" cellspacing="1" cellpadding="4"
			border="0" bgcolor="#666666" width="100%" class="newfont05">
			<tbody>
				<tr bgcolor="#eeeeee">
					<td height="35" width="10%">
						申请售后服务
					</td>
					<td height="35">
						申请内容
					</td>
					<td width="10%">
						申请人
					</td>
					<td width="10%">
						申请时间
					</td>
				</tr>
				<s:iterator value="ordSaleServiceList">
					<tr bgcolor="#ffffff">
						<td height="30">
							${serviceType }
						</td>
						<td>
							${applyContent}
						</td>
						<td>
							${operatorUsers.userName}
						</td>
						<td>
							<s:date name="createTime" format="yyyy-MM-dd HH:mm:ss" />
						</td>
					</tr>
				</s:iterator>
			</tbody>
		</table>
		<div class="popbox" id="div_sale"
			href="<%=basePath%>ordSale/showOrderSaleDealList.do"
			param="{'saleServiceId':'${saleServiceId}'}">
		</div>
		<table style="font-size: 12px" cellspacing="1" cellpadding="4"
			border="0" bgcolor="#666666" width="100%" class="newfont05">
			<tbody>
				<tr class="CTitle">
					<td height="22" align="left" style="font-size: 16px;" colspan="13">
						添加售后服务内容
					</td>
				</tr>
				<tr bgcolor="#eeeeee">
					<td width="18%">
						处理内容
					</td>
					<td width="8%">
						<textarea id="ord_applyContent" name="ord_applyContent" rows="5"
							cols="80"></textarea>
					</td>
				</tr>
			</tbody>
		</table>
		<br />
		<input type="button" name="btnSaleAdd" class="right-button08" onClick="btn_sale_add()"
			value="处理服务内容" />
		<input type="button" name="btnCloseHistorySDDiv" class="right-button08" value="关闭"
			onclick="javascript:closeDetailDiv('historySaleDealDiv');">

	</body>
<script type="text/javascript"> 
$(document).ready(function() {
		$("#div_sale").loadUrlHtml();
});
function btn_sale_add(){
	var content=document.getElementById("ord_applyContent").value;
	if(content==""){
		alert("请输入订单售后服务的处理内容!");
		return false;
	}
	var dataMember = {saleServiceId:${saleServiceId},ord_applyContent:content};
	$.ajax({type:"POST", url:"<%=basePath%>ordSale/addOrderSaleDeal.do", data:dataMember, dataType:"json", success:function (json) {
			if(json.jsonMsg){
				$("#div_sale").reload({"saleServiceId":${saleServiceId}});
			}
	}});
}
</script>
</html>
