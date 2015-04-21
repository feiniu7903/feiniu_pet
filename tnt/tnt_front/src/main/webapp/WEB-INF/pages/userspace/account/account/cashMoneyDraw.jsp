<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
</head>
<body>
	<c:if test="${tntMoneyDrawList!=null}">
			<div class="p_box">
				<table width="760" border="0" class="tabTable04">
						<tr>
							<th width="150">日期</th>
		                    <th width="160">提现金额</th>
		   					<th width="140">收款户名</th>
		                    <th width="170">收款账号</th>
		    				<th width="90">状态</th>
						</tr>
						<c:forEach var="tntCashAccount" items="${tntMoneyDrawList}">
							<tr>
								<td>${tntCashAccount.cnCreateTime}</td>
								<td>${tntCashAccount.drawAmountToYuan}</td>
								<td>${tntCashAccount.bankAccountName}</td>
								<td>${tntCashAccount.bankAccount}</td>
								<td>${tntCashAccount.cnAuditStatus}</td>
							</tr>
						</c:forEach>
				</table>
				<c:if test="${page!=null }">
					<div class="moneyDrawClass_pags">
						${page.pagination}
					</div>
				</c:if>
			</div>
		</c:if>
</body>
<script type="text/javascript">
$(function(){
	$(".moneyDrawClass_pags").children().children().find("a").click(function(){
		if($(this).attr("href")!="#"){
			queryDiv($(this).attr("href"),"moneyDrawClass");
			return false;
		}
	});
});
</script>
</html>
