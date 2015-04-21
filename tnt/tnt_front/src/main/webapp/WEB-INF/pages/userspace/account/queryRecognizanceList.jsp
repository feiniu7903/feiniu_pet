<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="lv" uri="/WEB-INF/pages/tld/lvmama-tags.tld"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="http://pic.lvmama.com/styles/fx/b2b_fx.css" rel="stylesheet"
	type="text/css">
<script src="/js/ajaxpage.js/"></script>
</head>
<table width="760" border="0" class="pre_deposit_table">
	<tr>
		<th width="180">日期</th>
		<th width="180">金额</th>
		<th width="180">类型</th>
		<th width="180">状态</th>
		<th width="180">原因</th>
	</tr>
	<c:forEach items="${tntRecognizanceChangeList }"
		var="tntRecognizanceChange">
		<tr>
			<td>${tntRecognizanceChange.strCreateTime }</td>
			<c:if test="${tntRecognizanceChange.debit==true }">
				<td><strong>-${tntRecognizanceChange.amountY }</strong></td>
			</c:if>
			<c:if test="${tntRecognizanceChange.debit==false }">
				<td><b>+${tntRecognizanceChange.amountY }</b></td>
			</c:if>
			<td>${tntRecognizanceChange.typeName }</td>
			<td><lv:mapValueShow
					key="${tntRecognizanceChange.approveStatus }"
					map="${approveStatusMap }"></lv:mapValueShow></td>
			<td>${tntRecognizanceChange.reason }</td>
		</tr>
	</c:forEach>
</table>
<c:if test="${page!=null }">
			${page.pagination}
			<script type="text/javascript">
				$(function() {
					setAjaxPages("queryRecognizanceList.do",
							"queryRecognizanceList.do");
				});
			</script>
</c:if>
</html>
