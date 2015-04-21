<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:set var="basePath"><%=request.getContextPath()%></s:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>驳回</title>
<s:include value="/WEB-INF/pages/pub/suggest.jsp" />
<script type="text/javascript" src="${basePath}/js/base/form.js"></script>
</head>
<body>
	<div>
		<s:hidden name="contract.contractId" id="reject_contractId" />
		<table id="reject_table" class="cg_xx" cellspacing="0" cellpadding="0" width="100%">
			<tr>
				<td colspan="6"><font color="red">*</font>录入错误：</td>
			</tr>
			<tr>
				<td colspan="6">供应商基本信息：</td>
			</tr>
			<tr>
				<td><input type="checkbox" value="供应商名称" />供应商名称</td>
				<td><input type="checkbox" value="供应商类型" />供应商类型</td>
				<td><input type="checkbox" value="所在省市" />所在省市</td>
				<td><input type="checkbox" value="地址" />地址</td>
				<td><input type="checkbox" value="供应商电话" />供应商电话</td>
				<td><input type="checkbox" value="传真" />传真</td>
			</tr>
			<tr>
				<td><input type="checkbox" value="邮编" />邮编</td>
				<td><input type="checkbox" value="我方负责人" />我方负责人</td>
				<td colspan="4">&nbsp;</td>
			</tr>
			<tr>
				<td colspan="6">结算对象基本信息：</td>
			</tr>
			<tr>
				<td><input type="checkbox" value="结算周期" />结算周期</td>
				<td><input type="checkbox" value="开户名称" />开户名称</td>
				<td><input type="checkbox" value="开户银行" />开户银行</td>
				<td><input type="checkbox" value="开户账号" />开户账号</td>
				<td><input type="checkbox" value="支付宝账号" />支付宝账号</td>
				<td><input type="checkbox" value="支付宝用户名" />支付宝用户名</td>
			</tr>
			<tr>
				<td colspan="6">合同基本信息：</td>
			</tr>
			<tr>
				<td><input type="checkbox" value="合同编号" />合同编号</td>
				<td><input type="checkbox" value="合同类型" />合同类型</td>
				<td><input type="checkbox" value="有效期" />有效期</td>
				<td><input type="checkbox" value="签署日期" />签署日期</td>
				<td><input type="checkbox" value="会计主体" />会计主体</td>
				<td><input type="checkbox" value="采购产品经理" />采购产品经理</td>
			</tr>
			<tr>
				<td colspan="6">其他：<input type="text" name="memo"
					id="reject_memo" /> <input type="button" value="确认驳回" class="button"
					id="reject_submit" />
				</td>
			</tr>
		</table>
	</div>
	<script type="text/javascript">
		$(function() {
			$("#reject_submit").click(function() {
				var contractId = $("#reject_contractId").val();
				if(contractId == "") {
					alert("合同编号为空");
					return false;
				}
				var $reject_table = $("#reject_table");
				var content = "";
				$reject_table.find("input[type=checkbox]").each(function() {
					var $this = $(this);
					if ($this.attr("checked")) {
						content += $this.val() + "、";
					}
				});
				if ($.trim(content) == "") {
					alert("请选择错误内容");
					return false;
				}
				var memo = $("reject_memo").val();
				if($.trim(memo) != "") {
					content += "," + memo;
				}
				content = content.substring(0, content.length - 1);
				$.post("/pet_back/contract/doRejectContract.do", {
					"contract.contractId" : contractId,
					"content" : content
				}, function(dt) {
					var data = eval("(" + dt + ")");
					if (data.success) {
						alert("操作成功");
						window.location.reload();
					} else {
						alert(data.msg);
					}
				});
			});
		});
	</script>
</body>
</html>