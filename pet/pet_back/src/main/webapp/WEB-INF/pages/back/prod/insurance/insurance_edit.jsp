<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="mis" uri="/tld/lvmama-tags.tld"%>
<s:set var="basePath"><%=request.getContextPath()%></s:set>
<html>
<head>
	<title><s:if test="insurance.productId == null">新增保险产品</s:if><s:else>修改保险产品</s:else></title>
</head>
<body>
	<form id="editForm" action="${basePath}/prod/insurance/save.do" method="post">
		<table class="cg_xx" width="760" border="0" cellspacing="0" cellpadding="0">
			<s:hidden name="insurance.productId" />
			<tr>
				<td class="td_r"><span class="red">*</span>产品名称：</td>
				<td><s:textfield key="insurance.productName" cssClass="required"/></td>
				<td class="td_r"><span class="red">*</span>产品编号：</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td class="td_r"><span class="red">*</span>产品经理：</td>
				<td><s:hidden key="insurance.managerId"/><s:textfield key="permUser.realName" id="search_managerName" /></td>
				<td class="td_r"><span class="red">*</span>所属分公司：</td>
				<td><s:select list="#{'BJ_FILIALE':'北京分部','CD_FILIALE':'成都分部','GZ_FILIALE':'广州分部','HS_FILIALE':'黄山办事处','HZ_FILIALE':'杭州分部','SH_FILIALE':'上海总部','SY_FILIALE':'三亚分部','XM_FILIALE':'厦门办事处'}" key="insurance.filialeName"></s:select></td>
			</tr>			
			<tr>
				<td class="td_r"><span class="red">*</span>供应商：</td>
				<td><s:select list="#{'21':'平安保险','6054':'大众保险'}" key="insurance.supplierId"></s:select></td>
				<td class="td_r"><span class="red">*</span>采购价：</td>
				<td><s:textfield key="insurance.settlementPriceYuan" cssClass="{required:true, number:true, min:0}"/>(单位：元)</td>
			</tr>
			<tr>
				<td class="td_r"><span class="red">*</span>市场价：</td>
				<td><s:textfield key="insurance.sellPriceYuan" cssClass="{required:true, number:true, min:0}" />(单位：元)</td>
				<td class="td_r"><span class="red">*</span>销售价：</td>
				<td><s:textfield key="insurance.marketPriceYuan" cssClass="{required:true, number:true, min:0}" />(单位：元)</td>
			</tr>
			<tr>
				<td class="td_r"><span class="red">*</span>保险有效天数：</td>
				<td><s:textfield key="insurance.days" cssClass="{required:true, number:true, min:0}"/>(单位：天)</td>
				<td class="td_r"><span class="red">*</span>供应商产品代码：</td>
				<td><s:textfield key="insurance.productIdSupplier" cssClass="{required:true, number:true, min:0}"/></td>
			</tr>
			<tr>
				<td class="td_r">产品描述：</td>
				<td colspan="3"><s:textarea key="insurance.description" rows="10" cols="100"/></td>
			</tr>
		</table>
		<center>
			<input type="submit" value="保存 " class="button"/>
		</center>
	</form>
	<script type="text/javascript">
	$(function(){
		$("#search_managerName").jsonSuggest({
			url : "${basePath}/perm_user/search_user.do",
			maxResults : 10,
			width : 300,
			minCharacters : 1,
			onSelect : function(item) {
				$("#insurance_managerId").val(item.id);
			}
		});
		
		$("#editForm").validateAndSubmit(function($form,dt) {
			var data=eval("("+dt+")");
			if (data.success) {
				alert('<s:if test="insurance.productId == null">新增保险产品成功</s:if><s:else>修改保险产品成功</s:else>');
				$("#editInsurance").dialog("close");
				window.location.reload();
			} else {
				alert('操作失败，请重新尝试!');
			}
		});
	});
	</script>
</body>
</html>