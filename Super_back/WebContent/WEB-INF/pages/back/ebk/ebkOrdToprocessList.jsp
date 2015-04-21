<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>订单处理后台_Ebooking待处理订单监控</title>
<script type="text/javascript"
	src="${basePath}/js/base/jquery-1.4.4.min.js"></script>
<script type="text/javascript"
	src="${basePath}/js/base/jquery-ui-1.8.5.js"></script>
<!-- LVMAMA -->
<script type="text/javascript" src="${basePath}js/base/form.js"></script>
<script type="text/javascript" src="${basePath}js/base/lvmama_common.js">
	
</script>
<script type="text/javascript" src="${basePath}js/base/lvmama_dialog.js">
	
</script>
<!-- jquery -->
<script type="text/javascript"
	src="${basePath}/js/base/jquery.datepick-zh-CN.js"></script>
<script type="text/javascript"
	src="${basePath}/js/base/jquery.jsonSuggest-2.min.js"></script>
<!-- css -->
<link rel="stylesheet" type="text/css" href="${basePath }/themes/base/jquery.ui.all.css" >
<link rel="stylesheet" type="text/css" href="${basePath }/themes/ebk/admin.css" >
<link rel="stylesheet" type="text/css" href="${basePath}/themes/ebk/ui-components.css" />
<link type="text/css" rel="stylesheet" href="${basePath}/themes/base/jquery.jsonSuggest.css">

<script type="text/javascript">
	$(function() {
		$("input[name='createTime1']").datepicker({
			dateFormat : 'yy-mm-dd'
		});
		$("input[name='createTime2']").datepicker({
			dateFormat : 'yy-mm-dd'
		});
		$("#taskType").val("${taskType}");
		$("#consumeTime").val("${consumeTime}");
	});

	$(function() {
		//查找供应商
		$("#searchSupplierName").jsonSuggest({
			url : "${basePath}/supplier/searchSupplier.do",
			maxResults : 10,
			minCharacters : 1,
			onSelect : function(item) {
				$("#comSupplierId").val(item.id);
			}
		});
	});

	//显示弹出层
	function showDetailDiv(id) {
		var url = "${basePath}/ebooking/order/ebkOrdToprocessDetail.do?ebkTaskId=" + id;
		
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

	//我要跟进
	function turnOnFollowOrder(id) {
		var url = "${basePath}/ebooking/order/turnOnFollowOrder.do?ebkTaskId="
				+ id;
		$.post(url, function(data) {
			if(data.success==true){
				alert(data.message);
			}else {
				alert(data.message);
			}
 			document.location.reload();
		}, "json");
	}
	//解除跟进
	function turnOffFollowOrder(id) {
		var url = "${basePath}/ebooking/order/turnOffFollowOrder.do?ebkTaskId="
				+ id;
		$.post(url, function(data) {
			if(data.success==true){
				alert(data.message);
			}else {
				alert(data.message);
			}
 			document.location.reload();
		}, "json");
	}
</script>
</head>
<body>
<input type="hidden" name="currentUser" value="${currentUser}" />
<form name='form1' method='get' action='${basePath}/ebooking/order/ebkOrdToprocessList.do'>
	<table width="99%" border="0" class="newfont06"
		style="font-size: 12; text-align: left;">
		<tr>
			<td align="right" width="8%">订单号：</td>
			<td width="12%"><input name="orderId" value="${orderId}" />
			</td>

			<td align="right" width="8%">酒店名称：</td>
			<td width="12%"><input type="text" id="searchSupplierName"
				name="supplierName" class="newtext1"  /> 
				<input type="hidden" name="supplierId" id="comSupplierId"
				 /></td>

			<td align="right" width="8%">下单时间：</td>
			<td width="32%"><input name="createTime1" value="${createTime1 }" /> ~
				<input name="createTime2" value="${createTime2 }" /></td>
		</tr>
		<tr>
			<td align="right" width="8%">订单状态：</td>
			<td><select id="taskType" name="taskType" style="width: 125px;">
					<option value="">全部</option>
					<option value="RESOURCE_CONFIRM">正常</option>
					<option value="CANCEL_CONFIRM">取消</option>
			</select></td>

			<td align="right" width="8%">耗时：</td>
			<td><select id="consumeTime" name="consumeTime" style="width: 125px;">
					<option value="">全部</option>
					<option value="FIVE_MIN">5分钟以内</option>
					<option value="FIVE_TEN_MIN">5-10分钟</option>
					<option value="TEN_MIN">10分钟以上</option>
			</select></td>
		    <td align="right" width="8%">跟进人ID：</td>
			<td width="12%"><input name="followUserId" value="${followUserId}" />
			</td>
		</tr>
		<tr>
			<td colspan="4"></td>
			<td colspan="2"><input type="submit" value="查 询"
				class="right-button08 btn btn-small" name="btnOrdMonitorQuery" /></td>
			<td>
		</tr>
	</table>
</form>
<table class="gl_table">
	<tbody>
		<tr class="chengse">
			<th width="100">订单号</th>
			<th>产品名称</th>
			<th width="60">订单状态</th>
			<th width="60">确认状态</th>
			<th width="60">支付状态</th>
			<th width="115">订单提交时间</th>
			<th width="70">耗时</th>
			<th width="80">跟进人</th>
			<th width="120">最后跟进时间</th>
			<th width="80">操作</th>
		</tr>
		<s:iterator id="order" value="ebkTaskPage.items">
		     <s:if test="consumeTime <= 10">
				 <tr bgcolor="FFFFFF" > 
			 </s:if> 
			 <s:else>
				 <s:if test="followUser == null||followUser == ''">
				     <tr   class="hongse"><!-- red -->
				 </s:if>
				 <s:else>
				     <tr  class="chengse">
				 </s:else>
			 </s:else>
				<td height="30">${orderId}
				<s:if test="resourceConfirm == 'false'"><span class="orange">(保留房)</span></s:if>
				<s:if test="orderUpdate == 'true'"><span style="color:red">(有备注)</span></s:if>
				</td>
				<td height="30">${productName}</td>
				<td><s:if test="taskType == 'CANCEL_CONFIRM'">
						<span class="red">${zhTaskType }</span>
					</s:if> <s:else>
			   		  	${zhTaskType }
			   		  </s:else></td>
				<td>${zhViewResourceStatus }</td>
				<td>${zhPaymentStatus }</td>
				<td>${zhCreateTime}</td>
				<td>${ZhConsumeTime}</td>
				<td>${followUser }</td>
				<td>${zhFollowTime }</td>
				<td><a
					href="javascript:showDetailDiv('${ebkTaskId}');">
						查看</a>
				     <s:if test="followUser == null||followUser == ''">
				    	 <a href="javascript:turnOnFollowOrder('${ebkTaskId}');">
						我要跟进</a> 
 				     </s:if>
 				     <s:if test="followUser == currentUser">
 				            <a href="javascript:turnOffFollowOrder('${ebkTaskId}');">
						解除跟进</a> 
 				     </s:if>
 				  </td>
			</tr>
		</s:iterator>
		<tr bgcolor="#ffffff">
			<td colspan="3" align="left">共有<s:property
					value="ebkTaskPage.totalResultSize" />个订单酒店还未处理
			</td>
			<td colspan="7" align="right">
			${ebkTaskPage.pagination }
			</td>
		</tr>
	</tbody>
</table>
</body>
</html>

