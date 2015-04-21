<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="lv" uri="/WEB-INF/pages//tld/lvmama-tags.tld"%>
<!DOCTYPE html>
<html>
<head>
</head>
<body>
	<c:if test="${tntCashRechargeList!=null}">
			<div class="p_box">
				<table class="p_table table_center">
					<thead>
						<tr>
							<th>日期</th>
							<th>充值金额</th>
							<th>转账单编号</th>
							<th>转账单日期</th>
							<th>分销商姓名</th>
							<th>分销商银行账号</th>
							<th>分销商银行账户名</th>
							<th>转账银行</th>
							<th>状态</th>
							<th>原因</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="tntCashAccount" items="${tntCashRechargeList}">
							<tr>
								<td>${tntCashAccount.cnCreateTime}</td>
								<td>${tntCashAccount.amountToYuan}</td>
								<td>${tntCashAccount.billNo}</td>
								<td>${tntCashAccount.billTime}</td>
								<td>${tntCashAccount.tntUser.realName}</td>
								<td>${tntCashAccount.bankAccount}</td>
								<td>${tntCashAccount.bankAccountName}</td>
								<td>${tntCashAccount.bankName}</td>
								<td>${tntCashAccount.cnStatus}</td>
								<td>${tntCashAccount.reason}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<c:if test="${page!=null }">
					<div class="paging rechargeClass_pags">
						<p class="page_msg cc3">
							共 <em class="cc1">${page.totalResultSize }</em> 条记录，每页显示 10 条记录
						</p>
						<div class="paging">${page.pagination}</div>
					</div>
				</c:if>
			</div>
		</c:if>
		<c:if test="${tntCashRechargeList==null}">
			<div class="no_data mt20">
				<i class="icon-warn32"></i>暂无相关条目！
			</div>
		</c:if>
</body>
<script type="text/javascript">
$(function(){
	$(".rechargeClass_pags").children().children().children().find("a").click(function(){
		if($(this).attr("href")!="#"){
			query($(this).attr("href"),"rechargeClass");
			return false;
		}
	});
});
</script>
</html>
