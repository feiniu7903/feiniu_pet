<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<sf:form id="editForm" modelAttribute="tntAnnouncement" method="post" target="_top">
	<table class="p_table form-inline">
		<tbody>
			<tr>
				<td class="e_label td_top" width="80px">标题：</td>
                <td>${tntAnnouncement.title}</td>
			</tr>
			<tr>
				<td class="e_label td_top" width="80px">内容：</td>
				<td>${tntAnnouncement.body}</td>
			</tr>
		</tbody>
	</table>
</sf:form>

