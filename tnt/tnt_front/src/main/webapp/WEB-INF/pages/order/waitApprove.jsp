<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="lv" uri="/WEB-INF/pages//tld/lvmama-tags.tld"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>资源审核</title>
<link rel="shortcut icon" type="image/x-icon" href="http://www.lvmama.com/favicon.ico">
<link
	href="http://pic.lvmama.com/min/index.php?f=/styles/v5/modules/tip.css"
	rel="stylesheet" type="text/css">
<link
	href="http://pic.lvmama.com/min/index.php?f=/styles/v5/modules/button.css"
	rel="stylesheet" type="text/css">
<link href="http://pic.lvmama.com/styles/fx/b2b_fx.css" rel="stylesheet"
	type="text/css">
</head>
<body>
	<jsp:include page="/WEB-INF/pages/common/head.jsp"></jsp:include>
	<div class="orderstep_wrap">
		<div class="orderstep step02"></div>
		<div class="tipbox tip-success tip-nowrap">
			<span class="tip-icon-big tip-icon-big-success"></span>
			<div class="tip-content">
				<h3 class="tip-title">订单提交成功，正在进行资源审核中，请稍做等待！</h3>
			</div>
		</div>
		<h1 class="orderH1">
			<c:forEach items="${orders }" var="order">
				<p>
					<b>预订：</b>${order.productName }
				</p>
			</c:forEach>
		</h1>
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
	<jsp:include page="/WEB-INF/pages/common/footer.jsp"></jsp:include>
</body>
</html>
