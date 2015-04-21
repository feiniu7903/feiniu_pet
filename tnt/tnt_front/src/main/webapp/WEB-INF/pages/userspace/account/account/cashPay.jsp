<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
</head>
<body>
	<c:if test="${tntCashPayList!=null}">
		<table width="760" border="0" class="tabTable01">
						<tr>
							<th width="120">日期</th>
		                    <th width="420">商品名称</th>
		   					<th width="130">订单号码</th>
		    				<th width="50">消费金额</th>
						</tr>
						<c:forEach var="tntPay" items="${tntCashPayList}">
							<tr>
								<td>${tntPay.cnCreateTime}</td>
								<td>${tntPay.productName}</td>
								<td>${tntPay.tntOrder.orderId}</td>
								<td>-${tntPay.amountToYuan}</td>
							</tr>
						</c:forEach>
				</table>
				<c:if test="${page!=null }">
					<div class="payClass_pags">
						${page.pagination}
					</div>
				</c:if>			
	</c:if>
</body>
<script type="text/javascript">
$(function(){
	$(".payClass_pags").children().children().find("a").click(function(){
		if($(this).attr("href")!="#"){
			queryDiv($(this).attr("href"),"payClass");
			return false;
		}
	});
});
</script>
</html>
