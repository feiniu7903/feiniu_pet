<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
<script type="text/javascript">
		function doPriceOperator(url, data) {
			$.ajax({type:"POST", url:url, data:data, success:function (dt) {
			
						var res=eval("("+dt+")");
						if(res.success){
					
					alert("\u64cd\u4f5c\u6210\u529f!");
					$("#priceDiv").reload({"orderId":data.orderId});
				} else {
					alert("\u64cd\u4f5c\u5931\u8d25!");
				}
			}});
		}
		
function addAmount(oughtPay, actualPay,payStatus) {
	var amountType = $("#amountType").val();
	var moneyYuan = $("#moneyYuan").val();
	var money = $("#moneyYuan").val()*100;
	var price = oughtPay - actualPay;
	var   r = /^\+?[1-9][0-9]*$/; //正整数   
	if(payStatus=='PAYED'){
		 document.getElementById("PAY1").innerHTML="该订单已支付完成,不能修改价格";
		 return ;
	}
	if(!r.test(moneyYuan)){
		document.getElementById("PAY1").innerHTML="金额必须是正整数并且金额必须大于0元";
		return ;
	}
	if (amountType == 'REDUCE') {
		if (money >= price) {
			document.getElementById("PAY1").innerHTML = "减少的费用应小于应付金额减去实付金额";
			return ;
		}
	}
	if (confirm("您确定要提交该订单金额的修改？")) {
		var data = $("#priceForm").getForm({prefix:""});
		doPriceOperator("<%=basePath%>ord/saveModifyAmountApply.do", data);
	}
}


function approveApplyOrder(orderId,applyId,applyStatus) {
	var url="<%=basePath%>ord/approveApplyOrder.do";
	var datafrom={orderId:orderId,applyId:applyId,applyStatus:applyStatus}
	$.ajax({type:"POST", url:url, data:datafrom, success:function (dt) {
				var res=eval("("+dt+")");
					if(res.success){
					alert("\u64cd\u4f5c\u6210\u529f!");
					$("#historyDiv").reload({"orderId":orderId});
				   } else if(res.code==1){
					   alert("此订单金额申请违反原则,此申请系统会自动取消的!!");
					  $("#historyDiv").reload({"orderId":orderId});
				   }else{
					alert(res.msg);
				}
			}});
}


</script>
	</head>

	<body>
		<s:if test="order==null">
		<div>${errorMessages}</div>
		</s:if>
		<s:else>
		<strong>价格修改</strong>
		<s:if test="!order.isCanceled()&&!order.isPaymentSucc()">
		<div class="orderremark">
			<form id="priceForm" method="post">
				<input type="hidden" id="orderId" name="orderId" value="${orderId}" />
				
				<select name="ordOrderAmountApply.applyType" id="ordOrderAmountApply.applyType">
							<s:iterator id="mountApplyType" value="orderAmountTypes">
								<option value="${mountApplyType.code }">
									${mountApplyType.name }
								</option>
							</s:iterator>
						</select>
				<select name="amountType" id="amountType"> 
					<option value="ADD">
						增加费用
					</option>
					<option value="REDUCE">
						减少费用
					</option>
				</select>
				<input type="text" id="moneyYuan" name="moneyYuan"/>
				元
				申请备注<input id="ordOrderAmountApply.applyMemo" name="ordOrderAmountApply.applyMemo" type="text">
					<a href="javascript:addAmount('${order.oughtPay}','${order.actualPay}','${order.paymentStatus}');">确 定</a>
					
			</form>
		</div>
		</s:if>
		<span
			style="font-weight: 100; color: #f00; margin-left: 10px; font-weight: bold"
			id="PAY1"></span>
		
		<table style="font-size: 12px" cellspacing="1" cellpadding="4"
			id="changPrice" border="0" bgcolor="#B8C9D6" width="100%"
			class="newfont03">
			<tbody>
				<tr bgcolor="#f4f4f4" align="center">
				    <td height="30" width="10%">
						修改价格原因
					</td>
					<td height="30" width="10%">
						类型
					</td>
					<td width="8%">
						修改金额
					</td>
					<td width="6%">
						申请者
					</td>
					<td width="6%">
						审核者
					</td>
					<td width="10%">
						审核状态
					</td>
					<td width="10%">
						审核时间
					</td>
					<td width="20%">
						申请备注
					</td>
					<td width="20%">
						审核备注
					</td>
					
				</tr>
				<s:if test="ordOrderAmountApplyList!=null&&ordOrderAmountApplyList.size()>0">
				<s:iterator value="ordOrderAmountApplyList" id="orderAmount">
					<tr bgcolor="#ffffff" align="center">
					    <td height="25">
							${orderAmount.zhApplyType}
						</td>
						<td>
							<s:if test="#orderAmount.amount>0">增加费用</s:if>
							<s:else>减少费用</s:else>
						</td>
						<td>
							${orderAmount.amountYuan}
						</td>
						<td>
							${orderAmount.applyUser}
						</td>
						<td>
							${orderAmount.approveUser}
						</td>
						<td>
						    ${orderAmount.orderAmountApplyStatusStr}
						</td>
						<td>
						    <s:date name="approveTime" format="yyyy-MM-dd HH:mm:ss"/>
							<fmt:formatDate value="${orderAmount.approveTime}" pattern="yyyy-MM-dd HH:mm:ss" />
						</td>
						<td>
							${orderAmount.applyMemo}
						</td>
						<td>
							${orderAmount.approveMemo}
						</td>
						
					</tr>
				</s:iterator>
				</s:if>
			</tbody>
		</table>
		</s:else>
	</body>
</html>
