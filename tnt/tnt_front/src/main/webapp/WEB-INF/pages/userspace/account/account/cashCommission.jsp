<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
</head>
<body>
	<c:if test="${tntCashCommissionList!=null}">
	<table width="760" border="0" class="tabTable05">
  				<tr>
    				<th width="130">日期</th>
                    <th width="100">返佣金额</th>
   					<th width="160">销售总额度</th>
                    <th width="100">返佣比例</th>
    				<th width="70">产品类型</th>
                    <th width="140">履行时间</th>
  				</tr>
                <c:forEach var="tntCashCommission" items="${tntCashCommissionList}">
					<tr>
						<td>${tntCashCommission.cnCreateTime}</td>
						<td>${tntCashCommission.commisAmountToYuan}</td>
						<td>${tntCashCommission.totalAmountToYuan}</td>
						<td>${tntCashCommission.commisRate}</td>
						<td>${tntCashCommission.cnProductType}</td>
						<td>${tntCashCommission.cnPerformBeginDate} - ${tntCashCommission.cnPerformEndDate}</td>
					</tr>
				</c:forEach>
		</table>
		
		<c:if test="${page!=null }">
			<div class="commissionClass_pags">
				${page.pagination}
			</div>
		</c:if>
	</c:if> 
</body>
<script type="text/javascript">
$(function(){
	$(".commissionClass_pags").children().children().find("a").click(function(){
		if($(this).attr("href")!="#"){
			queryDiv($(this).attr("href"),"commissionClass");
			return false;
		}
	});
});
</script>
</html>
