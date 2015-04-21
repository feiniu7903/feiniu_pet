<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<sf:form id="recognizanceForm" modelAttribute="tntRecognizanceChange"
	method="post" action="/recognizance" target="_top">
	<sf:hidden path="userId" />
	<table class="p_table form-inline">
		<tbody>
			<tr>
				<td colspan="2">请按照合同设置该分销商需要缴纳的保证金额度！</td>
			</tr>
			<tr>
				<td class="p_label"><span class="notnull">*</span>保证金额度：</td>
				<td><sf:input path="amountY" /></td>
			</tr>
			<tr>
				<td class="p_label"><span class="notnull">*</span>设置额度值原因：</td>
				<td><sf:textarea path="reason" cssStyle="width:250px;" rows="5" /></td>
			</tr>
		</tbody>
	</table>
</sf:form>
<input class="pbtn pbtn-small btn-ok" id="recognizanceButton"
	style="float: right; margin-top: 20px;" type="button" value="保存" />
<script type="text/javascript">
	$().ready(function() {
		$("#recognizanceForm").validate(recognizance);
	});

	$("#recognizanceButton").bind("click", function() {
		var form = $("#recognizanceForm");
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

