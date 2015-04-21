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
		src="<%=basePath%>js/base/lvmama_dialog.js"></script>
	<script language="javascript"> 

</script>
	<body>
		<div class="orderpoptit">
			<strong>储值卡消费信息</strong>
			<p class="inputbtn">
				<input type="button" class="button" value="关闭"  name="btnOrdHistoryPayDiv"
					onclick="javascript:closeDetailDiv('usageDiv');">
			</p>
		</div>

		<div class="orderpopmain">
			<div class="popbox">
				<strong>消费记录</strong>
				<table style="font-size: 12px" cellspacing="1" cellpadding="4"
					border="0" bgcolor="#B8C9D6" width="100%" class="newfont03">
					<tbody>
						<tr bgcolor="#f4f4f4" align="center">
							<td height="35" width="20%">
								订单号
							</td>
							<td width="20%">
								消费类型
							</td>
							<td width="20%">
								金额
							</td>
							<td width="40%">
								消费时间
							</td>
						</tr>
						
						<s:iterator value="usageList" var="usage">
							<tr height="35" bgcolor="#ffffff">
							<td align="center">
								${usage.orderId}
							</td>
							<td align="center">
								${usage.zhUsageType}
							</td>
							<td align="center">
								${usage.amountFloat}
							</td>
							<td align="center">
								<s:date name="#usage.createTime" format="yyyy-MM-dd HH:mm:ss" />
							</td>
						</tr>
						</s:iterator>
						
					</tbody>
				</table>
			</div>
			
			<!--弹出层灰色背景-->
			<div id="bgPay" class="bg" style="display: none;">
			</div>
			<!--popbox end-->
			<p class="submitbtn2">
				<input type="button" name="btnHistoryPayDiv" class="button" value="关闭"
				onclick="javascript:closeDetailDiv('usageDiv');">
			</p>
		</div>
	</body>
</html>
