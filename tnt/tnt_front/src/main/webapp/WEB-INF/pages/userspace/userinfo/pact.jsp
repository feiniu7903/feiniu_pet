<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<title>我的合同</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="http://pic.lvmama.com/styles/fx/b2b_fx.css" rel="stylesheet"
	type="text/css">
<link
	href="http://pic.lvmama.com/min/index.php?f=/styles/v5/modules/paging.css"
	rel="stylesheet" type="text/css">
<script src="/js/ajaxpage.js/"></script>
</head>
<body>
	<h3>我的合同</h3>
	<div class="rightBox" id="queryBox">
		<table width="760" border="0" class="pact">
			<tr>
				<th width="340">合同名称</th>
				<th width="300">上传日期</th>
				<th width="120">操作</th>
			</tr>
			<c:forEach items="${tntContractList }" var="tntContract">
				<tr>
					<td>${tntContract.contractName }</td>
					<td>${tntContract.strUploadTime }</td>
					<td><a
						href="/pet/ajax/file/downLoad?fileId=${tntContract.attachment}">下载</a></td>
				</tr>
			</c:forEach>
		</table>
		<c:if test="${page!=null }">
			${page.pagination}
			<script type="text/javascript">
				$(function() {
					setAjaxPages("pact.do", "queryPactList.do");
				});
			</script>
		</c:if>
	</div>
</body>
</html>