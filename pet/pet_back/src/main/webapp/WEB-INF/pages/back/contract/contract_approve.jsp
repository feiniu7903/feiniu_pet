<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:set var="basePath"><%=request.getContextPath()%></s:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>审核</title>
<s:include value="/WEB-INF/pages/pub/suggest.jsp" />
<script type="text/javascript" src="${basePath}/js/base/city.js"></script>
<script type="text/javascript" src="${basePath}/js/base/form.js"></script>
<script type="text/javascript" src="${basePath}/js/contract/contract.js"></script>
</head>
<body>
	<div>
		<form action="${basePath }/contract/doApproveContract.do"
			id="contractApproveFrom" method="post">
			<s:hidden name="supplier.supplierId" />
			<s:hidden name="contract.contractId" />
			<table class="cg_xx" cellspacing="0" cellpadding="0" width="100%">
				<tr>
					<td colspan="6"><h3>供应商基本信息：</h3></td>
				</tr>
				<tr>
					<td><font color="red">*</font>供应商名称：</td>
					<td>${supplier.supplierName }</td>
					<td><font color="red">*</font>供应商类型：</td>
					<td><s:select list="supplierTypeList" cssClass="required"
							listKey="code" headerValue="请选择" headerKey="" listValue="cnName"
							name="supplier.supplierType" /></td>
					<td><font color="red">*</font>所在省市</td>
					<td id="supplier_cityName"><s:select list="provinceList"
							name="province" cssClass="required"
							onchange="changeCity(this,'contractCitySelect')"
							listKey="provinceId" listValue="provinceName" /> <s:select
							name="supplier.cityId" list="cityList" id="contractCitySelect"
							cssClass="required" listKey="cityId" listValue="cityName" /></td>
				</tr>
				<tr>
					<td><font color="red">*</font>地址：</td>
					<td>${supplier.address }</td>
					<td><font color="red">*</font>供应商电话：</td>
					<td><s:textfield name="supplier.telephone" cssClass="required" /></td>
					<td><font color="red">*</font>传真：</td>
					<td><s:textfield name="supplier.fax" cssClass="required" /></td>
				</tr>
				<tr>
					<td><font color="red">*</font>邮编：</td>
					<td><s:textfield name="supplier.postcode" size="6" cssClass="required" /></td>
					<td><font color="red">*</font>我方负责人：</td>
					<td><s:textfield name="supplier.bosshead" cssClass="required"
							class="easyui-validatebox" /></td>
					<td>法定代表人：</td>
					<td><s:textfield name="supplier.legalPerson" /></td>
				</tr>

				<tr>
					<td>旅行社许可证：</td>
					<td><s:textfield name="supplier.travelLicense" /></td>
					<td>父供应商：</td>
					<td><s:hidden name="supplier.parentId" id="supplier_parentId" />
						<s:textfield name="supSupplierName" id="supplier_suggest_id" /></td>
					<td>网址：</td>
					<td><s:textfield name="supplier.webSite" /></td>
				</tr>
				<tr>
					<td>预存款预警余额：</td>
					<td><s:textfield name="supplier.advancedpositsAlertYuan" /></td>
					<td>押金回收时间：</td>
					<td>
					<input type="text" name="supplier.foregiftsAlert"
						value="<s:date name="supplier.foregiftsAlert" format="yyyy-MM-dd"/>"
						class="date" />
					</td>
					<td>预存款余额：</td>
					<td>${supplier.advancedepositsBalanceYuan }</td>
				</tr>
				<tr>
					<td>押金余额：</td>
					<td>${supplier.foregiftsBalanceYuan }</td>
					<td>担保金余额：</td>
					<td>${supplier.guaranteeLimitYuan }</td>
					<td></td>
					<td></td>
				</tr>
			</table>
			<br />
			<s:if
				test="settlementTargetList != null">
				<div>
					<h3>结算对象：</h3>
					<table style="width: 100%" id="targetTable" border="0"
						class="zhanshi_table">
						<tr>
							<td>编号</td>
							<td>名称</td>
						</tr>
						<s:iterator value="settlementTargetList" var="target">
							<tr>
								<td>${targetId }</td>
								<td>${name }</td>
							</tr>
						</s:iterator>
					</table>
				</div>
			</s:if>
			<s:else>
				<s:if test="settlementTarget != null">
					<s:hidden name="settlementTarget.targetId" />
					<div>
						<table class="cg_xx" cellspacing="0" cellpadding="0" width="100%">
							<tr>
								<td colspan="5"><h3>结算对象基本信息：</h3></td>
							</tr>
							<tr>
								<td><font color="red">*</font>对象名称:</td>
								<td>${settlementTarget.name }</td>
								<td colspan="2"><font color="red">*</font>结算帐号:</td>
								<td>其他:</td>
							</tr>
							<tr>
								<td>结算周期</td>
								<td valign="top"><s:iterator value="settlementPeriodList"
										var="sp">
										<div>
											<input type="radio" name="settlementTarget.settlementPeriod"
												value="${sp.code}"
												<s:if test="#sp.code==settlementTarget.settlementPeriod">checked</s:if> />${sp.cnName}
											<s:if test="#sp.code=='PERORDER'">
												<span style="margin-left: 10px;">提前<s:textfield
														name="settlementTarget.advancedDays"
														cssStyle="width:40px;" />天结算
												</span>
											</s:if>
										</div>
									</s:iterator></td>
								<td colspan="2" valign="top">
									<table>
										<tr>
											<td>开户名称：</td>
											<td><s:textfield name="settlementTarget.bankAccountName" /></td>
										</tr>
										<tr>
											<td>开户银行：</td>
											<td><s:textfield name="settlementTarget.bankName" /></td>
										</tr>
										<tr>
											<td>开户账号：</td>
											<td><s:textfield name="settlementTarget.bankAccount" /></td>
										</tr>
										<tr>
											<td>支付宝账号：</td>
											<td><s:textfield name="settlementTarget.alipayAccount" /></td>
										</tr>
										<tr>
											<td>支付宝用户名：</td>
											<td><s:textfield name="settlementTarget.alipayName" /></td>
										</tr>
									</table>
								</td>
								<td colspan="2" valign="top">
									<table>
										<tr>
											<td>类型：</td>
											<td><s:radio list="settlementTargetTypeList"
													name="settlementTarget.type" listKey="code"
													listValue="cnName" /></td>
										</tr>
										<tr>
											<td>付款方式：</td>
											<td><s:select list="paymentTypeList"
													name="settlementTarget.paymentType" listValue="cnName"
													listKey="code" headerKey="" headerValue="请选择" /></td>
										</tr>
										<tr>
											<td>联行号：</td>
											<td><s:textfield name="settlementTarget.bankLines" /></td>
										</tr>
										<tr>
											<td>备注：</td>
											<td><s:textarea name="settlementTarget.memo" cols="30"
													rows="3" /></td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</div>
				</s:if>
			</s:else>
			<br />
			<table class="cg_xx" cellspacing="0" cellpadding="0" width="100%">
				<tr>
					<td colspan="6"><h3>合同基本信息：</h3></td>
				</tr>
				<tr>
					<td><font color="red">*</font>合同名称：</td>
					<td>${contract.contractName }</td>
					<td><font color="red">*</font>合同编号：</td>
					<td>
					<s:hidden name="contract.contractNo" id="contract_no" />
					<input type="text" id="contractNo_1" size="5" />-
					<input type="text" id="contractNo_2" size="5" />-
					<input type="text" id="contractNo_3" size="5" />-
					<input type="text" id="contractNo_4" size="5" />
					</td>
					<td><font color="red">*</font>合同类型：</td>
					<td><s:select list="contractTypesList" cssClass="required"
							listKey="code" headerValue="请选择" headerKey="" listValue="cnName"
							name="contract.contractType" /></td>
				</tr>
				<tr>
					<td><font color="red">*</font>有效期：</td>
					<td>
						<input type="text" name="contract.beginDate"
						value="<s:date name="contract.beginDate" format="yyyy-MM-dd"/>"
						class="date" readonly="readonly" /> 至 <input type="text"
						name="contract.endDate"
						value="<s:date name="contract.endDate" format="yyyy-MM-dd"/>"
						class="date" readonly="readonly" /></td>
					<td><font color="red">*</font>签署日期：</td>
					<td>						
						<input type="text" name="contract.signDate"
						value="<s:date name="contract.signDate" format="yyyy-MM-dd"/>"
						class="date" readonly="readonly" /></td>
					<td>经办人：</td>
					<td><s:textfield name="contract.arranger" /></td>
				</tr>
				<tr>
					<td><font color="red">*</font>采购产品经理：</td>
					<td><s:hidden name="contract.managerId" id="search_managerId" />
						<s:textfield name="managerName" id="search_managerName" />
					</td>
					<td><font color="red">*</font>会计主体：</td>
					<td colspan="3"><s:select list="finAccountingEntityList"
							cssClass="required" listKey="accountingEntityId"
							headerValue="请选择" headerKey="" listValue="name"
							name="contract.partyA" /></td>
					</tr>
					<tr>
					<td>合同扫描件：</td>
					<td colspan="5">
					<s:iterator value="supContractFsList">
						<a href="${basePath}/contract/downLoad.do?path=${fsId}" target="_blank"><s:property value="fsName"/> </a>&nbsp;
					</s:iterator>
				</td>
				</tr>
			</table>
			<div>
				<center>
					<input type="button" class="button" value="驳  回" 
					onclick="$('#reject_div').showWindow({width:1000,title:'驳回:',data:{'contract.contractId':'${contract.contractId}'}})" /> 
					&nbsp;&nbsp; <input type="button"
						class="button" value="审核通过" id="approve_submit" />
				</center>
			</div>
		</form>
		<div id="reject_div" url="${basePath}/contract/toRejectPage.do"></div>
	</div>
	<script type="text/javascript">
		$(function() {
			$(document).ready(function() {
				/*$("input.date").attr("readonly", true).datepicker({
					dateFormat : 'yy-mm-dd',
					changeMonth : true,
					changeYear : true,
					showOtherMonths : true,
					selectOtherMonths : true,
					buttonImageOnly : true
				});
				$("#supplier_suggest_id").jsonSuggest({
					url : basePath + "/sup/searchSupplierJSON.do",
					maxResults : 10,
					width : 300,
					emptyKeyup : false,
					minCharacters : 1,
					onSelect : function(item) {
						$("#supplier_parentId").val(item.id);
					}
				}).change(function() {
					$("#supplier_parentId").val("");
				});*/
				
				var contractNo = $("#contract_no").val();
				if(contractNo != "") {
					var nos = contractNo.split("-");
					$("#contractNo_1").val(nos[0]);
					$("#contractNo_2").val(nos[1]);
					$("#contractNo_3").val(nos[2]);
					$("#contractNo_4").val(nos[3]);
				}
				$("#contractApproveFrom").find("input[type='text']").attr("readonly", true);
				$("#contractApproveFrom").find("input[type='radio'],select").attr("disabled", true);
			});
			$("#approve_submit").click(function() {
				if(!window.confirm("确定审核通过?")){
					return false;
				}
				var $form=$("#contractApproveFrom");
				$.post($form.attr("action"),
						$form.serialize(),
						function(dt){
							var data = eval("(" + dt + ")");
							if (data.success) {
								alert("操作成功");
								window.location.reload();
							} else {
								alert(data.msg);
							}	
				});
			});
			/*$("#search_managerName").jsonSuggest(
					{
						url : basePath
						+ "/perm_user/search_user.do",
						maxResults : 10,
						width : 300,
						minCharacters : 1,
						onSelect : function(item) {
							$("#search_managerId").val(item.id);
						}
				});*/
		});
	</script>
</body>
</html>