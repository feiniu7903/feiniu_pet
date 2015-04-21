<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="mis" uri="/tld/lvmama-tags.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>super后台_酒店订单监控</title>
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>themes/cc.css" />
		<link rel="stylesheet"
			href="<%=basePath%>themes/base/jquery.ui.all.css" />
					<script type="text/javascript">
var path = '<%=basePath%>'; 
</script>
<script type="text/javascript"
	src="<%=basePath%>js/base/jquery-1.4.4.min.js">
</script>
<script type="text/javascript" src="<%=basePath%>js/base/form.js">
</script>
</head>
	<body>
			<div>
				<div class="main2">
				<form name='hotelmonitor' method='post' action='<%=basePath%>/ord/showOrdOrderHotelList.do'>
					<div class="table_box" id=tags_content_1>
						<div class="mrtit3">
							<table width="99%" border="0" class="newfont06"
								style="font-size: 12; text-align: left;">
								<tr>
									<td width="8%">
										驴妈妈订单号：
									</td>
									<td width="12%">
										<mis:checkElement permCode="1974" name="lvmamaOrderId"
											value="${lvmamaOrderId}" />
									</td>
									<td width="8%">
										合作方订单号：
									</td>
									<td width="12%">
										<mis:checkElement permCode="1975" name="partnerOrderId"
											value="${partnerOrderId}" />
									</td>
									<td width="8%">
										订单子子项编码：
									</td>
									<td width="12%">
											<mis:checkElement permCode="1978" name="orderItemId"
											value="${orderItemId}" />
									</td>
									
									<td colspan="3">
                                   <input type="submit" value="查 询" class="right-button08" name="btnOrdMonitorQuery"/>                           
                                    </td>

							</table>
						</div>
						</div>
						</form>
						<table cellspacing="1" cellpadding="4" border="0"
							bgcolor="#666666" width="99%" class="newfont06"
							style="font-size: 12; text-align: center;">
							<tbody>
								<tr bgcolor="#eeeeee">
									<td height="35" width="6%">
										订单号
									</td>
									<td height="35" width="6%">
										合作方订单号
									</td>
									<td width="6%">
									          订单子子项编码
									</td>
									<td width="6%">
										供应商平台名称
									</td>
									<td width="8%">
										订单状态
									</td>
									<td width="6%">
										创建时间
									</td>
								</tr>
								<s:iterator id="order" value="pagination.items">
									<tr bgcolor="#ffffff">
										<td height="30">
											${order.lvmamaOrderId}
										</td>
										<td height="30">
											${order.partnerOrderId}
										</td>
										<td>
											${order.orderItemId}
										</td>
										<td>
											${order.hotelSupplierName}
										</td>
										<td>
											${order.statusName}
										</td>
										<td>
										<s:date name="createTime" format="yyyy-MM-dd HH:mm:ss"/>
										</td>
									</tr>
								</s:iterator>
							<tr bgcolor="#ffffff">
								<td>总条数：<s:property value="pagination.totalResultSize" /></td>
								<td colspan="5" style="text-align: right"><s:property
										escape="false"
										value="@com.lvmama.comm.utils.Pagination@pagePost(pagination.pageSize,pagination.totalPageNum,pagination.url,pagination.currentPage)" /></td>
							</tr>
						</tbody>
						</table>
					</div>
					</div>
	</body>
</html>
