<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="mis" uri="/tld/lvmama-tags.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
	<head>
		<title>成团监控</title>
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
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>themes/cc.css">
		<link href="<%=basePath%>themes/base/jquery.ui.all.css" rel="stylesheet"></link>
		<script type="text/javascript"
			src="<%=basePath%>js/ui/jquery-ui-1.8.5.js"></script>
			<script type="text/javascript" src="<%=basePath %>js/base/lvmama_dialog.js"></script>
			<script src="${basePath}js/phoneorder/important_tips.js"
			type="text/javascript">
</script>
			<script type="text/javascript">
		$(function() {
			$("input[name='visitTime']" ).datepicker({dateFormat:'yy-mm-dd'});
	});
			
			</script>
		<script language="JavaScript">
			function check(flag) {
				var $form=$("form[name=form1]");
				if(flag == 1){
					$form.attr("action","<%=basePath%>/ord/ord_team_monitor!doQuery.do");
				}
				if(flag == 0){
					$form.attr("action","<%=basePath%>/ord/ord_team_monitor!exportQueryResult.do");
				}
				var productId=$("#productId").val();
				if(productId==''){
					alert("产品ID不能为空");
					return false;
				}
				if(isNaN(productId)){
					alert("产品ID必须是数字");
					return false;
				}
			}

       </script>
	   <script type="text/javascript" src="<%=basePath%>js/base/jquery.datepick-zh-CN.js"></script>		
	
	</head>
	<body>
		<div id="tg_order">
			<div class="wrap">
					<!--=========================主体内容==============================-->
					<div class="table_box" id=tags_content_2>
						<div class="mrtit3">
							<form name='form1' method='post'
								action='<%=basePath%>/ord/ord_team_monitor!doQuery.do'>
								<table style="font-size: 12px" width="100%" border="0">
									<tr>
										<td>
											产品ID：
										</td>
										<td>
											<input id="productId" name="productId" type="text" value="${productId}"/>
										</td>
										<td>
											游玩时间：
										</td>
										<td>
											<input name="visitTime" type="text" class="easyui-datebox" value="${visitTime}"/>
										</td>
										<td><input type="hidden" name="hiddenStatus" value="${queryString}" /></td>
									</tr>
									<tr>
											<td>
												<mis:checkPerm permCode="1645">
												<input type="submit" name="button" value="所有订单" onClick="return check(1);" class="right-button08"/>
												</mis:checkPerm>
											</td>
											<td>
											<mis:checkPerm permCode="1646">
												<input type="submit" name="button" value="库耗订单" onClick="return check(1);" class="right-button08"/>
												</mis:checkPerm>
											</td>
											<td>
											<mis:checkPerm permCode="1647">
												<input type="submit" name="button" value="取消订单" onClick="return check(1);" class="right-button08"/>
												</mis:checkPerm>
											</td>
											<td>
											<mis:checkPerm permCode="1648">
												<input type="submit" name="button" value="出游订单" onClick="return check(1);" class="right-button08"/>
												</mis:checkPerm>
											</td>
											<td>
											<!-- 
											<mis:checkPerm permCode="1649">
												<input type="submit" name="button" value="导出Excel" onClick="return check(0);" class="right-button08"/>
												</mis:checkPerm> -->
											</td>
									</tr>
								</table>
							</form>
						</div>
						<table style="font-size: 12px" cellspacing="1" cellpadding="4"
							border="0" bgcolor="#666666" width="100%" class="newfont06">
							<tbody>
								<tr bgcolor="#eeeeee">
									<td height="35" width="5%">
										订单号
									</td>
									<td width="7%">
										取票人/联系人
									</td>
									<td width="5%">
										电话
									</td>
									<td width="20%">
										产品名称
									</td>
									<td width="7%">
										订单状态
									</td>
									<td width="7%">
										支付状态
									</td>
									<td width="11%">
										支付时间
									</td>
									<td width="8%">
										下单人
									</td>
									<td width="5%">
										游玩人数
									</td>
									<td width="5%">
										操作
									</td>
								</tr>
								<s:iterator id="order" value="ordersList">
									<tr bgcolor="#ffffff">
										<td height="30">
											${order.orderId }
										</td>
										<td>
											${order.contact.name }
										</td>
										<td>
											${order.contact.mobile }
										</td>
										<td>
											<s:iterator id="orderItem" value="#order.ordOrderItemProds">
											<mis:checkPerm permCode="1649">
											<a class="showImportantTips" href="javascript:void(0)"
											productId="${orderItem.productId}"  prodBranchId="${orderItem.prodBranchId}">
											</mis:checkPerm>
												${orderItem.productName }
											<mis:checkPerm permCode="1649">
												<%out.print("</a>") ;%>
											</mis:checkPerm>
												<br />
											
											</s:iterator>
										</td>
										<td>
											${order.zhOrderStatus }
										</td>
										<td>
											${order.zhPaymentStatus }
										</td>
										<td>
											<s:date name="pay.createTime" format="yyyy-MM-dd HH:mm:ss" />
										</td>
										<td>
											${order.userName }
										</td>
										<td>
											<s:iterator id="orderItem" value="#order.ordOrderItemProds">
											${orderItem.hotelQuantity }<br />
											</s:iterator>
										</td>
										<td>
											<mis:checkPerm permCode="1650">
											<a href="javascript:showDetailDiv('historyDiv', ${order.orderId })">查看</a>
											</mis:checkPerm>
										</td>
									</tr>
								</s:iterator>
							</tbody>
						</table>
					</div>
					<table>
						<tr>
							<td>人数统计：&nbsp;&nbsp;&nbsp;&nbsp;</td>
							<td>成人:${opc.adultCount }人&nbsp;&nbsp;</td>
							<td>儿童:${opc.childCount }人&nbsp;&nbsp;</td>
						</tr>
					</table>
					<!--=========================主体内容 end==============================-->
			</div>
			<!-- 分页组件 -->
			<table width="90%" border="0" align="center">
				<s:include value="/WEB-INF/pages/back/base/pag.jsp" />
			</table>
			
			<div class="orderpop" id="historyDiv" style="display: none;"
				href="showOrderDetail.do">
			</div>
			<!--弹出层灰色背景-->
			<div id="bg" class="bg" style="display: none;"></div>
		</div>
	</body>
</html>
