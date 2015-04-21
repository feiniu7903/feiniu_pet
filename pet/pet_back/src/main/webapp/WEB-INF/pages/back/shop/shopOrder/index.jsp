<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>订单管理</title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>themes/icon.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>themes/mis.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/base/back_base.css">

<jsp:include page="/WEB-INF/pages/pub/jquery.jsp" />
<jsp:include page="/WEB-INF/pages/pub/suggest.jsp" />
<jsp:include page="/WEB-INF/pages/pub/timepicker.jsp" />
<script type="text/javascript" src="http://pic.lvmama.com/js/ui/lvmamaUI/lvmamaUI.js"  charset="utf-8"></script>
</head>

<body>

<div id="editRemarkDiv" style="display: none"></div>
<div id="showDetailDiv" style="display: none"></div>

	<div class="main main02">
		<div class="row1">
			<h3 class="newTit">订单管理</h3>
			<form action="<%=basePath%>shop/shopOrder/queryShopOrderList.do" method="post" id="shopOrderListForm">
                <input type="hidden" value="" id="lastest3day" name="lastest3day"/>
				<table border="1" cellspacing="0" cellpadding="0" class="search_table" width="100%">
					<tr>
						<td>订单编号：</td>
						<td>
							<s:textfield id="orderId" name="orderId" cssClass="newtext1" />
						</td>
						<td>兑换时间：</td>
						<td><input id="startDate" type="text" class="newtext1"
							name="startDate" value="${startDate}"/> ~<input id="endDate" type="text"
							class="newtext1" name="endDate" value="${endDate}"/>
						</td>
						<td>产品类型: </td>
						<td>
							<select id="productType" name="productType">					
								<option value = "" <s:if test='productType == ""'>selected</s:if> >全部</option>
								<option value = "COUPON" <s:if test='productType == "COUPON"'>selected</s:if> >优惠卷</option>
								<option value = "COOPERATION_COUPON" <s:if test='productType == "COOPERATION_COUPON"'>selected</s:if> >合作网站优惠券</option>
								<option value = "PRODUCT" <s:if test='productType == "PRODUCT"'>selected</s:if> >实物</option>
							</select>
						</td>
					</tr>
					
					<tr>
						<td>产品名称：</td>
						<td>
							<s:textfield id="productName" name="productName" cssClass="newtext1"/>
						</td>
						<td>用户名：</td>
						<td> 
							<s:textfield id="userName" name="userName" cssClass="newtext1"/>
						</td>
						<td>订单状态：</td>
						<td> 
							<select id="orderStatus" name="orderStatus">					
								<option value = "" <s:if test='orderStatus == ""'>selected</s:if> >全部</option>
								<option value = "UNCONFIRM" <s:if test='orderStatus == "UNCONFIRM"'>selected</s:if> >末发货</option>
								<option value = "FINISHED" <s:if test='orderStatus == "FINISHED"'>selected</s:if> >已发货</option>
								<option value = "CANCEL" <s:if test='orderStatus == "CANCEL"'>selected</s:if> >取消</option>
							</select>
						</td>
					</tr>
					
					<tr>
					    <td align="right"><input type="button" class="button" value="导出" id="exportExcelBtn" <s:if test='disabled == true'>disabled="isDisabled"</s:if>/></td>
					    <td align="right"><input type="button" class="button" value="查询"  id="btnSubmit"/></td>
					    <td align="right"><input type="button" class="button" value="批量发货" onClick="batchDelivery('ORDER_DELIVERY')" <s:if test='disabled == true'>disabled="isDisabled"</s:if>/></td>
					    <td align="left"><input type="button" class="button" value="批量取消" onClick="batchCancel('ORDER_CANCEL')" <s:if test='disabled == true'>disabled="isDisabled"</s:if>/></td>
						<td></td><td></td>
					</tr>
					
				</table>
			</form>
		</div>
		
		<div class="row2">
			<table border="1" cellspacing="0" cellpadding="0" class="gl_table" width="100%">
				<tr>
				    <th width="25px"><input type="checkbox" id="allShopOrderSelect" onClick="selectAll()"/></th>
					<th width="50px" align="middle">订单编号</th>
					<th align="middle">兑换时间</th>
					<th>产品类型</th>
					<th align="middle">兑换产品名称</th>
					<th width="70px" align="middle">兑换数量</th>
					<th align="middle">收货人姓名</th>
					<th align="middle">收货人地址</th>
					<th align="middle">收货人电话</th>
					<th>订单状态</th>
					<th>操作</th>
				</tr>
				
				<s:iterator value="shopOrderList" var="shopOrder" status="c">
					<tr>
					    <td><input type="checkbox" id="checkBox<s:property value="#c.index"/>"  value="${shopOrder.orderId}"/></td>
						<td align="middle">${shopOrder.orderId}</td>
						<td>${shopOrder.formatterCreatedTime}</td>
						<td>${shopOrder.formatProductType}</td>
						<td>${shopOrder.productName}</td>
						<td align="middle">${shopOrder.quantity}</td>
						<td>${shopOrder.name}</td>
						<td>${shopOrder.address}</td>
						<td>${shopOrder.mobile}</td>
						<td>${shopOrder.formatOrderStatus}</td>
                        <td>
	                        <input type="submit" class="button" value="查看" id="query" onClick="showDetail(${shopOrder.orderId}, 'showDetail')"/><br/>
	                        <input type="button" class="button" value="发货" id="delivery" <s:if test='orderStatus != "UNCONFIRM"'>disabled="isDisabled"</s:if> onClick="operationOrder('ORDER_DELIVERY',${shopOrder.orderId})"/><br/>
	                        <input type="button" class="button" value="取消订单" id="cancel" <s:if test='orderStatus != "UNCONFIRM"'>disabled="isDisabled"</s:if> onClick="operationOrder('ORDER_CANCEL',${shopOrder.orderId})"/><br/>
	                        <input type="button" class="button" value="操作备注" id="remark" onClick="showDetail(${shopOrder.orderId}, 'editRemark')"/><br/>
                        </td>
					</tr>
				</s:iterator>
				<tr>
     				<td colspan="3"> 总条数：<s:property value="pagination.totalResultSize"/> </td>
     				<td colspan="13" align="left">
     					<s:property escape="false" value="@com.lvmama.comm.utils.Pagination@pagination(pagination)"/>
     				</td>
    			</tr> 
				<tr>
     				 <td colspan="16"></td>
    			</tr>
			</table>
			
		</div>
	</div>
