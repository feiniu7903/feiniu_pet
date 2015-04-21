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
		<script type="text/javascript"
			src="<%=basePath%>js/ui/jquery-ui-1.8.5.js"></script>
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>themes/cc.css" />
		<script type="text/javascript"
			src="<%=basePath%>js/base/remoteUrlLoad.js"></script>
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>themes/icon.css">
		<script type="text/javascript" src="<%=basePath%>js/base/form.js"></script>
		<script language="javascript" src="<%=basePath%>js/xiangmu.js"
			type="text/javascript"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/base/lvmama_common.js"></script>
		<script language="javascript" src="<%=basePath%>js/ord/ord_item.js"
			type="text/javascript"></script>
		<link href="<%=basePath%>themes/base/jquery.ui.all.css"
			rel="stylesheet"></link>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/base/perm.js"></script>
			
		<script type="text/javascript">
		
		$(function() {
			$("input[name='ordItemVisitTimeStart']" ).datepicker({dateFormat:'yy-mm-dd'});
		
			$("input[name='ordItemVisitTimeEnd']").datepicker({dateFormat:'yy-mm-dd'});

			if("${supplierFlag}") {
				$("#supplierFlag").attr("checked", "checked");
			}
		
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
		</script>
		<script type="text/javascript" src="<%=basePath%>js/base/jquery.datepick-zh-CN.js"></script>	
		
	</head>
	<body>



		<div id="tg_order">

			<div class="wrap">
				<div class="main2">
					<!--=========================主体内容==============================-->
					<ul class="tags">
						<li class="tags_at" id="tags_title_1" onclick='location.href="<%=basePath%>ordItem/opListWaitResult.do?permId=${permId}"'>
							待审核任务
						</li> 
						<li class="tags_none" id=tags_title_2
							onclick='location.href="<%=basePath%>ordItem/opListAuditResult.do?permId=${permId}"'>
							我的订单审核任务
						</li>
						<li class="tags_none" id="tags_title_3"
							onclick='location.href="<%=basePath%>ordItem/opOrdItemHistoryQuery.do?permId=${permId}"'>
							我的历史订单
						</li>
					</ul>
					<div class="table_box" id=tags_content_1>
						<form name='frmWaitOrder' id="frmWaitOrder" method='post'
							action='ordItem/opListWaitResult.do'>
							<input type="hidden" name="permId" value="${permId}"></input>
							<div class="mrtit3">
								<table width="100%" border="0" style="font-size: 12;">
									<tr>
										<td>供应商</td>
										<td><input name="ordItemSupplierName" type="text"
											value="${ordItemSupplierName}" /></td>
										<td>采购产品：</td>
										<td><input name="ordItemMetaProductName" type="text"
											value="${ordItemMetaProductName}" /></td>
										<td><input type="checkbox" name="supplierFlag"
											value="true" id="supplierFlag" /> EBK订单</td>
									</tr>
									<tr>
										<td>游玩时间：</td>
										<td><input name="ordItemVisitTimeStart" type="text"
											value="${ordItemVisitTimeStart}" /> ~ <input
											name="ordItemVisitTimeEnd" type="text"
											value="${ordItemVisitTimeEnd}" /></td>
										<td colspan="2" align="center"><input type='submit'
											name="btnQryOrdItemList" value="查 询" class="right-button08" />
										</td>
									</tr>
								</table>
							</div>
							<br />
							
							<input type="button" name="btnItemListGetOrder" value="领单" class="right-button08"
								onclick='btn_AuditOrder("checkAuditName","frmWaitOrder","<%=basePath%>ordItem/opGetOrderAll.do")' />
							<input type="hidden" name="productType" value="${productType}" />
							<input type="hidden" name="tab" value="1" />
							<table cellspacing="1" cellpadding="4" border="0"
								bgcolor="#666666" width="100%" class="newfont06"
								style="font-size: 12; text-align: center;">
								<tbody>
									<tr bgcolor="#eeeeee">
										<td width="2%">&nbsp;</td>
										<td width="6%">订单号</td>
										<td width="6%">订单处理人</td>
										<td width="6%">订单子号</td>
										<td width="15%">采购产品</td>
										<td width="5%">数量</td>
										<td width="8%">下单人</td>
										<td width="7%">联系人</td>
										<td width="9%">下单时间</td>
										<td width="9%">首处理时间</td>
										<td width="8%">游玩时间</td>
										<td width="8%">有库存时下单</td>
										<td width="6%">资源状态</td>
										<td width="6%">支付状态</td>
									</tr>
									<s:include value="/WEB-INF/pages/back/ord/ord_item_list_page.jsp"/>
								</tbody>
							</table>
						</form>
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
				href="<%=basePath %>/ordItem/showHistoryOrderDetail.do">
			</div>
			<!--弹出层灰色背景-->
			<div id="bg" class="bg" style="display: none;"></div>
			<div class="orderpop" id="approveDiv" style="display: none;"
				href="<%=basePath %>/ordItem/showApproveOrderDetail.do"></div>
		</div>
		
	</body>
</html>
