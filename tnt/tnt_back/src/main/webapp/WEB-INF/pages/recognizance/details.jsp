<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="lv" uri="/WEB-INF/pages//tld/lvmama-tags.tld"%>
<!DOCTYPE html>
<html>
<body>

	<c:if test="${tntRecognizanceChangeList!=null}">
		<div class="p_box">
			<table class="p_table table_center">
				<thead>
					<tr>
						<th>类型</th>
						<th>金额</th>
						<th>日期</th>
						<th>转账单编号</th>
						<th>转账单日期</th>
						<th>分销商用户名</th>
						<th>银行账户名</th>
						<th>分销商银行账号</th>
						<th>转账银行</th>
						<th width="200px">原因</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="tntRecognizanceChange"
						items="${tntRecognizanceChangeList}">
						<tr>
							<td><lv:mapValueShow key="${tntRecognizanceChange.type}"
									map="${typeMap }" /></td>
							<td>${tntRecognizanceChange.amountY}</td>
							<td><lv:dateOutput
									date="${tntRecognizanceChange.createTime}"
									format="yyyy-MM-dd HH:mm" /></td>
							<td>${tntRecognizanceChange.billNo }</td>
							<td><lv:dateOutput date="${tntRecognizanceChange.billTime}" /></td>
							<td>${userName }</td>
							<td>${tntRecognizanceChange.bankAccountName }</td>
							<td>${tntRecognizanceChange.bankAccount }</td>
							<td>${tntRecognizanceChange.bankName }</td>
							<td>${tntRecognizanceChange.reason}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<c:if test="${page!=null }">
				<div class="paging">
					<p class="page_msg cc3">
						共 <em class="cc1">${page.totalResultSize }</em> 条记录，每页显示 10 条记录
					</p>
					<div id="dialogPage">${page.pagination}</div>
				</div>
				<script src='<c:url value="/js/ajaxpage.js/"/>'></script>
				<script type="text/javascript">
					var getRecognizanceDialog = function() {
						return recognizanceDialog;
					}
					dialogAjaxPages("getRecognizanceDialog()");
				</script>
			</c:if>
		</div>
	</c:if>
	<c:if test="${tntRecognizanceChangeList==null}">
		<div class="no_data mt20">
			<i class="icon-warn32"></i>暂无相关条目，重新输入相关条件查询！
		</div>
	</c:if>

</body>
</html>
