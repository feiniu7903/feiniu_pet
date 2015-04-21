<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="mis" uri="/tld/lvmama-tags.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>线下支付管理-手工支付</title>
		<script type="text/javascript">
var path = '<%=basePath%>'; 
</script>
		<script type="text/javascript" src="<%=basePath%>js/base/jquery-1.4.4.min.js"> </script>
		<script type="text/javascript" src="<%=basePath%>js/base/remoteUrlLoad.js"> </script>
		<script type="text/javascript" src="<%=basePath%>js/base/form.js"> </script>
		<script language="javascript" src="<%=basePath%>js/xiangmu.js" type="text/javascript"> </script>
		<script language="javascript" src="<%=basePath%>js/ord/ord.js" type="text/javascript"> </script>
		<script language="javascript" src="<%=basePath%>js/ord/in_add.js" type="text/javascript"> </script>
		<script language="javascript" src="<%=basePath%>js/ord/ord_div.js" type="text/javascript"> </script>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>themes/cc.css" />
		<link rel="stylesheet" href="<%=basePath%>themes/base/jquery.ui.all.css" />
		<script type="text/javascript" src="<%=basePath%>js/ui/jquery-ui-1.8.5.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/base/lvmama_common.js"> </script>
		<script type="text/javascript" src="<%=basePath%>js/base/lvmama_dialog.js"> </script>
        <script src="${basePath}js/phoneorder/important_tips.js" type="text/javascript"> </script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/base/jquery.datepick-zh-CN.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/prod/date.js"></script>
        <%@ include file="/WEB-INF/pages/back/base/timepicker.jsp"%>
	</head>
	<body>
		
			<div>
				<div class="main2">
					<!--=========================主体内容==============================-->
					<div class="table_box" id=tags_content_1>
						<div class="mrtit3">
                         <form name='form1' method='post' action='<%=basePath%>/offlinePay/doQueryOrder.do' >
							<table width="99%" border="0" class="newfont06"
								style="font-size: 12; text-align: left;">
								<tr>
								    <td>
										订单号：<input name="orderId" type="text" value="${orderId}"/> 
									</td>
									<td>
										下单时间：
									     <input type="text" name="beginTime" id="beginTime" class="date" value="<s:date name="beginTime" format="yyyy-MM-dd"/>"/>
											~ <input type="text" name="endTime" id="endTime"  class="date" value="<s:date name="endTime" format="yyyy-MM-dd"/>" />
									</td>
									<input name="payStatus" type="hidden" value="${payStatus}"/>
									<td>
										<input type="submit" value="查 询" class="right-button08" name="btnOrdMonitorQuery"/>
									</td>
								</tr>
								
							 </table>
							</form>
						</div>
						
						<table cellspacing="1" cellpadding="4" border="0"
							bgcolor="#666666" width="99%" class="newfont06"
							style="font-size: 12; text-align: center;">
							<tbody>
								<tr bgcolor="#eeeeee">
									<td width="20%" align="center">订单号</td>
									<td width="20%" align="center">下单时间 </td>
									<td width="20%" align="center">应付金额(元)</td>
									<td width="20%" align="center">实付金额(元)</td>
									<td width="20%" align="center"> 操作 </td>
								</tr>
								<s:iterator id="order" value="orderList">
								<tr bgcolor="#ffffff">
										<td height="30">
											${order.orderId}
										</td>
										<td height="30">
											 <s:if test="#order.createTime!=null">
											  <s:date name="#order.createTime" format="yyyy-MM-dd HH:mm:ss"/>
										     </s:if>
										</td>
										<td height="30">
											${order.oughtPayFloat}
										</td>
										<td>
											${order.actualPayFloat}
										</td>
										<td>
											  <s:if test="#request.payStatus=='ONLINE'">
												   <a href="javascript:showDetailDivByAmount('onlinePayDiv', '${order.oughtPay-order.actualPay<0?0:order.oughtPay-order.actualPay}', '${order.orderId}');">付款</a>
											  </s:if>
											  <s:if test="#request.payStatus=='OTHER'">
											     <a href="javascript:showDetailDivByAmount('otherPayDiv', '${order.oughtPay-order.actualPay<0?0:order.oughtPay-order.actualPay}', '${order.orderId}');">付款</a>
											  </s:if>
											  <s:if test="#request.payStatus=='DIST'">
											     <a href="javascript:showDetailDivByAmount('distPayDiv','${order.oughtPay-order.actualPay<0?0:order.oughtPay-order.actualPay}', '${order.orderId}');">付款</a>
											  </s:if>
											  <a href="javascript:showDetailDiv('historyDiv', '${order.orderId}');">查看订单</a>
										</td>
								</tr>
								
								
								</s:iterator>
							</tbody>
						</table>

					</div>
					<!--=========================主体内容 end==============================-->
				</div>
				<!--main2 end-->
			</div>

	<table width="90%" border="0" align="center">
			<s:include value="/WEB-INF/pages/back/base/pag.jsp" />
		</table>
		
		<div class="orderpop" id="onlinePayDiv" style="display: none;"
			href="<%=basePath%>offlinePay/onlinePay.do">
		</div>
		
		<div class="orderpop" id="otherPayDiv" style="display: none;"
			href="<%=basePath%>offlinePay/otherPay.do">
		</div>
		<div class="orderpop" id="distPayDiv" style="display: none;"
			href="<%=basePath%>offlinePay/distPay.do">
		</div>
		
		<div class="orderpop" id="historyDiv" style="display: none;"
			href="<%=basePath%>ord/showHistoryOrderDetail.do">
		</div>
		
		
		<div id="bg" class="bg" style="display: none;">
			<iframe
				style="position: absolute; width: 100%; height: 100%; _filter: alpha(opacity =                                           0); opacity =0; border-style: none; z-index: -1">
			</iframe>
		</div>
	
	</body>
</html>

