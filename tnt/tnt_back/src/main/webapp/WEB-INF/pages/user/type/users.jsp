<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="iframe_content pd0" id="users">
	<!-- 
	<div class="mb10 f14">
		分销商类型：<strong></strong>
	</div>
	 -->
	<table class="pg_d_table table_center">
		<thead>
			<tr>
				<th>编号</th>
				<th>分销商名称</th>
				<th>公司名称</th>
				<th>分销商用户名</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${tntUserList }" var="tntUser">
				<tr>
					<td>${tntUser.userId }</td>
					<td>${tntUser.realName }</td>
					<td><c:if test="${tntUser.isCompany}">
										${tntUser.detail.companyName}
									</c:if> <c:if test="${!tntUser.isCompany}">
										个人
									</c:if></td>
					<td>${tntUser.userName }</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<c:if test="${page!=null }">
			${page.pagination}
			<script src='<c:url value="/js/ajaxpage.js/"/>'></script>
		<script type="text/javascript">
			$(function() {
				setAjaxPages("users", "users", "users");
			});
		</script>
	</c:if>
</div>