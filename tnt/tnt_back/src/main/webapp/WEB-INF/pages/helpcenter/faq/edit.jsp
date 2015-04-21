<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<sf:form id="editForm" modelAttribute="tntFAQ" method="post"
	action="/helpcenter/faq" target="_top">
	<input type="hidden" name="_method" value="put" />
	<sf:hidden path="tntFAQId" />

	<table class="p_table form-inline">
		<tbody>
			<tr>
				<td class="p_label"><span class="notnull">*</span>问题：</td>
				<td><sf:textarea path="question" cssStyle="width:250px;"
						rows="4" /></td>
			</tr>
			<tr>
				<td class="p_label"><span class="notnull">*</span>答案：</td>
				<td><sf:textarea path="answer" cssStyle="width:250px;"
						rows="6" /></td>
			</tr>
		</tbody>
	</table>
</sf:form>
<input class="pbtn pbtn-small btn-ok" id="typeSaveButton"
	style="float: right; margin-top: 20px;" type="button" value="保存" />

<script type="text/javascript">
	$().ready(function() {
		$("#editForm").validate(tntFAQ);
	});

	$("#typeSaveButton").bind("click", function() {
		var form = $("#editForm");
		if (!form.validate().form()) {
			return;
		}
		form.ajaxSubmit({
			success : function(data) {
				$("#searchForm").submit();
			},
			error : function(XmlHttpRequest, textStatus, errorThrown) {
				alert("系统处理异常，请确保您提交的数据的正确！");
			}
		});
	});
</script>

