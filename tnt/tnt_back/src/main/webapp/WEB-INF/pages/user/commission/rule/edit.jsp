<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<sf:form id="editForm" modelAttribute="tntCommissionRule" target="_top"
	action="/user/commission/rule">
	<input type="hidden" name="_method" value="put" />
	<sf:hidden path="commissionRuleId" />
	<table class="p_table form-inline">
		<tbody>
			<tr>
				<td class="p_label"><span class="notnull">*</span>产品类型：</td>
				<td><sf:select path="productType"
						onchange="changeProductType(this,'addTypeBox_subProductType')">
						<sf:option value="" label="--请选择--" />
						<sf:options items="${productTypeMap}" />
					</sf:select></td>
			</tr>
			<tr>
				<td class="p_label"><span class="notnull">*</span>子类型：</td>
				<td><sf:select path="subProductType"
						id="addTypeBox_subProductType">
						<sf:option value="" label="--请选择--" />
						<sf:options items="${subProductTypeMap }" />
					</sf:select></td>
			</tr>
			<tr>
				<td class="p_label"><span class="notnull">*</span>支付方式：</td>
				<td><sf:select path="payOnline">
						<sf:option value="" label="--请选择--" />
						<sf:options items="${payOnlineMap}" />
					</sf:select></td>
			</tr>
			<tr>
				<td class="p_label"><span class="notnull">*</span>月销售额：</td>
				<td><sf:input path="minSales" size="5" />-<sf:input
						path="maxSales" size="5" /></td>
			</tr>
			<tr>
				<td class="p_label"><span class="notnull">*</span>分销返佣点：</td>
				<td><sf:input path="discountRate" /></td>
			</tr>
		</tbody>
	</table>
	<div>
		<input class="pbtn pbtn-small btn-ok" id="typeSaveButton"
			style="float: right; margin-top: 20px;" type="button" value="保存" />
	</div>
</sf:form>
<script type="text/javascript">
	$().ready(function() {
		$("#editForm").validate(commissionRule);
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

