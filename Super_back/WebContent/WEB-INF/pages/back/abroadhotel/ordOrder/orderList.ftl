<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<#assign basePath="${request.scheme}://${request.serverName}:${request.serverPort?c}${request.getContextPath()}">
		<title>境外酒店订单查询</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<link href="${basePath}/style/jquery.editable-select.css" type="text/css" rel="stylesheet" />
		<link href="${basePath}/style/phoneorder/superCss.css" type="text/css" rel="stylesheet" />
		<link href="${basePath}/style/houtai.css" type="text/css" rel="stylesheet" />
		<link href="${basePath}/themes/base/jquery.ui.all.css" rel="stylesheet"></link>
		<script type="text/javascript" src="${basePath}/js/base/jquery-1.4.4.min.js"></script>
		<script type="text/javascript" src="${basePath}/js/ui/jquery-ui-1.8.5.js"></script>
		<script type="text/javascript" src="${basePath}/js/base/jquery.datepick-zh-CN.js"></script>
		<script type="text/javascript" src="${basePath}/js/base/remoteUrlLoad.js"></script>
		<script type="text/javascript" src="${basePath}/js/base/form.js"></script>
		<script type="text/javascript" src="${basePath}/js/base/jquery.showLoading.min.js"></script>
		<script type="text/javascript" src="${basePath}/js/base/jquery-ui-timepicker-addon.js"></script>
		<script type="text/javascript" src="${basePath}/js/base/jquery.easyui.min.js"></script>
		<script type="text/javascript" src="${basePath}/js/ord/ord_div.js" type="text/javascript"></script>
		<link href="${basePath}/themes/base/showLoading.css" rel="stylesheet" type="text/css" />
		<link href="${basePath}/themes/suggest/jquery.suggest.css" rel="stylesheet" type="text/css" />
		<link href="${basePath}/themes/base/jquery.ui.all.css" rel="stylesheet" type="text/css" />
		<link href="${basePath}/themes/default/easyui.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" type="text/css" href="${basePath}/themes/cc.css" />
		<style type="text/css">
			.datatable {
				margin-top: 10px;
				margin-left: 5px;
				margin-bottom: 10px;
				background: #fff;
			}
			
			.datatable th {
				background: #BAD9FA;
				line-height: 27px;
				font-size: 12px;
			}
			
			.main {
				background: #fff;
			}
			
			input {
				border: 1px solid #A1C5E6;
				height: 14px;
				padding: 3px 0;
				vertical-align: middle;
			}
			.ui-timepicker-div .ui-widget-header { margin-bottom: 8px; }
			.ui-timepicker-div dl { text-align: left; }
			.ui-timepicker-div dl dt { height: 25px; margin-bottom: -25px; }
			.ui-timepicker-div dl dd { margin: 0 10px 10px 65px; }
			.ui-timepicker-div td { font-size: 90%; }
			.ui-tpicker-grid-label { background: none; border: none; margin: 0; padding: 0; }
			.pagination-info{
				float:left;
				padding-right:6px;
				padding-left:16px;
				padding-top:16px;
				font-size:12px;
				
			}
		</style>
	</head>
	<body>
		<form id="queryListTest" action="abroadhotel/ordOrder/list.do" name="queryListTest"
			method="post">
			<table class="newTable" >
				<tr>
					<td align="right">订单编号：</td>
					<td>
						<input name="reservationsOrderReq.OrderNo" 
						value="${(reservationsOrderReq.orderNo)!}">
					</td>
					<td colspan="8"></td>
				</tr>
				<tr>
					<td align="right">
						联系人姓名：
					</td>
					<td>
						<input name="reservationsOrderReq.ContactPersonName" 
						value="${(reservationsOrderReq.contactPersonName)!}">
					</td>
					<td align="right">
						联系人手机：
					</td>
					<td>
						<input name="reservationsOrderReq.ContactMobile" 
						value="${(reservationsOrderReq.contactMobile)!}">
					</td>
					<td align="right">
						电子邮件：
					</td>
					<td>
						<input name="reservationsOrderReq.ContactEmail" 
						value="${(reservationsOrderReq.contactEmail)!}">
					</td>
					<td align="right">
						下单时间：
					</td>
					<td>
						<input id="ResMadeFromId" name="reservationsOrderReq.ResMadeFrom" type="text" 
							value="${(reservationsOrderReq.resMadeFrom?string('yyyy-MM-dd'))!}"/>
					</td>
					<td align="center">
						~
					</td>
					<td>
						<input id="ResMadeToId" name="reservationsOrderReq.ResMadeTo" type="text" 
							value="${(reservationsOrderReq.resMadeTo?string('yyyy-MM-dd'))!}"/>
					
					</td>
				</tr>
				<tr>
					<td align="right">
						支付状态：
					</td>
					<td>
						<select name="reservationsOrderReq.paymentStatus">
							<option value="">--请选择--</option>
							<option value="PAYED" 
								<#if reservationsOrderReq??>
									<#if reservationsOrderReq.paymentStatus=='PAYED'>selected</#if>
								</#if>
							>已支付</option>
							<option value="UNPAY"
								<#if reservationsOrderReq??>
									<#if reservationsOrderReq.paymentStatus=='UNPAY'>selected</#if>
								</#if>
							>未支付</option>
						</select>
					</td>
					<td align="right">
						审核状态：
					</td>
					<td>
						<select name="reservationsOrderReq.approveStatus">
							<option value="">--请选择--</option>
							<option value="APPROVED" 
								<#if reservationsOrderReq??>
									<#if reservationsOrderReq.approveStatus=='APPROVED'>selected</#if>
								</#if>
							>已审核</option>
						</select>
					</td>
					<td align="right">
						订单状态：
					</td>
					<td>
						<select name="reservationsOrderReq.orderStatus">
							<option value="">--请选择--</option>
							<option value="CONFIRMED" 
								<#if reservationsOrderReq??>
									<#if reservationsOrderReq.orderStatus=='CONFIRMED'>selected</#if>
								</#if>
							>正常</option>
							<option value="CANCEL"
								<#if reservationsOrderReq??>
									<#if reservationsOrderReq.orderStatus=='CANCEL'>selected</#if>
								</#if>
							>取消</option>
							<option value="SUCCESS"
								<#if reservationsOrderReq??>
									<#if reservationsOrderReq.orderStatus=='SUCCESS'>selected</#if>
								</#if>
							>完成</option>
							<option value="CANCEL_FAILED"
								<#if reservationsOrderReq??>
									<#if reservationsOrderReq.orderStatus=='CANCEL_FAILED'>selected</#if>
								</#if>
							>取消失败</option>
						</select>
					</td>
					<td align="right">
						入住时间：
					</td>
					<td>
						<input id="CheckInFromId" name="reservationsOrderReq.CheckInFrom" type="text" 
							value="${(reservationsOrderReq.checkInFrom?string('yyyy-MM-dd'))!}"/>
					</td>
					<td align="center">
						~
					</td>
					<td>
						<input id="CheckInToId" name="reservationsOrderReq.CheckInTo" type="text" 
							value="${(reservationsOrderReq.checkInTo?string('yyyy-MM-dd'))!}"/>
					</td>
				</tr>
				<tr>
					<td align="right">处理人：</td>
					<td>
						<input name="reservationsOrderReq.CancelOperator" 
						value="${(reservationsOrderReq.cancelOperator)!}">
					</td>
					<td align="right">首处理时间：</td>
					<td>
						<input id="CancelledFromId" name="reservationsOrderReq.CancelledFrom" type="text" 
							value="${(reservationsOrderReq.cancelledFrom?string('yyyy-MM-dd'))!}"/>
					</td>
					<td align="center">~</td>
					<td>
						<input id="CancelledToId" name="reservationsOrderReq.CancelledTo" type="text" 
							value="${(reservationsOrderReq.cancelledTo?string('yyyy-MM-dd'))!}"/>
					</td>
					<td align="right">支付时间：</td>
					<td>
						<input id="PaymentTimeFromId" name="reservationsOrderReq.PaymentTimeFrom" type="text" 
							value="${(reservationsOrderReq.paymentTimeFrom?string('yyyy-MM-dd'))!}"/>
					</td>
					<td align="center">~</td>
					<td>
						<input id="PaymentTimeToId" name="reservationsOrderReq.PaymentTimeTo" type="text" 
							value="${(reservationsOrderReq.paymentTimeTo?string('yyyy-MM-dd'))!}"/>
					</td>
				</tr>
				<tr>
					<td colspan="9">
						<input type="hidden" name="isInit" value="true">
					</td>
					<td colspan="1">
						<input class="button" type="submit" value="查询" id="submitQuery">
					</td>
				</tr>
			</table>
			<table border="0" cellspacing="0" cellpadding="0" class="newTable">
				<tr class="newTableTit">
					<td  class="prodw">
						订单号
					</td>
					<td class="prodw">
						处理人
					</td>
					<td class="prodw">
						联系人姓名
					</td>
					<td class="prodw">
						联系人电话
					</td>
					<td class="prodw">
						产品名
					</td>
					<td class="prodw">
						订购数量
					</td>
					<td class="prodw">
						订单状态
					</td>
					<td class="prodw">
						审核状态
					</td>
					<td class="prodw">
						支付状态
					</td>
					<td class="prodw">
						下单时间
					</td>
					<td class="prodw">
						操作
					</td>
				</tr>
				<#if reservationsOrderRes??> <#if
				reservationsOrderRes.reservationsOrders??> <#list
				reservationsOrderRes.reservationsOrders as reservationsOrder>
				<tr>
					<td class="prodw">
						${(reservationsOrder.orderNo)!}
					</td>
					<td class="prodw">
						${(reservationsOrder.cancelOperator)!}
					</td>
					<td class="prodw">
						${(reservationsOrder.contactPersonName)!}
					</td>
					<td class="prodw">
						${(reservationsOrder.contactMobile)!}
					</td>
					<td class="prodw">
						${(reservationsOrder.productName)!}
					</td>
					<td class="prodw">
						${(reservationsOrder.quantity)!}
					</td>
					<td class="prodw">
						${(reservationsOrder.orderStatusZH)!}
					</td>
					<td class="prodw">
						${(reservationsOrder.approveStatusZH)!}
					</td>
					<td class="prodw">
						${(reservationsOrder.paymentStatusZH)!}
					</td>
					<td class="prodw">
						${(reservationsOrder.createdTime?string('yyyy-MM-dd HH:mm:ss'))!}
					</td>
					<td>
						<a href="javascript:showDetailDiv('historyDiv', '${(reservationsOrder.orderNo)!}');">查看</a>
						<!--<a href="javascript:showPaymentInfoDiv('paymentInfoDiv', '${(reservationsOrder.orderNo)!}');">支付信息</a>-->
						<a href="javascript:showSaleServiceDiv('${(reservationsOrder.orderNo)!}');">售后服务</a>
						<#if reservationsOrderReq.orderStatus=='CANCEL_FAILED'>
							<br><a href="javascript:showDetailDiv('statusDiv', '${(reservationsOrder.orderNo)!}');">重置状态</a>
						</#if>
						
					</td>
				</tr>
				</#list> </#if> </#if>
			</table>
			<div id="pp" style="background:#efefef;border:1px solid #ccc;"></div> 
			<input id="pagination_page" name="page" type="hidden">
			<input id="pagination_size" name="perPageRecord" type="hidden">
			<input id="sortStrId" name="sortStr" type="hidden">
		</form>
		<div class="orderpop" id="historyDiv" style="display: none;"
			href="${basePath}/abroadhotel/ordOrder/detail.do">
		</div>
		<div class="orderpop" id="statusDiv" style="display: none;margin-top:100px;"
			href="${basePath}/abroadhotel/ordOrder/resetOrderStatus.do">
		</div>
		<div class="orderpop" id="paymentInfoDiv" style="display: none;margin-top:100px;"
			href="${basePath}/abroadhotel/ordOrder/paymentInfo.do">
		</div>
		<div id="bg" class="bg" style="display: none;">
			<iframe
				style="position: absolute; width: 100%; height: 100%; _filter: alpha(opacity = 0); opacity =0; border-style: none; z-index: -1">
			</iframe>
		</div>
	</body>
	<script type="text/javascript">
		window.onload=	function(){
			$('#ResMadeFromId').datepicker();
		 	$('#ResMadeToId').datepicker();
		 	$('#CheckInFromId').datepicker();
		 	$('#CheckInToId').datepicker();
		 	$('#CancelledFromId').datepicker();
		 	$('#CancelledToId').datepicker();
		 	$('#PaymentTimeFromId').datepicker();
		 	$('#PaymentTimeToId').datepicker();
		 	$('#pp').pagination({ 	
					total:${(pagination.totalRecords)!},   
					pageSize:${(pagination.perPageRecord)!}, 
					pageNumber:${(pagination.page)!},
					onSelectPage:function(pageNumber, pageSize){
						$('#pagination_size')[0].value=pageSize;
						$('#pagination_page')[0].value=pageNumber;
						$("#submitQuery").click();
					},
					onChangePageSize:function(pageSize){
						$('#pagination_size')[0].value=pageSize;
						$("#submitQuery").click();
					},
					displayMsg:"<#if (pagination.totalRecords)?? && pagination.totalRecords!=0>从{from}到{to}条,</#if>总共{total}条",
					beforePageText:"第",
					afterPageText:"页,共{pages}页"
					
				}); 
		 }
		
		function showDetail(orderNo){
			var strUrl="${basePath}/abroadhotel/ordOrder/detail.do?reservationsOrderReq.OrderNo="+orderNo;
			//document.location=strUrl;
			//var strUrl="${basePath}/abroadhotel/ordOrder/downloadVoucher.do";
			//document.location=strUrl;
			window.open(strUrl,"_blank","height=700,width=1000");
		}
		
		//关闭层
		function closeDetailDiv(divName) {
			document.getElementById(divName).style.display = "none";
			document.getElementById("bg").style.display = "none";
			
		}
		//显示弹出层
		function showDetailDiv(divName, orderNo) {
			document.getElementById(divName).style.display = "block";
			document.getElementById("bg").style.display = "block";
			//请求数据,重新载入层
			$("#" + divName).reload({"reservationsOrderReq.OrderNo": orderNo});
		}
		//显示支付信息弹出层
		function showPaymentInfoDiv(divName, orderNo) {
			document.getElementById(divName).style.display = "block";
			document.getElementById("bg").style.display = "block";
			//请求数据,重新载入层
			$("#" + divName).reload({"reservationsOrderReq.OrderNo": orderNo});
		}
		//显示售后服务弹出层
		function showSaleServiceDiv(orderNo) {
			var url="${basePath}/abroadhotel/sale/ahotelOrdSaleAddJump.zul?orderId="+orderNo;
			window.open(url,'','height=500,width=1000,top=200, left=200,scrollbars=yes');
		}
	//ajax发送请求操作
	function httpRequest(url, param, closeFlag, divName) {
		$.ajax({type:"POST", url:url + "?random=" + Math.random(), data:param, success:function (result) {
			alert(result);
			var res = eval(result);
			alert(res);
			if (res) {
				alert("\u64cd\u4f5c\u6210\u529f!");
				var flag = closeFlag != null ? closeFlag : false;
				var div = divName != null ? divName : "approveDiv";
				//关闭层
				if (flag) {
					closeDetailDiv(div);
					document.location.reload();
				} else {
					$("#"+div).reload({"orderId":orderId});
				}
			} else {
				alert("\u64cd\u4f5c\u5931\u8d25!");
			}
		}});
	}
	//退款并取消订单
	function doCancelOrder(divName,orderNo) {
		var reason = $("#cancelReason").find("option:selected").text();
		$("input[name=btnCancelOrder1]")[0].disabled=true;
		window.open('${basePath}/abroadhotel/refundMent/ahotelOrdOrderRefundAdd.zul?orderId='+orderNo+'&isCancelOrder=true&cancelResson=' + reason,
						'',
						'height=500,width=1000,top=200, left=200,scrollbars=yes');
		//httpRequest("doOrderCancel.do", {"orderId":orderNo, "orderDetail.cancelReason":reason}, true, divName);
	}
	//取消订单
	function doCancelLocalOrder(divName,orderNo) {
		var btn=$("input[name=btnCancelOrder1]");
		btn[0].disabled=true;
		var reason = $("#cancelReason").find("option:selected").text();
		var url="${basePath}/abroadhotel/ordOrder/cancelOrder.do?random=" + Math.random();
		var param={'reservationsOrderReq.orderNo':orderNo,'cancelReason':reason};
		$.ajax({
			type:"POST",
			url:url,
			data:param,
			success:function(result){
				var jsonObj = JSON.parse(result);
				if(jsonObj.errorID!=null && jsonObj.errorID.length>0){
					alert(jsonObj.errorMessage+";"+jsonObj.subErrorMessage);
					btn[0].disabled=false;
				}else{
					alert("订单成功取消！");
					btn[0].disabled=false;
					closeDetailDiv(divName);
					$('#pagination_size')[0].value=${(pagination.perPageRecord)!};
					$('#pagination_page')[0].value=${(pagination.page)!};
					$("#submitQuery").click();
				}
			}
		});
	}
	
	//重置订单状态
	function doResetOrder(divName,orderNo) {
		var btn=$("input[name=btnResetOrder]");
		btn[0].disabled=true;
		var reason = $("input[name=resetReason]")[0].value;
		var orderStatus = $("#orderStatusId").find("option:selected")[0].value;
		var approveStatus = $("#approveStatusId").find("option:selected")[0].value;
		var paymentStatus = $("#paymentStatusId").find("option:selected")[0].value;
		if(orderStatus==null || orderStatus.length==0 || approveStatus==null || approveStatus.length==0 ||paymentStatus==null || paymentStatus.length==0){
			alert("请设置新状态");
			btn[0].disabled=false;
			return;
		}
		var message="状态将被置为：";
			message+="\r\n\r\n订单状态:"+$("#orderStatusId").find("option:selected").text();
			message+="\r\n支付状态:"+$("#paymentStatusId").find("option:selected").text();
			message+="\r\n审核状态:"+$("#approveStatusId").find("option:selected").text();
		if (confirm(message)==false)
		{
		 	btn[0].disabled=false;
		 	return;
		}
		
		var url="${basePath}/abroadhotel/ordOrder/doResetOrderStatus.do?random=" + Math.random();
		var param={'reservationsOrderReq.orderNo':orderNo,
			'cancelReason':reason,
			'reservationsOrderReq.orderStatus':orderStatus,
			'reservationsOrderReq.approveStatus':approveStatus,
			'reservationsOrderReq.paymentStatus':paymentStatus
			};
		$.ajax({
			type:"POST",
			url:url,
			data:param,
			success:function(result){
				var jsonObj = JSON.parse(result);
				if(jsonObj.flag){
					alert(jsonObj.message);
					btn[0].disabled=false;
					closeDetailDiv(divName);
					$('#pagination_size')[0].value=${(pagination.perPageRecord)!};
					$('#pagination_page')[0].value=${(pagination.page)!};
					$("#submitQuery").click();
				}else{
					alert(jsonObj.message);
					btn[0].disabled=false;
				}
			}
		});
	}
    </script>
</html>
