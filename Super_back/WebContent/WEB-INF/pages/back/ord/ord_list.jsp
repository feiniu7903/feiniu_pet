<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="mis" uri="/tld/lvmama-tags.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	response.setHeader("Pragma","No-cache"); 
	response.setHeader("Cache-Control","no-cache"); 
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
			<script type="text/javascript" src="<%=basePath%>js/base/jquery.datepick-zh-CN.js"></script>	
			<script src="${basePath}js/phoneorder/important_tips.js"
			type="text/javascript">
</script>
			<script type="text/javascript">
		
		$(function() {
			$("input[name='visitTimeStart']" ).datepicker({dateFormat:'yy-mm-dd'});
			$("input[name='visitTimeEnd']").datepicker({dateFormat:'yy-mm-dd'});
			$("input[name='createTimeStart']").datepicker({dateFormat:'yy-mm-dd'});
			$("input[name='createTimeEnd']").datepicker({dateFormat:'yy-mm-dd'});
			
			$("a.groupWordStatus").click(function(){
					if(!confirm("确定已发出团通知")){
						return false;
					}	
					
					var $this=$(this);
					var orderId=$(this).attr("orderId");
					$.post("<%=basePath%>op/opChangeWordStatus.do",{"ordId":orderId},function(dt){
						var data=eval("("+dt+")");
						if(data.success){
							$("#groupWordStatus_"+orderId).html("已发");
							$this.remove();
							alert("修改成功");
						}else{
							alert(data.msg);
						}
					});
				});	
	});
			
			function checkGoingBack(orderId,type,permId){
			var url = "<%=basePath%>ord/order_list!doCancelOrder.do?id="+orderId+"&orderType="+type+"&permId="+permId;
				$.ajax({type:"POST", url:"<%=basePath%>ord/checkGoingBack.do",data:{orderId:orderId} ,success:function (result) {
					var res = eval(result);
 					if (res) {
						window.location.href=url;
					} else {
						alert("分配的订单无法退单");
					}
				}});
			}
			
			function checkCanApprove(orderId,permId){
				$.ajax({type:"POST", url:"<%=basePath%>ord/checkApprove.do",data:{orderId:orderId,permId:permId} ,success:function (result) {
					var res = eval(result);
 					if (res) {
						alert("订单已被回收！");
					} else {
						showDetailDiv('approveDiv', orderId);
					}
				}});
			}
			</script>
			
	</head>
	<body>
		<div id="tg_order">
			<div class="wrap">
				<div class="main2">
					<!--=========================主体内容==============================-->
					<ul class="tags">
						<li class="tags_at" id=tags_title_1
							onclick="javascript:Show_list_div(1,2);window.location='<%=basePath%>ord/order_list!execute.do?orderType=${orderType}&tab=1&permId=${permId}';">
							我的审核任务
						</li>
						<li class="tags_none" id=tags_title_2
							onclick="javascript:Show_list_div(2,2);">
							我的历史订单
						</li>
					</ul>
					<div class="table_box" id=tags_content_1>
						<form action="doGetOrder.do">
						<input type="hidden" name="permId" value="${permId}"></input>
							<input type="hidden" name="orderType" value="${orderType}" />
							<mis:checkPerm permCode="1513,1519,1525,1531,1537,1547,1553,1559,1565,1571,1577,1583,1588" permParentCode="${permId}">
							<input type="submit" value="领 单" name="btnGetOrder" class="right-button02" />
							</mis:checkPerm>
						</form>
						<table style="font-size: 12px" cellspacing="1" cellpadding="4"
							border="0" bgcolor="#666666" width="98%" class="newfont06">
							<tbody>
								<tr bgcolor="#eeeeee">									
									<td height="35" width="5%">
										订单号
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
									<td width="11%">
										下单时间
									</td>
									<td width="11%">
										首处理时间
									</td>
									<td width="8%">
										游玩时间
									</td>
									<td width="8%">
										审核状态
									</td>
									<td width="10%">
										操作
									</td>
								</tr>
								<s:iterator id="order" value="ordersList">
									<tr bgcolor="#ffffff">										
										<td height="30">
											<a href="${order.orderMonitorUrl }" target="_blank">${order.orderId }</a>
										</td>
										<td>
											<s:iterator id="orderItem" value="#order.ordOrderItemProds">
											<a class="showImportantTips" href="javascript:void(0)"
						productId="${orderItem.productId}" prodBranchId="${orderItem.prodBranchId}">${orderItem.productName }</a><br />
											</s:iterator>
										</td>
										<td>
											<s:iterator id="orderItem" value="#order.ordOrderItemProds">
											
											<s:if test="#orderItem.productType=='HOTEL'">
										${orderItem.hotelQuantity }<br />
									</s:if>
									<s:else>
										${orderItem.quantity }<br />
									</s:else>
											</s:iterator>
										</td>
										<td>
											${order.userName }
										</td>
										<td>
											${order.contact.name }
										</td>
										<td>
											<s:date name="createTime" format="yyyy-MM-dd HH:mm:ss" />
										</td>
										<td>
											<s:date name="dealTime" format="yyyy-MM-dd HH:mm:ss" />
										</td>
										<td>
											<s:date name="visitTime" format="yyyy-MM-dd" />
										</td>
										<td>
											${order.zhInfoApproveStatus }
										</td>
										<td>
											<mis:checkPerm permCode="1515,1521,1527,1533,1539,1549,1555,1561,1567,1573,1579,1585" permParentCode="${permId}">
											<input type="button" value="审 核" name="btnCheckCanApprove" class="right-button02"
													onclick="checkCanApprove('${order.orderId }','${permId}')">
													</mis:checkPerm>
											<s:if test='infoApproveStatus=="UNVERIFIED"'>
											<mis:checkPerm permCode="1514,1522,1528,1534,1540,1550,1556,1562,1568,1574,1576,1580" permParentCode="${permId}">
											<input type="button" name="btnRefundOrder" value="退 单" class="right-button02"
													onclick="checkGoingBack('${order.orderId}','${orderType}','${permId}')">
													</mis:checkPerm>
											</s:if>

											</td>
									</tr>
								</s:iterator>
								<tr><td colspan="10"  bgcolor="#eeeeee">未审核记录共<font color=red>${totalRecords}</font>条</td></tr>
							</tbody>
						</table>
					</div>
					<div class="table_box" id=tags_content_2 style="display: none;">
						<div class="mrtit3">
							<form name='form1' method='post'
								action='<%=basePath%>/ord/order_list!doHistoryQuery.do?orderType=${orderType}&tab=2&permId=${permId}'>
								<table style="font-size: 12px" width="98%" border="0">
									<tr>
										<td>
											订 单 号：
										</td>
										<td>
											<input name="ordId"
												type="text" />
										</td>
										<td>
											下 单 人：
										</td>
										<td>
											<input name="person" type="text" />
										</td>
										<td>
											电子邮件：
										</td>
										<td>
											<input name="email"
												type="text" />
										</td>
									</tr>

									<tr>
										<td>
											手&nbsp;&nbsp;&nbsp;&nbsp;机：
										</td>
										<td>
											<input name="mobile"
												type="text" />
										</td>
										<td>
											游玩时间：
										</td>
										<td>
											<input
												name="visitTimeStart"
												type="text" class="easyui-datebox" />
											~
											<input
												name="visitTimeEnd" 
												type="text" class="easyui-datebox" />
										</td>
										<td>
											下单时间：
										</td>
										<td>
											<input
												name="createTimeStart"
												type="text" class="easyui-datebox" />
											~
											<input
												name="createTimeEnd"
												type="text" class="easyui-datebox" />
										</td>
									</tr>
									<s:if test="useTravelGroup!=null&&useTravelGroup">
									<tr>
										<td>团号：</td><td><input name="travelCode" type="text" value="${travelCode}"/></td>
										<td> 订单状态:</td>
										<td>
											<s:select  list="#{'':'全部','NORMAL':'正常','CANCEL':'取消','FINISHED':'完成'}" name="order_Status"></s:select>
                                        </td>
                                        <td></td><td></td>
									</tr>
									<tr>
										
										<td>审核状态：</td><td>
											<s:select  list="#{'':'全部','UNVERIFIED':'未审核','VERIFIED':'已审核','INFOPASS':'信息审核通过','RESOURCEPASS':'资源审核通过','RESOURCEFAIL':'资源审核不通过'}" name="approveStatus"></s:select>
										</td>
										<td> 支付状态:</td>
										<td> 
											<s:select  list="#{'':'全部','PAYED':'已经支付','PARTPAY':'部分支付','UNPAY':'未支付'}" name="paymentStatus"></s:select>
                                        </td>
                                        <td> 开票状态: </td>
                                        <td>
											<s:select  list="#{'':'全部','true':'已开票','false':'未开票'}" name="trafficTicketStatus"></s:select>
                                        </td>
									</tr>
									</s:if>
									<tr>
										<td></td><td></td><td></td><td></td><td></td>
										<td>
											<input type='submit' name="btnOrdListQuery" value="查 询" class="right-button08" />
										</td>
									</tr>
								</table>
							</form>
						</div>
						<table style="font-size: 12px" cellspacing="1" cellpadding="4"
							border="0" bgcolor="#666666" width="98%" class="newfont06">
							<tbody>
								<tr bgcolor="#eeeeee">
									<s:if test="useTravelGroup!=null&&useTravelGroup"><td width="5%">团号</td></s:if>
									<td height="35" width="5%">
										订单号
									</td>
									<td width="10%">
										销售产品
									</td>
									<td width="3%">
										数量
									</td>
									<td width="7%">
										下单人
									</td>
									<td width="4%">
										联系人
									</td>
									<td width="8%">
										下单时间
									</td>
									<td width="8%">
										首处理时间
									</td>
									<td width="8%">
										游玩时间
									</td>
									<td width="5%">
										状态
									</td><s:if test="useTravelGroup!=null&&useTravelGroup">
									<td width="5%"> 审核状态 </td>
									<td width="5%"> 支付状态 </td>
									<s:if test="orderType!='SELFHELP_BUS'">
									<td width="5%"> 开票状态 </td>
									</s:if>
									<td width="3%" align="center"> 合同</td>
									<s:if test="orderType=='GROUP_FOREIGN'||orderType=='FREENESS_FOREIGN'">
									<td width="5%">签证状态</td>
									</s:if>
									<td width="5%">出团通知书状态</td>
									</s:if>
									<td width="10%">
										操作
									</td>
								</tr>
								<s:iterator id="order" value="historyOrdersList">
									<tr bgcolor="#ffffff">
										<s:if test="useTravelGroup!=null&&useTravelGroup"><td>${order.travelGroupCode}</td></s:if>
										<td height="30">
											<a href="${order.orderMonitorUrl }" target="_blank">${order.orderId }</a>
										</td>
										<td>
											<s:iterator id="orderItem" value="#order.ordOrderItemProds">
											<a class="showImportantTips" href="javascript:void(0)"
						productId="${orderItem.productId}" prodBranchId="${orderItem.prodBranchId}">${orderItem.productName }</a><br />
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
											<s:date name="createTime" format="yyyy-MM-dd HH:mm:ss" />
										</td>
										<td>
											<s:date name="dealTime" format="yyyy-MM-dd HH:mm:ss" />
										</td>
										<td>
											<s:date name="visitTime" format="yyyy-MM-dd" />
										</td>
										<td>
											${order.zhOrderStatus }
											<s:if test="redail=='true'">
												/需重播
											</s:if>
										</td>
								<s:if test="useTravelGroup!=null&&useTravelGroup">
										<td align="center">
											${order.zhInfoApproveStatus}
										</td>
										<td>
									  	  <s:property value="#order.zhPaymentStatus"/>							    
										</td>
										<s:if test="orderType!='SELFHELP_BUS'">
										<td>
										    <s:if test="#order.trafficTicketStatus=='false'">未开票 </s:if>
										    <s:if test="#order.trafficTicketStatus=='true'">已开票</s:if>
										</td>
										</s:if>
										<td align="center">
										<s:if test="#order.needContract=='NEED_ECONTRACT'"> 
											<s:if test="#order.eContractStatus=='CONFIRM'">已签</s:if> 
											<s:else>未签</s:else> 
										</s:if>
										
									</td>
										<s:if test="orderType=='GROUP_FOREIGN'||orderType=='FREENESS_FOREIGN'">
										<td align="center" class="visaStatus" result="${order.visaStatus}" id="visa_status_${order.orderId}">															
											<s:property value="#order.zhVisaStatus"/>
										</td>
										</s:if>
										<td id="groupWordStatus_${order.orderId}">
										    <s:if test="#order.groupWordStatus=='NEEDSEND'">未发 </s:if>
										    <s:if test="#order.groupWordStatus=='SENT_NOTICE'||#order.groupWordStatus=='MODIFY_NOTICE'">已发</s:if>
										</td>
									</s:if>
										<td>
											<s:if test="useTravelGroup!=null&&useTravelGroup"><s:if test="#order.groupWordStatus=='NEEDSEND' && #order.travelGroupCode!=null"><a href="#notice" class="groupWordStatus" orderId="${order.orderId}">发出团通知书</a></s:if></s:if>
											<mis:checkPerm permCode="1518,1524,1530,1536,1542,1552,1558,1564,1570,1576,1582,1586" permParentCode="${permId}">
											<a href="javascript:showDetailDiv('historyDiv', ${order.orderId })">查看</a>
											</mis:checkPerm>
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
			
			<!--wrap end-->
			<div class="orderpop" id="historyDiv" style="display: none;"
				href="showHistoryOrderDetail.do">
			</div>
			<!--弹出层灰色背景-->
			<div id="bg" class="bg" style="display: none;"></div>
			<div class="orderpop" id="approveDiv" style="display: none;"
				href="showApproveOrderDetail.do"></div>
		</div>
		<!--main2 end-->
		<script>
		var tabIndex = (${tab}==null || ${tab}==0)?1:${tab}; 
		Show_list_div(tabIndex, 2);</script>
	</body>
</html>
