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
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>订单详情</title>  
</head>
<body>
	<div class="main main02">
		<div class="row1">
				<table border="1" cellspacing="0" cellpadding="0" class="search_table" width="100%">
					<tr>
						<td width="150px" align="right">订单编号：</td>
						<td>
							${shopOrder.orderId}
						</td>
						
						<td align="right">用户名：</td>
						<td>
							${shopOrder.userName}
						</td>
					</tr>
					<tr>
				        <td align="right">产品编号：</td>
						<td>
							${shopOrder.productId}
						</td>
						
						<td align="right">兑换时间：</td>
						<td>
                           ${shopOrder.formatterCreatedTime}
						</td>
					</tr>
					<tr>
						<td align="right">产品类型：</td>
					    <td>${shopOrder.formatProductType}</td>
					    
					    <td align="right">产品名称：</td>
					    <td>${shopOrder.productName}</td>
					</tr>
					
					<tr>
						<td align="right">兑换数量：</td>
						<td>${shopOrder.quantity}</td>

                       	<td align="right">所用积分：</td>
						<td>
							${shopOrder.oughtPay}
						</td>
					</tr>
					<tr>
						<td align="right">收货人姓名：</td>
						<td>${shopOrder.name}</td>

                       	<td align="right">收货人地址：</td>
						<td>
							${shopOrder.address}
						</td>
					</tr>
					<tr>
						<td align="right">收货人手机：</td>
						<td>${shopOrder.mobile}</td>

                       	<td align="right">订单状态：</td>
						<td>
							${shopOrder.formatOrderStatus}
						</td>
					</tr>
					<tr>
						<td align="right">订单产品信息：</td>
						<td colspan="3">${shopOrder.productInfo}</td>
					</tr>
					 <tr>
                       	<td align="right">备注：</td>
						<td colspan="3">${shopOrder.remark}</td>
					</tr>
					
				</table>
		</div>
	</div>
</body>
</html>
