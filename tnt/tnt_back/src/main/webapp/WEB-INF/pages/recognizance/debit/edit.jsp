<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<sf:form id="rechargeForm" modelAttribute="tntRecognizanceChange"
	target="_top" action="/recognizance/edit" method="post">
	<input type="hidden" name="_method" value="put" />
	<sf:hidden path="changeId" />
	<table class="p_table form-inline">
		<tbody>
			<tr>
				<td class="p_label"><span class="notnull">*</span>分销商用户名：</td>
				<td><sf:input readonly="true" path="userName"
						id="rechargeForm_userName" /></td>
			</tr>
			<tr>
				<td class="p_label"><span class="notnull">*</span>扣款额度：</td>
				<td><sf:input path="amountY" /></td>
			</tr>
			<tr>
				<td class="p_label"><span class="notnull">*</span>扣款原因：</td>
				<td><sf:textarea path="reason" cssStyle="width:250px;" rows="5" /></td>
			</tr>
		</tbody>
	</table>
</sf:form>
<input class="pbtn pbtn-small btn-ok" id="rechargeButton"
	style="float: right; margin-top: 20px;" type="button" value="保存" />
<script type="text/javascript">
	$().ready(function() {
		$("#rechargeForm").validate(recognizance);
	});
	$("#rechargeButton").bind("click", function() {
		var form = $("#rechargeForm");
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
