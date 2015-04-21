<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
</head>
<body>
	<c:if test="${tntCashRechargeList!=null}">
			<div class="p_box">
				<table width="760" border="0" class="tabTable02">
					<tr>
						<th width="160">日期</th>
	                    <th width="150">充值金额</th>
	   					<th width="130">状态</th>
	    				<th width="320">原因</th>
					</tr>
					<c:forEach var="tntCashAccount" items="${tntCashRechargeList}">
						<tr>
							<td>${tntCashAccount.cnCreateTime}</td>
							<td>${tntCashAccount.amountToYuan}</td>
							<td>${tntCashAccount.cnStatus}</td>
							<td>${tntCashAccount.reason}</td>
						</tr>
					</c:forEach>
				</table>
				<c:if test="${page!=null }">
					<div class="rechargeClass_pags">
						${page.pagination}
					</div>
				</c:if>
			</div>
		</c:if>
</body>
<script type="text/javascript">
$(function(){
	$(".rechargeClass_pags").children().children().find("a").click(function(){
		if($(this).attr("href")!="#"){
			queryDiv($(this).attr("href"),"rechargeClass");
			return false;
		}
	});
});
</script>
</html>
