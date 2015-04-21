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
		<title>订单处理后台_订单审核</title>
		<script type="text/javascript">
	   		var path='<%=basePath%>';
		</script>
		<script type="text/javascript"
			src="<%=basePath%>js/base/jquery-1.4.4.min.js"></script>
	<script type="text/javascript"
			src="<%=basePath%>js/base/remoteUrlLoad.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/base/form.js"></script>
		<script language="javascript" src="<%=basePath%>js/xiangmu.js"
			type="text/javascript"></script>
		<script language="javascript" src="<%=basePath%>js/ord/ord.js"
			type="text/javascript"></script>
		<script language="javascript" src="<%=basePath%>js/base/lvmama_common.js"
			type="text/javascript"></script>
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>themes/icon.css">
			<link href="<%=basePath%>themes/base/jquery.ui.all.css" rel="stylesheet"></link>
		<script type="text/javascript"
			src="<%=basePath%>js/ui/jquery-ui-1.8.5.js"></script>
			<script src="${basePath}js/phoneorder/important_tips.js"
			type="text/javascript">
</script>
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>themes/cc.css">
		<!--弹出层js-->
		<script type="text/javascript">
		
		
		
			$(function() {
		$("input[name='queryParms.visitTimeB']" ).datepicker({dateFormat:'yy-mm-dd'});
		$("input[name='queryParms.visitTimeE']").datepicker({dateFormat:'yy-mm-dd'});
		$("input[name='queryParms.createTimeB']").datepicker({dateFormat:'yy-mm-dd'});
		$("input[name='queryParms.createTimeE']").datepicker({dateFormat:'yy-mm-dd'});

		
	});
		
		</script>
		<script type="text/javascript" src="<%=basePath%>js/base/jquery.datepick-zh-CN.js"></script>	
		
		<!--弹出层js end-->
	</head>
	<body>
	<form id="forms" action="<%=basePath%>ord/order_approve_area!doQuery.do" method="post">
		<div id="tg_order">
			<div class="wrap">
				<div class="main2">
					<!--=========================主体内容==============================-->
					
					<div class="table_box" id=tags_content_1>
						<div class="mrtit2">
							<table width="98%" border="0" style="font-size: 12;">
								
								<tr>
									<td>
										订 单 号：
									</td>
									<td>
										<input name="queryParms.orderId" type="text" value="${queryParms.orderId}"/>
									</td>
									<td>
										下 单 人：
									</td>
									<td>
										<input name="queryParms.userId" type="text" value="${queryParms.userId}"/>
									</td>
								</tr>
								<tr>
									<td>
										游玩时间：
									</td>
									<td>
										<input name="queryParms.visitTimeB"
											type="text" value="${queryParms.visitTimeB}"/>
										~
										<input name="queryParms.visitTimeE"
											type="text"value="${queryParms.visitTimeE}"/>
									</td>
									<td>
										下单时间：
									</td>
									<td>
										<input name="queryParms.createTimeB"
											type="text"  value="${queryParms.createTimeB}"/>
										~
										<input name="queryParms.createTimeE"
											type="text" value="${queryParms.createTimeE}"/>
									</td>
								</tr>
								<tr>
									<td></td><td></td><td></td>
								<td>
									<input type="button" name="btnOrdApproveArea" value="查询" class="right-button02"
											onclick="javascript:document.getElementById('forms').submit();" />
								</td>
								</tr>
							</table>
						</div>
						<table cellspacing="1" cellpadding="4" border="0"
							bgcolor="#666666" width="100%" class="newfont06"
							style="font-size: 12; text-align: center;">
							<tbody>
								<tr bgcolor="#eeeeee">
									<td width="5%"><input type="checkbox" onClick="setCheckbox(this,'queryParms.cbx')"/></td>
									<td height="35" width="10%">订单号</td>
				                    <td width="10%">销售产品</td>
									<td width="8%">数量</td>					
									<td width="8%">下单人</td>
				                    <td width="8%">联系人</td>
				                    <td width="10%">联系电话</td>					
									<td width="10%">下单时间</td>
								    <td width="10%">首处理时间</td>
									<td width="10%">游玩时间</td>					
									<td width="8%">操作</td>
								</tr>
								<s:iterator value="ordersList" var="order">
									<tr bgcolor="#ffffff">
										<td><input name="queryParms.cbx" type="checkbox" value="${orderId }"/></td>
										<td height="30">
											<a href="${order.orderMonitorUrl }" target="_blank">${order.orderId }</a>
										</td>
										<td >
											<s:iterator value="ordOrderItemProds">
											<a class="showImportantTips" href="javascript:void(0)"
						productId="${productId}" prodBranchId="${prodBranchId}">${productName }</a><br />
											</s:iterator>
										</td>
										<td >
											<s:iterator value="ordOrderItemProds">
												${orderItem.hotelQuantity }<br />
											</s:iterator>
										</td>
										<td>
											${userName }
										</td>
										<td>
											${contact.name }
										</td>
										<td>
											${contact.mobile }
										</td>
										<td>
											<s:date name="createTime" format="yyyy-MM-dd HH:mm:ss"/>
											<fmt:formatDate value="${order.createTime }" pattern="yyyy-MM-dd HH:mm:ss" />
											
										</td>
										<td>
											<s:date name="dealTime" format="yyyy-MM-dd HH:mm:ss"/>
										</td>
										<td>
											<s:date name="visitTime" format="yyyy-MM-dd"/>
										</td>
										<td>
											<mis:checkPerm permCode="1546">
											<a href="#" onclick="javascript:showDetailDiv('historyDiv', '${orderId}');">查看</a>
											</mis:checkPerm>
										</td>
									</tr>
								</s:iterator>
							</tbody>
						</table>
					</div>
				</div>
				<!--main2 end-->
				<p class="alignr">
				<select id="cancelReason" name="cancelReason">
						<s:iterator id="reason" value="cancelReasons">
							<option value="${reason.code }">
								${reason.name }
							</option>
						</s:iterator>
					</select>
					<mis:checkPerm permCode="1544">
				<input type="button" name="btnOrdApproveCancel" value="废 单" class="right-button08" onClick="document.getElementById('forms').action='<%=basePath%>ord/order_approve_area!cancelOrder.do';document.getElementById('forms').submit();">
				</mis:checkPerm></p>
			</div>
			<!--wrap end-->
			
		</div>
		<!--main2 end-->
		
		</form>
		<table width="90%" border="0" align="center">
				<s:include value="/WEB-INF/pages/back/base/pag.jsp" />
			</table>
			<div class="orderpop" id="historyDiv" style="display: none;"
				href="<%=basePath%>ord/showHistoryOrderDetail.do">
			</div>
		<div id="bg" class="bg" style="display: none;"></div>
	</body>
</html>

<script type="text/javascript">
function setCheckbox(obj,listCbxName){
	var sels=document.getElementsByName(listCbxName);
	for(var i=0;i<sels.length;i++){
		sels[i].checked=obj.checked;
	}
}
</script>
