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
		<script type="text/javascript">
		$(function() {
			//游玩时间:开始时间.
			$("input[name='ordOrderVisitTimeStart']" ).datepicker({dateFormat:'yy-mm-dd'});
			//游玩时间:结束时间.
			$("input[name='ordOrderVisitTimeEnd']").datepicker({dateFormat:'yy-mm-dd'});
			
			if("${supplierFlag}") {
				$("#supplierFlag").attr("checked", "checked");
			}
		});
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
		});
		function showDiw(type1,type2,url){
			Show_list_div(type1,type2);
			document.getElementById("frmNewOrder").action=url;
			document.getElementById("frmNewOrder").submit();
		}
		function btn_AuditOrder(chkName,frm,url){
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
	</head>
	<body>
		<div id="tg_order">
			<div class="wrap">
				<div class="main2">
					<!--=========================主体内容==============================-->
					<ul class="tags">
						<li class="tags_at" id="tags_title_1" onclick='Show_list_div(1,3)'>
							待审核任务
						</li>
						<li class="tags_none" id=tags_title_2
							onclick='showDiw(2,3,"<%=basePath%>ordItem/listAuditResult.do?permId=${permId}")'>
							我的订单审核任务
						</li>
						<li class="tags_none" id="tags_title_3" onclick='Show_list_div(3,3)'>
							我的历史订单
						</li>
					</ul>
					<div class="table_box" id=tags_content_1>
						<form name='frmWaitOrder' id="frmWaitOrder" method='post' action='ordItem/listWaitResult.do'>
							<input type="hidden" name="permId" value="${permId}"></input>
							<div class="mrtit3">
								<table width="100%" border="0" style="font-size: 12;">
									<tr>
										<td>采购产品:</td>
										<td>
											<input name="metaProductName" type="text" value="${metaProductName}" />
										</td>
										<td>联系人：</td>
										<td>
											<input name="contactName" type="text" value="${contactName}" />
										</td>
										<td>游玩时间：</td>
										<td>
											<input name="ordOrderVisitTimeStart" type="text"
													value="${ordOrderVisitTimeStart}" readonly="readonly"/>
												~
											<input name="ordOrderVisitTimeEnd" type="text"
													value="${ordOrderVisitTimeEnd}" readonly="readonly"/>
										</td>
										<td>
											<s:if test="productType=='HOTEL' or productType=='ROUTE'">
												<input type="checkbox" name="supplierFlag" value="true" id="supplierFlag"/>
												是否EBK订单
											</s:if>
										</td>
									</tr>
									<tr>
										<td>销售产品:</td>
										<td>
											<input name="productName" type="text" value="${productName}" />
										</td>
										<td>手机号码：</td>
										<td>
											<input name="contactMobile" type="text" value="${contactMobile}" />
										</td>
										<td>所属公司：</td>
										<td colspan="2"><s:select list="filialeNameList" name="filialeName" listKey="code" listValue="name"/>
											<input type='submit' name="btnQryOrdItemList" value="查 询" class="right-button08" />
										</td>
									</tr>
								</table>
							</div>
							<br />
							<mis:checkPerm permCode="1589,1598,1607,1733,3312" permParentCode="${permId}">
								<input type="button" name="btnItemListGetOrder" value="领单" class="right-button08"
								onclick='btn_AuditOrder("checkAuditName","frmWaitOrder","<%=basePath%>ordItem/doGetOrderAll.do")' />
							</mis:checkPerm>
							<input type="hidden" name="productType" value="${productType}" />
							<input type="hidden" name="tab" value="1" />
							<table cellspacing="1" cellpadding="4" border="0" bgcolor="#666666" width="98%" class="newfont06"
								style="font-size: 12; text-align: center;">
								<tbody>
									<tr bgcolor="#eeeeee">
										<td width="4%">&nbsp;</td>
										<td width="8%">订单号</td>
										<td width="8%">订单处理人</td>
										<td width="5%">订单子号</td>
										<td width="15%">采购产品</td>
										<td width="4%">数量</td>
										<td width="8%">下单人</td>
										<td width="6%">联系人</td>
										<td width="10%">下单时间</td>
										<td width="10%">首处理时间</td>
										<td width="7%">游玩时间</td>
										<td width="5%">是否减库存</td>
										<td width="5%">资源状态</td>
									</tr>
									<s:include value="/WEB-INF/pages/back/ord/ord_item_list_page.jsp"/>
								</tbody>
							</table>
						</form>
					</div>

				<s:if test="!workGetOrder">
			         <div class="table_box" id=tags_content_2>
				                 已被领单
				    </div>
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
										<td width="4%">
											<input type="checkbox" name="checkall" value="1" id="checkall" />
										</td>
										<td width="6%">订单号</td>
										<td width="6%">订单子号</td>
										<td width="10%">采购产品</td>
										<td width="4%">数量</td>
										<td width="8%">下单人</td>
										<td width="6%">联系人</td>
										<td width="10%">下单时间</td>
										<td width="10%">首处理时间</td>
										<td width="7%">游玩时间</td>
										<td width="7%">资源保留时间</td>
										<td width="6%">资源状态</td>
										<td width="6%">确认状态</td>
										<td width="10%">操作</td>
									</tr>
									<s:include value="/WEB-INF/pages/back/ord/ord_item_audit_list_page.jsp"/>
								</tbody>
							</table>
                            <%--<p class="alignr">
                                <mis:checkPerm permCode="1595,1604,1613,1739,3315" permParentCode="${permId}">
                                    <input type="button" name="btnResourceNotValid" value="资源不满足" class="right-button08"
                                           onclick='btn_AuditOrder("checkname","frmNewOrder","<%=basePath%>ordItem/doResourceLackOrder.do?permId=${permId}")'
                                            />
                                </mis:checkPerm>
                            </p>--%>
						</form>
					</div>
                 
					<div class="table_box" id=tags_content_3 style="display: none;">
						<div class="mrtit3">
							<form name='form1' method='post'
								action='<%=basePath%>ordItem/doOrdItemHistoryQuery.do'>
								<input type="hidden" name="permId" value="${permId}"></input>
								<input type="hidden" name="productType" value="${productType}" />
								<input type="hidden" name="tab" value="3" />
								<table width="98%" border="0" style="font-size: 12;">
									<tr>
										<td>订 单 号：</td>
										<td>
											<input name="ordItemOrderId" type="text" value="${ordItemOrderId}" />
										</td>
										<td>下 单 人：</td>
										<td>
											<input name="ordItemUserName" type="text" value="${ordItemUserName}" />
										</td>
										<td>供应商：</td>
										<td>
											<input name="ordItemSupplierName" type="text" value="${ordItemSupplierName}" />
										</td>
									</tr>
									<tr>
										<td>采购产品：</td>
										<td>
											<input name="ordItemMetaProductName" type="text" value="${ordItemMetaProductName}" />
										</td>
										<td>游玩时间：</td>
										<td>
											<input name="ordItemVisitTimeStart" type="text" value="${ordItemVisitTimeStart}" />
											 ~ 
											<input name="ordItemVisitTimeEnd" type="text" value="${ordItemVisitTimeEnd}" />
										</td>
										<td colspan="2" align="center">
											<input type='submit' value="查 询" name="btnOrdQueryItemList" class="right-button08" />
										</td>
									</tr>
								</table>
							</form>
						</div>

						<table cellspacing="1" cellpadding="4" border="0"
							bgcolor="#666666" width="98%" class="newfont06"
							style="font-size: 12; text-align: center;">
							<tbody>
								<tr bgcolor="#eeeeee">
									<td height="35" width="5%">订单号</td>
									<td height="35" width="6%">订单子号</td>
									<td width="18%">采购产品</td>
									<td width="4%">数量</td>
									<td width="8%">下单人</td>
									<td width="8%">供应商</td>
									<td width="12%">下单时间</td>
									<td width="12%">首处理时间</td>
									<td width="8%">游玩时间</td>
									<td width="6%">资源状态</td>
									<td width="6%">操作</td>
								</tr>
								<s:iterator id="order" value="historyOrdersList">
									<s:iterator value="allOrdOrderItemMetas">
										<tr bgcolor="#ffffff">
											<td height="30">
												<a href="javascript:openWin('/super_back/ord/order_monitor_list!doOrderQuery.do?pageType=monitor&orderId=${orderId }',700,700)">${orderId}</a>
												<s:if test="supplierFlag=='true' && (productType=='HOTEL' or productType=='ROUTE')">
													<span style="color: red;">(EBK订单)</span>
												</s:if>
											</td>
											<td height="30">${orderItemMetaId}</td>
											<td>
												<a href="javascript:openWin('/super_back/metas/view_index.zul?metaProductId=${metaProductId}&metaBranchId=${metaBranchId}&productType=${productType }',700,700)">${productName}</a>
											</td>
											<td>
												<s:if test="productType=='HOTEL'">
													${hotelQuantity }
												</s:if>
												<s:else>
													<s:property value="productQuantity*quantity" />
												</s:else>
											</td>
											<td>${userName }</td>
											<td>
												<a href="javascript:openWin('/pet_back/sup/detail.do?supplierId=${supplier.supplierId}',700,700)">${supplier.supplierName}</a>
											</td>
											<td>
												<s:date name="createTime" format="yyyy-MM-dd HH:mm:ss" />
											</td>
											<td>
												<s:date name="dealTime" format="yyyy-MM-dd HH:mm:ss" />
											</td>
											<td><s:date name="visitTime" format="yyyy-MM-dd HH:mm:ss" /></td>
											<td>${zhResourceStatus}</td>
											<td>${zhPaymentStatus}</td>
											<td>
												<mis:checkPerm permCode="1597,1606,1615,1741,3316" permParentCode="${permId}">
													<a href="javascript:showDetailDiv('historyDiv', ${orderId }, ${orderItemMetaId});">查看</a>
												</mis:checkPerm>
												<s:if test="supplierFlag=='true' && certificateStatus=='CREATE' && ebkCertificateType != 'ENQUIRY'">
													<a href="#" onclick="return sendFax('${orderItemMetaId}')">转传真</a>
												</s:if>
											</td>
										</tr>
									</s:iterator>
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
			Show_list_div(tabIndex, 3);
		</script>
	</body>
</html>
