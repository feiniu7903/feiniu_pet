<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
</head>
<body>
	<c:if test="${tntCashRefundmentList!=null}">
			<div class="p_box">
				<table width="760" border="0" class="tabTable03">
						<tr>
							<th width="130">日期</th>
		                    <th width="420">商品名称</th>
		   					<th width="150">订单号码</th>
		    				<th width="60">消费金额</th>
						</tr>
						<c:forEach var="tntRefundment" items="${tntCashRefundmentList}">
							<tr>
								<td>${tntRefundment.cnCreateTime}</td>
								<td>${tntRefundment.productName}</td>
								<td>${tntRefundment.tntOrder.orderId}</td>
								<td>+${tntRefundment.amountToYuan}</td>
							</tr>
						</c:forEach>
				</table>
				<c:if test="${page!=null }">
					<div class="refundmentClass_pags">
						${page.pagination}
					</div>
				</c:if>
			</div>
		</c:if>
</body>
<script type="text/javascript">
$(function(){
	$(".refundmentClass_pags").children().children().find("a").click(function(){
		if($(this).attr("href")!="#"){
			queryDiv($(this).attr("href"),"refundmentClass");
			return false;
		}
	});
});
</script>
</html>