</body>

<script type="text/javascript">
 
$(function(){
     $('#startDate').datepicker({dateFormat: 'yy-mm-dd'});
     $('#endDate').datepicker({dateFormat: 'yy-mm-dd'});
     
     $("#btnSubmit").click(function(){
    	 var orderId = $("#orderId").val();
    	 var reg = /^[0-9]*[1-9][0-9]*$/;
    	 if(orderId != "" && !reg.test(orderId)){
    		  alert("订单号填写数字。");
    		  return;
    	}
    	$("#shopOrderListForm").submit();
     });
});

//导出.
function operationOrder(operationType, orderId) {
	/** 登陆完提交页面  **/
	$("#myForm").attr("target","_self");
	$("#myForm").submit();

}

//执行订单操作.
function operationOrder(operationType, orderId) {
	
	var data = "orderId=" + orderId + "&operationType=" + operationType;
	if(operationType == "ORDER_DELIVERY"){
		if(!confirm("确定发货吗")){
			return false;
		} 
	}else if(operationType == "ORDER_CANCEL"){ 
		if(!confirm("确定取消吗")){
			return false;
		}
	}
	$.ajax({
	 	url: "<%=basePath%>shop/shopOrder/operationOrder.do",
	 	data: data,
	 	beforeSend: function(XMLHttpRequest){
             XMLHttpRequest.setRequestHeader("RequestType", "ajax");
        },
		type:"post",
	 	dataType:"json",
	 	success: function(result) {
	 		if (result.success) {
	 			alert("操作成功.");
	 		 	window.location.reload();	
	 		 	//window.location.href="<%=basePath%>shop/shopOrder/queryShopOrderList.do?_=" + (new Date).getTime();
	 		} else {
	 			alert(result.errorMessage);
	 		}
	 	}
	 });
}


