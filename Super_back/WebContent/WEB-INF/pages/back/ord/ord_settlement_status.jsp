<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!-- 订单管理模块-单笔订单查询页面-结算状态详情页面  -->
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<script language="javascript" src="<%=basePath%>js/ord/ord.js"
			type="text/javascript">
</script>
		<script type="text/javascript">
</script>
	</head>
	<body>
		<div class="orderpoptit">
			<table style="font-size: 12px" cellspacing="1" cellpadding="4"
				border="0" bgcolor="#666666" width="100%" class="newfont05">
				<tbody>
					<tr class="CTitle">
						<td height="22" align="center" style="font-size: 16px;"
							colspan="9">
							订单子子项结算状态查看
						</td>
					</tr>
					<tr bgcolor="#eeeeee">
						<td height="35" width="4%">
							供应商
						</td>
						<td height="35" width="4%">
							采购产品
						</td>
						<td height="35" width="4%">
							结算日期
						</td>
						<td height="35" width="4%">
							结算状态
						</td>
						<td height="35" width="4%">
							结算单号
						</td>
						<td height="35" width="4%">
							结算周期
						</td>
					</tr>
					<s:iterator id="settlementItem" value="settlementItemList">
						<tr bgcolor="#ffffff">
							<td>
								${settlementItem.supplierName}
							</td>
							<td>
								${settlementItem.metaProductName}
							</td>
							<td>
								${settlementItem.settlementTimeStr}
							</td>
							<td>
									${settlementItem.settlementStatusName}
							</td>
							<td>
									${settlementItem.settlementId == null ? "无" :
									settlementItem.settlementId}
							</td>
							<td>
									${settlementItem.settlementPeriodName}
							</td>
						</tr>
					</s:iterator>

				</tbody>
			</table>
			<p align="right">
				<input type="button" name="btnCloseOrder" class="button" value="关闭"
					onclick="javascript:closeDetailDiv('settlementStatusDiv');">
			</p>
		</div>
	</body>
</html>