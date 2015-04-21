<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>常见问题</title>
<link href="http://pic.lvmama.com/styles/fx/b2b_fx.css" rel="stylesheet"
	type="text/css">
<link
	href="http://pic.lvmama.com/min/index.php?f=/styles/v5/modules/paging.css"
	rel="stylesheet" type="text/css">
<script src="http://pic.lvmama.com/js/new_v/jquery-1.7.2.min.js"></script>
</head>
<body>
	<h3>常见问题</h3>
	<div class="rightBox" id="queryBox">
		<c:if test="${tntFAQList!=null}">
			<c:forEach var="tntFAQ" items="${tntFAQList}">
				<dl>
					<dt>
						<span>Q</span> ${tntFAQ.question}
					</dt>
					<dd>
						<span>A</span>
						<p>${tntFAQ.answer}</p>
					</dd>
				</dl>
			</c:forEach>
			<c:if test="${page!=null }">
				${page.pagination}
				<script src="/js/ajaxpage.js/"></script>
				<script type="text/javascript">
					$(function() {
						setAjaxPages("faq", "queryFAQList");
					});
				</script>
			</c:if>
		</c:if>
	</div>
</body>
</html>