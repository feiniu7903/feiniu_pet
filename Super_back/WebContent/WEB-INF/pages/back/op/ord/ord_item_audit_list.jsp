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
		<script type="text/javascript"
			src="<%=basePath%>js/base/jquery-1.4.4.min.js"></script>
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>themes/cc.css" />
		<script type="text/javascript"
			src="<%=basePath%>js/base/remoteUrlLoad.js"></script>
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>themes/icon.css">
		<script type="text/javascript" src="<%=basePath%>js/base/form.js"></script>
		<script language="javascript" src="<%=basePath%>js/xiangmu.js"
			type="text/javascript"></script>
		<script language="javascript" src="<%=basePath%>js/ord/ord_item.js"
			type="text/javascript"></script>
		<script language="javascript" src="<%=basePath%>js/ord/ord_send.js"
			type="text/javascript"></script>
		<link href="<%=basePath%>themes/base/jquery.ui.all.css"
			rel="stylesheet"></link>
		<script type="text/javascript"
			src="<%=basePath%>js/ui/jquery-ui-1.8.5.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/base/lvmama_common.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/base/perm.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/ord/modify_settlement_price_common.js"></script>
		<script type="text/javascript">
		
		
		$(document).ready(function(){
			$("#checkall").click( 
			function(){
				if(this.checked){ 
					$("input[name='checkname']").each(function(){this.checked=true;}); 
				}else{ 
					$("input[name='checkname']").each(function(){this.checked=false;}); 
				} 
			} 
			);
			$("#checkall1").click( 
			function(){
				if(this.checked){ 
					$("input[name='checkAuditName']").each(function(){this.checked=true;}); 
				}else{ 
					$("input[name='checkAuditName']").each(function(){this.checked=false;}); 
				} 
			} 
			);
			
		});
		
		function showDiw(type1,type2,url){
			Show_list_div(type1,type2);
			document.getElementById("frmNewOrder").action=url;
			document.getElementById("frmNewOrder").submit();
		}
		function btn_AuditOrder(chkName,frm,url){
			if(!confirm("是否确认资源已经审核")){
				return false;
			}
			var checkall=document.getElementsByName(chkName);
			var count=0;
			for(var i=0;i<checkall.length;i++){
				if(checkall[i].checked){
					count=count+1;
				}
			}
			if(count==0){
				alert("请选择订单项进行操作!");
				return false;
			}else{
				document.getElementById(frm).action=url;
				document.getElementById(frm).submit();
			}			
		}
		
		// 查看日志
		function showLog(obj){
			window.open('<%=request.getContextPath()%>/log/viewSuperLog.zul?parentId='+obj.id +'&parentType=ORD_ORDER_ITEM_META',500,300);
		}
		//显示弹出层
		function showDetailDivMessage(id) {
			var url = "${basePath}/ebooking/order/ebkOrdMetaEbkCertDetail.do?orderItemMetaId=" + id;
			$("<iframe frameborder='0' id='showDetailWin'></iframe>").dialog({
				autoOpen: true, 
		        modal: true,
		        title : "订单详情",
		        position: 'top',
		        width: 1024
			}).width(1000).height(600).attr("src",url);
		}
		function closePopupWin() {
			$("#showDetailWin").dialog("close");
			$("#showDetailWin").remove();
		}
		</script>
		<script type="text/javascript" src="<%=basePath%>js/base/jquery.datepick-zh-CN.js"></script>	
		<style type="text/css">
		.optTd input{ margin-top:5px;}
		</style>		
	</head>
	<body>
		<div id="tg_order">
			<div class="wrap">
				<div class="main2">
					<!--=========================主体内容==============================-->
					<ul class="tags">
						<li class="tags_none" id="tags_title_1" onclick='location.href="<%=basePath%>ordItem/opListWaitResult.do?permId=${permId}"'>
							待审核任务
						</li> 
						<li class="tags_at" id=tags_title_2
							onclick='location.href="<%=basePath%>ordItem/opListAuditResult.do?permId=${permId}"'>
							我的订单审核任务
						</li>
						<li class="tags_none" id="tags_title_3"
							onclick='location.href="<%=basePath%>ordItem/opOrdItemHistoryQuery.do?permId=${permId}"'>
							我的历史订单
						</li>
					</ul>

				<s:if test="!workGetOrder">
				    已被领单
				</s:if>
					<div class="table_box" id=tags_content_2>
						<form name='frmNewOrder' id="frmNewOrder" method='post' action=''>
							<input type="hidden" name="productType" value="${productType}" />
							<input type="hidden" name="tab" value="2" />
							<table cellspacing="1" cellpadding="4" border="0"
								bgcolor="#666666" width="98%" class="newfont06"
								style="font-size: 12; text-align: center;">
								<tbody>
									<tr bgcolor="#eeeeee">
										<td width="2%">
											<input type="checkbox" name="checkall" value="1" id="checkall" />
										</td>
										<td width="5%">订单号</td>
										<td width="6%">订单处理人</td>
										<td width="5%">订单子号</td>
										<td width="20%">采购产品</td>
										<td width="5%">结算单价</td>
										<td width="6%">实际结算价</td>
										<td width="3%">数量</td>
										<td width="3%">实际结算总价</td>
										<td width="5%">下单人</td>
										<td width="5%">联系人</td>
										<td width="7%">下单时间</td>
										<td width="7%">首处理时间</td>
										<td width="6%">游玩时间</td>
										<td width="5%">资源状态</td>
										<td width="5%">确认状态</td>
										<td width="10%">操作</td>
									</tr>
									<s:iterator id="item" value="auditOrderMetaList">
										<tr bgcolor="#ffffff">
											<td>
												<input type="checkbox" name="checkname" id="${metaProductId}" value="${orderItemMetaId}"
													title="${metaBranchId}" alt="${actualSettlementPriceYuan }" />
											</td>
											<td height="30">
												${orderId} 
												<s:if test="supplierFlag=='true' && (productType=='HOTEL' or productType=='ROUTE')">
													<span style="color: red;">(EBK订单)</span>
												</s:if>
											</td>
											<td>
												<span class="perm">
													<s:property value="ordOrder.takenOperator" />
												</span>
											</td>
											<td height="30">${orderItemMetaId}</td>
											<td>
											<a href="javascript:openWin('/super_back/metas/view_index.zul?metaProductId=${metaProductId}&productType=${productType }',700,700)">${productName}</a>
											</td>
											<td>${settlementPriceYuan}</td>
											<td>${actualSettlementPrice/100}</td>
											<td>
												<s:if test="productType=='HOTEL'">
													<s:property value="hotelQuantity" />
												</s:if> <s:else>
													<s:property value="productQuantity*quantity" />
												</s:else>
											</td>
											<td>${totalSettlementPriceYuan }</td>
											<td>${ordOrder.userName }</td>
											<td>${ordOrder.contact.name }</td>
											<td>
												<s:date name="ordOrder.createTime" format="yyyy-MM-dd HH:mm:ss" />
											</td>
											<td>
												<s:date name="ordOrder.dealTime" format="yyyy-MM-dd HH:mm:ss" />
											</td>
											<td>
												<s:date name="ordOrder.visitTime" format="yyyy-MM-dd HH:mm:ss" />
											</td>
											<td>
												<s:if test="zhResourceStatus=='待跟进'">
													<span style="color: red;"> 待跟进</span>
												</s:if>
												<s:else>
													${zhResourceStatus }
												</s:else>
											</td>
											<td>
											<s:if test="supplierFlag=='true'">
												${zhCertificateStatus }
											</s:if>
											</td>
											<td align="left" class="optTd">
												<input type="button" name="btnAuditOrd" value="审 核" class="right-button02"
													onclick="javascript:showDetailDiv('approveDiv', ${orderId }, ${orderItemMetaId})">
												<s:if test="zhResourceStatus!='待跟进'">
													<input type="button" name="btnItemRefundOrder" value="退 单" class="right-button02"
														onclick="javascript:window.location='<%=basePath%>ordItem/doCancelOrderItem.do?id=${orderItemMetaId}&tab=2&permId=${permId}&opPage=true';">
												</s:if>
												<s:if test="supplierFlag=='true'">
													<input type="button" name="btnItemCheckOrder" value="查看订单" class="right-button08"
														onclick="javascript:showDetailDivMessage('${orderItemMetaId}');">
												</s:if>
												<input type="button" value="修改结算价" class="right-button08" id="${orderItemMetaId}"
													name="<s:property value="actualSettlementPriceYuan"/>"
													alt="<s:property value="totalSettlementPriceYuan"/>"
													productQuantity="<s:property value="productQuantity"/>"
													quantity="<s:property value="quantity"/>"
													sellPrice="<s:property value="sellPrice"/>"
													title="<s:property value="totalQuantity"/>"
													onClick="updatePrice(this, 1, '<%=basePath%>')" />
												<input type="button" value="修改结算总价" class="right-button08" id="${orderItemMetaId}"
													name="<s:property value="totalSettlementPriceYuan"/>"
													alt="<s:property value="actualSettlementPriceYuan"/>"
													title="<s:property value="totalQuantity"/>"
													productQuantity="<s:property value="productQuantity"/>"
													quantity="<s:property value="quantity"/>"
													sellPrice="<s:property value="sellPrice"/>"
													onClick="updatePrice(this, 2, '<%=basePath%>')" />
												<bi>
													<input type="button" name="show_log" value="查看日志" class="right-button08" 
														id="${orderItemMetaId}" onclick="showLog(this)" />
												</bi>
												<s:if test="supplierFlag=='true'">
												<s:if test="certificateStatus=='REJECT'">
														<input type="button" name="btnItemResendOrder" value="重发订单" class="right-button08" 
															onclick="return reSendOrder('${orderItemMetaId}')">
													<s:if test="ebkCertificateType=='CHANGE'">
														<input type="button" name="btnWasteOrder" value="废单(单笔订单监控)" class="right-button09"
															onclick="window.location.href='<%=basePath%>ord/order_monitor_list.do?pageType=single&orderId=${orderId}';">
													</s:if>
												</s:if>
												</s:if>
											</td>
										</tr>
									</s:iterator>
								</tbody>
							</table>
							<p class="alignr">
							<input id="updateBatchPrice" type="button" class="right-button08" value="批量修改" alt="<%=basePath%>" />
							<!-- input type="button" id="updateBatchPrice" name="updateBatchPrice" value="修改结算价" class="right-button08" /> -->
							<input type="button" name="btnResourceNotValid" value="资源不满足" class="right-button08"
								onclick='btn_AuditOrder("checkname","frmNewOrder","<%=basePath%>ordItem/doResourceLackOrder.do?permId=${permId}")' /></p>
						</form>
					</div>
					<!--=========================主体内容 end==============================-->
				</div>
				<!--main2 end-->
			</div>

			<table width="90%" border="0" align="center">
				<s:include value="/WEB-INF/pages/back/base/pag.jsp" />
			</table>

			<!--弹出层灰色背景-->
			<div id="bg" class="bg" style="display: none;"></div>
			<div class="orderpop" id="approveDiv" style="display: none;"
				href="<%=basePath %>/ordItem/showApproveOrderDetail.do"></div>
				
			<div id="confirm_div" style="display:none; width: 200px;"></div>
			<div id="update_div" style="display:none; width: 400px;">
				<form id="update_form" method="post"> 
					<table style="font-size: 12; text-align: left;height: 210px;">
						<tr height="50">
							<td width="150" align="right"><span class="type"></span></td>
							<td align="left">
								<input id="settlementPrice2add" type="text" name="settlementPrice2add" />
								<span class="msg" style="color:red"></span>
								<input id="ordItemId2add" type="hidden" name="ordItemId2add" />
								<input id="changeType2add" type="hidden" name="changeType2add" />
								<input id="priceBeforeUpdate" type="hidden" name="priceBeforeUpdate" />
								<input id="totalPriceBeforeUpdate" type="hidden" name="totalPriceBeforeUpdate" />
							</td>
						</tr>
						<tr height="50">
							<td align="right">修改原因：</td>
							<td align="left">
								<select name="reason2add" class="cash_name" id="reason2add">
									<s:iterator value="resultList" id="item">
										<option value="${item.code}">${item.cnName}</option>
									</s:iterator>
								</select>
							</td>
						</tr>
						<tr height="50">
							<td align="right">备注：</td>
							<td align="left">
								<textarea id="remark2add" name="remark2add" rows="4" cols="21"></textarea>
								<span class="msg_remark" style="color:red"></span>
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</body>
</html>
						