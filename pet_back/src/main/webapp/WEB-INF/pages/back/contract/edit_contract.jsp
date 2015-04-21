<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:set var="basePath"><%=request.getContextPath()%></s:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>新建合同</title>
<script type="text/javascript"
	src="${basePath}/js/base/jquery.dateFormat-1.0.js"></script>
</head>
<body>
	<div>
		<form action="${basePath }/contract/doAddContract.do" method="post"
			id="addContractForm">
			<s:hidden name="supplier.supplierId" />
			<p>
				<b>新建供应商</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input type="button"
					value="关联已有供应商" class="relate_supplier button" /><span
					id="supplier_id"></span>
			</p>
			<table class="cg_xx" cellspacing="0" cellpadding="0" width="100%">
				<tr>
					<td colspan="6"><h3>供应商基本信息：</h3></td>
				</tr>
				<tr>
					<td><font color="red">*</font>供应商名称：</td>
					<td><s:textfield name="supplier.supplierName"
							data-options="required:true" cssClass="easyui-validatebox" maxlength="50" /></td>
					<td><font color="red">*</font>供应商类型：</td>
					<td><s:select list="supplierTypeList"
							data-options="required:true" listKey="code" headerValue="请选择"
							headerKey="" listValue="cnName" name="supplier.supplierType"
							cssClass="easyui-validatebox" /></td>
					<td><font color="red">*</font>所在省市：</td>
					<td id="supplier_cityName"><s:select list="provinceList"
							name="province" data-options="required:true"
							onchange="changeCity(this,'contractCitySelect');return false;"
							cssClass="easyui-validatebox" listKey="provinceId"
							listValue="provinceName" /> <s:select name="supplier.cityId"
							list="cityList" id="contractCitySelect"
							data-options="required:true" cssClass="easyui-validatebox"
							listKey="cityId" listValue="cityName" /></td>
				</tr>
				<tr>
					<td><font color="red">*</font>地址：</td>
					<td><s:textfield name="supplier.address"
							data-options="required:true" cssClass="easyui-validatebox" maxlength="100" /></td>
					<td><font color="red">*</font>供应商电话：</td>
					<td><s:textfield name="supplier.telephone"
							data-options="required:true" cssClass="easyui-validatebox" /></td>
					<td><font color="red">*</font>传真：</td>
					<td><s:textfield name="supplier.fax"
							data-options="required:true" cssClass="easyui-validatebox" /></td>
				</tr>
				<tr>
					<td><font color="red">*</font>邮编：</td>
					<td><s:textfield name="supplier.postcode"
							data-options="required:true" size="6"
							cssClass="easyui-validatebox" /></td>
					<td><font color="red">*</font>我方负责人：</td>
					<td><s:textfield name="supplier.bosshead"
							data-options="required:true" class="easyui-validatebox" /></td>
					<td>法定代表人：</td>
					<td><s:textfield name="supplier.legalPerson" /></td>
				</tr>

				<tr>
					<td>旅行社许可证：</td>
					<td><s:textfield name="supplier.travelLicense" /></td>
					<td>父供应商：</td>
					<td><s:hidden name="supplier.parentId" id="supplier_parentId" /><input
						type="text" name="supplier_suggest_id" id="supplier_suggest_id" /></td>
					<td>网址：</td>
					<td><s:textfield name="supplier.webSite" /></td>
				</tr>
				<tr>
					<td>我方结算主体</td>
					<td><s:select list="settlementCompanyList"
							name="supplier.companyId" listKey="code" listValue="cnName" /></td>
					<td>预存款预警金额：</td>
					<td><s:textfield name="supplier.advancedpositsAlertYuan" /></td>
					<td>押金回收时间：</td>
					<td><s:textfield name="supplier.foregiftsAlert"
							cssClass="date" /></td>
				</tr>
				<tr>
					<td>预存款余额：</td>
					<td id="supplier_advancedepositsBalance"></td>
					<td>押金余额：</td>
					<td id="supplier_foregiftsBalance"></td>
					<td>担保金余额：</td>
					<td id="supplier_guaranteeLimit"></td>
				</tr>
			</table>
			<br />
			<div id="targetsDiv" style="display: none;">
				<h3>结算对象：</h3>
				<table style="width: 100%" id="targetTable" border="0"
					class="zhanshi_table">
					<tr>
						<td>编号</td>
						<td>名称</td>
					</tr>
				</table>
			</div>
			<br />
			<div id="newTargetDiv">
				<table class="cg_xx" cellspacing="0" cellpadding="0" width="100%">
					<tr>
						<td colspan="6"><h3>结算对象基本信息：</h3></td>
					</tr>
					<tr>
						<td><font color="red">*</font>对象名称：</td>
						<td>自动生成</td>
						<td colspan="2"><font color="red">*</font>结算帐号：</td>
						<td colspan="2">其他：</td>
					</tr>
					<tr>
						<td rowspan="5">结算周期：</td>
						<td rowspan="5"><s:iterator value="settlementPeriodList"
								var="sp">
								<div>
									<input type="radio" name="settlementTarget.settlementPeriod"
										value="${sp.code}" />${sp.cnName}
									<s:if test="#sp.code=='PERORDER'">
										<span style="margin-left: 10px;">提前<s:textfield
												name="settlementTarget.advancedDays" cssStyle="width:40px;" />天结算
										</span>
									</s:if>
								</div>
							</s:iterator></td>
							<td>开户名称：</td>
									<td><s:textfield name="settlementTarget.bankAccountName" /></td>
							<td>类型：</td>
									<td><s:radio list="settlementTargetTypeList"
											name="settlementTarget.type" listKey="code"
											listValue="cnName" /></td>
							
							</tr><tr>
							<td>开户银行：</td>
									<td><s:textfield name="settlementTarget.bankName" /></td>
							<td>付款方式：</td>
									<td><s:select list="paymentTypeList"
											name="settlementTarget.paymentType" listValue="cnName"
											listKey="code" /></td>
											</tr>
								<tr>
									<td>开户账号：</td>
									<td><s:textfield name="settlementTarget.bankAccount" /></td>
									<td>联行号：</td>
									<td><s:textfield name="settlementTarget.bankLines" /></td>
								</tr>
								<tr>
									<td>支付宝账号：</td>
									<td><s:textfield name="settlementTarget.alipayAccount" /></td>
									<td rowspan="2">备注：</td>
									<td rowspan="2"><s:textarea name="settlementTarget.memo" cols="30"
											rows="3" /></td>
								</tr>
								<tr>
									<td>支付宝用户名：</td>
									<td><s:textfield name="settlementTarget.alipayName" /></td>
								</tr>
				</table>
				<br /> <h3>财务联系人：</h3>
				<table style="width: 100%" border="0" cellspacing="0"
					cellpadding="0" id="contacts_tab" class="zhanshi_table">
					<tr>
						<th>姓名</th>
						<th>电话</th>
						<th width="50%">说明</th>
						<th>操作</th>
					</tr>
				</table>
				<input type="button" class="button" value="新增联系人"
					id="bindContactBtn" />
			</div>
			<br />
			<table class="cg_xx" cellspacing="0" cellpadding="0" width="100%">
				<tr>
					<td colspan="6"><h3>合同基本信息：</h3></td>
				</tr>
				<tr>
					<td><font color="red">*</font>合同名称：</td>
					<td>自动生成</td>
					<td><font color="red">*</font>合同编号：</td>
					<td>
					<s:hidden name="contract.contractNo" id="contract_no" />
					<input type="text" id="contractNo_1" size="5" />-<input type="text" id="contractNo_2" size="5" />-<input type="text" id="contractNo_3" size="5" />-<input type="text" id="contractNo_4" size="5" />
					</td>
					<td><font color="red">*</font>合同类型：</td>
					<td><s:select list="contractTypesList"
							data-options="required:true" listKey="code" listValue="cnName"
							name="contract.contractType" cssClass="easyui-validatebox" /></td>
				</tr>
				<tr>
					<td><font color="red">*</font>有效期：</td>
					<td><s:textfield name="contract.beginDate" cssClass="date"
							readonly="readonly" /> 至<s:textfield name="contract.endDate"
							cssClass="date" readonly="readonly" /></td>
					<td><font color="red">*</font>签署日期：</td>
					<td><s:textfield name="contract.signDate" cssClass="date"
							readonly="readonly" /></td>
					<td>经办人：</td>
					<td><s:textfield name="contract.arranger" /></td>
				</tr>
				<tr>
					<td><font color="red">*</font>采购产品经理：</td>
					<td><s:hidden name="contract.managerId" id="search_managerId" />
						<s:textfield name="managerName" id="search_managerName" /></td>
					<td><font color="red">*</font>会计主体：</td>
					<td colspan="3"><s:select list="finAccountingEntityList"
							data-options="required:true" listKey="accountingEntityId"
							headerValue="请选择" headerKey="" listValue="name"
							name="contract.partyA" cssClass="easyui-validatebox" /></td>
				</tr>
			</table>
			<div>
				<center>
					<input type="button" class="button" value="确定"
						id="add_contract_submit" />
				</center>
			</div>
		</form>
	</div>
	<div id="relate_supplier_div" url="${basePath}/sup/relateSupplier.do"></div>
	<div id="edit_contact_div" url="${basePath}/contract/contact_index.do"></div>
	<script type="text/javascript">
		$(function() {
			$(document)
					.ready(
							function() {
								$("input.date").attr("readonly", true)
										.datepicker({
											dateFormat : 'yy-mm-dd',
											changeMonth : true,
											changeYear : true,
											showOtherMonths : true,
											selectOtherMonths : true,
											buttonImageOnly : true
										});

								$(
										"input[name='settlementTarget.settlementPeriod'][value='PERMONTH']")
										.attr("checked", true);
								$("input[name='settlementTarget.advancedDays']")
										.attr("readonly", true);
								$("select[name='contract.contractType']").find(
										"option[value='COOPERATION']").attr(
										"selected", true);
								$("#supplier_suggest_id")
										.jsonSuggest(
												{
													url : basePath
															+ "/sup/searchSupplierJSON.do",
													maxResults : 10,
													width : 300,
													emptyKeyup : false,
													minCharacters : 1,
													onSelect : function(item) {
														$("#supplier_parentId")
																.val(item.id);
													}
												}).change(function() {
											$("#supplier_parentId").val("");
										});
								$("#search_managerName")
										.jsonSuggest(
												{
													url : basePath
															+ "/perm_user/search_user.do",
													maxResults : 10,
													width : 300,
													minCharacters : 1,
													onSelect : function(item) {
														$("#search_managerId")
																.val(item.id);
													}
												});
								$("input[name='contract.beginDate']")
										.change(
												function() {
													var d = $(this).val();
													var strs = d.split('-');
													var date = new Date();
													date.setUTCFullYear(strs[0], strs[1] - 1, strs[2]);
													date.setUTCHours(0, 0, 0, 0);
													date.setFullYear(date
															.getFullYear() + 1);
													$(
															"input[name='contract.endDate']")
															.val(
																	$.format
																			.date(
																					date,
																					"yyyy-MM-dd"));
												});

								$(
										"input[name='settlementTarget.type'][value='COMPANY']")
										.attr("checked", true);
								$("input[name='settlementTarget.type']")
										.change(function() {
											var val = $(this).val();
											changePaymentType(val);
										});
								changePaymentType("COMPANY");
							});

		})

		function changePaymentType(val) {
			if (val == "COMPANY") {
				$("select[name='settlementTarget.paymentType']").attr(
						"disabled", true).find("option[value='TRANSFER']")
						.attr("selected", true);
			} else {
				$("select[name='settlementTarget.paymentType']").removeAttr(
						"disabled");
			}
		}
	</script>
</body>
</html>