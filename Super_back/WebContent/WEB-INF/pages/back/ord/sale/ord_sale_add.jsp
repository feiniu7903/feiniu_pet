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
		src="<%=basePath%>js/base/jquery.jsonSuggest.js"></script>
	<script type="text/javascript"
		src="<%=basePath%>js/base/remoteUrlLoad.js"></script>

	<body>
		<div class="orderpoptit">
			<strong>申请售后服务</strong>
			<p class="inputbtn">
				<input type="button" class="button" value="关闭"
					onclick="javascript:closeDetailDiv('historySaleDiv');">
			</p>
		</div>
		<div class="popbox" id="div_sale_list"
			href="<%=basePath%>ordSale/showOrderSaleList.do"
			param="{'orderId':'${orderId}'}" style="margin: 5px 0px 20px"></div>
		<div class="orderpopmain">
			<div class="popbox">
				<strong>添加售后服务内容</strong>
				<table style="font-size: 12px" cellspacing="1" cellpadding="4"
					border="0" bgcolor="#B8C9D6" width="100%" class="newfont03">
					<tbody>
						<tr align="center">
							<td  bgcolor="#f4f4f4" width="15%">
								申请售后服务类型
							</td>
							<td bgcolor="#ffffff" align="left">
								<s:select list="channelList" id="channelId" listKey="code"
									listValue="name" name="serviceType" headerKey=""></s:select>
							</td>
						</tr>
						<tr align="center">
							<td height="35" bgcolor="#f4f4f4" width="10%">
								申请内容
							</td>
							<td bgcolor="#ffffff" align="left">
								<textarea id="ord_applyContent" name="ord_applyContent" rows="5"
									cols="80"></textarea>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			<input type="button" class="right-button08" onClick="btn_sale_add()"
				value="提交服务申请" />

			<!--popbox end-->
			<p class="submitbtn2">
				<input type="button" class="button" value="关闭"
					onclick="javascript:closeDetailDiv('historySaleDiv');">
			</p>
		</div>


	</body>
	<script language="javascript"> 
	$(document).ready(function() {
		$("#div_sale_list").loadUrlHtml();
	});
function btn_sale_add(){
	var content=document.getElementById("ord_applyContent").value;
	var channelId=document.getElementById("channelId").value;
	if(content==""){
		alert("请输入游客申请内容!");
		return false;
	}
	var dataMember = {orderId:${orderId},ord_applyContent:content,channelId:channelId};
	$.ajax({type:"POST", url:"<%=basePath%>ordSale/addOrderSale.do", data:dataMember, dataType:"json", success:function (json) {
			//if(json.jsonMsg){
				$("#div_sale_list").reload({"orderId":${orderId}});
			//}
			
	}});
}
</script>
</html>
