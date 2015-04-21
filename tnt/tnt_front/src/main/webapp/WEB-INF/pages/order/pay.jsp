<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="lv" uri="/WEB-INF/pages//tld/lvmama-tags.tld"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>订单支付</title>
<link rel="shortcut icon" type="image/x-icon" href="http://www.lvmama.com/favicon.ico">
<link
	href="http://pic.lvmama.com/min/index.php?f=/styles/v5/modules/tip.css"
	rel="stylesheet" type="text/css">
<link
	href="http://pic.lvmama.com/min/index.php?f=/styles/v5/modules/button.css,/styles/v4/modules/dialog.css"
	rel="stylesheet" type="text/css">
<link href="http://pic.lvmama.com/styles/fx/b2b_fx.css" rel="stylesheet"
	type="text/css">
</head>
<body>
	<jsp:include page="/WEB-INF/pages/common/head.jsp"></jsp:include>
	<div class="orderstep_wrap">
		<div class="orderstep step03"></div>
		<div class="tipbox tip-success tip-nowrap">
			<span class="tip-icon-big tip-icon-big-success"></span>
			<div class="tip-content">
				<h3 class="tip-title">订单提交成功，请支付订单！</h3>
			</div>
		</div>
		<h1 class="orderH1">
			<c:forEach items="${orders }" var="order">
				<p>
					<b>预订：</b>${order.productName }
				</p>
			</c:forEach>
		</h1>
		<div class="yucun">
			<h4>
				预存款账户支付<span class="tiptext tip-line"><span
					class="tip-icon tip-icon-warning"></span>
					注：请于${lastWaitPaymentTime}之前完成支付，逾期订单将失效</span>
			</h4>
			<sf:form id="payForm" method="post" action="/order/pay.do"
				modelAttribute="tntCashAccount">
				<input type="hidden" name="orderId" id="orderId" value="${orderId }" />
				<div class="yucun_account">
					<p>
						<b>您的账户余额：</b><span>${tntCashAccount.balanceToYuan }</span>元；
						<c:if test="${tntCashAccount.balance< orderAmount}">
						您好，您的账户余额不足，无法支付，请尽快充值！<a href="javascript:;" id="pop01">如何充值？</a>
						</c:if>
					</p>
					<p>
						<b>请输入支付密码：</b>
						<sf:password class="yucun_password" path="paymentPassword" />
						<span class="tiptext tip-line errorTipsBox"> </span> <span
							class="tiptext tip-line tureTipsBox" style="display: none">
							<span class="tip-icon tip-icon-success"></span>
						</span>
					</p>
					<p>
						<b></b><a class="zhifu_btn" id="payButton">确认支付</a>
					</p>
				</div>
			</sf:form>
			<script src="/js/jquery.validate.min.js/"></script>
			<script src="/js/jquery.validate.expand.js/"></script>
			<script type="text/javascript">
				var payMent = {
					errorPlacement : function(error, element) {
						if (error.html() == "") {
							putOkSpan(element.attr("id"));
						} else {
							putErrorSpan(element.attr("id"), error.html());
						}
					},
					success : function(element) {
						putOkSpan(element.attr("id"));
					},
					rules : {
						paymentPassword : {
							required : true,
							clientRemote : {
								url : "/order/payValidate.do",
								type : "get",
								dataType : 'json',
								data : {
									orderId : function() {
										var orderId = $("#orderId").val();
										return orderId ? orderId : null;
									},
									orderIds : function() {
										var orderIds = $(
												"input[name='orderId']").val();
										return orderIds ? orderIds : null;
									},
									money : function() {
										var money = $("#money").val();
										return money ? money : null;
									}
								}
							}
						}
					},
					messages : {
						paymentPassword : {
							required : "请输入支付密码",
							clientRemote : "请修正支付密码"
						}
					}
				};
				var form = $("#payForm");
				form.validate(payMent);
				$("#payButton").click(function() {
					if (!form.validate().form()) {
						return;
					}
					form.submit();
				});
			</script>
		</div>
		<table width="1000" border="0" class="orderTable">
			<tr style="border: 1px solid #ebebeb;">
				<th class="first" width="200">订单号</th>
				<th width="550">产品名称</th>
				<th width="100">订单金额</th>
				<th width="150">游玩时间</th>
			</tr>
			<c:forEach items="${orders }" var="order">
				<tr>
					<td width="200" class="first">${order.orderId }</td>
					<td width="550"><c:forEach items="${order.mainOrderList }"
							var="item">
							<p>${item.productName } X ${item.quantity}</p>
						</c:forEach> <c:forEach items="${order.relativeOrderList }" var="item">
							<p>${item.productName } X ${item.quantity}</p>
						</c:forEach> <c:forEach items="${order.additionalOrderList }" var="item">
							<p>${item.productName } X ${item.quantity}</p>
						</c:forEach></td>
					<td width="100" class="money">&yen;${order.orderAmountYuan }</td>
					<td width="150"><lv:dateOutput date="${order.visitTime }" /></td>
				</tr>
			</c:forEach>
		</table>
	</div>
	<div id="demo01" style="display: none">
		<p>
			<b> 预存款充值需要通过银行转账的方式线下进行。<br> 银行转账时请注明用途为：预存款账户充值。<br>
				银行转账完成后，请电话联系我们的客服。<br> 驴妈妈确认银行转账到账后，会充值到您的保证金账户里。<br>
				处理时间周一至周五9:00--17:00，节假日不予处理。<br>
			</b>
		</p>
		<p id="accountDesc"></p>
	</div>
	<jsp:include page="/WEB-INF/pages/common/footer.jsp"></jsp:include>
	<script
		src="http://pic.lvmama.com/min/index.php?f=/js/v5/modules/pandora-poptip.js,/js/v4/modules/pandora-dialog.js"></script>
	<script type="text/javascript">
	$("#pop01").click(function(){
		var url = "/userspace/cashAccount/showAccount.do";
		$.getJSON(url, function(data) {
			if(data){
				var content = "驴妈妈账户：" + data.bankAccount + "（"
				+ data.bankName + "）<br/>开户人姓名："
				+ data.accountName
				+ "<br/>客服电话：021-51212088-3530";
				$("#accountDesc").html(content);				
				$.dialog({
					title : "如何充值",
					content : $("#demo01").html()
				});
			}
		});
	});
	</script>
</body>
</html>
