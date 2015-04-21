<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="http://pic.lvmama.com/styles/fx/b2b_fx.css" rel="stylesheet"
	type="text/css">
<script src="http://pic.lvmama.com/js/new_v/jquery-1.7.2.min.js"></script>
<script>
function ancHandler(ancId){
	var url = "/help/show_announcement_detail?id="+ancId;
	query(url);
}
</script>
</head>
<body>
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
							setAjaxPages("queryAnnouncementList", "queryAnnouncementList");
						});
					</script>
			</c:if>
		</c:if>
	</ul>
</body>
</html>