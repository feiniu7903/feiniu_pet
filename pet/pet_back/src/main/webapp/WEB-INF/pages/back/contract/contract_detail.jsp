<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:set var="basePath"><%=request.getContextPath()%></s:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>合同基本信息</title>
<script type="text/javascript" src="${basePath}/js/base/ajaxupload.js"></script>
</head>
<body>
	<div>
		<form action="${basePath }/contract/doEditContract.do" method="post"
			id="editContractForm">
			<s:hidden name="contract.contractId" />
			<table class="cg_xx" border="0" cellspacing="0" cellpadding="0"
				width="100%">
				<tr>
					<td><font color="red">*</font>合同编号：</td>
					<td><s:hidden name="contract.contractNo" id="contract_no" />
					<input type="text" id="contractNo_1" size="5" />-<input type="text" id="contractNo_2" size="5" />-<input type="text" id="contractNo_3" size="5" />-<input type="text" id="contractNo_4" size="5" /></td>
					<td><font color="red">*</font>合同类型：</td>
					<td><s:select list="contractTypesList"
							data-options="required:true" listKey="code" headerValue="请选择"
							headerKey="" listValue="cnName" name="contract.contractType"
							cssClass="easyui-validatebox" /></td>
					<td>合同状态：</td>
					<td>${contract.zhContractStatus }</td>
				</tr>
				<tr>
					<td><font color="red">*</font>有效期：</td>
					<td><input type="text" name="contract.beginDate"
						value="<s:date name="contract.beginDate" format="yyyy-MM-dd"/>"
						class="date" readonly="readonly" /> 至 <input type="text"
						name="contract.endDate"
						value="<s:date name="contract.endDate" format="yyyy-MM-dd"/>"
						class="date" readonly="readonly" /></td>
					<td><font color="red">*</font>签署日期：</td>
					<td><input type="text" name="contract.signDate"
						value="<s:date name="contract.signDate" format="yyyy-MM-dd"/>"
						class="date" readonly="readonly" /></td>
					<td>经办人：</td>
					<td><s:textfield name="contract.arranger" /></td>
				</tr>
				<tr>
					<td><font color="red">*</font>采购产品经理：</td>
					<td><s:hidden name="contract.managerId" id="search_managerId" />
						<s:textfield name="managerName" id="search_managerName" /></td>
					<td><font color="red">*</font>甲方：</td>
					<td colspan="3"><s:select list="finAccountingEntityList"
							data-options="required:true" listKey="accountingEntityId"
							headerValue="请选择" headerKey="" listValue="name"
							name="contract.partyA" cssClass="easyui-validatebox" /></td>
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
			<s:if
				test="contract.contractAudit != null && contract.contractAudit=='PASS'">
			</s:if>
			<s:else>
				<div>
					<input type="button" class="button" value="修改"
						id="edit_contract_btn" />
				</div>
			</s:else>
		</form>
		<br />
		<p><h3>变更及补充单：</h3></p>
		<table style="width: 100%" border="0" cellspacing="0" cellpadding="0"
			width="100%" class="zhanshi_table">
			<tr>
				<th>类型</th>
				<th>变更内容说明</th>
				<th>录入人/录入时间</th>
				<th>变更副本</th>
			</tr>
			<s:iterator value="contractChangeList" var="change">
				<tr>
					<td>${zhChangeType }</td>
					<td>${changeMemo }</td>
					<td>${operatorName }/<s:date name="createTime" format="yyyy-MM-dd HH:mm:ss"/></td>
					<td><s:if test="fsId != null">
							<a target="_blank"
								href="${basePath}/contract/downLoad.do?path=${fsId}">下载</a>
						</s:if> <s:else>
			 				<input type="button" class="button" value="上传" name="uploadChangeFile" changeId="${contractChangeId}" serverType="SUP_SUPPLIER_CONTRACT"/>
						</s:else></td>
				</tr>
			</s:iterator>
		</table>
		<br />
		<p><h3>对应供应商：</h3></p>
		<table style="width: 100%" border="0" cellspacing="0" cellpadding="0"
			width="100%" class="zhanshi_table">
			<tr>
				<th>编号</th>
				<th>供应商名称</th>
				<th>父供应商名称</th>
				<th>结算对象名称</th>
				<th>操作</th>
			</tr>
			<tr>
				<td><s:property value="supplier.supplierId" /></td>
				<td><s:property value="supplier.supplierName" /></td>
				<td><s:property value="supSupplierName" /></td>
				<td><s:iterator value="settlementTargetList" status="st">
					${name }
					<s:if test="st.index != settlementTargetList.size() - 1">
						/
					</s:if>
					</s:iterator></td>
				<td><a href="javascript:void(0);" onclick="window.open('/pet_back/sup/index.do?supplierId=${supplier.supplierId}');">去修改</a></td>
			</tr>
		</table>
	</div>
	<script type="text/javascript">
		$(function() {
			$(document).ready(function() {
				$("input.date").attr("readonly", true).datepicker({
					dateFormat : 'yy-mm-dd',
					changeMonth : true,
					changeYear : true,
					showOtherMonths : true,
					selectOtherMonths : true,
					buttonImageOnly : true
				});

				$("#search_managerName").jsonSuggest({
					url : basePath + "/perm_user/search_user.do",
					maxResults : 10,
					width : 300,
					minCharacters : 1,
					onSelect : function(item) {
						$("#search_managerId").val(item.id);
					}
				});
				
				var contractNo = $("#contract_no").val();
				if(contractNo != "") {
					var nos = contractNo.split("-");
					$("#contractNo_1").val(nos[0]);
					$("#contractNo_2").val(nos[1]);
					$("#contractNo_3").val(nos[2]);
					$("#contractNo_4").val(nos[3]);
				}
			});
			
			$("input[name='uploadChangeFile']").each(function() {
				var $this = $(this);
				var $btn = $this.fileUpload({
					onComplete:function(file,dt){
						var data=eval("("+dt+")");
						if(data.success){
							var fsId = data.file;
							var fsName = data.fileName;
							if(fsId == undefined || fsId == null || fsId == "") {
								alert("保存失败,请重新上传！");
								return false;
							}
							if(fsName == undefined || fsName == null || fsName == "") {
								alert("文件名称为空,请重新上传！");
								return false;
							}
							var $parent = $this.parents("td");
							if($parent != null && $parent != undefined) {
								$.ajax( {
									type : "POST",
									dataType : "json",
									url : "/pet_back/contract/updateContractChangeFile.do",
									async : false,
									data : {
										'contractChangeId' : $this.attr("changeId"),
										'fsId' : fsId
									},
									success : function(d) {
										if (d.success) {
											alert("上传成功");
											$btn.hide();
											$parent.html("<a target='_blank' href='/pet_back/contract/downLoad.do?path="+fsId+"'>下载</a>");
										} else {
											alert(d.msg);
										}
									}
								});
							};
						}else{
							alert(data.msg);
						}
					}});
			});
		});
	</script>
</body>
</html>