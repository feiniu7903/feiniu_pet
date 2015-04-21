<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="lv" uri="/WEB-INF/pages//tld/lvmama-tags.tld"%>
<!DOCTYPE html>
<html>
<head>
</head>
<body>
	<c:if test="${tntCashCommissionList!=null}">
			<div class="p_box">
				<table class="p_table table_center">
					<thead>
						<tr>
							<th>日期</th>
							<th>返佣金额</th>
							<th>销售总金额</th>
							<th>返佣比率</th>
							<th>产品类型</th>
							<th>履行时间</th>
						</tr>
					</thead>
					<tbody>
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
					</tbody>
				</table>
				<c:if test="${page!=null }">
					<div class="paging commissionClass_pags">
						<p class="page_msg cc3">
							共 <em class="cc1">${page.totalResultSize }</em> 条记录，每页显示 10 条记录
						</p>
						<div class="paging">${page.pagination}</div>
					</div>
				</c:if>
			</div>
		</c:if>
		<c:if test="${tntCashCommissionList==null}">
			<div class="no_data mt20">
				<i class="icon-warn32"></i>暂无相关条目！
			</div>
		</c:if>
</body>
<script type="text/javascript">
$(function(){
	$(".commissionClass_pags").children().children().children().find("a").click(function(){
		if($(this).attr("href")!="#"){
			query($(this).attr("href"),"commissionClass");
			return false;
		}
	});
});
</script>
</html>
