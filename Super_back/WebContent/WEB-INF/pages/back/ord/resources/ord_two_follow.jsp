<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="mis" uri="/tld/lvmama-tags.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setDateHeader("Expires", 0);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

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
		<script language="javascript" src="<%=basePath%>js/ord/ord.js"
			type="text/javascript"></script>
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>themes/cc.css">
		<script type="text/javascript"
			src="<%=basePath%>js/ui/jquery-ui-1.8.5.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/base/lvmama_dialog.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/base/jquery.datepick-zh-CN.js"></script>
		<link href="<%=basePath%>themes/base/jquery.ui.all.css"
			rel="stylesheet"></link>
		<script src="${basePath}js/phoneorder/important_tips.js"
			type="text/javascript">
</script>
		<script src="${basePath}js/phoneorder/important_tips.js"
			type="text/javascript">
</script>
		<script type="text/javascript">
		$(function() {
			$("input[name='trackCreateTimeStart']" ).datepicker({dateFormat:'yy-mm-dd'});
			$("input[name='trackCreateTimeEnd']").datepicker({dateFormat:'yy-mm-dd'});
	});
		
		function btn_checkOrder(){
			var dataMember = {trackQuantity:$("#trackQuantity").val()};
			$.ajax({type:"POST", url:"<%=basePath%>ord/doCheckOrderTrack.do", data:dataMember, dataType:"html", success:function (data) {
					if("fail".indexOf(data)>-1){
						alert("请先处理完页面上待领取的订单(除继续跟踪订单外),在领取新订单!");
					}else{
						document.getElementById("frmGetOrder").url="doGetOrderTrack.do";
						document.getElementById("frmGetOrder").submit();
					}
			}});
		}	
	</script>
		<style type="text/css">
		table{
		width:100%;
		}
		table tr th{
		width:10%;
		height:10px;
		width:50px;
		word-break: keep-all;
		white-space:rowrap;
		}
	</style>
	</head>
	<body>
		<div id="tg_order" >
			<div class="wrap" >
				<div class="main2">
					<!--=========================主体内容==============================-->
					<ul class="tags">
						<li class="tags_at" id=tags_title_1
							onclick="javascript:Show_list_div(1,2);window.location='<%=basePath%>ord/listTwoFollow.do?tab=1';">
							我的跟踪订单
						</li>
						<li class="tags_none" id=tags_title_2
							onclick="javascript:Show_list_div(2,2);">
							我的历史订单
						</li>
					</ul>
					<div class="table_box" id=tags_content_1>
						<form name="frmGetOrder" id="frmGetOrder" action="doGetOrderTrack.do">
							<mis:checkPerm permCode="3025">
								<br />
							&nbsp;<input type="button" value="领 单" name="btnGetOrderTrack" onClick="btn_checkOrder()"
									class="right-button02" />&nbsp;&nbsp;
							</mis:checkPerm>
							<s:select name="trackQuantity"
								list="#{1:1,2:2,3:3,4:4,5:5,6:6,7:7,8:8,9:9,10:10,11:11,12:12}"></s:select>
						</form>
						<table style="font-size: 12px;" cellspacing="1" cellpadding="4"
							border="0" bgcolor="#666666"  class="newfont06">
							<tbody>
							<tr bgcolor="#eeeeee">									
								<tr bgcolor="#eeeeee">
									<td height="35" width="5%">
										订单号
									</td>
									<td  width="5%">
										领单人
									</td>
									<td >
										领单时间
									</td>
									<td width="18%">
										销售产品
									</td>
									<td width="3%">
										数量
									</td>
									<td width="6%">
										下单人
									</td>
									<td width="6%">
										联系人
									</td>
									<td >
										联系电话
									</td>
									<td width="11%">
										下单时间
									</td>
									<td 
									>
										游玩时间
									</td>
									<td width="6%"
									>
										审核状态
									</td>
									<td width="8%"
									>
										处理状态
									</td>
									<td >
										操作
									</td>
								</tr>
								<s:iterator id="order" value="ordersList">
									<tr bgcolor="#ffffff">
										<td >
											<a href="${order.orderMonitorUrl }" target="_blank">${order.orderId }</a>
										</td>
										<td >
											${order.orderTrack.ordTrackId}
										</td>
										<td>
											<s:date name="orderTrack.createTime" format="yyyy-MM-dd HH:mm:ss" />
										</td>
										<td>
											<s:iterator id="orderItem" value="#order.ordOrderItemProds">
												<a class="showImportantTips" href="javascript:void(0)"
													productId="${orderItem.productId}" prodBranchId="${orderItem.prodBranchId}">${orderItem.productName}</a>
												<br />
											</s:iterator>
										</td>
										<td height="50px">
											<s:iterator id="orderItem" value="#order.ordOrderItemProds">
											${orderItem.quantity }<br />
											</s:iterator>
										</td>
										<td>
											${order.userName }
										</td>
										
										<td height="50px">
											${order.contact.name }
										</td>
										<td>
										${order.contact.mobile}
										
										</td>
										<td>
											<s:date name="createTime" format="yyyy-MM-dd HH:mm:ss" />
										</td>
										<td>
											<s:date name="visitTime" format="yyyy-MM-dd" />
										</td>
										<td>
											${order.zhApproveStatus }
										</td>
										<td>
										${orderTrack.trackLogStatus }
										</td>
										<td>
											<mis:checkPerm
												permCode="3026">
												<input type="button" value="二次跟踪处理"
													name="btnCheckCanApprove" class="right-button09"
													onclick="javascript:showDetailDivByTrackId('followDiv', '${order.orderTrack.ordTrackId}','${order.orderId}')"/>
											</mis:checkPerm>
										</td>
									</tr>
								</s:iterator>
							</tbody>
						</table>
						<s:if test="ordersList.size>0&&tab==1">
							<table width="90%" border="0" align="center">
								<s:include value="/WEB-INF/pages/back/base/pag.jsp" />
							</table>
						</s:if>
					</div>
					<div class="table_box" id=tags_content_2 style="display: none;">
						<div class="mrtit3">
							<s:form name='form1' method='post'
								action='doHistoryQuery.do?tab=2' theme="simple" namespace="/ord">
								<table style="font-size: 12px" width="98%" border="0">
									<tr>
										<td>
											订 单 号：
										</td>
										<td>
											<input name="ordId" value="${ordId}" type="text" />
										</td>
										<td>
											下 单 人：
										</td>
										<td>
											<input name="person" type="text" value="${person}" />
										</td>
										<td>
											处理结果：
										</td>
										<td>
											<s:select list="tarckItemList" name="trackType"
												listKey="code" listValue="name" headerKey="" 
												headerValue="--请选择--" id="trackType">
											</s:select>
										</td>
									</tr>
									<tr>
										<td>
											领单时间：
										</td>
										<td colspan="8">
											<input name="trackCreateTimeStart" type="text"
												class="easyui-datebox" value="${trackCreateTimeStart}" />
											~
											<input name="trackCreateTimeEnd" type="text"
												class="easyui-datebox" value="${trackCreateTimeEnd}" />
										</td>
									</tr>
									<tr>
										<td colspan="6" align="center">
											<input type='submit' name="btnOrdListQuery" value="查 询"
												class="right-button08" />
										</td>
									</tr>
								</table>
							</s:form>
						</div>
						<table style="font-size: 12px" cellspacing="1" cellpadding="4"
							border="0" bgcolor="#666666" width="98%" class="newfont06">
							<tbody>
								<tr bgcolor="#eeeeee">
									<td height="35" width="5%">
										订单号
									</td>
									<td width="5%">
										领单人
									</td>
									<td  width="8%">
										领单时间
									</td>
									<td width="8%">
										完成时间
									</td>
									<td width="18%">
										销售产品
									</td>
									<td width="3%">
										数量
									</td>
									<td width="6%">
										下单人
									</td>
									<td width="4%">
										联系人
									</td>
									<td width="6%">
									联系电话
									</td>
									<td width="9%">
										下单时间
									</td>
									<td width="9%">
										游玩时间
									</td>
									<td width="5%">
										审核状态
									</td>
									<td width="8%">
										处理状态
									</td>
									<td width="8%">
										操作
									</td>
								</tr>
								<s:iterator id="order" value="historyOrdersList">
									<tr bgcolor="#ffffff">
										<td height="30">
											<a href="${order.orderMonitorUrl }" target="_blank">${order.orderId }</a>
										</td>
										<td >
											${order.orderTrack.userName}
										</td>
										<td>
											<s:date name="orderTrack.createTime" format="yyyy-MM-dd HH:mm:ss" />
										</td>
										<td>
										
											<s:date name="orderTrack.finishTime" format="yyyy-MM-dd HH:mm:ss" />
										</td>
										<td>
											<s:iterator id="orderItem" value="#order.ordOrderItemProds">
												<a class="showImportantTips" href="javascript:void(0)"
						productId="${orderItem.productId}">${orderItem.productName}</a>
												<br />
											</s:iterator>
										</td>
										<td>
											<s:iterator id="orderItem" value="#order.ordOrderItemProds">
											${orderItem.quantity }<br />
											</s:iterator>
										</td>
										<td>
											${order.userName }
										</td>
										<td>
											${order.contact.name }
										</td>
										<td>
											${order.contact.mobile}
										</td>
										<td>
											<s:date name="createTime" format="yyyy-MM-dd HH:mm:ss" />
										</td>
										<td>
											<s:date name="visitTime" format="yyyy-MM-dd" />
										</td>
										<td>
											${order.zhApproveStatus }
										</td>
										<td>
										${orderTrack.trackLogStatus }
										</td>
										<td>
											<a
													href="javascript:showDetailDiv('historyDiv', ${order.orderId })">查看</a>
											<mis:checkPerm permCode="3027">
											<br/>
												<a href="javascript:showDetailDivByTrackId('followLogDiv', '${order.orderTrack.ordTrackId}')">跟踪日志</a>
											</mis:checkPerm>
											
										</td>
									</tr>
								</s:iterator>
							</tbody>
						</table>
						<s:if test="historyOrdersList.size>0&&tab==2">
							<table width="90%" border="0" align="center">
								<s:include value="/WEB-INF/pages/back/base/pag.jsp" />
							</table>
						</s:if>
					</div>
					<!--=========================主体内容 end==============================-->
				</div>
				<!--main2 end-->
			</div>
		</div>
		<!--wrap end-->
		<div id="bg" class="bg" style="display: none;"></div>
		<div class="orderpop" id="followDiv" style="display: none;"
			href="jumpOrdTarck.do"></div>
		<div class="orderpop" id="historyDiv" style="display: none;"
			href="showHistoryOrderDetail.do"></div>
		<div class="orderpop" id="followLogDiv" style="display: none;"
			href="jumpOrdTarckLog.do"></div>
		<!--main2 end-->
		<script>
		var tabIndex = (${tab}==null || ${tab}==0)?1:${tab}; 
		Show_list_div(tabIndex, 2);</script>
	</body>
</html>
