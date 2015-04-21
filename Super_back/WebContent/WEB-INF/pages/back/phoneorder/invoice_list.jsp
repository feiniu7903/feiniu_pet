<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	</head>

	<body>
		<input type="hidden" id="hasInvoice" value="${oinvoice!=null }" />
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr class="tableTit">
				<td>
					发票抬头
				</td>
				<td>
					发票内容
				</td>
				<td>
					发票金额
				</td>
				<td>
					发票备注
				</td>
				<td>
					操作
				</td>
			</tr>
			<s:if test="oinvoice != null">
				<tr>
					<td>
						${oinvoice.title}
					</td>
					<td>
						${oinvoice.zhDetail}
					</td>
					<td>
						${oinvoice.amount}
					</td>
					<td>
						${oinvoice.memo}
					</td>
					<td>
						<a href="javascript:void(0)"
							onclick="doOperator('invoiceList', 'delete');">删除</a>
					</td>
				</tr>
			</s:if>
		</table>
	</body>
</html>
