<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
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
		<link href="<%=basePath%>themes/base/jquery.ui.all.css"
			rel="stylesheet"></link>
		<script type="text/javascript"
			src="<%=basePath%>js/ui/jquery-ui-1.8.5.js"></script>
	</head>
	<body>



		<div id="tg_order">

			<div class="wrap">
				<div class="main2">
					<!--=========================主体内容==============================-->
					<ul class="tags">
						<li class="tags_at" id="tags_title_1" onclick='Show_list_div(1,3)'>
							常规售后
						</li>
						<li class="tags_none" id="tags_title_2"
							onclick='showDiw(2,3,"<%=basePath%>/ordSale/queryOrderSale.do?serviceType=COMPLAINT&tab=2")'>
							投诉
						</li>
						<li class="tags_none" id=tags_title_3
							onclick='showDiw(3,3,"<%=basePath%>/ordSale/queryOrderSale.do?serviceType=URGENCY&tab=3")'>
							紧急入园
						</li>
					</ul>
					<div class="table_box" id=tags_content_1>
						<form name='frmWaitOrder' id="frmWaitOrder" method='post'
							action='ordSale/queryOrderSale.do'>
							<input type="hidden" name="serviceType" value="${serviceType}" />
							<input type="hidden" name="tab" value="${tab}" />
							<div class="mrtit3">
								<table width="100%" border="0" style="font-size: 12;">
									<tr>
										<td>
											订单号:
										</td>
										<td>
											<input name="ordOrderId" type="text" value="${ordOrderId}" />
										</td>
										<td>
											下单人：
										</td>
										<td>
											<input name="userName" type="text" value="${userName}" />
										</td>
										<td>
											下单人手机：
										</td>
										<td>
											<input name="moblieNumber" type="text"
												value="${moblieNumber}" />
										</td>

									</tr>
									<tr>
										<td>
											申请人：
										</td>
										<td>
											<input name="opearUserName" type="text"
												value="${opearUserName}" />
										</td>
										<td>
											产品类型：
										</td>
										<td >
											<s:select name="orderType" id="orderType"
												list="#{'--查询所有--':'','门票':'TICKET','自由行,单人出发':'FREENESS','跟团游,多人成团':'GROUP'}"
												value="''" listKey="value" listValue="key"></s:select>
										</td>
										<td>
											申请时间：
										</td>
										<td >
											<input name="saleBeginDate" type="text"
												value="${saleBeginDate}" />
											~
											<input name="saleEndDate" type="text" value="${saleEndDate}" />
										</td>
										<!-- <td>
											申请类型：
										</td>
										<td>
											<s:select list="channelList" id="serviceType" listKey="code"
												listValue="name" name="serviceType" headerKey=""
												value="'NORMAL'" value="NORMAL" headerValue="--请选择--">
											</s:select>
										</td> -->
									</tr>
									<tr>
										<td colspan="6" align="center">
											<input type='submit' name="btnOrdSaleQuery" value="查 询" class="right-button08" />
										</td>
									</tr>
								</table>
							</div>
							<br />
							<table cellspacing="1" cellpadding="4" border="0"
								bgcolor="#666666" width="100%" class="newfont06"
								style="font-size: 12; text-align: center;">
								<tr bgcolor="#eeeeee">
									<td width="8%">
										订单号
									</td>
									<td width="8%">
										下单人
									</td>
									<td width="10%">
										手机号
									</td>
									<td width="6%">
										产品类型
									</td>
									<td width="6%">
										游玩时间
									</td>
									<td width="11%">
										申请时间
									</td>
									<td width="11%">
										申请类型
									</td>
									<td width="11%">
										申请内容
									</td>
									<td width="11%">
										操作
									</td>
								</tr>

								<s:iterator value="ordSaleServiceList">
									<tr bgcolor="#ffffff">
										<td>
											${orderId}
										</td>
										<td>
											${ordOrder.userName}
										</td>
										<td>
											${ordOrder.mobileNumber }
										</td>
										<td>
											${orderItemProd.productType}
										</td>
										<td>
											<s:date name="ordOrder.visitTime" format="yyyy-MM-dd" />
										</td>
										<td>
											<s:date name="createTime" format="yyyy-MM-dd HH:mm:ss" />
										</td>
										<td>
											${serviceTypeName}
										</td>
										<td>
											${applyContent}
										</td>
										<td>
											<a href="#"
												onclick="showSaleDiv('historySaleDealDiv','${saleServiceId}','');">处理结果</a>
											<a href="#"
												onclick="showSaleDiv('historyRefundMentDiv','${saleServiceId}','${orderId}');">退款处理</a>
										</td>
									</tr>
								</s:iterator>
							</table>
						</form>
					</div>


					<!--wrap end-->
					<div class="orderpop" id="historySaleDealDiv"
						style="display: none;"
						href="<%=basePath%>ordSale/tranitOrdDeal.do">
					</div>
					<!--弹出层灰色背景-->
					<div id="bg" class="bg" style="display: none;"></div>
					<div class="orderpop" id="historyRefundMentDiv"
						style="display: none;"
						href="<%=basePath%>ordRefund/tansitAddRefundMent.do">
					</div>
					<!--弹出层灰色背景-->
					<div id="bg" class="bg" style="display: none;"></div>
					<!--=========================主体内容 end==============================-->
				</div>
				<!--main2 end-->
			</div>

			<table width="90%" border="0" align="center">
				<s:include value="/WEB-INF/pages/back/base/pag.jsp" />
			</table>

		</div>
		<!--main2 end-->
		<script type="text/javascript">
//显示弹出层
function showSaleDiv(divName,saleServiceId,orderId) {
	document.getElementById(divName).style.display = "block";
	document.getElementById("bg").style.display = "block";
	//请求数据,重新载入层
	$("#" + divName).reload({"saleServiceId": saleServiceId,"orderId": orderId});
}

function showDiw(type1,type2,url){
		Show_list_div(type1,type2);
		window.location=url;
}
var tabIndex = (${tab}==null || ${tab}==0)?1:${tab}; 
Show_list_div(tabIndex, 3);
$(function() {
	$("input[name='saleBeginDate']" ).datepicker({dateFormat:'yy-mm-dd'});
	$("input[name='saleEndDate']").datepicker({dateFormat:'yy-mm-dd'});
});
//显示tab下信息
function Show_list_div($z, $kk) {
	for ($i = 1; $i <= $kk; $i++) {
		$div_name = "tags_content_" + $i;
		$title_name = "tags_title_" + $i;
		$object = document.getElementById($div_name);
		$objects = document.getElementById($title_name);
		if ($i == $z) {
			$objects.className = "tags_at";
		} else {
			$objects.className = "tags_none";
		}
	}
}
function closeDetailDiv(divName) {
	document.getElementById(divName).style.display = "none";
	document.getElementById("bg").style.display = "none";
}
</script>
	</body>
</html>
