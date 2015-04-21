<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() 
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<script type="text/javascript" src="<%=basePath %>js/base/jquery-1.4.4.min.js"></script>
		 <script type="text/javascript">
		 		function changeOptType(type){
		 			if(type=='COUPON_AMOUNT'){
		 			 	$("#UPDATE_AMOUNT").css("display","none");
		 			 	$("#COUPON_AMOUNT").css("display","");
		 			}else{
		 				$("#UPDATE_AMOUNT").css("display","");
		 			 	$("#COUPON_AMOUNT").css("display","none");
		 			}
		 		}
		 		
		 		function addModfyAmountApplay(){
		 			var orderId = $("#orderId").val();
		 			var amountMoeny = $("#amountMoenyId").val();
		 			var memo = $("#memoId").val();
		 			var param = {'ordOrderAmountApplay.orderId':orderId,'ordOrderAmountApplay.amount':amountMoeny,'ordOrderAmountApplay.memo':memo}
		 			$.ajax({type : "POST",dataType : "html",url : "<%=basePath%>ord/saveModifyAmountApplay.do",async : false,data :param,timeout : 3000,
						success : function(data) {
							loadApplayList(orderId);
						}
					});
		 		}
		 		function loadApplayList(orderId){
 		 			var param = {orderId:orderId}
		 			$.ajax({type : "POST",dataType : "html",url : "<%=basePath%>ord/modifyAmountApplayList.do",async : false,data :param,timeout : 3000,
						success : function(data) {
							 $("#applayListId").html(data);
						}
					});
		 		}
		 </script>
	</head>

	<body onload="loadApplayList('<s:property value="orderId"/>')">
	&lt;<input type="hidden" id="orderId" value="<s:property value="orderId"/>">
	<table border="1">
	<tr>
		<td>
				<input type="radio" checked="checked"  name="modifyAmount" onchange="changeOptType('UPDATE_AMOUNT')"/>修改金额
				<input type="radio"  name="modifyAmount" onchange="changeOptType('COUPON_AMOUNT')"/>优惠券
		</td>

		<td>
				<div id="UPDATE_AMOUNT">
						请输入金额：<input type="text" id="amountMoenyId">(元)
						<select id="type">
							<option selected="selected">请选择原因类型</option>
							<option>test</option>
							<option>test2</option>
							<option>test3</option>
						</select>
						备注：<input type="text" id="memoId">
				</div>
				<div id="COUPON_AMOUNT" style="display: none;">
					请输入优惠券号码：<input type="text" id="amountMoenyId">
				</div>
		</td>
		<td><input type="button" onclick="addModfyAmountApplay()" value="添加"> </td>
	</tr>
		</table>
		 <div id="applayListId"></div>
	</body>

</html>