//批处理取消订单.
function batchCancel(operationType) {
	
	var orderIds = "";
	for(var i = 0; i < 10; i++)
	{
		if($("#checkBox"+i) != null && $("#checkBox"+i).attr("checked")==true)
		{
			orderIds += $("#checkBox"+i).val() + ",";
		}
	}
	if(orderIds == ""){
		alert("请至少选择一个订单项！");
		return;
	}
	var data = "orderIds=" + orderIds;
	if(operationType == "delivery"){
		if(!confirm("确定发货吗")){
			return false;
		}
	}
	$.ajax({
	 	url: "<%=basePath%>shop/shopOrder/batchCancel.do",
	 	data: data,
	 	beforeSend: function(XMLHttpRequest){
           XMLHttpRequest.setRequestHeader("RequestType", "ajax");
      },
		type:"post",
	 	dataType:"json",
	 	success: function(result) {
	 		if (result.success) {
	 			alert("操作成功.");
	 		 	window.location.reload();	
	 		 	//去掉所有选择项
	 			for(var i = 0; i < 10; i++)
	 			{
	 				if($("#checkBox"+i) != null)
	 				{
	 					$("#checkBox"+i).attr("checked",false);
	 				}
	 			}
	 		} else {
	 			alert(result.errorMessage);
	 		}
	 	}
	 });
}
//批处理发货.
function batchDelivery(operationType) {
	var orderIds = "";
	for(var i = 0; i < 10; i++)
	{
		if($("#checkBox"+i) != null && $("#checkBox"+i).attr("checked")==true)
		{
			orderIds += $("#checkBox"+i).val() + ",";
		}
	}
	if(orderIds == ""){
		alert("请至少选择一个订单项！");
		return;
	}
	var data = "orderIds=" + orderIds;
	if(operationType == "delivery"){
		if(!confirm("确定发货吗")){
			return false;
		}
	}
	$.ajax({
	 	url: "<%=basePath%>shop/shopOrder/batchDelivery.do",
	 	data: data,
	 	beforeSend: function(XMLHttpRequest){
           XMLHttpRequest.setRequestHeader("RequestType", "ajax");
      },
		type:"post",
	 	dataType:"json",
	 	success: function(result) {
	 		if (result.success) {
	 			alert("操作成功.");
	 		 	window.location.reload();	
	 		 	//去掉所有选择项
	 			for(var i = 0; i < 10; i++)
	 			{
	 				if($("#checkBox"+i) != null)
	 				{
	 					$("#checkBox"+i).attr("checked",false);
	 				}
	 			}
	 		} else {
	 			alert(result.errorMessage);
	 		}
	 	}
	 });
}

//导出到Excel.
$("#exportExcelBtn").click(function() {
	//表单提交 
	var req = "orderId=" + $("#orderId").val() + "&startDate=" + $("#startDate").val() + "&endDate=" + $("#endDate").val()+ "&productType=" + $("#productType").val() + 
			"&productName=" + $("#productName").val() + "&userName=" + $("#userName").val() + "&orderStatus=" + $("#orderStatus").val();
	if(!confirm("确定导出吗(该功能最多导出900笔记录)")){
		return false;
	}
	var url = "<%=basePath%>shop/shopOrder/doExport.do?" + req;
	window.location = url;
});

//查看订单/编辑备注.
function showDetail(orderId, operation) {
	$("#showDetailDiv").load("<%=basePath%>shop/shopOrder/shopOrderDetail.do?_=" + (new Date).getTime() + "&orderId=" + orderId + "&operation=" + operation,function() {
		$(this).dialog({
			modal:true,
			title:"查看订单",
			width:950,
			height:380
    	});
	});
}

//选择所有记录
function selectAll()
{
	if($("#allShopOrderSelect").attr("checked")==true)
	{
		for(var i = 0; i < 10; i++)
		{
			if($("#checkBox"+i) != null)
			{
				$("#checkBox"+i).attr("checked",true);
			}
		}
	}
	else if($("#allShopOrderSelect").attr("checked")==false)
	{
		for(var i = 0; i < 10; i++)
		{
			if($("#checkBox"+i) != null)
			{
				$("#checkBox"+i).attr("checked",false);
			}
		}
	}
}
    </script>
</html>
