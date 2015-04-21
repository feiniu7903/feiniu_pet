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
		<link href="<%=basePath%>themes/base/jquery.ui.all.css"
			rel="stylesheet"></link>
		<script type="text/javascript"
			src="<%=basePath%>js/ui/jquery-ui-1.8.5.js"></script>
			<script type="text/javascript"
			src="<%=basePath%>js/base/lvmama_common.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/base/perm.js"></script>
		<script type="text/javascript">
		$(document).ready(function(){
			$("input[name='ordItemVisitTimeStart']" ).datepicker({dateFormat:'yy-mm-dd'});
			$("input[name='ordItemVisitTimeEnd']").datepicker({dateFormat:'yy-mm-dd'});
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
		//发送传真
		function sendFax(id){
			if(confirm("是否确定转为传真方式？")){
				$.ajax({
					type : "post",
					url : "ordItem/sendFax.do?id="+id,
					cache : false,
					success : function(data) {
						if(data=="SUCCESS"){
							alert("操作成功！")
							location.reload();
						}else{
							alert(data);
						}
					},
					error : function(data) {
						alert("操作错误！");
					}
				});
			}
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
						<li class="tags_none" id="tags_title_1" onclick='location.href="<%=basePath%>ordItem/opListWaitResult.do?permId=${permId}"'>
							待审核任务
						</li> 
						<li class="tags_none" id=tags_title_2
							onclick='location.href="<%=basePath%>ordItem/opListAuditResult.do?permId=${permId}"'>
							我的订单审核任务
						</li>
						<li class="tags_at" id="tags_title_3"
							onclick='location.href="<%=basePath%>ordItem/opOrdItemHistoryQuery.do?permId=${permId}"'>
							我的历史订单
						</li>
					</ul>

					<div class="table_box" id=tags_content_3>
						<div class="mrtit3">
							<form name='form1' method='post'
								action='<%=basePath%>ordItem/opOrdItemHistoryQuery.do'>
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
									<td width="6%">订单处理人</td>
									<td height="35" width="6%">订单子号</td>
									<td width="12%">采购产品</td>
									<td width="5%">结算单价</td>
									<td width="6%">实际结算价</td>
									<td width="3%">数量</td>
									<td width="7%">下单人</td>
									<td width="7%">供应商</td>
									<td width="9%">下单时间</td>
									<td width="9%">首处理时间</td>
									<td width="7%">游玩时间</td>
									<td width="5%">资源状态</td>
									<td width="5%">确认状态</td>
									<td width="8%">操作</td>
								</tr>
								<s:iterator id="order" value="historyOrdersList">
									<s:iterator var="itemMeta" value="allOrdOrderItemMetas">
										<tr bgcolor="#ffffff">
											<td height="30">
												${orderId} 
												<s:if test="supplierFlag=='true' && (productType=='HOTEL' or productType=='ROUTE')">
													<span style="color: red;">(EBK订单)</span>
												</s:if>
											</td>
											<td>
												<span class="perm">
													<s:property value="takenOperator" />
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
											<td>
											<s:if test="supplierFlag=='true'">
											${zhCertificateTypeStatus}
											</s:if>
											</td>
											<td>
												<a href="javascript:showDetailDiv('historyDiv', ${orderId }, ${orderItemMetaId});">查看</a>
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
				href="<%=basePath %>ordItem/showHistoryOrderDetail.do">
			</div>
			<div id="bg" class="bg" style="display: none;"></div>
		</div>
	</body>
</html>
