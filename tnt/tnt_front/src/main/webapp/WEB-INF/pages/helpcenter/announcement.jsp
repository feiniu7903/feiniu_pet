<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>平台公告</title>
<link
	href="http://pic.lvmama.com/min/index.php?f=/styles/v5/modules/paging.css"
	rel="stylesheet" type="text/css" />
<link href="http://pic.lvmama.com/styles/fx/b2b_fx.css" rel="stylesheet"
	type="text/css">
<script>
function ancHandler(ancId){
	var url = "/help/show_announcement_detail?id="+ancId;
	query(url);
}
</script>
</head>
<body>
	<h3>平台公告</h3>
	<div class="rightBox" id="queryBox">
		<ul class="notice">
			<c:if test="${tntAnnouncementList!=null}">
				<c:forEach var="tntAnnouncement" items="${tntAnnouncementList}">
					<li><span>${tntAnnouncement.formatdate}</span> <a href="#"
						onclick="javascript:ancHandler(${tntAnnouncement.announcementId})"
						title=${tntAnnouncement.title
								}
						id="${tntAnnouncement.announcementId}">${tntAnnouncement.title}</a></li>
				</c:forEach>
				<c:if test="${page!=null }">
					${page.pagination}
					<script src="/js/ajaxpage.js/"></script>
					<script type="text/javascript">
						$(function() {
							setAjaxPages("announcement", "queryAnnouncementList");
						});
					</script>
				</c:if>
			</c:if>
		</ul>
	</div>
</body>
</html>